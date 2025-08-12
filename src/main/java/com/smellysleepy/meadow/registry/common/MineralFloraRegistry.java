package com.smellysleepy.meadow.registry.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;

public class MineralFloraRegistry {

    public static final HashMap<ResourceLocation, MineralFloraRegistryBundle> MINERAL_FLORA_TYPES = new HashMap<>();

    public static final Codec<MineralFloraRegistryBundle> CODEC = ResourceLocation.CODEC.flatXmap(
            key -> Optional.ofNullable(MINERAL_FLORA_TYPES.get(key)).map(DataResult::success).orElseThrow(),
            bundle -> Optional.of(bundle.id).map(DataResult::success).orElseThrow());

    public static final MineralFloraRegistryBundle AMETHYST_FLORA = register("amethyst", MeadowMobEffectRegistry.AMETHYST_FRUIT_EFFECT, new Color(254, 203, 230), Blocks.AMETHYST_BLOCK, Tags.Blocks.ORES_COAL);

    public static final MineralFloraRegistryBundle COAL_FLORA = register("coal", MobEffects.DIG_SPEED, new Color(40, 36, 38), Blocks.COAL_ORE, Tags.Blocks.ORES_COAL);
    public static final MineralFloraRegistryBundle LAPIS_FLORA = register("lapis", MobEffects.DIG_SPEED, new Color(103, 120, 238), Blocks.LAPIS_ORE, Tags.Blocks.ORES_LAPIS);
    public static final MineralFloraRegistryBundle REDSTONE_FLORA = register("redstone", MobEffects.DIG_SPEED, new Color(255, 86, 81), Blocks.REDSTONE_ORE, Tags.Blocks.ORES_REDSTONE);

    public static final MineralFloraRegistryBundle COPPER_FLORA = register("copper", MeadowMobEffectRegistry.COPPER_FRUIT_EFFECT, new Color(245, 151, 91), Blocks.COPPER_ORE, Tags.Blocks.ORES_COPPER);
    public static final MineralFloraRegistryBundle IRON_FLORA = register("iron", MeadowMobEffectRegistry.IRON_FRUIT_EFFECT, new Color(216, 175, 147), Blocks.IRON_ORE, Tags.Blocks.ORES_IRON);
    public static final MineralFloraRegistryBundle GOLD_FLORA = register("gold", MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT, new Color(253, 226, 86), Blocks.GOLD_ORE, Tags.Blocks.ORES_GOLD);

    public static final MineralFloraRegistryBundle DIAMOND_FLORA = register("diamond", MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT, new Color(185, 228, 255), Blocks.DIAMOND_ORE, Tags.Blocks.ORES_DIAMOND);
    public static final MineralFloraRegistryBundle EMERALD_FLORA = register("emerald", MeadowMobEffectRegistry.EMERALD_FRUIT_EFFECT, new Color(207, 255, 94), Blocks.EMERALD_ORE, Tags.Blocks.ORES_EMERALD);
    public static final MineralFloraRegistryBundle NETHERITE_FLORA = register("netherite", MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT, new Color(93, 52, 44), Blocks.NETHER_GOLD_ORE, Tags.Blocks.ORES_NETHERITE_SCRAP);

    public static void init() {
    }

    public static MineralFloraRegistryBundle register(String prefix, Holder<MobEffect> effect, Color color, Block ore, TagKey<Block> tag) {
        return register(MeadowMod.meadowPath(prefix), effect, color, ore, tag);
    }

    public static MineralFloraRegistryBundle register(ResourceLocation id, Holder<MobEffect> effect, Color color, Block ore, TagKey<Block> tag) {
        var bundle = new MineralFloraRegistryBundle(id, effect, color, ore, tag);
        MINERAL_FLORA_TYPES.put(id, bundle);
        return bundle;
    }
}
