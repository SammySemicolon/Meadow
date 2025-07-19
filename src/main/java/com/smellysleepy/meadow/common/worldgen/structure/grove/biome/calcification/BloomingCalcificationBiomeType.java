package com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification;

import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.MeadowGroveBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class BloomingCalcificationBiomeType extends MeadowGroveBiomeType {
    public BloomingCalcificationBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock() {
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
    public GroveFeatureProvider createSurfaceFeatures(RandomSource random) {
        int largeStalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_IN, 2, 8);
        int stalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_OUT, 0, 4);
        var features = GroveFeatureProvider.create();
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, largeStalagmiteCount);
        features.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES, stalagmiteCount);
        return features.build();
    }
}
