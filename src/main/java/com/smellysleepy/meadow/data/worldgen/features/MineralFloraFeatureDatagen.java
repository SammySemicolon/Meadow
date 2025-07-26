package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
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
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class MineralFloraFeatureDatagen {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        addMineralFloraTree(context, MineralFloraRegistry.COAL_FLORA, List.of(
                new StraightTrunkPart(7, 9),
                new OffsetPart(0, -3, 0),
                new LeafBlobPart(List.of(1, 2, 2, 2, 2, 1)),
                new RandomOffsetsPart(4, 0, 2),
                new LeafBlobPart(List.of(1, 2, 2, 2, 1))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.LAPIS_FLORA, List.of(
                new StraightTrunkPart(8, 10),
                new OffsetPart(0, -1, 0),
                new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1)),
                new OffsetPart(0, -3, 0),
                new SplitBranchesPart(3, 4, 2, 2, 2, 2),
                new OffsetPart(0, -2, 0),
                new LeafBlobPart(List.of(1, 2, 2, 2, 2, 2, 1))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.REDSTONE_FLORA, List.of(
                new ThickStumpPart(List.of(2, 1)),
                new StraightTrunkPart(2, 4),
                new DirectionalOffset(),
                new StraightTrunkPart(1, 3),
                new DirectionalOffset(),
                new StraightTrunkPart(1, 2),
                new LeafBlobPart(List.of(1, 3, 2, 1))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.COPPER_FLORA, List.of(
                new StraightTrunkPart(3, 4),
                new LeafBlobPart(List.of(3, 2, 1)),
                new ReturnPart(),
                new SplitBranchesPart(2, 3, 2, 3, 3, 4),
                new LeafBlobPart(List.of(2, 1))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.IRON_FLORA, List.of(
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
        ));

        addMineralFloraTree(context, MineralFloraRegistry.GOLD_FLORA, List.of(
                new ThickStumpPart(List.of(1)),
                new StraightTrunkPart(2, 3),
                new SplitBranchesPart(1, 1, 2, 3, 1, 1),
                new SplitBranchesPart(2, 3, 2, 3, 1, 1),
                new StraightTrunkPart(4, 5),
                new OffsetPart(0, -1, 0),
                new LeafBlobPart(List.of(2, 3, 3, 2, 1)),
                new OffsetPart(0, -2, 0),
                new SplitBranchesPart(1, 2, 2, 3, 3, 4),
                new LeafBlobPart(List.of(2, 3, 3, 2))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.DIAMOND_FLORA, List.of(
                new ThickStumpPart(List.of(1, 1)),
                new StraightTrunkPart(10, 12),
                new OffsetPart(0, -3, 0),
                new LeafDiamondPart(List.of(2, 3, 4, 4, 4, 3, 2)),
                new OffsetPart(0, -2, 0),
                new SplitBranchesPart(2, 3, 3, 4, 3, 4),
                new OffsetPart(0, -2, 0),
                new LeafBlobPart(List.of(1, 2, 3, 3, 2, 1))
        ));

        addMineralFloraTree(context, MineralFloraRegistry.EMERALD_FLORA, List.of(
                new ThickStumpPart(List.of(1, 1)),
                new StraightTrunkPart(10, 12),
                new OffsetPart(0, -3, 0),
                new LeafDiamondPart(List.of(2, 3, 4, 4, 4, 3, 2)),
                new OffsetPart(0, -2, 0),
                new SplitBranchesPart(2, 3, 3, 4, 3, 4),
                new OffsetPart(0, -2, 0),
                new LeafBlobPart(List.of(1, 2, 3, 3, 2, 1))
        ));

        for (MineralFloraRegistryBundle flora : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            addMineralFloraFlower(context, flora);
            addMineralFloraNaturalPatch(context, flora);
            addMineralGrassBonemeal(context, flora);
            addMineralLeavesBonemeal(context, flora);
        }
    }

    private static void addMineralFloraTree(BootstrapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora, List<MineralTreePart> parts) {
        context.register(flora.configuredTreeFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.MINERAL_TREE.get(), new MineralTreeFeatureConfiguration(
                flora, parts)
        ));
    }

    private static void addMineralFloraFlower(BootstrapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredFlowerFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.STRANGE_PLANT.get(), new StrangePlantFeatureConfiguration(
                flora)
        ));
    }

    private static void addMineralFloraNaturalPatch(BootstrapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredNaturalPatchFeature, new ConfiguredFeature<>(MeadowFeatureRegistry.LAYERED_PATCH.get(), new LayeredPatchConfiguration(
                flora, List.of(1, 1, 2, 2, 1)
        )));
    }

    private static void addMineralGrassBonemeal(BootstrapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredGrassBonemealFeature, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(flora.floraBlock.get().defaultBlockState()))));
    }

    private static void addMineralLeavesBonemeal(BootstrapContext<ConfiguredFeature<?, ?>> context, MineralFloraRegistryBundle flora) {
        context.register(flora.configuredLeavesBonemealFeature, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(flora.hangingLeavesBlock.get().defaultBlockState()))));
    }
}