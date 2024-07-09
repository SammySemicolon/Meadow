package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.data.MeadowBiomeTagDatagen;
import com.smellysleepy.meadow.data.MeadowBlockStateDatagen;
import com.smellysleepy.meadow.data.MeadowBlockTagDatagen;
import com.smellysleepy.meadow.data.MeadowItemModelDatagen;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructureSets;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MeadowStructureDatagen {

    public static void structureBootstrap(BootstapContext<Structure> context) {
        MeadowStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context)));
    }
    public static void structureSetBootstrap(BootstapContext<StructureSet> context) {
        MeadowStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE))));
    }
}