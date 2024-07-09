package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;

public class MeadowBlockTagDatagen extends LodestoneBlockTagsProvider {

    public MeadowBlockTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MeadowMod.MEADOW, existingFileHelper);
    }


    @Override
    public String getName() {
        return "Meadow Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        tag(MeadowBlockTagRegistry.MEADOW_GRASS_GROUND).addTag(BlockTags.MOSS_REPLACEABLE);
        tag(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND).addTag(BlockTags.MOSS_REPLACEABLE);

        addTagsFromBlockProperties(blocks.stream().map(RegistryObject::get).collect(Collectors.toList()));
    }
}