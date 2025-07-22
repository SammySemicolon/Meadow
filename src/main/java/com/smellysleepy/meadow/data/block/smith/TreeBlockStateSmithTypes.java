package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.fungi.ChanterelleMushroomCrownBlock;
import com.smellysleepy.meadow.common.block.fungi.ChanterelleMushroomStemBlock;
import com.smellysleepy.meadow.common.block.wood.NaturalThinAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.PartiallyCalcifiedAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.PartiallyCalcifiedThinAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.ThinAspenLogBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraftforge.client.model.generators.*;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockStateProvider;
import team.lodestar.lodestone.systems.datagen.statesmith.BlockStateSmith;

import java.util.function.Function;

public class TreeBlockStateSmithTypes {

    public static BlockStateSmith<Block> HANGING_LEAVES = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        BlockModelBuilder model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name));
        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        builder.modelFile(model)
                .nextModel().modelFile(model).rotationY(90)
                .nextModel().modelFile(model).rotationY(180)
                .nextModel().modelFile(model).rotationY(270)
                .addModel();
    });

    public static BlockStateSmith<LeavesBlock> HANGING_ASPEN_LEAVES = new BlockStateSmith<>(LeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name + "_" + i, MeadowMod.meadowModPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        for (int i = 0; i < 3; i++) {
            ModelFile model = modelProvider.apply(i);
            builder.modelFile(model)
                    .nextModel().modelFile(model).rotationY(90)
                    .nextModel().modelFile(model).rotationY(180)
                    .nextModel().modelFile(model).rotationY(270);
            if (i != 2) {
                builder = builder.nextModel();
            }

        }
        builder.addModel();
    });

    public static BlockStateSmith<ThinAspenLogBlock> THIN_LOG_BLOCK = new BlockStateSmith<>(ThinAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String logName = name.replace("thin_", "");
        boolean isWood = !name.endsWith("_log");
        if (isWood) {
            logName = logName.replace("_wood", "_log");
        }
        var sideTexture = provider.getBlockTexture(logName);
        var endTexture = provider.getBlockTexture(logName + (isWood ? "" : "_top"));
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", endTexture)
                .texture("bottom", endTexture);
        provider.getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(model);
            Direction.Axis value = state.getValue(ThinAspenLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<NaturalThinAspenLogBlock> NATURAL_THIN_LOG_BLOCK = new BlockStateSmith<>(NaturalThinAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile modelFile = null;
            switch (s.getValue(NaturalThinAspenLogBlock.LEAVES)) {
                case NONE -> modelFile = thinLogModels[0];
                case SMALL -> modelFile = thinLogModels[1];
                case MEDIUM -> modelFile = thinLogModels[2];
                case LARGE -> modelFile = thinLogModels[3];
                case TOP -> modelFile = thinLogModels[4];
            }
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = s.getValue(ThinAspenLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<PartiallyCalcifiedThinAspenLogBlock> PARTIALLY_CALCIFIED_THIN_LOG_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedThinAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        ModelFile[] flippedThinLogModels = getThinLogModels(provider, block, true);
        provider.getVariantBuilder(block).forAllStates(state -> {
            var flipped = state.getValue(PartiallyCalcifiedThinAspenLogBlock.FLIPPED);
            var models = flipped ? flippedThinLogModels : thinLogModels;
            ModelFile modelFile = null;
            switch (state.getValue(NaturalThinAspenLogBlock.LEAVES)) {
                case NONE -> modelFile = models[0];
                case SMALL -> modelFile = models[1];
                case MEDIUM -> modelFile = models[2];
                case LARGE -> modelFile = models[3];
                case TOP -> modelFile = models[4];
            }
            var axis = state.getValue(PartiallyCalcifiedAspenLogBlock.AXIS);
            var direction = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);

            final int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
            final int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;

            return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationX(rotationX)
                    .rotationY(rotationY)
                    .build();
        });
    });

    public static BlockStateSmith<PartiallyCalcifiedAspenLogBlock> PARTIALLY_CALCIFIED_LOG_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        boolean isWood = !name.endsWith("_log");
        String logName = isWood ? name.replace("_wood", "_log") : name;
        String affix = isWood ? "" : "_top";
        ResourceLocation side = provider.getBlockTexture(logName);
        ResourceLocation sideFlipped = provider.getBlockTexture(logName + "_flipped");
        ResourceLocation bottom = provider.getBlockTexture("calcified/calcified_log" + affix);
        ResourceLocation top = provider.getBlockTexture("aspen/aspen_log" + affix);
        ModelFile model = provider.models().cubeBottomTop(name, side, bottom, top);
        ModelFile flippedModel = provider.models().cubeBottomTop(name + "_flipped", sideFlipped, top, bottom);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    var flipped = state.getValue(PartiallyCalcifiedThinAspenLogBlock.FLIPPED);
                    ModelFile modelFile = flipped ? flippedModel : model;
                    var axis = state.getValue(PartiallyCalcifiedAspenLogBlock.AXIS);
                    var direction = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);

                    int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
                    int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;

                    return ConfiguredModel.builder()
                            .modelFile(modelFile)
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .build();
                });
    });

    public static BlockStateSmith<ChanterelleMushroomStemBlock> CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(ChanterelleMushroomStemBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL.apply("_middle"), (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation inside = provider.getBlockTexture("chanterelle_inside");
        provider.getVariantBuilder(block).forAllStates(state -> {
            var layer = state.getValue(ChanterelleMushroomStemBlock.LAYER);
            String layerName = name + "_" + layer.type;
            ResourceLocation side = provider.getBlockTexture(layerName);
            BlockModelBuilder model = provider.models().cubeBottomTop(layerName, side, inside, inside);
            return ConfiguredModel.builder().modelFile(model).build();
        });
    });

    public static BlockStateSmith<ChanterelleMushroomCrownBlock> CHANTERELLE_CROWN_BLOCK = new BlockStateSmith<>(ChanterelleMushroomCrownBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation inside = provider.getBlockTexture("chanterelle_inside");
        ResourceLocation outside = provider.getBlockTexture("chanterelle_outside");
        provider.simpleBlock(block, provider.models().cubeAll(name, outside));
    });

    public static ModelFile[] getThinLogModels(LodestoneBlockStateProvider provider, Block block) {
        return getThinLogModels(provider, block, false);
    }
    public static ModelFile[] getThinLogModels(LodestoneBlockStateProvider provider, Block block, boolean flipped) {
        var name = provider.getBlockName(block);
        var leaves = name;
        var logName = name.replace("thin_", "");
        boolean isWood = !name.endsWith("_log");
        if (isWood) {
            logName = logName.replace("_wood", "_log");
            leaves = "thin_" + logName;
        }
        var flippedAffix = flipped ? "_flipped" : "";
        boolean isPartiallyCalcified = logName.contains("partially_");
        var endAffix = isWood ? "" : "_top";
        var smallLeavesTexture = provider.getBlockTexture(leaves + "_small_leaves");
        var mediumLeavesTexture = provider.getBlockTexture(leaves + "_medium_leaves");
        var largeLeavesTexture = provider.getBlockTexture(leaves + "_large_leaves");
        var calcifiedLogPath = "calcified/" + logName.replace("partially_", "").replace("aspen_", "");
        var aspenLogPath = "aspen/" + logName.replace("partially_calcified_", "");

        var sideTexture = provider.getBlockTexture(logName + flippedAffix);
        var topTexture = provider.getBlockTexture((isPartiallyCalcified ? aspenLogPath : logName) + endAffix);
        var bottomTexture = provider.getBlockTexture((isPartiallyCalcified ? calcifiedLogPath : logName) + endAffix);
        var topLeavesTexture = provider.getStaticBlockTexture("wood/aspen_leaves");
        var hangingLeavesTexture = provider.getStaticBlockTexture("wood/hanging_aspen_leaves_0");
        if (flipped) {
            var cache = bottomTexture;
            bottomTexture = topTexture;
            topTexture = cache;
        }
        ModelFile noLeaves = provider.models().withExistingParent(name + flippedAffix, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture);

        ModelFile smallLeaves = provider.models().withExistingParent(name + "_small_leaves" + flippedAffix, MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", smallLeavesTexture);

        ModelFile mediumLeaves = provider.models().withExistingParent(name + "_medium_leaves" + flippedAffix, MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", mediumLeavesTexture);

        ModelFile largeLeaves = provider.models().withExistingParent(name + "_large_leaves" + flippedAffix, MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture);

        ModelFile topLeaves = provider.models().withExistingParent(name + "_top_leaves" + flippedAffix, MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves_top"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture)
                .texture("leaves_block", topLeavesTexture)
                .texture("hanging_leaves", hangingLeavesTexture);
        return new ModelFile[]{noLeaves, smallLeaves, mediumLeaves, largeLeaves, topLeaves};
    }
}