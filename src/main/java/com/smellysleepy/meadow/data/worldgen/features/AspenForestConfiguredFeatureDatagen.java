package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.aspen.AspenTreeFeatureConfiguration;
import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

public class AspenForestConfiguredFeatureDatagen {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.SMALL_ASPEN_TREE.get(), new AspenTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.THIN_ASPEN_LOG.get(),
                MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.THIN_CALCIFIED_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.ASPEN_TREE.get(), new AspenTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.ASPEN_LOG.get(),
                MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.CALCIFIED_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_ASPEN_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_ASPEN_GRASS.get(), MeadowBlockRegistry.TALL_ASPEN_GRASS.get(), MeadowBlockRegistry.MEDIUM_ASPEN_GRASS.get(), MeadowBlockRegistry.SHORT_ASPEN_GRASS.get()),
                List.of(3, 6, 9, 4)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_ASPEN_GRASS.get(), MeadowBlockRegistry.MEDIUM_ASPEN_GRASS.get(), MeadowBlockRegistry.SHORT_ASPEN_GRASS.get()),
                List.of(3, 6, 3)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_ASPEN_GRASS.get(), MeadowBlockRegistry.MEDIUM_ASPEN_GRASS.get(), MeadowBlockRegistry.SHORT_ASPEN_GRASS.get()),
                List.of(3, 4, 2)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_GRASS_BONEMEAL, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(MeadowBlockRegistry.SHORT_ASPEN_GRASS.get().defaultBlockState()))));
    }
}