package com.smellysleepy.meadow.registry.common.item;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.block.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;
import static com.smellysleepy.meadow.registry.common.MeadowItemProperties.DEFAULT_PROPERTIES;
import static team.lodestar.lodestone.systems.item.LodestoneItemProperties.TAB_SORTING;

public class MeadowItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MEADOW);

    public static final RegistryObject<Item> GIANT_CALCIFIED_DRIPSTONE = register("giant_calcified_dripstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get(), p));
    public static final RegistryObject<Item> CALCIFIED_DRIPSTONE = register("calcified_dripstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get(), p));

    public static final RegistryObject<Item> CALCIFIED_FRAGMENT = register("calcified_fragment", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(MeadowBlockRegistry.CALCIFIED_COVERING.get(), p));
    public static final RegistryObject<Item> CALCIFIED_BRICK = register("calcified_brick", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> CALCIFIED_EARTH = register("calcified_earth", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_EARTH.get(), p));
    public static final RegistryObject<Item> CALCIFIED_ROCK = register("calcified_rock", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_ROCK.get(), p));

    public static final RegistryObject<Item> CALCIFIED_BRICKS = register("calcified_bricks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_BRICKS.get(), p));
    public static final RegistryObject<Item> CHISELED_CALCIFIED_BRICKS = register("chiseled_calcified_bricks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CHISELED_CALCIFIED_BRICKS.get(), p));
    public static final RegistryObject<Item> CALCIFIED_BRICKS_STAIRS = register("calcified_bricks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> CALCIFIED_BRICKS_SLAB = register("calcified_bricks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> CALCIFIED_BRICKS_WALL = register("calcified_bricks_wall", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_BRICKS = register("heavy_calcified_bricks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS.get(), p));
    public static final RegistryObject<Item> HEAVY_CHISELED_CALCIFIED_BRICKS = register("heavy_chiseled_calcified_bricks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CHISELED_CALCIFIED_BRICKS.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_BRICKS_STAIRS = register("heavy_calcified_bricks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_BRICKS_SLAB = register("heavy_calcified_bricks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_BRICKS_WALL = register("heavy_calcified_bricks_wall", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_BRICKS_WALL.get(), p));

    public static final RegistryObject<Item> CALCIFIED_LOG = register("calcified_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_LOG.get(), p));
    public static final RegistryObject<Item> CALCIFIED_WOOD = register("calcified_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_WOOD.get(), p));
    public static final RegistryObject<Item> THIN_CALCIFIED_LOG = register("thin_calcified_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_CALCIFIED_LOG.get(), p));
    public static final RegistryObject<Item> THIN_CALCIFIED_WOOD = register("thin_calcified_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_CALCIFIED_WOOD.get(), p));

    public static final RegistryObject<Item> STRIPPED_CALCIFIED_LOG = register("stripped_calcified_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.STRIPPED_CALCIFIED_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_CALCIFIED_WOOD = register("stripped_calcified_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.STRIPPED_CALCIFIED_WOOD.get(), p));
    public static final RegistryObject<Item> THIN_STRIPPED_CALCIFIED_LOG = register("thin_stripped_calcified_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_STRIPPED_CALCIFIED_LOG.get(), p));
    public static final RegistryObject<Item> THIN_STRIPPED_CALCIFIED_WOOD = register("thin_stripped_calcified_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_STRIPPED_CALCIFIED_WOOD.get(), p));

    public static final RegistryObject<Item> CALCIFIED_PLANKS = register("calcified_planks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PLANKS.get(), p));
    public static final RegistryObject<Item> CALCIFIED_PLANKS_STAIRS = register("calcified_planks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> CALCIFIED_PLANKS_SLAB = register("calcified_planks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PLANKS_SLAB.get(), p));

    public static final RegistryObject<Item> HEAVY_CALCIFIED_PLANKS = register("heavy_calcified_planks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_PLANKS_STAIRS = register("heavy_calcified_planks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> HEAVY_CALCIFIED_PLANKS_SLAB = register("heavy_calcified_planks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_CALCIFIED_PLANKS_SLAB.get(), p));

    public static final RegistryObject<Item> CALCIFIED_FENCE = register("calcified_planks_fence", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_FENCE.get(), p));
    public static final RegistryObject<Item> CALCIFIED_FENCE_GATE = register("calcified_planks_fence_gate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> SOLID_CALCIFIED_DOOR = register("solid_calcified_door", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_CALCIFIED_DOOR.get(), p));
    public static final RegistryObject<Item> CALCIFIED_DOOR = register("calcified_door", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_DOOR.get(), p));
    public static final RegistryObject<Item> SOLID_CALCIFIED_TRAPDOOR = register("solid_calcified_trapdoor", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_CALCIFIED_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> CALCIFIED_TRAPDOOR = register("calcified_trapdoor", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> CALCIFIED_PRESSURE_PLATE = register("calcified_planks_pressure_plate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> CALCIFIED_BUTTON = register("calcified_planks_button", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_BUTTON.get(), p));

    public static final RegistryObject<Item> CALCIFIED_SIGN = register("calcified_sign", DEFAULT_PROPERTIES(), (p) -> new SignItem(p, MeadowBlockRegistry.CALCIFIED_SIGN.get(), MeadowBlockRegistry.CALCIFIED_WALL_SIGN.get()));
    public static final RegistryObject<Item> CALCIFIED_BOAT = register("calcified_boat", DEFAULT_PROPERTIES(), (p) -> new LodestoneBoatItem(p.stacksTo(1), MeadowEntityRegistry.CALCIFIED_BOAT));

    public static final RegistryObject<Item> CALCIFIED_PEARLLAMP = register("calcified_pearllamp", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PEARLLAMP.get(), p));

    public static final RegistryObject<Item> PARTIALLY_CALCIFIED_ASPEN_LOG = register("partially_calcified_aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> PARTIALLY_CALCIFIED_ASPEN_WOOD = register("partially_calcified_aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), p));
    public static final RegistryObject<Item> THIN_PARTIALLY_CALCIFIED_ASPEN_LOG = register("thin_partially_calcified_aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD = register("thin_partially_calcified_aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_WOOD.get(), p));

    public static final RegistryObject<Item> ASPEN_PEARLLAMP = register("aspen_pearllamp", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PEARLLAMP.get(), p));

    public static final RegistryObject<Item> ASPEN_LOG = register("aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> ASPEN_WOOD = register("aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_WOOD.get(), p));
    public static final RegistryObject<Item> THIN_ASPEN_LOG = register("thin_aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_ASPEN_WOOD = register("thin_aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_ASPEN_WOOD.get(), p));
    public static final RegistryObject<Item> STRIPPED_ASPEN_LOG = register("stripped_aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.STRIPPED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_ASPEN_WOOD = register("stripped_aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.STRIPPED_ASPEN_WOOD.get(), p));
    public static final RegistryObject<Item> THIN_STRIPPED_ASPEN_LOG = register("thin_stripped_aspen_log", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_STRIPPED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_STRIPPED_ASPEN_WOOD = register("thin_stripped_aspen_wood", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_STRIPPED_ASPEN_WOOD.get(), p));

    public static final RegistryObject<Item> ASPEN_PLANKS = register("aspen_planks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PLANKS.get(), p));
    public static final RegistryObject<Item> ASPEN_PLANKS_STAIRS = register("aspen_planks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> ASPEN_PLANKS_SLAB = register("aspen_planks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PLANKS_SLAB.get(), p));

    public static final RegistryObject<Item> HEAVY_ASPEN_PLANKS = register("heavy_aspen_planks", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_ASPEN_PLANKS.get(), p));
    public static final RegistryObject<Item> HEAVY_ASPEN_PLANKS_STAIRS = register("heavy_aspen_planks_stairs", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_ASPEN_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> HEAVY_ASPEN_PLANKS_SLAB = register("heavy_aspen_planks_slab", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_ASPEN_PLANKS_SLAB.get(), p));

    public static final RegistryObject<Item> ASPEN_FENCE = register("aspen_planks_fence", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_FENCE.get(), p));
    public static final RegistryObject<Item> ASPEN_FENCE_GATE = register("aspen_planks_fence_gate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> SOLID_ASPEN_DOOR = register("solid_aspen_door", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> ASPEN_DOOR = register("aspen_door", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> SOLID_ASPEN_TRAPDOOR = register("solid_aspen_trapdoor", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> ASPEN_TRAPDOOR = register("aspen_trapdoor", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> ASPEN_PRESSURE_PLATE = register("aspen_planks_pressure_plate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> ASPEN_BUTTON = register("aspen_planks_button", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_BUTTON.get(), p));

    public static final RegistryObject<Item> ASPEN_SIGN = register("aspen_sign", DEFAULT_PROPERTIES(), (p) -> new SignItem(p, MeadowBlockRegistry.ASPEN_SIGN.get(), MeadowBlockRegistry.ASPEN_WALL_SIGN.get()));
    public static final RegistryObject<Item> ASPEN_BOAT = register("aspen_boat", DEFAULT_PROPERTIES(), (p) -> new LodestoneBoatItem(p.stacksTo(1), MeadowEntityRegistry.ASPEN_BOAT));

    public static final RegistryObject<Item> SMALL_ASPEN_SAPLING = register("small_aspen_sapling", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SMALL_ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));
    public static final RegistryObject<Item> ASPEN_SAPLING = register("aspen_sapling", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));
    public static final RegistryObject<Item> HANGING_ASPEN_LEAVES = register("hanging_aspen_leaves", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> ASPEN_LEAVES = register("aspen_leaves", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LEAVES.get(), p));

    public static final RegistryObject<Item> PEARLLIGHT = register("pearllight", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PEARLLIGHT.get(), p));
    public static final RegistryObject<Item> PEARLFLOWER_BUD = register("pearlflower_bud", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CLUMP_OF_FUR = register("clump_of_fur", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> TALL_GRASSY_PEARLFLOWER = register("tall_grassy_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> GRASSY_PEARLFLOWER = register("grassy_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_MARINE_PEARLFLOWER = register("tall_marine_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> MARINE_PEARLFLOWER = register("marine_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_CALCIFIED_PEARLFLOWER = register("tall_calcified_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> CALCIFIED_PEARLFLOWER = register("calcified_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_ROCKY_PEARLFLOWER = register("tall_rocky_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_ROCKY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> ROCKY_PEARLFLOWER = register("rocky_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ROCKY_PEARLFLOWER.get(), p));

    public static final RegistryObject<Item> TALL_WILTED_GRASSY_PEARLFLOWER = register("tall_wilted_grassy_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_WILTED_GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> WILTED_GRASSY_PEARLFLOWER = register("wilted_grassy_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.WILTED_GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_WILTED_MARINE_PEARLFLOWER = register("tall_wilted_marine_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_WILTED_MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> WILTED_MARINE_PEARLFLOWER = register("wilted_marine_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.WILTED_MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_WILTED_CALCIFIED_PEARLFLOWER = register("tall_wilted_calcified_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_WILTED_CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> WILTED_CALCIFIED_PEARLFLOWER = register("wilted_calcified_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.WILTED_CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_WILTED_ROCKY_PEARLFLOWER = register("tall_wilted_rocky_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_WILTED_ROCKY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> WILTED_ROCKY_PEARLFLOWER = register("wilted_rocky_pearlflower", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.WILTED_ROCKY_PEARLFLOWER.get(), p));

//    public static final RegistryObject<Item> AUREATE_WHEAT_CROP = register("aureate_wheat_test", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.AUREATE_WHEAT_CROP.get(), p));
    public static final RegistryObject<Item> SHORT_MEADOW_GRASS = register("short_meadow_grass", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> MEDIUM_MEADOW_GRASS = register("medium_meadow_grass", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> TALL_MEADOW_GRASS = register("tall_meadow_grass", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> MEADOW_GRASS_BLOCK = register("meadow_grass_block", DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(), p));


    public static <T extends Item> RegistryObject<T> register(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
            TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, (key) -> new ArrayList<>()).add(MeadowMod.meadowModPath(name));
        }
        return ITEMS.register(name, () -> function.apply(properties));
    }
}
