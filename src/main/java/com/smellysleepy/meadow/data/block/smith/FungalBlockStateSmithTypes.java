package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.PartiallyCalcifiedLogBlock;
import com.smellysleepy.meadow.common.block.ThinLogBlock;
import com.smellysleepy.meadow.common.block.fungi.*;
import com.smellysleepy.meadow.common.block.fungi.ChanterelleMushroomStemBlock.ChanterelleLayer;
import com.smellysleepy.meadow.common.block.fungi.ThinNaturalChanterelleStemBlock;
import com.smellysleepy.meadow.common.block.fungi.ThinPartiallyCalcifiedChanterelleStemBlock;
import com.smellysleepy.meadow.data.block.MeadowBlockStateDatagen;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockStateProvider;
import team.lodestar.lodestone.systems.datagen.statesmith.BlockStateSmith;

import javax.annotation.Nullable;

public class FungalBlockStateSmithTypes {

    public static BlockStateSmith<ChanterelleMushroomCapBlock> CHANTERELLE_CAP_BLOCK = new BlockStateSmith<>(ChanterelleMushroomCapBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
//        ResourceLocation inside = provider.getBlockTexture("chanterelle_inside");
        ResourceLocation crown = provider.getBlockTexture(name);
        provider.simpleBlock(block, provider.models().cubeAll(name, crown));
    });

    public static BlockStateSmith<ChanterelleMushroomStemBlock> CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(ChanterelleMushroomStemBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL_ITEM.apply("_middle"), (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation inside = provider.getBlockTexture("chanterelle_inside");
        provider.getVariantBuilder(block).forAllStates(state -> {
            var layer = state.getValue(ChanterelleMushroomStemBlock.LAYER);
            String layerName = name + "_" + layer.type;
            ResourceLocation side = provider.getBlockTexture(layerName);
            BlockModelBuilder model = provider.models().cubeBottomTop(layerName, side, inside, inside);
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

    public static BlockStateSmith<ThinNaturalChanterelleStemBlock> THIN_CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(ThinNaturalChanterelleStemBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL_ITEM.apply("_middle"), (block, provider) -> {
        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile[] thinStemModels = getThinStemModels(provider, block, s.getOptionalValue(ChanterelleMushroomStemBlock.LAYER).orElse(null));
            ModelFile modelFile = s.getValue(ThinNaturalChanterelleStemBlock.HAS_CAP) ? thinStemModels[1] : thinStemModels[0];
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = s.getValue(ThinLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<ThinPartiallyCalcifiedChanterelleStemBlock> THIN_PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(ThinPartiallyCalcifiedChanterelleStemBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile[] thinStemModels = getThinStemModels(provider, block, s.getValue(ThinPartiallyCalcifiedChanterelleStemBlock.FLIPPED));
            ModelFile modelFile = s.getValue(ThinNaturalChanterelleStemBlock.HAS_CAP) ? thinStemModels[1] : thinStemModels[0];
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = s.getValue(ThinLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<PartiallyCalcifiedLogBlock> PARTIALLY_CALCIFIED_CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(PartiallyCalcifiedLogBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation side = provider.getBlockTexture(name);
        ResourceLocation sideFlipped = provider.getBlockTexture(name + "_flipped");
        ResourceLocation bottom = provider.getBlockTexture("calcified_chanterelle_stem_block");
        ResourceLocation top = provider.getBlockTexture("chanterelle_inside");
        ModelFile model = provider.models().cubeBottomTop(name, side, bottom, top);
        ModelFile flippedModel = provider.models().cubeBottomTop(name + "_flipped", sideFlipped, top, bottom);
        MeadowBlockStateDatagen.partiallyCalcifiedLogBlockState(provider, block, model, flippedModel);
    });



    public static BlockStateSmith<ThinNaturalChanterelleStemBlock> THIN_CALCIFIED_CHANTERELLE_STEM_BLOCK = new BlockStateSmith<>(ThinNaturalChanterelleStemBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        provider.getVariantBuilder(block).forAllStates(s -> {
            ModelFile[] thinStemModels = getThinStemModels(provider, block, null);
            ModelFile modelFile = s.getValue(ThinNaturalChanterelleStemBlock.HAS_CAP) ? thinStemModels[1] : thinStemModels[0];
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(modelFile);
            Direction.Axis value = s.getValue(ThinLogBlock.AXIS);
            if (value.equals(Direction.Axis.X) || value.equals(Direction.Axis.Z)) {
                builder.rotationX(90);
                if (value.equals(Direction.Axis.X)) {
                    builder.rotationY(90);
                }
            }
            return builder.build();
        });
    });

    public static ModelFile[] getThinStemModels(LodestoneBlockStateProvider provider, Block block, @Nullable ChanterelleLayer layer) {
        return getThinStemModels(provider, block, layer, false);
    }

    public static ModelFile[] getThinStemModels(LodestoneBlockStateProvider provider, Block block, boolean flipped) {
        return getThinStemModels(provider, block, null, flipped);
    }

    public static ModelFile[] getThinStemModels(LodestoneBlockStateProvider provider, Block block, @Nullable ChanterelleLayer layer, boolean flipped) {
        var name = provider.getBlockName(block);
        var side = name.replace("thin_", "");
        var affix = layer != null ? "_" + layer.type : flipped ? "_flipped" : "";
        boolean isPartiallyCalcified = name.contains("partially_");
        var calcifiedStem = "calcified_chanterelle_stem_block";
        var stemInside = side.equals(calcifiedStem) ? calcifiedStem : "chanterelle_inside";
        var sideTexture = provider.getBlockTexture(side + affix);
        var topTexture = provider.getBlockTexture((stemInside));
        var bottomTexture = provider.getBlockTexture((isPartiallyCalcified ? calcifiedStem : stemInside));
        if (flipped) {
            var cache = bottomTexture;
            bottomTexture = topTexture;
            topTexture = cache;
        }

        ModelFile noCap = provider.models().withExistingParent(name + affix, MeadowMod.meadowModPath("block/templates/template_thin_log"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture);

        ModelFile cap = provider.models().withExistingParent(name + "_cap" + affix, MeadowMod.meadowModPath("block/templates/template_thin_stem_with_cap"))
                .texture("side", sideTexture)
                .texture("top", topTexture)
                .texture("bottom", bottomTexture);
        return new ModelFile[]{noCap, cap};
    }
}