package com.smellysleepy.meadow.common.worldgen;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineralTreeDistribution {

    public static final List<Pair<ResourceKey<ConfiguredFeature<?, ?>>, Integer>> WEIGHTED_TREE_LIST = new ArrayList<>(
            List.of(
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_COAL_TREE, 6),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_LAPIS_TREE, 3),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_REDSTONE_TREE, 4),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_COPPER_TREE, 5),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_IRON_TREE, 3),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_GOLD_TREE, 3),
                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_EMERALD_TREE, 2)
//                    Pair.of(MeadowConfiguredFeatureRegistry.CONFIGURED_DIAMOND_TREE, 2)
            )
    );

    public static ResourceKey<ConfiguredFeature<?, ?>> getRandomWeightedTree() {
        int totalWeight = WEIGHTED_TREE_LIST.stream().mapToInt(Pair::getSecond).sum();
        Random random = new Random();
        int randomValue = random.nextInt(totalWeight);

        for (Pair<ResourceKey<ConfiguredFeature<?, ?>>, Integer> entry : WEIGHTED_TREE_LIST) {
            randomValue -= entry.getSecond();
            if (randomValue < 0) {
                return entry.getFirst();
            }
        }

        // This point should never be reached if the weights are positive
        throw new IllegalStateException("Random selection failed due to an unexpected error.");
    }

}
