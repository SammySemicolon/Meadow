package com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification;

import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.MeadowGroveBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class CreepingCalcificationBiomeType extends MeadowGroveBiomeType {
    public CreepingCalcificationBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler) {
        return MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.1f) {
            return MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState();
        }
        if (depth < 0.6f) {
            return MeadowBlockRegistry.CALCIFIED_ROCK.get().defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public void createSurfaceFeatures(RandomSource random, GroveFeatureProvider.GroveFeatureProviderBuilder builder) {
        int pearlflowerCount = random.nextInt(4, 8);

        int largeStalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_IN, 4, 10);
        int stalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_OUT, 6, 12);

        int vegetationCount = RandomHelper.randomBetween(random, Easing.QUAD_OUT, 6, 12);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, pearlflowerCount);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, largeStalagmiteCount);
        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES, stalagmiteCount);

        builder.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_VEGETATION, vegetationCount);
    }
}
