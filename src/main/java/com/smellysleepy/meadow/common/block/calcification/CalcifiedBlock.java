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

    public CalcifiedBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature) {
        super(pProperties);
        this.bonemealFeature = bonemealFeature;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        var above = pLevel.getBlockState(pPos.above());
        return above.is(MeadowBlockRegistry.CALCIFIED_COVERING.get()) || above.isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pLevel.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap((features) -> features.getHolder(bonemealFeature))
                .ifPresent((holder) -> holder.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos.above()));
    }
}