package com.smellysleepy.meadow.common.worldgen;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import net.minecraft.core.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.*;

import static net.minecraft.tags.BlockTags.MOSS_REPLACEABLE;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class WorldgenHelper {

    private static final PerlinSimplexNoise COVERING_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public static Set<BlockPos> generateCovering(ServerLevelAccessor level, BlockPos center, int radius) {
        Set<BlockPos> positions = new HashSet<>();

        int x = center.getX();
        int z = center.getZ();
        var mutable = new BlockPos.MutableBlockPos();

        int searchRadius = radius * 2 + 1;
        float limit = Mth.sqrt(radius * radius + radius * radius);
        for (int i = 0; i < searchRadius; i++) {
            for (int j = 0; j < searchRadius; j++) {
                int offsetX = x + i - radius;
                int offsetZ = z + j - radius;
                float differenceX = x - offsetX;
                float differenceZ = z - offsetZ;
                float distance = Mth.sqrt(differenceX * differenceX + differenceZ * differenceZ);
                double theta = Math.toDegrees(Math.atan2(differenceX, differenceZ)) * 0.01f;
                double noise = (COVERING_NOISE.getValue(x * 10000 + theta, z * 10000 + theta, true)+1)/2;
                double threshold = Easing.SINE_IN_OUT.clamped(noise, 0.5f, 2) * radius * (limit-distance)/limit;
                if (distance <= threshold) {
                    mutable.set(offsetX, center.getY(), offsetZ);
                    int verticalRange = 4;
                    for (int k = 0; !level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::canBeReplaced) && k < verticalRange; ++k) {
                        mutable.move(Direction.UP);
                    }
                    for (int k = 0; level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::canBeReplaced) && k < verticalRange; ++k) {
                        mutable.move(Direction.DOWN);
                    }
                    if (!level.getBlockState(mutable).canBeReplaced()) {
                        positions.add(mutable.immutable());
                    }
                }
            }
        }
        return positions;
    }

    public static void updateLeaves(LevelAccessor pLevel, Set<BlockPos> logPositions) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        for (int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (BlockPos pos : Lists.newArrayList(logPositions)) {
            for (Direction direction : Direction.values()) {
                mutable.setWithOffset(pos, direction);
                if (!logPositions.contains(mutable)) {
                    BlockState blockstate = pLevel.getBlockState(mutable);
                    if (blockstate.hasProperty(LeavesBlock.DISTANCE)) {
                        list.get(0).add(mutable.immutable());
                        pLevel.setBlock(mutable, blockstate.setValue(LeavesBlock.DISTANCE, 1), 19);
                    }
                }
            }
        }

        for (int l = 1; l < 6; ++l) {
            Set<BlockPos> set = list.get(l - 1);
            Set<BlockPos> set1 = list.get(l);

            for (BlockPos pos : set) {
                for (Direction direction1 : Direction.values()) {
                    mutable.setWithOffset(pos, direction1);
                    if (!set.contains(mutable) && !set1.contains(mutable)) {
                        BlockState blockstate1 = pLevel.getBlockState(mutable);
                        if (blockstate1.hasProperty(LeavesBlock.DISTANCE)) {
                            int k = blockstate1.getValue(LeavesBlock.DISTANCE);
                            if (k > l + 1) {
                                BlockState blockstate2 = blockstate1.setValue(LeavesBlock.DISTANCE, l + 1);
                                pLevel.setBlock(mutable, blockstate2, 19);
                                set1.add(mutable.immutable());
                            }
                        }
                    }
                }
            }
        }
    }
}