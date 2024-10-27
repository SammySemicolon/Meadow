package com.smellysleepy.meadow.registry.common.tags;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;

public class ItemTagRegistry {
    public static final TagKey<Item> ASPEN_LOGS = meadowTag("aspen_logs");
    public static final TagKey<Item> THIN_ASPEN_LOGS = meadowTag("thin_aspen_logs");
    public static final TagKey<Item> ASPEN_PLANKS = meadowTag("aspen_planks");
    public static final TagKey<Item> ASPEN_SLABS = meadowTag("aspen_slabs");
    public static final TagKey<Item> ASPEN_STAIRS = meadowTag("aspen_stairs");

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
