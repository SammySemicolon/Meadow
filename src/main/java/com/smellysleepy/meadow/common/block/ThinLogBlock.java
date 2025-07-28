package com.smellysleepy.meadow.common.block;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ThinLogBlock extends RotatedPillarBlock {

    protected static final VoxelShape SHAPE_X = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    protected static final VoxelShape SHAPE_Y = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);

    public ThinLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter pReader, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        switch (state.getValue(AXIS)) {
            case X -> {
                return SHAPE_X;
            }
            case Y -> {
                return SHAPE_Y;
            }
            case Z -> {
                return SHAPE_Z;
            }
        }
        return null;
    }
}