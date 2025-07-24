package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.mojang.datafixers.util.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class GroveBiomeHelper {

    public static List<MeadowGroveBiomeType> pickBiomes(RandomSource randomSource) {
        List<MeadowGroveBiomeType> biomes = new ArrayList<>();
        List<MeadowGroveBiomeType> naturalBiomes = new ArrayList<>(MeadowGroveBiomeType.BIOME_TYPES.values().stream().filter(MeadowGroveBiomeType::spawnsNaturally).toList());
        int biomeCount = RandomHelper.randomBetween(randomSource, Easing.CUBIC_IN_OUT, 1, 3);

        for (int i = 0; i < biomeCount; i++) {
            float totalWeight = naturalBiomes.stream().map(MeadowGroveBiomeType::getWeight).reduce(0f, Float::sum);
            float random = randomSource.nextFloat() * totalWeight;
            MeadowGroveBiomeType addedBiome = null;
            for (MeadowGroveBiomeType value : naturalBiomes) {
                random -= value.getWeight();
                if (random <= 0) {
                    addedBiome = value;
                    break;
                }
            }
            naturalBiomes.remove(addedBiome);
            biomes.add(addedBiome);
        }
        return biomes;
    }

    public static Pair<MeadowGroveBiomeType, Double> getBiomeType(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, int blockX, int blockZ, double delta) {
        if (delta >= 0.4f) {
            double startingDelta = 0.5f;
            double threshold = 0.3f;
            float calcificationDelta = Easing.EXPO_OUT.clamped((delta - startingDelta) / (1 - startingDelta), 0, 1);
            if (calcificationDelta > threshold) {
                double gain = (calcificationDelta - threshold) * (delta - startingDelta) / (1 - startingDelta);
                return Pair.of(MeadowGroveBiomeTypes.DEEP_CALCIFICATION, gain);
            }
            else {
                double calcificationCracks = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 25000, 0.35f);
                double abs = Math.abs(0.5f - calcificationCracks);
                double crackStrength = (0.3f - Math.min(abs, 0.3f)) / 0.3f;
                startingDelta -= crackStrength * 0.1f;
                threshold -= crackStrength * 0.2f;
                calcificationDelta = Easing.EXPO_OUT.clamped((delta - startingDelta) / (1 - startingDelta), 0, 1);
                if (calcificationDelta > threshold && crackStrength > 0.5f) {
                    double gain = (calcificationDelta - threshold) * (crackStrength - 0.5f) * 2;
                    return Pair.of(MeadowGroveBiomeTypes.CREEPING_CALCIFICATION, gain);
                }
            }
        }

        MeadowGroveBiomeType dominantBiome = null;
        double highestNoise = 0;
        double secondHighest = 0;
        final List<MeadowGroveBiomeType> naturalBiomes = config.getNaturalBiomes();
        for (MeadowGroveBiomeType biomeType : naturalBiomes) {
            double noise = getBiomeNoise(config, noiseSampler, blockX, blockZ, biomeType);
            if (noise > highestNoise) {
                highestNoise = noise;
                dominantBiome = biomeType;
            }
        }
        if (dominantBiome == null) {
            throw new IllegalStateException("No dominant biome found for coordinates: " + blockX + ", " + blockZ);
        }

        for (MeadowGroveBiomeType biomeType : naturalBiomes) {
            if (biomeType == dominantBiome) {
                continue;
            }
            double noise = getBiomeNoise(config, noiseSampler, blockX, blockZ, biomeType);
            if (noise > secondHighest) {
                secondHighest = noise;
            }
        }

        double gain = highestNoise - secondHighest;
        return Pair.of(dominantBiome, gain);
    }

    public static double getBiomeNoise(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, int blockX, int blockZ, MeadowGroveBiomeType biomeType) {
        double biomeNoise = 0;
        int layerCount = 3;
        float[] layerWeights = new float[]{
                0.6f, 0.2f, 0.2f
        };
        float[] frequencyMultipliers = new float[]{
                1f, 1.25f, 1.5f
        };
        float totalWeight = 0;
        for (float weight : layerWeights) {
            totalWeight += weight;
        }
        int biomeOffset = biomeType.getSeed();
        for (int j = 0; j < layerCount; j++) {
            float frequency = config.getBiomeSize() * frequencyMultipliers[j];
            float weight = layerWeights[j];
            double noiseLayer = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, biomeOffset, frequency);
            biomeNoise += noiseLayer * weight;
        }
        return biomeNoise / totalWeight;
    }
}
