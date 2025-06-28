package com.smellysleepy.meadow.common.worldgen.structure.grove.old.area;

import com.mojang.datafixers.types.Func;
import com.smellysleepy.meadow.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

import java.util.HashMap;
import java.util.function.Function;

public abstract class SpecialMeadowRegion {

    public final ResourceLocation type;
    protected final Vec2 directionalOffset;
    protected final double size;

    public SpecialMeadowRegion(ResourceLocation type, Vec2 directionalOffset, double size) {
        this.type = type;
        this.directionalOffset = directionalOffset;
        this.size = size;
    }

    public final CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.toString());
        tag.putFloat("directionalOffsetX", directionalOffset.x);
        tag.putFloat("directionalOffsetZ", directionalOffset.y);
        tag.putDouble("size", size);
        return serialize(tag);
    }
    public CompoundTag serialize(CompoundTag tag) {
        return tag;
    };

    public double getDistance(BlockPos groveCenter, int blockX, int blockZ, double localRadius) {
        double x = (groveCenter.getX() + directionalOffset.x * localRadius) - blockX;
        double z = (groveCenter.getZ() + directionalOffset.y * localRadius) - blockZ;
        return x * x + z * z;
    }

    public double getThreshold(double noise, double localRadius) {
        return size * localRadius * getNoiseVariance(noise);
    }

    public abstract double getNoiseVariance(double noise);
}
