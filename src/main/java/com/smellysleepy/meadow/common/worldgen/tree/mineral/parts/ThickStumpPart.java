package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LOGS;

public class ThickStumpPart extends MineralTreePart {

    public static final Codec<ThickStumpPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.list(Codec.INT).fieldOf("sizes").forGetter(obj -> obj.sizes))
                    .apply(inst, ThickStumpPart::new)
            );

    public final List<Integer> sizes;

    public ThickStumpPart(List<Integer> sizes) {
        super(MineralTreePartTypes.THICK_STUMP);
        this.sizes = sizes;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        BlockPos.MutableBlockPos mutable = partPos.mutable();
        boolean stump = feature.makeDiamond(level, MeadowBlockRegistry.ASPEN_LOG, filler.getLayer(LOGS), mutable, sizes);
        return conditionalSuccess(stump, mutable.immutable());
    }
}
