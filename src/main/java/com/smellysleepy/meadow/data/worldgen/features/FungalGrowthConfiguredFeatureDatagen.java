package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.feature.PearlFlowerConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.StrangePlantFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.calcification.PointyCalcificationConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.aspen.AspenTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.fungi.ChanterelleFungusFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts.*;
import com.smellysleepy.meadow.registry.common.MineralFloraRegistry;
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
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;

public class FungalGrowthConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CHANTERELLE_FUNGUS_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.CHANTERELLE_FUNGUS_TREE.get(), new ChanterelleFungusFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                Blocks.MUSHROOM_STEM,
                MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_WOOD.get(),
                MeadowBlockRegistry.CALCIFIED_WOOD.get(),
                Blocks.BROWN_MUSHROOM_BLOCK
        )));
    }
}