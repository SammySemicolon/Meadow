package com.smellysleepy.meadow.common.worldgen.tree;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.worldgen.*;

import java.util.*;
import java.util.function.*;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class MeadowTreeFeature extends Feature<MeadowTreeFeatureConfiguration> {

    public static final LodestoneBlockFiller.LodestoneLayerToken LOGS = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken LEAVES = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken HANGING_LEAVES = new LodestoneBlockFiller.LodestoneLayerToken();

    public MeadowTreeFeature() {
        super(MeadowTreeFeatureConfiguration.CODEC);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 7, 10);
    }

    private int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }

    private int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private int getBranchEndOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    private boolean rollForFancyLeaves(RandomSource random) {
        return random.nextFloat() < 0.25f;
    }

    @Override
    public boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        final MeadowTreeFeatureConfiguration config = context.config();
        Block sapling = config.sapling;

        if (level.isEmptyBlock(pos.below()) || !sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        Block log = config.log;
        BlockState logState = log.defaultBlockState();

        LodestoneBlockFiller filler = new LodestoneBlockFiller()
                .addLayer(new LodestoneBlockFiller.LodestoneBlockFillerLayer(LOGS, LodestoneBlockFiller.MergingStrategy.REPLACE))
                .addLayer(new LodestoneBlockFiller.LodestoneBlockFillerLayer(LEAVES, LodestoneBlockFiller.MergingStrategy.ADD))
                .addLayer(new LodestoneBlockFiller.LodestoneBlockFillerLayer(HANGING_LEAVES, LodestoneBlockFiller.MergingStrategy.ADD));

        int trunkHeight = getTrunkHeight(rand);
        BlockPos trunkTop = pos.above(trunkHeight);

        for (int i = 0; i <= trunkHeight; i++) {
            BlockPos trunkPos = pos.above(i);
            if (canPlace(level, trunkPos)) {
                filler.getLayer(LOGS).put(trunkPos, create(logState).setForcePlace());
            } else {
                return false;
            }
        }

        for (Direction direction : DIRECTIONS) {
            int sideTrunkHeight = getSideTrunkHeight(rand);
            final BlockPos relative = pos.relative(direction);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = relative.above(i);
                if (canPlace(level, sideTrunkPos)) {
                    filler.getLayer(LOGS).put(sideTrunkPos, create(logState).setForcePlace());
                } else {
                    return false;
                }
            }
            downwardsTrunk(logState, level, filler, relative);
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchEndOffset = getBranchEndOffset(rand);
            BlockPos branchStartPos = trunkTop.below(downwardsBranchOffset).relative(direction, branchEndOffset);
            for (int i = 0; i < branchEndOffset; i++) {
                BlockPos branchConnectionPos = branchStartPos.relative(direction.getOpposite(), i);
                if (canPlace(level, branchConnectionPos)) {
                    filler.getLayer(LOGS).put(branchConnectionPos, create(logState.setValue(RotatedPillarBlock.AXIS, direction.getAxis())).setForcePlace());
                } else {
                    return false;
                }
            }
            int branchHeight = getBranchHeight(rand);
            for (int i = 0; i < branchHeight; i++) {
                BlockPos branchPos = branchStartPos.above(i);
                if (canPlace(level, branchPos)) {
                    filler.getLayer(LOGS).put(branchPos, create(logState).setForcePlace());
                } else {
                    return false;
                }
            }
            makeLeafBlob(config, rand, filler, branchStartPos.above(1));
        }
        makeLeafBlob(config, rand, filler, trunkTop);

        filler.fill(level);
        updateLeaves(level, filler.getLayer(LOGS).keySet());
        return true;
    }

    public void downwardsTrunk(BlockState logState, WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.below(i);
            if (canPlace(level, trunkPos)) {
                filler.getLayer(LOGS).put(trunkPos, create(logState).setForcePlace());
            } else {
                break;
            }
            if (i > level.getMaxBuildHeight()) {
                break;
            }
        }
        while (true);
    }

    public void makeLeafBlob(MeadowTreeFeatureConfiguration config, RandomSource rand, LodestoneBlockFiller filler, BlockPos pos) {
        final BlockPos.MutableBlockPos mutable = pos.mutable();
        int[] leafSizes = new int[]{1, 2, 2, 2, 1};
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 2; i++) {
            int size = leafSizes[i];
            var entry = create(config.hangingLeaves.defaultBlockState())
                    .setDiscardPredicate((l, p, s) -> {
                        final LodestoneBlockFiller.BlockStateEntry blockStateEntry = filler.getMainLayer().get(p.above());
                        return blockStateEntry == null || blockStateEntry.getState().getBlock() instanceof MeadowHangingLeavesBlock;
                    })
                    .build();
            makeLeafSlice(filler.getLayer(HANGING_LEAVES), mutable, size, ()->entry);
            mutable.move(Direction.UP);
        }
        mutable.move(Direction.DOWN, 1);
        for (int i = 0; i < 5; i++) {
            int size = leafSizes[i];
            makeLeafSlice(filler.getLayer(LEAVES), mutable, size, ()->create((rollForFancyLeaves(rand) ? config.fancyLeaves : config.leaves).defaultBlockState()).build());
            mutable.move(Direction.UP);
        }
    }

    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, int leavesSize, Supplier<LodestoneBlockFiller.BlockStateEntry> entry) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                layer.put(pos.offset(x, 0, z), entry.get());
            }
        }
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();
        return block instanceof MeadowSaplingBlock || level.isEmptyBlock(pos) || state.canBeReplaced();
    }
}