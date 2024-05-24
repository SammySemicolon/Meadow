package com.smellysleepy.meadow.registry.common;

import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.systems.block.*;

public class MeadowBlockProperties {

    public static LodestoneBlockProperties MEADOW_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GRASS)
                .randomTicks()
                .strength(0.6F)
                .sound(SoundType.GRASS)
                .addTag(MeadowBlockTagRegistry.MEADOW_GRASS_GROUND)
                .addTag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND);
    }

    public static LodestoneBlockProperties MEADOW_WOOD_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.WOOD)
                .sound(SoundType.CHERRY_WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(1.75F, 4.0F);
    }

    public static LodestoneBlockProperties MEADOW_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(BlockTags.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .sound(SoundType.CHERRY_LEAVES)
                .setCutoutRenderType()
                .needsHoe();
    }

    public static LodestoneBlockProperties MEADOW_GRASS_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GRASS)
                .instabreak()
                .noCollission()
                .noOcclusion()
                .setCutoutRenderType()
                .sound(SoundType.GRASS);
    }

    public static LodestoneBlockProperties STRANGE_FLORA_PROPERTIES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GRASS)
                .instabreak()
                .noCollission()
                .noOcclusion()
                .setCutoutRenderType()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.GRASS);
    }
}
