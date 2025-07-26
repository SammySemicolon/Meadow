package com.smellysleepy.meadow.common.effect;

import net.minecraft.world.effect.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class CopperFruitEffect extends MobEffect {
    public CopperFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(243, 104, 50)));
    }
}