package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

public class LeafDiamondPart extends MineralTreePart {

    public static final MapCodec<LeafDiamondPart> CODEC =
            RecordCodecBuilder.mapCodec(inst -> inst.group(
                    Codec.list(Codec.INT).fieldOf("leafSizes").forGetter(obj -> obj.leafSizes))
                    .apply(inst, LeafDiamondPart::new)
            );

    private final List<Integer> leafSizes;
    public LeafDiamondPart(List<Integer> leafSizes) {
        super(MineralTreePartTypes.LEAF_DIAMOND);
        this.leafSizes = leafSizes;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        feature.makeDiamond(level, config.leaves, filler.getLayer(MineralTreeFeature.LEAVES), partPos.mutable(), leafSizes);
        return success(partPos);
    }
}
