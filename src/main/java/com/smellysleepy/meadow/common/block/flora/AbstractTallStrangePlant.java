package com.smellysleepy.meadow.common.block.flora;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public abstract class AbstractTallStrangePlant extends TallFlowerBlock {
    public AbstractTallStrangePlant(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND);
    }
}