package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class GoldFruitEffect extends MobEffect {
    public GoldFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(255, 212, 75)));
    }

    public static int getLootingBonus(LivingEntity entity) {
        var effect = MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT;
        var instance = entity.getEffect(effect);
        return instance != null ? instance.getAmplifier()+1 : 0;
    }
}