package com.smellysleepy.meadow.common.block.mineral;

import com.smellysleepy.meadow.common.worldgen.feature.tree.SimpleTreeGrower;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MineralSaplingBlock extends SaplingBlock {
    public final TagKey<Block> tag;

    public MineralSaplingBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> feature, TagKey<Block> tag) {
        this(new SimpleTreeGrower(feature), pProperties, tag);
    }

    public MineralSaplingBlock(AbstractTreeGrower grower, Properties pProperties, TagKey<Block> tag) {
        super(grower, pProperties);
        this.tag = tag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.MINERAL_FLORA_CAN_PLACE_ON) || pState.is(tag);
    }
}