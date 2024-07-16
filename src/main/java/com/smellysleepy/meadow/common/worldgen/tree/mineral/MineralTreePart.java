package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.mojang.serialization.Codec;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

public abstract class MineralTreePart {

    public static final Codec<MineralTreePart> CODEC = MineralTreePartTypes.CODEC.dispatch(MineralTreePart::getType, MineralTreePartType::getCodec);

    private final MineralTreePartType<?> type;

    protected MineralTreePart(MineralTreePartType<?> type) {
        this.type = type;
    }

    public MineralTreePartType<?> getType() {
        return type;
    }

    public abstract PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos);

    public static class PartPlacementResult {
        public final boolean isSuccess;
        public final List<BlockPos> partEndPoints;


        public PartPlacementResult(boolean isSuccess) {
            this(isSuccess, BlockPos.ZERO);
        }

        public PartPlacementResult(boolean isSuccess, BlockPos pos) {
            this(isSuccess, List.of(pos));
        }

        public PartPlacementResult(boolean isSuccess, List<BlockPos> partEndPoints) {
            this.isSuccess = isSuccess;
            this.partEndPoints = partEndPoints;
        }
    }
}
