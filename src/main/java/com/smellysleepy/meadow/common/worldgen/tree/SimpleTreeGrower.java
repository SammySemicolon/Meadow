package com.smellysleepy.meadow.common.worldgen.tree;

import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SimpleTreeGrower extends AbstractTreeGrower {

    public final ResourceKey<ConfiguredFeature<?, ?>> tree;

    public SimpleTreeGrower(ResourceKey<ConfiguredFeature<?, ?>> tree) {
        this.tree = tree;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return tree;
    }
}