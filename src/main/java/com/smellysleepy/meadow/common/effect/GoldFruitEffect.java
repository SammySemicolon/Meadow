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
        addAttributeModifier(Attributes.ATTACK_SPEED, "750f9114-6436-445f-8136-dc74885057be", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player.hasEffect(MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT.get())) {
            final int amplifier = player.getEffect(MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT.get()).getAmplifier()+1;
            event.setNewSpeed(event.getOriginalSpeed() * (1 + 0.2f * amplifier));
        }
    }
}