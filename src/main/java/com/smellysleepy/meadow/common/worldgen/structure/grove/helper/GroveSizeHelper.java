package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.smellysleepy.meadow.common.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.systems.easing.*;

public class GroveSizeHelper {

    public static int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.QUAD_IN, 0.05f, localHeight, delta);
    }

    public static int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, localDepth, delta);
    }

    public static int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float noiseFrequency, double localValue, double delta) {
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos, 100000, noiseFrequency); // Random Noise based on position
        double deltaScalar = easing.clamped(depthNoise / 2f, 0.7f, 1.4f); //Slight delta scaling based on noise
        double carverDelta = 1 - delta * deltaScalar; //Carver delta based on distance from center and delta scaling, approaches 1 towards the end of the grove
        float expoOut = Easing.EXPO_OUT.clamped(carverDelta, 0, 1); // Main easing, falls off quickly towards the end of the grove
        float bounceInOut = Easing.BOUNCE_IN_OUT.clamped(carverDelta * 1.5f, 0, 1); // Bounce effect to add some organic feel to the height
        float sum = expoOut * 0.6f + bounceInOut * 0.4f; //Combining different easing functions for a more organic feel
        if (sum > 1) {
            sum = 1 - (sum - 1) * 0.5f; // Clamping the sum to avoid excessive height
        }
        return (int) (sum * localValue);
    }
}
