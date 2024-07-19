package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.feature.StrangePlantFeature;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchFeature;
import com.smellysleepy.meadow.common.worldgen.tree.meadow.MeadowTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.meadow.SmallMeadowTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraftforge.registries.*;

public class MeadowFeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MeadowMod.MEADOW);

    public static final RegistryObject<SmallMeadowTreeFeature> SMALL_ASPEN_TREE = FEATURE_TYPES.register("small_aspen_tree", SmallMeadowTreeFeature::new);
    public static final RegistryObject<MeadowTreeFeature> ASPEN_TREE = FEATURE_TYPES.register("aspen_tree", MeadowTreeFeature::new);

    public static final RegistryObject<MineralTreeFeature> MINERAL_TREE = FEATURE_TYPES.register("mineral_tree", MineralTreeFeature::new);

    public static final RegistryObject<StrangePlantFeature> STRANGE_PLANT = FEATURE_TYPES.register("strange_plant", StrangePlantFeature::new);

    public static final RegistryObject<LayeredPatchFeature> LAYERED_PATCH = FEATURE_TYPES.register("layered_patch", LayeredPatchFeature::new);

}