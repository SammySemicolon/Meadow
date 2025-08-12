package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MeadowConfiguredFeatureRegistry {

    //Aspen Forest
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_THIN_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("thin_aspen_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("aspen_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("aspen_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("large_aspen_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("aspen_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("small_aspen_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("large_meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("small_meadow_lake_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_PEARLFLOWER = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("pearlflower"));

    //Fungal Growth
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CHANTERELLE_FUNGUS_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("chanterelle_fungus_tree"));


    //Calcification
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("large_calcified_stalagmites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_stalagmites"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_CALCIFIED_STALACTITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("large_calcified_stalactites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_STALACTITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_stalactites"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_COVERING = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_covering"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_VEGETATION = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_vegetation"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_EARTH_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_earth_bonemeal"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_ROCK_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath("calcified_rock_bonemeal"));

}
