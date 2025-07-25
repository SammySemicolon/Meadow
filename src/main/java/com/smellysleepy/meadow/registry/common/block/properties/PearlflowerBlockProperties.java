package com.smellysleepy.meadow.registry.common.block.properties;

import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class PearlflowerBlockProperties {
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
                .randomTicks()
                .instabreak();
    }

    public static LodestoneBlockProperties SMALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(BlockTags.SMALL_FLOWERS).addTags(MeadowBlockTagRegistry.MOOMOO_EDIBLE);
    }

    public static LodestoneBlockProperties TALL_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(BlockTags.TALL_FLOWERS).addTags(MeadowBlockTagRegistry.MOOMOO_EDIBLE);
    }

    public static LodestoneBlockProperties SMALL_WILTED_PEARLFLOWER_PROPERTIES() {
        return PEARLFLOWER_PROPERTIES().addTag(BlockTags.SMALL_FLOWERS);
    }

    public static LodestoneBlockProperties TALL_WILTED_PEARLFLOWER_PROPERTIES() {
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
}
