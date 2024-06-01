package com.smellysleepy.meadow.common.worldgen;

import com.google.common.collect.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

public class WorldgenHelper {

    public static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};

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