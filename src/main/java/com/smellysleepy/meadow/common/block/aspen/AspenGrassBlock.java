package com.smellysleepy.meadow.common.block.aspen;

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

    public AspenGrassBlock(Properties pProperties) {
        super(pProperties);
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
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = Blocks.GRASS.defaultBlockState();
        var configuredFeatures = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        var optional = configuredFeatures.getHolder(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_GRASS_BONEMEAL);

        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos offset = blockpos;

            for (int j = 0; j < i / 16; ++j) {
                offset = offset.offset(pRandom.nextInt(3) - 1, (pRandom.nextInt(3) - 1) * pRandom.nextInt(3) / 2, pRandom.nextInt(3) - 1);
                if (!pLevel.getBlockState(offset.below()).is(this) || pLevel.getBlockState(offset).isCollisionShapeFullBlock(pLevel, offset)) {
                    continue label49;
                }
            }

            BlockState offsetState = pLevel.getBlockState(offset);
            if (offsetState.is(blockstate.getBlock()) && pRandom.nextInt(10) == 0) {
                ((BonemealableBlock) blockstate.getBlock()).performBonemeal(pLevel, pRandom, offset, offsetState);
            }

            if (offsetState.isAir()) {
                Holder<ConfiguredFeature<?, ?>> holder = null;
                if (pRandom.nextInt(24) == 0) {
                    var pearlflowerOptional = configuredFeatures.getHolder(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER);
                    if (pearlflowerOptional.isPresent()) {
                        holder = pearlflowerOptional.get();
                    }
                } else {
                    if (optional.isEmpty()) {
                        continue;
                    }

                    holder = optional.get();
                }
                if (holder != null) {
                    holder.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, offset);
                }
            }
        }
    }
}
