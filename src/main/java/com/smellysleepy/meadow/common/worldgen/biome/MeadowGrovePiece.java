package com.smellysleepy.meadow.common.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeadowGrovePiece extends StructurePiece {

    private final BlockPos groveCenter;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    public List<Pair<BlockPos, ResourceLocation>> bufferedFeatures = new ArrayList<>();

    protected MeadowGrovePiece(BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveCenter = groveCenter;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveDepth;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveCenter = NbtUtils.readBlockPos(tag.getCompound("origin"));
        this.groveRadius = tag.getInt("groveRadius");
        this.groveHeight = tag.getInt("groveHeight");
        this.groveDepth = tag.getInt("groveDepth");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("groveCenter", NbtUtils.writeBlockPos(groveCenter));
        tag.putInt("groveRadius", groveRadius);
        tag.putInt("groveHeight", groveHeight);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutableBlockPos = new BlockPos.MutableBlockPos();
        var stateProvider = new NoiseProvider(worldGenLevel.getSeed(),
                worldGenLevel.registryAccess().lookup(Registries.NOISE).orElseThrow().getOrThrow(Noises.BADLANDS_SURFACE).value(),
                0.5F,
                List.of(Blocks.GRAVEL.defaultBlockState(), Blocks.MUD.defaultBlockState(), Blocks.CLAY.defaultBlockState()));

        ChunkAccess chunk = worldGenLevel.getChunk(chunkPos.x, chunkPos.z);

        int treeCounter = 6;

        int[] treeX = new int[treeCounter];
        int[] treeZ = new int[treeCounter];

        for (int i = 0; i < treeCounter; i++) {
            treeX[i] = random.nextInt(16);
            treeZ[i] = random.nextInt(16);
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);

                mutableBlockPos.set(blockX, 0, blockZ);

                double noise = getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;
                double depthNoise = getNoise(noiseSampler, blockZ, blockX, 100000, 0.025f) * 0.5f;

                double localRadius = (int) Mth.clampedLerp(groveRadius * 0.4, groveRadius, noise);
                double localHeight = (int) Mth.clampedLerp(groveHeight * 0.5, groveHeight, noise);
                double localDepth = (int) Mth.clampedLerp(groveDepth * 0.6, groveDepth, depthNoise);

                int blendWidth = 48;

                int rimSize = (int) (localRadius * 0.05);

                BlockState[] topBlocks = getCeilingBlocks(mutableBlockPos, noiseSampler, blockX, blockZ, groveCenter.getY(), localHeight, chunk);
                BlockState[] bottomBlocks = getSurfaceBlocks(mutableBlockPos, blockX, blockZ, groveCenter.getY(), chunk);

                var features = buildGroveLayer(worldGenLevel, chunk, stateProvider, mutableBlockPos, random,
                        noiseSampler, unsafeBoundingBox, topBlocks, bottomBlocks,
                        localRadius, localHeight, localDepth, blendWidth, rimSize, blockX, blockZ);

                if (treeCounter > 0) {
                    if (features.isPresent()) {
                        for (int i = 0; i < treeCounter; i++) {
                            int xPos = treeX[i];
                            int zPos = treeZ[i];
                            if (x == xPos && z == zPos) {
                                var pair = features.get();
                                bufferedFeatures.add(pair.mapSecond(ResourceKey::location));
                                treeCounter--;
                            }
                        }
                    }
                }
            }
        }
        if (unsafeBoundingBox.valid()) {
            this.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }

    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(WorldGenLevel worldGenLevel, ChunkAccess chunk, BlockStateProvider stateProvider, BlockPos.MutableBlockPos pos,
                                                                           RandomSource random, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockState[] ceilingCovering, BlockState[] surfaceCovering,
                                                                           double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {

        if (!pos.setY(groveCenter.getY()).closerThan(groveCenter, localRadius - blendWidth - rimSize)) {
            return Optional.empty();
        }
        Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> featurePlacement = null;

        int centerY = groveCenter.getY();
        double offset = localRadius - blendWidth - rimSize;
        double delta = (pos.setY(centerY).distSqr(groveCenter) - Mth.square(offset)) / Mth.square(localRadius - offset);

        int height = getUpperDepth(noiseSampler, pos, localHeight, delta);
        int depth = getLowerDepth(noiseSampler, pos, localDepth, delta);

        int ceilingCoverage = Math.min(ceilingCovering.length - 1, height < 2 ? 0 : Mth.floor((2+height)*2));
        int ceilingLimit = centerY + height;
        
        int surfaceCoverage = Math.min(surfaceCovering.length - 1, depth < 6 ? 0 : Mth.floor(depth/2f));
        int surfaceLimit = centerY - depth;

        int surfacePlacementDepth = surfaceLimit - surfaceCoverage;
        int ceilingPlacementHeight = ceilingLimit + ceilingCoverage;

        int ceilingShellOffset = (height > 8 ? Mth.floor((height - 8) * 0.2f) : 0);
        int ceilingShellLimit = ceilingPlacementHeight + 6 - ceilingShellOffset;
        int ceilingShellStart = centerY + ceilingShellOffset;

        int surfaceShellOffset = (depth > 6 ? Mth.floor((depth - 6) * 0.6f) : 0);
        int surfaceShellLimit = surfacePlacementDepth - 6 + surfaceShellOffset;
        int surfaceShellStart = centerY - surfaceShellOffset;

        int cutoffCounter = 0;
        for (int y = ceilingShellStart; y < ceilingShellLimit; y++) { // top shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);

            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.isAir()) {
                if (cutoffCounter++ >= 6) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, Blocks.GREEN_WOOL.defaultBlockState(), true);
        }

        cutoffCounter = 0;
        for (int y = surfaceShellStart; y > surfaceShellLimit; y--) { // bottom shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);
            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.isAir()) {
                if (cutoffCounter++ >= 4) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, Blocks.RED_WOOL.defaultBlockState(), true);
        }
        if (centerY < ceilingPlacementHeight) {
            for (int y = centerY; y < ceilingPlacementHeight; y++) { // ceiling
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                if (y >= ceilingLimit) {
                    int index = Mth.clamp(y-ceilingLimit, 0, ceilingCoverage);
                    chunk.setBlockState(pos, ceilingCovering[index], true);
                    continue;
                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        if (centerY > surfacePlacementDepth) {
            for (int y = centerY; y > surfacePlacementDepth; y--) { //surface
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceLimit + 1) {
                    int agh = groveCenter.getY() - y;
                    if (agh > groveDepth * 0.2f && agh < groveDepth * 0.55f) {
//                        featurePlacement = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_TREE);
                    }

                    if (agh > groveDepth * 0.55f) {
//                        featurePlacement = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_TREE);
                    }
                }

                if (y <= surfaceLimit) {
                    int index = Mth.clamp(surfaceLimit - y, 0, surfaceCoverage);
                    chunk.setBlockState(pos, surfaceCovering[index], true);
                    continue;
                }

                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        return Optional.ofNullable(featurePlacement);
    }

    private int getUpperDepth(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getDepth(noiseSampler, pos, Easing.QUAD_IN, 0.05f, 1f, localHeight, groveHeight, delta);
    }
    private int getLowerDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getDepth(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, 0.5f, localDepth, groveDepth, delta);
    }

    private int getDepth(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float frequency, double depthOffsetScalar, double value, double total, double delta) {
        double depthNoise = getNoise(noiseSampler, pos.getX(), pos.getZ(), 100000, frequency) * 0.5f;

        double depthOffset = easing.clamped(depthNoise, 0, (value*depthOffsetScalar));
        return (int) Easing.SINE_IN_OUT.clamped(-delta, 0, total/2f+depthOffset);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return noiseSampler.noise((blockX+offset) * noiseFrequency, 0, (blockZ+offset) * noiseFrequency) + 1;
    }

    private BlockState[] getCeilingBlocks(BlockPos.MutableBlockPos mutableBlockPos, ImprovedNoise noiseSampler, int blockX, int blockZ, int startY, double localHeight, ChunkAccess chunk) {
        float yPosition = startY+Mth.floor(localHeight);
        float bias = yPosition < 0 ? 1 : 1 - yPosition/8f;
        Block rock;
        if (bias != 1) {
            double noise = getNoise(noiseSampler, blockX, blockZ, 0.02f) / 2;
            rock = noise >= bias ? Blocks.DEEPSLATE : Blocks.STONE;
        }
        else {
            rock = Blocks.DEEPSLATE;
        }
        BlockState rockState = rock.defaultBlockState();
        BlockState tuffState = Blocks.TUFF.defaultBlockState();
        BlockState[] blockStates = {
                rockState,
                tuffState, tuffState, tuffState,
                rockState, rockState, rockState, rockState, rockState
        };
        return getFillerBlocks(mutableBlockPos, blockStates, blockX, blockZ, startY, true, chunk);
    }

    private BlockState[] getSurfaceBlocks(BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int startY, ChunkAccess chunk) {
        BlockState[] blockStates = {
                MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.STONE.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.STONE.defaultBlockState()
        };
        return getFillerBlocks(mutableBlockPos, blockStates, blockX, blockZ, startY, false, chunk);
    }
    private BlockState[] getFillerBlocks(BlockPos.MutableBlockPos mutableBlockPos, BlockState[] blockSet, int blockX, int blockZ, int startY, boolean isCeiling, ChunkAccess chunk) {
        for (int y = 0; y < blockSet.length; y++) {
            mutableBlockPos.set(blockX, startY + (isCeiling ? 1 : -1), blockZ);
            BlockState blockState = chunk.getBlockState(mutableBlockPos);

            if (blockState.isAir()) {
                continue;
            }
            if (blockState.is(BlockTags.LUSH_GROUND_REPLACEABLE)) {
                continue;
            }
            blockSet[y] = blockState;
        }
        return blockSet;
    }
}