package com.smellysleepy.meadow.common.block.meadow.wood;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.block.*;

import static com.smellysleepy.meadow.common.block.meadow.wood.RootedMeadowBlock.*;

public class CalcifiedMeadowLogBlock extends LodestoneLogBlock {
    public CalcifiedMeadowLogBlock(Properties p_49795_) {
        super(p_49795_, null);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return convertToRootedLog(context.getLevel(), super.getStateForPlacement(context), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return convertToRootedLog(pLevel, pState, pPos);
    }
}