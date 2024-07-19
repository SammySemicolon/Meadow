package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.common.worldgen.strange_plant.*;
import com.smellysleepy.meadow.common.worldgen.tree.meadow.MeadowTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.meadow.small.*;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.parts.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.*;

public class MeadowConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE, addTreeConfig(MeadowFeatureRegistry.SMALL_ASPEN_TREE.get(), new SmallMeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.THIN_ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.FLOWERING_ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, addTreeConfig(MeadowFeatureRegistry.ASPEN_TREE.get(), new MeadowTreeFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.ASPEN_LOG.get(),
                MeadowBlockRegistry.ASPEN_LEAVES.get(),
                MeadowBlockRegistry.FLOWERING_ASPEN_LEAVES.get(),
                MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get()
        )));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COAL_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COAL_FLORA, List.of(
                        new StraightTrunkPart(7, 9),
                        new OffsetPart(0, -2, 0),
                        new RandomOffsetsPart(4, 0, 2),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 1))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_LAPIS_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.LAPIS_FLORA, List.of(
                        new StraightTrunkPart(8, 10),
                        new OffsetPart(0, -1, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1)),
                        new OffsetPart(0, -3, 0),
                        new SplitBranchesPart(3, 4, 2, 2, 2, 2),
                        new OffsetPart(0, -2, 0),
                        new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_REDSTONE_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.REDSTONE_FLORA, List.of(
                        new ThickStumpPart(List.of(2, 2, 1)),
                        new StumpFoldsPart(2, 2, 1, 1, 3, 4, 1, 2),
                        new StraightTrunkPart(3, 6)
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_COPPER_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.COPPER_FLORA, List.of(
                        new StraightTrunkPart(3, 4),
                        new LeafBlobPart(List.of(3, 2)),
                        new ReturnPart(),
                        new SplitBranchesPart(2, 3, 2, 3, 3, 4),
                        new LeafBlobPart(List.of(3, 2))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_IRON_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.IRON_FLORA, List.of(
                        new ThickStumpPart(List.of(1, 1)),
                        new StraightTrunkPart(4, 5),
                        new ReturnPart(),
                        new DirectionalOffset(),
                        new StraightTrunkPart(6, 8),
                        new DirectionalOffset(),
                        new StraightTrunkPart(3, 4),
                        new OffsetPart(0, -3, 0),
                        new RandomOffsetsPart(4, 0, 2),
                        new LeafDiamondPart(List.of(0, 1, 2, 3, 2, 1, 0))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_GOLD_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.GOLD_FLORA, List.of(
                        new ThickStumpPart(List.of(1)),
                        new StraightTrunkPart(2, 3),
                        new SplitBranchesPart(1, 1, 2, 3, 1, 1),
                        new SplitBranchesPart(2, 3, 2, 3, 1, 1),
                        new StraightTrunkPart(2, 3),
                        new LeafBlobPart(List.of(2, 3, 4, 2)),
                        new OffsetPart(0, -2, 0),
                        new SplitBranchesPart(2, 4, 2, 3, 3, 4),
                        new LeafBlobPart(List.of(2, 3, 2))
                ))
        ));

        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_EMERALD_TREE, addTreeConfig(MeadowFeatureRegistry.MINERAL_TREE.get(),
                new MineralTreeFeatureConfiguration(MineralFloraRegistry.EMERALD_FLORA, List.of(
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