package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.meadow.flora.*;
import com.smellysleepy.meadow.common.block.meadow.leaves.*;
import com.smellysleepy.meadow.common.block.meadow.wood.*;
import com.smellysleepy.meadow.common.block.strange_flora.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import java.util.function.*;

public class MeadowBlockStateSmithTypes {

    public static BlockStateSmith<MeadowHangingLeavesBlock> HANGING_LEAVES = new BlockStateSmith<>(MeadowHangingLeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name+"_"+i, MeadowMod.meadowModPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();

        for (int i = 0; i < 4; i++) {
            final ModelFile model = modelProvider.apply(i);
            builder = builder.modelFile(model)
                    .nextModel().modelFile(model).rotationY(90)
                    .nextModel().modelFile(model).rotationY(180)
                    .nextModel().modelFile(model).rotationY(270);
            if (i != 3) {
                builder = builder.nextModel();
            }
        }
        builder.addModel();
    });

    public static BlockStateSmith<LeavesBlock> FLOWERING_LEAVES = new BlockStateSmith<>(LeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name+"_"+i, new ResourceLocation("block/leaves")).texture("all", provider.getBlockTexture(name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();

        for (int i = 0; i < 3; i++) {
            final ModelFile model = modelProvider.apply(i);
            builder = builder.modelFile(model);
            if (i != 2) {
                builder = builder.nextModel();
            }
        }
        builder.addModel();
    });


    public static BlockStateSmith<MeadowTallHangingLeavesBlock> TALL_HANGING_LEAVES = new BlockStateSmith<>(MeadowTallHangingLeavesBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_bottom"), (block, provider) -> {
        String name = provider.getBlockName(block);
        final ResourceLocation bottomTexture = provider.getBlockTexture(name + "_bottom");
        final ResourceLocation topTexture = provider.getBlockTexture(name + "_top");
        ModelFile model = provider.models().withExistingParent(name + "_bottom", MeadowMod.meadowModPath("block/templates/template_hanging_leaves"))
                .texture("hanging_leaves", bottomTexture);
        ModelFile topModel = provider.models().withExistingParent(name + "_top", MeadowMod.meadowModPath("block/templates/template_hanging_leaves"))
                .texture("hanging_leaves", topTexture);

        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(MeadowTallHangingLeavesBlock.HALF).equals(DoubleBlockHalf.UPPER) ? topModel : model).build());

    });

    public static BlockStateSmith<MeadowWallFungusBlock> WALL_MUSHROOM = new BlockStateSmith<>(MeadowWallFungusBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            int rotation = ((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360;
            int age = s.getValue(MeadowWallFungusBlock.AGE);
            final BlockModelBuilder model = provider.models().withExistingParent(name + "_" + age, MeadowMod.meadowModPath("block/templates/template_wall_mushroom"))
                    .texture("mushroom", provider.getBlockTexture(name + "_" + age));
            return ConfiguredModel.builder().modelFile(model).rotationY(rotation).build();
        });
    });

    public static BlockStateSmith<ThinMeadowLogBlock> THIN_LOG_BLOCK = new BlockStateSmith<>(ThinMeadowLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String logName = name.replace("thin_", "");
        final ResourceLocation sideTexture = provider.getBlockTexture(logName);
        final ResourceLocation topTexture = provider.getBlockTexture(logName + "_top");
        final ResourceLocation smallLeavesTexture = provider.getBlockTexture(name + "_small_leaves");
        final ResourceLocation mediumLeavesTexture = provider.getBlockTexture(name + "_medium_leaves");
        final ResourceLocation largeLeavesTexture = provider.getBlockTexture(name + "_large_leaves");
        final ResourceLocation topLeavesTexture = provider.getBlockTexture("meadow_leaves");
        final ResourceLocation hangingLeavesTexture = provider.getBlockTexture("hanging_meadow_leaves_3");
        ModelFile noLeaves = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", topTexture);

        ModelFile smallLeaves = provider.models().withExistingParent(name+"_small_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("leaves", smallLeavesTexture);

        ModelFile mediumLeaves = provider.models().withExistingParent(name+"_medium_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("leaves", mediumLeavesTexture);

        ModelFile largeLeaves = provider.models().withExistingParent(name+"_large_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("leaves", largeLeavesTexture);

        ModelFile topLeaves = provider.models().withExistingParent(name+"_top_leaves", MeadowMod.meadowModPath("block/templates/template_thin_log_with_leaves_top"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
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

    public static BlockStateSmith<CalcifiedCoveringBlock> CALCIFIED_COVERING_BLOCK = new BlockStateSmith<>(CalcifiedCoveringBlock.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            int rotation = ((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360;
            boolean corner = s.getValue(CalcifiedCoveringBlock.CORNER);
            String modelName = name + (corner ? "_corner" : "");

            final BlockModelBuilder model = provider.models().withExistingParent(modelName, MeadowMod.meadowModPath("block/templates/template_covering"))
                    .texture("covering", provider.getBlockTexture(modelName));
            return ConfiguredModel.builder().modelFile(model).rotationY(rotation).build();
        });
    });

    public static BlockStateSmith<AbstractStrangePlant> STRANGE_PLANT_BLOCK = new BlockStateSmith<>(AbstractStrangePlant.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            boolean stone = s.getValue(AbstractStrangePlant.STONE);
            String modelName = name + (stone ? "_stone" : "");

            final BlockModelBuilder model = provider.models().cross(modelName, provider.getBlockTexture(modelName))
                    .texture("cross", provider.getBlockTexture(modelName));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    });
}
