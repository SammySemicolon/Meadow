package com.smellysleepy.meadow.common.worldgen.feature.calcification;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PointyCalcificationConfiguration(BlockStateProvider stalagmiteProvider, boolean growsUpwards, int minSize,
                                               int maxSize, int minHeight,
                                               int maxHeight) implements FeatureConfiguration {

    public static final Codec<PointyCalcificationConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BlockStateProvider.CODEC.fieldOf("stalagmiteProvider").forGetter(obj -> obj.stalagmiteProvider),
                            Codec.BOOL.fieldOf("growsUpwards").forGetter(obj -> obj.growsUpwards),
                            Codec.INT.fieldOf("minSize").forGetter(obj -> obj.minSize),
                            Codec.INT.fieldOf("maxSize").forGetter(obj -> obj.maxSize),
                            Codec.INT.fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.INT.fieldOf("maxHeight").forGetter(obj -> obj.maxHeight))
                    .apply(inst, PointyCalcificationConfiguration::new)
            );

}