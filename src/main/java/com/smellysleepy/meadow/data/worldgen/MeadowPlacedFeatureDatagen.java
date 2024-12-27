package com.smellysleepy.meadow.data.worldgen;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.block.mineral.*;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blockpredicates.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.placement.*;

public class MeadowPlacedFeatureDatagen {

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

        int offset = 0;
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            if (bundle.equals(MineralFloraRegistry.NETHERITE_FLORA) || bundle.equals(MineralFloraRegistry.AMETHYST_FLORA)) {
                continue;
            }
            context.register(bundle.placedTreeFeature,
                    addMineralTreeFeature(features.getOrThrow(bundle.configuredTreeFeature), bundle.saplingBlock.get(), -32, 64,
                            NoiseThresholdCountPlacement.of(0.4D, 0, 40),
                            RarityFilter.onAverageOnceEvery(4)));
            offset += 25000;
        }
    }

    private static PlacedFeature addMineralTreeFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, Block saplingBlock, int minHeight, int maxHeight, PlacementModifier... extraModifiers) {
        var predicate = BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.not(BlockPredicate.replaceable(new BlockPos(0, -1, 0))), BlockPredicate.wouldSurvive(saplingBlock.defaultBlockState(), BlockPos.ZERO));
        var modifiers = ImmutableList.<PlacementModifier>builder().add(
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, predicate, BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())
                .add(extraModifiers)
                .build();
        return new PlacedFeature(configureFeature, modifiers);
    }
}
