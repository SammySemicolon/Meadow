package com.smellysleepy.meadow.common.block.mineral;

import com.smellysleepy.meadow.registry.common.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.*;

public class TallMineralFlower extends TallFlowerBlock {

    public final TagKey<Block> oreTag;
    public TallMineralFlower(Properties pProperties, TagKey<Block> oreTag) {
        super(pProperties);
        this.oreTag = oreTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.MINERAL_FLORA_CAN_PLACE_ON) || pState.is(oreTag);
    }
}
