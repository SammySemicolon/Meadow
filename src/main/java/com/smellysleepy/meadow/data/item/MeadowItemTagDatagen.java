package com.smellysleepy.meadow.data.item;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.recipe.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
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
    }
}