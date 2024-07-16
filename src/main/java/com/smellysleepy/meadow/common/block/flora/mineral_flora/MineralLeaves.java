package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;

public class MineralLeaves extends LeavesBlock {
    public MineralLeaves(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(6) == 0) {
            for (int i = 0; i < 2; i++) {
                double posX = (double) pPos.getX() + 0.1f + pRandom.nextDouble() * 0.8f;
                double posY = (double) pPos.getY() + 0.1f + pRandom.nextDouble() * 0.8f;
                double posZ = (double) pPos.getZ() + 0.1f + pRandom.nextDouble() * 0.8f;

                var dust = StrangeFloraParticleEffects.mineralFlora(pLevel, new Vec3(posX, posY, posZ));
                dust.getBuilder().setLifeDelay(i * 2);
                dust.spawnParticles();
            }
        }
    }
}
