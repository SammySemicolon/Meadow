package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.systems.block.*;

public class MeadowBlockProperties {

    public static LodestoneBlockProperties CALCIFIED_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(2.5F, 4.0F)
                .addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND)
                .mapColor(MapColor.COLOR_BLUE)
                .sound(SoundType.NETHERRACK);
    }

    public static LodestoneBlockProperties WALL_FUNGUS_PROPERTIES() {
        return new LodestoneBlockProperties()
                .randomTicks()
                .strength(0.2F, 2.0F)
                .noCollission()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .setCutoutRenderType()
                .needsHoe();
    }

    public static LodestoneBlockProperties MEADOW_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .randomTicks()
                .strength(0.6F)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .addTag(MeadowBlockTagRegistry.MEADOW_GRASS_GROUND);
    }

    public static LodestoneBlockProperties MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties THIN_MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.25F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.CHERRY_WOOD)
                .mapColor(MapColor.WOOD)
                .setCutoutRenderType();
    }

    public static LodestoneBlockProperties ASPEN_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .setCutoutRenderType()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .sound(SoundType.CHERRY_LEAVES)
                .needsHoe()
                .addTag(BlockTags.LEAVES);
    }

    public static LodestoneBlockProperties HANGING_ASPEN_LEAVES_PROPERTIES() {
        return ASPEN_LEAVES_PROPERTIES()
                .noCollission()
                .lightLevel(s -> 8);
    }


    public static LodestoneBlockProperties MEADOW_GRASS_PROPERTIES() {
        return new LodestoneBlockProperties()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .setCutoutRenderType()
                .lightLevel(s -> 6)
                .noCollission()
                .noOcclusion()
                .replaceable()
                .instabreak();
    }

    public static LodestoneBlockProperties MINERAL_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .randomTicks()
                .strength(0.6F)
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .addTag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND);
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
                .randomTicks()
                .instabreak();
    }

    public static LodestoneBlockProperties MINERAL_GRASS_PROPERTIES() {
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

    public static LodestoneBlockProperties MINERAL_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND)
                .isValidSpawn(Blocks::ocelotOrParrot)
                .sound(SoundType.CHERRY_LEAVES)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .addTag(BlockTags.LEAVES)
                .setCutoutRenderType()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .needsHoe();
    }
}
