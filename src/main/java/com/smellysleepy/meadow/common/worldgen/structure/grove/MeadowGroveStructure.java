package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.MineralFeatureDistribution;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.*;

import static com.smellysleepy.meadow.common.worldgen.MineralFeatureDistribution.getRandomWeightedMineralFeatureDistributor;

public class MeadowGroveStructure extends Structure {

    public static final Codec<MeadowGroveStructure> CODEC = RecordCodecBuilder.<MeadowGroveStructure>mapCodec(builder ->
            builder.group(settingsCodec(builder))
                    .apply(builder, MeadowGroveStructure::new)
    ).codec();

    public MeadowGroveStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        RandomState randomState = context.randomState();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        var levelHeightAccessor = context.heightAccessor();

        int blockX = chunkPos.getBlockX(random.nextInt(16));
        int blockZ = chunkPos.getBlockZ(random.nextInt(16));

        int baseHeight = chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);

        int blockY = Math.min(random.nextIntBetweenInclusive(-32, 32), baseHeight - random.nextIntBetweenInclusive(64, 96));
        var groveCenter = new BlockPos(blockX, blockY, blockZ);

        int groveRadius = (int) (random.nextIntBetweenInclusive(128, 192) * RandomHelper.randomBetween(random, Easing.CUBIC_OUT, 1.25f, 2f));
        int groveHeight = random.nextIntBetweenInclusive(28, 32);
        int groveDepth = random.nextIntBetweenInclusive(16, 20);

        List<CalcifiedArea> calcifiedAreas = new ArrayList<>();
//        int calcifiedAreaCounter = RandomHelper.randomBetween(random, Easing.QUAD_IN, 0, 3);
        int calcifiedAreaCounter = 6;
        for (int i = 0; i < calcifiedAreaCounter; i++) {
            float x = 6.28f * random.nextFloat();
            float z = 6.28f * random.nextFloat();
            float distance = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, 0.5f, 1f);
            var direction = new Vec2(Mth.sin(x), Mth.cos(z)).normalized().scale(distance * groveRadius * 0.4f);
            var pos = new BlockPos.MutableBlockPos(groveCenter.getX() + direction.x, groveCenter.getY(), groveCenter.getZ() + direction.y);
            float size = RandomHelper.randomBetween(random, Easing.CIRC_IN, 0.0725f, 0.125f) * groveRadius;
            var distributor = getRandomWeightedMineralFeatureDistributor();
            calcifiedAreas.add(new CalcifiedArea(pos, distributor, size, distance > 0.7f && random.nextBoolean()));
        }

        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG,
                (b) -> createGrovePieces(context, b, levelHeightAccessor, groveCenter, calcifiedAreas, groveRadius, groveHeight, groveDepth));
    }

    private static void createGrovePieces(GenerationContext context, StructurePiecesBuilder piecesBuilder, LevelHeightAccessor levelHeightAccessor, BlockPos groveCenter, List<CalcifiedArea> calcifiedAreas, int groveRadius, int groveHeight, int groveDepth) {
        var chunkPos = context.chunkPos();
        int radius = SectionPos.blockToSectionCoord(groveRadius) + 1;

        Collection<MeadowGroveDecoratorPiece> decorators = new ArrayList<>();

        for (int chunkX = -radius; chunkX <= radius; chunkX++) {
            for (int chunkZ = -radius; chunkZ <= radius; chunkZ++) {
                int x = SectionPos.sectionToBlockCoord(chunkPos.x + chunkX);
                int z = SectionPos.sectionToBlockCoord(chunkPos.z + chunkZ);
                var chunkStartPos = new BlockPos(x, groveCenter.getY(), z);
                BoundingBox boundingBox = new BoundingBox(
                        chunkStartPos.getX(), levelHeightAccessor.getMinBuildHeight(), chunkStartPos.getZ(),
                        chunkStartPos.getX() + 15, levelHeightAccessor.getMaxBuildHeight(), chunkStartPos.getZ() + 15
                );
                MeadowGrovePiece meadowGrovePiece = new MeadowGrovePiece(groveCenter, calcifiedAreas, groveRadius, groveHeight, groveDepth, boundingBox);
                piecesBuilder.addPiece(meadowGrovePiece);

                decorators.add(new MeadowGroveDecoratorPiece(meadowGrovePiece, boundingBox));
            }
        }
        for (MeadowGroveDecoratorPiece decorator : decorators) {
            piecesBuilder.addPiece(decorator);
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return MeadowStructureTypes.MEADOW_GROVE.get();
    }

    public static class CalcifiedArea {
        private final BlockPos.MutableBlockPos center;

        private final ResourceKey<ConfiguredFeature<?, ?>> treeFeature;
        private final ResourceKey<ConfiguredFeature<?, ?>> plantFeature;
        private final ResourceKey<ConfiguredFeature<?, ?>> oreFeature;
        private final ResourceKey<ConfiguredFeature<?, ?>> patchFeature;
        private final double size;
        private final boolean isRampRegion;

        public CalcifiedArea(BlockPos.MutableBlockPos center, MineralFeatureDistribution distribution, double size, boolean isRampRegion) {
            this(center, distribution.tree, distribution.plant, distribution.ore, distribution.patch, size, isRampRegion);
        }
        public CalcifiedArea(BlockPos.MutableBlockPos center,
                             ResourceKey<ConfiguredFeature<?, ?>> treeFeature,
                             ResourceKey<ConfiguredFeature<?, ?>> plantFeature,
                             ResourceKey<ConfiguredFeature<?, ?>> oreFeature,
                             ResourceKey<ConfiguredFeature<?, ?>> patchFeature,
                             double size, boolean isRampRegion) {
            this.center = center;
            this.treeFeature = treeFeature;
            this.plantFeature = plantFeature;
            this.oreFeature = oreFeature;
            this.patchFeature = patchFeature;
            this.size = size;
            this.isRampRegion = isRampRegion;
        }

        public static CalcifiedArea deserialize(CompoundTag tag) {
            return new CalcifiedArea(
                    NbtUtils.readBlockPos(tag.getCompound("position")).mutable(),
                    ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("treeFeature"))),
                    ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("plantFeature"))),
                    ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("oreFeature"))),
                    ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("patchFeature"))),
                    tag.getDouble("size"),
                    tag.getBoolean("isRampRegion"));
        }

        public CompoundTag serialize() {
            CompoundTag tag = new CompoundTag();
            tag.put("position", NbtUtils.writeBlockPos(center));
            tag.putString("treeFeature", treeFeature.location().toString());
            tag.putString("plantFeature", plantFeature.location().toString());
            tag.putString("oreFeature", oreFeature.location().toString());
            tag.putString("patchFeature", patchFeature.location().toString());
            tag.putDouble("size", size);
            tag.putBoolean("isRampRegion", isRampRegion);
            return tag;
        }

        public double getDistance(BlockPos target) {
            return center.setY(target.getY()).distSqr(target);
        }

        public double getThreshold(double noise) {
            return Easing.SINE_IN_OUT.clamped(noise, 0.5f, 1f);
        }

        public double getDelta(BlockPos target, double noise) {
            float differenceX = center.getX() - target.getX();
            float differenceZ = center.getZ() - target.getZ();
            float distance = Mth.sqrt(differenceX * differenceX + differenceZ * differenceZ);
            return distance / (size * getThreshold(noise));
        }

        public boolean isRampRegion() {
            return isRampRegion;
        }

        public ResourceKey<ConfiguredFeature<?, ?>> chooseFeature(RandomSource randomSource) {
            return chooseFeature(randomSource, 1);
        }

        public ResourceKey<ConfiguredFeature<?, ?>> chooseFeature(RandomSource randomSource, float scalar) {
            float rand = randomSource.nextFloat();
            if (rand < 0.0025f * scalar) {
                return treeFeature;
            } else if (rand < 0.0075f * scalar) {
                return plantFeature;
            } else if (rand < 0.025f * scalar) {
                return patchFeature;
            } else if (rand < 0.05f * scalar) {
                return oreFeature;
            }
            return null;
        }
    }
}
