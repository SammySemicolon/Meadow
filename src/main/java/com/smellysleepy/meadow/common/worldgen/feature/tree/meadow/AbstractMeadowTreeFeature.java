package com.smellysleepy.meadow.common.worldgen.feature.tree.meadow;

import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.feature.tree.AbstractTreeFeature;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.updateLeaves;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public abstract class AbstractMeadowTreeFeature extends AbstractTreeFeature<MeadowTreeFeatureConfiguration> {

    protected static final LodestoneBlockFiller.LodestoneLayerToken COVERING = new LodestoneBlockFiller.LodestoneLayerToken();
    protected static final LodestoneBlockFiller.LodestoneLayerToken LOGS = new LodestoneBlockFiller.LodestoneLayerToken();
    protected static final LodestoneBlockFiller.LodestoneLayerToken LEAVES = new LodestoneBlockFiller.LodestoneLayerToken();

    public AbstractMeadowTreeFeature() {
        super(MeadowTreeFeatureConfiguration.CODEC);
    }
    @Override
    public final boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        var rand = context.random();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        var filler = new LodestoneBlockFiller().addLayers(LOGS, COVERING, LEAVES);
        if (place(context, filler)) {
            var calcifiedEarthState = create(MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState()).setForcePlace().build();
            var covering = MeadowBlockRegistry.CALCIFIED_COVERING.get();

            var logsLayer = filler.getLayer(LOGS);
            Set<BlockPos> coveringPositions = new HashSet<>();
            for (Map.Entry<BlockPos, LodestoneBlockFiller.BlockStateEntry> log : logsLayer.entrySet()) {
                if (log.getKey().getY() > pos.getY()) {
                    continue;
                }
                BlockPos below = log.getKey().below();
                if (!logsLayer.containsKey(below)) {
                    coveringPositions.add(below);
                }
            }
            for (BlockPos blockPos : coveringPositions) {
                filler.getLayer(COVERING).put(blockPos, calcifiedEarthState);

                BlockPos above = blockPos.above();
                for (int i = 0; i < 4; i++) {
                    Direction direction = Direction.from2DDataValue(i);
                    BlockPos offsetPos = above.relative(direction);
                    filler.getLayer(COVERING).put(offsetPos, create(covering.getStateForPlacement(covering.defaultBlockState(), level, offsetPos, Direction.DOWN)).build());
                }
            }


            var hangingLeavesEntry = create(config.hangingLeaves.defaultBlockState()).build();
            var leavesLayer = filler.getLayer(LEAVES);
            Set<BlockPos> hangingLeavesPositions = new HashSet<>();
            for (Map.Entry<BlockPos, LodestoneBlockFiller.BlockStateEntry> leaves : leavesLayer.entrySet()) {
                BlockPos below = leaves.getKey().below();
                if (!leavesLayer.containsKey(below)) {
                    hangingLeavesPositions.add(below);
                }
            }
            for (BlockPos blockPos : hangingLeavesPositions) {
                filler.getLayer(LEAVES).put(blockPos, hangingLeavesEntry);
            }

            filler.fill(level);
            updateLeaves(level, filler.getLayer(LOGS).keySet());
            return true;
        }
        return false;
    }

    public abstract boolean place(FeaturePlaceContext<MeadowTreeFeatureConfiguration> context, LodestoneBlockFiller filler);
}