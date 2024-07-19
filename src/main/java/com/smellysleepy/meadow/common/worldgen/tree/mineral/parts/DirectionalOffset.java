package com.smellysleepy.meadow.common.worldgen.tree.mineral.parts;

import com.mojang.serialization.Codec;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeatureConfiguration;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.registry.worldgen.MineralTreePartTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

public class DirectionalOffset extends MineralTreePart {

    public static final Codec<DirectionalOffset> CODEC = Codec.unit(new DirectionalOffset());

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
