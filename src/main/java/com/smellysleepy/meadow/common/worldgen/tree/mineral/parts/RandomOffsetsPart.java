package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.HashSet;
import java.util.Set;

public class RandomOffsetsPart extends MineralTreePart {

    public static final Codec<RandomOffsetsPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.INT.fieldOf("offsetCount").forGetter(obj -> obj.offsetCount),
                            Codec.INT.fieldOf("minOffset").forGetter(obj -> obj.minOffset),
                            Codec.INT.fieldOf("maxOffset").forGetter(obj -> obj.maxOffset))
                    .apply(inst, RandomOffsetsPart::new)
            );

    private final int offsetCount;
    private final int minOffset;
    private final int maxOffset;

    public RandomOffsetsPart(int offsetCount, int minOffset, int maxOffset) {
        super(MineralTreePartTypes.RANDOM_OFFSETS);
        this.offsetCount = offsetCount;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        RandomSource random = level.getRandom();
        Set<BlockPos> endPoints = new HashSet<>();
        for (int i = 0; i < offsetCount; i++) {
            int xOffset = (random.nextIntBetweenInclusive(minOffset, maxOffset)) * (random.nextBoolean()? -1 : 1);
            int yOffset = (random.nextIntBetweenInclusive(minOffset, maxOffset)-1) * (random.nextBoolean()? -1 : 1);
            int zOffset = (random.nextIntBetweenInclusive(minOffset, maxOffset)) * (random.nextBoolean()? -1 : 1);
            var extraPos = partPos.offset(xOffset, yOffset, zOffset);
            endPoints.add(extraPos);
        }
        return success(endPoints);
    }
}
