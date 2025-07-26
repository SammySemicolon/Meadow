package com.smellysleepy.meadow.common.block.mineral.leaves;

import com.smellysleepy.meadow.common.block.HangingLeavesBlock;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.function.Supplier;

public class HangingMineralLeavesBlock extends HangingLeavesBlock {

    private final Supplier<LodestoneWorldParticleType> particleType;
    public final Color color;

    public HangingMineralLeavesBlock(Properties pProperties, Supplier<LodestoneWorldParticleType> particleType, Color color) {
        super(pProperties);
        this.particleType = particleType;
        this.color = color;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(10) == 0) {
            BlockPos below = pPos.below();
            BlockState blockstate = pLevel.getBlockState(below);
            if (isFaceFull(blockstate.getCollisionShape(pLevel, below), Direction.UP)) {
                return;
            }
            double posX = (double) pPos.getX() + pRandom.nextDouble();
            double posY = (double) pPos.getY() - 0.05D;
            double posZ = (double) pPos.getZ() + pRandom.nextDouble();
            MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ), particleType.get()).spawnParticles();
        }
    }
}