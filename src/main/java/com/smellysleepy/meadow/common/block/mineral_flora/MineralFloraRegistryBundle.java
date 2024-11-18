package com.smellysleepy.meadow.common.block.mineral_flora;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.effect.*;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockProperties;
import com.smellysleepy.meadow.registry.common.MeadowItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.*;

import static com.smellysleepy.meadow.registry.common.MeadowMobEffectRegistry.EFFECTS;
import static com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.item.MeadowItemRegistry.register;

public class MineralFloraRegistryBundle {

    public final ResourceLocation id;

    public final Block oreBlock;

    public final ResourceKey<ConfiguredFeature<?, ?>> configuredTreeFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredFlowerFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredNaturalPatchFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredGrassBonemealFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredLeavesBonemealFeature;

    public final ResourceKey<PlacedFeature> placedTreeFeature;

    public final RegistryObject<Block> grassBlock;
    public final RegistryObject<Item> grassBlockItem;

    public final RegistryObject<Block> leavesBlock;
    public final RegistryObject<Item> leavesBlockItem;

    public final RegistryObject<Block> hangingLeavesBlock;
    public final RegistryObject<Item> hangingLeavesBlockItem;

    public final RegistryObject<Block> saplingBlock;
    public final RegistryObject<Item> saplingBlockItem;

    public final RegistryObject<Block> flowerBlock;
    public final RegistryObject<Item> flowerBlockItem;

    public final RegistryObject<Block> floraBlock;
    public final RegistryObject<Item> floraBlockItem;

    public final RegistryObject<Item> fruitItem;
    public final RegistryObject<Item> candyItem;

    public MineralFloraRegistryBundle(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> feature, Supplier<MobEffect> effectSupplier, Color color, Block oreBlock, TagKey<Block> tag) {
        this.id = id;
        var prefix = id.getPath();
        this.oreBlock = oreBlock;

        configuredTreeFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_tree"));
        configuredFlowerFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_plant"));
        configuredNaturalPatchFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_patch"));
        configuredGrassBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_grass_bonemeal"));
        configuredLeavesBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_leaves_bonemeal"));

        placedTreeFeature = ResourceKey.create(Registries.PLACED_FEATURE, MeadowMod.meadowModPath(prefix + "_tree"));

        var grassBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_grass_bonemeal"));
        var leavesBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_leaves_bonemeal"));

        var grassBlockProperties = MeadowBlockProperties.MINERAL_GRASS_BLOCK_PROPERTIES();
        var grassProperties = MeadowBlockProperties.MINERAL_GRASS_PROPERTIES();
        var floraProperties = MeadowBlockProperties.MINERAL_FLORA_PROPERTIES();
        var hangingLeavesProperties = MeadowBlockProperties.HANGING_MINERAL_LEAVES_PROPERTIES();
        var leavesProperties = MeadowBlockProperties.MINERAL_LEAVES_PROPERTIES();

        var itemProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES();
        grassBlock = BLOCKS.register(prefix + "_grass_block", () -> new MineralGrassBlock(grassBlockProperties, grassBonemealFeature));
        grassBlockItem = register(prefix + "_grass_block", itemProperties, (p) -> new BlockItem(grassBlock.get(), p));

        leavesBlock = BLOCKS.register(prefix + "_leaves", () -> new MineralLeavesBlock(leavesProperties, leavesBonemealFeature, color));
        leavesBlockItem = register(prefix + "_leaves", itemProperties, (p) -> new BlockItem(leavesBlock.get(), p));

        hangingLeavesBlock = BLOCKS.register("hanging_" + prefix + "_leaves", () -> new HangingMineralLeavesBlock(hangingLeavesProperties, color));
        hangingLeavesBlockItem = register("hanging_" + prefix + "_leaves", itemProperties, (p) -> new BlockItem(hangingLeavesBlock.get(), p));

        saplingBlock = BLOCKS.register(prefix + "_sapling", () -> new MineralSaplingBlock(floraProperties.addTag(BlockTags.SAPLINGS), feature, tag));
        saplingBlockItem = register(prefix + "_sapling", itemProperties, (p) -> new BlockItem(saplingBlock.get(), p));

        flowerBlock = BLOCKS.register(prefix + "_flower", () -> new TallMineralFlower(floraProperties.addTag(BlockTags.TALL_FLOWERS), tag));
        flowerBlockItem = register(prefix + "_flower", itemProperties, (p) -> new BlockItem(flowerBlock.get(), p));

        floraBlock = BLOCKS.register(prefix + "_flora", () -> new MineralFloraPlant(grassProperties, tag));
        floraBlockItem = register(prefix + "_flora", itemProperties, (p) -> new BlockItem(floraBlock.get(), p));

        var fruitProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES().food(
                new FoodProperties.Builder().nutrition(4).effect(() -> new MobEffectInstance(effectSupplier.get(), 1200, 0), 1f).saturationMod(0.4f).alwaysEat().build()
        );
        var candyProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES().food(
                new FoodProperties.Builder().nutrition(3).effect(() -> new MobEffectInstance(effectSupplier.get(), 300, 1), 1f).saturationMod(0.3f).fast().alwaysEat().build()
        );

        fruitItem = register(prefix + "_fruit", fruitProperties, Item::new);
        candyItem = register(prefix + "_candy", candyProperties, Item::new);


    }
}