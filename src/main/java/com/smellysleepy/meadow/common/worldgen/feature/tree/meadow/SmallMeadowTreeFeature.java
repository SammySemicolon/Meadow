package com.smellysleepy.meadow.common.worldgen.feature.tree.meadow;

import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.block.meadow.wood.ThinMeadowLogBlock.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.systems.worldgen.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class SmallMeadowTreeFeature extends AbstractMeadowTreeFeature {

    public SmallMeadowTreeFeature() {
        super();
    }

    private int getTrunkHeight(RandomSource random, boolean isTiny) {
        if (isTiny) {
            return Mth.nextInt(random, 2, 4);
        }
        return Mth.nextInt(random, 3, 5);
    }

    private int getCalcificationHeight(RandomSource random, boolean isTiny) {
        if (isTiny) {
            return Mth.nextInt(random, 1, 2);
        }
        return Mth.nextInt(random, 1, 3);
    }

    private int getCrownHeight(RandomSource random, boolean isTiny) {
        if (isTiny) {
            return Mth.nextInt(random, 2, 3);
        }
        return Mth.nextInt(random, 2, 4);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context, LodestoneBlockFiller filler) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }

        var rand = context.random();
        var logState = config.log.defaultBlockState();
        var calcifiedLogState = config.calcifiedLog.defaultBlockState();
        var partiallyCalcifiedLogState = config.partiallyCalcifiedLog.defaultBlockState();
        var mutable = new BlockPos.MutableBlockPos().set(pos);

        boolean isTiny = rand.nextFloat() < 0.3f;
        int trunkHeight = getTrunkHeight(rand, isTiny);
        int calcificationHeight = getCalcificationHeight(rand, isTiny);
        int crownHeight = getCrownHeight(rand, isTiny);
        int crownWidth = isTiny ? 0 : 1;

        for (int i = 0; i <= trunkHeight; i++) {
            int leafStateIndex = i == trunkHeight ? 4 : Mth.clamp(i, 0, 3);
            final BlockPos logPos = mutable.immutable();
            if (canPlace(level, logPos)) {
                BlockState state = logState;
                if (i <= calcificationHeight) {
                    state = i == calcificationHeight ? partiallyCalcifiedLogState : calcifiedLogState;
                }
                var entry = create(state.setValue(ThinMeadowLogBlock.LEAVES, MeadowLeavesType.values()[leafStateIndex]));
                filler.getLayer(LOGS).put(logPos, entry);
            }
            else {
                return false;
            }
            mutable.move(Direction.UP);
        }

        BlockStateEntry leavesEntry = create(config.leaves.defaultBlockState()).build();
        for (int x = -crownWidth; x <= crownWidth; x++) {
            for (int y = 0; y <= crownHeight; y++) {
                for (int z = -crownWidth; z <= crownWidth; z++) {
                    if ((!isTiny && (y == crownHeight || y == 0))) {
                        if (Math.sqrt(x*x+z*z) > crownWidth) {
                            continue;
                        }
                    }
                    filler.getLayer(LEAVES).put(mutable.offset(x, y, z), leavesEntry);
                }
            }
        }
        return true;
    }
}