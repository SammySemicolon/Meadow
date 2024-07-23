package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public class MineralTreePartType<T extends MineralTreePart> {

    private final ResourceLocation id;
    private final Codec<T> codec;

    public MineralTreePartType(ResourceLocation id, Codec<T> codec) {
        this.id = id;
        this.codec = codec;
    }

    public ResourceLocation getId() {
        return id;
    }
    public Codec<T> getCodec() {
        return codec;
    }
}
