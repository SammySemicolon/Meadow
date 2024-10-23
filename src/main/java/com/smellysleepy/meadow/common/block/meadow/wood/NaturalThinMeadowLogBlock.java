package com.smellysleepy.meadow.common.block.meadow.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.function.Supplier;

public class NaturalThinMeadowLogBlock extends ThinMeadowLogBlock implements BonemealableBlock{

    public static final EnumProperty<MeadowLeavesType> LEAVES = EnumProperty.create("leaves", MeadowLeavesType.class);

    public final Supplier<Block> stripped;
    public NaturalThinMeadowLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(LEAVES, MeadowLeavesType.NONE));
        this.stripped = stripped;
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

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction.equals(ToolActions.AXE_STRIP)) {
            return stripped.get().defaultBlockState();
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LEAVES);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.getValue(LEAVES).equals(MeadowLeavesType.TOP)) {
            return Shapes.empty();
        }
        return super.getOcclusionShape(pState, pLevel, pPos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(LEAVES).equals(MeadowLeavesType.TOP)) {
            return Shapes.block();
        }
        return super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !pState.getValue(LEAVES).equals(MeadowLeavesType.TOP);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
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
