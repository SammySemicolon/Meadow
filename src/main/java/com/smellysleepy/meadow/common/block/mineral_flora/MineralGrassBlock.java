package com.smellysleepy.meadow.common.block.mineral_flora;

import com.smellysleepy.meadow.common.block.meadow.flora.grass.MeadowGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MineralGrassBlock extends Block implements BonemealableBlock {

    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

    public MineralGrassBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature) {
        super(pProperties);
        this.bonemealFeature = bonemealFeature;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!MeadowGrassBlock.canBeGrass(pState, pLevel, pPos)) {
            if (!pLevel.isAreaLoaded(pPos, 1))
                return;
            pLevel.setBlockAndUpdate(pPos, Blocks.DIRT.defaultBlockState());
        }
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
        var optional = configuredFeatures.getHolder(bonemealFeature);

        label49:
        for (int i = 0; i < 128; ++i) {
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
                if (optional.isPresent()) {
                    optional.get().value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, offset);
                }
            }
        }
    }
}
