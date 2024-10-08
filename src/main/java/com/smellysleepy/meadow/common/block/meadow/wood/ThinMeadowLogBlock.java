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

public class ThinMeadowLogBlock extends Block implements BonemealableBlock{

    public static final EnumProperty<MeadowLeavesType> LEAVES = EnumProperty.create("leaves", MeadowLeavesType.class);

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public ThinMeadowLogBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEAVES, MeadowLeavesType.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LEAVES);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.getValue(LEAVES).equals(MeadowLeavesType.TOP)) {
            return Shapes.empty();
        }
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(LEAVES).equals(MeadowLeavesType.TOP)) {
            return super.getShape(pState, pLevel, pPos, pContext);
        }
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !pState.getValue(LEAVES).equals(MeadowLeavesType.TOP);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos, pState.setValue(LEAVES, pState.getValue(LEAVES).next()), 3);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.canPerformAction(ToolActions.SHEARS_HARVEST)) {
            if (!pState.getValue(LEAVES).equals(MeadowLeavesType.NONE)) {
                pLevel.setBlock(pPos, pState.setValue(LEAVES, pState.getValue(LEAVES).previous()), 3);
                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public enum MeadowLeavesType implements StringRepresentable {
        NONE("none"),
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        TOP("top");

        private final String name;
        MeadowLeavesType(String pName) {
            this.name = pName;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }

        private static final MeadowLeavesType[] VALUES = values();

        public MeadowLeavesType next() {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }

        public MeadowLeavesType previous() {
            return VALUES[(ordinal() - 1  + VALUES.length) % VALUES.length];
        }
    }
}
