package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
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
        if (sourceIncline.isSource() && sourceIncline.needsExpansion()) {
            if (sourceIncline.isCompletelyAlone()) {
                sourceIncline.setIntensity(1);
            }
            int range = 3;
            InclinePropagationHandler handler = create()
                    .setInclineBuilder(() -> InclineData.artificialSource(sourceIncline));
            propagate(config, range, handler, blockX, blockZ);
        }
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
        if (sourceIncline.isSource() || sourceIncline.isArtificialSource()) {
            int range = sourceIncline.getPropagationRange();
            InclinePropagationHandler handler = create()
                    .setInclineBuilder(() -> InclineData.overhang(sourceIncline))
                    .setPropagationCondition(InclineData::isOverhang)
                    .setInclinePropagator(((inclineData, delta) -> inclineData.applyPropagation(sourceIncline, delta)));
            propagate(config, range, handler, blockX, blockZ);
        }
    }

    public static void propagate(MeadowGroveGenerationConfiguration config, int range, InclinePropagationHandler handler, int blockX, int blockZ) {
        GroveDataPropagationHelper.propagate(config, handler, range, blockX, blockZ);
    }

    /**
     * Iterates through every currently present incline data and updates information about it's neighboring inclines
     */
    public static void countNeighbors(MeadowGroveGenerationConfiguration config) {
        MeadowGroveGenerationData data = config.getGenerationData();
        for (DataEntry entry : data.getEntries()) {
            entry.getInclineData().ifPresent(inclineData -> {
                var position = entry.getDataCoordinate();
                inclineData.checkNeighbors(config, position);
            });
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
                0.5f, 2.5f, 5f
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

    public static InclinePropagationHandler create() {
        return new InclinePropagationHandler();
    }

    public static class InclinePropagationHandler implements GroveDataPropagationHelper.PropagationHandler {

        public Supplier<InclineData> inclineBuilder;
        public Predicate<InclineData> propagationCondition;
        public BiConsumer<InclineData, Double> inclinePropagator;

        public InclinePropagationHandler setInclineBuilder(Supplier<InclineData> inclineBuilder) {
            this.inclineBuilder = inclineBuilder;
            return this;
        }
        public InclinePropagationHandler setPropagationCondition(Predicate<InclineData> propagationCondition) {
            this.propagationCondition = propagationCondition;
            return this;
        }

        public InclinePropagationHandler setInclinePropagator(BiConsumer<InclineData, Double> inclinePropagator) {
            this.inclinePropagator = inclinePropagator;
            return this;
        }

        @Override
        public boolean propagate(DataEntry target, double delta) {
            InclineData targetIncline = target.getInclineData().orElse(null);
            if (targetIncline != null && targetIncline.isSource()) {
                return false;
            }
            if (targetIncline == null) {
                if (inclineBuilder != null) {
                    targetIncline = target.setInclineData(inclineBuilder.get());
                }
                else {
                    return false;
                }
            }
            if (inclinePropagator != null) {
                if (propagationCondition == null || propagationCondition.test(targetIncline)) {
                    inclinePropagator.accept(targetIncline, delta);
                }
            }
            return true;
        }
    }
}