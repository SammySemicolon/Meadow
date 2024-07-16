package com.smellysleepy.meadow.registry.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Optional;

public class MineralFloraRegistry {

    public static final HashMap<ResourceLocation, MineralFloraRegistryBundle> MINERAL_FLORA = new HashMap<>();

    public static final Codec<MineralFloraRegistryBundle> CODEC = ResourceLocation.CODEC.flatXmap(
            key -> Optional.ofNullable(MINERAL_FLORA.get(key)).map(DataResult::success).orElseThrow(),
            bundle -> Optional.of(bundle.id).map(DataResult::success).orElseThrow());

    public static final MineralFloraRegistryBundle AMETHYST_FLORA = register("amethyst", Tags.Blocks.STORAGE_BLOCKS_AMETHYST);

    public static final MineralFloraRegistryBundle COAL_FLORA = register("coal", Tags.Blocks.ORES_COAL);
    public static final MineralFloraRegistryBundle LAPIS_FLORA = register("lapis", Tags.Blocks.ORES_LAPIS);
    public static final MineralFloraRegistryBundle REDSTONE_FLORA = register("redstone", Tags.Blocks.ORES_REDSTONE);

    public static final MineralFloraRegistryBundle COPPER_FLORA = register("copper", Tags.Blocks.ORES_COPPER);
    public static final MineralFloraRegistryBundle IRON_FLORA = register("iron", Tags.Blocks.ORES_IRON);
    public static final MineralFloraRegistryBundle GOLD_FLORA = register("gold", Tags.Blocks.ORES_GOLD);

    public static final MineralFloraRegistryBundle DIAMOND_FLORA = register("diamond", Tags.Blocks.ORES_DIAMOND);
    public static final MineralFloraRegistryBundle EMERALD_FLORA = register("emerald", Tags.Blocks.ORES_EMERALD);
    public static final MineralFloraRegistryBundle NETHERITE_FLORA = register("netherite", Tags.Blocks.ORES_NETHERITE_SCRAP);

    public static void init() {

    }

    public static MineralFloraRegistryBundle register(String prefix, TagKey<Block> tag) {
        return register(MeadowMod.meadowModPath(prefix), ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_tree")), tag);
    }

    public static MineralFloraRegistryBundle register(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> key, TagKey<Block> tag) {
        var bundle = new MineralFloraRegistryBundle(id, key, tag);
        MINERAL_FLORA.put(id, bundle);
        return bundle;
    }
}
