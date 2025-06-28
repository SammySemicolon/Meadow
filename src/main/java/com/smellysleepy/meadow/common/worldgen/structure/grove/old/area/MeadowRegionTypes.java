package com.smellysleepy.meadow.common.worldgen.structure.grove.old.area;

import com.smellysleepy.meadow.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;

import java.util.*;
import java.util.function.*;

public class MeadowRegionTypes {

    public static final HashMap<ResourceLocation, Function<CompoundTag, SpecialMeadowRegion>> DECODER = new HashMap<>();

    public static final ResourceLocation CALCIFICATION = addType(MeadowMod.meadowModPath("calcification"), CalcifiedRegion::deserialize);
    public static final ResourceLocation LAKE = addType(MeadowMod.meadowModPath("lake"), LakeRegion::deserialize);

    public static ResourceLocation addType(ResourceLocation type, Function<CompoundTag, SpecialMeadowRegion> function) {
        DECODER.put(type, function);
        return type;
    }
}
