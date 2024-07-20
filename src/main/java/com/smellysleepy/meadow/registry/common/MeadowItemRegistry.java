package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;
import static team.lodestar.lodestone.systems.item.LodestoneItemProperties.TAB_SORTING;

public class MeadowItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MEADOW);

    public static final RegistryObject<Item> CALCIFIED_EARTH = register("calcified_earth", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_EARTH.get(), p));
    public static final RegistryObject<Item> CALCIFIED_ROCK = register("calcified_rock", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_ROCK.get(), p));

    public static final RegistryObject<Item> CALCIFIED_ASPEN_LOG = register("calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> PARTIALLY_CALCIFIED_ASPEN_LOG = register("partially_calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_CALCIFIED_ASPEN_LOG = register("thin_calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_ASPEN_LOG = register("thin_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> ASPEN_LOG = register("aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LOG.get(), p));


    public static final RegistryObject<Item> MEADOW_GRASS_BLOCK = register("meadow_grass_block", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(), p));
    public static final RegistryObject<Item> ASPEN_SAPLING = register("aspen_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));
    public static final RegistryObject<Item> SMALL_ASPEN_SAPLING = register("small_aspen_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SMALL_ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));

    public static final RegistryObject<Item> TALL_MEADOW_GRASS = register("tall_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> MEDIUM_MEADOW_GRASS = register("medium_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> SHORT_MEADOW_GRASS = register("short_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), p));

    public static final RegistryObject<Item> ASPEN_LEAVES = register("aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> FLOWERING_ASPEN_LEAVES = register("flowering_aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.FLOWERING_ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_ASPEN_LEAVES = register("hanging_aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> TALL_HANGING_ASPEN_LEAVES = register("tall_hanging_aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_HANGING_ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> ASPEN_LEAF_PILE = register("aspen_leaf_pile", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LEAF_PILE.get(), p));

    public static final RegistryObject<Item> ASPEN_PLANKS = register("aspen_planks", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PLANKS.get(), p));
    public static final RegistryObject<Item> ASPEN_BOARDS = register("aspen_boards", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_BOARDS.get(), p));
    public static final RegistryObject<Item> HEAVY_ASPEN_BOARDS = register("heavy_aspen_boards", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_ASPEN_BOARDS.get(), p));
    public static final RegistryObject<Item> ASPEN_DOOR = register("aspen_door", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> SOLID_ASPEN_DOOR = register("solid_aspen_door", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> ASPEN_TRAPDOOR = register("aspen_trapdoor", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_ASPEN_TRAPDOOR = register("solid_aspen_trapdoor", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_TRAPDOOR.get(), p));


    public static <T extends Item> RegistryObject<T> register(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
            TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, (key) -> new ArrayList<>()).add(MeadowMod.meadowModPath(name));
        }
        return ITEMS.register(name, () -> function.apply(properties));
    }
}
