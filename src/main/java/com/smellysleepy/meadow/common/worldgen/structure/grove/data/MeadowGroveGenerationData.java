package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.helper.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.synth.*;

import java.util.*;
import java.util.concurrent.*;

public class MeadowGroveGenerationData {

    public static final Codec<MeadowGroveGenerationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(DataCoordinate.CODEC, DataEntry.CODEC).fieldOf("data").forGetter(MeadowGroveGenerationData::getData),
            Codec.unboundedMap(DataCoordinate.CODEC, Codec.list(DataEntry.CODEC)).fieldOf("byChunk").forGetter(data -> data.byChunk)
    ).apply(instance, MeadowGroveGenerationData::new));

    private final ConcurrentHashMap<DataCoordinate, DataEntry> data = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<DataCoordinate, List<DataEntry>> byChunk = new ConcurrentHashMap<>();
    private final ArrayList<DataEntry> entries = new ArrayList<>();

    public MeadowGroveGenerationData() {
    }

    public MeadowGroveGenerationData(Map<DataCoordinate, DataEntry> data, Map<DataCoordinate, List<DataEntry>> byChunk) {
        this.data.putAll(data);
    }

    public ArrayList<DataEntry> getEntries() {
        return entries;
    }

    public Map<DataCoordinate, DataEntry> getData() {
        return data;
    }

    public DataEntry getData(BlockPos pos) {
        return getData(new DataCoordinate(pos));
    }

    public DataEntry getData(DataCoordinate dataCoordinate) {
        if (!data.containsKey(dataCoordinate)) {
            throw new IllegalStateException("No data found for coordinate: " + dataCoordinate + ". This is a keter level disaster.");
        }
        return data.get(dataCoordinate);
    }

    public boolean hasData(DataCoordinate dataCoordinate) {
        return data.containsKey(dataCoordinate);
    }

    public List<DataEntry> getData(ChunkPos pos) {
        DataCoordinate key = new DataCoordinate(pos);
        if (!byChunk.containsKey(key)) {
            return Collections.emptyList();
        }
        return byChunk.get(key);
    }

    protected void addData(BlockPos pos, DataEntry entry) {
        DataCoordinate key = new DataCoordinate(pos);
        data.put(key, entry);
        DataCoordinate chunkCoordinate = key.asChunkCoordinate();
        byChunk.computeIfAbsent(chunkCoordinate, k -> new ArrayList<>()).add(entry);
        entries.add(entry);
    }

    public void propagate(MeadowGroveGenerationConfiguration config) {
        GroveInclineHelper.propagateInclineData(config);
        GroveInclineHelper.countNeighbors(config);
    }

    public void compute(MeadowGroveGenerationConfiguration config, ImprovedNoise noiseSampler, ChunkPos chunkPos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos groveCenter = config.getGroveCenter();
        int groveRadius = config.getGroveRadius();
        int groveHeight = config.getGroveHeight();
        int groveDepth = config.getGroveDepth();
        for (int i = 0; i < 256; i++) {
            int blockX = chunkPos.getBlockX(i % 16);
            int blockY = groveCenter.getY();
            int blockZ = chunkPos.getBlockZ(i / 16);
            mutable.set(blockX, blockY, blockZ);
            double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.1f);

            int localRadius = (int) Mth.clampedLerp(groveRadius * 0.4f, groveRadius * 1.4f, noise);
            int localHeight = (int) Mth.clampedLerp(groveHeight * 0.6f, groveHeight, noise);
            int localDepth = (int) Mth.clampedLerp(groveDepth * 0.6f, groveDepth, noise);

            if (!mutable.closerThan(groveCenter, localRadius)) {
                continue;
            }
            double distance = Math.sqrt(mutable.distSqr(groveCenter));
            double delta = distance / groveRadius;
            if (delta > 0.6f) {
                //We reduce some of the erratic nature the grove has when nearing the outer area by removing the variation in distance variation
                //This helps keep the terrain connected and create less lone areas
                localRadius = (int) Mth.lerp((delta - 0.6f) / 0.4f, localRadius, groveRadius);
            }
            double localDelta = distance / localRadius;

            double offsetNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.2f);
            int centerOffset = (int) Mth.lerp(offsetNoise, -2, 2);
            int height = GroveSizeHelper.getGroveHeight(noiseSampler, mutable, localHeight, localDelta);
            int depth = GroveSizeHelper.getGroveDepth(noiseSampler, mutable, localDepth, localDelta);
            float upperOpeningSize = localHeight * 0.3f;
            float lowerOpeningSize = localDepth * 0.3f;
            int openHeight = Mth.floor(Math.max(height - upperOpeningSize, 0));
            int openDepth = Mth.floor(Math.max(depth - lowerOpeningSize, 0));

            if (delta < 0.7f) {
                //We gently fuse the opening from both ends
                if (openHeight > 0 || openDepth > 0) {
                    if (openHeight < upperOpeningSize && openDepth < lowerOpeningSize) {
                        float upperDelta = openHeight / upperOpeningSize;
                        float lowerDelta = openDepth / lowerOpeningSize;
                        openHeight = Mth.floor(openHeight * lowerDelta);
                        openDepth = Mth.floor(openDepth * upperDelta);
                    }
                }
            } else {
                //Once the terrain is far out enough, we take on a much more aggressive approach to fusing the air pockets
                if (openHeight == 0) {
                    openDepth = 0;
                } else if (openDepth == 0) {
                    openHeight = 0;
                }
            }

            boolean isOpen = openHeight > 0 && openDepth > 0;

            var biomeData = GroveBiomeHelper.getBiomeType(config, noiseSampler, blockX, blockZ, localDelta);
            var biomeType = biomeData.getFirst();
            double biomeInfluence = biomeData.getSecond();
            Optional<InclineData> inclineData;
            if (isOpen) {
                inclineData = GroveInclineHelper.getInclineData(config, noiseSampler, blockX, blockZ, localDelta);
            }
            else {
                inclineData = Optional.empty();
            }

            addData(mutable, new DataEntry(blockX, blockZ, biomeType, biomeInfluence, centerOffset, height, depth, openHeight, openDepth, inclineData.orElse(null)));
        }
    }
}
