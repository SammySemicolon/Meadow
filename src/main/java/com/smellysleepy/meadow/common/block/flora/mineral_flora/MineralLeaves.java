package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;

public class MineralLeaves extends LeavesBlock {

    public final Color color;

    public MineralLeaves(Properties pProperties, Color color) {
        super(pProperties);
        this.color = color;
    }
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(6) == 0) {
            double posX = (double) pPos.getX() + 0.1f + pRandom.nextDouble() * 0.8f;
            double posY = (double) pPos.getY() + 0.1f + pRandom.nextDouble() * 0.8f;
            double posZ = (double) pPos.getZ() + 0.1f + pRandom.nextDouble() * 0.8f;

            var dust = StrangeFloraParticleEffects.mineralFloraShine(pLevel, new Vec3(posX, posY, posZ));
            dust.spawnParticles();
        }
        if (pRandom.nextInt(10) == 0) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                double posX = (double)pPos.getX() + pRandom.nextDouble();
                double posY = (double)pPos.getY() - 0.05D;
                double posZ = (double)pPos.getZ() + pRandom.nextDouble();

                var leaves = MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ));
                leaves.getBuilder().setColorData(ColorParticleData.create(color).build());
                leaves.spawnParticles();
            }
        }
    }

}
