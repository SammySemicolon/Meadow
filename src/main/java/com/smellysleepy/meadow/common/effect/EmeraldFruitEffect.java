package com.smellysleepy.meadow.common.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class EmeraldFruitEffect extends MobEffect {
    public EmeraldFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(135, 255, 101)));
        addAttributeModifier(Attributes.LUCK, "57bfdd6b-ac08-4ab6-86d8-6f5ceb1e92c1", 1, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}