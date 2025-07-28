package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.statesmith.BlockStateSmith;

public class CalcificationBlockStateSmithTypes {

    public static BlockStateSmith<CalcifiedCoveringBlock> COVERING_BLOCK = new BlockStateSmith<>(CalcifiedCoveringBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
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

    public static BlockStateSmith<PointedDripstoneBlock> POINTED_DRIPSTONE_BLOCK = new BlockStateSmith<>(PointedDripstoneBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_ITEM.apply("_tip"), (block, provider) -> {
        String name = provider.getBlockName(block);

        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    DripstoneThickness thickness = state.getValue(PointedDripstoneBlock.THICKNESS);
                    Direction direction = state.getValue(PointedDripstoneBlock.TIP_DIRECTION);
                    String partName = name + "_" + thickness;
                    ModelFile model = provider.models().withExistingParent(partName, ResourceLocation.parse("block/pointed_dripstone"))
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
}