package com.smellysleepy.meadow.common.block.aspen.leaves;

import com.smellysleepy.meadow.common.block.HangingLeavesBlock;
import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import net.minecraft.core.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;

public class HangingAspenLeavesBlock extends HangingLeavesBlock {

    public HangingAspenLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(10) == 0) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                return;
            }
            double posX = (double) pPos.getX() + pRandom.nextDouble();
            double posY = (double) pPos.getY() - 0.05D;
            double posZ = (double) pPos.getZ() + pRandom.nextDouble();

            MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ), MeadowParticleRegistry.ASPEN_LEAVES.get()).spawnParticles();
        }
    }
}