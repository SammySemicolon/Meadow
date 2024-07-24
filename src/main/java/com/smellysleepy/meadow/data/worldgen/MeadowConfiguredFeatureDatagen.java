package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.common.worldgen.feature.PearlFlowerConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.StrangePlantFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.meadow.MeadowTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.meadow.SmallMeadowTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.worldgen.*;
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
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.SMALL_ASPEN_TREE.get(), new SmallMeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.THIN_ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.ASPEN_TREE.get(), new MeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COAL_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COAL_FLORA, Blocks.COAL_ORE, List.of(
                        new StraightTrunkPart(7, 9),
                        new OffsetPart(0, -3, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 1)),
                        new RandomOffsetsPart(4, 0, 2),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 1))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LAPIS_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_REDSTONE_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COPPER_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COPPER_FLORA, Blocks.COPPER_ORE, List.of(
                        new StraightTrunkPart(3, 4),
                        new LeafBlobPart(List.of(3, 2)),
                        new ReturnPart(),
                        new SplitBranchesPart(2, 3, 2, 3, 3, 4),
                        new LeafBlobPart(List.of(3, 2))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_IRON_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_GOLD_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_EMERALD_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(),
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

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COAL_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.COAL_FLORA, Blocks.COAL_ORE
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LAPIS_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.LAPIS_FLORA, Blocks.LAPIS_ORE
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_REDSTONE_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.REDSTONE_FLORA, Blocks.REDSTONE_ORE
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COPPER_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.COPPER_FLORA, Blocks.COPPER_ORE
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_IRON_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.IRON_FLORA, Blocks.IRON_ORE
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_GOLD_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.GOLD_FLORA, Blocks.GOLD_ORE
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_EMERALD_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.EMERALD_FLORA, Blocks.EMERALD_ORE
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_DIAMOND_PLANT, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                MineralFloraRegistry.DIAMOND_FLORA, Blocks.DIAMOND_ORE
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COAL_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.COAL_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LAPIS_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.LAPIS_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_REDSTONE_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.REDSTONE_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COPPER_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.COPPER_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_IRON_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.IRON_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_GOLD_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.GOLD_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_EMERALD_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.EMERALD_FLORA, List.of(1, 1, 2, 2, 1)
        )));
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_DIAMOND_PATCH, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                MineralFloraRegistry.DIAMOND_FLORA, List.of(1, 1, 2, 2, 1)
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
                                .add(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get().defaultBlockState(), 1)))));

    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }

    private static ConfiguredFeature<?, ?> addOreConfig(Feature<OreConfiguration> feature, List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(feature, new OreConfiguration(targetList, veinSize));
    }
}