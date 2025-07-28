package com.smellysleepy.meadow.common.block.pearlflower.wilted;

import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerReplacementHandler;
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

@SuppressWarnings({"deprecation", "NullableProblems"})
public class WiltedTallPearlFlowerBlock extends TallPearlFlowerBlock {

    public WiltedTallPearlFlowerBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.01f) {
            PearlFlowerReplacementHandler.performExchange(this, level, pos, state);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return InteractionResult.PASS;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        PearlFlowerReplacementHandler.performExchange(this, level, pos, state);
    }
}
