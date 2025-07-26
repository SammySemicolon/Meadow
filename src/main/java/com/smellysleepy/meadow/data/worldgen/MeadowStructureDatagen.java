package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.registry.worldgen.MeadowStructureSets;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class MeadowStructureDatagen {

    public static void structureBootstrap(BootstrapContext<Structure> context) {
        MeadowStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context)));
    }
    public static void structureSetBootstrap(BootstrapContext<StructureSet> context) {
        MeadowStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE))));
    }
}