package com.smellysleepy.meadow.common.worldgen.tree;

import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.systems.worldgen.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import java.util.function.*;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class MeadowTreeFeature extends AbstractMeadowTreeFeature<MeadowTreeFeatureConfiguration> {

    public static final LodestoneBlockFiller.LodestoneLayerToken LOGS = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken LEAVES = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken HANGING_LEAVES = new LodestoneBlockFiller.LodestoneLayerToken();

    public MeadowTreeFeature() {
        super(MeadowTreeFeatureConfiguration.CODEC);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 8, 10);
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

    private boolean rollForFancyLeaves(RandomSource random) {
        return random.nextFloat() < 0.25f;
    }

    @Override
    public boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        var rand = context.random();
        var log = config.log;
        var logState = log.defaultBlockState();
        var filler = new LodestoneBlockFiller().addLayers(LOGS, LEAVES, HANGING_LEAVES);
        int trunkHeight = getTrunkHeight(rand);
        var mutable = new BlockPos.MutableBlockPos().set(pos);

        for (int i = 0; i <= trunkHeight; i++) { //Main Trunk
            if (!canPlace(level, mutable)) {
                return false;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
            mutable.move(Direction.UP);
        }
        for (int i = 0; i < 4; i++) { //Side Trunk Stumps
            Direction direction = Direction.from2DDataValue(i);
            int sideTrunkHeight = getSideTrunkHeight(rand);
            if (sideTrunkHeight == 0) {
                continue;
            }
            mutable.set(pos).move(direction);
            for (int j = 0; j < sideTrunkHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
                mutable.move(Direction.UP);
            }
            addDownwardsTrunkConnections(logState, level, filler, mutable);
        }

        int branches = Mth.nextInt(rand, 1, 4);
        int directionOffset = rand.nextInt(4);
        for (int i = 0; i < branches; i++) {
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
            makeLeafBlob(config, rand, filler, mutable.move(Direction.DOWN, branchHeight));
        }
        makeLeafBlob(config, rand, filler, mutable.set(pos).move(Direction.UP, trunkHeight-2));

        filler.fill(level);
        updateLeaves(level, filler.getLayer(LOGS).keySet());
        return true;
    }

    public void addDownwardsTrunkConnections(BlockState logState, WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        var mutable = pos.mutable();
        while (true) {
            mutable.move(Direction.DOWN);
            if (!canPlace(level, mutable)) {
                break;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
        }
    }

    public void makeLeafBlob(MeadowTreeFeatureConfiguration config, RandomSource rand, LodestoneBlockFiller filler, BlockPos pos) {
        int[] leafSizes = new int[]{1, 2, 3, 3, 2, 1};
        var mutable = pos.mutable();

        Supplier<BlockStateEntry> leavesEntry = () -> create((rollForFancyLeaves(rand) ? config.fancyLeaves : config.leaves).defaultBlockState()).build();
        BlockStateEntry hangingLeavesEntry = create(config.hangingLeaves.defaultBlockState())
                .setDiscardPredicate((l, p, s) -> !filler.getLayer(LEAVES).containsKey(p.above()))
                .build();
        for (int i = 0; i < 6; i++) {
            mutable.move(Direction.UP);
            makeLeafSlice(filler.getLayer(LEAVES), mutable, leafSizes[i], leavesEntry);
        }
        mutable.set(pos).move(Direction.DOWN);
        for (int i = 0; i < 3; i++) {
            mutable.move(Direction.UP);
            makeLeafSlice(filler.getLayer(HANGING_LEAVES), mutable, leafSizes[i], hangingLeavesEntry);
        }
    }

    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, BlockStateEntry entry) {
        makeLeafSlice(layer, pos, leavesSize, ()->entry);
    }
    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, Supplier<BlockStateEntry> entry) {
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