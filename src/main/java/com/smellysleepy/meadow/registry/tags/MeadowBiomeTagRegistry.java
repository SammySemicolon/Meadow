package com.smellysleepy.meadow.registry.tags;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class MeadowBiomeTagRegistry {

    public static final TagKey<Biome> HAS_MEADOW_GROVES = meadowTag("has_meadow_groves");

    private static TagKey<Biome> modTag(String path) {
        return TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(path));
    }

    private static TagKey<Biome> meadowTag(String path) {
        return TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), MeadowMod.meadowModPath(path));
    }

    private static TagKey<Biome> forgeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation("forge", name));
    }
}