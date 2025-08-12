package com.smellysleepy.meadow.common.worldgen.structure.grove.feature;

import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.MeadowGroveBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataCoordinate;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.MeadowGroveGenerationData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record GroveFeatureProvider(List<GroveFeatureDistributionEntry> features, @Nullable MeadowGroveBiomeType biomeType) {

    public GroveFeatureProvider(List<GroveFeatureDistributionEntry> features, @Nullable MeadowGroveBiomeType biomeType) {
        this.features = List.copyOf(features);
        this.biomeType = biomeType;
    }

    public void generateFeatures(WorldGenLevel level, ChunkGenerator generator, MeadowGroveGenerationData data, GroveFeaturePlacementCache placementCache, ChunkPos chunkPos, RandomSource random) {
        HashMap<DataCoordinate, ResourceKey<ConfiguredFeature<?, ?>>> features = new HashMap<>();
        var featurePool = getFeaturePool();
        int totalFeatures = Mth.floor(featurePool.size());
        Collections.shuffle(featurePool); //TODO: Non-Worldgen-Friendly
        float step = 256f / totalFeatures;
        for (int i = 0; i < totalFeatures; i++) {
            int start = Mth.floor(step * i);
            int stop = Mth.floor(step * (i + 1));
            int index = random.nextInt(start, stop);
            var position = new DataCoordinate(chunkPos, index);
            features.put(position, featurePool.remove(0));
        }
        for (Map.Entry<DataCoordinate, ResourceKey<ConfiguredFeature<?, ?>>> entry : features.entrySet()) {
            var position = entry.getKey();
            if (!data.hasData(position)) {
                continue;
            }
            var dataEntry = data.getData(position);
            var feature = entry.getValue();
            var registryAccess = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
            var holder = registryAccess.getHolder(feature).orElseThrow().value();
            if (biomeType == null || dataEntry.biomeType().equals(biomeType)) {
                var optional = placementCache.getFeaturePosition(position);
                if (optional.isEmpty()) {
                    continue;
                }
                if (holder.place(level, generator, random, optional.get())) {
                    placementCache.consumeFeaturePosition(position);
                }
            }
        }
    }

    private ArrayList<ResourceKey<ConfiguredFeature<?, ?>>> getFeaturePool() {
        return features.stream()
                .flatMap(e -> Stream.of(e.asList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static GroveFeatureProviderBuilder create(MeadowGroveBiomeType biomeType) {
        return new GroveFeatureProviderBuilder(biomeType);
    }

    public static final class GroveFeatureProviderBuilder {

        public final List<GroveFeatureDistributionEntry> features = new ArrayList<>();
        public final MeadowGroveBiomeType biomeType;

        public GroveFeatureProviderBuilder(MeadowGroveBiomeType biomeType) {
            this.biomeType = biomeType;
        }

        public void addFeature(ResourceKey<ConfiguredFeature<?, ?>> feature, int featureCount) {
            features.add(new GroveFeatureDistributionEntry(feature, featureCount));
        }

        public GroveFeatureProvider build() {
            return new GroveFeatureProvider(features, biomeType);
        }
    }

}