package com.smellysleepy.meadow.data.worldgen.features;

import com.smellysleepy.meadow.common.worldgen.feature.tree.fungi.ChanterelleFungusFeatureConfiguration;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowFeatureRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class FungalGrowthConfiguredFeatureDatagen {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MeadowConfiguredFeatureRegistry.CONFIGURED_CHANTERELLE_FUNGUS_TREE, new ConfiguredFeature<>(MeadowFeatureRegistry.CHANTERELLE_FUNGUS_TREE.get(), new ChanterelleFungusFeatureConfiguration(
                MeadowBlockRegistry.ASPEN_SAPLING.get(),
                MeadowBlockRegistry.CHANTERELLE_STEM_BLOCK.get(),
                MeadowBlockRegistry.PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK.get(),
                MeadowBlockRegistry.CALCIFIED_CHANTERELLE_STEM_BLOCK.get(),
                MeadowBlockRegistry.CHANTERELLE_CAP_BLOCK.get()
        )));
    }
}