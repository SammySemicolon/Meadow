package com.smellysleepy.meadow.common.block;

import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingLeavesBlock extends LeavesBlock {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public HangingLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean decaying(BlockState state) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(DISTANCE)) {
                int distance = Math.min(pFacingState.getValue(DISTANCE)+1, 7);
                return super.updateShape(state.setValue(DISTANCE, distance), pFacing, pFacingState, level, pCurrentPos, pFacingPos);
            }
        }
        return !state.canSurvive(level, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, pFacing, pFacingState, level, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState blockstate = level.getBlockState(above);
        return isFaceFull(blockstate.getCollisionShape(level, above), Direction.DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        return SHAPE;
    }
}