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
    public static final RegistryObject<Item> CALCIFIED_COVERING = register("calcified_covering", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_COVERING.get(), p));
    public static final RegistryObject<Item> CALCIFIED_DRIPSTONE = register("calcified_dripstone", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_DRIPSTONE.get(), p));
    public static final RegistryObject<Item> GIANT_CALCIFIED_DRIPSTONE = register("giant_calcified_dripstone", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.GIANT_CALCIFIED_DRIPSTONE.get(), p));

    public static final RegistryObject<Item> CALCIFIED_ASPEN_LOG = register("calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_CALCIFIED_ASPEN_LOG = register("thin_calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> PARTIALLY_CALCIFIED_ASPEN_LOG = register("partially_calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_PARTIALLY_CALCIFIED_ASPEN_LOG = register("thin_partially_calcified_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_PARTIALLY_CALCIFIED_ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> ASPEN_LOG = register("aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LOG.get(), p));
    public static final RegistryObject<Item> THIN_ASPEN_LOG = register("thin_aspen_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_ASPEN_LOG.get(), p));

    public static final RegistryObject<Item> ASPEN_PLANKS = register("aspen_planks", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_PLANKS.get(), p));
    public static final RegistryObject<Item> ASPEN_BOARDS = register("aspen_boards", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_BOARDS.get(), p));
    public static final RegistryObject<Item> HEAVY_ASPEN_BOARDS = register("heavy_aspen_boards", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HEAVY_ASPEN_BOARDS.get(), p));
    public static final RegistryObject<Item> ASPEN_DOOR = register("aspen_door", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> SOLID_ASPEN_DOOR = register("solid_aspen_door", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_DOOR.get(), p));
    public static final RegistryObject<Item> ASPEN_TRAPDOOR = register("aspen_trapdoor", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_ASPEN_TRAPDOOR = register("solid_aspen_trapdoor", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SOLID_ASPEN_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> ASPEN_LEAVES = register("aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_ASPEN_LEAVES = register("hanging_aspen_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HANGING_ASPEN_LEAVES.get(), p));

    public static final RegistryObject<Item> ASPEN_SAPLING = register("aspen_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));
    public static final RegistryObject<Item> SMALL_ASPEN_SAPLING = register("small_aspen_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SMALL_ASPEN_SAPLING.get(), p.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15f).build())));

    public static final RegistryObject<Item> MEADOW_GRASS_BLOCK = register("meadow_grass_block", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(), p));

    public static final RegistryObject<Item> TALL_MEADOW_GRASS = register("tall_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> MEDIUM_MEADOW_GRASS = register("medium_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> SHORT_MEADOW_GRASS = register("short_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), p));

    public static final RegistryObject<Item> PEARLLAMP = register("pearllamp", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PEARLLAMP.get(), p));

    public static final RegistryObject<Item> GRASSY_PEARLLIGHT = register("grassy_pearllight", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.GRASSY_PEARLLIGHT.get(), p));
    public static final RegistryObject<Item> MARINE_PEARLLIGHT = register("marine_pearllight", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MARINE_PEARLLIGHT.get(), p));
    public static final RegistryObject<Item> CALCIFIED_PEARLLIGHT = register("calcified_pearllight", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PEARLLIGHT.get(), p));
    public static final RegistryObject<Item> PEARLLIGHT = register("pearllight", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PEARLLIGHT.get(), p));

    public static final RegistryObject<Item> PEARLFLOWER_BUD = register("pearlflower_bud", MeadowItemProperties.DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> TALL_GRASSY_PEARLFLOWER = register("tall_grassy_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_MARINE_PEARLFLOWER = register("tall_marine_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_CALCIFIED_PEARLFLOWER = register("tall_calcified_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> TALL_PEARLFLOWER = register("tall_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> GRASSY_PEARLFLOWER = register("grassy_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.GRASSY_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> MARINE_PEARLFLOWER = register("marine_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MARINE_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> CALCIFIED_PEARLFLOWER = register("calcified_pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get(), p));
    public static final RegistryObject<Item> PEARLFLOWER = register("pearlflower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PEARLFLOWER.get(), p));



    public static <T extends Item> RegistryObject<T> register(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
            TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, (key) -> new ArrayList<>()).add(MeadowMod.meadowModPath(name));
        }
        return ITEMS.register(name, () -> function.apply(properties));
    }
}
