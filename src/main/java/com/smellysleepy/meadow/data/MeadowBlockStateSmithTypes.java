package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import com.smellysleepy.meadow.common.block.flora.pearl_flower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.meadow.flora.MeadowGrassBlock;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.datagen.*;
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
                provider.models().withExistingParent(name+"_"+i, MeadowMod.meadowModPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        for (int i = 0; i < 4; i++) {
            ModelFile model = modelProvider.apply(i);
            builder.modelFile(model)
                    .nextModel().modelFile(model).rotationY(90)
                    .nextModel().modelFile(model).rotationY(180)
                    .nextModel().modelFile(model).rotationY(270);
            if (i != 3) {
                builder = builder.nextModel();
            }

        }
        builder.addModel();
    });
//    public static BlockStateSmith<LeavesBlock> ASPEN_LEAVES = new BlockStateSmith<>(LeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL.apply("_0"), (block, provider) -> {
//        String name = provider.getBlockName(block);
//        Function<Integer, ModelFile> modelProvider = (i) ->
//                provider.models().withExistingParent(name+"_"+i, new ResourceLocation("block/leaves")).texture("all", provider.getBlockTexture(name + "_" + i));
//        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(modelProvider.apply(s.getValue(MeadowLeavesBlock.COLOR))).build());
//    });

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
        boolean isPartiallyCalcified = logName.contains("partially_");
        final ResourceLocation sideTexture = provider.getBlockTexture(logName);
        final ResourceLocation topTexture = provider.getBlockTexture((isPartiallyCalcified ? logName.replace("partially_calcified_", "") : logName) + "_top");
        final ResourceLocation bottomTexture = provider.getBlockTexture((isPartiallyCalcified ? logName.replace("partially_", "") : logName) + "_top");
        final ResourceLocation smallLeavesTexture = provider.getBlockTexture(name + "_small_leaves");
        final ResourceLocation mediumLeavesTexture = provider.getBlockTexture(name + "_medium_leaves");
        final ResourceLocation largeLeavesTexture = provider.getBlockTexture(name + "_large_leaves");
        final ResourceLocation topLeavesTexture = provider.getBlockTexture("aspen_leaves");
        final ResourceLocation hangingLeavesTexture = provider.getBlockTexture("thin_aspen_log_hanging_leaves");
        ModelFile noLeaves = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture);

        ModelFile smallLeaves = provider.models().withExistingParent(name+"_small_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", smallLeavesTexture);

        ModelFile mediumLeaves = provider.models().withExistingParent(name+"_medium_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", mediumLeavesTexture);

        ModelFile largeLeaves = provider.models().withExistingParent(name+"_large_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture);

        ModelFile topLeaves = provider.models().withExistingParent(name+"_top_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves_top"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture)
                .texture("leaves", largeLeavesTexture)
                .texture("leaves_block", topLeavesTexture)
                .texture("hanging_leaves", hangingLeavesTexture);

        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile modelFile = null;
            switch (s.getValue(ThinMeadowLogBlock.LEAVES)) {
                case NONE -> modelFile = noLeaves;
                case SMALL -> modelFile = smallLeaves;
                case MEDIUM -> modelFile = mediumLeaves;
                case LARGE -> modelFile = largeLeaves;
                case TOP -> modelFile = topLeaves;
            }

            return ConfiguredModel.builder().modelFile(modelFile).build();
        });

    });

    public static BlockStateSmith<LodestoneDirectionalBlock> DIRECTIONAL_LOG_BLOCK = new BlockStateSmith<>(LodestoneDirectionalBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation side = provider.getBlockTexture(name);
        ResourceLocation sideFlipped = provider.getBlockTexture(name + "_flipped");
        ResourceLocation bottom = provider.getBlockTexture("calcified_aspen_log_top");
        ResourceLocation top = provider.getBlockTexture("aspen_log_top");
        ModelFile model = provider.models().cubeBottomTop(name, side, bottom, top);
        ModelFile flippedModel = provider.models().cubeBottomTop(name + "_flipped", sideFlipped, bottom, top);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction direction = state.getValue(BlockStateProperties.FACING);
                    Direction.AxisDirection axisForFlipped = Direction.AxisDirection.NEGATIVE;
                    if (direction.getAxis().equals(Direction.Axis.Z)) {
                        axisForFlipped = axisForFlipped.opposite();
                    }
                    ModelFile logModel = direction.getAxisDirection().equals(axisForFlipped) ? flippedModel : model;
                    final int rotationX = direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0;
                    final int rotationY = direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360;
                    return ConfiguredModel.builder()
                            .modelFile(logModel)
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
            int yRotation = ((int) direction.toYRot()+180) % 360;
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
}
