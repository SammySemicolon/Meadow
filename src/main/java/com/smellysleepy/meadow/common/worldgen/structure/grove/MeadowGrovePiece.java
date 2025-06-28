package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.*;
import net.minecraft.world.level.levelgen.synth.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.easing.*;

public class MeadowGrovePiece extends StructurePiece {
    protected MeadowGroveGenerationData groveData;

    protected MeadowGrovePiece(MeadowGroveGenerationData groveData, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveData = groveData;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveData = MeadowGroveGenerationData.load(tag);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext pContext, @NotNull CompoundTag pTag) {
        groveData.save(pTag);
    }

    @Override
    public void postProcess(WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(level.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutable = new BlockPos.MutableBlockPos();

        BlockPos groveCenter = groveData.getGroveCenter();
        int groveRadius = groveData.getGroveRadius();
        int groveHeight = groveData.getGroveHeight();
        int groveDepth = groveData.getGroveDepth();
        for (int i = 0; i < 256; i++) {
            int blockX = chunkPos.getBlockX(i % 16);
            int blockY = groveCenter.getY();
            int blockZ = chunkPos.getBlockZ(i / 16);
            mutable.set(blockX, blockY, blockZ);
            double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.1f) * 0.5f;

            int localRadius = (int) Mth.clampedLerp(groveRadius * 0.4f, groveRadius * 1.4f, noise);
            int localHeight = (int) Mth.clampedLerp(groveHeight * 0.5f, groveHeight, noise);
            int localDepth = (int) Mth.clampedLerp(groveDepth * 0.6f, groveDepth, noise);

            if (!mutable.closerThan(groveCenter, localRadius)) {
                continue;
            }
            double distance = Math.sqrt(mutable.distSqr(groveCenter));
            double delta = distance / (localRadius);
            int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
            int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);

            MeadowGroveBiomeType biomeType = getBiomeType(noiseSampler, blockX, blockZ);
            MeadowGroveBiomeType calcificationBiome = tryAddCalcification(noiseSampler, blockX, blockZ, delta);
            if (calcificationBiome != null) {
                biomeType = calcificationBiome;
            }



            if (height <= 0 && depth <= 0) {
                continue;
            }
            Block block;

            if (biomeType == MeadowGroveBiomeType.MEADOW_FOREST) {
                block = Blocks.YELLOW_WOOL;
            } else if (biomeType == MeadowGroveBiomeType.ROCKY_HILLS) {
                block = Blocks.COBBLESTONE;
            } else if (biomeType == MeadowGroveBiomeType.FUNGUS_SHELVES) {
                block = Blocks.MUSHROOM_STEM;
            } else if (biomeType == MeadowGroveBiomeType.CALCIFIED_OUTSKIRTS) {
                block = Blocks.BLUE_WOOL;
            } else if (biomeType == MeadowGroveBiomeType.CAVERNOUS_CALCIFICATION) {
                block = Blocks.PURPLE_WOOL;
            } else {
                throw new IllegalArgumentException("Unknown biome type: " + biomeType);
            }
            level.setBlock(mutable, block.defaultBlockState(), 2);
//            for (int j = 0; j < height; j++) {
//                mutable.move(Direction.UP);
//                level.setBlock(mutable, Blocks.RED_STAINED_GLASS.defaultBlockState(), 2);
//            }
//            mutable.setY(blockY);
//            for (int j = 0; j < depth; j++) {
//                level.setBlock(mutable, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 2);
//                mutable.move(Direction.DOWN);
//            }
        }
    }

    private static MeadowGroveBiomeType tryAddCalcification(ImprovedNoise noiseSampler, int blockX, int blockZ, double delta) {
        if (delta >= 0.4f) {
            double startingDelta = 0.75f;
            double calcificationCracks = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 25000, 0.35f) * 0.5f;
            double abs = Math.abs(0.5f - calcificationCracks);
            double crackStrength = (0.3f - Math.min(abs, 0.3f)) / 0.3f;
            double threshold = 0.4f;
            float calcificationDelta = Easing.CIRC_OUT.clamped((delta - startingDelta) / (1 - startingDelta), 0, 1);
            if (calcificationDelta > threshold) {
                return MeadowGroveBiomeType.CAVERNOUS_CALCIFICATION;
            }
            else {
                startingDelta -= crackStrength * 0.1f;
                threshold -= crackStrength * 0.2f;
                calcificationDelta = Easing.CIRC_OUT.clamped((delta - startingDelta) / (1 - startingDelta), 0, 1);
                if (calcificationDelta > threshold && crackStrength > 0.5f) {
                    return MeadowGroveBiomeType.CALCIFIED_OUTSKIRTS;
                }
            }
        }
        return null;
    }
    private static MeadowGroveBiomeType getBiomeType(ImprovedNoise noiseSampler, int blockX, int blockZ) {
        MeadowGroveBiomeType dominantBiome = null;
        double highestNoise = Double.NEGATIVE_INFINITY;

        for (MeadowGroveBiomeType biomeType : MeadowGroveBiomeType.BIOME_TYPES.values()) {
            if (!biomeType.spawnsNaturally()) {
                continue;
            }
            double noise = getBiomeNoise(noiseSampler, blockX, blockZ, biomeType);
            if (noise > highestNoise) {
                highestNoise = noise;
                dominantBiome = biomeType;
            }
        }
        if (dominantBiome == null) {
            throw new IllegalStateException("No dominant biome found for coordinates: " + blockX + ", " + blockZ);
        }
        return dominantBiome;
    }

    private static double getBiomeNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, MeadowGroveBiomeType biomeType) {
        double biomeNoise = 0;
        int layerCount = 3;
        float[] layerWeights = new float[]{
            0.5f, 0.3f, 0.2f
        };
        float[] layerFrequencies = new float[]{
            0.02f, 0.05f, 0.12f
        };
        int biomeOffset = biomeType.getSeed();
        for (int j = 0; j < layerCount; j++) {
            int offset = biomeOffset * (j+1);
            float frequency = layerFrequencies[j];
            float weight = layerWeights[j];
            double noiseLayer = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, offset, frequency);
            biomeNoise += noiseLayer * weight;
        }
        return biomeNoise * 0.5f;
    }

    private int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.QUAD_IN, 0.05f, localHeight, delta);
    }

    private int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, localDepth, delta);
    }

    private int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float noiseFrequency, double localValue, double delta) {
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos, 100000, noiseFrequency); // Random Noise based on position
        double deltaScalar = easing.clamped(depthNoise / 2f, 0.7f, 1.4f); //Slight delta scaling based on noise
        double carverDelta = 1 - delta * deltaScalar; //Carver delta based on distance from center and delta scaling, approaches 1 towards the end of the grove
        float expoOut = Easing.EXPO_OUT.clamped(carverDelta, 0, 1); // Main easing, falls off quickly towards the end of the grove
        float bounceInOut = Easing.BOUNCE_IN_OUT.clamped(carverDelta * 1.5f, 0, 1); // Bounce effect to add some organic feel to the height
        float sum = expoOut * 0.6f + bounceInOut * 0.4f; //Combining different easing functions for a more organic feel
        if (sum > 1) {
            sum = 1 - (sum - 1) * 0.5f; // Clamping the sum to avoid excessive height
        }
        return (int) (sum * localValue);
    }
}
