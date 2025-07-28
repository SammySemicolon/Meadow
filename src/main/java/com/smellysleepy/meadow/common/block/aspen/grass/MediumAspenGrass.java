package com.smellysleepy.meadow.common.block.aspen.grass;

import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class MediumAspenGrass extends ShortAspenGrass {
    public MediumAspenGrass(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Block doubleplantblock = MeadowBlockRegistry.TALL_ASPEN_GRASS.get();
        if (doubleplantblock.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(level, doubleplantblock.defaultBlockState(), pos, 2);
        }
    }
}
