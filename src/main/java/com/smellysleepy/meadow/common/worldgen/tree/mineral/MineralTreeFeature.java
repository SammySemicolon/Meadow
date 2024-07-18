package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.AbstractTreeFeature;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.BlockStateEntry;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.LodestoneLayerToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.smellysleepy.meadow.common.worldgen.WorldgenHelper.updateLeaves;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class MineralTreeFeature extends AbstractTreeFeature<MineralTreeFeatureConfiguration> {

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
        var bundle = context.config().bundle;
        if (level.isEmptyBlock(pos.below()) || !bundle.sapling.get().defaultBlockState().canSurvive(level, pos)) {
            return false;
        }

        var filler = new LodestoneBlockFiller().addLayers(LOGS, LEAVES, FOLIAGE);
        List<MineralTreePart> parts = context.config().parts;

        List<BlockPos> endPoints = null;
        for (MineralTreePart part : parts) {
            if (endPoints != null) {
                List<BlockPos> newEndPoints = new ArrayList<>();
                for (BlockPos partEndPoint : endPoints) {
                    MineralTreePart.PartPlacementResult result = part.place(level, this, bundle, filler, partEndPoint, pos);
                    if (!result.isSuccess) {
                        return false;
                    }
                    newEndPoints.addAll(result.partEndPoints);
                }
                endPoints = newEndPoints;
            } else {
                MineralTreePart.PartPlacementResult result = part.place(level, this, bundle, filler, pos, pos);
                if (!result.isSuccess) {
                    return false;
                }
                endPoints = result.partEndPoints;
            }
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

    public boolean makeBlob(WorldGenLevel level, Supplier<Block> state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        return makeBlob(level, state.get().defaultBlockState(), layer, pos, sizes);
    }

    public boolean makeBlob(WorldGenLevel level, BlockState state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        var mutable = pos.mutable();
        var entry = create(state).build();
        boolean success = true;

        for (int size : sizes) {
            mutable.move(Direction.UP);
            boolean result = makeRoundShape(level, layer, mutable, size, entry);
            if (!result) {
                success = false;
            }
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

    public boolean makeDiamond(WorldGenLevel level, Supplier<Block> state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        return makeDiamond(level, state.get().defaultBlockState(), layer, pos, sizes);
    }

    public boolean makeDiamond(WorldGenLevel level, BlockState state, LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos.MutableBlockPos pos, List<Integer> sizes) {
        var mutable = pos.mutable();
        var entry = create(state).build();
        boolean success = true;

        for (int size : sizes) {
            mutable.move(Direction.UP);
            boolean result = makeDiamondShape(level, layer, mutable, size, entry);
            if (!result) {
                success = false;
            }
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
