package com.smellysleepy.meadow.data.recipe;

import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;

import java.util.function.*;

public class MeadowRecipes extends VanillaRecipeProvider {

    public PackOutput pOutput;

    public MeadowRecipes(PackOutput pOutput) {
        super(pOutput);
        this.pOutput = pOutput;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        MeadowCraftingRecipes.buildRecipes(consumer);
        new MeadowVanillaRecipeReplacements(pOutput).buildRecipes(consumer);
    }
}
