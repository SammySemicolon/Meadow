package com.smellysleepy.meadow.common.worldgen.structure.grove.feature;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public record GroveFeatureDistributionEntry(ResourceKey<ConfiguredFeature<?, ?>> feature, int featureCount) {
    public List<ResourceKey<ConfiguredFeature<?, ?>>> asList() {
        ArrayList<ResourceKey<ConfiguredFeature<?, ?>>> list = new ArrayList<>();
        for (int i = 0; i < featureCount; i++) {
            list.add(feature);
        }
        return list;
    }
}
