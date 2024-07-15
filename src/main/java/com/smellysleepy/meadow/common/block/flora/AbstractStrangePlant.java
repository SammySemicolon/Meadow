package com.smellysleepy.meadow.common.block.flora;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

public abstract class AbstractStrangePlant extends TallGrassBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public static final BooleanProperty STONE = BooleanProperty.create("stone");

    public AbstractStrangePlant(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STONE, false));
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STONE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        final BlockState stateForPlacement = super.getStateForPlacement(pContext);
        if (pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return stateForPlacement.setValue(STONE, true);
        }
        return stateForPlacement;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        final BlockState state = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (state.is(Blocks.AIR)) {
            return state;
        }
        if (pFacing == Direction.DOWN && pFacingState.is(BlockTags.BASE_STONE_OVERWORLD)) {
            state.setValue(STONE, true);
        }
        else {
            state.setValue(STONE, false);
        }
        return state;
    }
}
