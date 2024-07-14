package com.smellysleepy.meadow.common.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
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
import net.minecraftforge.common.Tags;
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

                buildGroveLayer(
                        chunk, noiseSampler, unsafeBoundingBox, mutableBlockPos,
                        localRadius, localHeight, localDepth, blendWidth, rimSize, blockX, blockZ);
            }
        }
        if (unsafeBoundingBox.valid()) {
            this.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }

    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(ChunkAccess chunk, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockPos.MutableBlockPos pos,
                                                                                           double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {
        double offset = localRadius - blendWidth - rimSize;
        if (!pos.setY(groveCenter.getY()).closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        int centerY = groveCenter.getY();
        double distance = pos.setY(centerY).distSqr(groveCenter);
        double sqrtDistance = Math.sqrt(distance);
        double delta = (distance - Mth.square(offset)) / Mth.square(localRadius - offset);

        int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
        int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);

        int ceilingLimit = centerY + height;
        BlockState ceilingPatternBlock = chooseSediment(noiseSampler, blockX, ceilingLimit, blockZ);
        List<BlockState> ceilingPattern = getCeilingPattern(chunk, ceilingPatternBlock, pos, ceilingLimit);
        int ceilingCoverage = Math.min(ceilingPattern.size() - 1, height < 2 ? 0 : Mth.floor((2 + height) * 2));

        int surfaceLimit = centerY - depth;
        List<BlockState> surfaceCovering = getSurfacePattern(chunk, pos, surfaceLimit);
        int surfaceCoverage = Math.min(surfaceCovering.size() - 1, depth < 6 ? 0 : Mth.floor(depth / 2f));

        int surfacePlacementDepth = surfaceLimit - surfaceCoverage;
        int ceilingPlacementHeight = ceilingLimit + ceilingCoverage;

        int ceilingShellOffset = (height > 8 ? Mth.floor((height - 8) * 0.2f) : 0);
        int ceilingShellLimit = ceilingPlacementHeight + 6 - ceilingShellOffset;
        int ceilingShellStart = centerY + ceilingShellOffset;

        int surfaceShellOffset = (depth > 6 ? Mth.floor((depth - 6) * 0.6f) : 0);
        int surfaceShellLimit = surfacePlacementDepth - 6 + surfaceShellOffset;
        int surfaceShellStart = centerY - surfaceShellOffset;

        double rampDelta = -(distance / Mth.square(localRadius - offset));
        double rampNoise = getNoise(noiseSampler, blockX, blockZ, 75000, 0.01f) / 2;
        double smoothedRampNoise = getSmoothedRampNoise(noiseSampler, rampNoise, blockX, blockZ);
        int rampDepth = getGroveDepth(noiseSampler, pos, localDepth, rampDelta);
        int rampYLevel = centerY - rampDepth;
        int rampHeight = Mth.ceil(rampDepth * Easing.QUINTIC_IN_OUT.clamped(rampNoise, 0.8f, 1.2f));
        List<BlockState> rampBlockPattern = getSurfacePattern(chunk, pos, surfaceLimit + rampYLevel);

        createShell(chunk, pos, unsafeBoundingBox, ceilingPatternBlock, blockX, blockZ, ceilingShellStart, ceilingShellLimit, surfaceShellStart, surfaceShellLimit);

        if (centerY < ceilingPlacementHeight) {
            for (int y = centerY; y < ceilingPlacementHeight; y++) { // ceiling
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                if (y >= ceilingLimit) {
                    int index = Mth.clamp(y - ceilingLimit, 0, ceilingCoverage);
                    chunk.setBlockState(pos, ceilingPattern.get(index), true);
                    continue;
                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        if (centerY > surfacePlacementDepth) {
            BlockState peripheralState = getPeripheralState(noiseSampler, blockX, blockZ, sqrtDistance, offset);
            for (int y = centerY; y > surfacePlacementDepth; y--) { //surface
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceLimit + 1) {
                    var feature = createSurfaceFeatures(y, pos);
                    if (feature != null) {
//                        bufferedFeatures.add(feature.mapSecond(ResourceKey::location));
                    }
                }

                if (y <= surfaceLimit) {
                    int index = Mth.clamp(surfaceLimit - y, 0, surfaceCoverage);
                    BlockState state = surfaceCovering.get(index);
                    chunk.setBlockState(pos, peripheralState != null ? peripheralState : state, true);
                    continue;
                }

                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        createRamps(chunk, noiseSampler, pos, rampBlockPattern, rampYLevel, blockX, blockZ, rampHeight, smoothedRampNoise, sqrtDistance, offset);
        return Optional.empty();
    }

    private void createShell(ChunkAccess chunk, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, BlockState ceilingPatternBlock, int blockX, int blockZ, int ceilingShellStart, int ceilingShellLimit, int surfaceShellStart, int surfaceShellLimit) {
        int cutoffCounter = 0;
        for (int y = ceilingShellStart; y < ceilingShellLimit; y++) { // top shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);

            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.canBeReplaced()) {
                if (cutoffCounter++ >= 6) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, ceilingPatternBlock, true);
        }

        cutoffCounter = 0;
        for (int y = surfaceShellStart; y > surfaceShellLimit; y--) { // bottom shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);
            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.canBeReplaced()) {
                if (cutoffCounter++ >= 4) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
        }
    }
    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createSurfaceFeatures(int y, BlockPos.MutableBlockPos pos) {
        int featureTypeOffset = groveCenter.getY() - y;
        float start = groveDepth * 0.3f;
        float midpoint = groveDepth * 0.5f;
        float end = groveDepth * 0.7f;
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;

        if (featureTypeOffset > start && featureTypeOffset < midpoint) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_TREE;
        }
        if (featureTypeOffset >= midpoint && featureTypeOffset < end) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_TREE;
        }
        if (featureTypeOffset >= end) {

        }
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    private void createRamps(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, List<BlockState> rampBlockPattern, int yLevel, int blockX, int blockZ, int rampHeight, double noise, double distance, double offset) {
        boolean isAtEdge = distance > offset * 0.95f;
        float noiseOffset = Easing.QUAD_OUT.clamped((distance - offset * 0.6f) / (offset * 0.4f), 0, 0.3f);
        if (distance < offset * 0.6f) {
            noise *= (distance - offset * 0.6f) / offset * 0.6f;
        }
        float topThreshold = 0.6f - noiseOffset;
        float bottomThreshold = 0.625f - noiseOffset;

        if (noise >= topThreshold) {
            int topCutoff = Mth.floor(rampHeight * Easing.CIRC_OUT.clamped(getRampCutIn(noise, topThreshold), 0, 1));
            int rampYLevel = yLevel + rampHeight;

            BlockState peripheralState = getPeripheralState(noiseSampler, blockX, blockZ, distance, offset);
            for (int i = 0; i < rampHeight; i++) {
                if (i > topCutoff) {
                    break;
                }
                int y = rampYLevel - i;
                pos.set(blockX, y, blockZ);
                if (chunk.getBlockState(pos).canBeReplaced()) {
                    BlockState state = Blocks.DIRT.defaultBlockState();
                    if (isAtEdge) {
                        state = Blocks.STONE.defaultBlockState();
                    } else if (i > 0 || chunk.getBlockState(pos.above()).isAir()) {
                        if (i < rampBlockPattern.size()) {
                            state = rampBlockPattern.get(i);
                        } else {
                            state = Blocks.STONE.defaultBlockState();
                        }
                    }
                    chunk.setBlockState(pos, peripheralState != null ? peripheralState : state, true);
                }
            }
        }

        if (noise >= bottomThreshold) {
            int bottomCutoff = Mth.floor(rampHeight * Easing.QUAD_IN_OUT.clamped(getRampCutIn(noise, bottomThreshold), 0, 1));
            for (int i = 0; i < rampHeight; i++) {
                if (i > bottomCutoff) {
                    break;
                }
                pos.set(blockX, yLevel + i, blockZ);
                chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
            }
        }
    }

    public double getSmoothedRampNoise(ImprovedNoise noiseSampler, double centralNoise, int blockX, int blockZ) {
        int radius = 4;
        int coverage = 1 + radius * 2;
        double[][] noiseValues = new double[coverage][coverage];
        int x = 4;
        int z = 4;
        double highestNoise = centralNoise;

        int emergencyCounter = 0;
        while (true) {
            if (emergencyCounter > coverage) {
                return highestNoise;
            }
            boolean foundHigher = false;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int offsetX = Mth.clamp(x - 1 + i * 2, 0, coverage-1);
                    int offsetZ = Mth.clamp(z - j + i * 2, 0, coverage-1);

                    double noise;
                    if (noiseValues[offsetX][offsetZ] == 0) {
                        noise = noiseValues[offsetX][offsetZ] = getRampNoise(noiseSampler, blockX+offsetX, blockZ+offsetZ) / 2;
                    }
                    else {
                        noise = noiseValues[offsetX][offsetZ];
                    }
                    if (noise > highestNoise) {
                        foundHigher = true;
                        highestNoise = noise;
                        x = offsetX;
                        z = offsetZ;
                    }
                }
            }
            emergencyCounter++;
            if (!foundHigher) {
                return highestNoise;
            }
        }
    }

    public double getRampNoise(ImprovedNoise noiseSampler, int blockX, int blockZ) {
        return getNoise(noiseSampler, blockX, blockZ, 50000, 0.02f);
    }

    public double getRampCutIn(double noise, float threshold) {
        return (noise - threshold) / (1 - threshold);
    }

    public BlockState getPeripheralState(ImprovedNoise noiseSampler, int blockX, int blockZ, double distance, double offset) {
        if (distance > offset * 0.8f) {
            double stateNoise = getNoise(noiseSampler, blockX, blockZ, 75000, 0.15f) / 2;
            float stateNoiseOffset = (float) ((distance - offset * 0.8f) / (offset * 0.2f)) * 0.25f;
            if (stateNoise > 0.5f - stateNoiseOffset && stateNoise < 0.6f + stateNoiseOffset) {
                return Blocks.STONE.defaultBlockState();
            }
        }
        return null;
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

    public static BlockState chooseSediment(ImprovedNoise noiseSampler, int blockX, int startingY, int blockZ) {
        float yScalar = (Mth.clamp(startingY, -50, 50) + 50) / 100f;
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

    private List<BlockState> getCeilingPattern(ChunkAccess chunk, BlockState patternState, BlockPos.MutableBlockPos pos, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i > 1 && i <= 3) {
                pattern.add(Blocks.TUFF.defaultBlockState());
                continue;
            }
            if (i > 5) {
                BlockState existingBlock = chunk.getBlockState(pos.setY(startingY + i));
                if (existingBlock.is(Tags.Blocks.ORES)) {
                    pattern.add(existingBlock);
                    continue;
                }
            }
            pattern.add(patternState);
        }
        return pattern;
    }

    private List<BlockState> getSurfacePattern(ChunkAccess chunk, BlockPos.MutableBlockPos pos, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        pattern.add(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState());
        pattern.add(Blocks.DIRT.defaultBlockState());
        pattern.add(Blocks.DIRT.defaultBlockState());
        for (int i = 3; i < 8; i++) {
            if (i > 3 && i <= 6) {
                pattern.add(Blocks.TUFF.defaultBlockState());
                continue;
            }
            BlockState existingBlock = chunk.getBlockState(pos.setY(startingY-i));
            if (existingBlock.is(Tags.Blocks.ORES)) {
                pattern.add(existingBlock);
                continue;
            }
            pattern.add(Blocks.STONE.defaultBlockState());
        }
        return pattern;
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