package com.smellysleepy.meadow.common.block.meadow;

import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import team.lodestar.lodestone.systems.block.*;

import java.awt.*;

public class MeadowLeavesBlock extends LodestoneLeavesBlock {

    public static final Color MEADOW_YELLOW = new Color(255, 178, 38);
    public static final Color MEADOW_ORANGE = new Color(229, 94, 53);

    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 4);

    public MeadowLeavesBlock(Properties properties) {
        super(properties, MEADOW_ORANGE, MEADOW_YELLOW);
    }

    @Override
    public IntegerProperty getColorProperty() {
        return COLOR;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        final BlockState state = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        final int value = state.getValue(DISTANCE);
        return state.setValue(COLOR, Mth.clamp(4 - pCurrentPos.getY()%5, 0, 4));

    }
}