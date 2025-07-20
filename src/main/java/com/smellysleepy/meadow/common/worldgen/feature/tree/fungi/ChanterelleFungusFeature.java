package com.smellysleepy.meadow.common.worldgen.feature.tree.fungi;

import com.smellysleepy.meadow.common.block.meadow.wood.PartiallyCalcifiedMeadowLogBlock;
import com.smellysleepy.meadow.common.worldgen.feature.tree.AbstractCalcifiedTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.function.Supplier;

import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

public class ChanterelleFungusFeature extends AbstractCalcifiedTreeFeature<ChanterelleFungusFeatureConfiguration> {

    public ChanterelleFungusFeature() {
        super(ChanterelleFungusFeatureConfiguration.CODEC);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 8, 16);
    }

    private int getCalcificationHeight(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 4);
    }

    @Override
    public boolean place(FeaturePlaceContext<ChanterelleFungusFeatureConfiguration> context, LodestoneBlockFiller filler) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.getSapling().defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        var rand = context.random();
        var fungusStemState = config.getLog().defaultBlockState();
        var calcifiedLogState = config.getCalcifiedLog().defaultBlockState();
        var calcifiedFungusStemState = config.getPartiallyCalcifiedLog().defaultBlockState().trySetValue(PartiallyCalcifiedMeadowLogBlock.AXIS, Direction.Axis.Y);
        int trunkHeight = getTrunkHeight(rand);
        int calcificationHeight = getCalcificationHeight(rand);
        var mutable = new BlockPos.MutableBlockPos().set(pos);

        for (int i = 0; i <= trunkHeight; i++) { //Main Trunk
            if (!canPlace(level, mutable)) {
                return false;
            }
            var state = fungusStemState;
            if (i <= calcificationHeight) {
                state = i == calcificationHeight ? calcifiedFungusStemState : calcifiedLogState;
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
            for (int j = 0; j < sideTrunkHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(j == 0 ? calcifiedFungusStemState : fungusStemState));
                mutable.move(Direction.UP);
            }
        }

        BlockStateEntry crownEntry = create(config.fungalCrown.defaultBlockState()).build();
        return makeFungalCrown(level, filler, crownEntry, mutable.set(pos).move(Direction.UP, trunkHeight-2));
    }

    public boolean makeFungalCrown(WorldGenLevel level, LodestoneBlockFiller filler, BlockStateEntry leavesEntry, BlockPos pos) {
        int[] fungiLayerSizes = new int[]{4, 5, 6};
        var mutable = pos.mutable();

        for (int i = 0; i < 3; i++) {
            mutable.move(Direction.UP);
            boolean success = makeFungusSlice(level, filler.getLayer(LEAVES), mutable, fungiLayerSizes[i], leavesEntry);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    public boolean makeFungusSlice(WorldGenLevel level, LodestoneBlockFillerLayer layer, BlockPos pos, int layerSize, BlockStateEntry entry) {
        return makeFungusSlice(level, layer, pos, layerSize, ()->entry);
    }
    public boolean makeFungusSlice(WorldGenLevel level, LodestoneBlockFillerLayer layer, BlockPos pos, int layerSize, Supplier<BlockStateEntry> entry) {
        for (int x = -layerSize; x <= layerSize; x++) {
            for (int z = -layerSize; z <= layerSize; z++) {
                if (Math.abs(x) == layerSize && Math.abs(z) == layerSize) {
                    continue;
                }
                BlockPos offsetPos = pos.offset(x, 0, z);
                if (!canPlace(level, offsetPos)) {
                    return false;
                }
                layer.put(offsetPos, entry.get());
            }
        }
        return true;
    }
}