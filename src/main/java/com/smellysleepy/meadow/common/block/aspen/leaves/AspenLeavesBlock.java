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

    public AspenLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                double posX = (double)pos.getX() + random.nextDouble();
                double posY = (double)pos.getY() - 0.05D;
                double posZ = (double)pos.getZ() + random.nextDouble();

                MeadowParticleEffects.fallingLeaves(level, new Vec3(posX, posY, posZ), MeadowParticleRegistry.ASPEN_LEAVES.get()).spawnParticles();
            }
        }
    }
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos.below(), MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get().defaultBlockState(), 3);
    }
}