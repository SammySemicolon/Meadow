package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

public class FloraBlockStateSmithTypes {

    public static BlockStateSmith<Block> TINTED_CROSS_MODEL_BLOCK = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.CROSS_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.simpleBlock(block, provider.models().withExistingParent(name, MeadowMod.meadowPath("block/templates/template_tinted_cross")).texture("cross", provider.getBlockTexture(name)));
    });

    public static BlockStateSmith<Block> TINTED_TALL_CROSS_MODEL_BLOCK = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_ITEM.apply("_top"), (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            final String affix = s.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? "_bottom" : "_top";
            final String affixedName = name + affix;
            return ConfiguredModel.builder().modelFile(provider.models().withExistingParent(affixedName, MeadowMod.meadowPath("block/templates/template_tinted_cross")).texture("cross", provider.getBlockTexture(affixedName))).build();
        });
    });

    public static BlockStateSmith<PearlFlowerBlock> SMALL_TALL_CROSS_MODEL_BLOCK = new BlockStateSmith<>(PearlFlowerBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_ITEM.apply("_bottom"), (block, provider) -> {
        String name = provider.getBlockName(block);
        var bottomTexture = provider.getBlockTexture(name + "_bottom");
        var topTexture = provider.getBlockTexture(name + "_top");
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowPath("block/templates/template_tall_one_block_flower"))
                .texture("bottom_cross", bottomTexture)
                .texture("top_cross", topTexture);
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(model).build());
    });
}