package com.smellysleepy.meadow.common.block.meadow.wood;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class PartiallyCalcifiedMeadowLogBlock extends MeadowLogBlock {

    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");

    public PartiallyCalcifiedMeadowLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
        registerDefaultState(defaultBlockState().setValue(FLIPPED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FLIPPED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) {
            direction = direction.getOpposite();
        }
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(FLIPPED, direction.getAxisDirection().equals(Direction.AxisDirection.POSITIVE));
        }
        return state;
    }
}