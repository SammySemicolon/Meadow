package com.smellysleepy.meadow.events;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        MeadowParticleRegistry.registerParticleFactory(event);
    }
}