package com.smellysleepy.meadow.common.block.meadow;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.systems.block.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class MeadowWallFungusBlock extends LodestoneHorizontalBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    protected static final VoxelShape EAST = Block.box(4.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
    protected static final VoxelShape WEST = Block.box(0.0D, 0.0D, 2.0D, 12.0D, 16.0D, 14.0D);
    protected static final VoxelShape SOUTH = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 16.0D, 16.0D);
    protected static final VoxelShape NORTH = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 12.0D);


    public MeadowWallFungusBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(HORIZONTAL_FACING)) {
            case EAST -> {
                return EAST;
            }
            case WEST -> {
                return WEST;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case NORTH -> {
                return NORTH;
            }
        }
        return super.getShape(pState, pLevel, pPos, pContext);
    }

}
