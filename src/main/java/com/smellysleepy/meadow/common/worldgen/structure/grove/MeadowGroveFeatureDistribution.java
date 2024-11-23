package com.smellysleepy.meadow.common.worldgen.structure.grove;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class MeadowGroveFeatureDistribution extends HashMap<ResourceKey<ConfiguredFeature<?, ?>>, MeadowGroveFeatureDistribution.FeatureDistributionData> {

    public void addFeature(ResourceKey<ConfiguredFeature<?, ?>> feature, RandomSource randomSource, int amount) {
        ArrayList<Integer> indexes = new ArrayList<>();

        float step = 256f / amount;
        for (int i = 0; i < amount; i++) {
            int start = Mth.floor(step * i);
            int stop = Mth.floor(step * (i+1));
            indexes.add(randomSource.nextInt(start, stop));
        }
        put(feature, new FeatureDistributionData(indexes));
    }

    public record FeatureDistributionData(ArrayList<Integer> indexes) {
        public boolean canPlace(int blockIndex) {
            return canPlace(blockIndex, 0);
        }

        public boolean canPlace(int blockIndex, int leniency) {
            if (indexes.isEmpty()) {
                return false;
            }
            Integer featureIndex = indexes.get(0);
            boolean canPlace = featureIndex >= ((blockIndex-leniency)%256) && featureIndex <= ((blockIndex+leniency)%256);
            if (canPlace) {
                indexes.remove(0);
            }
            return canPlace;
        }
    }

    public static class FeatureDataGetter implements Predicate<ResourceKey<ConfiguredFeature<?, ?>>> {

        private final MeadowGroveFeatureDistribution distribution;
        private int currentBlockIndex;

        public FeatureDataGetter(MeadowGroveFeatureDistribution distribution) {
            this.distribution = distribution;
        }

        public void nextIndex() {
            currentBlockIndex++;
        }

        @SafeVarargs
        public final ResourceKey<ConfiguredFeature<?, ?>> choose(ResourceKey<ConfiguredFeature<?, ?>>... keys) {
            return choose(0, keys);
        }
        @SafeVarargs
        public final ResourceKey<ConfiguredFeature<?, ?>> choose(int leniency, ResourceKey<ConfiguredFeature<?, ?>>... keys) {
            for (ResourceKey<ConfiguredFeature<?, ?>> key : keys) {
                if (test(key, leniency)) {
                    return key;
                }
            }
            return null;
        }

        @Override
        public boolean test(ResourceKey<ConfiguredFeature<?, ?>> key) {
            return distribution.containsKey(key) && distribution.get(key).canPlace(currentBlockIndex);
        }
        public boolean test(ResourceKey<ConfiguredFeature<?, ?>> key, int leniency) {
            return distribution.containsKey(key) && distribution.get(key).canPlace(currentBlockIndex, leniency);
        }
    }
}
