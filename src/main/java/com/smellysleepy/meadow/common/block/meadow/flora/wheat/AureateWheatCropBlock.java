package com.smellysleepy.meadow.common.block.meadow.flora.wheat;

import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchFeature.copyWaterloggedFrom;

public class AureateWheatCropBlock extends BushBlock {

    public static final EnumProperty<AureateWheatLayer> LAYER = EnumProperty.create("state", AureateWheatLayer.class);

    public AureateWheatCropBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYER, AureateWheatLayer.LOWERMOST));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYER);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        var mutable = pPos.mutable();
        var layer = AureateWheatLayer.LOWER;
        while (layer != AureateWheatLayer.LOWERMOST) {
            mutable.move(Direction.UP);
            pLevel.setBlock(mutable, copyWaterloggedFrom(pLevel, mutable, defaultBlockState().setValue(LAYER, layer)), 3);
            layer = layer.above();
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        var blockpos = pContext.getClickedPos();
        var level = pContext.getLevel();
        var mutable = blockpos.mutable();
        int extraLayers = AureateWheatLayer.values().length - 1;
        for (int i = 0; i < extraLayers; i++) {
            mutable.move(Direction.UP);
            if (!level.getBlockState(mutable).canBeReplaced(pContext)) {
                return null;
            }
        }
        return blockpos.getY() < level.getMaxBuildHeight() - extraLayers ? super.getStateForPlacement(pContext) : null;
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide) {
            if (pPlayer.isCreative()) {
//                preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
            } else {
                dropResources(pState, pLevel, pPos, null, pPlayer, pPlayer.getMainHandItem());
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return canSurvive(pState, pLevel, pCurrentPos) ? pState : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        var layer = pState.getValue(LAYER);
        if (!pLevel.getBlockState(pPos).canBeReplaced()) {
            var aboveState = pLevel.getBlockState(pPos.above());
            var belowState = pLevel.getBlockState(pPos.below());
            int matches = 0;
            int requiredMatches = 0;
            if (!layer.equals(AureateWheatLayer.UPPERMOST)) {
                if (aboveState.getBlock().equals(this)) {
                    var aboveLayer = aboveState.getValue(LAYER);
                    if (aboveLayer.equals(layer.above())) {
                        matches++;
                    }
                }
                requiredMatches++;
            }
            if (!layer.equals(AureateWheatLayer.LOWERMOST)) {
                if (belowState.getBlock().equals(this)) {
                    var belowLayer = belowState.getValue(LAYER);
                    if (belowLayer.equals(layer.below())) {
                        matches++;
                    }
                }
                requiredMatches++;
            }
            if (matches < requiredMatches) {
                return false;
            }
            if (!layer.equals(AureateWheatLayer.LOWERMOST)) {
                return true;
            }
        }
        return super.canSurvive(pState, pLevel, pPos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockRegistry.ASPEN_GRASS_BLOCK.get()) || pState.is(BlockTags.DIRT) || super.mayPlaceOn(pState, pLevel, pPos);
    }
    @Override
    public long getSeed(BlockState pState, BlockPos pPos) {
        int offset = (AureateWheatLayer.values().length-1) - pState.getValue(LAYER).ordinal();
        return Mth.getSeed(pPos.getX(), pPos.getY() - offset, pPos.getZ());
    }

    protected static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockPos.MutableBlockPos mutable = pPos.mutable();
        var layer = pState.getValue(LAYER);
        for (int i = 0; i < AureateWheatLayer.values().length; i++) {
            if (layer.equals(AureateWheatLayer.LOWERMOST)) {
                mutable.move(Direction.UP, 3);
            }
            var blockState = pLevel.getBlockState(mutable);
            var nextLayer = blockState.getValue(LAYER);
            boolean isAdjacent = Mth.abs(layer.compareTo(nextLayer)) != 0;
            if (blockState.is(pState.getBlock()) && isAdjacent) {
                BlockPos offset = mutable.immutable();
                BlockState setTo = blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                pLevel.setBlock(offset, setTo, 3);
                pLevel.levelEvent(pPlayer, 2001, offset, Block.getId(blockState));
            }
            layer = layer.below();
        }
    }

    public enum AureateWheatLayer implements StringRepresentable {
        UPPERMOST("uppermost"),
        UPPER("upper"),
        LOWER("lower"),
        LOWERMOST("lowermost");

        final String name;

        AureateWheatLayer(String name) {
            this.name = name;
        }

        public AureateWheatLayer above() {
            if (this.equals(UPPERMOST)) {
                return LOWERMOST;
            }
            return AureateWheatLayer.values()[ordinal()-1];
        }

        public AureateWheatLayer below() {
            if (this.equals(LOWERMOST)) {
                return UPPERMOST;
            }
            return AureateWheatLayer.values()[ordinal()+1];
        }

        public boolean isDirectlyAboveOf(AureateWheatLayer state) {
            return ordinal() == state.ordinal() + 1;
        }

        public boolean isDirectlyBelowOf(AureateWheatLayer state) {
            return ordinal() == state.ordinal() - 1;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}