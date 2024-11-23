package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
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
        var effect = MeadowMobEffectRegistry.AMETHYST_FRUIT_EFFECT.get();
        if (entity.hasEffect(effect)) {
            var instance = entity.getEffect(effect);
            if (entity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                Item cooldownItem = MineralFloraRegistry.AMETHYST_FLORA.fruitItem.get();
                if (cooldowns.isOnCooldown(cooldownItem) && cooldowns.getCooldownPercent(cooldownItem, 0) <= 0.9f) {
                    return false;
                }
                int cooldown = 40 / (instance.getAmplifier()+1);
                cooldowns.addCooldown(cooldownItem, cooldown);
            }
            return true;
        }
        return false;
    }
}