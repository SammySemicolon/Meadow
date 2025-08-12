package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;
import java.util.Map;

public class MeadowStructureSets {

    public static final Map<ResourceKey<StructureSet>, StructureSetFactory> STRUCTURE_SET_FACTORIES = new Reference2ObjectOpenHashMap<>();

    public static final ResourceKey<StructureSet> MEADOW_GROVE = register("meadow_grove", structureHolderGetter -> {
        return new StructureSet(List.of(
                StructureSet.entry(structureHolderGetter.getOrThrow(MeadowStructures.MEADOW_GROVE))),
                new RandomSpreadStructurePlacement(24, 16, RandomSpreadType.TRIANGULAR, 546451665));
    });
    private static ResourceKey<StructureSet> register(String id, StructureSetFactory factory) {
        ResourceKey<StructureSet> structureSetResourceKey = ResourceKey.create(Registries.STRUCTURE_SET, MeadowMod.meadowPath(id));
        STRUCTURE_SET_FACTORIES.put(structureSetResourceKey, factory);
        return structureSetResourceKey;
    }

    @FunctionalInterface
    public interface StructureSetFactory {
        StructureSet generate(HolderGetter<Structure> placedFeatureHolderGetter);
    }
}