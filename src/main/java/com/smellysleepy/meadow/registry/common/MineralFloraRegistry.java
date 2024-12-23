package com.smellysleepy.meadow.registry.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.*;

public class MineralFloraRegistry {

    public static final HashMap<ResourceLocation, MineralFloraRegistryBundle> MINERAL_FLORA_TYPES = new HashMap<>();

    public static final Codec<MineralFloraRegistryBundle> CODEC = ResourceLocation.CODEC.flatXmap(
            key -> Optional.ofNullable(MINERAL_FLORA_TYPES.get(key)).map(DataResult::success).orElseThrow(),
            bundle -> Optional.of(bundle.id).map(DataResult::success).orElseThrow());

    public static final MineralFloraRegistryBundle AMETHYST_FLORA = register("amethyst", MeadowParticleRegistry.AMETHYST_LEAF, MeadowMobEffectRegistry.AMETHYST_FRUIT_EFFECT, new Color(254, 203, 230), Blocks.AMETHYST_BLOCK, Tags.Blocks.STORAGE_BLOCKS_AMETHYST);

    public static final MineralFloraRegistryBundle COAL_FLORA = register("coal", MeadowParticleRegistry.COAL_LEAF, () -> MobEffects.DIG_SPEED, new Color(40, 36, 38), Blocks.COAL_ORE, Tags.Blocks.ORES_COAL);
    public static final MineralFloraRegistryBundle LAPIS_FLORA = register("lapis", MeadowParticleRegistry.LAPIS_LEAF, () -> MobEffects.DIG_SPEED, new Color(103, 120, 238), Blocks.LAPIS_ORE, Tags.Blocks.ORES_LAPIS);
    public static final MineralFloraRegistryBundle REDSTONE_FLORA = register("redstone", MeadowParticleRegistry.REDSTONE_LEAF, () -> MobEffects.DIG_SPEED, new Color(255, 86, 81), Blocks.REDSTONE_ORE, Tags.Blocks.ORES_REDSTONE);

    public static final MineralFloraRegistryBundle COPPER_FLORA = register("copper", MeadowParticleRegistry.COPPER_LEAF, MeadowMobEffectRegistry.COPPER_FRUIT_EFFECT, new Color(245, 151, 91), Blocks.COPPER_ORE, Tags.Blocks.ORES_COPPER);
    public static final MineralFloraRegistryBundle IRON_FLORA = register("iron", MeadowParticleRegistry.IRON_LEAF, MeadowMobEffectRegistry.IRON_FRUIT_EFFECT, new Color(216, 175, 147), Blocks.IRON_ORE, Tags.Blocks.ORES_IRON);
    public static final MineralFloraRegistryBundle GOLD_FLORA = register("gold", MeadowParticleRegistry.GOLD_LEAF, MeadowMobEffectRegistry.GOLD_FRUIT_EFFECT, new Color(253, 226, 86), Blocks.GOLD_ORE, Tags.Blocks.ORES_GOLD);

    public static final MineralFloraRegistryBundle DIAMOND_FLORA = register("diamond", MeadowParticleRegistry.DIAMOND_LEAF, MeadowMobEffectRegistry.DIAMOND_FRUIT_EFFECT, new Color(185, 228, 255), Blocks.DIAMOND_ORE, Tags.Blocks.ORES_DIAMOND);
    public static final MineralFloraRegistryBundle EMERALD_FLORA = register("emerald", MeadowParticleRegistry.EMERALD_LEAF, MeadowMobEffectRegistry.EMERALD_FRUIT_EFFECT, new Color(207, 255, 94), Blocks.EMERALD_ORE, Tags.Blocks.ORES_EMERALD);
    public static final MineralFloraRegistryBundle NETHERITE_FLORA = register("netherite", MeadowParticleRegistry.NETHERITE_LEAF, MeadowMobEffectRegistry.NETHERITE_FRUIT_EFFECT, new Color(93, 52, 44), Blocks.NETHER_GOLD_ORE, Tags.Blocks.ORES_NETHERITE_SCRAP);

    public static void init() {
    }

    public static MineralFloraRegistryBundle register(String prefix, RegistryObject<LodestoneWorldParticleType> particleType, Supplier<MobEffect> effect, Color color, Block ore, TagKey<Block> tag) {
        return register(MeadowMod.meadowModPath(prefix), ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_tree")),
                particleType, effect, color, ore, tag);
    }

    public static MineralFloraRegistryBundle register(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> key, RegistryObject<LodestoneWorldParticleType> particleType, Supplier<MobEffect> effect, Color color, Block ore, TagKey<Block> tag) {
        var bundle = new MineralFloraRegistryBundle(id, key, particleType, effect, color, ore, tag);
        MINERAL_FLORA_TYPES.put(id, bundle);
        return bundle;
    }
}
