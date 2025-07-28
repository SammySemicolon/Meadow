package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class DiamondFruitEffect extends MobEffect {
    public DiamondFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(128, 255, 225)));
    }

    public static int getFortuneBonus(LivingEntity entity) {
        var effect = MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT;
        var instance = entity.getEffect(effect);
        return instance != null ? instance.getAmplifier()+1 : 0;
    }
}