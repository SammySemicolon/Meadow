package com.smellysleepy.meadow.common.block.aspen.grass;

import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class MediumAspenGrass extends ShortAspenGrass {
    public MediumAspenGrass(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        Block doubleplantblock = MeadowBlockRegistry.TALL_ASPEN_GRASS.get();
        if (doubleplantblock.defaultBlockState().canSurvive(pLevel, pPos) && pLevel.isEmptyBlock(pPos.above())) {
            DoublePlantBlock.placeAt(pLevel, doubleplantblock.defaultBlockState(), pPos, 2);
        }
    }
}
