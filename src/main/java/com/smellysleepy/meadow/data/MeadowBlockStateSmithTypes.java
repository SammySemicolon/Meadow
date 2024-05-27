package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.meadow.*;
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

    public static BlockStateSmith<MeadowWallFungusBlock> WALL_MUSHROOM = new BlockStateSmith<>(MeadowWallFungusBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name + "_" + i, MeadowMod.meadowModPath("block/templates/template_wall_mushroom"))
                        .texture("mushroom", provider.getBlockTexture(name + "_" + i));

        provider.getVariantBuilder(block).forAllStates(s -> {
            final int rotation = ((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360;
            return ConfiguredModel.builder()
                    .modelFile(modelProvider.apply(0)).rotationY(rotation)
                    .nextModel().modelFile(modelProvider.apply(1)).rotationY(rotation)
                    .nextModel().modelFile(modelProvider.apply(2)).rotationY(rotation)
                    .build();
        });
    });

}
