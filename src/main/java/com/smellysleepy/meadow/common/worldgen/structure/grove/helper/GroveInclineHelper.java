package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class GroveInclineHelper {

    public static int steps;

    /**
     * Iterates through every currently present incline data and propagates it outwards.
     */
    public static void propagateInclineData(MeadowGroveGenerationConfiguration config) {
        MeadowGroveGenerationData data = config.getGenerationData();
        List<DataCoordinate> existingInclineData = data.getEntries().stream()
                .filter(e -> e.getInclineData().isPresent()).map(DataEntry::getDataCoordinate).toList();
        for (DataCoordinate coordinate : existingInclineData) {
            GroveInclineHelper.propagateInclineData(config, coordinate.x(), coordinate.z());
        }
    }


    /**
     * Propagates incline data outwards within a limited radius.
     * Newly made incline data has reduced intensity.
     * When multiple propagations affect the same coordinate,
     */
    public static void propagateInclineData(MeadowGroveGenerationConfiguration config, int blockX, int blockZ) {
        DataCoordinate start = new DataCoordinate(blockX, blockZ);

        MeadowGroveGenerationData generationData = config.getGenerationData();
        DataEntry source = generationData.getData(start);
        InclineData sourceIncline = source.getInclineData().orElseThrow();
        int range = sourceIncline.getPropagationRange();
        List<Set<DataCoordinate>> list = Lists.newArrayList();
        for (int j = 0; j < range; ++j) {
            list.add(Sets.newHashSet());
        }

        list.get(0).add(start);

        for (int i = 1; i < range; ++i) {
            Set<DataCoordinate> set = list.get(i - 1);
            Set<DataCoordinate> set1 = list.get(i);
            double delta = 1 - i / (float)range;
            for (DataCoordinate dataCoordinate : set) {
                for (int j = 0; j < 4; j++) {
                    steps++;
                    Direction direction = Direction.from2DDataValue(j);
                    DataCoordinate newCoordinate = dataCoordinate.move(direction);
                    if (set.contains(newCoordinate) || set1.contains(newCoordinate)) {
                        continue;
                    }

                    if (generationData.hasData(newCoordinate)) {
                        DataEntry target = generationData.getData(newCoordinate);
                        InclineData targetIncline = target.getInclineData().orElse(null);
                        if (targetIncline != null && targetIncline.isSource()) {
                            continue;
                        }
                        if (targetIncline == null) {
                            targetIncline = target.setInclineData(InclineData.propagation(sourceIncline));
                        }
                        targetIncline.applyPropagation(sourceIncline, delta);
                    }
                    set1.add(newCoordinate);
                }
            }
        }
    }

    public static Optional<InclineData> getInclineData(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, int blockX, int blockZ, double delta) {
        double startingDelta = 0.3f;
        if (delta >= startingDelta) {
            double offsetDelta = (delta - startingDelta) / (1 - startingDelta);
            double threshold = Mth.lerp(offsetDelta, 0.4f, 0.2f);
            double inclineNoise = getInclineNoise(config, noiseSampler, blockX, blockZ);
            double inclineDelta = Easing.QUAD_IN.clamped(inclineNoise, 0, 1);
            if (inclineDelta >= threshold) {
                double heightNoise = getInclineNoise(noiseSampler, blockX, blockZ, 0.05f, 25000);
                int averageHeight = config.getAverageInclineHeight();
                double minimumHeight = averageHeight * Mth.lerp(offsetDelta, 0.3f, 0.8f);
                double maximumHeight = averageHeight * Mth.lerp(offsetDelta, 0.8f, 1.6f);
                int inclineHeight = Mth.ceil(Mth.lerp(heightNoise, minimumHeight, maximumHeight));
                double inclineIntensity = (inclineDelta - threshold) / (1 - threshold) * 10f;
                InclineData inclineData = InclineData.source(inclineHeight, Math.min(inclineIntensity, 1));
                return Optional.of(inclineData);
            }
        }
        return Optional.empty();
    }

    public static double getInclineNoise(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, int blockX, int blockZ) {
        return getInclineNoise(noiseSampler, blockX, blockZ, config.getInclineSize(), 50000);
    }

    public static double getInclineNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float baseFrequency, int baseOffset) {
        double noise = 0;
        int layerCount = 3;
        float[] layerWeights = new float[]{
                0.5f, 0.3f, 0.2f
        };
        float[] frequencyMultipliers = new float[]{
                0.3f, 1.5f, 3f
        };
        float totalWeight = 0;
        for (float weight : layerWeights) {
            totalWeight += weight;
        }
        for (int j = 0; j < layerCount; j++) {
            int offset = baseOffset * (j + 1);
            float frequency = baseFrequency * frequencyMultipliers[j];
            float weight = layerWeights[j];
            double noiseLayer = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, offset, frequency);
            noise += noiseLayer * weight;
        }
        return noise / totalWeight;
    }
}