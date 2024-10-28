package com.smellysleepy.meadow.common.worldgen.feature;

import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;

public class PearlflowerFeature extends Feature<PearlFlowerConfiguration> {

    public PearlflowerFeature() {
        super(PearlFlowerConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<PearlFlowerConfiguration> context) {
        var config = context.config();
        var worldgenlevel = context.level();
        var blockpos = context.origin();

        var below = worldgenlevel.getBlockState(blockpos.below());
        BlockStateProvider provider = null;
        boolean isWaterlogged = worldgenlevel.isWaterAt(blockpos);

        if (isWaterlogged || below.is(MeadowBlockTagRegistry.MARINE_PEARLFLOWER_GENERATES_ON)) {
            provider = config.marine();
        }
        else if (below.is(MeadowBlockTagRegistry.GRASSY_PEARLFLOWER_GENERATES_ON)) {
            provider = config.grassy();
        }
        else if (below.is(MeadowBlockTagRegistry.ROCKY_PEARLFLOWER_GENERATES_ON)) {
            provider = config.rocky();
        }
        else if (below.is(MeadowBlockTagRegistry.CALCIFIED_PEARLFLOWER_GENERATES_ON)) {
            provider = config.calcified();
        }
        if (provider == null) {
            return false;
        }
        var blockstate = provider.getState(context.random(), blockpos);
        if (blockstate.canSurvive(worldgenlevel, blockpos)) {
            if (!worldgenlevel.getBlockState(blockpos).canBeReplaced()) {
                return false;
            }
            if (blockstate.getBlock() instanceof DoublePlantBlock) {
                if (!worldgenlevel.getBlockState(blockpos.above()).canBeReplaced()) {
                    return false;
                }

                DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
            } else {
                worldgenlevel.setBlock(blockpos, copyWaterloggedFrom(worldgenlevel, blockpos, blockstate), 2);
            }

            return true;
        } else {
            return false;
        }
    }

    public static BlockState copyWaterloggedFrom(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pState.hasProperty(BlockStateProperties.WATERLOGGED) ? pState.setValue(BlockStateProperties.WATERLOGGED, pLevel.isWaterAt(pPos)) : pState;
    }
}
