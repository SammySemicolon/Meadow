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
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                return;
            }
            double posX = (double) pos.getX() + random.nextDouble();
            double posY = (double) pos.getY() - 0.05D;
            double posZ = (double) pos.getZ() + random.nextDouble();

            MeadowParticleEffects.fallingLeaves(level, new Vec3(posX, posY, posZ), MeadowParticleRegistry.ASPEN_LEAVES.get()).spawnParticles();
        }
    }
}