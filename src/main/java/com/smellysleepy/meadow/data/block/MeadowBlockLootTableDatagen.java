package com.smellysleepy.meadow.data.block;

import com.google.common.collect.*;
import com.mojang.datafixers.types.*;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.*;
import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static team.lodestar.lodestone.helpers.DataHelper.*;

public class MeadowBlockLootTableDatagen extends LootTableProvider {

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private static final Function<Block, LootItemCondition.Builder> IS_UPPER_DOUBLE_PLANT = Util.memoize(b -> AllOfCondition.allOf(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)),
            LocationCheck.checkLocation(LocationPredicate.Builder.location()
                    .setBlock(BlockPredicate.Builder.block().of(b)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    private static final Function<Block, LootItemCondition.Builder> IS_LOWER_DOUBLE_PLANT = Util.memoize(b -> AllOfCondition.allOf(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)),
            LocationCheck.checkLocation(LocationPredicate.Builder.location()
                    .setBlock(BlockPredicate.Builder.block().of(b)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0))));




    private static final float[] SAPLING_DROP_CHANCE = new float[]{0.015F, 0.0225F, 0.033333336F, 0.05F};

    public MeadowBlockLootTableDatagen(PackOutput pOutput) {
        super(pOutput, Set.of(), List.of(
                new SubProviderEntry(BlocksLoot::new, LootContextParamSets.BLOCK))
        );
    }

    public static class BlocksLoot extends BlockLootSubProvider {

        protected BlocksLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return MeadowBlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }

        @Override
        protected void generate() {
            Set<RegistryObject<Block>> blocks = new HashSet<>(MeadowBlockRegistry.BLOCKS.getEntries());

            takeAll(blocks, b -> b.get().properties instanceof LodestoneBlockProperties && ((LodestoneBlockProperties) b.get().properties).getDatagenData().hasInheritedLootTable);

            takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));

            takeAll(blocks, b -> b.get() instanceof TallPearlFlowerBlock).forEach(b -> add(b.get(), createTallPearlflowerDrop(b.get())));
            takeAll(blocks, b -> b.get() instanceof PearlFlowerBlock).forEach(b -> add(b.get(), createPearlflowerDrop(b.get())));

            takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> add(b.get(), createTallPlantDrop(b.get())));
            takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouchOrShears(b.get(), b.get().asItem())));

            takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
            takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> add(b.get(), createSlabItemTable(b.get())));
            takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> add(b.get(), createDoorTable(b.get())));

            takeAll(blocks, b -> true).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));
        }

        protected LootTable.Builder createTallPlantDrop(Block block) {
            var upperCondition = IS_UPPER_DOUBLE_PLANT.apply(block);
            var lowerCondition = IS_LOWER_DOUBLE_PLANT.apply(block);

            return LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(HAS_SHEARS_OR_SILK_TOUCH, upperCondition),
                                    AllOfCondition.allOf(HAS_SHEARS_OR_SILK_TOUCH, lowerCondition)
                            )));
        }

        protected LootTable.Builder createTallPearlflowerDrop(Block block) {
            var upperCondition = IS_UPPER_DOUBLE_PLANT.apply(block);
            var lowerCondition = IS_LOWER_DOUBLE_PLANT.apply(block);

            return LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(MeadowItemRegistry.PEARLFLOWER_BUD.get()))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(HAS_NO_SHEARS_OR_SILK_TOUCH, upperCondition),
                                    AllOfCondition.allOf(HAS_NO_SHEARS_OR_SILK_TOUCH, lowerCondition)
                            )).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(HAS_SHEARS_OR_SILK_TOUCH, upperCondition),
                                    AllOfCondition.allOf(HAS_SHEARS_OR_SILK_TOUCH, lowerCondition)
                            )));
        }

        protected LootTable.Builder createPearlflowerDrop(Block block) {
            return createSilkTouchOrShearsDispatchTable(block,
                    LootItem.lootTableItem(MeadowItemRegistry.PEARLFLOWER_BUD.get())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
        }

        protected LootTable.Builder createSingleItemTableWithSilkTouchOrShears(Block block) {
            return createSingleItemTableWithSilkTouchOrShears(block, block.asItem());
        }
        protected LootTable.Builder createSingleItemTableWithSilkTouchOrShears(Block block, ItemLike item) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(item)));
        }
    }
}