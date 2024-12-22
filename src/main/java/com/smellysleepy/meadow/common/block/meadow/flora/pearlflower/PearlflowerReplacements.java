package com.smellysleepy.meadow.common.block.meadow.flora.pearlflower;

import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class PearlflowerReplacements {

    protected static final HashMap<Block, Block> FLOWER_CONVERSION = new HashMap<>();

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