package com.smellysleepy.meadow.data.block.smith;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.calcification.CalcifiedCoveringBlock;
import com.smellysleepy.meadow.common.block.fungi.ChanterelleMushroomStemBlock;
import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.wood.NaturalThinAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.PartiallyCalcifiedAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.PartiallyCalcifiedThinAspenLogBlock;
import com.smellysleepy.meadow.common.block.wood.ThinAspenLogBlock;
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

public class FloraBlockStateSmithTypes {

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
        var bottomTexture = provider.getBlockTexture(name + "_bottom");
        var topTexture = provider.getBlockTexture(name + "_top");
        ModelFile model = provider.models().withExistingParent(name, MeadowMod.meadowModPath("block/templates/template_tall_one_block_flower"))
                .texture("bottom_cross", bottomTexture)
                .texture("top_cross", topTexture);
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(model).build());
    });
}