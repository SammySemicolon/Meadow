package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.feature.PearlFlowerConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.StrangePlantFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.aspen.AspenTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts.*;
import com.smellysleepy.meadow.registry.common.MineralFloraRegistry;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowFeatureRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;

public class MiscConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_LAKE_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.SEAGRASS),
                List.of(1, 4, 2, 1)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_LAKE_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(Blocks.SEAGRASS),
                List.of(1, 2, 1)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_LAKE_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(Blocks.SEAGRASS),
                List.of(1, 1)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, new ConfiguredFeature<>(MeadowFeatureRegistry.PEARLFLOWER.get(),
                new PearlFlowerConfiguration(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.GRASSY_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.ROCKY_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_ROCKY_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.MARINE_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 1)))));
    }
}