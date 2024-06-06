package com.smellysleepy.meadow.common.block.meadow.leaves;

import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;

public class MeadowLeavesBlock extends LeavesBlock {

    public MeadowLeavesBlock(Properties properties) {
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

                var leaves = MeadowVisualEffects.meadowLeaves(pLevel, new Vec3(posX, posY, posZ), new WorldParticleOptions(MeadowParticleRegistry.ASPEN_LEAVES.get(), new SparkParticleBehavior(
                        GenericParticleData.create(0.075f, 0.035f, 0f).build()
                )));
                leaves.spawnParticles();
            }
        }
    }
}