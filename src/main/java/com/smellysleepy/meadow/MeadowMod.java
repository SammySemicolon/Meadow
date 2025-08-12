package com.smellysleepy.meadow;

import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.MeadowGroveBiomeTypes;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.*;

import static com.smellysleepy.meadow.registry.common.MeadowCreativeTabRegistry.CREATIVE_MODE_TABS;
import static com.smellysleepy.meadow.registry.common.MeadowMobEffectRegistry.EFFECTS;
import static com.smellysleepy.meadow.registry.common.MeadowSoundRegistry.SOUNDS;
import static com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowEntityRegistry.ENTITY_TYPES;
import static com.smellysleepy.meadow.registry.common.item.MeadowItemRegistry.*;
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

    public MeadowMod(IEventBus modEventBus, ModContainer modContainer) {
        CREATIVE_MODE_TABS.register(modEventBus);

        MineralTreePartTypes.init();
        MineralFloraRegistry.init();
        MeadowGroveBiomeTypes.init();

        SOUNDS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        EFFECTS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        FEATURE_TYPES.register(modEventBus);
        STRUCTURE_TYPES.register(modEventBus);
        STRUCTURE_PIECE_TYPES.register(modEventBus);
        PARTICLES.register(modEventBus);
    }

    public static ResourceLocation meadowPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MEADOW, path);
    }
}