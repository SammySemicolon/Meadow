package com.smellysleepy.meadow.registry.common.block;

import com.smellysleepy.meadow.common.block.meadow.wood.NaturalThinMeadowLogBlock;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import team.lodestar.lodestone.systems.block.*;

public class MeadowBlockProperties {

    public static LodestoneBlockProperties CALCIFIED_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.75F, 4.0F)
                .addTags(MeadowBlockTagRegistry.CALCIFICATION, MeadowBlockTagRegistry.CALCIFIED_PEARLFLOWER_GENERATES_ON)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(SoundType.NETHERRACK);
    }

    public static LodestoneBlockProperties CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.5F, 4.0F)
                .addTags(MeadowBlockTagRegistry.CALCIFICATION)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(SoundType.NETHERRACK)
                .setCutoutRenderType();
    }

    public static LodestoneBlockProperties CALCIFIED_COVERING_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.25F, 2.0F)
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .setCutoutRenderType()
                .noCollission()
                .noOcclusion()
                .addTags(MeadowBlockTagRegistry.CALCIFICATION)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.MINEABLE_WITH_HOE)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(SoundType.GLOW_LICHEN);
    }

    public static LodestoneBlockProperties CALCIFIED_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(MeadowBlockTagRegistry.CALCIFICATION)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .sound(SoundType.NETHER_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties CALCIFIED_LOG_PROPERTIES() {
        return CALCIFIED_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties STRIPPED_CALCIFIED_LOG_PROPERTIES() {
        return CALCIFIED_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties THIN_CALCIFIED_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.25F, 4.0F)
                .setCutoutRenderType()
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.NETHER_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties THIN_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties THIN_STRIPPED_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties NATURAL_THIN_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_LOG_PROPERTIES().lightLevel(s -> s.getValue(NaturalThinMeadowLogBlock.LEAVES).equals(NaturalThinMeadowLogBlock.MeadowLeavesType.TOP) ? 2 : 0);
    }
    
    public static LodestoneBlockProperties ASPEN_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties MEADOW_LOG_PROPERTIES() {
        return ASPEN_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties STRIPPED_MEADOW_LOG_PROPERTIES() {
        return MEADOW_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties THIN_MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.25F, 4.0F)
                .setCutoutRenderType()
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties THIN_MEADOW_LOG_PROPERTIES() {
        return THIN_MEADOW_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties THIN_STRIPPED_MEADOW_LOG_PROPERTIES() {
        return THIN_MEADOW_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties NATURAL_THIN_MEADOW_LOG_PROPERTIES() {
        return THIN_MEADOW_LOG_PROPERTIES().lightLevel(s -> s.getValue(NaturalThinMeadowLogBlock.LEAVES).equals(NaturalThinMeadowLogBlock.MeadowLeavesType.TOP) ? 2 : 0);
    }

    public static LodestoneBlockProperties ASPEN_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .setCutoutRenderType()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .needsHoe()
                .sound(SoundType.CHERRY_LEAVES)
                .addTags(BlockTags.MINEABLE_WITH_HOE)
                .addTag(BlockTags.LEAVES);
    }

    public static LodestoneBlockProperties HANGING_ASPEN_LEAVES_PROPERTIES() {
        return ASPEN_LEAVES_PROPERTIES().lightLevel(s -> 3).noCollission();
    }

    public static LodestoneBlockProperties MEADOW_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTags(MeadowBlockTagRegistry.CALCIFICATION_REPLACEABLE, MeadowBlockTagRegistry.MEADOW_GRASS_CAN_PLACE_ON, MeadowBlockTagRegistry.GRASSY_PEARLFLOWER_GENERATES_ON)
                .addTags(BlockTags.DIRT)
                .addTags(BlockTags.MINEABLE_WITH_SHOVEL)
                .isValidSpawn(((pState, pLevel, pPos, pValue) -> pValue.equals(MeadowEntityRegistry.MOO_MOO.get())))
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .setCutoutRenderType()
                .strength(0.6F)
                .randomTicks();
    }

    public static LodestoneBlockProperties MEADOW_GRASS_PROPERTIES() {
        return new LodestoneBlockProperties()
                .setCutoutRenderType()
                .noCollission()
                .noOcclusion()
                .replaceable()
                .instabreak()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS);
    }

    public static LodestoneBlockProperties ASPEN_SAPLING_PROPERTIES() {
        return new LodestoneBlockProperties()
                .setCutoutRenderType()
                .noCollission()
                .noOcclusion()
                .instabreak()
                .randomTicks()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .addTag(BlockTags.SAPLINGS);
    }

    public static LodestoneBlockProperties PEARLFLOWER_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(BlockTags.FLOWERS)
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.FLOWERING_AZALEA)
                .setCutoutRenderType()
                .lightLevel(s -> 12)
                .noCollission()
                .noOcclusion()
                .instabreak();
    }

    public static LodestoneBlockProperties SMALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(BlockTags.SMALL_FLOWERS);
    }

    public static LodestoneBlockProperties TALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(BlockTags.TALL_FLOWERS);
    }

    public static LodestoneBlockProperties PEARLLIGHT_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(BlockTags.MINEABLE_WITH_HOE)
                .mapColor(MapColor.COLOR_YELLOW)
                .sound(SoundType.SHROOMLIGHT)
                .strength(1.0F)
                .lightLevel((state) -> 15);
    }

    public static LodestoneBlockProperties MINERAL_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTags(MeadowBlockTagRegistry.ROCKY_PEARLFLOWER_GENERATES_ON, MeadowBlockTagRegistry.MINERAL_FLORA_CAN_PLACE_ON)
                .addTags(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTags(BlockTags.DIRT)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .randomTicks()
                .strength(0.6F);
    }

    public static LodestoneBlockProperties MINERAL_FLORA_PROPERTIES() {
        return new LodestoneBlockProperties()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .pushReaction(PushReaction.DESTROY)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .setCutoutRenderType()
                .noCollission()
                .noOcclusion()
                .replaceable()
                .instabreak();
    }
    public static LodestoneBlockProperties MINERAL_SAPLING_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES().addTag(BlockTags.SAPLINGS);
    }
    public static LodestoneBlockProperties MINERAL_FLOWER_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES().addTag(BlockTags.TALL_FLOWERS);
    }

    public static LodestoneBlockProperties MINERAL_GRASS_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES();
    }

    public static LodestoneBlockProperties MINERAL_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .sound(SoundType.CHERRY_LEAVES)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .addTag(BlockTags.LEAVES)
                .addTags(BlockTags.MINEABLE_WITH_HOE)
                .setCutoutRenderType()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .needsHoe();
    }

    public static LodestoneBlockProperties HANGING_MINERAL_LEAVES_PROPERTIES() {
        return MINERAL_LEAVES_PROPERTIES()
                .noCollission()
                .lightLevel(s -> 4);
    }
}
