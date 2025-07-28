package com.smellysleepy.meadow.common.block.mineral.plant;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class MineralSaplingBlock extends SaplingBlock {
    public final TagKey<Block> tag;

    public MineralSaplingBlock(TreeGrower grower, Properties properties, TagKey<Block> tag) {
        super(grower, properties);
        this.tag = tag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(MeadowTags.BlockTags.MINERAL_FLORA_CAN_PLACE_ON) || state.is(tag);
    }
}