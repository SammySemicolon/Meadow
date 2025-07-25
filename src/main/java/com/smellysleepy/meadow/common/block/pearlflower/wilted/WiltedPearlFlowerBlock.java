package com.smellysleepy.meadow.common.block.pearlflower.wilted;

import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerReplacementHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class WiltedPearlFlowerBlock extends PearlFlowerBlock implements BonemealableBlock {

    public WiltedPearlFlowerBlock(Properties pProperties) {
        super(pProperties.randomTicks());
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.02f) {
            PearlFlowerReplacementHandler.performExchange(this, pLevel, pPos);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        PearlFlowerReplacementHandler.performExchange(this, pLevel, pPos);
    }
}
