package com.smellysleepy.meadow.common.block.meadow.wood;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.datafix.fixes.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class RootedMeadowBlock extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    public static final BooleanProperty[] MAP = new BooleanProperty[]{NORTH, SOUTH, WEST, EAST};

    public RootedMeadowBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, SOUTH, WEST, EAST);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        final BlockState stateForPlacement = super.getStateForPlacement(pContext);
        return getConnectedState(pContext.getLevel(), stateForPlacement, pContext.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        final BlockState state = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (state.is(Blocks.AIR)) {
            return state;
        }
        if (pLevel.getBlockState(pCurrentPos.below()).getBlock().equals(this)) {
            return MeadowBlockRegistry.CALCIFIED_MEADOW_LOG.get().defaultBlockState();
        }
        return getConnectedState(pLevel, state, pCurrentPos);
    }

    public static BlockState getConnectedState(LevelAccessor pLevel, BlockState currentState, BlockPos pos) {
        BlockState state = currentState;

        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        for (int i = 0; i < 4; i++) {
            Direction direction = Direction.from3DDataValue(i + 2);
            mutableBlockPos.move(direction).move(0, -1, 0);
            final boolean canSupport = Block.canSupportCenter(pLevel, mutableBlockPos, Direction.UP);
            state = state.setValue(MAP[i], canSupport);
            mutableBlockPos.move(direction.getOpposite()).move(0, 1, 0);
        }
        return state;
    }

    public static BlockState convertToRootedLog(LevelAccessor level, BlockState state, BlockPos pos) {
        final BlockPos below = pos.below();
        Direction.Axis axis = state.hasProperty(BlockStateProperties.AXIS) ? state.getValue(BlockStateProperties.AXIS) : state.getValue(BlockStateProperties.FACING).getAxis();
        if (Block.canSupportCenter(level, below, Direction.UP) && axis.equals(Direction.Axis.Y)) {
            final BlockState belowState = level.getBlockState(below);
            if (belowState.getBlock().equals(state.getBlock()) || belowState.getBlock() instanceof RootedMeadowBlock) {
                return state;
            }
            return RootedMeadowBlock.getConnectedState(level, MeadowBlockRegistry.ROOTED_CALCIFIED_MEADOW_LOG.get().defaultBlockState(), pos);
        }
        return state;
    }
}