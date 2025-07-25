package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MeadowConfiguredFeatureRegistry {

    //Aspen Forest
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_THIN_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("thin_aspen_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("aspen_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("aspen_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_aspen_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("aspen_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_ASPEN_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_aspen_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_meadow_lake_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_PEARLFLOWER = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("pearlflower"));

    //Fungal Growth
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CHANTERELLE_FUNGUS_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("chanterelle_fungus_tree"));


    //Calcification
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_calcified_stalagmites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_stalagmites"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_CALCIFIED_STALACTITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_calcified_stalactites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_STALACTITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_stalactites"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_COVERING = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_covering"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_VEGETATION = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_vegetation"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_EARTH_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_earth_bonemeal"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_ROCK_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_rock_bonemeal"));

}
