package com.smellysleepy.meadow.common.block.meadow.wood;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.common.*;

public class ThinMeadowLogBlock extends RotatedPillarBlock {

    protected static final VoxelShape SHAPE_X = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    protected static final VoxelShape SHAPE_Y = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);

    public ThinMeadowLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(AXIS)) {
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