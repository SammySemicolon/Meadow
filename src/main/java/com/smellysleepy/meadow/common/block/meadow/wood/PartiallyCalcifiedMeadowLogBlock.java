package com.smellysleepy.meadow.common.block.meadow.wood;

import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.common.*;
import team.lodestar.lodestone.systems.block.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class PartiallyCalcifiedMeadowLogBlock extends LodestoneDirectionalBlock {
    public PartiallyCalcifiedMeadowLogBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction.equals(ToolActions.AXE_STRIP)) {
            return MeadowBlockRegistry.ASPEN_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(FACING).getAxis());
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}