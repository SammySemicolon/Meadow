package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreeFeature;
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

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.register;

public class MineralFloraRegistryBundle {

    public final ResourceLocation id;

    public final RegistryObject<Block> grass;
    public final RegistryObject<Block> flora;
    public final RegistryObject<Block> flower;
    public final RegistryObject<Block> sapling;
    public final RegistryObject<Block> leaves;

    public final RegistryObject<Item> grassItem;
    public final RegistryObject<Item> floraItem;
    public final RegistryObject<Item> flowerItem;
    public final RegistryObject<Item> saplingItem;
    public final RegistryObject<Item> leavesItem;

    public MineralFloraRegistryBundle(ResourceLocation id, ResourceKey<ConfiguredFeature<?, ?>> feature, TagKey<Block> tag) {
        this.id = id;
        String prefix = id.getPath();
        grass = BLOCKS.register(prefix + "_grass_block", () -> new MineralGrassBlock(MeadowBlockProperties.MINERAL_GRASS_BLOCK_PROPERTIES()));
        flora = BLOCKS.register(prefix + "_flora", () -> new MineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        flower = BLOCKS.register(prefix + "_flower", () -> new TallMineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        sapling = BLOCKS.register(prefix + "_sapling", () -> new MineralSapling(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), feature, tag));
        leaves = BLOCKS.register(prefix + "_leaves", () -> new MineralLeaves(MeadowBlockProperties.MINERAL_LEAVES_PROPERTIES()));

        grassItem = register(prefix + "_grass_block", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(grass.get(), p));
        floraItem = register(prefix + "_flora", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(flora.get(), p));
        flowerItem = register(prefix + "_flower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(flower.get(), p));
        saplingItem = register(prefix + "_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(sapling.get(), p));
        leavesItem = register(prefix + "_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(leaves.get(), p));
        }
}
