package com.smellysleepy.meadow.data.recipe;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.item.*;
import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.common.item.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.conditions.*;

import java.util.function.*;

import static com.smellysleepy.meadow.data.recipe.MeadowCraftingRecipes.has;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;

public class MeadowWoodSetDatagen implements IConditionBuilder {

    private static final MeadowDatagenWoodSet ASPEN = new MeadowDatagenWoodSet(
            "aspen",
            MeadowItemRegistry.ASPEN_LOG.get(), MeadowItemRegistry.ASPEN_WOOD.get(),
            MeadowItemRegistry.STRIPPED_ASPEN_LOG.get(), MeadowItemRegistry.STRIPPED_ASPEN_WOOD.get(),
            MeadowItemRegistry.THIN_ASPEN_LOG.get(), MeadowItemRegistry.THIN_ASPEN_WOOD.get(),
            MeadowItemRegistry.THIN_STRIPPED_ASPEN_LOG.get(), MeadowItemRegistry.THIN_STRIPPED_ASPEN_WOOD.get(),
            MeadowItemRegistry.ASPEN_PLANKS.get(), MeadowItemRegistry.ASPEN_PLANKS_STAIRS.get(), MeadowItemRegistry.ASPEN_PLANKS_SLAB.get(),
            MeadowItemRegistry.HEAVY_ASPEN_PLANKS.get(), MeadowItemRegistry.HEAVY_ASPEN_PLANKS_STAIRS.get(), MeadowItemRegistry.HEAVY_ASPEN_PLANKS_SLAB.get(),
            MeadowItemRegistry.ASPEN_FENCE.get(), MeadowItemRegistry.ASPEN_FENCE_GATE.get(),
            MeadowItemRegistry.SOLID_ASPEN_DOOR.get(),MeadowItemRegistry.ASPEN_DOOR.get(),
            MeadowItemRegistry.SOLID_ASPEN_TRAPDOOR.get(), MeadowItemRegistry.ASPEN_TRAPDOOR.get(),
            MeadowItemRegistry.ASPEN_BUTTON.get(), MeadowItemRegistry.ASPEN_PRESSURE_PLATE.get(),
            MeadowItemRegistry.ASPEN_SIGN.get(), MeadowItemRegistry.ASPEN_SIGN.get(),
            ItemTagRegistry.ASPEN_LOGS, ItemTagRegistry.THIN_ASPEN_LOGS, ItemTagRegistry.ASPEN_PLANKS, ItemTagRegistry.ASPEN_STAIRS, ItemTagRegistry.ASPEN_SLABS,
            MeadowItemRegistry.ASPEN_BOAT.get()
    );

    private static final MeadowDatagenWoodSet CALCIFIED = new MeadowDatagenWoodSet(
            "calcified",
            MeadowItemRegistry.CALCIFIED_LOG.get(), MeadowItemRegistry.CALCIFIED_WOOD.get(),
            MeadowItemRegistry.STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.STRIPPED_CALCIFIED_WOOD.get(),
            MeadowItemRegistry.THIN_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_CALCIFIED_WOOD.get(),
            MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_LOG.get(), MeadowItemRegistry.THIN_STRIPPED_CALCIFIED_WOOD.get(),
            MeadowItemRegistry.CALCIFIED_PLANKS.get(), MeadowItemRegistry.CALCIFIED_PLANKS_STAIRS.get(), MeadowItemRegistry.CALCIFIED_PLANKS_SLAB.get(),
            MeadowItemRegistry.HEAVY_CALCIFIED_PLANKS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_PLANKS_STAIRS.get(), MeadowItemRegistry.HEAVY_CALCIFIED_PLANKS_SLAB.get(),
            MeadowItemRegistry.CALCIFIED_FENCE.get(), MeadowItemRegistry.CALCIFIED_FENCE_GATE.get(),
            MeadowItemRegistry.SOLID_CALCIFIED_DOOR.get(),MeadowItemRegistry.CALCIFIED_DOOR.get(),
            MeadowItemRegistry.SOLID_CALCIFIED_TRAPDOOR.get(), MeadowItemRegistry.CALCIFIED_TRAPDOOR.get(),
            MeadowItemRegistry.CALCIFIED_BUTTON.get(), MeadowItemRegistry.CALCIFIED_PRESSURE_PLATE.get(),
            MeadowItemRegistry.CALCIFIED_SIGN.get(), MeadowItemRegistry.CALCIFIED_SIGN.get(),
            ItemTagRegistry.CALCIFIED_LOGS, ItemTagRegistry.THIN_CALCIFIED_LOGS, ItemTagRegistry.CALCIFIED_PLANKS, ItemTagRegistry.CALCIFIED_STAIRS, ItemTagRegistry.CALCIFIED_SLABS,
            MeadowItemRegistry.CALCIFIED_BOAT.get()
    );

    public static void addTags(MeadowItemTagDatagen provider) {
        addTags(provider, ASPEN);
        addTags(provider, CALCIFIED);
    }

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildRecipes(consumer, ASPEN);
        buildRecipes(consumer, CALCIFIED);
    }

    protected static void addTags(MeadowItemTagDatagen provider, MeadowDatagenWoodSet woodSet) {
        provider.tag(woodSet.logTag).add(woodSet.log, woodSet.wood, woodSet.strippedLog, woodSet.strippedWood);
        provider.tag(woodSet.thinLogTag).add(woodSet.thinLog, woodSet.thinWood, woodSet.thinStrippedLog, woodSet.thinStrippedWood);
        provider.tag(woodSet.planksTag).add(
                woodSet.planks, woodSet.heavyPlanks
        );
        provider.tag(woodSet.stairsTag).add(
                woodSet.planksStairs, woodSet.heavyPlanksStairs
        );
        provider.tag(woodSet.slabsTag).add(
                woodSet.planksSlab, woodSet.heavyPlanksSlab
        );

        provider.safeCopy(MeadowBlockRegistry.BLOCKS, woodSet.logTag);
        provider.safeCopy(MeadowBlockRegistry.BLOCKS, woodSet.planksTag);
        provider.safeCopy(MeadowBlockRegistry.BLOCKS, woodSet.stairsTag);
        provider.safeCopy(MeadowBlockRegistry.BLOCKS, woodSet.slabsTag);
    }
    protected static void buildRecipes(Consumer<FinishedRecipe> consumer, MeadowDatagenWoodSet woodSet) {
        shapelessPlanks(consumer, woodSet.planks, woodSet.logTag, 4);
        shapelessPlanks(consumer, woodSet.planks, woodSet.thinLogTag, 1);

        shapedSlab(consumer, woodSet.planksSlab, woodSet.planks);
        shapedStairs(consumer, woodSet.planksStairs, woodSet.planks);

        shapedSlab(consumer, woodSet.heavyPlanksSlab, woodSet.heavyPlanks);
        shapedStairs(consumer, woodSet.heavyPlanksStairs, woodSet.heavyPlanks);

        shapelessWood(consumer, woodSet.wood, woodSet.log);
        shapelessWood(consumer, woodSet.strippedWood, woodSet.strippedLog);
        shapelessWood(consumer, woodSet.thinWood, woodSet.thinLog);
        shapelessWood(consumer, woodSet.thinStrippedWood, woodSet.thinStrippedLog);

        shapelessThinLog(consumer, woodSet.thinLog, woodSet.log);
        shapelessThinLog(consumer, woodSet.thinStrippedLog, woodSet.strippedLog);
        shapelessThinLog(consumer, woodSet.thinWood, woodSet.wood);
        shapelessThinLog(consumer, woodSet.thinStrippedWood, woodSet.strippedWood);


        shapelessButton(consumer, woodSet.button, woodSet.planksTag);
        shapedDoor(consumer, woodSet.solidDoor, woodSet.planksTag);
        shapedFence(consumer, woodSet.fence, woodSet.planksTag);
        shapedFenceGate(consumer, woodSet.fenceGate, woodSet.planksTag);
        shapedPressurePlate(consumer, woodSet.pressurePlate, woodSet.planksTag);

        shapedTrapdoor(consumer, woodSet.solidTrapdoor, woodSet.planksTag);

        shapedSign(consumer, woodSet.sign, woodSet.planksTag);

        trapdoorExchange(consumer, woodSet.solidDoor, woodSet.openDoor, woodSet.prefix + "_open_door_exchange");
        trapdoorExchange(consumer, woodSet.openDoor, woodSet.solidDoor, woodSet.prefix + "_solid_door_exchange");

        trapdoorExchange(consumer, woodSet.solidTrapdoor, woodSet.openTrapdoor, woodSet.prefix + "_open_trapdoor_exchange");
        trapdoorExchange(consumer, woodSet.openTrapdoor, woodSet.solidTrapdoor, woodSet.prefix + "_solid_trapdoor_exchange");

        shapedBoat(consumer, woodSet.boat, woodSet.planksTag);
    }

    private static void trapdoorExchange(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output, String path) {
        shapeless(RecipeCategory.BUILDING_BLOCKS, output)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer, MeadowMod.meadowModPath(path));
    }

    private static void shapelessPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planks, TagKey<Item> input, int outputCount) {
        var recipeName = BuiltInRegistries.ITEM.getKey(planks.asItem()).withSuffix("_from_" + input.location().getPath());
        shapeless(RecipeCategory.BUILDING_BLOCKS, planks, outputCount)
                .requires(input)
                .group("planks")
                .unlockedBy("has_logs", has(input))
                .save(recipeConsumer, recipeName);
    }

    private static void shapelessWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, stripped, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .group("bark")
                .unlockedBy("has_log", has(input))
                .save(recipeConsumer);
    }

    private static void shapelessThinLog(Consumer<FinishedRecipe> recipeConsumer, ItemLike thin, ItemLike input) {
        var recipeName = BuiltInRegistries.ITEM.getKey(thin.asItem()).withSuffix("_from_" + BuiltInRegistries.ITEM.getKey(input.asItem()).getPath());
        shaped(RecipeCategory.BUILDING_BLOCKS, thin, 8)
                .define('#', input)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_log", has(input))
                .save(recipeConsumer, recipeName);
    }

    private static void shapelessButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, TagKey<Item> input) {
        shapeless(RecipeCategory.REDSTONE, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, TagKey<Item> input) {
        shaped(RecipeCategory.REDSTONE, pressurePlate)
                .define('#', input)
                .pattern("##")
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

    private static void shapedTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, sign, 3)
                .group("sign")
                .define('#', input)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedBoat(Consumer<FinishedRecipe> recipeConsumer, ItemLike boat, TagKey<Item> input) {
        shaped(RecipeCategory.TRANSPORTATION, boat)
                .define('#', input)
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    public record MeadowDatagenWoodSet(
            String prefix,

            Item log, Item wood,
            Item strippedLog, Item strippedWood,

            Item thinLog, Item thinWood,
            Item thinStrippedLog, Item thinStrippedWood,

            Item planks, Item planksStairs, Item planksSlab,

            Item heavyPlanks, Item heavyPlanksStairs, Item heavyPlanksSlab,

            Item fence, Item fenceGate,

            Item solidDoor, Item openDoor,

            Item solidTrapdoor, Item openTrapdoor,

            Item button, Item pressurePlate,

            Item sign, Item hangingSign,

            TagKey<Item> logTag, TagKey<Item> thinLogTag, TagKey<Item> planksTag, TagKey<Item> stairsTag, TagKey<Item> slabsTag,

            Item boat
    ) { }
}