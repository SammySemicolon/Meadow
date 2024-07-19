package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.block.meadow.wood.MeadowLogBlock;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LOGS;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class StumpFoldsPart extends MineralTreePart {

    public static final Codec<StumpFoldsPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.intRange(0, 64).fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.intRange(0, 64).fieldOf("maxHeight").forGetter(obj -> obj.maxHeight),
                            Codec.intRange(0, 16).fieldOf("minWidth").forGetter(obj -> obj.minWidth),
                            Codec.intRange(0, 16).fieldOf("maxWidth").forGetter(obj -> obj.maxWidth),
                            Codec.intRange(0, 16).fieldOf("minDistance").forGetter(obj -> obj.minDistance),
                            Codec.intRange(0, 16).fieldOf("maxDistance").forGetter(obj -> obj.maxDistance),
                            Codec.intRange(0, 4).fieldOf("minCount").forGetter(obj -> obj.minCount),
                            Codec.intRange(0, 4).fieldOf("maxCount").forGetter(obj -> obj.maxCount)
                    )
                    .apply(inst, StumpFoldsPart::new));

    public final int minHeight;
    public final int maxHeight;
    public final int minWidth;
    public final int maxWidth;
    public final int minDistance;
    public final int maxDistance;
    public final int minCount;
    public final int maxCount;

    public StumpFoldsPart(int minHeight, int maxHeight, int minWidth, int maxWidth, int minDistance, int maxDistance, int minCount, int maxCount) {
        super(MineralTreePartTypes.STUMP_FOLDS);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        RandomSource random = level.getRandom();
        int count = random.nextIntBetweenInclusive(minCount, maxCount);
        for (int i = 0; i < count; i++) {
            BlockPos.MutableBlockPos mutable = partPos.mutable();
            Direction direction = Direction.from2DDataValue((2*i)%4);
            Direction clockWise = direction.getClockWise();
            BlockState state = MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState().setValue(MeadowLogBlock.AXIS, direction.getAxis());
            int height = random.nextIntBetweenInclusive(minHeight, maxHeight);
            int width = random.nextIntBetweenInclusive(minWidth, maxWidth);
            int distance = random.nextIntBetweenInclusive(minDistance, maxDistance);
            for (int j = 0; j <= distance+width; j++) {
                mutable.move(direction);
                if (!feature.canPlace(level, mutable)) {
                    continue;
                }
                if (j >= distance) {
                    height--;
                    width--;
                }
                for (int k = -width; k <= width; k++) {
                    BlockPos.MutableBlockPos copy = mutable.mutable().move(clockWise, k);
                    int relativeHeight = height-Math.abs(k);
                    for (int l = 0; l < relativeHeight; l++) {
                        filler.getLayer(LOGS).put(copy.immutable(), create(state));
                        copy.move(Direction.UP);
                    }
                }
            }
        }
        return success(partPos);
    }
}
