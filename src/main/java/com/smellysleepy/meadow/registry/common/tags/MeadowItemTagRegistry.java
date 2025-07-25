package com.smellysleepy.meadow.registry.common.tags;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;

public class MeadowItemTagRegistry {
    public static final TagKey<Item> ASPEN_LOGS = meadowTag("aspen_logs");
    public static final TagKey<Item> THIN_ASPEN_LOGS = meadowTag("thin_aspen_logs");
    public static final TagKey<Item> ASPEN_PLANKS = meadowTag("aspen_planks");
    public static final TagKey<Item> ASPEN_SLABS = meadowTag("aspen_slabs");
    public static final TagKey<Item> ASPEN_STAIRS = meadowTag("aspen_stairs");

    public static final TagKey<Item> CALCIFIED_LOGS = meadowTag("calcified_logs");
    public static final TagKey<Item> THIN_CALCIFIED_LOGS = meadowTag("thin_calcified_logs");
    public static final TagKey<Item> CALCIFIED_PLANKS = meadowTag("calcified_planks");
    public static final TagKey<Item> CALCIFIED_SLABS = meadowTag("calcified_slabs");
    public static final TagKey<Item> CALCIFIED_STAIRS = meadowTag("calcified_stairs");

    public static final TagKey<Item> MINERAL_FRUIT = meadowTag("mineral_fruit");

    public static final TagKey<Item> STRIPPED_LOGS = forgeTag("stripped_logs");

    private static TagKey<Item> modTag(String path) {
        return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(path));
    }

    private static TagKey<Item> meadowTag(String path) {
        return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), MeadowMod.meadowModPath(path));
    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
}
