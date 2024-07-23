package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.CalcifiedRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.LakeRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.SpecialMeadowRegion;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
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

        int blockY = Math.min(random.nextIntBetweenInclusive(-16, 48), baseHeight - random.nextIntBetweenInclusive(64, 96));
        var groveCenter = new BlockPos(blockX, blockY, blockZ);

        int groveRadius = (int) (random.nextIntBetweenInclusive(128, 192) * RandomHelper.randomBetween(random, Easing.CUBIC_OUT, 1.5f, 2f));
        int groveHeight = random.nextIntBetweenInclusive(32, 40);
        int groveDepth = random.nextIntBetweenInclusive(16, 20);

        List<SpecialMeadowRegion> specialRegions = new ArrayList<>();
        int calcifiedAreaCounter = RandomHelper.randomBetween(random, Easing.QUAD_IN, 0, 3);
        for (int i = 0; i < calcifiedAreaCounter; i++) {
            float x = 6.28f * random.nextFloat();
            float z = 6.28f * random.nextFloat();
            float distance = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, 0.7f, 0.9f);
            var directionalOffset = new Vec2(Mth.sin(x), Mth.cos(z)).normalized().scale(distance);
            float size = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 0.3f, 0.4f);
            var distributor = getRandomWeightedMineralFeatureDistributor();
            specialRegions.add(new CalcifiedRegion(directionalOffset, size, distributor));
        }

        int lakeCounter = RandomHelper.randomBetween(random, Easing.QUAD_IN, 4, 12);
        for (int i = 0; i < lakeCounter; i++) {
            boolean isCentralLake = i == 0;
            float sizeMultiplier = isCentralLake ? 2.5f : 1;
            float depthMultiplier = isCentralLake ? 2 : 1;
            float distanceMultiplier = isCentralLake ? 0.1f : 1;
            float x = 6.28f * i / lakeCounter;
            float z = 6.28f * i / lakeCounter;
            float distance = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, 0.3f, 0.4f) * distanceMultiplier;
            var directionalOffset = new Vec2(Mth.sin(x), Mth.cos(z)).normalized().scale(distance);
            float size = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 0.04f, 0.08f) * sizeMultiplier;
            float depth = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 0.2f, 0.6f) * depthMultiplier;
            float surfaceLevel = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 0.6f, 0.8f);

            specialRegions.add(new LakeRegion(directionalOffset, size, depth, surfaceLevel));
        }

        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG,
                (b) -> createGrovePieces(context, b, levelHeightAccessor, groveCenter, specialRegions, groveRadius, groveHeight, groveDepth));
    }

    private static void createGrovePieces(GenerationContext context, StructurePiecesBuilder piecesBuilder, LevelHeightAccessor levelHeightAccessor, BlockPos groveCenter, List<SpecialMeadowRegion> specialRegions, int groveRadius, int groveHeight, int groveDepth) {
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
                MeadowGrovePiece meadowGrovePiece = new MeadowGrovePiece(groveCenter, specialRegions, groveRadius, groveHeight, groveDepth, boundingBox);
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

}
