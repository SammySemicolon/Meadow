package com.smellysleepy.meadow.data.block;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.PartiallyCalcifiedLogBlock;
import com.smellysleepy.meadow.common.block.aspen.ThinPartiallyCalcifiedAspenLogBlock;
import com.smellysleepy.meadow.common.block.pearlflower.wilted.WiltedPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.TallPearlFlowerBlock;
import com.smellysleepy.meadow.data.block.smith.CalcificationBlockStateSmithTypes;
import com.smellysleepy.meadow.data.block.smith.FloraBlockStateSmithTypes;
import com.smellysleepy.meadow.data.block.smith.AspenBlockStateSmithTypes;
import com.smellysleepy.meadow.data.block.smith.FungalBlockStateSmithTypes;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.block.*;
import net.minecraft.core.Direction;
import net.minecraft.data.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
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

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::varyingRotationBlock, this::meadowGrassBlockModel, MeadowBlockRegistry.ASPEN_GRASS_BLOCK);
        FloraBlockStateSmithTypes.TINTED_CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.SHORT_ASPEN_GRASS, MeadowBlockRegistry.MEDIUM_ASPEN_GRASS);
        FloraBlockStateSmithTypes.TINTED_TALL_CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.TALL_ASPEN_GRASS);

        setTexturePath("calcification/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_EARTH, MeadowBlockRegistry.CALCIFIED_ROCK, MeadowBlockRegistry.CALCIFIED_BRICKS, MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS);
        BlockStateSmithTypes.DIRECTIONAL_BLOCK.act(data, MeadowBlockRegistry.HEAVY_CHISELED_CALCIFIED_BRICKS);
        DIRECTIONAL_BOTTOM_TOP_BLOCK.act(data, MeadowBlockRegistry.CHISELED_CALCIFIED_BRICKS);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_BRICKS_SLAB, MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_BRICKS_STAIRS, MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS);
        BlockStateSmithTypes.WALL_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_BRICKS_WALL, MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_WALL);

        CalcificationBlockStateSmithTypes.COVERING_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_COVERING);
        CalcificationBlockStateSmithTypes.POINTED_DRIPSTONE_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_DRIPSTONE);
        CalcificationBlockStateSmithTypes.GIANT_POINTED_DRIPSTONE_BLOCK.act(data, MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE);

        setTexturePath("pearlflower/");
        DataHelper.getAll(blocks, b -> b.get() instanceof TallPearlFlowerBlock).forEach(b -> BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, b));
        DataHelper.getAll(blocks, b -> b.get() instanceof WiltedPearlFlowerBlock).forEach(b -> BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, b));
        DataHelper.getAll(blocks, b -> b.get() instanceof PearlFlowerBlock).forEach(b -> FloraBlockStateSmithTypes.SMALL_TALL_CROSS_MODEL_BLOCK.act(data, b));
        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.PEARLLIGHT);

        setTexturePath("wood/aspen/");
        BlockStateSmithTypes.LOG_BLOCK.act(data, MeadowBlockRegistry.ASPEN_LOG, MeadowBlockRegistry.STRIPPED_ASPEN_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, MeadowBlockRegistry.ASPEN_WOOD, MeadowBlockRegistry.STRIPPED_ASPEN_WOOD);
        AspenBlockStateSmithTypes.THIN_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_ASPEN_LOG, MeadowBlockRegistry.THIN_ASPEN_WOOD);
        AspenBlockStateSmithTypes.THIN_STRIPPED_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_STRIPPED_ASPEN_LOG, MeadowBlockRegistry.THIN_STRIPPED_ASPEN_WOOD);

        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PLANKS, MeadowBlockRegistry.HEAVY_ASPEN_PLANKS);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PLANKS_STAIRS, MeadowBlockRegistry.HEAVY_ASPEN_PLANKS_STAIRS);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PLANKS_SLAB, MeadowBlockRegistry.HEAVY_ASPEN_PLANKS_SLAB);

        BlockStateSmithTypes.DOOR_BLOCK.act(data, MeadowBlockRegistry.ASPEN_DOOR, MeadowBlockRegistry.SOLID_ASPEN_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, MeadowBlockRegistry.ASPEN_TRAPDOOR, MeadowBlockRegistry.SOLID_ASPEN_TRAPDOOR);

        BlockStateSmithTypes.BUTTON_BLOCK.act(data, MeadowBlockRegistry.ASPEN_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PRESSURE_PLATE);

        BlockStateSmithTypes.FENCE_BLOCK.act(data, MeadowBlockRegistry.ASPEN_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, MeadowBlockRegistry.ASPEN_FENCE_GATE);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, MeadowBlockRegistry.ASPEN_SIGN, MeadowBlockRegistry.ASPEN_WALL_SIGN);

        BlockStateSmithTypes.AXIS_BLOCK.act(data, MeadowBlockRegistry.ASPEN_PEARLLAMP);

        setTexturePath("wood/calcified/");
        BlockStateSmithTypes.LOG_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_LOG, MeadowBlockRegistry.STRIPPED_CALCIFIED_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_WOOD, MeadowBlockRegistry.STRIPPED_CALCIFIED_WOOD);
        AspenBlockStateSmithTypes.THIN_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_CALCIFIED_LOG, MeadowBlockRegistry.THIN_CALCIFIED_WOOD);
        AspenBlockStateSmithTypes.THIN_STRIPPED_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_STRIPPED_CALCIFIED_LOG, MeadowBlockRegistry.THIN_STRIPPED_CALCIFIED_WOOD);

        BlockStateSmithTypes.FULL_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_PLANKS, MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_PLANKS_STAIRS, MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS_STAIRS);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_PLANKS_SLAB, MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS_SLAB);

        BlockStateSmithTypes.DOOR_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_DOOR, MeadowBlockRegistry.SOLID_CALCIFIED_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_TRAPDOOR, MeadowBlockRegistry.SOLID_CALCIFIED_TRAPDOOR);

        BlockStateSmithTypes.BUTTON_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_PRESSURE_PLATE);

        BlockStateSmithTypes.FENCE_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_FENCE_GATE);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_SIGN, MeadowBlockRegistry.CALCIFIED_WALL_SIGN);

        BlockStateSmithTypes.AXIS_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_PEARLLAMP);

        setTexturePath("wood/");
        AspenBlockStateSmithTypes.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG, MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD);
        AspenBlockStateSmithTypes.PARTIALLY_CALCIFIED_ASPEN_LOG_BLOCK.act(data, MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG, MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_WOOD);

        BlockStateSmithTypes.LEAVES_BLOCK.act(data, MeadowBlockRegistry.ASPEN_LEAVES);
        AspenBlockStateSmithTypes.HANGING_ASPEN_LEAVES.act(data, MeadowBlockRegistry.HANGING_ASPEN_LEAVES);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, MeadowBlockRegistry.ASPEN_SAPLING, MeadowBlockRegistry.SMALL_ASPEN_SAPLING);

        setTexturePath("mushroom/");
        FungalBlockStateSmithTypes.CHANTERELLE_CAP_BLOCK.act(data, MeadowBlockRegistry.CHANTERELLE_CAP_BLOCK);

        FungalBlockStateSmithTypes.CHANTERELLE_STEM_BLOCK.act(data, MeadowBlockRegistry.CHANTERELLE_STEM_BLOCK);
        FungalBlockStateSmithTypes.PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK.act(data, MeadowBlockRegistry.PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK);
        AXIS_BLOCK.act(data, MeadowBlockRegistry.CALCIFIED_CHANTERELLE_STEM_BLOCK);

        FungalBlockStateSmithTypes.THIN_CHANTERELLE_STEM_BLOCK.act(data, MeadowBlockRegistry.THIN_CHANTERELLE_STEM_BLOCK);
        FungalBlockStateSmithTypes.THIN_PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK.act(data, MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK);
        FungalBlockStateSmithTypes.THIN_CALCIFIED_CHANTERELLE_STEM_BLOCK.act(data, MeadowBlockRegistry.THIN_CALCIFIED_CHANTERELLE_STEM_BLOCK);


        setTexturePath("mineral_flora/");
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            BlockStateSmithTypes.GRASS_BLOCK.act(data, bundle.grassBlock);
            BlockStateSmithTypes.TALL_CROSS_MODEL_BLOCK.act(data, bundle.flowerBlock);
            BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, bundle.saplingBlock, bundle.floraBlock);
            BlockStateSmithTypes.LEAVES_BLOCK.act(data, bundle.leavesBlock);
            AspenBlockStateSmithTypes.HANGING_LEAVES.act(data, bundle.hangingLeavesBlock);
        }
        setTexturePath("");
    }

    //TODO: Move this to Lodestone
    public static BlockStateSmith<Block> DIRECTIONAL_BOTTOM_TOP_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation textureName = provider.getBlockTexture(name);
        BlockModelBuilder directionalModel = provider.models().cubeBottomTop(name, textureName, provider.extend(textureName, "_bottom"), provider.extend(textureName, "_top"));
        provider.directionalBlock(block, directionalModel);
    });

    //TODO: Move this to Lodestone
    public static BlockStateSmith<RotatedPillarBlock> AXIS_BLOCK = new BlockStateSmith<>(RotatedPillarBlock.class, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation texture = provider.getBlockTexture(name);
        provider.axisBlock(block, texture, texture);
    });

    public ModelFile meadowGrassBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation top = getBlockTexture(name + "_top");
        ResourceLocation overlay = getBlockTexture(name + "_overlay");
        return models().withExistingParent(name, "block/grass_block").texture("side", side).texture("overlay", overlay).texture("top", top);
    }

    public static void partiallyCalcifiedLogBlockState(LodestoneBlockStateProvider provider, Block block, ModelFile model, ModelFile flippedModel) {
        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    var flipped = state.getValue(ThinPartiallyCalcifiedAspenLogBlock.FLIPPED);
                    ModelFile modelFile = flipped ? flippedModel : model;
                    var axis = state.getValue(PartiallyCalcifiedLogBlock.AXIS);
                    var direction = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);

                    int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
                    int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;

                    return ConfiguredModel.builder()
                            .modelFile(modelFile)
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .build();
                });
    }
}
