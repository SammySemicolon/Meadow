package com.smellysleepy.meadow.common.worldgen.feature.calcification;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.common.worldgen.feature.PearlFlowerConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class PointyCalcificationConfiguration implements FeatureConfiguration {

    public static final Codec<PointyCalcificationConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BlockStateProvider.CODEC.fieldOf("stalagmiteProvider").forGetter(obj -> obj.stalagmiteProvider),
                            Codec.INT.fieldOf("minSize").forGetter(obj -> obj.minSize),
                            Codec.INT.fieldOf("maxSize").forGetter(obj -> obj.maxSize),
                            Codec.INT.fieldOf("minHeight").forGetter(obj -> obj.minHeight),
                            Codec.INT.fieldOf("maxHeight").forGetter(obj -> obj.maxHeight))
                    .apply(inst, PointyCalcificationConfiguration::new)
            );

    public final BlockStateProvider stalagmiteProvider;
    public final int minSize;
    public final int maxSize;
    public final int minHeight;
    public final int maxHeight;

    public PointyCalcificationConfiguration(BlockStateProvider stalagmiteProvider, int minSize, int maxSize, int minHeight, int maxHeight) {
        this.stalagmiteProvider = stalagmiteProvider;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }
}