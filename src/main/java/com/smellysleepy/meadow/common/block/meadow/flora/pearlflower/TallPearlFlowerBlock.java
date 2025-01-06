package com.smellysleepy.meadow.common.block.meadow.flora.pearlflower;

import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import team.lodestar.lodestone.systems.particle.ParticleEffectSpawner;

import javax.annotation.Nullable;

public class TallPearlFlowerBlock extends TallFlowerBlock {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TallPearlFlowerBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(HALF).equals(DoubleBlockHalf.UPPER)) {
            return;
        }
        if (pRandom.nextInt(10) == 0) {
            double posX = (double) pPos.getX() + 0.3f + pRandom.nextDouble() * 0.7f;
            double posY = (double) pPos.getY() + 1.4f + pRandom.nextDouble() * 0.2f;
            double posZ = (double) pPos.getZ() + 0.3f + pRandom.nextDouble() * 0.7f;
            ParticleEffectSpawner particles = MeadowParticleEffects.pearlflowerShine(pLevel, new Vec3(posX, posY, posZ), MeadowParticleRegistry.SHINY_GLIMMER.get());
            particles.spawnParticles();
        }
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.canPerformAction(ToolActions.SHEARS_HARVEST)) {
            boolean success = PearlflowerReplacements.performExchange(this, pLevel, pPos, pState);
            if (success) {
                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.PEARLFLOWER_CAN_PLACE_ON) || super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) {
            return null;
        }
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return state.setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
