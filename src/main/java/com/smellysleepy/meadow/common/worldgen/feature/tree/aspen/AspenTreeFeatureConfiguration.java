package com.smellysleepy.meadow.common.worldgen.feature.tree.aspen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.worldgen.feature.tree.AbstractCalcifiedTreeFeatureConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class AspenTreeFeatureConfiguration extends AbstractCalcifiedTreeFeatureConfiguration {

    public static final Codec<AspenTreeFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("sapling").forGetter(AspenTreeFeatureConfiguration::getSapling),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("log").forGetter(AspenTreeFeatureConfiguration::getLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("partiallyCalcifiedLog").forGetter(AspenTreeFeatureConfiguration::getPartiallyCalcifiedLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("calcifiedLog").forGetter(AspenTreeFeatureConfiguration::getCalcifiedLog),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("leaves").forGetter(AspenTreeFeatureConfiguration::getLeaves),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("hangingLeaves").forGetter(AspenTreeFeatureConfiguration::getHangingLeaves))
                    .apply(inst, AspenTreeFeatureConfiguration::new));

    protected final Block leaves;
    protected final Block hangingLeaves;

    public AspenTreeFeatureConfiguration(Block sapling, Block log, Block partiallyCalcifiedLog, Block calcifiedLog, Block leaves, Block hangingLeaves) {
        super(sapling, log, partiallyCalcifiedLog, calcifiedLog);
        this.leaves = leaves;
        this.hangingLeaves = hangingLeaves;
    }

    public Block getLeaves() {
        return leaves;
    }

    public Block getHangingLeaves() {
        return hangingLeaves;
    }
}
