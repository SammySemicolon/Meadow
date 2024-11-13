package com.smellysleepy.meadow.registry.common.tags;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.*;

public class MeadowBlockTagRegistry {

    public static final TagKey<Block> MEADOW_GROVE_IRREPLACEABLE = meadowTag("meadow_grove_irreplaceable");

    public static final TagKey<Block> CALCIFICATION = meadowTag("calcification");
    public static final TagKey<Block> CALCIFICATION_REPLACEABLE = meadowTag("calcification_replaceable");

    public static final TagKey<Block> MEADOW_GRASS_CAN_PLACE_ON = meadowTag("meadow_grass_can_place_on");
    public static final TagKey<Block> ASPEN_SAPLING_CAN_PLACE_ON = meadowTag("meadow_sapling_can_place_on");
    public static final TagKey<Block> MINERAL_FLORA_CAN_PLACE_ON = meadowTag("mineral_flora_can_place_on");

    public static final TagKey<Block> PEARLFLOWER_CAN_PLACE_ON = meadowTag("pearlflower_can_place_on");

    public static final TagKey<Block> GRASSY_PEARLFLOWER_GENERATES_ON = meadowTag("grassy_pearlflower_generates_on");
    public static final TagKey<Block> MARINE_PEARLFLOWER_GENERATES_ON = meadowTag("marine_pearlflower_generates_on");
    public static final TagKey<Block> CALCIFIED_PEARLFLOWER_GENERATES_ON = meadowTag("calcified_pearlflower_generates_on");
    public static final TagKey<Block> ROCKY_PEARLFLOWER_GENERATES_ON = meadowTag("rocky_pearlflower_generates_on");

    public static final TagKey<Block> MOOMOO_EDIBLE = meadowTag("moomoo_edible");

    public static final TagKey<Block> STRIPPED_LOGS = forgeTag("stripped_logs");

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