package com.smellysleepy.meadow.registry.common.block.properties;

import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.systems.block.*;

public class FungalBlockProperties {

    public static LodestoneBlockProperties CHANTERELLE_PROPERTIES() {
        return new LodestoneBlockProperties()
                .instrument(NoteBlockInstrument.BASS)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .pushReaction(PushReaction.DESTROY)
                .mapColor(MapColor.WOOD);
    }

    public static LodestoneBlockProperties CHANTERELLE_CAP_PROPERTIES() {
        return CHANTERELLE_PROPERTIES()
                .strength(2F, 4.0F)
                .sound(SoundType.NETHER_WOOD);
    }

    public static LodestoneBlockProperties CHANTERELLE_STEM_PROPERTIES() {
        return CHANTERELLE_PROPERTIES()
                .strength(0.5F, 4.0F)
                .sound(SoundType.NETHER_WOOD);
    }

    public static LodestoneBlockProperties THIN_CHANTERELLE_STEM_PROPERTIES() {
        return CHANTERELLE_PROPERTIES()
                .strength(0.25F, 4.0F)
                .sound(SoundType.NETHER_WOOD);
    }
}