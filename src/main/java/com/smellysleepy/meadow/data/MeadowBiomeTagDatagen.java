package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MeadowBiomeTagDatagen extends BiomeTagsProvider {

    public MeadowBiomeTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, MeadowMod.MEADOW, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MeadowTags.BiomeTags.HAS_MEADOW_GROVES).addTags(net.minecraft.tags.BiomeTags.HAS_VILLAGE_PLAINS, net.minecraft.tags.BiomeTags.HAS_VILLAGE_TAIGA, net.minecraft.tags.BiomeTags.IS_HILL, net.minecraft.tags.BiomeTags.IS_FOREST, net.minecraft.tags.BiomeTags.IS_SAVANNA, net.minecraft.tags.BiomeTags.HAS_ANCIENT_CITY);
        tag(MeadowTags.BiomeTags.HAS_MINERAL_TREES).addTags(net.minecraft.tags.BiomeTags.IS_OVERWORLD);
    }
}
