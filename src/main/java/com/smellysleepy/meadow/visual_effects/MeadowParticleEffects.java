package com.smellysleepy.meadow.visual_effects;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class MeadowParticleEffects {

    public static ParticleEffectSpawner fallingLeaves(Level level, Vec3 pos, LodestoneWorldParticleType particleType) {
        return fallingLeaves(level, pos, new WorldParticleOptions(particleType).setBehavior(new SparkBehaviorComponent()));
    }
    public static ParticleEffectSpawner fallingLeaves(Level level, Vec3 pos, WorldParticleOptions options) {
        var rand = level.getRandom();

        float scale = RandomHelper.randomBetween(rand, 0.1F, 0.2F);
        int lifetime = RandomHelper.randomBetween(rand, 250, 300);

        var scaleData = GenericParticleData.create(scale, scale * 0.75f).build();
        var transparencyData = GenericParticleData.create(0f, 1f, 0.4f).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build();

        float direction = rand.nextFloat();
        float gravity = RandomHelper.randomBetween(rand, 0.00025f, 0.00075f);
        int flutterOffset = RandomHelper.randomBetween(rand, 0, 160);
        final Consumer<LodestoneWorldParticle> fall = p -> {
            final int particleLifetime = p.getLifetime();
            float f = (float) (particleLifetime - p.getAge());
            float f1 = Math.min(f / particleLifetime, 1.0F);

            final double radians = Math.toRadians((direction * 60.0F));
            double x = Math.cos(radians) * 2.0D * Math.pow(f1, 1.25D) * 0.00025F;
            float y = -gravity * 1.5f;
            double z = Math.sin(radians) * 2.0D * Math.pow(f1, 1.25D) * 0.00025F;
            if (f < particleLifetime * 0.9f) {
                float flutterTimer = (f + flutterOffset) % 80;
                if (flutterTimer < 40 || flutterTimer > 120) {
                    float flutterScalar = (1 + abs(flutterTimer - 80) / 40f) * 0.75f;
                    y = -gravity + gravity * flutterScalar;
                }
            }

            float scalar = 0.98f;
            p.setParticleSpeed(p.getParticleSpeed().add(x, y, z).multiply(scalar, scalar, scalar));
        };
        var worldParticleBuilder = WorldParticleBuilder.create(options)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setTransparencyData(transparencyData)
                .setScaleData(scaleData)
                .setLifetime(lifetime)
                .setNaturalLighting()
                .addTickActor(fall);

        return new ParticleEffectSpawner(level, pos, worldParticleBuilder);
    }
    public static ParticleEffectSpawner pearlflowerShine(Level level, Vec3 pos, LodestoneWorldParticleType particleType) {
        return pearlflowerShine(level, pos, new WorldParticleOptions(particleType));
    }
    public static ParticleEffectSpawner pearlflowerShine(Level level, Vec3 pos, WorldParticleOptions options) {
        var rand = level.getRandom();

        float scale = RandomHelper.randomBetween(rand, 0.075F, 0.15F);
        int lifetime = RandomHelper.randomBetween(rand, 150, 200);

        var scaleData = GenericParticleData.create(scale, scale * 0.75f).build();
        var transparencyData = GenericParticleData.create(0f, 1f, 0.1f).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build();
        var spinData = SpinParticleData.createRandomDirection(rand, 0f, 0.1f, 0f).randomSpinOffset(rand).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build();

        var colorData = ColorParticleData.create(new Color(233, 195, 41), new Color(195, 118, 85)).build();
        var worldParticleBuilder = WorldParticleBuilder.create(options)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                .setTransparencyData(transparencyData)
                .setScaleData(scaleData)
                .setColorData(colorData)
                .setSpinData(spinData)
                .setRandomMotion(0.002f, 0.005f)
                .setLifetime(lifetime);

        return new ParticleEffectSpawner(level, pos, worldParticleBuilder);
    }
}
