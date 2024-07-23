package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature.LEAVES;

public class LeafBlobPart extends MineralTreePart {

    public static final Codec<LeafBlobPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                    Codec.list(Codec.INT).fieldOf("leafSizes").forGetter(obj -> obj.leafSizes))
                    .apply(inst, LeafBlobPart::new)
            );

    private final List<Integer> leafSizes;
    public LeafBlobPart(List<Integer> leafSizes) {
        super(MineralTreePartTypes.LEAF_BLOB);
        this.leafSizes = leafSizes;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        feature.makeBlob(level, config.leaves, filler.getLayer(LEAVES), partPos.mutable(), leafSizes);
        return success(partPos);
    }
}
