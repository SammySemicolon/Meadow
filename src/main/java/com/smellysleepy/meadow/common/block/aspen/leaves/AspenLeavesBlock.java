package com.smellysleepy.meadow.common.block.aspen.leaves;

import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import java.awt.*;

public class AspenLeavesBlock extends LeavesBlock implements BonemealableBlock {

    public static final Color ASPEN_LEAVES_COLOR = new Color(255, 192, 27);

    public AspenLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(10) == 0) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                double posX = (double)pPos.getX() + pRandom.nextDouble();
                double posY = (double)pPos.getY() - 0.05D;
                double posZ = (double)pPos.getZ() + pRandom.nextDouble();

                MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ), MeadowParticleRegistry.ASPEN_LEAVES.get()).spawnParticles();
            }
        }
    }
    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos.below(), MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get().defaultBlockState(), 3);
    }
}