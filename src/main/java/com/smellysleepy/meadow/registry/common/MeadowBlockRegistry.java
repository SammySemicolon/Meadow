package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.common.block.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.*;

import static com.smellysleepy.meadow.MeadowMod.MEADOW;

public class MeadowBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MEADOW);

    public static final RegistryObject<Block> MEADOW_GRASS_BLOCK = BLOCKS.register("meadow_grass_block", () -> new Block(MeadowBlockProperties.MEADOW_GRASS_BLOCK_PROPERTIES()));

    public static final RegistryObject<Block> TALL_MEADOW_GRASS = BLOCKS.register("tall_meadow_grass", () -> new DoubleMeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> MEDIUM_MEADOW_GRASS = BLOCKS.register("medium_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));
    public static final RegistryObject<Block> SHORT_MEADOW_GRASS = BLOCKS.register("short_meadow_grass", () -> new MeadowGrassBlock(MeadowBlockProperties.MEADOW_GRASS_PROPERTIES()));


}
