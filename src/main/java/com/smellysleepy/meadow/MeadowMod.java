package com.smellysleepy.meadow;

import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

import static com.smellysleepy.meadow.registry.common.BlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.ItemRegistry.ITEMS;

@SuppressWarnings("unused")
@Mod(MeadowMod.MEADOW)
public class MeadowMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MEADOW = "meadow";
    public static final RandomSource RANDOM = RandomSource.create();

    public MeadowMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);

    }

    public static ResourceLocation meadowModPath(String path) {
        return new ResourceLocation(MEADOW, path);
    }


}