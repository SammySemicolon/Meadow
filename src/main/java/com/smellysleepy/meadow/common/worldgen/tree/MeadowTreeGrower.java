package com.smellysleepy.meadow.common.worldgen.tree;

import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MeadowTreeGrower extends AbstractTreeGrower {

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_TREE;
    }
}
