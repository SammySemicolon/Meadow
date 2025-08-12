package com.smellysleepy.meadow.common.block.mineral;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.block.mineral.leaves.HangingMineralLeavesBlock;
import com.smellysleepy.meadow.common.block.mineral.leaves.MineralLeavesBlock;
import com.smellysleepy.meadow.common.block.mineral.plant.MineralFloraPlant;
import com.smellysleepy.meadow.common.block.mineral.plant.MineralSaplingBlock;
import com.smellysleepy.meadow.common.block.mineral.plant.TallMineralFlower;
import com.smellysleepy.meadow.registry.common.MeadowParticleRegistry;
import com.smellysleepy.meadow.registry.common.MeadowTreeGrowers;
import com.smellysleepy.meadow.registry.common.block.properties.MineralBlockProperties;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.core.Holder;
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
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.function.*;

import static com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry.BLOCKS;

public class MineralFloraRegistryBundle {

    public final ResourceLocation id;

    public final Block oreBlock;

    public final ResourceKey<ConfiguredFeature<?, ?>> configuredTreeFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredFlowerFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredNaturalPatchFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredGrassBonemealFeature;
    public final ResourceKey<ConfiguredFeature<?, ?>> configuredLeavesBonemealFeature;

    public final ResourceKey<PlacedFeature> placedTreeFeature;

    public final Supplier<LodestoneWorldParticleType> particle;

    public final Supplier<Block> grassBlock;
    public final Supplier<Item> grassBlockItem;

    public final Supplier<Block> leavesBlock;
    public final Supplier<Item> leavesBlockItem;

    public final Supplier<Block> hangingLeavesBlock;
    public final Supplier<Item> hangingLeavesBlockItem;

    public final Supplier<Block> saplingBlock;
    public final Supplier<Item> saplingBlockItem;

    public final Supplier<Block> flowerBlock;
    public final Supplier<Item> flowerBlockItem;

    public final Supplier<Block> floraBlock;
    public final Supplier<Item> floraBlockItem;

    public final Supplier<Item> fruitItem;
    public final Supplier<Item> candyItem;
    public final Supplier<Item> pastryItem;


    public MineralFloraRegistryBundle(ResourceLocation id, Holder<MobEffect> effect, Color color, Block oreBlock, TagKey<Block> tag) {
        this.id = id;
        var name = id.getPath();
        this.oreBlock = oreBlock;

        configuredTreeFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_tree"));
        configuredFlowerFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_plant"));
        configuredNaturalPatchFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_patch"));
        configuredGrassBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_grass_bonemeal"));
        configuredLeavesBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_leaves_bonemeal"));

        placedTreeFeature = ResourceKey.create(Registries.PLACED_FEATURE, MeadowMod.meadowPath(name + "_tree"));

        particle = MeadowParticleRegistry.PARTICLES.register(name + "_leaf", LodestoneWorldParticleType::new);

        var grassBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_grass_bonemeal"));
        var leavesBonemealFeature = ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowPath(name + "_leaves_bonemeal"));

        var grassBlockProperties = MineralBlockProperties.MINERAL_GRASS_BLOCK_PROPERTIES();
        var grassProperties = MineralBlockProperties.MINERAL_GRASS_PROPERTIES();
        var saplingProperties = MineralBlockProperties.MINERAL_SAPLING_PROPERTIES();
        var flowerProperties = MineralBlockProperties.MINERAL_FLOWER_PROPERTIES();
        var hangingLeavesProperties = MineralBlockProperties.HANGING_MINERAL_LEAVES_PROPERTIES();
        var leavesProperties = MineralBlockProperties.MINERAL_LEAVES_PROPERTIES();

        var itemProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES();

        var treeGrower = MeadowTreeGrowers.register(name, configuredTreeFeature);
        String grassBlockName = name + "_grass_block";
        String leavesName = name + "_leaves";
        String hangingLeavesName = "hanging_" + name + "_leaves";
        String saplingName = name + "_sapling";
        String flowerName = name + "_flower";
        String floraName = name + "_flora";

        String fruitName = name + "_fruit";
        String candy = name + "_candy";
        String pastry = name + "_pastry";

        grassBlock = registerBlock(grassBlockName, () -> new MineralGrassBlock(grassBlockProperties, grassBonemealFeature));
        grassBlockItem = registerItem(grassBlockName, itemProperties, (p) -> new BlockItem(grassBlock.get(), p));

        leavesBlock = registerBlock(leavesName, () -> new MineralLeavesBlock(leavesProperties, particle, leavesBonemealFeature, color));
        leavesBlockItem = registerItem(leavesName, itemProperties, (p) -> new BlockItem(leavesBlock.get(), p));

        hangingLeavesBlock = registerBlock(hangingLeavesName, () -> new HangingMineralLeavesBlock(hangingLeavesProperties, particle, color));
        hangingLeavesBlockItem = registerItem(hangingLeavesName, itemProperties, (p) -> new BlockItem(hangingLeavesBlock.get(), p));

        saplingBlock = registerBlock(saplingName, () -> new MineralSaplingBlock(treeGrower, saplingProperties, tag));
        saplingBlockItem = registerItem(saplingName, itemProperties, (p) -> new BlockItem(saplingBlock.get(), p));

        flowerBlock = registerBlock(flowerName, () -> new TallMineralFlower(flowerProperties.addTag(BlockTags.TALL_FLOWERS), tag));
        flowerBlockItem = registerItem(flowerName, itemProperties, (p) -> new BlockItem(flowerBlock.get(), p));

        floraBlock = registerBlock(floraName, () -> new MineralFloraPlant(grassProperties, tag));
        floraBlockItem = registerItem(floraName, itemProperties, (p) -> new BlockItem(floraBlock.get(), p));

        var fruitProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES().food(new FoodProperties.Builder().nutrition(4)
                .effect(() -> new MobEffectInstance(effect, 1200, 0), 1f)
                .saturationModifier(0.4f)
                .alwaysEdible().build()
        );
        var candyProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES().food(new FoodProperties.Builder().nutrition(3)
                .effect(() -> new MobEffectInstance(effect, 300, 1), 1f)
                .saturationModifier(0.3f).fast()
                .alwaysEdible().build()
        );
        var pastryProperties = MeadowItemProperties.MINERAL_FLORA_PROPERTIES().food(new FoodProperties.Builder().nutrition(6)
                .effect(() -> new MobEffectInstance(effect, 3000, 0), 1f)
                .saturationModifier(0.45f)
                .alwaysEdible().build()
        );

        fruitItem = registerItem(fruitName, fruitProperties, Item::new);
        candyItem = registerItem(candy, candyProperties, Item::new);
        pastryItem = registerItem(pastry, pastryProperties, Item::new);
    }

    public <T extends Item> Supplier<T> registerItem(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        return MeadowItemRegistry.register(name, properties, function);
    }

    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }
}