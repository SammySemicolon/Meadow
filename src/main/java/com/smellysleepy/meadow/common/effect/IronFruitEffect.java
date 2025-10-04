package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class IronFruitEffect extends MobEffect {
    public IronFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(213, 200, 200)));
        ResourceLocation id = MeadowMod.meadowPath("iron_fruit_effect");
        addAttributeModifier(Attributes.ARMOR, id, 2, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, id, 1, AttributeModifier.Operation.ADD_VALUE);
    }
}