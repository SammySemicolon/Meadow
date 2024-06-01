package com.smellysleepy.meadow.common.block.meadow.flora;

import com.smellysleepy.meadow.common.worldgen.tree.small.*;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class MeadowSaplingBlock extends SaplingBlock {
    public MeadowSaplingBlock(Properties pProperties) {
        super(new SmallMeadowTreeGrower(), pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.MEADOW_GRASS_GROUND);
    }
}