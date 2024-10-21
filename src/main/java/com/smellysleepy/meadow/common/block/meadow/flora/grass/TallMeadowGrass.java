package com.smellysleepy.meadow.common.block.meadow.flora.grass;

import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import static com.smellysleepy.meadow.common.block.meadow.flora.grass.MeadowGrassVariantHelper.VARIANT;

public class TallMeadowGrass extends DoublePlantBlock {
    public TallMeadowGrass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return MeadowGrassVariantHelper.getStateForBushPlacement(ctx.getLevel(), ctx.getClickedPos(), super.getStateForPlacement(ctx));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
        return MeadowGrassVariantHelper.getStateForBushPlacement(accessor, currentPos, super.updateShape(state, facing, facingState, accessor, currentPos, facingPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.MEADOW_GRASS_CAN_PLACE_ON);
    }
}
