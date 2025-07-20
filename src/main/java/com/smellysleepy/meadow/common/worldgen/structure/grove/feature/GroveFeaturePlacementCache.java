package com.smellysleepy.meadow.common.worldgen.structure.grove.feature;

import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.MeadowGroveBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataCoordinate;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.MeadowGroveGenerationData;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.*;

public class GroveFeaturePlacementCache {

    private final HashMap<DataCoordinate, BlockPos> coordinates = new HashMap<>();

    private final Set<DataCoordinate> spentCoordinates = new HashSet<>();

    private final Set<MeadowGroveBiomeType> activeBiomes = new HashSet<>();

    public void addPosition(DataEntry dataEntry, BlockPos pos) {
        coordinates.put(dataEntry.getDataCoordinate(), pos);
        activeBiomes.add(dataEntry.biomeType());
    }

    public Optional<BlockPos> getFeaturePosition(DataCoordinate position) {
        if (coordinates.containsKey(position)) {
            return Optional.of(coordinates.get(position));
        }
        return Optional.empty();
    }

    public void consumeFeaturePosition(DataCoordinate position) {
        spentCoordinates.add(position);
    }

    public void clearSpentCoordinates() {
        for (DataCoordinate spentCoordinate : spentCoordinates) {
            coordinates.remove(spentCoordinate);
        }
        spentCoordinates.clear();
    }

    public void generateFeatures(WorldGenLevel level, ChunkGenerator generator, MeadowGroveGenerationData data, ChunkPos chunkPos, RandomSource random) {
        List<GroveFeatureProvider> providers = activeBiomes.stream().map(b -> b.createSurfaceFeatures(random)).toList();
        for (GroveFeatureProvider provider : providers) {
            provider.generateFeatures(level, generator, data, this, chunkPos, random);
            clearSpentCoordinates();
        }
    }
}
