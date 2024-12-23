package com.smellysleepy.meadow.common.block.mineral;

import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
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
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;

public class HangingMineralLeavesBlock extends LeavesBlock {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private final RegistryObject<LodestoneWorldParticleType> particleType;
    public final Color color;

    public HangingMineralLeavesBlock(Properties pProperties, RegistryObject<LodestoneWorldParticleType> particleType,  Color color) {
        super(pProperties);
        this.particleType = particleType;
        this.color = color;
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
                MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ), particleType.get()).spawnParticles();

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