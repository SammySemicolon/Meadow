package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.data.worldgen.features.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class FungalGrowthBiomeType extends MeadowGroveBiomeType {
    public FungalGrowthBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler) {
        return Blocks.MYCELIUM.defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.3f) {
            return Blocks.DIRT.defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public void createSurfaceFeatures(RandomSource random, GroveFeatureProvider.GroveFeatureProviderBuilder builder) {
        int lichenCount = random.nextInt(2, 12);
        int rockCount = random.nextInt(1, 6);

        int brownMushroomCount = random.nextInt(1, 3);
        int redMushroomCount = random.nextInt(1, 3);

        int brownMushroomTreeCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 0, 2);
        int redMushroomTreeCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 0, 2);

        int chanterelleMushroomTreeCount = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 1, 4);

        int clayDiscCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 0, 2);

        builder.addFeature(CaveFeatures.GLOW_LICHEN, lichenCount);
        builder.addFeature(MiscOverworldFeatures.FOREST_ROCK, rockCount);

        builder.addFeature(VegetationFeatures.PATCH_BROWN_MUSHROOM, brownMushroomCount);
        builder.addFeature(VegetationFeatures.PATCH_RED_MUSHROOM, redMushroomCount);

        builder.addFeature(TreeFeatures.HUGE_BROWN_MUSHROOM, brownMushroomTreeCount);
        builder.addFeature(TreeFeatures.HUGE_RED_MUSHROOM, redMushroomTreeCount);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_CHANTERELLE_FUNGUS_TREE, chanterelleMushroomTreeCount);

        builder.addFeature(MiscOverworldFeatures.DISK_CLAY, clayDiscCount);

    }
}
