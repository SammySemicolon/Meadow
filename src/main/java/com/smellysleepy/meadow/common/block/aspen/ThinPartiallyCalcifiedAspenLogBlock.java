package com.smellysleepy.meadow.common.block.aspen;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.function.Supplier;

public class ThinPartiallyCalcifiedAspenLogBlock extends ThinNaturalAspenLogBlock {

    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");

    public ThinPartiallyCalcifiedAspenLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
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
