package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.common.block.mineral_flora.*;
import com.smellysleepy.meadow.common.worldgen.feature.PearlFlowerConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.calcification.PointyCalcificationConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.meadow.MeadowTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.*;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.*;

public class MeadowConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MineralFloraRegistry.COAL_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COAL_FLORA, Blocks.COAL_ORE, List.of(
                        new StraightTrunkPart(7, 9),
                        new OffsetPart(0, -3, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 1)),
                        new RandomOffsetsPart(4, 0, 2),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 1))
                ))
        ));

        context.register(MineralFloraRegistry.LAPIS_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.LAPIS_FLORA, Blocks.LAPIS_ORE, List.of(
                        new StraightTrunkPart(8, 10),
                        new OffsetPart(0, -1, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1)),
                        new OffsetPart(0, -3, 0),
                        new SplitBranchesPart(3, 4, 2, 2, 2, 2),
                        new OffsetPart(0, -2, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1))
                ))
        ));

        context.register(MineralFloraRegistry.REDSTONE_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.REDSTONE_FLORA, Blocks.REDSTONE_ORE, List.of(
                        new ThickStumpPart(List.of(2, 1)),
                        new StraightTrunkPart(2, 4),
                        new DirectionalOffset(),
                        new StraightTrunkPart(1, 3),
                        new DirectionalOffset(),
                        new StraightTrunkPart(1, 2),
                        new LeafBlobPart(List.of(1, 3, 2, 1))
                ))
        ));

        context.register(MineralFloraRegistry.COPPER_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COPPER_FLORA, Blocks.COPPER_ORE, List.of(
                        new StraightTrunkPart(3, 4),
                        new LeafBlobPart(List.of(3, 2)),
                        new ReturnPart(),
                        new SplitBranchesPart(2, 3, 2, 3, 3, 4),
                        new LeafBlobPart(List.of(3, 2))
                ))
        ));

        context.register(MineralFloraRegistry.IRON_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.IRON_FLORA, Blocks.IRON_ORE, List.of(
                        new ThickStumpPart(List.of(1, 1)),
                        new StraightTrunkPart(4, 5),
                        new ReturnPart(),
                        new DirectionalOffset(),
                        new StraightTrunkPart(6, 8),
                        new DirectionalOffset(),
                        new StraightTrunkPart(3, 4),
                        new OffsetPart(0, -3, 0),
                        new LeafDiamondPart(List.of(0, 1, 2, 3, 2, 1, 0)),
                        new RandomOffsetsPart(4, 0, 1),
                        new LeafDiamondPart(List.of(0, 1, 2, 1, 0))
                ))
        ));

        context.register(MineralFloraRegistry.GOLD_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.GOLD_FLORA, Blocks.GOLD_ORE, List.of(
                        new ThickStumpPart(List.of(1)),
                        new StraightTrunkPart(2, 3),
                        new SplitBranchesPart(1, 1, 2, 3, 1, 1),
                        new SplitBranchesPart(2, 3, 2, 3, 1, 1),
                        new StraightTrunkPart(4, 5),
                        new LeafBlobPart(List.of(2, 3, 4, 2)),
                        new OffsetPart(0, -3, 0),
                        new SplitBranchesPart(1, 2, 2, 3, 3, 4),
                        new LeafBlobPart(List.of(2, 3, 2))
                ))
        ));

        context.register(MineralFloraRegistry.EMERALD_FLORA.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.EMERALD_FLORA, Blocks.EMERALD_ORE, List.of(
                        new ThickStumpPart(List.of(1, 1)),
                        new StraightTrunkPart(10, 12),
                        new OffsetPart(0, -3, 0),
                        new LeafDiamondPart(List.of(2, 3, 4, 4, 4, 3, 2)),
                        new OffsetPart(0, -2, 0),
                        new SplitBranchesPart(2, 3, 3, 4, 3, 4),
                        new OffsetPart(0, -2, 0),
                        new LeafBlobPart(List.of(1, 2, 3, 3, 2, 1))
                ))
        ));

        for (MineralFloraRegistryBundle flora : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            addMineralFloraNaturalPatch(context, flora);
            addMineralGrassBonemeal(context, flora);
            addMineralLeavesBonemeal(context, flora);
        }

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.SMALL_ASPEN_TREE.get(), new MeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.THIN_ASPEN_LOG.get(),
                MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.THIN_CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.ASPEN_TREE.get(), new MeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.ASPEN_LOG.get(),
                MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.CALCIFIED_ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), MeadowBlockRegistry.TALL_MEADOW_GRASS.get(), MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), MeadowBlockRegistry.SHORT_MEADOW_GRASS.get()),
                List.of(3, 6, 9, 4)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), MeadowBlockRegistry.SHORT_MEADOW_GRASS.get()),
                List.of(3, 6, 3)
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                List.of(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), MeadowBlockRegistry.SHORT_MEADOW_GRASS.get()),
                List.of(3, 4, 2)
        )));

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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_GRASS_BONEMEAL, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get().defaultBlockState()))));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, new ConfiguredFeature<>(MeadowFeatureRegistry.PEARLFLOWER.get(),
                new PearlFlowerConfiguration(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.GRASSY_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.MARINE_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get().defaultBlockState(), 1)),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get().defaultBlockState(), 1)))));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get().defaultBlockState(), 3)
                                .add(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get().defaultBlockState(), 1)),
                        1, 3, 4, 6)));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES, new ConfiguredFeature<>(MeadowFeatureRegistry.POINTY_CALCIFICATION.get(),
                new PointyCalcificationConfiguration(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get().defaultBlockState(), 5)
                                .add(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get().defaultBlockState(), 1)),
                        1, 2, 2, 4)));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING, new ConfiguredFeature<>(Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration(MeadowBlockRegistry.CALCIFIED_COVERING.get(), 20, true, true, true, 0.5F,
                        HolderSet.direct(Block::builtInRegistryHolder,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE,
                                Blocks.DIRT, MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(), MeadowBlockRegistry.CALCIFIED_EARTH.get(), MeadowBlockRegistry.CALCIFIED_ROCK.get()))));
    }

    private static void addMineralFloraFlower(BootstapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredFlowerFeature, new ConfiguredFeature<>(Feature.FLOWER.get(), new LayeredPatchConfiguration(
                flora, List.of(1, 1, 2, 2, 1)
        )));
    }

    private static void addMineralFloraNaturalPatch(BootstapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredNaturalPatchFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                flora, List.of(1, 1, 2, 2, 1)
        )));
    }
    private static void addMineralGrassBonemeal(BootstapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredGrassBonemealFeature, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(flora.floraBlock.get().defaultBlockState()))));
    }
    private static void addMineralLeavesBonemeal(BootstapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredLeavesBonemealFeature, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(flora.hangingLeavesBlock.get().defaultBlockState()))));
    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }

    private static ConfiguredFeature<?, ?> addOreConfig(Feature<OreConfiguration> feature, List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(feature, new OreConfiguration(targetList, veinSize));
    }
}