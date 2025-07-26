package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.feature.PearlflowerFeature;
import com.smellysleepy.meadow.common.worldgen.feature.StrangePlantFeature;
import com.smellysleepy.meadow.common.worldgen.feature.calcification.PointyCalcificationFeature;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.aspen.AspenTreeFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.aspen.SmallAspenTreeFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.fungi.ChanterelleFungusFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

public class MeadowFeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(BuiltInRegistries.FEATURE, MeadowMod.MEADOW);

    public static final Supplier<SmallAspenTreeFeature> SMALL_ASPEN_TREE = FEATURE_TYPES.register("small_aspen_tree", SmallAspenTreeFeature::new);
    public static final Supplier<AspenTreeFeature> ASPEN_TREE = FEATURE_TYPES.register("aspen_tree", AspenTreeFeature::new);

    public static final Supplier<ChanterelleFungusFeature> CHANTERELLE_FUNGUS_TREE = FEATURE_TYPES.register("chanterelle_fungus_tree", ChanterelleFungusFeature::new);

    public static final Supplier<MineralTreeFeature> MINERAL_TREE = FEATURE_TYPES.register("mineral_tree", MineralTreeFeature::new);

    public static final Supplier<StrangePlantFeature> STRANGE_PLANT = FEATURE_TYPES.register("strange_plant", StrangePlantFeature::new);

    public static final Supplier<PearlflowerFeature> PEARLFLOWER = FEATURE_TYPES.register("pearlflower", PearlflowerFeature::new);

    public static final Supplier<LayeredPatchFeature> LAYERED_PATCH = FEATURE_TYPES.register("layered_patch", LayeredPatchFeature::new);

    public static final Supplier<PointyCalcificationFeature> POINTY_CALCIFICATION = FEATURE_TYPES.register("pointy_calcification", PointyCalcificationFeature::new);

}