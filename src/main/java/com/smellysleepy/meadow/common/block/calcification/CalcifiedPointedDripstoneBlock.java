package com.smellysleepy.meadow.common.block.calcification;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CalcifiedPointedDripstoneBlock extends PointedDripstoneBlock {

    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public CalcifiedPointedDripstoneBlock(Properties pProperties) {
        super(pProperties);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isValidPointedDripstonePlacement(pLevel, pPos, pState.getValue(TIP_DIRECTION));
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pDirection != Direction.UP && pDirection != Direction.DOWN) {
            return pState;
        } else {
            Direction direction = pState.getValue(TIP_DIRECTION);
            if (direction == Direction.DOWN && pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                return pState;
            } else if (pDirection == direction.getOpposite() && !this.canSurvive(pState, pLevel, pPos)) {
                if (direction == Direction.DOWN) {
                    pLevel.scheduleTick(pPos, this, 2);
                } else {
                    pLevel.scheduleTick(pPos, this, 1);
                }

                return pState;
            } else {
                boolean flag = pState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness dripstonethickness = calculateDripstoneThickness(pLevel, pPos, direction, flag);
                return pState.setValue(THICKNESS, dripstonethickness);
            }
        }
    }

    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos blockpos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, blockpos) && pProjectile instanceof ThrownTrident && pProjectile.getDeltaMovement().length() > 0.6D) {
            pLevel.destroyBlock(blockpos, true);
        }

    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pState.getValue(TIP_DIRECTION) == Direction.UP && pState.getValue(THICKNESS) == DripstoneThickness.TIP) {
            pEntity.causeFallDamage(pFallDistance + 2.0F, 2.0F, pLevel.damageSources().stalagmite());
        } else {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        }

    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (canDrip(pState)) {
            float f = pRandom.nextFloat();
            if (!(f > 0.12F)) {
                getFluidAboveStalactite(pLevel, pPos, pState).filter((p_221848_) -> f < 0.02F || canFillCauldron(p_221848_.fluid)).ifPresent((p_221881_) -> {
                    spawnDripParticle(pLevel, pPos, pState, p_221881_.fluid);
                });
            }
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isStalagmite(pState) && !this.canSurvive(pState, pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        } else {
            spawnFallingStalactite(pState, pLevel, pPos);
        }

    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        maybeTransferFluid(pState, pLevel, pPos, pRandom.nextFloat());
        if (pRandom.nextFloat() < 0.011377778F && isStalactiteStartPos(pState, pLevel, pPos)) {
            growStalactiteOrStalagmiteIfPossible(pState, pLevel, pPos, pRandom);
        }

    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Direction direction = pContext.getNearestLookingVerticalDirection().getOpposite();
        Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
        if (direction1 == null) {
            return null;
        } else {
            boolean flag = !pContext.isSecondaryUseActive();
            DripstoneThickness dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
            return dripstonethickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction1).setValue(THICKNESS, dripstonethickness).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
        }
    }

    private static void spawnFallingStalactite(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(BlockState blockstate = pState; isStalactite(blockstate); blockstate = pLevel.getBlockState(blockpos$mutableblockpos)) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, blockpos$mutableblockpos, blockstate);
            if (isTip(blockstate, true)) {
                int i = Math.max(1 + pPos.getY() - blockpos$mutableblockpos.getY(), 6);
                fallingblockentity.setHurtsEntities(Math.max(1 + pPos.getY() - blockpos$mutableblockpos.getY(), 6), 40);
                break;
            }

            blockpos$mutableblockpos.move(Direction.DOWN);
        }

    }

    @VisibleForTesting
    public static void growStalactiteOrStalagmiteIfPossible(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockState blockstate = pLevel.getBlockState(pPos.above(1));
        BlockState blockstate1 = pLevel.getBlockState(pPos.above(2));
        if (canGrow(blockstate, blockstate1)) {
            BlockPos blockpos = findTip(pState, pLevel, pPos, 7, false);
            if (blockpos != null) {
                BlockState blockstate2 = pLevel.getBlockState(blockpos);
                if (canDrip(blockstate2) && canTipGrow(blockstate2, pLevel, blockpos)) {
                    if (pRandom.nextBoolean()) {
                        grow(pLevel, pState.getBlock(), blockpos, Direction.DOWN);
                    } else {
                        growStalagmiteBelow(pLevel, pState.getBlock(), blockpos);
                    }

                }
            }
        }
    }

    private static void growStalagmiteBelow(ServerLevel pLevel, Block block, BlockPos pPos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(int i = 0; i < 10; ++i) {
            blockpos$mutableblockpos.move(Direction.DOWN);
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
            if (!blockstate.getFluidState().isEmpty()) {
                return;
            }

            if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, pLevel, blockpos$mutableblockpos)) {
                grow(pLevel, block, blockpos$mutableblockpos, Direction.UP);
                return;
            }

            if (isValidPointedDripstonePlacement(pLevel, blockpos$mutableblockpos, Direction.UP) && !pLevel.isWaterAt(blockpos$mutableblockpos.below())) {
                grow(pLevel, block, blockpos$mutableblockpos.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(pLevel, blockpos$mutableblockpos, blockstate)) {
                return;
            }
        }

    }

    private static void grow(ServerLevel pServer, Block block, BlockPos pPos, Direction pDirection) {
        BlockPos blockpos = pPos.relative(pDirection);
        BlockState blockstate = pServer.getBlockState(blockpos);
        if (isUnmergedTipWithDirection(blockstate, pDirection.getOpposite())) {
            createMergedTips(blockstate, pServer, blockpos);
        } else if (blockstate.isAir() || blockstate.is(Blocks.WATER)) {
            createDripstone(pServer, block, blockpos, pDirection, DripstoneThickness.TIP);
        }

    }

    private static void createDripstone(LevelAccessor pLevel, Block block, BlockPos pPos, Direction pDirection, DripstoneThickness pThickness) {
        BlockState blockstate = block.defaultBlockState().setValue(TIP_DIRECTION, pDirection).setValue(THICKNESS, pThickness).setValue(WATERLOGGED, Boolean.valueOf(pLevel.getFluidState(pPos).getType() == Fluids.WATER));
        pLevel.setBlock(pPos, blockstate, 3);
    }

    private static void createMergedTips(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        BlockPos blockpos;
        BlockPos blockpos1;
        if (pState.getValue(TIP_DIRECTION) == Direction.UP) {
            blockpos1 = pPos;
            blockpos = pPos.above();
        } else {
            blockpos = pPos;
            blockpos1 = pPos.below();
        }

        createDripstone(pLevel, pState.getBlock(), blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createDripstone(pLevel, pState.getBlock(), blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void spawnDripParticle(Level pLevel, BlockPos pPos, BlockState pState) {
        getFluidAboveStalactite(pLevel, pPos, pState).ifPresent((p_221856_) -> {
            spawnDripParticle(pLevel, pPos, pState, p_221856_.fluid);
        });
    }

    private static void spawnDripParticle(Level pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        double d0 = 0.0625D;
        double d1 = (double)pPos.getX() + 0.5D + vec3.x;
        double d2 = (double)((float)(pPos.getY() + 1) - 0.6875F) - 0.0625D;
        double d3 = (double)pPos.getZ() + 0.5D + vec3.z;
        Fluid fluid = getDripFluid(pLevel, pFluid);
        ParticleOptions particleoptions = fluid.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        pLevel.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    private static BlockPos findTip(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pMaxIterations, boolean pIsTipMerge) {
        if (isTip(pState, pIsTipMerge)) {
            return pPos;
        } else {
            Direction direction = pState.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> p_202024_.is(pState.getBlock()) && p_202024_.getValue(TIP_DIRECTION) == direction;
            return findBlockVertical(pLevel, pPos, direction.getAxisDirection(), bipredicate, (p_154168_) -> isTip(p_154168_, pIsTipMerge), pMaxIterations).orElse(null);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader pLevel, BlockPos pPos, Direction pDir) {
        Direction direction;
        if (isValidPointedDripstonePlacement(pLevel, pPos, pDir)) {
            direction = pDir;
        } else {
            if (!isValidPointedDripstonePlacement(pLevel, pPos, pDir.getOpposite())) {
                return null;
            }

            direction = pDir.getOpposite();
        }

        return direction;
    }

    private static DripstoneThickness calculateDripstoneThickness(LevelReader pLevel, BlockPos pPos, Direction pDir, boolean pIsTipMerge) {
        Direction direction = pDir.getOpposite();
        BlockState blockstate = pLevel.getBlockState(pPos.relative(pDir));
        if (isPointedDripstoneWithDirection(blockstate, direction)) {
            return !pIsTipMerge && blockstate.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isPointedDripstoneWithDirection(blockstate, pDir)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness dripstonethickness = blockstate.getValue(THICKNESS);
            if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockstate1 = pLevel.getBlockState(pPos.relative(direction));
                return !isPointedDripstoneWithDirection(blockstate1, pDir) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState p_154239_) {
        return isStalactite(p_154239_) && p_154239_.getValue(THICKNESS) == DripstoneThickness.TIP && !p_154239_.getValue(WATERLOGGED);
    }

    private static boolean canTipGrow(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        Direction direction = pState.getValue(TIP_DIRECTION);
        BlockPos blockpos = pPos.relative(direction);
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!blockstate.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockstate.isAir() ? true : isUnmergedTipWithDirection(blockstate, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> findRootBlock(Level pLevel, BlockPos pPos, BlockState pState, int pMaxIterations) {
        Direction direction = pState.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> p_202016_.is(pState.getBlock()) && p_202016_.getValue(TIP_DIRECTION) == direction;
        return findBlockVertical(pLevel, pPos, direction.getOpposite().getAxisDirection(), bipredicate, (p_154245_) -> !p_154245_.is(pState.getBlock()), pMaxIterations);
    }

    private static boolean isValidPointedDripstonePlacement(LevelReader pLevel, BlockPos pPos, Direction pDir) {
        BlockPos blockpos = pPos.relative(pDir.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return blockstate.isFaceSturdy(pLevel, blockpos, pDir) || isPointedDripstoneWithDirection(blockstate, pDir);
    }

    private static boolean isTip(BlockState pState, boolean pIsTipMerge) {
        if (!(pState.getBlock() instanceof CalcifiedPointedDripstoneBlock)) {
            return false;
        } else {
            DripstoneThickness dripstonethickness = pState.getValue(THICKNESS);
            return dripstonethickness == DripstoneThickness.TIP || pIsTipMerge && dripstonethickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState pState, Direction pDir) {
        return isTip(pState, false) && pState.getValue(TIP_DIRECTION) == pDir;
    }

    private static boolean isStalactite(BlockState pState) {
        return isPointedDripstoneWithDirection(pState, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState pState) {
        return isPointedDripstoneWithDirection(pState, Direction.UP);
    }

    private static boolean isStalactiteStartPos(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isStalactite(pState) && !pLevel.getBlockState(pPos.above()).is(pState.getBlock());
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    private static boolean isPointedDripstoneWithDirection(BlockState pState, Direction pDir) {
        return pState.getBlock() instanceof CalcifiedPointedDripstoneBlock && pState.getValue(TIP_DIRECTION) == pDir;
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level pLevel, BlockPos pPos, Fluid pFluid) {
        Predicate<BlockState> predicate = (p_154162_) -> p_154162_.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)p_154162_.getBlock()).canReceiveStalactiteDrip(pFluid);
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202034_, p_202035_) -> canDripThrough(pLevel, p_202034_, p_202035_);
        return findBlockVertical(pLevel, pPos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse(null);
    }

    @Nullable
    public static BlockPos findStalactiteTipAboveCauldron(Level pLevel, BlockPos pPos) {
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202030_, p_202031_) -> canDripThrough(pLevel, p_202030_, p_202031_);
        return findBlockVertical(pLevel, pPos, Direction.UP.getAxisDirection(), bipredicate, PointedDripstoneBlock::canDrip, 11).orElse((BlockPos)null);
    }

    public static Fluid getCauldronFillFluidType(ServerLevel pLevel, BlockPos pPos) {
        return getFluidAboveStalactite(pLevel, pPos, pLevel.getBlockState(pPos)).map((p_221858_) -> p_221858_.fluid).filter(CalcifiedPointedDripstoneBlock::canFillCauldron).orElse(Fluids.EMPTY);
    }

    private static Optional<FluidInfo> getFluidAboveStalactite(Level pLevel, BlockPos pPos, BlockState pState) {
        return !isStalactite(pState) ? Optional.empty() : findRootBlock(pLevel, pPos, pState, 11).map((p_221876_) -> {
            BlockPos blockpos = p_221876_.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            Fluid fluid;
            if (blockstate.is(Blocks.MUD) && !pLevel.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = pLevel.getFluidState(blockpos).getType();
            }

            return new FluidInfo(blockpos, fluid, blockstate);
        });
    }

    private static boolean canFillCauldron(Fluid p_154159_) {
        return p_154159_ == Fluids.LAVA || p_154159_ == Fluids.WATER;
    }

    private static boolean canGrow(BlockState pDripstoneState, BlockState pState) {
        return pDripstoneState.getBlock() instanceof PointedDripstoneBlock && pState.is(Blocks.WATER) && pState.getFluidState().isSource();
    }

    private static Fluid getDripFluid(Level pLevel, Fluid pFluid) {
        if (pFluid.isSame(Fluids.EMPTY)) {
            return pLevel.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return pFluid;
        }
    }

    private static boolean canDripThrough(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        if (pState.isAir()) {
            return true;
        } else if (pState.isSolidRender(pLevel, pPos)) {
            return false;
        } else if (!pState.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = pState.getCollisionShape(pLevel, pPos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
