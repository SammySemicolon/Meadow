package com.smellysleepy.meadow.common.block.aspen;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;

public class AspenSaplingBlock extends SaplingBlock {
    public AspenSaplingBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(MeadowTags.BlockTags.ASPEN_SAPLING_CAN_PLACE_ON);
    }
}