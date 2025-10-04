package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class EmeraldFruitEffect extends MobEffect {
    public EmeraldFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(135, 255, 101)));
        ResourceLocation id = MeadowMod.meadowPath("emerald_fruit_effect");
        addAttributeModifier(Attributes.LUCK, id, 1, AttributeModifier.Operation.ADD_VALUE);
    }
}