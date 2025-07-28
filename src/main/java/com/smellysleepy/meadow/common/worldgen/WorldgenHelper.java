package com.smellysleepy.meadow.common.worldgen;

import com.google.common.collect.*;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldgenHelper {

    private static final PerlinSimplexNoise COVERING_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public static Set<BlockPos> fetchCoveringPositions(ServerLevelAccessor level, BlockPos center, int radius) {
        return fetchCoveringPositions(level, center, radius,
                p -> {
                    BlockState state = level.getBlockState(p);
                    if (state.canBeReplaced() || !state.isFaceSturdy(level, p, Direction.UP)) {
                        return false;
                    }
                    return !level.getBlockState(p.below()).canBeReplaced() && level.getBlockState(p.above()).canBeReplaced();
                },
                false);
    }
    public static Set<BlockPos> fetchHangingBlockPositions(ServerLevelAccessor level, BlockPos center, int radius) {
        return fetchCoveringPositions(level, center, radius,
                p -> {
                    BlockState state = level.getBlockState(p);
                    if (state.canBeReplaced() || !state.isFaceSturdy(level, p, Direction.DOWN)) {
                        BlockState above = level.getBlockState(p.above());
                        return false;
                    }
                    return level.getBlockState(p.below()).canBeReplaced();
                },
                true);
    }
    public static Set<BlockPos> fetchCoveringPositions(ServerLevelAccessor level, BlockPos center, int radius, Predicate<BlockPos> statePredicate, boolean flipVerticalConditions) {
        Set<BlockPos> positions = new HashSet<>();
        int x = center.getX();
        int z = center.getZ();
        var mutable = new BlockPos.MutableBlockPos();

        int verticalRange = 6;
        float limit = Mth.sqrt(radius * radius + radius * radius);
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int offsetX = x + i;
                int offsetZ = z + j;
                float distance = Mth.sqrt(i * i + j * j);
                double theta = Math.toDegrees(Math.atan2(i, j)) * 0.01f;
                double noise = (COVERING_NOISE.getValue(x * 10000 + theta, z * 10000 + theta, true)+1)/2;
                double threshold = Easing.SINE_IN_OUT.clamped(noise, 0.5f, 2) * radius * (limit-distance)/limit;
                if (distance <= threshold) {
                    mutable.set(offsetX, center.getY(), offsetZ);
                    for (int k = 0; !level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::canBeReplaced) && k < verticalRange; ++k) {
                        mutable.move(flipVerticalConditions ? Direction.DOWN : Direction.UP);
                    }
                    for (int k = 0; level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::canBeReplaced) && k < verticalRange; ++k) {
                        mutable.move(flipVerticalConditions ? Direction.UP : Direction.DOWN);
                    }
                    if (statePredicate.test(mutable)) {
                        positions.add(mutable.immutable());
                    }
                }
            }
        }
        return positions;
    }

    public static void growPointedCalcification(LevelAccessor level, Block block, BlockPos pos, Direction pDirection, int pHeight, boolean pMergeTip) {
        if (isCalcifiedBase(level.getBlockState(pos.relative(pDirection.getOpposite())))) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
            buildBaseToTipColumn(block, pDirection, pHeight, pMergeTip, (state) -> {
                if (state.is(block)) {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, level.isWaterAt(blockpos$mutableblockpos));
                }

                level.setBlock(blockpos$mutableblockpos, state, 2);
                blockpos$mutableblockpos.move(pDirection);
            });
        }
    }

    private static void buildBaseToTipColumn(Block block, Direction pDirection, int pHeight, boolean pMergeTip, Consumer<BlockState> pBlockSetter) {
        if (pHeight >= 3) {
            pBlockSetter.accept(createPointedCalcification(block, pDirection, DripstoneThickness.BASE));

            for(int i = 0; i < pHeight - 3; ++i) {
                pBlockSetter.accept(createPointedCalcification(block, pDirection, DripstoneThickness.MIDDLE));
            }
        }

        if (pHeight >= 2) {
            pBlockSetter.accept(createPointedCalcification(block, pDirection, DripstoneThickness.FRUSTUM));
        }

        if (pHeight >= 1) {
            pBlockSetter.accept(createPointedCalcification(block, pDirection, pMergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }
    private static BlockState createPointedCalcification(Block block, Direction pDirection, DripstoneThickness pDripstoneThickness) {
        return block.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, pDirection).setValue(PointedDripstoneBlock.THICKNESS, pDripstoneThickness);
    }

    private static boolean isCalcifiedBase(BlockState state) {
        return state.is(MeadowTags.BlockTags.CALCIFICATION) || state.is(net.minecraft.tags.BlockTags.MOSS_REPLACEABLE);
    }

    public static void updateLeaves(LevelAccessor level, Set<BlockPos> logPositions) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        for (int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (BlockPos pos : Lists.newArrayList(logPositions)) {
            for (Direction direction : Direction.values()) {
                mutable.setWithOffset(pos, direction);
                if (!logPositions.contains(mutable)) {
                    BlockState blockstate = level.getBlockState(mutable);
                    if (blockstate.hasProperty(LeavesBlock.DISTANCE)) {
                        list.get(0).add(mutable.immutable());
                        level.setBlock(mutable, blockstate.setValue(LeavesBlock.DISTANCE, 1), 19);
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
                        BlockState blockstate1 = level.getBlockState(mutable);
                        if (blockstate1.hasProperty(LeavesBlock.DISTANCE)) {
                            int k = blockstate1.getValue(LeavesBlock.DISTANCE);
                            if (k > l + 1) {
                                BlockState blockstate2 = blockstate1.setValue(LeavesBlock.DISTANCE, l + 1);
                                level.setBlock(mutable, blockstate2, 19);
                                set1.add(mutable.immutable());
                            }
                        }
                    }
                }
            }
        }
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency, boolean blur) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency, blur);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency, boolean blur) {
        if (blur) {
            return getBlurredNoise(noiseSampler, blockX, blockZ, offset, noiseFrequency);
        }
        return noiseSampler.noise((blockX + offset) * noiseFrequency, 0, (blockZ + offset) * noiseFrequency) + 1;
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getBlurredNoise(noiseSampler, blockX, blockZ, 0, 1, noiseFrequency);
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return getBlurredNoise(noiseSampler, blockX, blockZ, offset, 1, noiseFrequency);
    }

    public static double getBlurredNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, int blurRadius, float noiseFrequency) {
        double noise = 0;
        int count = 0;
        for (int x = -blurRadius; x <= blurRadius; x++) {
            for (int z = -blurRadius; z <= blurRadius; z++) {
                noise += getNoise(noiseSampler, blockX, blockZ, offset, noiseFrequency);
                count++;
            }
        }
        return noise / count;
    }

    public static double getNoise(ImprovedNoise noiseSampler, BlockPos pos, float noiseFrequency) {
        return getNoise(noiseSampler, pos.getX(), pos.getZ(), 0, noiseFrequency);
    }

    public static double getNoise(ImprovedNoise noiseSampler, BlockPos pos, int offset, float noiseFrequency) {
        return getNoise(noiseSampler, pos.getX(), pos.getZ(), offset, noiseFrequency);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency);
    }

    public static double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return (noiseSampler.noise((blockX + offset) * noiseFrequency, 0, (blockZ + offset) * noiseFrequency) + 1) * 0.5f;
    }
}