package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;

public record DataCoordinate(int x, int z) {

    public static final Codec<DataCoordinate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(DataCoordinate::x),
            Codec.INT.fieldOf("z").forGetter(DataCoordinate::z)
    ).apply(instance, DataCoordinate::new));

    public DataCoordinate(BlockPos pos) {
        this(pos.getX(), pos.getZ());
    }
    public DataCoordinate(ChunkPos pos) {
        this(pos.x, pos.z);
    }

    public DataCoordinate asChunkCoordinate() {
        return new DataCoordinate(x >> 4, z >> 4);
    }
}
