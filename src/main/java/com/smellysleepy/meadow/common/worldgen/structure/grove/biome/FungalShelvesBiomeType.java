package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FungalShelvesBiomeType extends MeadowGroveBiomeType {
    public FungalShelvesBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock() {
        return Blocks.MYCELIUM.defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.3f) {
            return Blocks.DIRT.defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public GroveFeatureProvider createSurfaceFeatures(RandomSource random) {
        return GroveFeatureProvider.create().build();
    }
}
