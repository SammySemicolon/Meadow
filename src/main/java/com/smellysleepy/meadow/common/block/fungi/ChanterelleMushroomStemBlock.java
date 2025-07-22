package com.smellysleepy.meadow.common.block.fungi;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ChanterelleMushroomStemBlock extends Block {

    public static final EnumProperty<ChanterelleLayer> LAYER = EnumProperty.create("layer", ChanterelleLayer.class);

    public enum ChanterelleLayer implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        public final String type;

        ChanterelleLayer(String type) {
            this.type = type;
        }

        @Override
        public String getSerializedName() {
            return type;
        }
    }

    public ChanterelleMushroomStemBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(LAYER, ChanterelleLayer.MIDDLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYER);
    }
}
