package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class AspenForestBiomeType extends MeadowGroveBiomeType {
    public AspenForestBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler) {
        return MeadowBlockRegistry.ASPEN_GRASS_BLOCK.get().defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.6f) {
            return Blocks.DIRT.defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public void createSurfaceFeatures(RandomSource random, GroveFeatureProvider.GroveFeatureProviderBuilder builder) {
        int pearlflowerCount = random.nextInt(2, 6);
        int smallTreeCount = random.nextInt(2, 4);
        int largeTreeCount = random.nextInt(4, 6);

        int largePatchCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 1, 3);
        int mediumPatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 1, 3);
        int smallPatchCount = RandomHelper.randomBetween(random, Easing.SINE_OUT, 2, 4);

        int springWaterCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 0, 2);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, pearlflowerCount);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE, smallTreeCount);
        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, largeTreeCount);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_ASPEN_PATCH, largePatchCount);
        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_PATCH, mediumPatchCount);
        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_PATCH, smallPatchCount);

        builder.addFeature(MiscOverworldFeatures.SPRING_WATER, springWaterCount);

    }
}