package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.block.flora.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.flora.pearlflower.PearllampBlock;
import com.smellysleepy.meadow.common.block.flora.pearlflower.PearllightBlock;
import com.smellysleepy.meadow.common.block.flora.pearlflower.TallPearlFlowerBlock;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.data.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import team.lodestar.lodestone.helpers.DataHelper;
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
        BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.TALL_MEADOW_GRASS);

        setTexturePath("calcification/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_EARTH, MeadowBlockRegistry.CALCIFIED_ROCK);
        MeadowBlockStateSmithTypes.COVERING_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_COVERING);
        MeadowBlockStateSmithTypes.POINTED_DRIPSTONE_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_DRIPSTONE);
        MeadowBlockStateSmithTypes.GIANT_POINTED_DRIPSTONE_BLOCK.act(data, MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE);

        setTexturePath("pearlflower/");
        DataHelper.getAll(blocks, b -> b.get() instanceof TallPearlFlowerBlock).forEach(b -> BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, b));
        DataHelper.getAll(blocks, b -> b.get() instanceof PearlFlowerBlock).forEach(b -> MeadowBlockStateSmithTypes.SMALL_TALL_CROSS_MODEL_BLOCK.act(data, b));
        setTexturePath("pearlflower/light/");
        DataHelper.getAll(blocks, b -> b.get() instanceof PearllightBlock).forEach(b -> BlockStateSmithTypes.FULL_BLOCK.act(data, b));
        DataHelper.getAll(blocks, b -> b.get() instanceof PearllampBlock).forEach(b -> BlockStateSmithTypes.AXIS_BLOCK.act(data, b));


        setTexturePath("aspen_wood/");
        BlockStateSmithTypes.LOG_BLOCK.act(data, MeadowBlockRegistry.ASPEN_LOG, MeadowBlockRegistry.CALCIFIED_ASPEN_LOG);
        MeadowBlockStateSmithTypes.THIN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_ASPEN_LOG, MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG, MeadowBlockRegistry.THIN_CALCIFIED_ASPEN_LOG);
        MeadowBlockStateSmithTypes.DIRECTIONAL_LOG_BLOCK.act(data, MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG);

        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PLANKS, MeadowBlockRegistry.ASPEN_BOARDS, MeadowBlockRegistry.HEAVY_ASPEN_BOARDS);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, MeadowBlockRegistry.ASPEN_DOOR, MeadowBlockRegistry.SOLID_ASPEN_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, MeadowBlockRegistry.ASPEN_TRAPDOOR, MeadowBlockRegistry.SOLID_ASPEN_TRAPDOOR);

        BlockStateSmithTypes.LEAVES_BLOCK.act(data, MeadowBlockRegistry.ASPEN_LEAVES);
        MeadowBlockStateSmithTypes.HANGING_ASPEN_LEAVES.act(data, MeadowBlockRegistry.HANGING_ASPEN_LEAVES);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.ASPEN_SAPLING, MeadowBlockRegistry.SMALL_ASPEN_SAPLING);

        setTexturePath("mineral_flora/");
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA.values()) {
            BlockStateSmithTypes.GRASS_BLOCK.act(data, bundle.grassBlock);
            BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, bundle.flowerBlock);
            BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, bundle.saplingBlock, bundle.floraBlock);
            BlockStateSmithTypes.LEAVES_BLOCK.act(data, bundle.leavesBlock);
            MeadowBlockStateSmithTypes.HANGING_LEAVES.act(data, bundle.hangingLeavesBlock);
        }
        setTexturePath("");
    }
}
