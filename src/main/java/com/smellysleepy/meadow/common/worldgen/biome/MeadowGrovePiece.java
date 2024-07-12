package com.smellysleepy.meadow.common.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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

                int startY = groveCenter.getY();

                BlockState fillerBlock = chooseSediment(noiseSampler, blockX, blockZ, startY);

                BlockState[] topBlocks = getCeilingFillerBlocks(chunk, fillerBlock, mutableBlockPos, blockX, blockZ, startY);
                BlockState[] bottomBlocks = getSurfaceFillerBlocks(chunk, mutableBlockPos, blockX, blockZ, startY);

                var features = buildGroveLayer(
                        worldGenLevel, chunk, noiseSampler, random, unsafeBoundingBox,
                        mutableBlockPos, fillerBlock, topBlocks, bottomBlocks,
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

    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(WorldGenLevel worldGenLevel, ChunkAccess chunk, ImprovedNoise noiseSampler, RandomSource random, UnsafeBoundingBox unsafeBoundingBox,
                                                                                           BlockPos.MutableBlockPos pos, BlockState fillerBlock, BlockState[] ceilingCovering, BlockState[] surfaceCovering,
                                                                                           double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {

        double offset = localRadius - blendWidth - rimSize;
        if (!pos.setY(groveCenter.getY()).closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> featurePlacement = null;

        int centerY = groveCenter.getY();
        double delta = (pos.setY(centerY).distSqr(groveCenter) - Mth.square(offset)) / Mth.square(localRadius - offset);

        int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
        int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);

        int ceilingCoverage = Math.min(ceilingCovering.length - 1, height < 2 ? 0 : Mth.floor((2 + height) * 2));
        int ceilingLimit = centerY + height;

        int surfaceCoverage = Math.min(surfaceCovering.length - 1, depth < 6 ? 0 : Mth.floor(depth / 2f));
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
            chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
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
            chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
        }
        if (centerY < ceilingPlacementHeight) {
            for (int y = centerY; y < ceilingPlacementHeight; y++) { // ceiling
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                if (y >= ceilingLimit) {
                    int index = Mth.clamp(y - ceilingLimit, 0, ceilingCoverage);
                    chunk.setBlockState(pos, ceilingCovering[index], true);
                    continue;
                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        double distance = Math.sqrt(pos.set(blockX, groveCenter.getY(), blockZ).distSqr(groveCenter));
        if (centerY > surfacePlacementDepth) {
            for (int y = centerY; y > surfacePlacementDepth; y--) { //surface
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceLimit + 1) {
                    int featureTypeOffset = groveCenter.getY() - y;
                    float start = groveDepth * 0.3f;
                    float midpoint = groveDepth * 0.5f;
                    float end = groveDepth * 0.7f;
                    if (featureTypeOffset > start && featureTypeOffset < midpoint) {
//                        chunk.setBlockState(pos, Blocks.WHITE_STAINED_GLASS.defaultBlockState(), true);
//                        continue;
                    }
                    if (featureTypeOffset >= midpoint && featureTypeOffset < end) {
//                        chunk.setBlockState(pos, Blocks.GRAY_STAINED_GLASS.defaultBlockState(), true);
//                        continue;
                    }
                    if (featureTypeOffset >= end) {
//                        chunk.setBlockState(pos, Blocks.BLACK_STAINED_GLASS.defaultBlockState(), true);
//                        continue;
                    }
                    //                        featurePlacement = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_TREE);
                }

                if (y <= surfaceLimit) {
                    int index = Mth.clamp(surfaceLimit - y, 0, surfaceCoverage);
                    BlockState state = surfaceCovering[index];

                    state = getPeripheralState(noiseSampler, state, blockX, blockZ, distance, offset);
                    chunk.setBlockState(pos, state, true);
                    continue;
                }

                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        if (centerY >= surfacePlacementDepth) {
            createRamps(chunk, noiseSampler, pos, surfaceCovering, surfaceLimit, blockX, blockZ, localDepth, distance, offset);
        }
        return Optional.empty();
    }

    private void createRamps(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, BlockState[] surfaceCovering, int surfaceLimit, int blockX, int blockZ, double localDepth, double distance, double offset) {
        if (distance > offset * 0.55f) {
            boolean isAtEdge = distance > offset * 0.95f;
            double noise = getBlurredNoise(noiseSampler, blockX, blockZ, 50000, 0.01f) / 2;
            float noiseOffset = Easing.QUAD_OUT.clamped((distance - offset * 0.6f) / (offset * 0.4f), 0, 0.4f);
            float topThreshold = 0.7f - noiseOffset;
            float bottomThreshold = 0.6f - noiseOffset;
            int rampHeight = Mth.ceil((groveDepth * 1.2f - localDepth / 2f));

            if (noise >= topThreshold) {
                int topCutoff = isAtEdge ? 0 : Mth.floor(rampHeight * getRampCutIn(noise, distance, offset, 0.7f));

                boolean GAY = false;
//                if (rampOffset < 0.5f && distance < offset * 0.6f) {
                    if (isFloatingRamp(noiseSampler, distance, offset, blockX, blockZ)) {
                        topCutoff = rampHeight;
                        GAY = true;
                    }
//                }
                for (int i = 0; i < rampHeight; i++) {
                    if (!isAtEdge && i > topCutoff) {
                        break;
                    }
                    int y = surfaceLimit + rampHeight - i;
                    BlockState state = Blocks.DIRT.defaultBlockState();
                    if (isAtEdge && y > groveCenter.getY()+groveHeight*0.2f) {
                        state = Blocks.STONE.defaultBlockState();
                    }
                    pos.set(blockX, y, blockZ);
                    if (i > 0 || chunk.getBlockState(pos.above()).isAir()) {
                        state = surfaceCovering[Mth.clamp(i, 0, surfaceCovering.length - 1)];
                    }
                    state = getPeripheralState(noiseSampler, state, blockX, blockZ, distance, offset);
                    chunk.setBlockState(pos, GAY ? Blocks.BLUE_STAINED_GLASS.defaultBlockState() : state, true);
                }
            }
            if (isAtEdge || noise >= bottomThreshold) {
                int bottomCutoff = isAtEdge ? 0 : Mth.ceil(rampHeight * getRampCutIn(noise, distance, offset, 0.6f));
                for (int i = 0; i < rampHeight; i++) {
                    if (!isAtEdge && i > bottomCutoff) {
                        break;
                    }
                    pos.set(blockX, surfaceLimit + i, blockZ);
                    chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
                }
            }
        }
    }

    public double getRampCutIn(double noise, double distance, double offset, float start) {
        float noiseOffset = Easing.QUAD_OUT.clamped((distance - offset * 0.6f) / (offset * 0.4f), 0, 0.4f);
        float threshold = start - noiseOffset;
        double rampOffset = (noise - threshold) / (1 - threshold);
        return Easing.QUINTIC_OUT.ease(rampOffset, 0, 1);
    }

    public boolean isFloatingRamp(ImprovedNoise noiseSampler, double distance, double offset, int blockX, int blockZ) {
        int radius = 3;
        int counter = 0;
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                double noise = getNoise(noiseSampler, blockX+x, blockZ+z, 50000, 0.01f) / 2;
                double cutIn = getRampCutIn(noise, distance, offset, 0.6f);
                if (cutIn > 0.95f) {
                    counter++;
                }
            }
        }
        return counter < Math.pow(1+radius*2, 2) * 0.5f;
    }

    public BlockState getPeripheralState(ImprovedNoise noiseSampler, BlockState original, int blockX, int blockZ, double distance, double offset) {
        if (distance > offset * 0.8f) {
            double stateNoise = getNoise(noiseSampler, blockX, blockZ, 75000, 0.15f) / 2;
            float stateNoiseOffset = (float) ((distance - offset * 0.8f) / (offset * 0.2f)) * 0.25f;
            if (stateNoise > 0.5f - stateNoiseOffset && stateNoise < 0.6f + stateNoiseOffset) {
                return Blocks.STONE.defaultBlockState();
            }
        }
        return original;
    }

    private int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.QUAD_IN, 0.05f, 1f, localHeight, groveHeight, delta);
    }

    private int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, 0.5f, localDepth, groveDepth, delta);
    }

    private int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float frequency, double depthOffsetScalar, double value, double total, double delta) {
        double depthNoise = getNoise(noiseSampler, pos.getX(), pos.getZ(), 100000, frequency) * 0.5f;

        double depthOffset = easing.clamped(depthNoise, 0, (value * depthOffsetScalar));
        return (int) Easing.SINE_IN_OUT.clamped(-delta, 0, total / 2f + depthOffset);
    }

    public static BlockState chooseSediment(ImprovedNoise noiseSampler, int blockX, int blockZ, int startY) {
        float yScalar = (Mth.clamp(startY, -50, 50) + 50) / 100f;
        BlockState rock = Blocks.STONE.defaultBlockState();
        if (yScalar != 0 && yScalar != 1) {
            double noise = getNoise(noiseSampler, blockX, blockZ, 0.2f);
            if (noise > 1 - yScalar && noise < 1 + yScalar) {
                rock = Blocks.DEEPSLATE.defaultBlockState();
            }
        } else if (yScalar == 0) {
            rock = Blocks.DEEPSLATE.defaultBlockState();
        }
        return rock;
    }

    private BlockState[] getCeilingFillerBlocks(ChunkAccess chunk, BlockState filler, BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int startY) {
        BlockState tuff = Blocks.TUFF.defaultBlockState();
        BlockState[] blockStates = {
                filler, filler,
                tuff, tuff,
                filler, filler, filler, filler
        };
        return getFillerBlocks(mutableBlockPos, blockStates, blockX, blockZ, startY, true, chunk);
    }

    private BlockState[] getSurfaceFillerBlocks(ChunkAccess chunk, BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int startY) {
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
            mutableBlockPos.set(blockX, startY + y * (isCeiling ? 1 : -1), blockZ);
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

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency, boolean blur) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency, blur);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency, boolean blur) {
        if (blur) {
            return getBlurredNoise(noiseSampler, blockX, blockZ, offset, noiseFrequency);
        }
        return noiseSampler.noise((blockX + offset) * noiseFrequency, 0, (blockZ + offset) * noiseFrequency) + 1;
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getBlurredNoise(noiseSampler, blockX, blockZ, 0, 1, noiseFrequency);
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return getBlurredNoise(noiseSampler, blockX, blockZ, offset, 1, noiseFrequency);
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, int blurRadius, float noiseFrequency) {
        double noise = 0;
        int count = 0;
        for (int x = -blurRadius; x <= blurRadius; x++) {
            for (int z = -blurRadius; z <= blurRadius; z++) {
                noise += getNoise(noiseSampler, blockX, blockZ, offset, noiseFrequency);
                count++;
            }
        }
        return noise / count;
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return noiseSampler.noise((blockX + offset) * noiseFrequency, 0, (blockZ + offset) * noiseFrequency) + 1;
    }
}