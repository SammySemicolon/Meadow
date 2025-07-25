package com.smellysleepy.meadow.visual_effects;

import com.smellysleepy.meadow.registry.common.*;
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
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;

import static net.minecraft.util.Mth.*;

public class StrangeFloraParticleEffects {

    public static ParticleEffectSpawner mineralFloraShine(Level level, Vec3 pos) {
        var rand = level.getRandom();

        var pitchData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.01f, 0.02f), nextFloat(rand, 0.05f, 0.125f), 0f)
                .randomSpinOffset(rand).build();
        var yawData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.01f, 0.02f), nextFloat(rand, 0.05f, 0.125f), 0f)
                .randomSpinOffset(rand).build();

        var options = new WorldParticleOptions(MeadowParticleRegistry.SHINY_GLIMMER);
        return mineralFloraShine(level, pos, options);
    }

    public static ParticleEffectSpawner mineralFloraShine(Level level, Vec3 pos, WorldParticleOptions options) {
        var rand = level.getRandom();

        float scale = RandomHelper.randomBetween(rand, 0.06F, 0.09F);
        int lifetime = RandomHelper.randomBetween(rand, 100, 120);
        var spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.01f, 0.025f), nextFloat(rand, 0.1f, 0.125f), 0f)
                .randomSpinOffset(rand).build();
        var scaleData = GenericParticleData.create(scale * 0.5f, scale * 1.25f, scale * 0.75f).build();
        var transparencyData = GenericParticleData.create(0f, 1f, 0.1f).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build();

        float colorScalar = 0.6F + rand.nextFloat() * 0.4F;
        Color color = new Color(colorScalar, colorScalar, colorScalar);

        var worldParticleBuilder = WorldParticleBuilder.create(options)
                .setTransparencyData(transparencyData)
                .setSpinData(spinData)
                .setScaleData(scaleData)
                .setLifetime(lifetime)
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setColorData(ColorParticleData.create(color, color).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE);

        return new ParticleEffectSpawner(level, pos, worldParticleBuilder);
    }
}
