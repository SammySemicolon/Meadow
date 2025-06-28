package com.smellysleepy.meadow.common.worldgen.structure.grove.old.area;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;
import team.lodestar.lodestone.systems.easing.Easing;

public class LakeRegion extends SpecialMeadowRegion {

    private final double lakeDepth;
    private final double surfaceLevel;
    public LakeRegion(Vec2 directionalOffset, double size, double lakeDepth, double surfaceLevel) {
        super(MeadowRegionTypes.LAKE, directionalOffset, size);
        this.lakeDepth = lakeDepth;
        this.surfaceLevel = surfaceLevel;
    }

    public static LakeRegion deserialize(CompoundTag tag) {
        return new LakeRegion(
                new Vec2(tag.getFloat("directionalOffsetX"), tag.getFloat("directionalOffsetZ")),
                tag.getDouble("size"),
                tag.getDouble("lakeDepth"),
                tag.getDouble("surfaceLevel"));
    }

    @Override
    public CompoundTag serialize(CompoundTag tag) {
        tag.putDouble("lakeDepth", lakeDepth);
        tag.putDouble("surfaceLevel", surfaceLevel);
        return tag;
    }

    @Override
    public double getNoiseVariance(double noise) {
        return Easing.SINE_IN_OUT.clamped(noise, 0.8f, 1f);
    }

    public double getLakeDepth() {
        return lakeDepth;
    }

    public double getSurfaceLevel() {
        return surfaceLevel;
    }

    public double getWaterLevel() {
        return surfaceLevel+0.1f;
    }
}
