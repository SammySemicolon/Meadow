package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

public class DirectionalOffset extends MineralTreePart {

    public static final MapCodec<DirectionalOffset> CODEC = MapCodec.unit(new DirectionalOffset());

    public DirectionalOffset() {
        super(MineralTreePartTypes.DIRECTIONAL_OFFSET);
    }

    @Override
    public PartPlacementResult place(WorldGenLevel level, MineralTreeFeature feature, MineralTreeFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos partPos, BlockPos featurePos, ExtraPartResultData extraData) {
        Direction direction = Direction.from2DDataValue(level.getRandom().nextInt(4));
        BlockPos relative = partPos.relative(direction);
        return success(relative).addExtraData(new DirectionalResultData(relative, direction));
    }
}
