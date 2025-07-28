package com.smellysleepy.meadow.common.block.aspen.grass;

import com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import static com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper.VARIANT;

public class TallAspenGrass extends DoublePlantBlock {
    public TallAspenGrass(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return AspenGrassVariantHelper.getStateForBushPlacement(ctx.getLevel(), ctx.getClickedPos(), super.getStateForPlacement(ctx));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
        return AspenGrassVariantHelper.getStateForBushPlacement(accessor, currentPos, super.updateShape(state, facing, facingState, accessor, currentPos, facingPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(MeadowTags.BlockTags.ASPEN_GRASS_CAN_PLACE_ON);
    }
}
