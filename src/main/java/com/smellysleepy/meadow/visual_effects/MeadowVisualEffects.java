package com.smellysleepy.meadow.visual_effects;

import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.type.*;

import java.util.function.*;

import static net.minecraft.util.Mth.nextFloat;

public class MeadowVisualEffects {

    public static ParticleEffectSpawner<WorldParticleBuilder> meadowLeaves(Level level, Vec3 pos, Supplier<LodestoneParticleType> particle) {
        var rand = level.getRandom();

        float scale = RandomHelper.randomBetween(rand, 0.075F, 0.1F);
        int lifetime = RandomHelper.randomBetween(rand, 200, 300);

        var spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.01f, 0.025f), nextFloat(rand, 0.1f, 0.125f))
                .randomSpinOffset(rand).build();
        var scaleData = GenericParticleData.create(scale, scale * 0.75f).build();
        var transparencyData = GenericParticleData.create(0f, 1f, 0.2f).setEasing(Easing.QUARTIC_OUT, Easing.SINE_IN_OUT).build();
        final Consumer<LodestoneWorldParticleActor> fall = p -> {
            final Vec3 motion = p.getParticleSpeed();
            p.setParticleMotion(motion.x, (motion.y - 0.00025) * 0.999f, motion.z);
        };
        var worldParticleBuilder = WorldParticleBuilder.create(particle)
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
