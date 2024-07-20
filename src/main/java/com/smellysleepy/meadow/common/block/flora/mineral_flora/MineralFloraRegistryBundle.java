package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.registry.common.MeadowBlockProperties;
import com.smellysleepy.meadow.registry.common.MeadowItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.register;

public class MineralFloraRegistryBundle {

    public final ResourceLocation id;

    public final RegistryObject<Block> grassBlock;
    public final RegistryObject<Block> floraBlock;
    public final RegistryObject<Block> flowerBlock;
    public final RegistryObject<Block> saplingBlock;
    public final RegistryObject<Block> leavesBlock;

    public final RegistryObject<Item> grassItem;
    public final RegistryObject<Item> floraItem;
    public final RegistryObject<Item> flowerItem;
    public final RegistryObject<Item> saplingItem;
    public final RegistryObject<Item> leavesItem;

    public MineralFloraRegistryBundle(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> feature, Color color, TagKey<Block> tag) {
        this.id = id;
        String prefix = id.getPath();
        grassBlock = BLOCKS.register(prefix + "_grass_block", () -> new MineralGrassBlock(MeadowBlockProperties.MINERAL_GRASS_BLOCK_PROPERTIES()));
        floraBlock = BLOCKS.register(prefix + "_flora", () -> new MineralFloraPlant(MeadowBlockProperties.MINERAL_GRASS_PROPERTIES(), tag));
        flowerBlock = BLOCKS.register(prefix + "_flower", () -> new TallMineralFlower(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        saplingBlock = BLOCKS.register(prefix + "_sapling", () -> new MineralSapling(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), feature, tag));
        leavesBlock = BLOCKS.register(prefix + "_leaves", () -> new MineralLeaves(MeadowBlockProperties.MINERAL_LEAVES_PROPERTIES(), color));

        grassItem = register(prefix + "_grass_block", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(grassBlock.get(), p));
        floraItem = register(prefix + "_flora", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(floraBlock.get(), p));
        flowerItem = register(prefix + "_flower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(flowerBlock.get(), p));
        saplingItem = register(prefix + "_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(saplingBlock.get(), p));
        leavesItem = register(prefix + "_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(leavesBlock.get(), p));
    }
}