package com.smellysleepy.meadow.common.block.aspen.grass;

import com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
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

import static com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper.VARIANT;

public class ShortAspenGrass extends TallGrassBlock {
    public ShortAspenGrass(Properties properties) {
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

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var tallGrassState = MeadowBlockRegistry.MEDIUM_ASPEN_GRASS.get().defaultBlockState();
        if (tallGrassState.canSurvive(level, pos)) {
            level.setBlock(pos, copyWaterloggedFrom(level, pos, tallGrassState), 3);
        }
    }

    public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(pos)) : state;
    }
}
