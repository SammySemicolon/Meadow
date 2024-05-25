package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.meadow.*;
import com.smellysleepy.meadow.common.block.strange_flora.mineral_flora.*;
import net.minecraft.client.color.block.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.easing.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;

public class MeadowBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK = BLOCKS.register("meadow_grass_block", () -> new Block(MeadowBlockProperties.MEADOW_GRASS_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> TALL_MEADOW_GRASS = BLOCKS.register("tall_meadow_grass", () -> new MeadowTallGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_MEADOW_GRASS = BLOCKS.register("medium_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_MEADOW_GRASS = BLOCKS.register("short_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<MeadowLeavesBlock> MEADOW_LEAVES = BLOCKS.register("meadow_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.MEADOW_LEAVES_PROPERTIES()));

    public static final RegistryObject<Block> MEADOW_LOG_TEST = BLOCKS.register("meadow_log_test", () -> new RotatedPillarBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> MEADOW_BOARDS_TEST = BLOCKS.register("meadow_boards_test", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> MEADOW_VERTICAL_BOARDS_TEST = BLOCKS.register("meadow_vertical_boards_test", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));


    public static final RegistryObject<Block> CRIMSON_SUN = BLOCKS.register("crimson_sun", () -> new CrimsonSunFlower(MeadowBlockProperties.STRANGE_FLORA_PROPERTIES()));
    public static final RegistryObject<Block> LAZURITE_ROSE = BLOCKS.register("lazurite_rose", () -> new LazuriteRoseFlower(MeadowBlockProperties.STRANGE_FLORA_PROPERTIES()));
    public static final RegistryObject<Block> BERYL_ALSTRO = BLOCKS.register("beryl_alstro", () -> new BerylAlstroFlower(MeadowBlockProperties.STRANGE_FLORA_PROPERTIES()));
    public static final RegistryObject<Block> TRANQUIL_LILY = BLOCKS.register("tranquil_lily", () -> new TranquilLilyFlower(MeadowBlockProperties.STRANGE_FLORA_PROPERTIES()));

    @Mod.EventBusSubscriber(modid = MEADOW, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();
        }
    }
}
