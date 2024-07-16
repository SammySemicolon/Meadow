package com.smellysleepy.meadow.common.worldgen.tree.meadow;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class MeadowTreeFeatureConfiguration implements FeatureConfiguration {

    public static final Codec<MeadowTreeFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("sapling").forGetter(obj -> obj.sapling),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("log").forGetter(obj -> obj.log),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("leaves").forGetter(obj -> obj.leaves),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fancyLeaves").forGetter(obj -> obj.fancyLeaves),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("hangingLeaves").forGetter(obj -> obj.hangingLeaves))
                    .apply(inst, MeadowTreeFeatureConfiguration::new));

    public final Block sapling;
    public final Block log;
    public final Block leaves;
    public final Block fancyLeaves;
    public final Block hangingLeaves;

    public MeadowTreeFeatureConfiguration(Block sapling, Block log, Block leaves, Block fancyLeaves, Block hangingLeaves) {
        this.sapling = sapling;
        this.log = log;
        this.leaves = leaves;
        this.fancyLeaves = fancyLeaves;
        this.hangingLeaves = hangingLeaves;
    }
}
