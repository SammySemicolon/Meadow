package com.smellysleepy.meadow.data.block;

import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.TallPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.wilted.WiltedPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.pearlflower.wilted.WiltedTallPearlFlowerBlock;
import com.smellysleepy.meadow.common.block.mineral.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.block.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry.BLOCKS;
import static team.lodestar.lodestone.helpers.DataHelper.*;

public class MeadowBlockLootTableDatagen extends LootTableProvider {

    private static final Function<Block, LootItemCondition.Builder> IS_UPPER_PART = Util.memoize(b -> AllOfCondition.allOf(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)),
            LocationCheck.checkLocation(LocationPredicate.Builder.location()
                            .setBlock(BlockPredicate.Builder.block()
                                    .of(b)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
                            ),
                    new BlockPos(0, -1, 0)
            )
    ));

    private static final Function<Block, LootItemCondition.Builder> IS_LOWER_PART = Util.memoize(b -> AllOfCondition.allOf(
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)),
            LocationCheck.checkLocation(LocationPredicate.Builder.location()
                            .setBlock(BlockPredicate.Builder.block()
                                    .of(b)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))
                            ),
                    new BlockPos(0, 1, 0)
            )
    ));
    private static final float[] SAPLING_DROP_CHANCE = new float[]{0.015F, 0.0225F, 0.033333336F, 0.05F};
    private static final float[] FRUIT_DROP_CHANCE = new float[]{0.025F, 0.03125F, 0.4375F, 0.05F};
    private static final float[] STICK_DROP_CHANCE = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F};

    public MeadowBlockLootTableDatagen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(pOutput, Set.of(), List.of(
                new SubProviderEntry(BlocksLoot::new, LootContextParamSets.BLOCK)
        ), provider);
    }

    public static class BlocksLoot extends BlockLootSubProvider {

        protected BlocksLoot(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }

        @Override
        protected void generate() {
            Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BLOCKS.getEntries());


            takeAll(blocks, b -> {
                if (b.get().properties() instanceof LodestoneBlockProperties properties) {
                    return properties.getDatagenData().noLootDatagen;
                }
                return true;
            });
            for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
                add(take(blocks, bundle.grassBlock).get(), b -> createSingleItemTableWithSilkTouch(b, Items.DIRT));
                add(take(blocks, bundle.leavesBlock).get(), b -> createFruitLeavesDrops(b, bundle.saplingBlock.get(), bundle.fruitItem.get()));
                add(take(blocks, bundle.hangingLeavesBlock).get(), this::createSilkTouchOnlyTable);
            }
            add(take(blocks, MeadowBlockRegistry.ASPEN_LEAVES).get(), b -> createAspenLeavesDrop(b, MeadowBlockRegistry.ASPEN_SAPLING.get(), MeadowBlockRegistry.SMALL_ASPEN_SAPLING.get()));
            add(take(blocks, MeadowBlockRegistry.HANGING_ASPEN_LEAVES).get(), b -> createConditionalDrop(b, hasShearsOrSilkTouch()));
            add(take(blocks, MeadowBlockRegistry.ASPEN_GRASS_BLOCK).get(), b -> createSingleItemTableWithSilkTouch(b, Items.DIRT));

            takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> add(b.get(), createSingleItemTable(b.get())));

            takeAll(blocks, b -> b.get() instanceof WiltedTallPearlFlowerBlock).forEach(b -> add(b.get(), createTallPearlflowerDrop(b.get(), Items.STICK)));
            takeAll(blocks, b -> b.get() instanceof WiltedPearlFlowerBlock).forEach(b -> add(b.get(), createPearlflowerDrop(b.get(), Items.STICK)));
            takeAll(blocks, b -> b.get() instanceof TallPearlFlowerBlock).forEach(b -> add(b.get(), createTallPearlflowerDrop(b.get(), MeadowItemRegistry.PEARLFLOWER_BUD.get())));
            takeAll(blocks, b -> b.get() instanceof PearlFlowerBlock).forEach(b -> add(b.get(), createPearlflowerDrop(b.get(), MeadowItemRegistry.PEARLFLOWER_BUD.get())));

            takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> add(b.get(), createTallPlantDrop(b.get())));
            takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> add(b.get(), createConditionalDrop(b.get(), hasShearsOrSilkTouch())));

            takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
            takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> add(b.get(), createSlabItemTable(b.get())));
            takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> add(b.get(), createDoorTable(b.get())));

            takeAll(blocks, b -> true).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));
        }

        protected LootTable.Builder createTallPlantDrop(Block block) {
            var upperCondition = IS_UPPER_PART.apply(block);
            var lowerCondition = IS_LOWER_PART.apply(block);

            return LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(hasShearsOrSilkTouch(), upperCondition),
                                    AllOfCondition.allOf(hasShearsOrSilkTouch(), lowerCondition)
                            )));
        }

        protected LootTable.Builder createTallPearlflowerDrop(Block block, Item drop) {
            var upperCondition = IS_UPPER_PART.apply(block);
            var lowerCondition = IS_LOWER_PART.apply(block);

            return LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(drop))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(doesNotHaveShearsOrSilkTouch(), upperCondition),
                                    AllOfCondition.allOf(doesNotHaveShearsOrSilkTouch(), lowerCondition)
                            )).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block))
                            .when(AnyOfCondition.anyOf(
                                    AllOfCondition.allOf(hasShearsOrSilkTouch(), upperCondition),
                                    AllOfCondition.allOf(hasShearsOrSilkTouch(), lowerCondition)
                            )));
        }

        protected LootTable.Builder createPearlflowerDrop(Block block, Item drop) {
            return createSilkTouchOrShearsDispatchTable(block,
                    LootItem.lootTableItem(drop)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));
        }

        protected LootTable.Builder createFruitLeavesDrops(Block block, Block sapling, Item fruit) {
            var fortune = registries.lookup(Registries.ENCHANTMENT).orElseThrow().getOrThrow(Enchantments.FORTUNE);
            return createLeavesDrops(block, sapling)
                    .withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(fruit).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                    .when(doesNotHaveShearsOrSilkTouch())
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune, FRUIT_DROP_CHANCE))

                    );
        }

        protected LootTable.Builder createAspenLeavesDrop(Block block, Block bigSapling, Block smallSapling) {
            var fortune = registries.lookup(Registries.ENCHANTMENT).orElseThrow().getOrThrow(Enchantments.FORTUNE);
            return createLeavesDrops(block, bigSapling)
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(smallSapling))
                            .when(doesNotHaveShearsOrSilkTouch())
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune, SAPLING_DROP_CHANCE))
                    );
        }

        protected LootTable.Builder createLeavesDrops(Block block, Block sapling) {
            var fortune = registries.lookup(Registries.ENCHANTMENT).orElseThrow().getOrThrow(Enchantments.FORTUNE);
            return createConditionalDropWithFallback(block, hasShearsOrSilkTouch(),
                    LootItem.lootTableItem(sapling)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune, SAPLING_DROP_CHANCE))
            ).withPool(
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .when(doesNotHaveShearsOrSilkTouch())
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune, STICK_DROP_CHANCE))

            );
        }

        protected LootTable.Builder createConditionalDrop(Block block, LootItemCondition.Builder condition) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(condition)));
        }

        protected LootTable.Builder createConditionalDropWithFallback(Block block, LootItemCondition.Builder condition, LootPoolEntryContainer.Builder<?> fallback) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(condition).otherwise(fallback)));
        }
    }
}