package com.smellysleepy.meadow.data.recipe;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.mineral.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.minecraft.data.recipes.RecipeBuilder.getDefaultRecipeId;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.smelting;
import static net.minecraft.data.recipes.SingleItemRecipeBuilder.stonecutting;

public class MeadowCraftingRecipes implements IConditionBuilder {

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        MeadowWoodSetDatagen.buildRecipes(recipeOutput);

        //region mineral flora


        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            shapeless(RecipeCategory.FOOD, bundle.candyItem.get(), 2)
                    .requires(bundle.fruitItem.get())
                    .requires(Items.PAPER)
                    .requires(Items.SUGAR)
                    .unlockedBy("has_fruit", has(bundle.fruitItem.get()))
                    .save(recipeOutput);
            shapeless(RecipeCategory.FOOD, bundle.pastryItem.get(), 2)
                    .requires(bundle.fruitItem.get())
                    .requires(bundle.fruitItem.get())
                    .requires(Items.WHEAT)
                    .requires(Items.WHEAT)
                    .requires(Items.EGG)
                    .requires(Items.SUGAR)
                    .unlockedBy("has_fruit", has(bundle.fruitItem.get()))
                    .save(recipeOutput);
        }

        //endregion

        //region Calcification
        var hasCalcifiedFragment = has(MeadowItemRegistry.CALCIFIED_FRAGMENT.get());
        shaped(RecipeCategory.BUILDING_BLOCKS, MeadowItemRegistry.CALCIFIED_EARTH.get(), 4)
                .define('X', Items.DIRT)
                .define('Y', MeadowItemRegistry.CALCIFIED_FRAGMENT.get())
                .pattern("XY").pattern("YX")
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment).save(recipeOutput);
        shaped(RecipeCategory.BUILDING_BLOCKS, MeadowItemRegistry.CALCIFIED_ROCK.get(), 4)
                .define('X', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('Y', MeadowItemRegistry.CALCIFIED_FRAGMENT.get())
                .pattern("XY").pattern("YX")
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment).save(recipeOutput);

        shapeless(RecipeCategory.DECORATIONS, Items.BLUE_DYE, 1)
                .requires(MeadowItemRegistry.CALCIFIED_FRAGMENT.get())
                .unlockedBy("has_fragment", has(MeadowItemRegistry.CALCIFIED_FRAGMENT.get()))
                .save(recipeOutput, MeadowMod.meadowPath("blue_dye_from_calcification"));

        smelting(Ingredient.of(MeadowItemRegistry.CALCIFIED_FRAGMENT.get()), RecipeCategory.MISC, MeadowItemRegistry.CALCIFIED_BRICK.get(), 0.1f, 200)
                .unlockedBy("has_calcified_fragment", hasCalcifiedFragment)
                .save(recipeOutput);

        shapelessCalcify(recipeOutput, MeadowItemRegistry.CALCIFIED_LOG.get(), MeadowItemRegistry.ASPEN_LOG.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.STRIPPED_ASPEN_LOG.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_ASPEN_LOG.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_STRIPPED_ASPEN_LOG.get(), 2);

        shapelessCalcify(recipeOutput, MeadowItemRegistry.CALCIFIED_WOOD.get(), MeadowItemRegistry.ASPEN_WOOD.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.STRIPPED_CALCIFIED_WOOD.get(), MeadowItemRegistry.STRIPPED_ASPEN_WOOD.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_CALCIFIED_WOOD.get(), MeadowItemRegistry.THIN_ASPEN_WOOD.get(), 2);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_WOOD.get(), MeadowItemRegistry.THIN_STRIPPED_ASPEN_WOOD.get(), 2);

        shapelessCalcify(recipeOutput, MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(), MeadowItemRegistry.ASPEN_LOG.get(), 1);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(), MeadowItemRegistry.THIN_ASPEN_LOG.get(), 1);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), MeadowItemRegistry.ASPEN_WOOD.get(), 1);
        shapelessCalcify(recipeOutput, MeadowItemRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), MeadowItemRegistry.THIN_ASPEN_WOOD.get(), 1);

        shapedTwoByTwo(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICK.get());
        shapedStairs(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS_STAIRS.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());
        shapedSlab(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());
        shapedWall(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS_WALL.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());

        shapedTwoByTwo(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), 3, MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get());
        shapedStairs(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get());
        shapedSlab(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get());
        shapedWall(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), MeadowItemRegistry.CALCIFIED_BRICKS.get());

        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_SLAB.get(), 2);
        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_STAIRS.get(), 1);
        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.CALCIFIED_BRICKS_WALL.get(), 1);

        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), 1);
        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), 2);
        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), 1);
        calcificationStoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), 1);

        stoneCutting(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), 2);
        stoneCutting(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), 1);
        stoneCutting(recipeOutput, MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), 1);

        //endregion

        shapedTwoByTwo(recipeOutput, MeadowItemRegistry.PEARLLIGHT.get(), MeadowItemRegistry.PEARLFLOWER_BUD.get());


    }

    private static void shapelessCalcify(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        shapelessCalcify(recipeOutput, output, input, 1);
    }
    private static void shapelessCalcify(RecipeOutput recipeOutput, ItemLike output, ItemLike input, int calcifiedFragmentCount) {
        var recipeName = BuiltInRegistries.ITEM.getKey(output.asItem()).withSuffix("_from_calcification");
        shapeless(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .requires(input)
                .requires(MeadowItemRegistry.CALCIFIED_FRAGMENT.get(), calcifiedFragmentCount)
                .unlockedBy("has_fragment", has(MeadowItemRegistry.CALCIFIED_FRAGMENT.get()))
                .save(recipeOutput, recipeName);
    }

    private static void shapedTwoByTwo(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        shapedTwoByTwo(recipeOutput, output, 4, input);
    }
    private static void shapedTwoByTwo(RecipeOutput recipeOutput, ItemLike output, int outputCount, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, output, outputCount)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedThreeByThree(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        shapedThreeByThree(recipeOutput, output, 1, input);
    }

    private static void shapedThreeByThree(RecipeOutput recipeOutput, ItemLike output, int outputCount, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, output, outputCount)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedSlab(RecipeOutput recipeOutput, ItemLike slab, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedStairs(RecipeOutput recipeOutput, ItemLike stairs, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedWall(RecipeOutput recipeOutput, ItemLike wall, Item input) {
        shaped(RecipeCategory.MISC, wall, 6)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }
    private static void stoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output) {
        stoneCutting(recipeOutput, input, output, 1);
    }
    private static void stoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output, int outputCount) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_stonecutting_from_" + getDefaultRecipeId(input).getPath());
        stonecutting(Ingredient.of(input), RecipeCategory.MISC, output, outputCount).unlockedBy("has_input", has(input)).save(recipeOutput, recipeID);
    }
    private static void calcificationStoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output) {
        calcificationStoneCutting(recipeOutput, input, output, 1);
    }
    private static void calcificationStoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output, int outputCount) {
        stoneCutting(recipeOutput, input, output, outputCount);
        stoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_EARTH.get(), output, outputCount);
        stoneCutting(recipeOutput, MeadowItemRegistry.CALCIFIED_ROCK.get(), output, outputCount);
    }

    public static Criterion<EnterBlockTrigger.TriggerInstance> insideOf(Block block) {
        return CriteriaTriggers.ENTER_BLOCK
                .createCriterion(new EnterBlockTrigger.TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty()));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED
                .createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }
}
