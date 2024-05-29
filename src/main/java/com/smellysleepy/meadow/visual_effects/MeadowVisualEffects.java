package com.smellysleepy.meadow.visual_effects;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.*;
import team.lodestar.lodestone.systems.particle.world.options.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class MeadowVisualEffects {

    public static<T extends WorldParticleOptions> ParticleEffectSpawner<T> meadowLeaves(Level level, Vec3 pos, T options) {
        var rand = level.getRandom();

        float scale = RandomHelper.randomBetween(rand, 0.075F, 0.1F);
        int lifetime = RandomHelper.randomBetween(rand, 250, 300);

        var spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.01f, 0.025f), nextFloat(rand, 0.1f, 0.125f))
                .randomSpinOffset(rand).build();
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
                .setTransparencyData(transparencyData)
                .setSpinData(spinData)
                .setScaleData(scaleData)
                .setLifetime(lifetime)
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .addTickActor(fall);

        return new ParticleEffectSpawner<>(level, pos, worldParticleBuilder);
    }
}
