package com.smellysleepy.meadow.common.worldgen.structure.grove.feature;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.HashMap;
import java.util.Optional;

public final class PlacedGroveFeatures {
    private final ImmutableMap<Integer, ResourceKey<ConfiguredFeature<?, ?>>> featuresToPlace;

    public PlacedGroveFeatures(HashMap<Integer, ResourceKey<ConfiguredFeature<?, ?>>> featuresToPlace) {
        this.featuresToPlace = ImmutableMap.copyOf(featuresToPlace);
    }

    public Optional<ResourceKey<ConfiguredFeature<?, ?>>> getFeature(int coordinate) {
        return Optional.ofNullable(featuresToPlace.get(coordinate));
    }
}
