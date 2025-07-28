package com.smellysleepy.meadow.registry.common.block.properties;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class PearlflowerBlockProperties {
    public static LodestoneBlockProperties PEARLFLOWER_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(net.minecraft.tags.BlockTags.FLOWERS)
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .sound(SoundType.FLOWERING_AZALEA)
                .setCutoutRenderType()
                .lightLevel(s -> 12)
                .noCollission()
                .noOcclusion()
                .randomTicks()
                .instabreak();
    }

    public static LodestoneBlockProperties SMALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(net.minecraft.tags.BlockTags.SMALL_FLOWERS).addTags(MeadowTags.BlockTags.MOOMOO_EDIBLE);
    }

    public static LodestoneBlockProperties TALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(net.minecraft.tags.BlockTags.TALL_FLOWERS).addTags(MeadowTags.BlockTags.MOOMOO_EDIBLE);
    }

    public static LodestoneBlockProperties SMALL_WILTED_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(net.minecraft.tags.BlockTags.SMALL_FLOWERS);
    }

    public static LodestoneBlockProperties TALL_WILTED_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(net.minecraft.tags.BlockTags.TALL_FLOWERS);
    }

    public static LodestoneBlockProperties PEARLLIGHT_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE)
                .mapColor(MapColor.COLOR_YELLOW)
                .sound(SoundType.SHROOMLIGHT)
                .strength(1.0F)
                .lightLevel((state) -> 15);
    }
}
