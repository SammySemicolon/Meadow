package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.block.strange_flora.mineral_flora.*;
import com.smellysleepy.meadow.common.worldgen.tree.*;
import com.smellysleepy.meadow.common.worldgen.tree.small.*;
import net.minecraft.client.color.block.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;

public class MeadowBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    //region meadow wood
    public static final RegistryObject<Block> ASPEN_SAPLING = BLOCKS.register("aspen_sapling", () -> new MeadowSaplingBlock(new MeadowTreeGrower(), MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ASPEN_SAPLING = BLOCKS.register("small_aspen_sapling", () -> new MeadowSaplingBlock(new SmallMeadowTreeGrower(), MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_LOG = BLOCKS.register("aspen_log", () -> new MeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_ASPEN_LOG = BLOCKS.register("partially_calcified_aspen_log", () -> new PartiallyCalcifiedMeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> CALCIFIED_ASPEN_LOG = BLOCKS.register("calcified_aspen_log", () -> new CalcifiedMeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));

    public static final RegistryObject<Block> THIN_ASPEN_LOG = BLOCKS.register("thin_aspen_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> THIN_CALCIFIED_ASPEN_LOG = BLOCKS.register("thin_calcified_aspen_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));


    public static final RegistryObject<Block> ASPEN_PLANKS = BLOCKS.register("aspen_planks", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> ASPEN_BOARDS = BLOCKS.register("aspen_boards", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> HEAVY_ASPEN_BOARDS = BLOCKS.register("heavy_aspen_boards", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> ASPEN_DOOR = BLOCKS.register("aspen_door", () -> new DoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> SOLID_ASPEN_DOOR = BLOCKS.register("solid_aspen_door", () -> new DoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_TRAPDOOR = BLOCKS.register("aspen_trapdoor", () -> new TrapDoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> SOLID_ASPEN_TRAPDOOR = BLOCKS.register("solid_aspen_trapdoor", () -> new TrapDoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    //endregion

    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK = BLOCKS.register("meadow_grass_block", () -> new Block(MeadowBlockProperties.MEADOW_GRASS_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> TALL_MEADOW_GRASS = BLOCKS.register("tall_meadow_grass", () -> new MeadowTallGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_MEADOW_GRASS = BLOCKS.register("medium_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_MEADOW_GRASS = BLOCKS.register("short_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_LEAVES = BLOCKS.register("aspen_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> FLOWERING_ASPEN_LEAVES = BLOCKS.register("flowering_aspen_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> HANGING_ASPEN_LEAVES = BLOCKS.register("hanging_aspen_leaves", () -> new MeadowHangingLeavesBlock(MeadowBlockProperties.HANGING_ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> TALL_HANGING_ASPEN_LEAVES = BLOCKS.register("tall_hanging_aspen_leaves", () -> new MeadowTallHangingLeavesBlock(MeadowBlockProperties.HANGING_ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> ASPEN_LEAF_PILE = BLOCKS.register("aspen_leaf_pile", () -> new MeadowLeafPileBlock(MeadowBlockProperties.ASPEN_LEAVES_PROPERTIES()));

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
