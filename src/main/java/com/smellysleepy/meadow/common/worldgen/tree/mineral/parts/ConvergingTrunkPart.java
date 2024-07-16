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
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
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
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        RandomSource random = level.getRandom();
        int rootsOffset = random.nextIntBetweenInclusive(minOffset, maxOffset);
        int lowestHeight = maxHeight;
        for (int i = 0; i < 4; i++) {
            Direction direction = Direction.from2DDataValue(i);
            int trunkHeight = random.nextIntBetweenInclusive(minHeight, maxHeight);
            if (trunkHeight < lowestHeight) {
                lowestHeight = trunkHeight;
            }
            BlockPos.MutableBlockPos mutable = partPos.mutable().move(direction, rootsOffset);
            boolean success = feature.makeStraightTrunk(level, filler, mutable, trunkHeight);
            if (!success) {
                return new PartPlacementResult(false);
            }
            for (int j = 0; j < rootsOffset; j++) {
                mutable.move(direction, -1);
                if (!feature.canPlace(level, mutable)) {
                    return new PartPlacementResult(false);
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState()));
            }
        }
        return new PartPlacementResult(true, featurePos.above(lowestHeight));
    }
}