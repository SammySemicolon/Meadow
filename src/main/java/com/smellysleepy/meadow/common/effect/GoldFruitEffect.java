package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class GoldFruitEffect extends MobEffect {
    public GoldFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(255, 212, 75)));
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static int getLootingBonus(LivingEntity entity) {
        var effect = MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT.get();
        if (entity.hasEffect(effect)) {
            var instance = entity.getEffect(effect);
            return instance.getAmplifier()+1;
        }
        return 0;
    }
}