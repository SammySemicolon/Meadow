package com.smellysleepy.meadow.common.block.meadow.flora;

import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;

public class AspenSaplingBlock extends SaplingBlock {
    public AspenSaplingBlock(AbstractTreeGrower grower, Properties pProperties) {
        super(grower, pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.ASPEN_SAPLING_CAN_PLACE_ON);
    }
}