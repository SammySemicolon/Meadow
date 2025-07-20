package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class RockyHillsBiomeType extends MeadowGroveBiomeType{
    public RockyHillsBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        super(id, spawnsNaturally, weight);
    }

    @Override
    public BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler) {
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
    public void createSurfaceFeatures(RandomSource random, GroveFeatureProvider.GroveFeatureProviderBuilder builder) {
        int rockCount = random.nextInt(4, 12);

        int gravelDiscCount = RandomHelper.randomBetween(random, Easing.SINE_IN, 2, 8);
        int blackstoneBlobCount = RandomHelper.randomBetween(random, Easing.SINE_OUT, 1, 4);

        int basaltColumnCount = RandomHelper.randomBetween(random, Easing.EXPO_IN, 0, 8);


        builder.addFeature(MiscOverworldFeatures.FOREST_ROCK, rockCount);

        builder.addFeature(MiscOverworldFeatures.DISK_GRAVEL, gravelDiscCount);
        builder.addFeature(NetherFeatures.BLACKSTONE_BLOBS, blackstoneBlobCount);

        builder.addFeature(NetherFeatures.SMALL_BASALT_COLUMNS, basaltColumnCount);
    }
}
