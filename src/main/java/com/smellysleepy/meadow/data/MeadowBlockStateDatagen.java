package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
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
        BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.TALL_MEADOW_GRASS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, MeadowBlockRegistry.MEADOW_LOG, MeadowBlockRegistry.FULLY_CALCIFIED_MEADOW_LOG);

        MeadowBlockStateSmithTypes.THIN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_MEADOW_LOG);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::directionalBlock, this::calcifiedMeadowLogModel, MeadowBlockRegistry.PARTIALLY_CALCIFIED_MEADOW_LOG);


        BlockStateSmithTypes.LEAVES_BLOCK.act(data, MeadowBlockRegistry.MEADOW_LEAVES);
        MeadowBlockStateSmithTypes.FLOWERING_LEAVES.act(data, MeadowBlockRegistry.FLOWERING_MEADOW_LEAVES);
        MeadowBlockStateSmithTypes.HANGING_LEAVES.act(data, MeadowBlockRegistry.HANGING_MEADOW_LEAVES);
        MeadowBlockStateSmithTypes.TALL_HANGING_LEAVES.act(data, MeadowBlockRegistry.TALL_HANGING_MEADOW_LEAVES);
        MeadowBlockStateSmithTypes.WALL_MUSHROOM.act(data, MeadowBlockRegistry.MEADOW_MUSHROOM);

        setTexturePath("strange_flora/mineral/");
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.LAZURITE_ROSE, MeadowBlockRegistry.CRIMSON_SUN);
        BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.BERYL_ALSTRO, MeadowBlockRegistry.TRANQUIL_LILY);
        setTexturePath("");

    }

    public ModelFile wallFungusModel(Block block) {
        String name = getBlockName(block);
        return models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_wall_mushroom"))
                .texture("mushroom", getBlockTexture(name));
    }

    public ModelFile calcifiedMeadowLogModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = getBlockTexture("fully_calcified_meadow_log_top");
        ResourceLocation top = getBlockTexture("meadow_log_top");
        return models().cubeBottomTop(name, side, bottom, top);
    }
}
