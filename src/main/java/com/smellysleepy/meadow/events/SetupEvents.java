package com.smellysleepy.meadow.events;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.entity.MooMooCow;
import com.smellysleepy.meadow.registry.common.MeadowEntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.atn.SemanticContext;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MeadowMod.MEADOW)
public class SetupEvents {
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(MeadowEntityRegistry.MOO_MOO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MooMooCow::checkMooMooSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}