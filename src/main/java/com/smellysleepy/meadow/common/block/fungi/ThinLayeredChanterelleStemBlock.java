package com.smellysleepy.meadow.common.block.fungi;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ThinLayeredChanterelleStemBlock extends ThinNaturalChanterelleStemBlock {

    public static final EnumProperty<ChanterelleMushroomStemBlock.ChanterelleLayer> LAYER = ChanterelleMushroomStemBlock.LAYER;

    public ThinLayeredChanterelleStemBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(LAYER, ChanterelleMushroomStemBlock.ChanterelleLayer.MIDDLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LAYER);
    }
}
