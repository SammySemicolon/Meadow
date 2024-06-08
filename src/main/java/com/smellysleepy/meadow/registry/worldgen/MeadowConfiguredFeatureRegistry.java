package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MeadowConfiguredFeatureRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_MEADOW_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_meadow_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("meadow_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_SUN = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("crimson_sun"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAZURITE_ROSE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("lazurite_rose"));

}
