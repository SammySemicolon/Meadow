package com.smellysleepy.meadow.common.worldgen.feature.tree.meadow;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.common.block.meadow.leaves.MeadowLeavesBlock;
import com.smellysleepy.meadow.common.block.meadow.wood.PartiallyCalcifiedMeadowLogBlock;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.feature.tree.AbstractTreeFeature;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.worldgen.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.*;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class MeadowTreeFeature extends AbstractMeadowTreeFeature {

    public MeadowTreeFeature() {
        super();
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 8, 10);
    }

    private int getCalcificationHeight(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }

    private int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private int getBranchLength(RandomSource random) {
        return Mth.nextInt(random, 4, 5);
    }

    private int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context, LodestoneBlockFiller filler) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        var rand = context.random();
        var logState = config.log.defaultBlockState();
        var calcifiedLogState = config.calcifiedLog.defaultBlockState();
        var partiallyCalcifiedLogState = config.partiallyCalcifiedLog.defaultBlockState().trySetValue(PartiallyCalcifiedMeadowLogBlock.FACING, Direction.UP);
        int trunkHeight = getTrunkHeight(rand);
        int calcificationHeight = getCalcificationHeight(rand);
        var mutable = new BlockPos.MutableBlockPos().set(pos);

        for (int i = 0; i <= trunkHeight; i++) { //Main Trunk
            if (!canPlace(level, mutable)) {
                return false;
            }
            var state = logState;
            if (i <= calcificationHeight) {
                state = i == calcificationHeight ? partiallyCalcifiedLogState : calcifiedLogState;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(state));
            mutable.move(Direction.UP);
        }
        for (int i = 0; i < 4; i++) { //Side Trunk Stumps
            Direction direction = Direction.from2DDataValue(i);
            int sideTrunkHeight = getSideTrunkHeight(rand);
            if (sideTrunkHeight == 0) {
                continue;
            }
            mutable.set(pos).move(direction);
            addDownwardsTrunkConnections(calcifiedLogState, rand, level, filler, mutable);
            for (int j = 0; j < sideTrunkHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(j == 0 ? partiallyCalcifiedLogState : logState));
                mutable.move(Direction.UP);
            }
        }

        BlockStateEntry leavesEntry = create(config.leaves.defaultBlockState()).build();

        int branches = Mth.nextInt(rand, 1, 4);
        int directionOffset = rand.nextInt(4);
        for (int i = 0; i < branches; i++) { //Branches
            Direction direction = Direction.from2DDataValue((i+directionOffset)%4);
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchLength = getBranchLength(rand);
            int branchHeight = getBranchHeight(rand);
            boolean hanging = true;

            mutable.set(pos);
            mutable.move(Direction.UP, trunkHeight-downwardsBranchOffset);

            for (int j = 0; j < branchLength; j++) {
                mutable.move(direction);
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
                if (hanging && j >= branchLength/2) {
                    hanging = false;
                    j--;
                    mutable.move(Direction.DOWN);
                    mutable.move(direction.getOpposite());
                }
            }
            for (int j = 0; j < branchHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
                mutable.move(Direction.UP);
            }
            makeLeafBlob(leavesEntry, filler, mutable.move(Direction.DOWN, branchHeight));
        }
        makeLeafBlob(leavesEntry, filler, mutable.set(pos).move(Direction.UP, trunkHeight-2));
        return true;
    }

    public void addDownwardsTrunkConnections(BlockState calcifiedLogState, RandomSource randomSource, WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        var mutable = pos.mutable();
        int max = Mth.floor(getTrunkHeight(randomSource)/2f);
        while (true) {
            mutable.move(Direction.DOWN);
            if (!canPlace(level, mutable)) {
                return;
            }
            if (pos.getY()-mutable.getY() >= max) {
                return;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(calcifiedLogState));
        }
    }

    public void makeLeafBlob(BlockStateEntry leavesEntry, LodestoneBlockFiller filler, BlockPos pos) {
        int[] leafSizes = new int[]{1, 2, 3, 3, 2, 1};
        var mutable = pos.mutable();

        for (int i = 0; i < 6; i++) {
            mutable.move(Direction.UP);
            makeLeafSlice(filler.getLayer(LEAVES), mutable, leafSizes[i], leavesEntry);
        }
    }

    public void makeLeafSlice(LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, BlockStateEntry entry) {
        makeLeafSlice(layer, pos, leavesSize, ()->entry);
    }
    public void makeLeafSlice(LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, Supplier<BlockStateEntry> entry) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                layer.put(pos.offset(x, 0, z), entry.get());
            }
        }
    }
}