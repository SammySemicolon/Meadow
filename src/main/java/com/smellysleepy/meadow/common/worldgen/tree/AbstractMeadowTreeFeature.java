package com.smellysleepy.meadow.common.worldgen.tree;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.block.meadow.flora.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public abstract class AbstractMeadowTreeFeature<FC extends FeatureConfiguration> extends Feature<FC> {
    public AbstractMeadowTreeFeature(Codec<FC> pCodec) {
        super(pCodec);
    }

    public boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();
        return block instanceof MeadowSaplingBlock || level.isEmptyBlock(pos) || state.canBeReplaced();
    }
}
