package com.smellysleepy.meadow.registry.common.block;

import com.smellysleepy.meadow.common.block.CalcifiedLogBlock;
import com.smellysleepy.meadow.common.block.PartiallyCalcifiedLogBlock;
import com.smellysleepy.meadow.common.block.ThinLogBlock;
import com.smellysleepy.meadow.common.block.aspen.ThinPartiallyCalcifiedAspenLogBlock;
import com.smellysleepy.meadow.common.block.aspen.AspenGrassBlock;
import com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper;
import com.smellysleepy.meadow.common.block.aspen.AspenSaplingBlock;
import com.smellysleepy.meadow.common.block.aspen.ThinNaturalAspenLogBlock;
import com.smellysleepy.meadow.common.block.aspen.grass.MediumAspenGrass;
import com.smellysleepy.meadow.common.block.aspen.grass.ShortAspenGrass;
import com.smellysleepy.meadow.common.block.aspen.grass.TallAspenGrass;
import com.smellysleepy.meadow.common.block.aspen.leaves.AspenLeavesBlock;
import com.smellysleepy.meadow.common.block.aspen.leaves.HangingAspenLeavesBlock;
import com.smellysleepy.meadow.common.block.calcification.*;
import com.smellysleepy.meadow.common.block.fungi.*;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.TallPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.lamp.PearlLampBlock;
import com.smellysleepy.meadow.common.block.pearlflower.lamp.PearlLightBlock;
import com.smellysleepy.meadow.common.block.pearlflower.wilted.WiltedPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.wilted.WiltedTallPearlFlowerBlock;
import com.smellysleepy.meadow.common.worldgen.feature.tree.SimpleTreeGrower;
import com.smellysleepy.meadow.registry.common.block.properties.AspenBlockProperties;
import com.smellysleepy.meadow.registry.common.block.properties.CalcifiedBlockProperties;
import com.smellysleepy.meadow.registry.common.block.properties.FungalBlockProperties;
import com.smellysleepy.meadow.registry.common.block.properties.PearlflowerBlockProperties;
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
import team.lodestar.lodestone.systems.block.LodestoneDirectionalBlock;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;
import team.lodestar.lodestone.systems.block.sign.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.tags.BlockTags.WOODEN_STAIRS;
import static net.minecraft.world.level.block.PressurePlateBlock.Sensitivity.EVERYTHING;
import static net.minecraftforge.common.Tags.Blocks.FENCE_GATES_WOODEN;

public class MeadowBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    //region Natural Calcification
    public static final RegistryObject<Block> CALCIFIED_EARTH = BLOCKS.register("calcified_earth", () -> new CalcifiedBlock(CalcifiedBlockProperties.CALCIFIED_BLOCK_PROPERTIES(), MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_EARTH_BONEMEAL));
    public static final RegistryObject<Block> CALCIFIED_ROCK = BLOCKS.register("calcified_rock", () -> new CalcifiedBlock(CalcifiedBlockProperties.CALCIFIED_BLOCK_PROPERTIES(), MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_ROCK_BONEMEAL));
    public static final RegistryObject<CalcifiedCoveringBlock> CALCIFIED_COVERING = BLOCKS.register("calcified_covering", () -> new CalcifiedCoveringBlock(CalcifiedBlockProperties.CALCIFIED_COVERING_PROPERTIES()));

    public static final RegistryObject<Block> CALCIFIED_DRIPSTONE = BLOCKS.register("calcified_dripstone", () -> new CalcifiedPointedDripstoneBlock(CalcifiedBlockProperties.CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> GIANT_CALCIFIED_DRIPSTONE = BLOCKS.register("giant_calcified_dripstone", () -> new CalcifiedPointedDripstoneBlock(CalcifiedBlockProperties.CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES()));
    //endregion

    //region Calcified Blocks
    public static final RegistryObject<Block> CALCIFIED_BRICKS = BLOCKS.register("calcified_bricks", () -> new Block(CalcifiedBlockProperties.CALCIFIED_BRICK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_CALCIFIED_BRICKS = BLOCKS.register("chiseled_calcified_bricks", () -> new LodestoneDirectionalBlock(CalcifiedBlockProperties.CALCIFIED_BRICK_PROPERTIES()));
    public static final RegistryObject<Block> CALCIFIED_BRICKS_STAIRS = BLOCKS.register("calcified_bricks_stairs", () -> new StairBlock(() -> CALCIFIED_BRICKS.get().defaultBlockState(), CalcifiedBlockProperties.CALCIFIED_BRICK_PROPERTIES().addTags(STAIRS)));
    public static final RegistryObject<Block> CALCIFIED_BRICKS_SLAB = BLOCKS.register("calcified_bricks_slab", () -> new SlabBlock(CalcifiedBlockProperties.CALCIFIED_BRICK_PROPERTIES().addTags(SLABS)));
    public static final RegistryObject<Block> CALCIFIED_BRICKS_WALL = BLOCKS.register("calcified_bricks_wall", () -> new WallBlock(CalcifiedBlockProperties.CALCIFIED_BRICK_PROPERTIES().addTags(WALLS)));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_BRICKS = BLOCKS.register("heavy_calcified_bricks", () -> new Block(CalcifiedBlockProperties.HEAVY_CALCIFIED_BRICK_PROPERTIES()));
    public static final RegistryObject<Block> HEAVY_CHISELED_CALCIFIED_BRICKS = BLOCKS.register("heavy_chiseled_calcified_bricks", () -> new LodestoneDirectionalBlock(CalcifiedBlockProperties.HEAVY_CALCIFIED_BRICK_PROPERTIES()));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_BRICKS_STAIRS = BLOCKS.register("heavy_calcified_bricks_stairs", () -> new StairBlock(() -> HEAVY_CALCIFIED_BRICKS.get().defaultBlockState(), CalcifiedBlockProperties.HEAVY_CALCIFIED_BRICK_PROPERTIES().addTags(STAIRS)));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_BRICKS_SLAB = BLOCKS.register("heavy_calcified_bricks_slab", () -> new SlabBlock(CalcifiedBlockProperties.HEAVY_CALCIFIED_BRICK_PROPERTIES().addTags(SLABS)));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_BRICKS_WALL = BLOCKS.register("heavy_calcified_bricks_wall", () -> new WallBlock(CalcifiedBlockProperties.HEAVY_CALCIFIED_BRICK_PROPERTIES().addTags(WALLS)));

    //endregion

    //region Aspen Wood
    public static final RegistryObject<Block> THIN_STRIPPED_ASPEN_WOOD = BLOCKS.register("thin_stripped_aspen_wood", () -> new ThinLogBlock(AspenBlockProperties.THIN_STRIPPED_ASPEN_LOG_PROPERTIES()));
    public static final RegistryObject<Block> THIN_STRIPPED_ASPEN_LOG = BLOCKS.register("thin_stripped_aspen_log", () -> new ThinLogBlock(AspenBlockProperties.THIN_STRIPPED_ASPEN_LOG_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_ASPEN_WOOD = BLOCKS.register("stripped_aspen_wood", () -> new RotatedPillarBlock(AspenBlockProperties.STRIPPED_ASPEN_LOG_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_ASPEN_LOG = BLOCKS.register("stripped_aspen_log", () -> new RotatedPillarBlock(AspenBlockProperties.STRIPPED_ASPEN_LOG_PROPERTIES()));

    public static final RegistryObject<Block> THIN_ASPEN_WOOD = BLOCKS.register("thin_aspen_wood", () -> new ThinNaturalAspenLogBlock(AspenBlockProperties.NATURAL_THIN_ASPEN_LOG_PROPERTIES(), THIN_STRIPPED_ASPEN_WOOD));
    public static final RegistryObject<Block> THIN_ASPEN_LOG = BLOCKS.register("thin_aspen_log", () -> new ThinNaturalAspenLogBlock(AspenBlockProperties.NATURAL_THIN_ASPEN_LOG_PROPERTIES(), THIN_STRIPPED_ASPEN_LOG));
    public static final RegistryObject<Block> ASPEN_WOOD = BLOCKS.register("aspen_wood", () -> new LodestoneLogBlock(AspenBlockProperties.ASPEN_LOG_PROPERTIES(), STRIPPED_ASPEN_WOOD));
    public static final RegistryObject<Block> ASPEN_LOG = BLOCKS.register("aspen_log", () -> new LodestoneLogBlock(AspenBlockProperties.ASPEN_LOG_PROPERTIES(), STRIPPED_ASPEN_LOG));

    public static final RegistryObject<Block> ASPEN_PLANKS = BLOCKS.register("aspen_planks", () -> new Block(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(PLANKS)));
    public static final RegistryObject<Block> ASPEN_PLANKS_STAIRS = BLOCKS.register("aspen_planks_stairs", () -> new StairBlock(() -> ASPEN_PLANKS.get().defaultBlockState(), AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(STAIRS, WOODEN_STAIRS)));
    public static final RegistryObject<Block> ASPEN_PLANKS_SLAB = BLOCKS.register("aspen_planks_slab", () -> new SlabBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(SLABS, WOODEN_SLABS)));

    public static final RegistryObject<Block> HEAVY_ASPEN_PLANKS = BLOCKS.register("heavy_aspen_planks", () -> new Block(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(PLANKS)));
    public static final RegistryObject<Block> HEAVY_ASPEN_PLANKS_STAIRS = BLOCKS.register("heavy_aspen_planks_stairs", () -> new StairBlock(() -> HEAVY_ASPEN_PLANKS.get().defaultBlockState(), AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(STAIRS, WOODEN_STAIRS)));
    public static final RegistryObject<Block> HEAVY_ASPEN_PLANKS_SLAB = BLOCKS.register("heavy_aspen_planks_slab", () -> new SlabBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(SLABS, WOODEN_SLABS)));

    public static final RegistryObject<Block> ASPEN_FENCE = BLOCKS.register("aspen_planks_fence", () -> new FenceBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(FENCES, WOODEN_FENCES)));
    public static final RegistryObject<Block> ASPEN_FENCE_GATE = BLOCKS.register("aspen_planks_fence_gate", () -> new FenceGateBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(FENCE_GATES, FENCE_GATES_WOODEN), MeadowWoodTypeRegistry.ASPEN));

    public static final RegistryObject<Block> SOLID_ASPEN_DOOR = BLOCKS.register("solid_aspen_door", () -> new DoorBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_DOOR = BLOCKS.register("aspen_door", () -> new DoorBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> SOLID_ASPEN_TRAPDOOR = BLOCKS.register("solid_aspen_trapdoor", () -> new TrapDoorBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_TRAPDOOR = BLOCKS.register("aspen_trapdoor", () -> new TrapDoorBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.ASPEN));

    public static final RegistryObject<Block> ASPEN_PRESSURE_PLATE = BLOCKS.register("aspen_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES), MeadowBlockSetTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_BUTTON = BLOCKS.register("aspen_planks_button", () -> new ButtonBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(BUTTONS, WOODEN_BUTTONS).addTags(BUTTONS, WOODEN_BUTTONS), MeadowBlockSetTypes.ASPEN, 20, true));

    public static final RegistryObject<Block> ASPEN_SIGN = BLOCKS.register("aspen_sign", () -> new LodestoneStandingSignBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission(), MeadowWoodTypeRegistry.ASPEN));
    public static final RegistryObject<Block> ASPEN_WALL_SIGN = BLOCKS.register("aspen_wall_sign", () -> new LodestoneWallSignBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission(), MeadowWoodTypeRegistry.ASPEN));

    public static final RegistryObject<Block> ASPEN_PEARLLAMP = BLOCKS.register("aspen_pearllamp", () -> new PearlLampBlock(AspenBlockProperties.ASPEN_WOOD_PROPERTIES().lightLevel((state) -> 14)));
    //endregion

    //region Calcified Wood
    public static final RegistryObject<Block> THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD = BLOCKS.register("thin_partially_calcified_aspen_wood", () -> new ThinPartiallyCalcifiedAspenLogBlock(CalcifiedBlockProperties.THIN_CALCIFIED_LOG_PROPERTIES(), THIN_ASPEN_WOOD));
    public static final RegistryObject<Block> THIN_PARTIALLY_CALCIFIED_ASPEN_LOG = BLOCKS.register("thin_partially_calcified_aspen_log", () -> new ThinPartiallyCalcifiedAspenLogBlock(CalcifiedBlockProperties.THIN_CALCIFIED_LOG_PROPERTIES(), THIN_ASPEN_LOG));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_ASPEN_WOOD = BLOCKS.register("partially_calcified_aspen_wood", () -> new PartiallyCalcifiedLogBlock(AspenBlockProperties.ASPEN_LOG_PROPERTIES(), ASPEN_WOOD));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_ASPEN_LOG = BLOCKS.register("partially_calcified_aspen_log", () -> new PartiallyCalcifiedLogBlock(AspenBlockProperties.ASPEN_LOG_PROPERTIES(), ASPEN_LOG));

    public static final RegistryObject<Block> THIN_STRIPPED_CALCIFIED_WOOD = BLOCKS.register("thin_stripped_calcified_wood", () -> new ThinLogBlock(CalcifiedBlockProperties.THIN_STRIPPED_CALCIFIED_LOG_PROPERTIES()));
    public static final RegistryObject<Block> THIN_STRIPPED_CALCIFIED_LOG = BLOCKS.register("thin_stripped_calcified_log", () -> new ThinLogBlock(CalcifiedBlockProperties.THIN_STRIPPED_CALCIFIED_LOG_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_CALCIFIED_WOOD = BLOCKS.register("stripped_calcified_wood", () -> new RotatedPillarBlock(CalcifiedBlockProperties.STRIPPED_CALCIFIED_LOG_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_CALCIFIED_LOG = BLOCKS.register("stripped_calcified_log", () -> new RotatedPillarBlock(CalcifiedBlockProperties.STRIPPED_CALCIFIED_LOG_PROPERTIES()));

    public static final RegistryObject<Block> THIN_CALCIFIED_WOOD = BLOCKS.register("thin_calcified_wood", () -> new ThinNaturalAspenLogBlock(CalcifiedBlockProperties.NATURAL_THIN_CALCIFIED_LOG_PROPERTIES(), THIN_ASPEN_WOOD));
    public static final RegistryObject<Block> THIN_CALCIFIED_LOG = BLOCKS.register("thin_calcified_log", () -> new ThinNaturalAspenLogBlock(CalcifiedBlockProperties.NATURAL_THIN_CALCIFIED_LOG_PROPERTIES(), THIN_ASPEN_LOG));
    public static final RegistryObject<Block> CALCIFIED_WOOD = BLOCKS.register("calcified_wood", () -> new CalcifiedLogBlock(CalcifiedBlockProperties.CALCIFIED_LOG_PROPERTIES(), ASPEN_WOOD));
    public static final RegistryObject<Block> CALCIFIED_LOG = BLOCKS.register("calcified_log", () -> new CalcifiedLogBlock(CalcifiedBlockProperties.CALCIFIED_LOG_PROPERTIES(), ASPEN_LOG));

    public static final RegistryObject<Block> CALCIFIED_PLANKS = BLOCKS.register("calcified_planks", () -> new Block(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(PLANKS)));
    public static final RegistryObject<Block> CALCIFIED_PLANKS_STAIRS = BLOCKS.register("calcified_planks_stairs", () -> new StairBlock(() -> CALCIFIED_PLANKS.get().defaultBlockState(), CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(STAIRS, WOODEN_STAIRS)));
    public static final RegistryObject<Block> CALCIFIED_PLANKS_SLAB = BLOCKS.register("calcified_planks_slab", () -> new SlabBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(SLABS, WOODEN_SLABS)));

    public static final RegistryObject<Block> HEAVY_CALCIFIED_PLANKS = BLOCKS.register("heavy_calcified_planks", () -> new Block(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(PLANKS)));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_PLANKS_STAIRS = BLOCKS.register("heavy_calcified_planks_stairs", () -> new StairBlock(() -> HEAVY_CALCIFIED_PLANKS.get().defaultBlockState(), CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(STAIRS, WOODEN_STAIRS)));
    public static final RegistryObject<Block> HEAVY_CALCIFIED_PLANKS_SLAB = BLOCKS.register("heavy_calcified_planks_slab", () -> new SlabBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(SLABS, WOODEN_SLABS)));

    public static final RegistryObject<Block> CALCIFIED_FENCE = BLOCKS.register("calcified_planks_fence", () -> new FenceBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(FENCES, WOODEN_FENCES)));
    public static final RegistryObject<Block> CALCIFIED_FENCE_GATE = BLOCKS.register("calcified_planks_fence_gate", () -> new FenceGateBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(FENCE_GATES, FENCE_GATES_WOODEN), MeadowWoodTypeRegistry.CALCIFIED));

    public static final RegistryObject<Block> SOLID_CALCIFIED_DOOR = BLOCKS.register("solid_calcified_door", () -> new DoorBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.CALCIFIED));
    public static final RegistryObject<Block> CALCIFIED_DOOR = BLOCKS.register("calcified_door", () -> new DoorBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(BlockTags.DOORS, BlockTags.WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.CALCIFIED));
    public static final RegistryObject<Block> SOLID_CALCIFIED_TRAPDOOR = BLOCKS.register("solid_calcified_trapdoor", () -> new TrapDoorBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.CALCIFIED));
    public static final RegistryObject<Block> CALCIFIED_TRAPDOOR = BLOCKS.register("calcified_trapdoor", () -> new TrapDoorBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MeadowBlockSetTypes.CALCIFIED));

    public static final RegistryObject<Block> CALCIFIED_PRESSURE_PLATE = BLOCKS.register("calcified_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES), MeadowBlockSetTypes.CALCIFIED));
    public static final RegistryObject<Block> CALCIFIED_BUTTON = BLOCKS.register("calcified_planks_button", () -> new ButtonBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(BUTTONS, WOODEN_BUTTONS).addTags(BUTTONS, WOODEN_BUTTONS), MeadowBlockSetTypes.CALCIFIED, 20, true));

    public static final RegistryObject<Block> CALCIFIED_SIGN = BLOCKS.register("calcified_sign", () -> new LodestoneStandingSignBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission(), MeadowWoodTypeRegistry.CALCIFIED));
    public static final RegistryObject<Block> CALCIFIED_WALL_SIGN = BLOCKS.register("calcified_wall_sign", () -> new LodestoneWallSignBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission(), MeadowWoodTypeRegistry.CALCIFIED));

    public static final RegistryObject<Block> CALCIFIED_PEARLLAMP = BLOCKS.register("calcified_pearllamp", () -> new PearlLampBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES().lightLevel((state) -> 14)));
    //endregion

    //region Aspen Flora
    public static final RegistryObject<Block> ASPEN_LEAVES = BLOCKS.register("aspen_leaves", () -> new AspenLeavesBlock(AspenBlockProperties.ASPEN_LEAVES_PROPERTIES()));
    public static final RegistryObject<Block> HANGING_ASPEN_LEAVES = BLOCKS.register("hanging_aspen_leaves", () -> new HangingAspenLeavesBlock(AspenBlockProperties.HANGING_ASPEN_LEAVES_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_SAPLING = BLOCKS.register("aspen_sapling", () -> new AspenSaplingBlock(new SimpleTreeGrower(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE), AspenBlockProperties.ASPEN_SAPLING_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ASPEN_SAPLING = BLOCKS.register("small_aspen_sapling", () -> new AspenSaplingBlock(new SimpleTreeGrower(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE), AspenBlockProperties.ASPEN_SAPLING_PROPERTIES()));

    public static final RegistryObject<Block> PEARLLIGHT = BLOCKS.register("pearllight", () -> new PearlLightBlock(PearlflowerBlockProperties.PEARLLIGHT_PROPERTIES()));

    public static final RegistryObject<Block> TALL_GRASSY_PEARLFLOWER = BLOCKS.register("tall_grassy_pearlflower", () -> new TallPearlFlowerBlock(PearlflowerBlockProperties.TALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_MARINE_PEARLFLOWER = BLOCKS.register("tall_marine_pearlflower", () -> new TallPearlFlowerBlock(PearlflowerBlockProperties.TALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_CALCIFIED_PEARLFLOWER = BLOCKS.register("tall_calcified_pearlflower", () -> new TallPearlFlowerBlock(PearlflowerBlockProperties.TALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_ROCKY_PEARLFLOWER = BLOCKS.register("tall_rocky_pearlflower", () -> new TallPearlFlowerBlock(PearlflowerBlockProperties.TALL_PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> GRASSY_PEARLFLOWER = BLOCKS.register("grassy_pearlflower", () -> new PearlFlowerBlock(PearlflowerBlockProperties.SMALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> MARINE_PEARLFLOWER = BLOCKS.register("marine_pearlflower", () -> new PearlFlowerBlock(PearlflowerBlockProperties.SMALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> CALCIFIED_PEARLFLOWER = BLOCKS.register("calcified_pearlflower", () -> new PearlFlowerBlock(PearlflowerBlockProperties.SMALL_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> ROCKY_PEARLFLOWER = BLOCKS.register("rocky_pearlflower", () -> new PearlFlowerBlock(PearlflowerBlockProperties.SMALL_PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> TALL_WILTED_GRASSY_PEARLFLOWER = BLOCKS.register("tall_wilted_grassy_pearlflower", () -> new WiltedTallPearlFlowerBlock(PearlflowerBlockProperties.TALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_WILTED_MARINE_PEARLFLOWER = BLOCKS.register("tall_wilted_marine_pearlflower", () -> new WiltedTallPearlFlowerBlock(PearlflowerBlockProperties.TALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_WILTED_CALCIFIED_PEARLFLOWER = BLOCKS.register("tall_wilted_calcified_pearlflower", () -> new WiltedTallPearlFlowerBlock(PearlflowerBlockProperties.TALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> TALL_WILTED_ROCKY_PEARLFLOWER = BLOCKS.register("tall_wilted_rocky_pearlflower", () -> new WiltedTallPearlFlowerBlock(PearlflowerBlockProperties.TALL_WILTED_PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> WILTED_GRASSY_PEARLFLOWER = BLOCKS.register("wilted_grassy_pearlflower", () -> new WiltedPearlFlowerBlock(PearlflowerBlockProperties.SMALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> WILTED_MARINE_PEARLFLOWER = BLOCKS.register("wilted_marine_pearlflower", () -> new WiltedPearlFlowerBlock(PearlflowerBlockProperties.SMALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> WILTED_CALCIFIED_PEARLFLOWER = BLOCKS.register("wilted_calcified_pearlflower", () -> new WiltedPearlFlowerBlock(PearlflowerBlockProperties.SMALL_WILTED_PEARLFLOWER_PROPERTIES()));
    public static final RegistryObject<Block> WILTED_ROCKY_PEARLFLOWER = BLOCKS.register("wilted_rocky_pearlflower", () -> new WiltedPearlFlowerBlock(PearlflowerBlockProperties.SMALL_WILTED_PEARLFLOWER_PROPERTIES()));

    public static final RegistryObject<Block> TALL_ASPEN_GRASS = BLOCKS.register("tall_aspen_grass", () -> new TallAspenGrass(AspenBlockProperties.ASPEN_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_ASPEN_GRASS = BLOCKS.register("medium_aspen_grass", () -> new MediumAspenGrass(AspenBlockProperties.ASPEN_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_ASPEN_GRASS = BLOCKS.register("short_aspen_grass", () -> new ShortAspenGrass(AspenBlockProperties.ASPEN_GRASS_PROPERTIES()));

    public static final RegistryObject<Block> ASPEN_GRASS_BLOCK = BLOCKS.register("aspen_grass_block", () -> new AspenGrassBlock(AspenBlockProperties.ASPEN_GRASS_BLOCK_PROPERTIES()));
    //endregion

    public static final RegistryObject<Block> CHANTERELLE_CAP_BLOCK = BLOCKS.register("chanterelle_cap_block", () -> new ChanterelleMushroomCapBlock(FungalBlockProperties.CHANTERELLE_CAP_PROPERTIES()));

    public static final RegistryObject<Block> CHANTERELLE_STEM_BLOCK = BLOCKS.register("chanterelle_stem_block", () -> new ChanterelleMushroomStemBlock(FungalBlockProperties.CHANTERELLE_STEM_PROPERTIES()));
    public static final RegistryObject<Block> PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK = BLOCKS.register("partially_calcified_chanterelle_stem_block", () -> new PartiallyCalcifiedLogBlock(FungalBlockProperties.CHANTERELLE_STEM_PROPERTIES(), CHANTERELLE_STEM_BLOCK));
    public static final RegistryObject<Block> CALCIFIED_CHANTERELLE_STEM_BLOCK = BLOCKS.register("calcified_chanterelle_stem_block", () -> new CalcifiedLogBlock(CalcifiedBlockProperties.CALCIFIED_WOOD_PROPERTIES(), CHANTERELLE_STEM_BLOCK));

    public static final RegistryObject<Block> THIN_CHANTERELLE_STEM_BLOCK = BLOCKS.register("thin_chanterelle_stem_block", () -> new ThinLayeredChanterelleStemBlock(FungalBlockProperties.THIN_CHANTERELLE_STEM_PROPERTIES()));
    public static final RegistryObject<Block> THIN_PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK = BLOCKS.register("thin_partially_calcified_chanterelle_stem_block", () -> new ThinPartiallyCalcifiedChanterelleStemBlock(FungalBlockProperties.THIN_CHANTERELLE_STEM_PROPERTIES()));
    public static final RegistryObject<Block> THIN_CALCIFIED_CHANTERELLE_STEM_BLOCK = BLOCKS.register("thin_calcified_chanterelle_stem_block", () -> new ThinNaturalChanterelleStemBlock(FungalBlockProperties.THIN_CHANTERELLE_STEM_PROPERTIES()));

    @Mod.EventBusSubscriber(modid = MEADOW, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();

            blockColors.register(
                    (s, l, p, c) -> {
                        int variant = s.getValue(AspenGrassVariantHelper.VARIANT);
                        float desaturation = 1f - variant * 0.0125f;
                        int red = 255;
                        int notRed = Mth.floor(255 * desaturation);
                        return red << 16 | notRed << 8 | red;
                    }, ASPEN_GRASS_BLOCK.get(),
                    SHORT_ASPEN_GRASS.get(), MEDIUM_ASPEN_GRASS.get(), TALL_ASPEN_GRASS.get());
        }
    }
}