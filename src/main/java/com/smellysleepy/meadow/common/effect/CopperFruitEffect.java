package com.smellysleepy.meadow.common.effect;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.placementassistance.*;

import java.awt.*;

public class CopperFruitEffect extends MobEffect {
    public CopperFruitEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(243, 104, 50)));
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var level = event.getLevel();
        if (!level.isClientSide()) {
            var entity = event.getEntity();
            if (entity.hasEffect(MeadowMobEffectRegistry.COPPER_FRUIT_EFFECT.get())) {
                var pos = event.getPos();
                var state = level.getBlockState(pos);
                if (state.getBlock() instanceof WeatheringCopper copper) {
                    copper.getNext(state).ifPresent(s -> {
                        level.setBlock(pos, s, 3);
                        entity.swing(event.getHand(), true);
                    });
                }
            }
        }
    }
}