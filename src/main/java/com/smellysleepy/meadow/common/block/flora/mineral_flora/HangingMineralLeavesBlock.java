package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.block.meadow.leaves.MeadowLeavesBlock;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;

public class HangingMineralLeavesBlock extends LeavesBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public final Color color;

    public HangingMineralLeavesBlock(Properties pProperties, Color color) {
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

    @Override
    protected boolean decaying(BlockState pState) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(DISTANCE)) {
                final int distance = Math.min(pFacingState.getValue(DISTANCE)+1, 7);
                return super.updateShape(pState.setValue(DISTANCE, distance), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        final Block block = pLevel.getBlockState(pPos.above()).getBlock();
        return block instanceof MineralLeavesBlock;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}