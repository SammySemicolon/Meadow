package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;

import static team.lodestar.lodestone.LodestoneLib.LOGGER;

public class MeadowGroveGenerationData {

    public static final Codec<MeadowGroveGenerationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("groveCenter").forGetter(MeadowGroveGenerationData::getGroveCenter),
            Codec.INT.fieldOf("groveRadius").forGetter(MeadowGroveGenerationData::getGroveRadius),
            Codec.INT.fieldOf("groveHeight").forGetter(MeadowGroveGenerationData::getGroveHeight),
            Codec.INT.fieldOf("groveDepth").forGetter(MeadowGroveGenerationData::getGroveDepth)
    ).apply(instance, MeadowGroveGenerationData::new));

    private final BlockPos groveCenter;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    public MeadowGroveGenerationData(BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth) {
        this.groveCenter = groveCenter;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveDepth;
    }

    public BlockPos getGroveCenter() {
        return groveCenter;
    }

    public int getGroveRadius() {
        return groveRadius;
    }

    public int getGroveHeight() {
        return groveHeight;
    }

    public int getGroveDepth() {
        return groveDepth;
    }

    public void save(CompoundTag tag) {
        MeadowGroveGenerationData.CODEC
                .encodeStart(NbtOps.INSTANCE, this)
                .resultOrPartial(LOGGER::error)
                .ifPresent(p -> tag.put("groveData", p));
    }

    public static MeadowGroveGenerationData load(CompoundTag tag) {
        return MeadowGroveGenerationData.CODEC.parse(NbtOps.INSTANCE, tag.get("groveData")).resultOrPartial(LOGGER::error).orElse(null);
    }
}
