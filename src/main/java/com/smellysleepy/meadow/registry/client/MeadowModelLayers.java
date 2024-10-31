package com.smellysleepy.meadow.registry.client;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.client.model.entity.*;
import net.minecraft.client.model.geom.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(modid = MeadowMod.MEADOW, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MeadowModelLayers {

    public static final ModelLayerLocation MOO_MOO = new ModelLayerLocation(MeadowMod.meadowModPath("moo_moo"), "main");

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MOO_MOO, MooMooModel::createBodyLayer);
    }
}