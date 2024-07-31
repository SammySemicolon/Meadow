package com.smellysleepy.meadow.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PearlflowerFeature extends Feature<PearlFlowerConfiguration> {

    public PearlflowerFeature() {
        super(PearlFlowerConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<PearlFlowerConfiguration> context) {
        PearlFlowerConfiguration config = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();

        BlockState below = worldgenlevel.getBlockState(blockpos.below());
        BlockStateProvider provider = config.meadow();
        boolean isWaterlogged = worldgenlevel.isWaterAt(blockpos);
        if (below.is(MeadowBlockTagRegistry.CALCIFICATION)) {
            provider = config.calcified();
        }
        else if ((below.is(BlockTags.BASE_STONE_OVERWORLD)) && !isWaterlogged){
            provider = config.stone();
        }
        else if (below.is(BlockTags.MOSS_REPLACEABLE) || isWaterlogged) {
            provider = config.marine();
        }

        BlockState blockstate = provider.getState(context.random(), blockpos);
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
                worldgenlevel.setBlock(blockpos, blockstate, 2);
            }

            return true;
        } else {
            return false;
        }
    }

}
