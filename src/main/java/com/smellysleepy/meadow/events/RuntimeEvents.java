package com.smellysleepy.meadow.events;

import com.smellysleepy.meadow.common.effect.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class RuntimeEvents {

    @SubscribeEvent
    public static void handleIncomingDamage(LivingIncomingDamageEvent event) {
        NetheriteFruitEffect.preventFireDamage(event);
    }
}

