package com.smellysleepy.meadow.registry.worldgen;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.worldgen.placement.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import team.lodestar.lodestone.systems.worldgen.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MeadowPlacementFillerRegistry {

    public static PlacementModifierType<NoiseThresholdCountWithOffsetPlacement> NOISE_WITH_OFFSET;

    @SubscribeEvent
    public static void registerTypes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NOISE_WITH_OFFSET = register("meadow:noise_with_offset", NoiseThresholdCountWithOffsetPlacement.CODEC);
        });
    }

    public static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, name, () -> codec);
    }
}