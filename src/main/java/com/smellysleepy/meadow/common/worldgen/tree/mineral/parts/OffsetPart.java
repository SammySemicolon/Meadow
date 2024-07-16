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

public class OffsetPart extends MineralTreePart {

    public static final Codec<OffsetPart> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.INT.fieldOf("xOffset").forGetter(obj -> obj.xOffset),
                            Codec.INT.fieldOf("yOffset").forGetter(obj -> obj.yOffset),
                            Codec.INT.fieldOf("zOffset").forGetter(obj -> obj.zOffset)
                    )
                    .apply(inst, OffsetPart::new));

    public final int xOffset;
    public final int yOffset;
    public final int zOffset;

    public OffsetPart(int xOffset, int yOffset, int zOffset) {
        super(MineralTreePartTypes.OFFSET);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralFloraRegistryBundle bundle, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos) {
        return new PartPlacementResult(true, partPos.offset(xOffset, yOffset, zOffset));
    }
}
