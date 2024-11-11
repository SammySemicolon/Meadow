package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.registry.common.tags.MeadowBiomeTagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MeadowBiomeTagDatagen extends BiomeTagsProvider {
    public MeadowBiomeTagDatagen(PackOutput p_255800_, CompletableFuture<HolderLookup.Provider> p_256205_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255800_, p_256205_, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MeadowBiomeTagRegistry.HAS_MEADOW_GROVES).addTags(BiomeTags.HAS_VILLAGE_PLAINS, BiomeTags.HAS_VILLAGE_TAIGA, BiomeTags.IS_HILL, BiomeTags.IS_FOREST, BiomeTags.IS_SAVANNA, BiomeTags.HAS_ANCIENT_CITY);
        tag(MeadowBiomeTagRegistry.HAS_MINERAL_TREES).addTags(BiomeTags.IS_OVERWORLD);
    }
}
