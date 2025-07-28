package com.smellysleepy.meadow.registry.common.block.properties;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class MineralBlockProperties {
    public static LodestoneBlockProperties MINERAL_GRASS_BLOCK_PROPERTIES() {
        return new LodestoneBlockProperties()
                .addTags(MeadowTags.BlockTags.ROCKY_PEARLFLOWER_GENERATES_ON, MeadowTags.BlockTags.MINERAL_FLORA_CAN_PLACE_ON)
                .addTags(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL)
                .addTags(net.minecraft.tags.BlockTags.DIRT)
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
                .instabreak();
    }

    public static LodestoneBlockProperties MINERAL_SAPLING_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES().addTag(net.minecraft.tags.BlockTags.SAPLINGS);
    }

    public static LodestoneBlockProperties MINERAL_FLOWER_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES().addTag(net.minecraft.tags.BlockTags.TALL_FLOWERS);
    }

    public static LodestoneBlockProperties MINERAL_GRASS_PROPERTIES() {
        return MINERAL_FLORA_PROPERTIES()
                .replaceable();
    }

    public static LodestoneBlockProperties MINERAL_LEAVES_PROPERTIES() {
        return new LodestoneBlockProperties()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .sound(SoundType.CHERRY_LEAVES)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .addTag(net.minecraft.tags.BlockTags.LEAVES)
                .addTags(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE)
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
