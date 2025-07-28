package com.smellysleepy.meadow.common.block.calcification;

import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.features.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;

public class CalcifiedBlock extends Block implements BonemealableBlock {

    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

    public CalcifiedBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature) {
        super(properties);
        this.bonemealFeature = bonemealFeature;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        var above = level.getBlockState(pos.above());
        return above.is(MeadowBlockRegistry.CALCIFIED_COVERING.get()) || above.isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap((features) -> features.getHolder(bonemealFeature))
                .ifPresent((holder) -> holder.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }
}