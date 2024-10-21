package com.smellysleepy.meadow.common.block.meadow.flora.grass;

import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.smellysleepy.meadow.common.block.meadow.flora.grass.MeadowGrassVariantHelper.VARIANT;

public class ShortMeadowGrass extends TallGrassBlock {
    public ShortMeadowGrass(Properties pProperties) {
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

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        var tallGrassState = MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get().defaultBlockState();
        if (tallGrassState.canSurvive(pLevel, pPos)) {
            pLevel.setBlock(pPos, copyWaterloggedFrom(pLevel, pPos, tallGrassState), 3);
        }
    }

    public static BlockState copyWaterloggedFrom(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pState.hasProperty(BlockStateProperties.WATERLOGGED) ? pState.setValue(BlockStateProperties.WATERLOGGED, pLevel.isWaterAt(pPos)) : pState;
    }
}
