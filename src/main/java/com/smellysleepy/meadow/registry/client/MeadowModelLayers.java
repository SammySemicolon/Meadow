package com.smellysleepy.meadow.registry.client;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.client.model.entity.*;
import net.minecraft.client.model.geom.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MeadowMod.MEADOW, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MeadowModelLayers {

    public static final ModelLayerLocation MOO_MOO = new ModelLayerLocation(MeadowMod.meadowModPath("moo_moo"), "main");

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MOO_MOO, MooMooModel::createBodyLayer);
    }
}