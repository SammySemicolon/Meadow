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
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class GroveInclineHelper {

    /**
     * Iterates through every currently present lonely incline data and propagates it outwards a little bit
     */
    public static void expandLonelyInclines(MeadowGroveGenerationConfiguration config) {
        var data = config.getGenerationData().getEntries();
        for (DataEntry dataEntry : data) {
            dataEntry.getInclineData().ifPresent(inclineData ->
                    GroveInclineHelper.expandLonelyInclines(config, inclineData, dataEntry.getBlockX(), dataEntry.getBlockZ()));
        }
    }

    /**
     *
     */
    public static void expandLonelyInclines(MeadowGroveGenerationConfiguration config, InclineData sourceIncline, int blockX, int blockZ) {
        if (!sourceIncline.isSource()) {
            return;
        }
        if (!sourceIncline.isLonely()) {
            return;
        }
        Supplier<InclineData> propagationSupplier = () -> InclineData.artificialSource(sourceIncline);
        BiConsumer<InclineData, Double> propagationActor = ((inclineData, delta) -> inclineData.applyPropagation(sourceIncline, delta));
        propagate(config, sourceIncline, propagationSupplier, propagationActor, blockX, blockZ);
    }

    /**
     * Iterates through every currently present incline data and propagates it outwards.
     * At this stage in generation, there are only source inclines, both natural and created through {@link GroveInclineHelper#expandLonelyInclines}.
     */
    public static void createOverhangs(MeadowGroveGenerationConfiguration config) {
        var data = config.getGenerationData().getEntries();
        for (DataEntry dataEntry : data) {
            dataEntry.getInclineData().ifPresent(inclineData ->
                    GroveInclineHelper.createOverhangs(config, inclineData, dataEntry.getBlockX(), dataEntry.getBlockZ()));
        }
    }


    /**
     * Propagates incline data outwards from a source incline within a limited radius.
     * Newly made incline data has reduced intensity.
     * When multiple propagations affect the same coordinate, the greater intensity value always takes priority
     */
    public static void createOverhangs(MeadowGroveGenerationConfiguration config, InclineData sourceIncline, int blockX, int blockZ) {
        if (!sourceIncline.isSource()) {
            return;
        }
        Supplier<InclineData> propagationSupplier = () -> InclineData.overhang(sourceIncline);
        BiConsumer<InclineData, Double> propagationActor = ((inclineData, delta) -> inclineData.applyPropagation(sourceIncline, delta));
        propagate(config, sourceIncline, propagationSupplier, propagationActor, blockX, blockZ);
    }

    public static void propagate(MeadowGroveGenerationConfiguration config, InclineData sourceIncline, Supplier<InclineData> propagationSupplier, BiConsumer<InclineData, Double> propagationActor, int blockX, int blockZ) {
        DataCoordinate start = new DataCoordinate(blockX, blockZ);

        MeadowGroveGenerationData generationData = config.getGenerationData();
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
                    Direction direction = Direction.from2DDataValue(j);
                    DataCoordinate newCoordinate = dataCoordinate.move(direction);
                    if (set.contains(newCoordinate) || set1.contains(newCoordinate)) {
                        continue;
                    }

                    if (generationData.hasData(newCoordinate)) {
                        DataEntry target = generationData.getData(newCoordinate);
                        if (!target.isOpen()) {
                            continue;
                        }
                        InclineData targetIncline = target.getInclineData().orElse(null);
                        if (targetIncline != null && targetIncline.isSource()) {
                            continue;
                        }
                        if (targetIncline == null) {
                            targetIncline = target.setInclineData(propagationSupplier.get());
                        }
                        int hash = Objects.hash(blockX, blockZ);
                        float rate = Mth.lerp((Mth.sin(hash % 6.28f) + 1) * 0.5f, 0.7f, 1.3f);
                        int offsetX = blockX - newCoordinate.x();
                        int offsetZ = blockZ - newCoordinate.z();
                        double angle = hash + Math.atan2(offsetZ, offsetX);

                        double sine = Mth.lerp((Math.sin(angle * rate) + 1) * 0.5f, 0.6f, 1.4f);
                        double offsetDelta = sine * delta;
                        propagationActor.accept(targetIncline, offsetDelta);
                    }
                    set1.add(newCoordinate);
                }
            }
        }
    }
    /**
     * Iterates through every currently present incline data and updates information about it's neighboring inclines
     */
    public static void countNeighbors(MeadowGroveGenerationConfiguration config) {
        MeadowGroveGenerationData data = config.getGenerationData();
        for (DataEntry entry : data.getEntries()) {
            var inclineOptional = entry.getInclineData();
            if (inclineOptional.isEmpty()) {
                continue;
            }
            var position = entry.getDataCoordinate();
            inclineOptional.get().checkNeighbors(config, position);
        }
    }

    public static Optional<InclineData> getInclineData(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, int blockX, int blockZ, double delta) {
        double startingDelta = 0.3f;
        if (delta >= startingDelta) {
            double offsetDelta = (delta - startingDelta) / (1 - startingDelta);
            double threshold = Mth.lerp(offsetDelta, 0.45f, 0.25f);
            double inclineNoise = getInclineNoise(config, noiseSampler, blockX, blockZ);
            double inclineDelta = Easing.QUAD_IN.clamped(inclineNoise, 0, 1);
            if (inclineDelta >= threshold) {
                double heightNoise = getInclineNoise(noiseSampler, blockX, blockZ, 0.05f, 25000);
                double minimumHeight = config.getMinimumInclineHeight() * Mth.lerp(offsetDelta, 0.8f, 1.2f);
                double maximumHeight = config.getMaximumInclineHeight() * Mth.lerp(offsetDelta, 0.4f, 1.8f);
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