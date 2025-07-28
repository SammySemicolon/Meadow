package com.smellysleepy.meadow.data.item;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.recipe.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.concurrent.*;

public class MeadowItemTagDatagen extends LodestoneItemTagsProvider {

    public MeadowItemTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, pLookupProvider, pBlockTags, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Meadow Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        MeadowWoodSetDatagen.addTags(this);

        copy(net.minecraft.tags.BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(net.minecraft.tags.BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(net.minecraft.tags.BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(net.minecraft.tags.BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(net.minecraft.tags.BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(net.minecraft.tags.BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(net.minecraft.tags.BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(net.minecraft.tags.BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(net.minecraft.tags.BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(net.minecraft.tags.BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(net.minecraft.tags.BlockTags.FENCES, ItemTags.FENCES);
        copy(net.minecraft.tags.BlockTags.LEAVES, ItemTags.LEAVES);
        copy(net.minecraft.tags.BlockTags.PLANKS, ItemTags.PLANKS);
        copy(net.minecraft.tags.BlockTags.STAIRS, ItemTags.STAIRS);
        copy(net.minecraft.tags.BlockTags.SLABS, ItemTags.SLABS);
        copy(net.minecraft.tags.BlockTags.DOORS, ItemTags.DOORS);
        copy(net.minecraft.tags.BlockTags.WALLS, ItemTags.WALLS);
        copy(net.minecraft.tags.BlockTags.LOGS, ItemTags.LOGS);

        tag(Tags.Items.LEATHERS).add(MeadowItemRegistry.CLUMP_OF_FUR.get());
    }
}