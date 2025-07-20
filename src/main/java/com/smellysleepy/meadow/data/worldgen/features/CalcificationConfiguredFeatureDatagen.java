package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.worldgen.feature.calcification.PointyCalcificationConfiguration;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowFeatureRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class CalcificationConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        var holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        var largeDripstoneStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get().defaultBlockState(), 3)
                .add(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get().defaultBlockState(), 1));
        var smallDripstoneStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get().defaultBlockState(), 5)
                .add(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get().defaultBlockState(), 1));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        largeDripstoneStateProvider,
                        true, 1, 3, 4, 6)));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        smallDripstoneStateProvider,
                        true, 1, 2, 2, 4)));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALACTITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        largeDripstoneStateProvider,
                        false, 4, 8, 3, 8)));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALACTITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        smallDripstoneStateProvider,
                        false, 2, 6, 2, 6)));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING, new ConfiguredFeature<>(Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration(MeadowBlockRegistry.CALCIFIED_COVERING.get(), 20, true, true, true, 0.5F,
                        HolderSet.direct(Block::builtInRegistryHolder,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE,
                                Blocks.DIRT, MeadowBlockRegistry.ASPEN_GRASS_BLOCK.get(), MeadowBlockRegistry.CALCIFIED_EARTH.get(), MeadowBlockRegistry.CALCIFIED_ROCK.get()))));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_VEGETATION, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 3)
                        .add(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 6)
                        .add(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get().defaultBlockState(), 8)
                        .add(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get().defaultBlockState(), 12)
                        .add(MeadowBlockRegistry.CALCIFIED_COVERING.get().defaultBlockState(), 40)))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_EARTH_BONEMEAL, new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                MeadowBlockTagRegistry.CALCIFICATION_REPLACEABLE,
                BlockStateProvider.simple(MeadowBlockRegistry.CALCIFIED_EARTH.get()),
                PlacementUtils.inlinePlaced(holdergetter.getOrThrow(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_VEGETATION)),
                CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F, UniformInt.of(1, 2), 0.75F)));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_ROCK_BONEMEAL, new ConfiguredFeature<>(Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                MeadowBlockTagRegistry.CALCIFICATION_REPLACEABLE,
                BlockStateProvider.simple(MeadowBlockRegistry.CALCIFIED_ROCK.get()),
                PlacementUtils.inlinePlaced(holdergetter.getOrThrow(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_VEGETATION)),
                CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F, UniformInt.of(1, 2), 0.75F)));
    }
}