package com.smellysleepy.meadow.common.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineralFeatureDistribution {

    public static final List<MineralFeatureDistribution> WEIGHTED_FEATURE_LIST = new ArrayList<>(
            List.of(
                    new MineralFeatureDistribution("coal", OreFeatures.ORE_COAL, 6),
                    new MineralFeatureDistribution("lapis", OreFeatures.ORE_LAPIS, 3),
                    new MineralFeatureDistribution("redstone", OreFeatures.ORE_REDSTONE, 4),
                    new MineralFeatureDistribution("copper", OreFeatures.ORE_COPPPER_SMALL, 5),
                    new MineralFeatureDistribution("iron", OreFeatures.ORE_IRON, 3),
                    new MineralFeatureDistribution("gold", OreFeatures.ORE_GOLD, 3),
                    new MineralFeatureDistribution("emerald", OreFeatures.ORE_EMERALD, 2)
//                    new MineralFeatureDistribution(MeadowConfiguredFeatureRegistry.CONFIGURED_DIAMOND_TREE, 2)
            )
    );

    public final ResourceKey<ConfiguredFeature<?, ?>> tree;
    public final ResourceKey<ConfiguredFeature<?, ?>> plant;
    public final ResourceKey<ConfiguredFeature<?, ?>> patch;

    public final ResourceKey<ConfiguredFeature<?, ?>> ore;
    //    public final ResourceKey<ConfiguredFeature<?, ?>> patch;

    public final int weight;

    public MineralFeatureDistribution(String prefix, ResourceKey<ConfiguredFeature<?, ?>> ore, int weight) {
        this(ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_tree")),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_plant")),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, MeadowMod.meadowModPath(prefix + "_patch")),
                ore, weight);
    }
    public MineralFeatureDistribution(ResourceKey<ConfiguredFeature<?, ?>> tree, ResourceKey<ConfiguredFeature<?, ?>> plant,
                                      ResourceKey<ConfiguredFeature<?, ?>> patch, ResourceKey<ConfiguredFeature<?, ?>> ore, int weight) {
        this.tree = tree;
        this.plant = plant;
        this.ore = ore;
        this.patch = patch;
        this.weight = weight;
    }

    public static MineralFeatureDistribution getRandomWeightedMineralFeatureDistributor() {
        int totalWeight = WEIGHTED_FEATURE_LIST.stream().mapToInt(MineralFeatureDistribution::getWeight).sum();
        Random random = new Random();
        int randomValue = random.nextInt(totalWeight);

        for (MineralFeatureDistribution entry : WEIGHTED_FEATURE_LIST) {
            randomValue -= entry.weight;
            if (randomValue < 0) {
                return entry;
            }
        }

        // This point should never be reached if the weights are positive
        throw new IllegalStateException("Random selection failed due to an unexpected error.");
    }


    public int getWeight() {
        return weight;
    }
}
