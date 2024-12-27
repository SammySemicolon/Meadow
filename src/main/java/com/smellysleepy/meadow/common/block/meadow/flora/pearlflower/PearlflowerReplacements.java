package com.smellysleepy.meadow.common.block.meadow.flora.pearlflower;

import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

import static net.minecraft.world.level.block.DoublePlantBlock.HALF;

public class PearlflowerReplacements {

    protected static final HashMap<Block, Block> FLOWER_CONVERSION = new HashMap<>();

    public static boolean performExchange(Block block, Level pLevel, BlockPos pPos, BlockState pState) {
        if (block instanceof TallPearlFlowerBlock tallPearlFlowerBlock) {
            return performExchange(tallPearlFlowerBlock, pLevel, pPos, pState);
        }
        if (block instanceof PearlFlowerBlock pearlFlowerBlock) {
            return performExchange(pearlFlowerBlock, pLevel, pPos);
        }
        return false;
    }
    public static boolean performExchange(TallPearlFlowerBlock block, Level pLevel, BlockPos pPos, BlockState pState) {
        BlockPos bottom = pState.getValue(HALF).equals(DoubleBlockHalf.UPPER) ? pPos.below() : pPos;
        TallPearlFlowerBlock newFlower = PearlflowerReplacements.getBlockForExchange(block);
        if (newFlower != null) {
            pLevel.setBlock(bottom, Blocks.AIR.defaultBlockState(), 16);
            pLevel.setBlock(bottom.above(), Blocks.AIR.defaultBlockState(), 16);
            DoublePlantBlock.placeAt(pLevel, newFlower.defaultBlockState(), bottom, 3);
            return true;
        }
        return false;
    }

    public static boolean performExchange(PearlFlowerBlock block, Level pLevel, BlockPos pPos) {
        PearlFlowerBlock newFlower = PearlflowerReplacements.getBlockForExchange(block);
        if (newFlower != null) {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 16);
            pLevel.setBlock(pPos, newFlower.defaultBlockState(), 3);
            return true;
        }
        return false;
    }

    public static TallPearlFlowerBlock getBlockForExchange(TallPearlFlowerBlock block) {
        setupConversions();
        return (TallPearlFlowerBlock) FLOWER_CONVERSION.get(block);
    }

    public static PearlFlowerBlock getBlockForExchange(PearlFlowerBlock block) {
        setupConversions();
        return (PearlFlowerBlock) FLOWER_CONVERSION.get(block);
    }

    public static void setupConversions() {
        if (FLOWER_CONVERSION.isEmpty()) {
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_WILTED_GRASSY_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_WILTED_MARINE_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_WILTED_CALCIFIED_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_ROCKY_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_WILTED_ROCKY_PEARLFLOWER.get());

            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_WILTED_GRASSY_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_GRASSY_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_WILTED_MARINE_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_MARINE_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_WILTED_CALCIFIED_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_CALCIFIED_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.TALL_WILTED_ROCKY_PEARLFLOWER.get(), MeadowBlockRegistry.TALL_ROCKY_PEARLFLOWER.get());

            FLOWER_CONVERSION.put(MeadowBlockRegistry.GRASSY_PEARLFLOWER.get(), MeadowBlockRegistry.WILTED_GRASSY_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.MARINE_PEARLFLOWER.get(), MeadowBlockRegistry.WILTED_MARINE_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get(), MeadowBlockRegistry.WILTED_CALCIFIED_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.ROCKY_PEARLFLOWER.get(), MeadowBlockRegistry.WILTED_ROCKY_PEARLFLOWER.get());

            FLOWER_CONVERSION.put(MeadowBlockRegistry.WILTED_GRASSY_PEARLFLOWER.get(), MeadowBlockRegistry.GRASSY_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.WILTED_MARINE_PEARLFLOWER.get(), MeadowBlockRegistry.MARINE_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.WILTED_CALCIFIED_PEARLFLOWER.get(), MeadowBlockRegistry.CALCIFIED_PEARLFLOWER.get());
            FLOWER_CONVERSION.put(MeadowBlockRegistry.WILTED_ROCKY_PEARLFLOWER.get(), MeadowBlockRegistry.ROCKY_PEARLFLOWER.get());
        }
    }
}