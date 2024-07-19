package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.google.common.collect.ImmutableList;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.tree.AbstractTreeFeature;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.LodestoneLayerToken;

import java.util.*;
import java.util.function.Supplier;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.updateLeaves;
import static net.minecraft.tags.BlockTags.MOSS_REPLACEABLE;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class MineralTreeFeature extends AbstractTreeFeature<MineralTreeFeatureConfiguration> {

    public static final LodestoneLayerToken COVERING = new LodestoneLayerToken();
    public static final LodestoneLayerToken PLANTS = new LodestoneLayerToken();
    public static final LodestoneLayerToken LOGS = new LodestoneLayerToken();
    public static final LodestoneLayerToken FOLIAGE = new LodestoneLayerToken();
    public static final LodestoneLayerToken LEAVES = new LodestoneLayerToken();

    public MineralTreeFeature() {
        super(MineralTreeFeatureConfiguration.CODEC);
    }

    @Override
    public final boolean place(FeaturePlaceContext<MineralTreeFeatureConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }

        var filler = new LodestoneBlockFiller().addLayers(COVERING, PLANTS, LOGS, LEAVES, FOLIAGE);
        List<MineralTreePart> parts = context.config().parts;

        Set<BlockPos> endPoints = null;
        Map<BlockPos, MineralTreePart.ExtraPartResultData> extraData = new HashMap<>();
        for (MineralTreePart part : parts) {
            if (endPoints != null) {
                Set<BlockPos> newEndPoints = new HashSet<>();
                Map<BlockPos, MineralTreePart.ExtraPartResultData> newExtraData = new HashMap<>();
                for (BlockPos partEndPoint : endPoints) {
                    MineralTreePart.PartPlacementResult result = part.place(level, this, config, filler, partEndPoint, pos, extraData.get(partEndPoint));
                    if (!result.isSuccess) {
                        return false;
                    }
                    newEndPoints.addAll(result.partEndPoints);
                    for (BlockPos endPoint : result.partEndPoints) {
                        newExtraData.put(endPoint, result.extraData);
                    }
                }
                endPoints = newEndPoints;
                extraData = newExtraData;
            } else {
                MineralTreePart.PartPlacementResult result = part.place(level, this, config, filler, pos, pos, null);
                if (!result.isSuccess) {
                    return false;
                }
                endPoints = result.partEndPoints;
                for (BlockPos endPoint : endPoints) {
                    extraData.put(endPoint, result.extraData);
                }
            }
        }
        int coverageRadius = RandomHelper.randomBetween(level.getRandom(), 4, 8);
        BlockStateEntry entry = create(config.grass.defaultBlockState()).setForcePlace().build();
        Set<BlockPos> covering = WorldgenHelper.generateCovering(level, pos, coverageRadius);
        for (BlockPos blockPos : covering) {
            filler.getLayer(COVERING).put(blockPos, entry);
        }

        filler.fill(level);
        updateLeaves(level, filler.getLayer(LOGS).keySet());
        return true;
    }

    public boolean makeStraightTrunk(WorldGenLevel level, LodestoneBlockFiller filler, BlockPos.MutableBlockPos pos, int trunkHeight) {
        var entry = create(MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState());
        for (int i = 0; i <= trunkHeight; i++) { //Main Trunk
            if (!canPlace(level, pos)) {
                return false;
            }
            filler.getLayer(LOGS).put(pos.immutable(), entry);
            if (i < trunkHeight) {
                pos.move(Direction.UP);
            }
        }
        return true;
    }

    public boolean makeBlob(WorldGenLevel level, Block block, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        return makeBlob(level, block.defaultBlockState(), layer, pos, sizes);
    }

    public boolean makeBlob(WorldGenLevel level, BlockState state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        var mutable = pos.mutable();
        var entry = create(state).build();
        boolean success = true;

        for (int size : sizes) {
            boolean result = makeRoundShape(level, layer, mutable, size, entry);
            if (!result) {
                success = false;
            }
            mutable.move(Direction.UP);
        }
        return success;
    }

    public boolean makeRoundShape(WorldGenLevel level, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int size, BlockStateEntry entry) {
        return makeRoundShape(level, layer, pos, size, () -> entry);
    }

    public boolean makeRoundShape(WorldGenLevel level, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int size, Supplier<BlockStateEntry> entry) {
        boolean success = true;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (Math.abs(x) == size && Math.abs(z) == size) {
                    continue;
                }
                BlockPos offset = pos.offset(x, 0, z);
                if (!canPlace(level, offset)) {
                    success = false;
                }
                layer.put(offset, entry.get());
            }
        }
        return success;
    }

    public boolean makeDiamond(WorldGenLevel level, Block block, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        return makeDiamond(level, block.defaultBlockState(), layer, pos, sizes);
    }

    public boolean makeDiamond(WorldGenLevel level, BlockState state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        var mutable = pos.mutable();
        var entry = create(state).build();
        boolean success = true;

        for (int size : sizes) {
            boolean result = makeDiamondShape(level, layer, mutable, size, entry);
            if (!result) {
                success = false;
            }
            mutable.move(Direction.UP);
        }
        return success;
    }

    public boolean makeDiamondShape(WorldGenLevel level, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int size, BlockStateEntry entry) {
        return makeDiamondShape(level, layer, pos, size, () -> entry);
    }
    public boolean makeDiamondShape(WorldGenLevel level, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int size, Supplier<BlockStateEntry> entry) {
        boolean success = true;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (Math.abs(x) + Math.abs(z) > size) {
                    continue;
                }
                BlockPos offset = pos.offset(x, 0, z);
                if (!canPlace(level, offset)) {
                    success = false;
                }
                layer.put(offset, entry.get());
            }
        }
        return success;
    }
}
