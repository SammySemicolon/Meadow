package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

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
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        int downwardsOffset = Mth.ceil(leafSizes.size() / 2f);
        feature.makeLeafBlob(bundle, filler, partPos.mutable().move(Direction.DOWN, downwardsOffset), leafSizes);
        return new PartPlacementResult(true, partPos);
    }
}
