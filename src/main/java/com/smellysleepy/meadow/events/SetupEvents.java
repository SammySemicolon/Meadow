package com.smellysleepy.meadow.events;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.entity.MooMooCow;
import com.smellysleepy.meadow.registry.common.MeadowEntityRegistry;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MeadowMod.MEADOW)
public class SetupEvents {


    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        MeadowEntityRegistry.createDefaultAttributes(event);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        MeadowEntityRegistry.registerSpawnPlacements(event);
    }
}