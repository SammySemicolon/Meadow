package com.smellysleepy.meadow.common.block.meadow.wood;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.common.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.*;

public class ThinMeadowLogBlock extends LodestoneLogBlock implements BonemealableBlock{

    public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public ThinMeadowLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEAVES, BambooLeaves.NONE));
    }
    public ThinMeadowLogBlock(Properties properties) {
        this(properties, null);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LEAVES);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !pState.getValue(LEAVES).equals(BambooLeaves.LARGE);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BambooLeaves value = pState.getValue(LEAVES).equals(BambooLeaves.SMALL) ? BambooLeaves.LARGE : BambooLeaves.SMALL;
        pLevel.setBlock(pPos, pState.setValue(LEAVES, value), 3);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.canPerformAction(ToolActions.SHEARS_HARVEST)) {
            if (!pState.getValue(LEAVES).equals(BambooLeaves.NONE)) {
                BambooLeaves value = pState.getValue(LEAVES).equals(BambooLeaves.LARGE) ? BambooLeaves.SMALL : BambooLeaves.NONE;
                pLevel.setBlock(pPos, pState.setValue(LEAVES, value), 3);
                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}