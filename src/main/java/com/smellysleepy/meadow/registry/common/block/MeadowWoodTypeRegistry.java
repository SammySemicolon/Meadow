package com.smellysleepy.meadow.registry.common.block;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MeadowWoodTypeRegistry {

    public static final WoodType ASPEN = WoodType.register(new WoodType("meadow:aspen", MeadowBlockSetTypes.ASPEN,
            SoundType.CHERRY_WOOD, SoundType.CHERRY_WOOD_HANGING_SIGN, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));

    public static final WoodType CALCIFIED = WoodType.register(new WoodType("meadow:calcified", MeadowBlockSetTypes.ASPEN,
            SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN));

    @EventBusSubscriber(modid= MeadowMod.MEADOW, bus= EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientOnly {
        @SubscribeEvent
        public static void addWoodTypes(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(ASPEN);
            });
        }
    }
}