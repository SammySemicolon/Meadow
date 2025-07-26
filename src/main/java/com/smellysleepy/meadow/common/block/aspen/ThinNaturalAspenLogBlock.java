package com.smellysleepy.meadow.common.block.aspen;

import com.smellysleepy.meadow.common.block.ThinLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ThinNaturalAspenLogBlock extends ThinLogBlock implements BonemealableBlock{

    public static final EnumProperty<MeadowLeavesType> LEAVES = EnumProperty.create("leaves", MeadowLeavesType.class);

    public final Supplier<Block> stripped;
    public ThinNaturalAspenLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(LEAVES, MeadowLeavesType.NONE));
        this.stripped = stripped;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(LEAVES).next().isPresent();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pState.getValue(LEAVES).next().ifPresent(next -> pLevel.setBlock(pPos, pState.setValue(LEAVES, next), 3));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.canPerformAction(ItemAbilities.SHEARS_CARVE)) {
            var optional = state.getValue(LEAVES).previous();
            if (optional.isPresent()) {
                var previous = optional.get();
                level.setBlock(pos, state.setValue(LEAVES, previous), 3);
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            return stripped.get().defaultBlockState();
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
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

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public Optional<MeadowLeavesType> next() {
            MeadowLeavesType[] values = values();
            int next = ordinal() + 1;
            if (next >= values.length) {
                return Optional.empty();
            }
            return Optional.of(values[next]);
        }

        public Optional<MeadowLeavesType> previous() {
            MeadowLeavesType[] values = values();
            int previous = ordinal() - 1;
            if (previous < 0) {
                return Optional.empty();
            }
            return Optional.of(values[previous]);
        }
    }
}
