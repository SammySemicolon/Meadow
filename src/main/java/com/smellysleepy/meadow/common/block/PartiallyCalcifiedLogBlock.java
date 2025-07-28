package com.smellysleepy.meadow.common.block;

import net.minecraft.core.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

import java.util.function.Supplier;

@SuppressWarnings({"NullableProblems"})
public class PartiallyCalcifiedLogBlock extends LodestoneLogBlock {

    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");

    public PartiallyCalcifiedLogBlock(Properties properties, Supplier<Block> stripped) {
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
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            Direction direction = context.getClickedFace();
            Player player = context.getPlayer();
            if (player == null || !player.isShiftKeyDown()) {
                direction = direction.getOpposite();
            }
            state = state.setValue(FLIPPED, direction.getAxisDirection().equals(Direction.AxisDirection.POSITIVE));
        }
        return state;
    }
}