package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.registry.common.MeadowBlockProperties;
import com.smellysleepy.meadow.registry.common.MeadowItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

import java.awt.*;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.register;

public class MineralFloraRegistryBundle {

    public final ResourceLocation id;

    public final RegistryObject<Block> grassBlock;
    public final RegistryObject<Block> floraBlock;
    public final RegistryObject<Block> flowerBlock;
    public final RegistryObject<Block> saplingBlock;
    public final RegistryObject<Block> hangingLeavesBlock;
    public final RegistryObject<Block> leavesBlock;

    public final RegistryObject<Item> grassItem;
    public final RegistryObject<Item> floraItem;
    public final RegistryObject<Item> flowerItem;
    public final RegistryObject<Item> saplingItem;
    public final RegistryObject<Item> leavesItem;
    public final RegistryObject<Item> hangingLeavesItem;

    public final RegistryObject<Item> fruitItem;

    public MineralFloraRegistryBundle(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> feature, Color color, TagKey<Block> tag) {
        this.id = id;
        var prefix = id.getPath();
        var grassBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_grass_bonemeal"));

        var grassBlockProperties = MeadowBlockProperties.MINERAL_GRASS_BLOCK_PROPERTIES();
        var grassProperties = MeadowBlockProperties.MINERAL_GRASS_PROPERTIES();
        var floraProperties = MeadowBlockProperties.MINERAL_FLORA_PROPERTIES();
        var leavesProperties = MeadowBlockProperties.MINERAL_LEAVES_PROPERTIES();
        var hangingLeavesProperties = MeadowBlockProperties.HANGING_MINERAL_LEAVES_PROPERTIES();
        var itemProperties = MeadowItemProperties.DEFAULT_PROPERTIES();
        var foodProperties = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build();

        grassBlock = BLOCKS.register(prefix + "_grass_block", () -> new MineralGrassBlock(grassBlockProperties, grassBonemealFeature));
        floraBlock = BLOCKS.register(prefix + "_flora", () -> new MineralFloraPlant(grassProperties, tag));
        flowerBlock = BLOCKS.register(prefix + "_flower", () -> new TallMineralFlower(floraProperties, tag));
        saplingBlock = BLOCKS.register(prefix + "_sapling", () -> new MineralSaplingBlock(floraProperties, feature, tag));
        hangingLeavesBlock = BLOCKS.register("hanging_" + prefix + "_leaves", () -> new HangingMineralLeavesBlock(hangingLeavesProperties, color));
        leavesBlock = BLOCKS.register(prefix + "_leaves", () -> new MineralLeavesBlock(leavesProperties, hangingLeavesBlock, color));

        grassItem = register(prefix + "_grass_block", itemProperties, (p) -> new BlockItem(grassBlock.get(), p));
        floraItem = register(prefix + "_flora", itemProperties, (p) -> new BlockItem(floraBlock.get(), p));
        flowerItem = register(prefix + "_flower", itemProperties, (p) -> new BlockItem(flowerBlock.get(), p));
        saplingItem = register(prefix + "_sapling", itemProperties, (p) -> new BlockItem(saplingBlock.get(), p));
        leavesItem = register(prefix + "_leaves", itemProperties, (p) -> new BlockItem(leavesBlock.get(), p));
        hangingLeavesItem = register("hanging_" + prefix + "_leaves", itemProperties, (p) -> new BlockItem(hangingLeavesBlock.get(), p));

        fruitItem = register(prefix + "_fruit", itemProperties.food(foodProperties), Item::new);
    }
}