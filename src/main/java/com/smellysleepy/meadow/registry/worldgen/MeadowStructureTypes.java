package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.Supplier;

public class MeadowStructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE.key(), MeadowMod.MEADOW);

    public static final Supplier<StructureType<MeadowGroveStructure>> MEADOW_GROVE = STRUCTURE_TYPES.register("meadow_grove", ()-> ()-> MeadowGroveStructure.CODEC);

}