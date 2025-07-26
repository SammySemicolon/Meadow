package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.concurrent.*;

public class MeadowWorldgenRegistryDatagen extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.STRUCTURE, MeadowStructureDatagen::structureBootstrap)
            .add(Registries.STRUCTURE_SET, MeadowStructureDatagen::structureSetBootstrap)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureDatagen::bootstrap)
            .add(Registries.PLACED_FEATURE, MeadowPlacedFeatureDatagen::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MeadowBiomeModifications::bootstrap);

    public MeadowWorldgenRegistryDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", MeadowMod.MEADOW));
    }
}