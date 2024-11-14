package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.level.*;
import net.minecraftforge.eventbus.api.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.inventory.*;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class DiamondFruitEffect extends MobEffect {
    public DiamondFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(128, 255, 225)));
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static int getFortuneBonus(LivingEntity entity) {
        var effect = MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT.get();
        if (entity.hasEffect(effect)) {
            var instance = entity.getEffect(effect);
            return instance.getAmplifier()+1;
        }
        return 0;
    }
}