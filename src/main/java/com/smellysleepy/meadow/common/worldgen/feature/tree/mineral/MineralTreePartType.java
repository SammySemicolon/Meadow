package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public class MineralTreePartType<T extends MineralTreePart> {

    private final ResourceLocation id;
    private final MapCodec<T> codec;

    public MineralTreePartType(ResourceLocation id, MapCodec<T> codec) {
        this.id = id;
        this.codec = codec;
    }

    public ResourceLocation getId() {
        return id;
    }
    public MapCodec<T> getCodec() {
        return codec;
    }
}
