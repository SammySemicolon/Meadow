package com.smellysleepy.meadow.common.worldgen.structure.grove;

import net.minecraft.core.*;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.helpers.EasingHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public interface MeadowGroveShape {

    MeadowGroveShape CIRCLE = (center, point, size) -> 1f;
    MeadowGroveShape FUNNY = (center, point, size) -> {
        double seed = ((20 + center.getX() + center.getY() + center.getZ()) << 2) * 12.345;

        double x = (point.getX() - center.getX());
        double z = (point.getZ() - center.getZ());

        double angle = Math.atan2(-x, z) * 2;

        double distance = Math.sqrt(x*x+z*z);
        double commitment = (seed * 0.2f) % 0.4f + (seed * 0.4f) % 0.6f;
        double delta = Mth.clamp(distance / size * commitment, 0, 1);

        double one = seed % 8, oneRate = 1 + seed % 1.5f; seed *= 1.01f;
        double two = (seed * 2) % 16, twoRate = 1 + seed % 0.5f; seed *= 1.01f;
        double three = seed % 6, threeRate = 2 + seed % 3f; seed *= 1.01f;
        double four = seed % 3, fourRate = 3 + seed % 4f; seed *= 1.01f;
        double five = seed % 2, fiveRate = 4 + seed % 2f;

        double xDistance = one * Math.pow(Math.sin(oneRate * angle), 3.0);
        double zDistance = (two * Math.cos(twoRate * angle) - three * Math.cos(threeRate * angle) - four * Math.cos(fourRate * angle) - five * Math.cos(fiveRate * angle));
        double shape = Math.abs(xDistance) / one + Math.abs(zDistance) / (two + three + four + five);
        return EasingHelper.weightedEasingLerp(Easing.SINE_IN_OUT, delta, 1f, shape);
    };

    double distSqr(Vec3i center, Vec3i point, double size);

}