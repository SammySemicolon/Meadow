package com.smellysleepy.meadow.registry.common.block.properties;

import com.smellysleepy.meadow.common.block.aspen.ThinNaturalAspenLogBlock;
import com.smellysleepy.meadow.registry.common.MeadowSoundRegistry;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class CalcifiedBlockProperties {

    public static LodestoneBlockProperties CALCIFIED_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.75F, 4.0F)
                .addTags(MeadowBlockTagRegistry.CALCIFICATION, MeadowBlockTagRegistry.CALCIFIED_PEARLFLOWER_GENERATES_ON)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(MeadowSoundRegistry.CALCIFIED_ROCK);
    }

    public static LodestoneBlockProperties CALCIFIED_BRICK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1F, 4.0F)
                .addTags(MeadowBlockTagRegistry.CALCIFICATION)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(MeadowSoundRegistry.CALCIFIED_BRICK);
    }

    public static LodestoneBlockProperties HEAVY_CALCIFIED_BRICK_PROPERTIES() {
        return CALCIFIED_BRICK_PROPERTIES().sound(MeadowSoundRegistry.HEAVY_CALCIFIED_BRICK);
    }

    public static LodestoneBlockProperties CALCIFIED_DRIPSTONE_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.5F, 4.0F)
                .addTags(MeadowBlockTagRegistry.CALCIFICATION)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(MeadowSoundRegistry.CALCIFIED_ROCK)
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
                .sound(MeadowSoundRegistry.CALCIFIED_COVERING);
    }

    public static LodestoneBlockProperties CALCIFIED_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(2F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(MeadowBlockTagRegistry.CALCIFICATION)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .sound(MeadowSoundRegistry.CALCIFIED_WOOD)
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
                .strength(1.5F, 4.0F)
                .setCutoutRenderType()
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .sound(MeadowSoundRegistry.CALCIFIED_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties THIN_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_WOOD_PROPERTIES().addTag(BlockTags.LOGS);
    }

    public static LodestoneBlockProperties THIN_STRIPPED_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_LOG_PROPERTIES().addTag(MeadowBlockTagRegistry.STRIPPED_LOGS);
    }

    public static LodestoneBlockProperties NATURAL_THIN_CALCIFIED_LOG_PROPERTIES() {
        return THIN_CALCIFIED_LOG_PROPERTIES().lightLevel(s -> s.getValue(ThinNaturalAspenLogBlock.LEAVES).equals(ThinNaturalAspenLogBlock.MeadowLeavesType.TOP) ? 2 : 0);
    }
}
