package com.smellysleepy.meadow.common.block.mineral.plant;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.*;

public class TallMineralFlower extends TallFlowerBlock {

    public final TagKey<Block> oreTag;
    public TallMineralFlower(Properties properties, TagKey<Block> oreTag) {
        super(properties);
        this.oreTag = oreTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(MeadowTags.BlockTags.MINERAL_FLORA_CAN_PLACE_ON) || state.is(oreTag);
    }
}
