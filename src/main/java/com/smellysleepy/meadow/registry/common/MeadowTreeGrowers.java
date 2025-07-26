package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class MeadowTreeGrowers {

    public static final TreeGrower ASPEN = register("meadow:aspen", MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE);
    public static final TreeGrower THIN_ASPEN = register("thin_aspen", MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE);

    public static TreeGrower register(String id, ResourceKey<ConfiguredFeature<?, ?>> tree) {
        return new TreeGrower(id, Optional.empty(), Optional.of(tree), Optional.empty());
    }
}
