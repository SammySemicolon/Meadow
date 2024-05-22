package com.smellysleepy.meadow.common.block;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class DoubleMeadowGrassBlock extends DoublePlantBlock {
    public DoubleMeadowGrassBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(HALF).equals(DoubleBlockHalf.LOWER) && pLevel.getBlockState(pPos.below()).getBlock().equals(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get())) {
            return true;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }
}
