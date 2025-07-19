package com.smellysleepy.meadow.common.worldgen.structure.grove.feature;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record GroveFeatureProvider(List<GroveFeatureDistributionEntry> features) {

    public GroveFeatureProvider(List<GroveFeatureDistributionEntry> features) {
        this.features = List.copyOf(features);
    }

    public PlacedGroveFeatures generateFeaturePlacement(RandomSource random, float biomeInfluence) {
        HashMap<Integer, ResourceKey<ConfiguredFeature<?, ?>>> features = new HashMap<>();
        var featurePool = getFeaturePool();
        int totalFeatures = Mth.floor(featurePool.size() * biomeInfluence);
        Collections.shuffle(featurePool); //TODO: Non-Worldgen-Friendly
        float step = 256f / totalFeatures;
        for (int i = 0; i < totalFeatures; i++) {
            int start = Mth.floor(step * i);
            int stop = Mth.floor(step * (i + 1));
            int position = random.nextInt(start, stop);
            features.put(position, featurePool.remove(0));
        }
        return new PlacedGroveFeatures(features);
    }

    private ArrayList<ResourceKey<ConfiguredFeature<?, ?>>> getFeaturePool() {
        return features.stream()
                .flatMap(f -> Stream.of(f.asList()))
                .flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    public static GroveFeatureProviderBuilder create() {
        return new GroveFeatureProviderBuilder();
    }

    public static final class GroveFeatureProviderBuilder {

        public final List<GroveFeatureDistributionEntry> features = new ArrayList<>();

        public void addFeature(ResourceKey<ConfiguredFeature<?, ?>> feature, int featureCount) {
            features.add(new GroveFeatureDistributionEntry(feature, featureCount));
        }

        public GroveFeatureProvider build() {
            return new GroveFeatureProvider(features);
        }
    }

    public record GroveFeatureDistributionEntry(ResourceKey<ConfiguredFeature<?, ?>> feature, int featureCount) {
        private List<ResourceKey<ConfiguredFeature<?, ?>>> asList() {
            ArrayList<ResourceKey<ConfiguredFeature<?, ?>>> list = new ArrayList<>();
            for (int i = 0; i < featureCount; i++) {
                list.add(feature);
            }
            return list;
        }
    }
}