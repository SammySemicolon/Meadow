package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class MeadowTags {

    public static class ItemTags {
        public static final TagKey<Item> ASPEN_LOGS = tag("aspen_logs");
        public static final TagKey<Item> THIN_ASPEN_LOGS = tag("thin_aspen_logs");
        public static final TagKey<Item> ASPEN_PLANKS = tag("aspen_planks");
        public static final TagKey<Item> ASPEN_SLABS = tag("aspen_slabs");
        public static final TagKey<Item> ASPEN_STAIRS = tag("aspen_stairs");

        public static final TagKey<Item> CALCIFIED_LOGS = tag("calcified_logs");
        public static final TagKey<Item> THIN_CALCIFIED_LOGS = tag("thin_calcified_logs");
        public static final TagKey<Item> CALCIFIED_PLANKS = tag("calcified_planks");
        public static final TagKey<Item> CALCIFIED_SLABS = tag("calcified_slabs");
        public static final TagKey<Item> CALCIFIED_STAIRS = tag("calcified_stairs");

        public static final TagKey<Item> MINERAL_FRUITS = tag("mineral_fruits");

        private static TagKey<Item> tag(String path) {
            return MeadowTags.tag(Registries.ITEM, path);
        }

        private static TagKey<Item> modTag(String path) {
            return MeadowTags.modTag(Registries.ITEM, path);
        }

        private static TagKey<Item> commonTag(String path) {
            return MeadowTags.commonTag(Registries.ITEM, path);
        }

    }

    public static class BlockTags {


        public static final TagKey<Block> MEADOW_GROVE_IRREPLACEABLE = tag("meadow_grove_irreplaceable");
        public static final TagKey<Block> CALCIFICATION = tag("calcification");

        public static final TagKey<Block> CALCIFICATION_REPLACEABLE = tag("calcification_replaceable");
        public static final TagKey<Block> ASPEN_GRASS_CAN_PLACE_ON = tag("aspen_grass_can_place_on");

        public static final TagKey<Block> ASPEN_SAPLING_CAN_PLACE_ON = tag("aspen_sapling_can_place_on");

        public static final TagKey<Block> MINERAL_FLORA_CAN_PLACE_ON = tag("mineral_flora_can_place_on");

        public static final TagKey<Block> PEARLFLOWER_CAN_PLACE_ON = tag("pearlflower_can_place_on");
        public static final TagKey<Block> GRASSY_PEARLFLOWER_GENERATES_ON = tag("grassy_pearlflower_generates_on");
        public static final TagKey<Block> MARINE_PEARLFLOWER_GENERATES_ON = tag("marine_pearlflower_generates_on");
        public static final TagKey<Block> CALCIFIED_PEARLFLOWER_GENERATES_ON = tag("calcified_pearlflower_generates_on");

        public static final TagKey<Block> ROCKY_PEARLFLOWER_GENERATES_ON = tag("rocky_pearlflower_generates_on");
        public static final TagKey<Block> MOOMOO_EDIBLE = tag("moomoo_edible");

        public static final TagKey<Block> MOOMOO_CAN_SPAWN_ON = tag("moomoo_can_spawn_on");

        private static TagKey<Block> tag(String path) {
            return MeadowTags.tag(Registries.BLOCK, path);
        }

        private static TagKey<Block> modTag(String path) {
            return MeadowTags.modTag(Registries.BLOCK, path);
        }

        private static TagKey<Block> commonTag(String path) {
            return MeadowTags.commonTag(Registries.BLOCK, path);
        }
    
    }
    public static class BiomeTags {


        public static final TagKey<Biome> HAS_MEADOW_GROVES = tag("has_meadow_groves");

        public static final TagKey<Biome> HAS_MINERAL_TREES = tag("has_mineral_trees");

        private static TagKey<Biome> tag(String path) {
            return MeadowTags.tag(Registries.BIOME, path);
        }

        private static TagKey<Biome> modTag(String path) {
            return MeadowTags.modTag(Registries.BIOME, path);
        }

        private static TagKey<Biome> commonTag(String path) {
            return MeadowTags.commonTag(Registries.BIOME, path);
        }
    }

    private static <T> TagKey<T> tag(ResourceKey<Registry<T>> registry, String path) {
        return TagKey.create(registry, MeadowMod.meadowPath(path));
    }

    private static <T> TagKey<T> modTag(ResourceKey<Registry<T>> registry, String path) {
        return TagKey.create(registry, ResourceLocation.parse(path));
    }

    private static <T> TagKey<T> commonTag(ResourceKey<Registry<T>> registry, String path) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}