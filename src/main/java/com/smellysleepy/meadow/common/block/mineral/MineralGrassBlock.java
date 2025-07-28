package com.smellysleepy.meadow.common.block.mineral;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MineralGrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {

    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

    public MineralGrassBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature) {
        super(properties);
        this.bonemealFeature = bonemealFeature;
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
        BlockPos blockpos = pos.above();
        BlockState blockstate = Blocks.GRASS.defaultBlockState();
        var configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        var holder = configuredFeatures.getHolder(bonemealFeature);

        label49:
        for (int i = 0; i < 128; ++i) {
            BlockPos offset = blockpos;

            for (int j = 0; j < i / 16; ++j) {
                offset = offset.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(offset.below()).is(this) || level.getBlockState(offset).isCollisionShapeFullBlock(level, offset)) {
                    continue label49;
                }
            }

            BlockState offsetState = level.getBlockState(offset);
            if (offsetState.is(blockstate.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock) blockstate.getBlock()).performBonemeal(level, random, offset, offsetState);
            }

            if (offsetState.isAir()) {
                if (holder.isPresent()) {
                    holder.get().value().place(level, level.getChunkSource().getGenerator(), random, offset);
                }
            }
        }
    }
}
