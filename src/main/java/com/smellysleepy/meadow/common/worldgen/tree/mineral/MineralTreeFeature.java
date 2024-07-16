package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.AbstractTreeFeature;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
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
            }
            else {
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

    public void makeLeafBlob(MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos.MutableBlockPos pos, List<Integer> leafSizes) {
        var mutable = pos.mutable();
        var entry = create(bundle.leaves.get().defaultBlockState()).build();

        for (int leafSize : leafSizes) {
            mutable.move(Direction.UP);
            makeLeafSlice(filler.getLayer(LEAVES), mutable, leafSize, entry);
        }
    }

    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, BlockStateEntry entry) {
        makeLeafSlice(layer, pos, leavesSize, () -> entry);
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
