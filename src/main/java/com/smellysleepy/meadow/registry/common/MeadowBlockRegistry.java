package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.block.strange_flora.mineral_flora.*;
import net.minecraft.client.color.block.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;

public class MeadowBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    //region meadow wood NEEDS NAME !!! JOE BIDEN
    public static final RegistryObject<Block> FULLY_CALCIFIED_MEADOW_LOG = BLOCKS.register("fully_calcified_meadow_log", () -> new MeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_MEADOW_LOG = BLOCKS.register("partially_calcified_meadow_log", () -> new CalcifiedMeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> MEADOW_LOG = BLOCKS.register("meadow_log", () -> new MeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));

    public static final RegistryObject<Block> THIN_MEADOW_LOG = BLOCKS.register("thin_meadow_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));

    //endregion

    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK = BLOCKS.register("meadow_grass_block", () -> new Block(MeadowBlockProperties.MEADOW_GRASS_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> TALL_MEADOW_GRASS = BLOCKS.register("tall_meadow_grass", () -> new MeadowTallGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_MEADOW_GRASS = BLOCKS.register("medium_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_MEADOW_GRASS = BLOCKS.register("short_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> MEADOW_LEAVES = BLOCKS.register("meadow_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.MEADOW_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> FLOWERING_MEADOW_LEAVES = BLOCKS.register("flowering_meadow_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.MEADOW_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> HANGING_MEADOW_LEAVES = BLOCKS.register("hanging_meadow_leaves", () -> new MeadowHangingLeavesBlock(MeadowBlockProperties.HANGING_MEADOW_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> TALL_HANGING_MEADOW_LEAVES = BLOCKS.register("tall_hanging_meadow_leaves", () -> new MeadowTallHangingLeavesBlock(MeadowBlockProperties.HANGING_MEADOW_LEAVES_PROPERTIES()));

    public static final RegistryObject<Block> MEADOW_MUSHROOM = BLOCKS.register("meadow_mushroom", () -> new MeadowWallFungusBlock(MeadowBlockProperties.WALL_FUNGUS_PROPERTIES()));

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
