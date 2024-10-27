package com.smellysleepy.meadow.data.block;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

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
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        tag(MeadowBlockTagRegistry.CALCIFICATION_REPLACEABLE).addTag(BlockTags.MOSS_REPLACEABLE);

        tag(MeadowBlockTagRegistry.MEADOW_GRASS_CAN_PLACE_ON).addTag(BlockTags.MOSS_REPLACEABLE);
        tag(MeadowBlockTagRegistry.MINERAL_FLORA_CAN_PLACE_ON).addTags(BlockTags.MOSS_REPLACEABLE, Tags.Blocks.ORES);
        tag(MeadowBlockTagRegistry.PEARLFLOWER_CAN_PLACE_ON).addTags(BlockTags.LUSH_GROUND_REPLACEABLE);


        tag(MeadowBlockTagRegistry.MEADOW_GROVE_IRREPLACEABLE).addTags(
                MeadowBlockTagRegistry.CALCIFICATION, BlockTags.FEATURES_CANNOT_REPLACE,
                BlockTags.LOGS, BlockTags.LEAVES, BlockTags.FLOWERS, BlockTags.CORALS);

        addTagsFromBlockProperties(blocks.stream().map(RegistryObject::get).collect(Collectors.toList()));
    }
}