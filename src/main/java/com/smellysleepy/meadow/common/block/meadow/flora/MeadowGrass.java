package com.smellysleepy.meadow.common.block.meadow.flora;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class MeadowGrass extends TallGrassBlock {
    public MeadowGrass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.MEADOW_GRASS_CAN_PLACE_ON);
    }
}
