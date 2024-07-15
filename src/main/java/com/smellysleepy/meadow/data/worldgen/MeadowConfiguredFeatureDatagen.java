package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.common.worldgen.strange_plant.*;
import com.smellysleepy.meadow.common.worldgen.tree.*;
import com.smellysleepy.meadow.common.worldgen.tree.small.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.*;

public class MeadowConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_TREE, addTreeConfig(MeadowFeatureRegistry.SMALL_MEADOW_TREE.get(), new SmallMeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.THIN_ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.FLOWERING_ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_TREE, addTreeConfig(MeadowFeatureRegistry.MEADOW_TREE.get(), new MeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.FLOWERING_ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CRIMSON_SUN, addTreeConfig(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.REDSTONE_FLORA.flower.get(),
                Blocks.REDSTONE_ORE,
                Blocks.BARRIER,
                Blocks.TUFF,
                Blocks.ANDESITE
        )));

        context.register(MeadowConfiguredFeatureRegistry.LAZURITE_ROSE, addTreeConfig(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.LAPIS_FLORA.flower.get(),
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.BARRIER,
                MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(),
                Blocks.TUFF
        )));
    }

    private static <T extends FeatureConfiguration, K extends Feature<T>> ConfiguredFeature<?, ?> addTreeConfig(K feature, T config) {
        return new ConfiguredFeature<>(feature, config);
    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }

    private static ConfiguredFeature<?, ?> addOreConfig(Feature<OreConfiguration> feature, List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(feature, new OreConfiguration(targetList, veinSize));
    }
}