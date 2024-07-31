package com.smellysleepy.meadow.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PearlFlowerConfiguration(BlockStateProvider meadow, BlockStateProvider stone, BlockStateProvider marine, BlockStateProvider calcified) implements FeatureConfiguration {
   public static final Codec<PearlFlowerConfiguration> CODEC = RecordCodecBuilder.create(config -> config.group(
                   BlockStateProvider.CODEC.fieldOf("meadow").forGetter(PearlFlowerConfiguration::meadow),
                   BlockStateProvider.CODEC.fieldOf("stone").forGetter(PearlFlowerConfiguration::stone),
                   BlockStateProvider.CODEC.fieldOf("marine").forGetter(PearlFlowerConfiguration::marine),
                   BlockStateProvider.CODEC.fieldOf("calcified").forGetter(PearlFlowerConfiguration::calcified))
           .apply(config, PearlFlowerConfiguration::new));
}