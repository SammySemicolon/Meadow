package com.smellysleepy.meadow;

import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.*;

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