package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.structure.grove.MeadowGroveStructure;
import com.smellysleepy.meadow.registry.tags.MeadowBiomeTagRegistry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedRandomList;
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
    
    public static final ResourceKey<Structure> MEADOW_GROVE = register("meadow_grove", (structureFactoryBootstapContext) ->
            new MeadowGroveStructure(
                    structure(structureFactoryBootstapContext.lookup(Registries.BIOME).getOrThrow(MeadowBiomeTagRegistry.HAS_MEADOW_GROVES),
                            Map.of(
                                    MobCategory.MONSTER,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                    )),
                                    MobCategory.CREATURE,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                    )),
                                    MobCategory.AMBIENT,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                    )),
                                    MobCategory.AXOLOTLS,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                            new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 5, 1, 4)
                                    )),
                                    MobCategory.WATER_CREATURE,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                            new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 1, 2)
                                    )),
                                    MobCategory.WATER_AMBIENT,
                                    new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
                                            new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 8)
                                    ))
                            ),
                            GenerationStep.Decoration.RAW_GENERATION, TerrainAdjustment.BURY)
            )
    );
    

    private static ResourceKey<Structure> register(String id, StructureFactory factory) {
        ResourceKey<Structure> structureSetResourceKey = ResourceKey.create(Registries.STRUCTURE, MeadowMod.meadowModPath(id));
        STRUCTURE_FACTORIES.put(structureSetResourceKey, factory);
        return structureSetResourceKey;
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, TerrainAdjustment adj) {
        return structure(tag, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, Map<MobCategory, StructureSpawnOverride> spawnOverrides, TerrainAdjustment adj) {
        return structure(tag, spawnOverrides, GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tag, GenerationStep.Decoration decoration, TerrainAdjustment adj) {
        return structure(tag, Map.of(), decoration, adj);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> pBiomes, Map<MobCategory, StructureSpawnOverride> pSpawnOverrides, GenerationStep.Decoration pStep, TerrainAdjustment pTerrainAdaptation) {
        return new Structure.StructureSettings(pBiomes, pSpawnOverrides, pStep, pTerrainAdaptation);
    }

    @FunctionalInterface
    public interface StructureFactory {
        Structure generate(BootstapContext<Structure> structureFactoryBootstapContext);
    }
}