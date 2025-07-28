package com.smellysleepy.meadow.common.block.fungi;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ThinPartiallyCalcifiedChanterelleStemBlock extends ThinNaturalChanterelleStemBlock {

    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");

    public ThinPartiallyCalcifiedChanterelleStemBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FLIPPED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FLIPPED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) {
            direction = direction.getOpposite();
        }
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(FLIPPED, direction.getAxisDirection().equals(Direction.AxisDirection.POSITIVE));
        }
        return state;
    }
}
