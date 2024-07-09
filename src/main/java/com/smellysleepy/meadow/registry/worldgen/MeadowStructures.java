package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.biome.MeadowGroveStructure;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class MeadowStructures {
    public static final Map<ResourceKey<Structure>, StructureFactory> STRUCTURE_FACTORIES = new Reference2ObjectOpenHashMap<>();
    
    public static final ResourceKey<Structure> LARGE_LAKE = register("large_cold_lake", (structureFactoryBootstapContext) ->
            new MeadowGroveStructure(
                    Structures.structure(structureFactoryBootstapContext.lookup(Registries.BIOME).getOrThrow(BWGBiomeTags.LARGE_COLD_LAKE),
                            Map.of(
                                    MobCategory.WATER_AMBIENT,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, SimpleWeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.CAT, 5, 4, 10)))
                            ),
                            GenerationStep.Decoration.RAW_GENERATION, TerrainAdjustment.NONE)
            )
    );
    

    private static ResourceKey<Structure> register(String id, StructureFactory factory) {
        ResourceKey<Structure> structureSetResourceKey = ResourceKey.create(Registries.STRUCTURE, MeadowMod.meadowModPath(id));
        STRUCTURE_FACTORIES.put(structureSetResourceKey, factory);
        return structureSetResourceKey;
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, TerrainAdjustment adj) {
        return Structures.structure(tag, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, Map<MobCategory, StructureSpawnOverride> spawnOverrides, TerrainAdjustment adj) {
        return Structures.structure(tag, spawnOverrides, GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, GenerationStep.Decoration decoration, TerrainAdjustment adj) {
        return Structures.structure(tag, Map.of(), decoration, adj);
    }

    @FunctionalInterface
    public interface StructureFactory {
        Structure generate(BootstapContext<Structure> structureFactoryBootstapContext);
    }
}