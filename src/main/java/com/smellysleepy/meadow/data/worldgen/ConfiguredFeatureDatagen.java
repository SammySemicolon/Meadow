package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.data.worldgen.features.*;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.stringtemplate.v4.misc.Misc;

public class ConfiguredFeatureDatagen {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        AspenForestConfiguredFeatureDatagen.bootstrap(context);
        FungalGrowthConfiguredFeatureDatagen.bootstrap(context);
        CalcificationConfiguredFeatureDatagen.bootstrap(context);
        MineralFloraFeatureDatagen.bootstrap(context);
        MiscConfiguredFeatureDatagen.bootstrap(context);
    }
}