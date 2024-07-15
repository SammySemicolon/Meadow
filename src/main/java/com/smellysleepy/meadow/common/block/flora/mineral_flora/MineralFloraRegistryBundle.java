package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.registry.common.MeadowBlockProperties;
import com.smellysleepy.meadow.registry.common.MeadowItemProperties;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import static com.smellysleepy.meadow.registry.common.MeadowBlockRegistry.BLOCKS;
import static com.smellysleepy.meadow.registry.common.MeadowItemRegistry.register;

public class MineralFloraRegistryBundle {

    public final RegistryObject<Block> flora;
    public final RegistryObject<Block> flower;
    public final RegistryObject<Block> sapling;
    public final RegistryObject<Block> leaves;
    public final RegistryObject<Item> floraItem;
    public final RegistryObject<Item> flowerItem;
    public final RegistryObject<Item> saplingItem;
    public final RegistryObject<Item> leavesItem;

    public MineralFloraRegistryBundle(String prefix, TagKey<Block> tag) {
        this(prefix, tag, false);
    }
    public MineralFloraRegistryBundle(String prefix, TagKey<Block> tag, boolean doubleTallPlant) {
        flora = BLOCKS.register(prefix + "_flora", () -> new MineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        flower = doubleTallPlant ?
                BLOCKS.register(prefix + "_flower", () -> new TallMineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag)) :
                BLOCKS.register(prefix + "_flower", () -> new MineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        sapling = BLOCKS.register(prefix + "_sapling", () -> new MineralPlant(MeadowBlockProperties.MINERAL_FLORA_PROPERTIES(), tag));
        leaves = BLOCKS.register(prefix + "_leaves", () -> new MineralLeaves(MeadowBlockProperties.MINERAL_LEAVES_PROPERTIES()));

        floraItem = register(prefix + "_flora", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(flora.get(), p));
        flowerItem = register(prefix + "_flower", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(flower.get(), p));
        saplingItem = register(prefix + "_sapling", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(sapling.get(), p));
        leavesItem = register(prefix + "_leaves", MeadowItemProperties.DEFAULT_PROPERTIES(), (p) -> new BlockItem(leaves.get(), p));

    }
}
