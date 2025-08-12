package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.aspen.ThinNaturalAspenLogBlock;
import com.smellysleepy.meadow.common.block.PartiallyCalcifiedLogBlock;
import com.smellysleepy.meadow.common.block.aspen.ThinPartiallyCalcifiedAspenLogBlock;
import com.smellysleepy.meadow.common.block.ThinLogBlock;
import com.smellysleepy.meadow.data.block.MeadowBlockStateDatagen;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockStateProvider;
import team.lodestar.lodestone.systems.datagen.statesmith.BlockStateSmith;

import java.util.function.Function;

public class AspenBlockStateSmithTypes {

    public static BlockStateSmith<Block> HANGING_LEAVES = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        BlockModelBuilder model = provider.models().withExistingParent(name, MeadowMod.meadowPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name));
        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        builder.modelFile(model)
                .nextModel().modelFile(model).rotationY(90)
                .nextModel().modelFile(model).rotationY(180)
                .nextModel().modelFile(model).rotationY(270)
                .addModel();
    });

    public static BlockStateSmith<LeavesBlock> HANGING_ASPEN_LEAVES = new BlockStateSmith<>(LeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_ITEM.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name + "_" + i, MeadowMod.meadowPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name + "_" + i));

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

    public static BlockStateSmith<ThinLogBlock> THIN_STRIPPED_ASPEN_LOG_BLOCK = new BlockStateSmith<>(ThinLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String logName = name.replace("thin_", "");
        boolean isWood = !name.endsWith("_log");
        if (isWood) {
            logName = logName.replace("_wood", "_log");
        }
        var sideTexture = provider.getBlockTexture(logName);
        var endTexture = provider.getBlockTexture(logName + (isWood ? "" : "_top"));
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", endTexture)
                .texture("bottom", endTexture);
        provider.getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(model);
            Direction.Axis value = state.getValue(ThinLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<ThinNaturalAspenLogBlock> THIN_ASPEN_LOG_BLOCK = new BlockStateSmith<>(ThinNaturalAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        provider.getVariantBuilder(block).forAllStates(state -> {
            ModelFile modelFile = null;
            switch (state.getValue(ThinNaturalAspenLogBlock.LEAVES)) {
                case NONE -> modelFile = thinLogModels[0];
                case SMALL -> modelFile = thinLogModels[1];
                case MEDIUM -> modelFile = thinLogModels[2];
                case LARGE -> modelFile = thinLogModels[3];
                case TOP -> modelFile = thinLogModels[4];
            }
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = state.getValue(ThinLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<ThinPartiallyCalcifiedAspenLogBlock> THIN_PARTIALLY_CALCIFIED_ASPEN_LOG_BLOCK = new BlockStateSmith<>(ThinPartiallyCalcifiedAspenLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        ModelFile[] flippedThinLogModels = getThinLogModels(provider, block, true);
        provider.getVariantBuilder(block).forAllStates(state -> {
            var flipped = state.getValue(ThinPartiallyCalcifiedAspenLogBlock.FLIPPED);
            var models = flipped ? flippedThinLogModels : thinLogModels;
            ModelFile modelFile = null;
            switch (state.getValue(ThinNaturalAspenLogBlock.LEAVES)) {
                case NONE -> modelFile = models[0];
                case SMALL -> modelFile = models[1];
                case MEDIUM -> modelFile = models[2];
                case LARGE -> modelFile = models[3];
                case TOP -> modelFile = models[4];
            }
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
    });

    public static BlockStateSmith<PartiallyCalcifiedLogBlock> PARTIALLY_CALCIFIED_ASPEN_LOG_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
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
        MeadowBlockStateDatagen.partiallyCalcifiedLogBlockState(provider, block, model, flippedModel);
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
        var aspenLog = "aspen/" + logName.replace("partially_calcified_", "");
        var calcifiedLog = "calcified/" + logName.replace("partially_", "").replace("aspen_", "");

        var sideTexture = provider.getBlockTexture(logName + flippedAffix);
        var topTexture = provider.getBlockTexture((isPartiallyCalcified ? aspenLog : logName) + endAffix);
        var bottomTexture = provider.getBlockTexture((isPartiallyCalcified ? calcifiedLog : logName) + endAffix);
        var topLeavesTexture = provider.getStaticBlockTexture("wood/aspen_leaves");
        var hangingLeavesTexture = provider.getStaticBlockTexture("wood/hanging_aspen_leaves_0");
        if (flipped) {
            var cache = bottomTexture;
            bottomTexture = topTexture;
            topTexture = cache;
        }
        ModelFile noLeaves = provider.models().withExistingParent(name + flippedAffix, MeadowMod.meadowPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture);

        ModelFile smallLeaves = provider.models().withExistingParent(name + "_small_leaves" + flippedAffix, MeadowMod.meadowPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", smallLeavesTexture);

        ModelFile mediumLeaves = provider.models().withExistingParent(name + "_medium_leaves" + flippedAffix, MeadowMod.meadowPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", mediumLeavesTexture);

        ModelFile largeLeaves = provider.models().withExistingParent(name + "_large_leaves" + flippedAffix, MeadowMod.meadowPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture);

        ModelFile topLeaves = provider.models().withExistingParent(name + "_top_leaves" + flippedAffix, MeadowMod.meadowPath("block/templates/template_thin_log_with_leaves_top"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture)
                .texture("leaves_block", topLeavesTexture)
                .texture("hanging_leaves", hangingLeavesTexture);
        return new ModelFile[]{noLeaves, smallLeaves, mediumLeaves, largeLeaves, topLeaves};
    }
}