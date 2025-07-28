package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.client.renderer.entity.*;
import com.smellysleepy.meadow.common.entity.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.systems.entity.*;
import team.lodestar.lodestone.systems.entityrenderer.*;

import java.util.function.Supplier;

public class MeadowEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MeadowMod.MEADOW);

    public static final Supplier<EntityType<LodestoneBoatEntity>> ASPEN_BOAT = ENTITY_TYPES.register("aspen_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, MeadowItemRegistry.ASPEN_BOAT), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("aspen_boat").toString()));

    public static final Supplier<EntityType<LodestoneBoatEntity>> CALCIFIED_BOAT = ENTITY_TYPES.register("calcified_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, MeadowItemRegistry.ASPEN_BOAT), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("calcified_boat").toString()));

    public static final Supplier<EntityType<MooMooCow>> MOO_MOO = ENTITY_TYPES.register("moo_moo",
            () -> EntityType.Builder.of(MooMooCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10)
                    .build(MeadowMod.meadowModPath("moo_moo").toString()));

    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(MOO_MOO.get(), MooMooCow.createAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(MeadowEntityRegistry.MOO_MOO.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MooMooCow::checkMooMooSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @EventBusSubscriber(modid = MeadowMod.MEADOW, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(MeadowEntityRegistry.ASPEN_BOAT.get(), (c) -> new LodestoneBoatRenderer(c, MeadowMod.meadowModPath("textures/entity/boat/aspen.png"), false));
            EntityRenderers.register(MeadowEntityRegistry.CALCIFIED_BOAT.get(), (c) -> new LodestoneBoatRenderer(c, MeadowMod.meadowModPath("textures/entity/boat/aspen.png"), false));
            EntityRenderers.register(MeadowEntityRegistry.MOO_MOO.get(), MooMooCowRenderer::new);
        }
    }
}