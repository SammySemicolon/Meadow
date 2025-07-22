package com.smellysleepy.meadow.common.block.pearlflower.wilted;

import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerReplacements;
import com.smellysleepy.meadow.common.block.pearlflower.TallPearlFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WiltedTallPearlFlowerBlock extends TallPearlFlowerBlock {
    public WiltedTallPearlFlowerBlock(Properties pProperties) {
        super(pProperties.randomTicks());
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.01f) {
            PearlFlowerReplacements.performExchange(this, pLevel, pPos, pState);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return InteractionResult.PASS;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        PearlFlowerReplacements.performExchange(this, pLevel, pPos, pState);
    }
}
