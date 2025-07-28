package com.smellysleepy.meadow.data.block;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;

import static com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry.BLOCKS;

public class MeadowBlockTagDatagen extends LodestoneBlockTagsProvider {

    public MeadowBlockTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Meadow Block Tags";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var blocks = new HashSet<>(BLOCKS.getEntries());

        tag(MeadowTags.BlockTags.CALCIFICATION_REPLACEABLE).addTag(net.minecraft.tags.BlockTags.MOSS_REPLACEABLE);

        tag(MeadowTags.BlockTags.ASPEN_GRASS_CAN_PLACE_ON).addTag(net.minecraft.tags.BlockTags.MOSS_REPLACEABLE);
        tag(MeadowTags.BlockTags.ASPEN_SAPLING_CAN_PLACE_ON).addTags(MeadowTags.BlockTags.CALCIFICATION, MeadowTags.BlockTags.ASPEN_GRASS_CAN_PLACE_ON);
        tag(MeadowTags.BlockTags.MINERAL_FLORA_CAN_PLACE_ON).addTags(MeadowTags.BlockTags.CALCIFICATION, net.minecraft.tags.BlockTags.MOSS_REPLACEABLE, Tags.Blocks.ORES);


        tag(MeadowTags.BlockTags.GRASSY_PEARLFLOWER_GENERATES_ON).addTags(net.minecraft.tags.BlockTags.DIRT);
        tag(MeadowTags.BlockTags.MARINE_PEARLFLOWER_GENERATES_ON).add(Blocks.MOSS_BLOCK, Blocks.GRASS_BLOCK);
        tag(MeadowTags.BlockTags.ROCKY_PEARLFLOWER_GENERATES_ON).addTags(net.minecraft.tags.BlockTags.MOSS_REPLACEABLE).add(Blocks.COBBLESTONE);
        tag(MeadowTags.BlockTags.PEARLFLOWER_CAN_PLACE_ON).addTags(
                MeadowTags.BlockTags.GRASSY_PEARLFLOWER_GENERATES_ON, MeadowTags.BlockTags.MARINE_PEARLFLOWER_GENERATES_ON,
                MeadowTags.BlockTags.CALCIFIED_PEARLFLOWER_GENERATES_ON, MeadowTags.BlockTags.ROCKY_PEARLFLOWER_GENERATES_ON);

        tag(MeadowTags.BlockTags.MEADOW_GROVE_IRREPLACEABLE).addTags(
                net.minecraft.tags.BlockTags.FEATURES_CANNOT_REPLACE, net.minecraft.tags.BlockTags.LOGS, net.minecraft.tags.BlockTags.LEAVES, net.minecraft.tags.BlockTags.CAVE_VINES, net.minecraft.tags.BlockTags.FLOWERS, net.minecraft.tags.BlockTags.CORALS);

        addTagsFromBlockProperties(blocks);
    }
}