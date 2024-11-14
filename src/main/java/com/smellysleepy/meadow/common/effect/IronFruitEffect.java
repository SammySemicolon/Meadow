package com.smellysleepy.meadow.common.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class IronFruitEffect extends MobEffect {
    public IronFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(213, 200, 200)));
        addAttributeModifier(Attributes.ARMOR, "9c21c537-2a4a-49b7-bba1-bd78afd8fd4b", 2, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "8993a466-3f17-4f6c-adba-c9a00c218093", 1, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}