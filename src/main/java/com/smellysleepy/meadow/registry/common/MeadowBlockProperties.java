package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.systems.block.*;

public class MeadowBlockProperties {

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
                .addTag(MeadowBlockTagRegistry.MEADOW_GRASS_GROUND)
                .addTag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND);
    }

    public static LodestoneBlockProperties MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .mapColor(MapColor.WOOD)
                .sound(SoundType.CHERRY_WOOD)
                .instrument(NoteBlockInstrument.BASS);
    }

    public static LodestoneBlockProperties THIN_MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .strength(1.25F, 4.0F)
                .mapColor(MapColor.WOOD)
                .sound(SoundType.CHERRY_WOOD)
                .pushReaction(PushReaction.DESTROY)
                .instrument(NoteBlockInstrument.BASS)
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
        return ASPEN_LEAVES_PROPERTIES().noCollission();
    }

    public static LodestoneBlockProperties MEADOW_GRASS_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .instabreak()
                .noCollission()
                .noOcclusion()
                .setCutoutRenderType();
    }

    public static LodestoneBlockProperties STRANGE_FLORA_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
                .instabreak()
                .randomTicks()
                .noCollission()
                .noOcclusion()
                .setCutoutRenderType()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .ignitedByLava();
    }
}
