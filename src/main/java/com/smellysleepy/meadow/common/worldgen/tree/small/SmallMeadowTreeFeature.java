package com.smellysleepy.meadow.common.worldgen.tree.small;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.block.meadow.wood.ThinMeadowLogBlock.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.systems.worldgen.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.updateLeaves;

public class SmallMeadowTreeFeature extends Feature<SmallMeadowTreeFeatureConfiguration> {

    public static final LodestoneLayerToken LOGS = new LodestoneLayerToken();
    public static final LodestoneLayerToken LEAVES = new LodestoneLayerToken();

    public SmallMeadowTreeFeature() {
        super(SmallMeadowTreeFeatureConfiguration.CODEC);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    private int getCrownHeight(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private boolean rollForFancyLeaves(RandomSource random) {
        return random.nextFloat() < 0.25f;
    }

    @Override
    public boolean place(FeaturePlaceContext<SmallMeadowTreeFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        Block sapling = context.config().sapling;
        if (level.isEmptyBlock(pos.below()) || !sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }

        Block log = context.config().log;
        BlockState logState = log.defaultBlockState();
        Block leaves = context.config().leaves;
        Block fancyLeaves = context.config().fancyLeaves;
        Block hangingLeaves = context.config().hangingLeaves;

        LodestoneBlockFiller filler = new LodestoneBlockFiller().addLayers(LOGS, LEAVES);

        BlockPos.MutableBlockPos mutable = pos.mutable();
        int trunkHeight = getTrunkHeight(rand);
        boolean hasThinCrown = rand.nextFloat() < 0.3f;
        boolean hasDiamondShapedCrown = rand.nextFloat() < 0.4f;
        boolean hasBushierLogLeaves = !hasThinCrown && rand.nextFloat() < 0.5f;
        int crownHeight = getCrownHeight(rand)-(hasThinCrown?2:0);
        int crownWidth = hasThinCrown ? 0 : 1;
        if (hasDiamondShapedCrown) {
            crownWidth++;
        }

        for (int i = 0; i < trunkHeight; i++) {
            int leafStateIndex = i >= trunkHeight -(hasBushierLogLeaves ? 2 : 1) ? 4 : Mth.clamp(i, 0, 3);
            final BlockPos logPos = mutable.immutable();
            if (canPlace(level, logPos)) {
                BlockState state = logState.setValue(ThinMeadowLogBlock.LEAVES, MeadowLeaves.values()[leafStateIndex]);
                filler.getLayer(LOGS).put(logPos, LodestoneBlockFiller.create(state));
            }
            else {
                return false;
            }
            mutable.move(Direction.UP);
        }

        mutable.move(Direction.DOWN);
        for (int x = -crownWidth; x <= crownWidth; x++) {
            for (int y = -1; y <= crownHeight; y++) {
                for (int z = -crownWidth; z <= crownWidth; z++) {
                    if (hasDiamondShapedCrown || (!hasThinCrown && y == crownHeight)) {
                        int distance = crownWidth;
                        if (hasDiamondShapedCrown) {
                            if (y <= 0 || y == crownHeight) {
                                distance--;
                            }
                        }
                        if (Math.sqrt(x*x+z*z) > distance) {
                            continue;
                        }
                        if (y == 1) {
                            BlockState state = hangingLeaves.defaultBlockState();
                            var entry = LodestoneBlockFiller.create(state);
                            filler.getLayer(LEAVES).putIfAbsent(mutable.offset(x, 0, z), entry);
                        }
                    }

                    Block block = y >= 0 ? rollForFancyLeaves(rand) ? fancyLeaves : leaves : hangingLeaves;
                    BlockState state = block.defaultBlockState();
                    var entry = LodestoneBlockFiller.create(state);
                    filler.getLayer(LEAVES).put(mutable.offset(x, y, z), entry);
                }
            }
        }

        filler.fill(level);
        updateLeaves(level, filler.getLayer(LOGS).keySet());
        return true;
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