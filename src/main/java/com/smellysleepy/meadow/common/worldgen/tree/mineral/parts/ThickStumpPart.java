package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature.LOGS;

public class ThickStumpPart extends MineralTreePart {

    public static final Codec<ThickStumpPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.intRange(0, 64).fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.intRange(0, 64).fieldOf("maxHeight").forGetter(obj -> obj.maxHeight),
                            Codec.intRange(0, 16).fieldOf("width").forGetter(obj -> obj.width)
                    )
                    .apply(inst, ThickStumpPart::new));

    public final int minHeight;
    public final int maxHeight;
    public final int width;

    public ThickStumpPart(int height, int width) {
        this(height, height, width);
    }

    public ThickStumpPart(int minHeight, int maxHeight, int width) {
        super(MineralTreePartTypes.THICK_STUMP);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.width = width;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        RandomSource random = level.getRandom();
        int trunkHeight = random.nextIntBetweenInclusive(minHeight, maxHeight);
        BlockPos.MutableBlockPos mutable = partPos.mutable();
        Integer[] sizes = new Integer[trunkHeight];
        Arrays.fill(sizes, width);
        boolean success = feature.makeDiamond(level, MeadowBlockRegistry.ASPEN_LOG, filler.getLayer(LOGS), mutable, Arrays.asList(sizes));
        return new PartPlacementResult(success, mutable.immutable());
    }
}
