package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.ArrayList;
import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LOGS;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class SplitBranchesPart extends MineralTreePart {

    public static final Codec<SplitBranchesPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.intRange(0, 8).fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.intRange(0, 8).fieldOf("maxHeight").forGetter(obj -> obj.maxHeight),
                            Codec.intRange(0, 8).fieldOf("minOffset").forGetter(obj -> obj.minOffset),
                            Codec.intRange(0, 8).fieldOf("maxOffset").forGetter(obj -> obj.maxOffset),
                            Codec.intRange(0, 4).fieldOf("minCount").forGetter(obj -> obj.minCount),
                            Codec.intRange(0, 4).fieldOf("maxCount").forGetter(obj -> obj.maxCount)
                    )
                    .apply(inst, SplitBranchesPart::new));

    public final int minHeight;
    public final int maxHeight;
    public final int minOffset;
    public final int maxOffset;
    public final int minCount;
    public final int maxCount;

    public SplitBranchesPart(int minHeight, int maxHeight, int minOffset, int maxOffset, int minCount, int maxCount) {
        super(MineralTreePartTypes.SPLITTING_BRANCHES);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        RandomSource random = level.getRandom();
        List<BlockPos> endPoints = new ArrayList<>();
        int count = random.nextIntBetweenInclusive(minCount, maxCount);
        for (int i = 0; i < count; i++) {
            int rootsOffset = random.nextIntBetweenInclusive(minOffset, maxOffset);
            Direction direction = Direction.from2DDataValue(i);
            int trunkHeight = random.nextIntBetweenInclusive(minHeight, maxHeight);
            BlockPos.MutableBlockPos mutable = partPos.mutable();
            for (int j = 0; j < rootsOffset; j++) {
                mutable.move(direction);
                if (!feature.canPlace(level, mutable)) {
                    return new PartPlacementResult(false);
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState()));
            }
            boolean success = feature.makeStraightTrunk(level, filler, mutable, trunkHeight);
            if (!success) {
                return new PartPlacementResult(false);
            }
            endPoints.add(mutable.immutable());
        }
        return new PartPlacementResult(true, endPoints);
    }
}