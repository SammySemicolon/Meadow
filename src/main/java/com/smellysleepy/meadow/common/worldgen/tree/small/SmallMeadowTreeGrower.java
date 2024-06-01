package com.smellysleepy.meadow.common.worldgen.tree.small;

import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.levelgen.feature.*;

public class SmallMeadowTreeGrower extends AbstractTreeGrower {

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_TREE;
    }
}
