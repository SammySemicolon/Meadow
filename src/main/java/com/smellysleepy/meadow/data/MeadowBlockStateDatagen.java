package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.data.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import java.util.*;
import java.util.function.*;

public class MeadowBlockStateDatagen extends LodestoneBlockStateProvider {
    public MeadowBlockStateDatagen(PackOutput output, ExistingFileHelper exFileHelper, LodestoneItemModelProvider itemModelProvider) {
        super(output, MeadowMod.MEADOW, exFileHelper, itemModelProvider);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Supplier<Block>> blocks = new HashSet<>(MeadowBlockRegistry.BLOCKS.getEntries());
        AbstractBlockStateSmith.StateSmithData data = new AbstractBlockStateSmith.StateSmithData(this, blocks::remove);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::varyingRotationBlock, this::grassBlockModel, MeadowBlockRegistry.MEADOW_GRASS_BLOCK);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.SHORT_MEADOW_GRASS, MeadowBlockRegistry.MEDIUM_MEADOW_GRASS);
        BlockStateSmithTypes.TALL_GRASS_MODEL_BLOCK.act(data, MeadowBlockRegistry.TALL_MEADOW_GRASS);
    }
}
