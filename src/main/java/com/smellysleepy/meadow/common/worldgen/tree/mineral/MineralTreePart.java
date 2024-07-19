package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.mojang.serialization.Codec;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.*;

public abstract class MineralTreePart {

    public static final Codec<MineralTreePart> CODEC = MineralTreePartTypes.CODEC.dispatch(MineralTreePart::getType, MineralTreePartType::getCodec);

    private final MineralTreePartType<?> type;

    protected MineralTreePart(MineralTreePartType<?> type) {
        this.type = type;
    }

    public MineralTreePartType<?> getType() {
        return type;
    }

    public abstract PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData);


    public PartPlacementResult failure() {
        return new PartPlacementResult(false, Collections.emptySet());
    }

    public PartPlacementResult success(BlockPos pos) {
        return success(Set.of(pos));
    }
    public PartPlacementResult success(Set<BlockPos> partEndPoints) {
        return new PartPlacementResult(true, partEndPoints);
    }

    public PartPlacementResult conditionalSuccess(boolean success, BlockPos pos) {
        return success ? success(Set.of(pos)) : failure();
    }
    public PartPlacementResult conditionalSuccess(boolean success, Set<BlockPos> partEndPoints) {
        return success ? success(partEndPoints) : failure();
    }

    public static class PartPlacementResult {
        public final boolean isSuccess;
        public final Set<BlockPos> partEndPoints;
        public ExtraPartResultData extraData;

        private PartPlacementResult(boolean isSuccess, Set<BlockPos> partEndPoints) {
            this.isSuccess = isSuccess;
            this.partEndPoints = partEndPoints;
        }

        public PartPlacementResult addExtraData(ExtraPartResultData extraData) {
            this.extraData = extraData;
            return this;
        }
    }

    public abstract static class ExtraPartResultData {

    }

    public static class DirectionalResultData extends ExtraPartResultData {
        public final Map<BlockPos, Direction> directionMap;

        public DirectionalResultData(Map<BlockPos, Direction> directionMap) {
            this.directionMap = directionMap;
        }

        public DirectionalResultData(BlockPos pos, Direction direction) {
            this.directionMap = new HashMap<>();
            this.directionMap.put(pos, direction);
        }
    }
}
