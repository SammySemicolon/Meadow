package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LEAVES;

public class PuffyLeavesPart extends MineralTreePart {

    public static final Codec<PuffyLeavesPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.list(Codec.INT).fieldOf("leafSizes").forGetter(obj -> obj.leafSizes),
                            Codec.INT.fieldOf("extraBlobs").forGetter(obj -> obj.extraBlobs),
                            Codec.INT.fieldOf("minBlobOffset").forGetter(obj -> obj.minBlobOffset),
                            Codec.INT.fieldOf("maxBlobOffset").forGetter(obj -> obj.maxBlobOffset))
                    .apply(inst, PuffyLeavesPart::new)
            );

    private final List<Integer> leafSizes;
    private final int extraBlobs;
    private final int minBlobOffset;
    private final int maxBlobOffset;

    public PuffyLeavesPart(List<Integer> leafSizes, int extraBlobs, int minBlobOffset, int maxBlobOffset) {
        super(MineralTreePartTypes.PUFFY_LEAVES);
        this.leafSizes = leafSizes;
        this.extraBlobs = extraBlobs;
        this.minBlobOffset = minBlobOffset;
        this.maxBlobOffset = maxBlobOffset;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        RandomSource random = level.getRandom();
        BlockPos.MutableBlockPos offsetPos = partPos.mutable();
        feature.makeBlob(level, bundle.leaves, filler.getLayer(LEAVES), offsetPos, leafSizes);
        for (int i = 0; i < extraBlobs; i++) {
            int xOffset = (random.nextIntBetweenInclusive(minBlobOffset, maxBlobOffset)) * (random.nextBoolean()? -1 : 1);
            int yOffset = (random.nextIntBetweenInclusive(minBlobOffset, maxBlobOffset)-1) * (random.nextBoolean()? -1 : 1);
            int zOffset = (random.nextIntBetweenInclusive(minBlobOffset, maxBlobOffset)) * (random.nextBoolean()? -1 : 1);
            var extraPos = offsetPos.mutable().move(xOffset, yOffset, zOffset);
            feature.makeBlob(level, bundle.leaves, filler.getLayer(LEAVES), extraPos, leafSizes);
        }
        return new PartPlacementResult(true, partPos);
    }
}
