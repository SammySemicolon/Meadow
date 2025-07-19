package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RockyHillsBiomeType extends MeadowGroveBiomeType{
    public RockyHillsBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock() {
        return Blocks.TUFF.defaultBlockState();
    }

    @Override
    public BlockState getSurfaceLayerBlock(float depth) {
        if (depth < 0.4f) {
            return Blocks.TUFF.defaultBlockState();
        }
        if (depth < 0.8f) {
            return Blocks.BASALT.defaultBlockState();
        }
        return Blocks.STONE.defaultBlockState();
    }

    @Override
    public GroveFeatureProvider createSurfaceFeatures(RandomSource random) {
        return GroveFeatureProvider.create().build();
    }
}
