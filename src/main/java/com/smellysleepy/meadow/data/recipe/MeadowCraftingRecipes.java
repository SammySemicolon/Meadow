package com.smellysleepy.meadow.data.recipe;

import com.smellysleepy.meadow.common.block.mineral_flora.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.conditions.*;

import java.util.function.*;

import static net.minecraft.data.recipes.RecipeBuilder.getDefaultRecipeId;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.smelting;
import static net.minecraft.data.recipes.SingleItemRecipeBuilder.stonecutting;

public class MeadowCraftingRecipes implements IConditionBuilder {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        MeadowWoodSetDatagen.buildRecipes(consumer);

        //region mineral flora


        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            shapeless(RecipeCategory.FOOD, bundle.candyItem.get(), 2)
                    .requires(bundle.fruitItem.get())
                    .requires(Items.PAPER)
                    .requires(Items.SUGAR)
                    .unlockedBy("has_fruit", has(bundle.fruitItem.get()))
                    .save(consumer);
        }

        //endregion

        //region Calcification
        var hasCalcifiedFragment = has(MeadowItemRegistry.CALCIFIED_FRAGMENT.get());
        shaped(RecipeCategory.BUILDING_BLOCKS, MeadowItemRegistry.CALCIFIED_EARTH.get(), 4)
                .define('X', Items.DIRT)
                .define('Y', MeadowItemRegistry.CALCIFIED_FRAGMENT.get())
                .pattern("XY").pattern("YX")
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment).save(consumer);
        shaped(RecipeCategory.BUILDING_BLOCKS, MeadowItemRegistry.CALCIFIED_ROCK.get(), 4)
                .define('X', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('Y', MeadowItemRegistry.CALCIFIED_FRAGMENT.get())
                .pattern("XY").pattern("YX")
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment).save(consumer);

        smelting(Ingredient.of(MeadowItemRegistry.CALCIFIED_FRAGMENT.get()), RecipeCategory.MISC, MeadowItemRegistry.CALCIFIED_BRICK.get(), 0.1f, 200)
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment)
                .save(consumer);

        shapelessCalcify(consumer, MeadowItemRegistry.CALCIFIED_LOG.get(), MeadowItemRegistry.ASPEN_LOG.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.STRIPPED_ASPEN_LOG.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_ASPEN_LOG.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_STRIPPED_ASPEN_LOG.get(), 2);

        shapelessCalcify(consumer, MeadowItemRegistry.CALCIFIED_WOOD.get(), MeadowItemRegistry.ASPEN_WOOD.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.STRIPPED_CALCIFIED_WOOD.get(), MeadowItemRegistry.STRIPPED_ASPEN_WOOD.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_CALCIFIED_WOOD.get(), MeadowItemRegistry.THIN_ASPEN_WOOD.get(), 2);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_WOOD.get(), MeadowItemRegistry.THIN_STRIPPED_ASPEN_WOOD.get(), 2);

        shapelessCalcify(consumer, MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(), MeadowItemRegistry.ASPEN_LOG.get(), 1);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(), MeadowItemRegistry.THIN_ASPEN_LOG.get(), 1);
        shapelessCalcify(consumer, MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), MeadowItemRegistry.ASPEN_WOOD.get(), 1);
        shapelessCalcify(consumer, MeadowItemRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), MeadowItemRegistry.THIN_ASPEN_WOOD.get(), 1);

        shapedTwoByTwo(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICK.get());
        shapedStairs(consumer, MeadowItemRegistry.CALCIFIED_BRICKS_STAIRS.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());
        shapedSlab(consumer, MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());
        shapedWall(consumer, MeadowItemRegistry.CALCIFIED_BRICKS_WALL.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());

        shapedTwoByTwo(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), 3, MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get());
        shapedStairs(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get());
        shapedSlab(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get());
        shapedWall(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());

        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get(), 2);
        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_STAIRS.get(), 1);
        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_WALL.get(), 1);

        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), 1);
        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), 2);
        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), 1);
        calcificationStoneCutting(consumer, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), 1);

        stoneCutting(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), 2);
        stoneCutting(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), 1);
        stoneCutting(consumer, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), 1);

        //endregion

        shapedTwoByTwo(consumer, MeadowItemRegistry.PEARLLIGHT.get(), MeadowItemRegistry.PEARLFLOWER_BUD.get());


    }

    private static void shapelessCalcify(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input) {
        shapelessCalcify(recipeConsumer, output, input, 1);
    }
    private static void shapelessCalcify(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input, int calcifiedFragmentCount) {
        var recipeName = BuiltInRegistries.ITEM.getKey(output.asItem()).withSuffix("_from_calcification");
        shapeless(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .requires(input)
                .requires(MeadowItemRegistry.CALCIFIED_FRAGMENT.get(), calcifiedFragmentCount)
                .unlockedBy("has_fragment", has(MeadowItemRegistry.CALCIFIED_FRAGMENT.get()))
                .save(recipeConsumer, recipeName);
    }

    private static void shapedTwoByTwo(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input) {
        shapedTwoByTwo(recipeConsumer, output, 4, input);
    }
    private static void shapedTwoByTwo(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, int outputCount, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, output, outputCount)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedThreeByThree(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input) {
        shapedThreeByThree(recipeConsumer, output, 1, input);
    }

    private static void shapedThreeByThree(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, int outputCount, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, output, outputCount)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedWall(Consumer<FinishedRecipe> recipeConsumer, ItemLike wall, Item input) {
        shaped(RecipeCategory.MISC, wall, 6)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }
    private static void stoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output) {
        stoneCutting(recipeConsumer, input, output, 1);
    }
    private static void stoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output, int outputCount) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_stonecutting_from_" + getDefaultRecipeId(input).getPath());
        stonecutting(Ingredient.of(input), RecipeCategory.MISC, output, outputCount).unlockedBy("has_input", has(input)).save(recipeConsumer, recipeID);
    }
    private static void calcificationStoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output) {
        calcificationStoneCutting(recipeConsumer, input, output, 1);
    }
    private static void calcificationStoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output, int outputCount) {
        stoneCutting(recipeConsumer, input, output, outputCount);
        stoneCutting(recipeConsumer, MeadowItemRegistry.CALCIFIED_EARTH.get(), output, outputCount);
        stoneCutting(recipeConsumer, MeadowItemRegistry.CALCIFIED_ROCK.get(), output, outputCount);
    }

    protected static EnterBlockTrigger.TriggerInstance insideOf(Block pBlock) {
        return new EnterBlockTrigger.TriggerInstance(ContextAwarePredicate.ANY, pBlock, StatePropertiesPredicate.ANY);
    }

    public static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints pCount, ItemLike pItem) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItem).withCount(pCount).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    public static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
