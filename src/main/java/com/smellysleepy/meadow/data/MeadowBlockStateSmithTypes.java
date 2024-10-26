package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.wheat.AureateWheatCropBlock;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockStateProvider;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import java.util.function.Function;

public class MeadowBlockStateSmithTypes {

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

    public static BlockStateSmith<AureateWheatCropBlock> LAYERED_CROP_BLOCK = new BlockStateSmith<>(AureateWheatCropBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_uppermost"), (block, provider) -> {
        String name = provider.getBlockName(block);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    var layer = state.getValue(AureateWheatCropBlock.LAYER);
                    String partName = name + "_" + layer;
                    var model = provider.models().withExistingParent(partName, new ResourceLocation("block/crop"))
                            .texture("crop", provider.getBlockTexture(partName));

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .build();
                });
    });

    public static BlockStateSmith<Block> TINTED_CROSS_MODEL_BLOCK = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.CROSS_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.simpleBlock(block, provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_tinted_cross")).texture("cross", provider.getBlockTexture(name)));
    });

    public static BlockStateSmith<Block> TINTED_TALL_CROSS_MODEL_BLOCK = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_top"), (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            final String affix = s.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? "_bottom" : "_top";
            final String affixedName = name + affix;
            return ConfiguredModel.builder().modelFile(provider.models().withExistingParent(affixedName, MeadowMod.meadowModPath("block/templates/template_tinted_cross")).texture("cross", provider.getBlockTexture(affixedName))).build();
        });
    });

    public static BlockStateSmith<PearlFlowerBlock> SMALL_TALL_CROSS_MODEL_BLOCK = new BlockStateSmith<>(PearlFlowerBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_bottom"), (block, provider) -> {
        String name = provider.getBlockName(block);
        final ResourceLocation bottomTexture = provider.getBlockTexture(name + "_bottom");
        final ResourceLocation topTexture = provider.getBlockTexture(name + "_top");
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_tall_one_block_flower"))
                .texture("bottom_cross", bottomTexture)
                .texture("top_cross", topTexture);
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(model).build());
    });

    public static BlockStateSmith<ThinMeadowLogBlock> THIN_LOG_BLOCK = new BlockStateSmith<>(ThinMeadowLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String logName = name.replace("thin_", "");
        boolean isWood = !name.endsWith("_log");
        if (isWood) {
            logName = logName.replace("_wood", "_log");
        }
        final ResourceLocation sideTexture = provider.getBlockTexture(logName);
        final ResourceLocation endTexture = provider.getBlockTexture(logName + (isWood ? "" : "_top"));
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", endTexture)
                .texture("bottom", endTexture);
        provider.getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(model);
            Direction.Axis value = state.getValue(ThinMeadowLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<NaturalThinMeadowLogBlock> NATURAL_THIN_LOG_BLOCK = new BlockStateSmith<>(NaturalThinMeadowLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile modelFile = null;
            switch (s.getValue(NaturalThinMeadowLogBlock.LEAVES)) {
                case NONE -> modelFile = thinLogModels[0];
                case SMALL -> modelFile = thinLogModels[1];
                case MEDIUM -> modelFile = thinLogModels[2];
                case LARGE -> modelFile = thinLogModels[3];
                case TOP -> modelFile = thinLogModels[4];
            }
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = s.getValue(ThinMeadowLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<PartiallyCalcifiedThinMeadowLogBlock> PARTIALLY_CALCIFIED_THIN_LOG_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedThinMeadowLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        ModelFile[] thinLogModels = getThinLogModels(provider, block);
        ModelFile[] flippedThinLogModels = getThinLogModels(provider, block, true);
        provider.getVariantBuilder(block).forAllStates(state -> {
            var flipped = state.getValue(PartiallyCalcifiedThinMeadowLogBlock.FLIPPED);
            var models = flipped ? flippedThinLogModels : thinLogModels;
            ModelFile modelFile = null;
            switch (state.getValue(NaturalThinMeadowLogBlock.LEAVES)) {
                case NONE -> modelFile = models[0];
                case SMALL -> modelFile = models[1];
                case MEDIUM -> modelFile = models[2];
                case LARGE -> modelFile = models[3];
                case TOP -> modelFile = models[4];
            }
            var axis = state.getValue(PartiallyCalcifiedMeadowLogBlock.AXIS);
            var direction = Direction.fromAxisAndDirection(axis, flipped ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);

            final int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
            final int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;

            return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationX(rotationX)
                    .rotationY(rotationY)
                    .build();
        });
    });

    public static BlockStateSmith<PartiallyCalcifiedMeadowLogBlock> PARTIALLY_CALCIFIED_LOG_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedMeadowLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        boolean isWood = !name.endsWith("_log");
        String logName = isWood ? name.replace("_wood", "_log") : name;
        String affix = isWood ? "" : "_top";
        ResourceLocation side = provider.getBlockTexture(logName);
        ResourceLocation sideFlipped = provider.getBlockTexture(logName);// + "_flipped");
        ResourceLocation bottom = provider.getBlockTexture("calcified_aspen_log" + affix);
        ResourceLocation top = provider.getBlockTexture("aspen_log" + affix);
        ModelFile model = provider.models().cubeBottomTop(name, side, bottom, top);
        ModelFile flippedModel = provider.models().cubeBottomTop(name + "_flipped", sideFlipped, bottom, top);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    var flipped = state.getValue(PartiallyCalcifiedThinMeadowLogBlock.FLIPPED);
                    ModelFile modelFile = flipped ? flippedModel : model;
                    var axis = state.getValue(PartiallyCalcifiedMeadowLogBlock.AXIS);
                    var direction = Direction.fromAxisAndDirection(axis, flipped ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);

                    final int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
                    final int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;

                    return ConfiguredModel.builder()
                            .modelFile(modelFile)
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .build();
                });
    });

    public static BlockStateSmith<CalcifiedCoveringBlock> COVERING_BLOCK = new BlockStateSmith<>(CalcifiedCoveringBlock.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_covering"))
                .texture("covering", provider.getBlockTexture(name));
        MultiPartBlockStateBuilder multipartBuilder = provider.getMultipartBuilder(block);
        for (Direction direction : Direction.values()) {
            BooleanProperty property = (BooleanProperty) block.defaultBlockState().getProperties().stream().filter(p -> p.getName().equals(direction.getName())).findFirst().orElseThrow();
            int yRotation = ((int) direction.toYRot() + 180) % 360;
            int xRotation = 0;
            if (direction.getAxis().isVertical()) {
                xRotation = direction.equals(Direction.UP) ? 270 : 90;
            }
            multipartBuilder.part().modelFile(model).rotationY(yRotation).rotationX(xRotation).addModel()
                    .condition(property, true).end();

            //handles the situation where the block is all alone, not connected to anything
            final MultiPartBlockStateBuilder.PartBuilder partBuilder = multipartBuilder.part().modelFile(model).rotationY(yRotation).rotationX(xRotation).addModel();
            for (Direction again : Direction.values()) {
                property = (BooleanProperty) block.defaultBlockState().getProperties().stream().filter(p -> p.getName().equals(again.getName())).findFirst().orElseThrow();
                partBuilder.condition(property, false);
            }
            partBuilder.end();
        }
    });

    public static BlockStateSmith<PointedDripstoneBlock> POINTED_DRIPSTONE_BLOCK = new BlockStateSmith<>(PointedDripstoneBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_tip"), (block, provider) -> {
        String name = provider.getBlockName(block);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    DripstoneThickness thickness = state.getValue(PointedDripstoneBlock.THICKNESS);
                    Direction direction = state.getValue(PointedDripstoneBlock.TIP_DIRECTION);
                    String partName = name + "_" + thickness;
                    ModelFile model = provider.models().withExistingParent(partName, new ResourceLocation("block/pointed_dripstone"))
                            .texture("cross", provider.getBlockTexture(partName));

                    final int rotationX = direction.equals(Direction.UP) ? 0 : 180;
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(rotationX)
                            .build();
                });
    });
    public static BlockStateSmith<PointedDripstoneBlock> GIANT_POINTED_DRIPSTONE_BLOCK = new BlockStateSmith<>(PointedDripstoneBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    DripstoneThickness thickness = state.getValue(PointedDripstoneBlock.THICKNESS);
                    Direction direction = state.getValue(PointedDripstoneBlock.TIP_DIRECTION);
                    String partName = name + "_" + thickness;
                    String leftCross = partName + "_left";
                    String rightCross = partName + "_right";
                    ModelFile model = provider.models().withExistingParent(partName, MeadowMod.meadowModPath("block/templates/template_giant_pointed_dripstone"))
                            .texture("left_cross", provider.getBlockTexture(leftCross))
                            .texture("right_cross", provider.getBlockTexture(rightCross));

                    final int rotationX = direction.equals(Direction.UP) ? 0 : 180;
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationX(rotationX)
                            .build();
                });
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
        final ResourceLocation smallLeavesTexture = provider.getBlockTexture(leaves + "_small_leaves");
        final ResourceLocation mediumLeavesTexture = provider.getBlockTexture(leaves + "_medium_leaves");
        final ResourceLocation largeLeavesTexture = provider.getBlockTexture(leaves + "_large_leaves");

        final ResourceLocation sideTexture = provider.getBlockTexture(logName);// + flippedAffix);
        final ResourceLocation topTexture = provider.getBlockTexture((isPartiallyCalcified ? logName.replace("partially_calcified_", "") : logName) + endAffix);
        final ResourceLocation bottomTexture = provider.getBlockTexture((isPartiallyCalcified ? logName.replace("partially_", "") : logName) + endAffix);
        final ResourceLocation topLeavesTexture = provider.getBlockTexture("aspen_leaves");
        final ResourceLocation hangingLeavesTexture = provider.getBlockTexture("thin_aspen_log_hanging_leaves");
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