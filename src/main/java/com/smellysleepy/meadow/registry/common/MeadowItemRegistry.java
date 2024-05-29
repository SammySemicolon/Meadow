package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;
import static team.lodestar.lodestone.systems.item.LodestoneItemProperties.TAB_SORTING;

public class MeadowItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MEADOW);

    public static final RegistryObject<Item> MEADOW_GRASS_BLOCK = register("meadow_grass_block", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get(), p));

    public static final RegistryObject<Item> TALL_MEADOW_GRASS = register("tall_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> MEDIUM_MEADOW_GRASS = register("medium_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEDIUM_MEADOW_GRASS.get(), p));
    public static final RegistryObject<Item> SHORT_MEADOW_GRASS = register("short_meadow_grass", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.SHORT_MEADOW_GRASS.get(), p));

    public static final RegistryObject<Item> MEADOW_LEAVES = register("meadow_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_LEAVES.get(), p));
    public static final RegistryObject<Item> FLOWERING_MEADOW_LEAVES = register("flowering_meadow_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.FLOWERING_MEADOW_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_MEADOW_LEAVES = register("hanging_meadow_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.HANGING_MEADOW_LEAVES.get(), p));
    public static final RegistryObject<Item> TALL_HANGING_MEADOW_LEAVES = register("tall_hanging_meadow_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TALL_HANGING_MEADOW_LEAVES.get(), p));

    public static final RegistryObject<Item> CALCIFIED_COVERING = register("calcified_covering", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CALCIFIED_COVERING.get(), p));
    public static final RegistryObject<Item> FULLY_CALCIFIED_MEADOW_LOG = register("fully_calcified_meadow_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.FULLY_CALCIFIED_MEADOW_LOG.get(), p));
    public static final RegistryObject<Item> PARTIALLY_CALCIFIED_MEADOW_LOG = register("partially_calcified_meadow_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.PARTIALLY_CALCIFIED_MEADOW_LOG.get(), p));
    public static final RegistryObject<Item> MEADOW_LOG = register("meadow_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_LOG.get(), p));
    public static final RegistryObject<Item> THIN_MEADOW_LOG = register("thin_meadow_log", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.THIN_MEADOW_LOG.get(), p));

    public static final RegistryObject<Item> MEADOW_MUSHROOM = register("meadow_mushroom", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.MEADOW_MUSHROOM.get(), p));

    public static final RegistryObject<Item> CRIMSON_SUN = register("crimson_sun", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.CRIMSON_SUN.get(), p));
    public static final RegistryObject<Item> LAZURITE_ROSE = register("lazurite_rose", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.LAZURITE_ROSE.get(), p));
    public static final RegistryObject<Item> BERYL_ALSTRO = register("beryl_alstro", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.BERYL_ALSTRO.get(), p));
    public static final RegistryObject<Item> TRANQUIL_LILY = register("tranquil_lily", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(MeadowBlockRegistry.TRANQUIL_LILY.get(), p));

    public static <T extends Item> RegistryObject<T> register(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
            TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, (key) -> new ArrayList<>()).add(MeadowMod.meadowModPath(name));
        }
        return ITEMS.register(name, () -> function.apply(properties));
    }
}
