package com.smellysleepy.meadow.common.worldgen.structure.grove.area;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import team.lodestar.lodestone.systems.easing.Easing;

public class LakeRegion extends SpecialMeadowRegion {
    public static final ResourceLocation TYPE = addType(MeadowMod.meadowModPath("lake"), LakeRegion::deserialize);

    private final double depth;

    public LakeRegion(Vec2 directionalOffset, double size, double depth) {
        super(TYPE, directionalOffset, size);
        this.depth = depth;
    }

    public static LakeRegion deserialize(CompoundTag tag) {
        return new LakeRegion(
                new Vec2(tag.getFloat("directionalOffsetX"), tag.getFloat("directionalOffsetZ")),
                tag.getDouble("size"),
                tag.getDouble("depth"));
    }

    @Override
    public CompoundTag serialize(CompoundTag tag) {
        tag.putDouble("depth", depth);
        return tag;
    }

    @Override
    public double getNoiseVariance(double noise) {
        return Easing.SINE_IN_OUT.clamped(noise, 0.8f, 1f);
    }

    public double getDepth() {
        return depth;
    }
}
