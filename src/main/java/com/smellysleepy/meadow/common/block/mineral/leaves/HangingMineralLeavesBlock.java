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

    public HangingMineralLeavesBlock(Properties properties, Supplier<LodestoneWorldParticleType> particleType, Color color) {
        super(properties);
        this.particleType = particleType;
        this.color = color;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos below = pos.below();
            BlockState blockstate = level.getBlockState(below);
            if (isFaceFull(blockstate.getCollisionShape(level, below), Direction.UP)) {
                return;
            }
            double posX = (double) pos.getX() + random.nextDouble();
            double posY = (double) pos.getY() - 0.05D;
            double posZ = (double) pos.getZ() + random.nextDouble();
            MeadowParticleEffects.fallingLeaves(level, new Vec3(posX, posY, posZ), particleType.get()).spawnParticles();
        }
    }
}