package com.smellysleepy.meadow.data.item;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.recipe.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.concurrent.*;

public class MeadowItemTagDatagen extends LodestoneItemTagsProvider {

    public MeadowItemTagDatagen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Meadow Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        MeadowWoodSetDatagen.addTags(this);

        copy(MeadowBlockTagRegistry.STRIPPED_LOGS, MeadowItemTagRegistry.STRIPPED_LOGS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.LOGS, ItemTags.LOGS);
    }
}