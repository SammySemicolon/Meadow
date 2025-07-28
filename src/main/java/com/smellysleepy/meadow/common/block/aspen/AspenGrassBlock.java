package com.smellysleepy.meadow.common.block.aspen;

import com.mojang.serialization.MapCodec;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper.VARIANT;

public class AspenGrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {

    public AspenGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return AspenGrassVariantHelper.getStateForPlacement(ctx.getClickedPos(), super.getStateForPlacement(ctx));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
        return AspenGrassVariantHelper.getStateForPlacement(currentPos, super.updateShape(state, facing, facingState, accessor, currentPos, facingPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }


    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap((features) -> features.getHolder(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_GRASS_BONEMEAL))
                .ifPresent((holder) -> holder.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }
}