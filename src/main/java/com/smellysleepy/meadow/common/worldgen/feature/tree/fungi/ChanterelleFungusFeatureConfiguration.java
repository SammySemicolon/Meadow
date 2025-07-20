package com.smellysleepy.meadow.common.worldgen.feature.tree.fungi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.feature.tree.AbstractCalcifiedTreeFeatureConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class ChanterelleFungusFeatureConfiguration extends AbstractCalcifiedTreeFeatureConfiguration {

    public static final Codec<ChanterelleFungusFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("sapling").forGetter(ChanterelleFungusFeatureConfiguration::getSapling),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("log").forGetter(ChanterelleFungusFeatureConfiguration::getLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("partiallyCalcifiedLog").forGetter(ChanterelleFungusFeatureConfiguration::getPartiallyCalcifiedLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("calcifiedLog").forGetter(ChanterelleFungusFeatureConfiguration::getCalcifiedLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fungalCrown").forGetter(ChanterelleFungusFeatureConfiguration::getFungalCrown))
                    .apply(inst, ChanterelleFungusFeatureConfiguration::new));

    protected final Block fungalCrown;

    public ChanterelleFungusFeatureConfiguration(Block sapling, Block log, Block partiallyCalcifiedLog, Block calcifiedLog, Block fungalCrown) {
        super(sapling, log, partiallyCalcifiedLog, calcifiedLog);
        this.fungalCrown = fungalCrown;
    }

    public Block getFungalCrown() {
        return fungalCrown;
    }
}
