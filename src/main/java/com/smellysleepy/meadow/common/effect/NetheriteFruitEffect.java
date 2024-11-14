package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class NetheriteFruitEffect extends MobEffect {
    public NetheriteFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(101, 78, 69)));
        addAttributeModifier(Attributes.ARMOR, "2b373e4c-3fec-457e-9831-0a2e26dfbdb1", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "057973de-5936-459d-98da-a5225ac40e95", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "3a65ec92-843a-4d9f-9d05-5321b8197724", 0.2f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity().hasEffect(MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT.get())) {
            event.setCanceled(true);
        }
    }
}