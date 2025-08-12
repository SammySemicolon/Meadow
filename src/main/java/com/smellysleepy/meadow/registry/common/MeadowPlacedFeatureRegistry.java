package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class MeadowPlacedFeatureRegistry {

    public static final ResourceKey<PlacedFeature> ASPEN_TREE = registerKey("aspen_tree");
    public static final ResourceKey<PlacedFeature> SMALL_ASPEN_TREE = registerKey("small_aspen_tree");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, MeadowMod.meadowPath(name));
    }
}