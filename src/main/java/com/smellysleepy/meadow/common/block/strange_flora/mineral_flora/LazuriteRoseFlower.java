package com.smellysleepy.meadow.common.block.strange_flora.mineral_flora;

import com.smellysleepy.meadow.common.block.strange_flora.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.common.*;

public class LazuriteRoseFlower extends AbstractStrangePlant {
    public LazuriteRoseFlower(Properties pProperties) {
        super(pProperties);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(Tags.Blocks.ORES_LAPIS);
    }
}
