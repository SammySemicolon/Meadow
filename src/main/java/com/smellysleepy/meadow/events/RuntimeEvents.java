package com.smellysleepy.meadow.events;

import com.smellysleepy.meadow.common.effect.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber
public class RuntimeEvents {

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        NetheriteFruitEffect.onLivingAttack(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        CopperFruitEffect.onRightClickBlock(event);
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        GoldFruitEffect.increaseDigSpeed(event);
    }
}

