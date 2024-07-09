package com.smellysleepy.meadow.registry.tags;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.*;

public class MeadowBlockTagRegistry {

    public static final TagKey<Block> MEADOW_GRASS_GROUND = meadowTag("meadow_grass_ground");
    public static final TagKey<Block> STRANGE_FLORA_GROUND = meadowTag("strange_flora_ground");

    private static TagKey<Block> modTag(String path) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(path));
    }

    private static TagKey<Block> meadowTag(String path) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), MeadowMod.meadowModPath(path));
    }

    private static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }
}