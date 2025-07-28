package com.smellysleepy.meadow.common.block.fungi;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class ChanterelleMushroomStemBlock extends RotatedPillarBlock {

    public static final EnumProperty<ChanterelleLayer> LAYER = EnumProperty.create("layer", ChanterelleLayer.class);

    public ChanterelleMushroomStemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LAYER, ChanterelleLayer.MIDDLE));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LAYER);
    }

    public enum ChanterelleLayer implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        public final String type;

        ChanterelleLayer(String type) {
            this.type = type;
        }

        @Override
        public @NotNull String getSerializedName() {
            return type;
        }
    }
}
