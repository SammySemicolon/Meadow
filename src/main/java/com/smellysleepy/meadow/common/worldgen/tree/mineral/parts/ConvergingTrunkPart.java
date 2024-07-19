package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LOGS;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class ConvergingTrunkPart extends MineralTreePart {

    public static final Codec<ConvergingTrunkPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.intRange(0, 8).fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.intRange(0, 8).fieldOf("maxHeight").forGetter(obj -> obj.maxHeight),
                            Codec.intRange(0, 8).fieldOf("minOffset").forGetter(obj -> obj.minOffset),
                            Codec.intRange(0, 8).fieldOf("maxOffset").forGetter(obj -> obj.maxOffset)
                    )
                    .apply(inst, ConvergingTrunkPart::new));

    public final int minHeight;
    public final int maxHeight;
    public final int minOffset;
    public final int maxOffset;

    public ConvergingTrunkPart(int minHeight, int maxHeight, int minOffset, int maxOffset) {
        super(MineralTreePartTypes.CONVERGING_TRUNK);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        RandomSource random = level.getRandom();
        int rootsOffset = random.nextIntBetweenInclusive(minOffset, maxOffset);
        int directionOffset = random.nextInt(4);
        int step = random.nextIntBetweenInclusive(1, 2);
        int lowestHeight = maxHeight;
        for (int i = 0; i < 4; i++) {
            Direction direction = Direction.from2DDataValue((i*step+directionOffset)%4);
            int trunkHeight = random.nextIntBetweenInclusive(minHeight, maxHeight);
            if (trunkHeight < lowestHeight) {
                lowestHeight = trunkHeight;
            }
            BlockPos.MutableBlockPos mutable = partPos.mutable().move(direction, rootsOffset);
            boolean success = feature.makeStraightTrunk(level, filler, mutable, trunkHeight);
            if (!success) {
                return failure();
            }
            for (int j = 0; j < rootsOffset; j++) {
                mutable.move(direction, -1);
                if (!feature.canPlace(level, mutable)) {
                    return failure();
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState()));
            }
        }
        return success(featurePos.above(lowestHeight));
    }
}