package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MineralFloraRegistry {

    public static final ArrayList<MineralFloraRegistryBundle> MINERAL_FLORA = new ArrayList<>();

    public static final MineralFloraRegistryBundle AMETHYST_FLORA = registerTall("amethyst", Tags.Blocks.STORAGE_BLOCKS_AMETHYST);

    public static final MineralFloraRegistryBundle COAL_FLORA = registerTall("coal", Tags.Blocks.ORES_COAL);
    public static final MineralFloraRegistryBundle LAPIS_FLORA = register("lapis", Tags.Blocks.ORES_LAPIS);
    public static final MineralFloraRegistryBundle REDSTONE_FLORA = register("redstone", Tags.Blocks.ORES_REDSTONE);

    public static final MineralFloraRegistryBundle COPPER_FLORA = register("copper", Tags.Blocks.ORES_COPPER);
    public static final MineralFloraRegistryBundle IRON_FLORA = registerTall("iron", Tags.Blocks.ORES_IRON);
    public static final MineralFloraRegistryBundle GOLD_FLORA = registerTall("gold", Tags.Blocks.ORES_GOLD);

    public static final MineralFloraRegistryBundle DIAMOND_FLORA = registerTall("diamond", Tags.Blocks.ORES_DIAMOND);
    public static final MineralFloraRegistryBundle EMERALD_FLORA = registerTall("emerald", Tags.Blocks.ORES_EMERALD);
    public static final MineralFloraRegistryBundle NETHERITE_FLORA = registerTall("netherite", Tags.Blocks.ORES_NETHERITE_SCRAP);


    public static void init() {

    }

    public static MineralFloraRegistryBundle register(String prefix, TagKey<Block> tag) {
        var bundle = new MineralFloraRegistryBundle(prefix, tag);
        MINERAL_FLORA.add(bundle);
        return bundle;
    }

    public static MineralFloraRegistryBundle registerTall(String prefix, TagKey<Block> tag) {
        var bundle = new MineralFloraRegistryBundle(prefix, tag, true);
        MINERAL_FLORA.add(bundle);
        return bundle;
    }
}
