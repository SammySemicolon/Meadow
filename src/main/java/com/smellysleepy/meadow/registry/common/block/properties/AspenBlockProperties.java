package com.smellysleepy.meadow.registry.common.block.properties;

import com.smellysleepy.meadow.common.block.aspen.ThinNaturalAspenLogBlock;
import com.smellysleepy.meadow.registry.common.MeadowEntityRegistry;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class AspenBlockProperties {
    public static LodestoneBlockProperties ASPEN_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties ASPEN_LOG_PROPERTIES() {
        return ASPEN_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties STRIPPED_ASPEN_LOG_PROPERTIES() {
        return ASPEN_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties THIN_ASPEN_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.25F, 4.0F)
                .setCutoutRenderType()
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties THIN_ASPEN_LOG_PROPERTIES() {
        return THIN_ASPEN_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties THIN_STRIPPED_ASPEN_LOG_PROPERTIES() {
        return THIN_ASPEN_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties NATURAL_THIN_ASPEN_LOG_PROPERTIES() {
        return THIN_ASPEN_LOG_PROPERTIES().lightLevel(s -> s.getValue(ThinNaturalAspenLogBlock.LEAVES).equals(ThinNaturalAspenLogBlock.MeadowLeavesType.TOP) ? 2 : 0);
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

    public static LodestoneBlockProperties ASPEN_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTags(MeadowBlockTagRegistry.CALCIFICATION_REPLACEABLE, MeadowBlockTagRegistry.MOOMOO_CAN_SPAWN_ON)
                .addTags(MeadowBlockTagRegistry.ASPEN_GRASS_CAN_PLACE_ON, MeadowBlockTagRegistry.GRASSY_PEARLFLOWER_GENERATES_ON)
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

    public static LodestoneBlockProperties ASPEN_GRASS_PROPERTIES() {
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
}
