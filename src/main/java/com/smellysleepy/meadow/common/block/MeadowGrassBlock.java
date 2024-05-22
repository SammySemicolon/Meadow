package com.smellysleepy.meadow.common.block;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class MeadowGrassBlock extends TallGrassBlock {
    public MeadowGrassBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()).getBlock().equals(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get())) {
            return true;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }
}
