package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

public class StraightTrunkPart extends MineralTreePart {

    public static final MapCodec<StraightTrunkPart> CODEC =
            RecordCodecBuilder.mapCodec(inst -> inst.group(
                            Codec.intRange(0, 64).fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.intRange(0, 64).fieldOf("maxHeight").forGetter(obj -> obj.maxHeight)
                    )
                    .apply(inst, StraightTrunkPart::new));
    
    public final int minHeight;
    public final int maxHeight;

    public StraightTrunkPart(int height) {
        this(height, height);
    }
    public StraightTrunkPart(int minHeight, int maxHeight) {
        super(MineralTreePartTypes.STRAIGHT_TRUNK);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        RandomSource random = level.getRandom();
        int trunkHeight = random.nextIntBetweenInclusive(minHeight, maxHeight);
        BlockPos.MutableBlockPos mutable = partPos.mutable();
        boolean trunk = feature.makeStraightTrunk(level, filler, mutable, trunkHeight);
        return conditionalSuccess(trunk, mutable.immutable());
    }
}
