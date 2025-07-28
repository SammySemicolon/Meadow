package com.smellysleepy.meadow.data.recipe;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.item.*;
import com.smellysleepy.meadow.registry.common.MeadowTags;
import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

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
            MeadowItemRegistry.ASPEN_PEARLLAMP.get(),
            MeadowTags.ItemTags.ASPEN_LOGS, MeadowTags.ItemTags.THIN_ASPEN_LOGS, MeadowTags.ItemTags.ASPEN_PLANKS, MeadowTags.ItemTags.ASPEN_STAIRS, MeadowTags.ItemTags.ASPEN_SLABS,
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
            MeadowItemRegistry.CALCIFIED_PEARLLAMP.get(),
            MeadowTags.ItemTags.CALCIFIED_LOGS, MeadowTags.ItemTags.THIN_CALCIFIED_LOGS, MeadowTags.ItemTags.CALCIFIED_PLANKS, MeadowTags.ItemTags.CALCIFIED_STAIRS, MeadowTags.ItemTags.CALCIFIED_SLABS,
            MeadowItemRegistry.CALCIFIED_BOAT.get()
    );

    public static void addTags(MeadowItemTagDatagen provider) {
        addTags(provider, ASPEN);
        addTags(provider, CALCIFIED);
    }

    public static void buildRecipes(RecipeOutput recipeOutput) {
        buildRecipes(recipeOutput, ASPEN);
        buildRecipes(recipeOutput, CALCIFIED);
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
    protected static void buildRecipes(RecipeOutput recipeOutput, MeadowDatagenWoodSet woodSet) {
        shapelessPlanks(recipeOutput, woodSet.planks, woodSet.logTag, 4);
        shapelessPlanks(recipeOutput, woodSet.planks, woodSet.thinLogTag, 1);

        shapedSlab(recipeOutput, woodSet.planksSlab, woodSet.planks);
        shapedStairs(recipeOutput, woodSet.planksStairs, woodSet.planks);

        shapedHeavyPlanks(recipeOutput, woodSet.heavyPlanks, woodSet.planksSlab);

        shapedSlab(recipeOutput, woodSet.heavyPlanksSlab, woodSet.heavyPlanks);
        shapedStairs(recipeOutput, woodSet.heavyPlanksStairs, woodSet.heavyPlanks);

        shapelessWood(recipeOutput, woodSet.wood, woodSet.log);
        shapelessWood(recipeOutput, woodSet.strippedWood, woodSet.strippedLog);
        shapelessWood(recipeOutput, woodSet.thinWood, woodSet.thinLog);
        shapelessWood(recipeOutput, woodSet.thinStrippedWood, woodSet.thinStrippedLog);

        shapelessThinLog(recipeOutput, woodSet.thinLog, woodSet.log);
        shapelessThinLog(recipeOutput, woodSet.thinStrippedLog, woodSet.strippedLog);
        shapelessThinLog(recipeOutput, woodSet.thinWood, woodSet.wood);
        shapelessThinLog(recipeOutput, woodSet.thinStrippedWood, woodSet.strippedWood);

        shapelessButton(recipeOutput, woodSet.button, woodSet.planksTag);
        shapedDoor(recipeOutput, woodSet.solidDoor, woodSet.planksTag);
        shapedFence(recipeOutput, woodSet.fence, woodSet.planksTag);
        shapedFenceGate(recipeOutput, woodSet.fenceGate, woodSet.planksTag);
        shapedPressurePlate(recipeOutput, woodSet.pressurePlate, woodSet.planksTag);

        shapedTrapdoor(recipeOutput, woodSet.solidTrapdoor, woodSet.planksTag);

        shapedSign(recipeOutput, woodSet.sign, woodSet.planksTag);

        shapedPearllamp(recipeOutput, woodSet.pearllamp, woodSet.logTag);

        trapdoorExchange(recipeOutput, woodSet.solidDoor, woodSet.openDoor, woodSet.prefix + "_open_door_exchange");
        trapdoorExchange(recipeOutput, woodSet.openDoor, woodSet.solidDoor, woodSet.prefix + "_solid_door_exchange");

        trapdoorExchange(recipeOutput, woodSet.solidTrapdoor, woodSet.openTrapdoor, woodSet.prefix + "_open_trapdoor_exchange");
        trapdoorExchange(recipeOutput, woodSet.openTrapdoor, woodSet.solidTrapdoor, woodSet.prefix + "_solid_trapdoor_exchange");

        shapedBoat(recipeOutput, woodSet.boat, woodSet.planksTag);
    }

    private static void trapdoorExchange(RecipeOutput recipeOutput, ItemLike input, ItemLike output, String path) {
        shapeless(RecipeCategory.BUILDING_BLOCKS, output)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeOutput, MeadowMod.meadowModPath(path));
    }

    private static void shapedHeavyPlanks(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .group("planks")
                .unlockedBy("has_log", has(input))
                .save(recipeOutput);
    }

    private static void shapelessPlanks(RecipeOutput recipeOutput, ItemLike planks, TagKey<Item> input, int outputCount) {
        var recipeName = BuiltInRegistries.ITEM.getKey(planks.asItem()).withSuffix("_from_" + input.location().getPath());
        shapeless(RecipeCategory.BUILDING_BLOCKS, planks, outputCount)
                .requires(input)
                .group("planks")
                .unlockedBy("has_logs", has(input))
                .save(recipeOutput, recipeName);
    }

    private static void shapelessWood(RecipeOutput recipeOutput, ItemLike stripped, ItemLike input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, stripped, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .group("bark")
                .unlockedBy("has_log", has(input))
                .save(recipeOutput);
    }

    private static void shapelessThinLog(RecipeOutput recipeOutput, ItemLike thin, ItemLike input) {
        var recipeName = BuiltInRegistries.ITEM.getKey(thin.asItem()).withSuffix("_from_" + BuiltInRegistries.ITEM.getKey(input.asItem()).getPath());
        shaped(RecipeCategory.BUILDING_BLOCKS, thin, 8)
                .define('#', input)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_log", has(input))
                .save(recipeOutput, recipeName);
    }

    private static void shapelessButton(RecipeOutput recipeOutput, ItemLike button, TagKey<Item> input) {
        shapeless(RecipeCategory.REDSTONE, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedDoor(RecipeOutput recipeOutput, ItemLike door, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedFence(RecipeOutput recipeOutput, ItemLike fence, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedFenceGate(RecipeOutput recipeOutput, ItemLike fenceGate, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedPressurePlate(RecipeOutput recipeOutput, ItemLike pressurePlate, TagKey<Item> input) {
        shaped(RecipeCategory.REDSTONE, pressurePlate)
                .define('#', input)
                .pattern("##")
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

    private static void shapedTrapdoor(RecipeOutput recipeOutput, ItemLike trapdoor, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedPearllamp(RecipeOutput recipeOutput, ItemLike stairs, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 1)
                .define('X', input)
                .define('Y', MeadowItemRegistry.PEARLLIGHT.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedSign(RecipeOutput recipeOutput, ItemLike sign, TagKey<Item> input) {
        shaped(RecipeCategory.BUILDING_BLOCKS, sign, 3)
                .group("sign")
                .define('#', input)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedBoat(RecipeOutput recipeOutput, ItemLike boat, TagKey<Item> input) {
        shaped(RecipeCategory.TRANSPORTATION, boat)
                .define('#', input)
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeOutput);
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

            Item pearllamp,

            TagKey<Item> logTag, TagKey<Item> thinLogTag, TagKey<Item> planksTag, TagKey<Item> stairsTag, TagKey<Item> slabsTag,

            Item boat
    ) { }
}