package com.smellysleepy.meadow.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PearlFlowerConfiguration(BlockStateProvider grassy, BlockStateProvider rocky, BlockStateProvider marine, BlockStateProvider calcified) implements FeatureConfiguration {
   public static final Codec<PearlFlowerConfiguration> CODEC = RecordCodecBuilder.create(config -> config.group(
                   BlockStateProvider.CODEC.fieldOf("grassy").forGetter(PearlFlowerConfiguration::grassy),
                   BlockStateProvider.CODEC.fieldOf("rocky").forGetter(PearlFlowerConfiguration::rocky),
                   BlockStateProvider.CODEC.fieldOf("marine").forGetter(PearlFlowerConfiguration::marine),
                   BlockStateProvider.CODEC.fieldOf("calcified").forGetter(PearlFlowerConfiguration::calcified))
           .apply(config, PearlFlowerConfiguration::new));
}