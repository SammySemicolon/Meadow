package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LEAVES;

public class LeafDiamondPart extends MineralTreePart {

    public static final Codec<LeafDiamondPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
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
        feature.makeDiamond(level, config.leaves, filler.getLayer(LEAVES), partPos.mutable(), leafSizes);
        return success(partPos);
    }
}
