package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import com.smellysleepy.meadow.common.block.calcification.CalcifiedPointedDripstoneBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.grass.*;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.PearllampBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.PearllightBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.TallPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.worldgen.feature.tree.SimpleTreeGrower;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.client.color.block.*;
import net.minecraft.tags.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;

public class MeadowBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    public static final RegistryObject<Block> CALCIFIED_EARTH = BLOCKS.register("calcified_earth", () -> new Block(MeadowBlockProperties.CALCIFIED_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> CALCIFIED_ROCK = BLOCKS.register("calcified_rock", () -> new Block(MeadowBlockProperties.CALCIFIED_BLOCK_PROPERTIES()));
    public static final RegistryObject<CalcifiedCoveringBlock> CALCIFIED_COVERING = BLOCKS.register("calcified_covering", () -> new CalcifiedCoveringBlock(MeadowBlockProperties.CALCIFIED_COVERING_PROPERTIES()));

    public static final RegistryObject<Block> CALCIFIED_DRIPSTONE = BLOCKS.register("calcified_dripstone", () -> new CalcifiedPointedDripstoneBlock(MeadowBlockProperties.CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> GIANT_CALCIFIED_DRIPSTONE = BLOCKS.register("giant_calcified_dripstone", () -> new CalcifiedPointedDripstoneBlock(MeadowBlockProperties.CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK = BLOCKS.register("meadow_grass_block", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> TALL_MEADOW_GRASS = BLOCKS.register("tall_meadow_grass", () -> new TallMeadowGrass(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_MEADOW_GRASS = BLOCKS.register("medium_meadow_grass", () -> new MediumMeadowGrass(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_MEADOW_GRASS = BLOCKS.register("short_meadow_grass", () -> new ShortMeadowGrass(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> PEARLLIGHT = BLOCKS.register("pearllight", () -> new PearllightBlock(MeadowBlockProperties.PEARLLIGHT_PROPERTIES()));

    public static final RegistryObject<Block> TALL_GRASSY_PEARLFLOWER = BLOCKS.register("tall_grassy_pearlflower", () -> new TallPearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_MARINE_PEARLFLOWER = BLOCKS.register("tall_marine_pearlflower", () -> new TallPearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_CALCIFIED_PEARLFLOWER = BLOCKS.register("tall_calcified_pearlflower", () -> new TallPearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_PEARLFLOWER = BLOCKS.register("tall_pearlflower", () -> new TallPearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> GRASSY_PEARLFLOWER = BLOCKS.register("grassy_pearlflower", () -> new PearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> MARINE_PEARLFLOWER = BLOCKS.register("marine_pearlflower", () -> new PearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> CALCIFIED_PEARLFLOWER = BLOCKS.register("calcified_pearlflower", () -> new PearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> PEARLFLOWER = BLOCKS.register("pearlflower", () -> new PearlFlowerBlock(MeadowBlockProperties.PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_LEAVES = BLOCKS.register("aspen_leaves", () -> new MeadowLeavesBlock(MeadowBlockProperties.ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> HANGING_ASPEN_LEAVES = BLOCKS.register("hanging_aspen_leaves", () -> new HangingMeadowLeavesBlock(MeadowBlockProperties.HANGING_ASPEN_LEAVES_PROPERTIES()));

    //region aspen wood
    public static final RegistryObject<Block> CALCIFIED_ASPEN_LOG = BLOCKS.register("calcified_aspen_log", () -> new CalcifiedMeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_ASPEN_LOG = BLOCKS.register("partially_calcified_aspen_log", () -> new PartiallyCalcifiedMeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> ASPEN_LOG = BLOCKS.register("aspen_log", () -> new MeadowLogBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> THIN_CALCIFIED_ASPEN_LOG = BLOCKS.register("thin_calcified_aspen_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> THIN_PARTIALLY_CALCIFIED_ASPEN_LOG = BLOCKS.register("thin_partially_calcified_aspen_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));
    public static final RegistryObject<Block> THIN_ASPEN_LOG = BLOCKS.register("thin_aspen_log", () -> new ThinMeadowLogBlock(MeadowBlockProperties.THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS)));

    public static final RegistryObject<Block> PEARLLAMP = BLOCKS.register("pearllamp", () -> new PearllampBlock(MeadowBlockProperties.PEARLLAMP_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_SAPLING = BLOCKS.register("aspen_sapling", () -> new MeadowSaplingBlock(new SimpleTreeGrower(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE), MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ASPEN_SAPLING = BLOCKS.register("small_aspen_sapling", () -> new MeadowSaplingBlock(new SimpleTreeGrower(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE), MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_PLANKS = BLOCKS.register("aspen_planks", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> ASPEN_BOARDS = BLOCKS.register("aspen_boards", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> HEAVY_ASPEN_BOARDS = BLOCKS.register("heavy_aspen_boards", () -> new Block(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> ASPEN_DOOR = BLOCKS.register("aspen_door", () -> new DoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> SOLID_ASPEN_DOOR = BLOCKS.register("solid_aspen_door", () -> new DoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_TRAPDOOR = BLOCKS.register("aspen_trapdoor", () -> new TrapDoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> SOLID_ASPEN_TRAPDOOR = BLOCKS.register("solid_aspen_trapdoor", () -> new TrapDoorBlock(MeadowBlockProperties.MEADOW_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    //endregion

    @Mod.EventBusSubscriber(modid = MEADOW, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();

            blockColors.register(
                    (s, l, p, c) -> {
                        int variant = s.getValue(MeadowGrassVariantHelper.VARIANT);
                        float desaturation = 1f - variant * 0.015f;
                        int red = 255;
                        int notRed = Mth.floor(255 * desaturation);
                        return red << 16 | notRed << 8 | red;
                    }, MEADOW_GRASS_BLOCK.get(),
                    SHORT_MEADOW_GRASS.get(), MEDIUM_MEADOW_GRASS.get(), TALL_MEADOW_GRASS.get());

        }
    }
}