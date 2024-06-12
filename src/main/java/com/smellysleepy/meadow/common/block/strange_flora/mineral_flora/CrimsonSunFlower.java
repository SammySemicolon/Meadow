package com.smellysleepy.meadow.common.block.strange_flora.mineral_flora;

import com.smellysleepy.meadow.common.block.strange_flora.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

public class CrimsonSunFlower extends AbstractStrangePlant {
    public CrimsonSunFlower(Properties pProperties) {
        super(pProperties);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(Tags.Blocks.ORES_REDSTONE);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(2) == 0) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                for (int i = 0; i < 4; i++) {
                    double posX = (double) pPos.getX() + 0.15f + pRandom.nextDouble() * 0.7f;
                    double posY = (double) pPos.getY() + 0.15f + pRandom.nextDouble() * 0.7f;
                    double posZ = (double) pPos.getZ() + 0.15f + pRandom.nextDouble() * 0.7f;

                    var dust = StrangeFloraParticleEffects.crimsonSun(pLevel, new Vec3(posX, posY, posZ));
                    dust.getBuilder().setLifeDelay(i*2);
                    dust.spawnParticles();
                }
            }
        }
    }
}
