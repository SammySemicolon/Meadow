package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MeadowConfiguredFeatureRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_aspen_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_ASPEN_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("aspen_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("meadow_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_MEADOW_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_meadow_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("meadow_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_MEADOW_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_meadow_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("meadow_lake_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SMALL_MEADOW_LAKE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("small_meadow_lake_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_PEARLFLOWER = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("pearlflower"));


    //Calcification
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LARGE_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("large_calcified_stalagmites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_STALAGMITES = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_stalagmites"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CALCIFIED_COVERING = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("calcified_covering"));

    //Mineral Flora
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_AMETHYST_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("amethyst_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COAL_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("coal_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COAL_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("coal_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COAL_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("coal_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COAL_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("coal_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LAPIS_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("lapis_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LAPIS_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("lapis_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LAPIS_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("lapis_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_LAPIS_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("lapis_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_REDSTONE_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("redstone_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_REDSTONE_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("redstone_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_REDSTONE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("redstone_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_REDSTONE_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("redstone_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COPPER_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("copper_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COPPER_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("copper_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COPPER_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("copper_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_COPPER_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("copper_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_IRON_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("iron_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_IRON_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("iron_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_IRON_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("iron_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_IRON_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("iron_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_GOLD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("gold_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_GOLD_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("gold_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_GOLD_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("gold_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_GOLD_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("gold_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EMERALD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("emerald_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EMERALD_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("emerald_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EMERALD_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("emerald_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EMERALD_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("emerald_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DIAMOND_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("diamond_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DIAMOND_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("diamond_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DIAMOND_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("diamond_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DIAMOND_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("diamond_grass_bonemeal"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHERITE_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("netherite_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHERITE_PLANT = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("netherite_plant"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHERITE_PATCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("netherite_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NETHERITE_GRASS_BONEMEAL = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath("netherite_grass_bonemeal"));


}
