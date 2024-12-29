package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class MeadowGroveFeatureDistribution extends HashMap<ResourceKey<ConfiguredFeature<?, ?>>, MeadowGroveFeatureDistribution.FeatureDistributionData> {

    public static MeadowGroveFeatureDistribution makeDistribution(RandomSource random) {
        int pearlflowerCount = random.nextInt(3, 6);
        int smallTreeCount = random.nextInt(4, 8);
        int largeTreeCount = random.nextInt(12, 24);
        int largePatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 1, 3);
        int mediumPatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 1, 4);
        int smallPatchCount = RandomHelper.randomBetween(random, Easing.SINE_OUT, 1, 5);

        int coralCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 1, 4);
        int mossCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 8, 24);
        int azaleaTreeCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 1, 4);
        int sugarCaneCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 4, 8);
        int lakeGrassCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 2, 6);
        int largeLakePatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 2, 4);
        int mediumLakePatchCount = RandomHelper.randomBetween(random, Easing.SINE_IN_OUT, 2, 5);
        int smallLakePatchCount = RandomHelper.randomBetween(random, Easing.SINE_OUT, 2, 6);

        int largeStalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_IN, 2, 8);
        int stalagmiteCount = RandomHelper.randomBetween(random, Easing.QUAD_OUT, 0, 4);

        MeadowGroveFeatureDistribution featureDistribution = new MeadowGroveFeatureDistribution();
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER, random, pearlflowerCount);

        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE, random, smallTreeCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, random, largeTreeCount);

        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_PATCH, random, largePatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_PATCH, random, mediumPatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH, random, smallPatchCount);

        featureDistribution.addFeature(AquaticFeatures.WARM_OCEAN_VEGETATION, random, coralCount);
        featureDistribution.addFeature(CaveFeatures.MOSS_VEGETATION, random, mossCount);
        featureDistribution.addFeature(TreeFeatures.AZALEA_TREE, random, azaleaTreeCount);
        featureDistribution.addFeature(VegetationFeatures.PATCH_SUGAR_CANE, random, sugarCaneCount);
        featureDistribution.addFeature(VegetationFeatures.PATCH_TALL_GRASS, random, lakeGrassCount);
        featureDistribution.addFeature(VegetationFeatures.PATCH_GRASS, random, lakeGrassCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_LAKE_PATCH, random, largeLakePatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_LAKE_PATCH, random, mediumLakePatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_LAKE_PATCH, random, smallLakePatchCount);

        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, random, largeStalagmiteCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES, random, stalagmiteCount);

        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_PATCH, random, largePatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_PATCH, random, mediumPatchCount);
        featureDistribution.addFeature(MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH, random, smallPatchCount);
        return featureDistribution;
    }

    public void addFeature(ResourceKey<ConfiguredFeature<?, ?>> feature, RandomSource randomSource, int amount) {
        ArrayList<Integer> indexes = new ArrayList<>();

        float step = 256f / amount;
        for (int i = 0; i < amount; i++) {
            int start = Mth.floor(step * i);
            int stop = Mth.floor(step * (i+1));
            indexes.add(randomSource.nextInt(start, stop));
        }
        put(feature, new FeatureDistributionData(indexes));
    }

    public record FeatureDistributionData(ArrayList<Integer> indexes) {
        public boolean canPlace(int blockIndex) {
            return canPlace(blockIndex, 0);
        }

        public boolean canPlace(int blockIndex, int leniency) {
            if (indexes.isEmpty()) {
                return false;
            }
            Integer featureIndex = indexes.get(0);
            boolean canPlace = featureIndex >= ((blockIndex-leniency)%256) && featureIndex <= ((blockIndex+leniency)%256);
            if (canPlace) {
                indexes.remove(0);
            }
            return canPlace;
        }
    }

    public static class FeatureDataGetter implements Predicate<ResourceKey<ConfiguredFeature<?, ?>>> {

        private final MeadowGroveFeatureDistribution distribution;
        private int currentBlockIndex;

        public FeatureDataGetter(MeadowGroveFeatureDistribution distribution) {
            this.distribution = distribution;
        }

        public void nextIndex() {
            currentBlockIndex++;
        }

        @SafeVarargs
        public final ResourceKey<ConfiguredFeature<?, ?>> choose(ResourceKey<ConfiguredFeature<?, ?>>... keys) {
            return choose(0, keys);
        }
        @SafeVarargs
        public final ResourceKey<ConfiguredFeature<?, ?>> choose(int leniency, ResourceKey<ConfiguredFeature<?, ?>>... keys) {
            for (ResourceKey<ConfiguredFeature<?, ?>> key : keys) {
                if (test(key, leniency)) {
                    return key;
                }
            }
            return null;
        }

        @Override
        public boolean test(ResourceKey<ConfiguredFeature<?, ?>> key) {
            return distribution.containsKey(key) && distribution.get(key).canPlace(currentBlockIndex);
        }
        public boolean test(ResourceKey<ConfiguredFeature<?, ?>> key, int leniency) {
            return distribution.containsKey(key) && distribution.get(key).canPlace(currentBlockIndex, leniency);
        }
    }
}
