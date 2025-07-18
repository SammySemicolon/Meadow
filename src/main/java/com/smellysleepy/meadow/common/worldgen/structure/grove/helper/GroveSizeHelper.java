package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.smellysleepy.meadow.common.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.systems.easing.*;

public class GroveSizeHelper {

    public static int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        int height = getVerticalSize(noiseSampler, pos, Easing.QUARTIC_IN, 0.05f, localHeight, delta);
        if (delta > 0.6f) {
            //We want to create larger clumps of terrain further out, so we interpolate to a larger scale in terms of noise
            int outerHeight = getVerticalSize(noiseSampler, pos, Easing.SINE_IN, 0.01f, localHeight, delta);
            double outerDelta = (delta - 0.6f) / 0.4f;
            height = Mth.floor(Mth.lerp(outerDelta, height, outerHeight));
        }
        return height;
    }

    public static int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        int depth = getVerticalSize(noiseSampler, pos, Easing.CUBIC_IN, 0.025f, localDepth, delta);
        if (delta > 0.6f) {
            //We want to create larger clumps of terrain further out, so we interpolate to a larger scale in terms of noise
            int outerDepth = getVerticalSize(noiseSampler, pos, Easing.SINE_IN, 0.0025f, localDepth, delta);
            double outerDelta = (delta - 0.6f) / 0.4f;
            depth = Mth.floor(Mth.lerp(outerDelta, depth, outerDepth));
        }
        return depth;
    }

    public static int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float noiseFrequency, double localValue, double delta) {
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos, 100000, noiseFrequency); // Random Noise based on position
        double deltaScalar = easing.clamped(depthNoise, 0.9f, 1.4f); //Slight delta scaling based on noise
        double carverDelta = 1 - delta * deltaScalar; //Carver delta based on distance from center and delta scaling, approaches 1 towards the end of the grove
        float sineOut = Easing.SINE_OUT.clamped(carverDelta, 0, 1); // Main easing, falls off quickly towards the end of the grove
        float bounceInOut = Easing.BOUNCE_IN_OUT.clamped(carverDelta * 1.5f, 0, 1); // Bounce effect to add some organic feel to the height
        float sum = sineOut * 0.6f + bounceInOut * 0.4f; //Combining different easing functions for a more organic feel
        if (sum > 1) {
            sum = 1;
        }
        return (int) (sum * localValue);
    }
}
