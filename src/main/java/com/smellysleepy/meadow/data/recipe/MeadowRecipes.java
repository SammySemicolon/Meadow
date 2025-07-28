package com.smellysleepy.meadow.data.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.*;

public class MeadowRecipes extends VanillaRecipeProvider {

    public final MeadowVanillaRecipeReplacements vanillaRecipeReplacements;

    public MeadowRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.vanillaRecipeReplacements = new MeadowVanillaRecipeReplacements(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        MeadowCraftingRecipes.buildRecipes(recipeOutput);
        vanillaRecipeReplacements.buildRecipes(recipeOutput);
    }
}
