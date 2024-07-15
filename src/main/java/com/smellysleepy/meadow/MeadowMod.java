package com.smellysleepy.meadow;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.apache.logging.log4j.*;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.*;
import static com.smellysleepy.meadow.registry.common.MeadowParticleRegistry.PARTICLES;
import static com.smellysleepy.meadow.registry.worldgen.MeadowFeatureRegistry.*;
import static com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes.STRUCTURE_PIECE_TYPES;
import static com.smellysleepy.meadow.registry.worldgen.MeadowStructureTypes.STRUCTURE_TYPES;

@SuppressWarnings("unused")
@Mod(MeadowMod.MEADOW)
public class MeadowMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MEADOW = "meadow";
    public static final RandomSource RANDOM = RandomSource.create();

    public MeadowMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MeadowCreativeTabRegistry.CREATIVE_MODE_TABS.register(modBus);

        MineralFloraRegistry.init();

        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        FEATURE_TYPES.register(modBus);
        STRUCTURE_TYPES.register(modBus);
        STRUCTURE_PIECE_TYPES.register(modBus);
        PARTICLES.register(modBus);
    }

    public static ResourceLocation meadowModPath(String path) {
        return new ResourceLocation(MEADOW, path);
    }
}