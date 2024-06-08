package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.strange_plant.*;
import com.smellysleepy.meadow.common.worldgen.tree.*;
import com.smellysleepy.meadow.common.worldgen.tree.small.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraftforge.registries.*;

public class MeadowFeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MeadowMod.MEADOW);

    public static final RegistryObject<SmallMeadowTreeFeature> SMALL_MEADOW_TREE = FEATURE_TYPES.register("small_meadow_tree", SmallMeadowTreeFeature::new);
    public static final RegistryObject<MeadowTreeFeature> MEADOW_TREE = FEATURE_TYPES.register("meadow_tree", MeadowTreeFeature::new);

    public static final RegistryObject<StrangePlantFeature> STRANGE_PLANT = FEATURE_TYPES.register("strange_plant", StrangePlantFeature::new);

}