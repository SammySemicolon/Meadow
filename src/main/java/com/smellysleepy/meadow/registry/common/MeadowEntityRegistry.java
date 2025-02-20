package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.client.renderer.entity.*;
import com.smellysleepy.meadow.common.entity.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.entity.*;
import team.lodestar.lodestone.systems.entityrenderer.*;

@Mod.EventBusSubscriber(modid = MeadowMod.MEADOW, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MeadowEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MeadowMod.MEADOW);

    public static final RegistryObject<EntityType<LodestoneBoatEntity>> ASPEN_BOAT = ENTITY_TYPES.register("aspen_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, MeadowItemRegistry.ASPEN_BOAT), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("aspen_boat").toString()));

    public static final RegistryObject<EntityType<LodestoneBoatEntity>> CALCIFIED_BOAT = ENTITY_TYPES.register("calcified_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, MeadowItemRegistry.ASPEN_BOAT), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("calcified_boat").toString()));

    public static final RegistryObject<EntityType<MooMooCow>> MOO_MOO = ENTITY_TYPES.register("moo_moo",
            () -> EntityType.Builder.of(MooMooCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("moo_moo").toString()));

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(MOO_MOO.get(), MooMooCow.createAttributes().build());
    }

    @Mod.EventBusSubscriber(modid = MeadowMod.MEADOW, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(MeadowEntityRegistry.ASPEN_BOAT.get(), (c) -> new LodestoneBoatRenderer(c, MeadowMod.meadowModPath("textures/entity/boat/aspen.png"), false));
            EntityRenderers.register(MeadowEntityRegistry.CALCIFIED_BOAT.get(), (c) -> new LodestoneBoatRenderer(c, MeadowMod.meadowModPath("textures/entity/boat/aspen.png"), false));
            EntityRenderers.register(MeadowEntityRegistry.MOO_MOO.get(), MooMooCowRenderer::new);
        }
    }
}