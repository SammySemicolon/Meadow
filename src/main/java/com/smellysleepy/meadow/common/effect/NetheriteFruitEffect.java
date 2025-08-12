package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class NetheriteFruitEffect extends MobEffect {
    public NetheriteFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(101, 78, 69)));
        ResourceLocation id = MeadowMod.meadowPath("netherite_fruit");
        addAttributeModifier(Attributes.ARMOR, id, 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, id, 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, id, 0.2f, AttributeModifier.Operation.ADD_VALUE);
    }

    public static void preventFireDamage(LivingIncomingDamageEvent event) {
        if (!event.getSource().is(DamageTypeTags.IS_FIRE)) {
            return;
        }
        if (event.getEntity().hasEffect(MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT)) {
            event.setCanceled(true);
        }
    }
}