package com.smellysleepy.meadow.common.block.aspen;

import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;

public class AspenSaplingBlock extends SaplingBlock {
    public AspenSaplingBlock(TreeGrower treeGrower, Properties pProperties) {
        super(treeGrower, pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.ASPEN_SAPLING_CAN_PLACE_ON);
    }
}