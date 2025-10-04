package com.smellysleepy.meadow.common.block.pearlflower.wilted;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerReplacementHandler;
import com.smellysleepy.meadow.common.block.pearlflower.TallPearlFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings({"NullableProblems"})
public class WiltedTallPearlFlowerBlock extends TallPearlFlowerBlock {

    public static final MapCodec<TallFlowerBlock> CODEC = simpleCodec(WiltedTallPearlFlowerBlock::new);

    public WiltedTallPearlFlowerBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    public MapCodec<TallFlowerBlock> codec() {
        return CODEC;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.01f) {
            PearlFlowerReplacementHandler.performExchange(this, level, pos, state);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        PearlFlowerReplacementHandler.performExchange(this, level, pos, state);
    }
}
