package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class AmethystFruitEffect extends MobEffect {
    public AmethystFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(238, 128, 255)));
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static boolean blockVibrations(LivingEntity entity) {
        var effect = MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT.get();
        if (entity.hasEffect(effect)) {
            var instance = entity.getEffect(effect);
            return entity.getRandom().nextFloat() < 0.4f + (instance.getAmplifier()) * 0.2f;
        }
        return false;
    }
}