package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class AspenForestBiomeType extends MeadowGroveBiomeType {
    public AspenForestBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock() {
        return MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.6f) {
            return Blocks.DIRT.defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public GroveFeatureProvider createSurfaceFeatures(RandomSource random) {
        int pearlflowerCount = random.nextInt(3, 6);
        int smallTreeCount = random.nextInt(4, 8);
        int largeTreeCount = random.nextInt(12, 24);
        int largePatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 1, 3);
        int mediumPatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 1, 4);
        int smallPatchCount = RandomHelper.randomBetween(random, Easing.SINE_OUT, 1, 5);

        var features = GroveFeatureProvider.create();
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, pearlflowerCount);

        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE, smallTreeCount);
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, largeTreeCount);

        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_PATCH, largePatchCount);
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_PATCH, mediumPatchCount);
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH, smallPatchCount);
        return features.build();
    }
}