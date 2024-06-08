package com.smellysleepy.meadow.common.worldgen.strange_plant;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.tree.small.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class StrangePlantFeatureConfiguration implements FeatureConfiguration {

    public static final Codec<StrangePlantFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plant").forGetter(obj -> obj.plant),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ore").forGetter(obj -> obj.ore),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("foliage").forGetter(obj -> obj.foliage),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("primaryDecorator").forGetter(obj -> obj.primaryDecorator),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("secondaryDecorator").forGetter(obj -> obj.secondaryDecorator))
                    .apply(inst, StrangePlantFeatureConfiguration::new));

    public final Block plant;
    public final Block ore;
    public final Block foliage;
    public final Block primaryDecorator;
    public final Block secondaryDecorator;

    public StrangePlantFeatureConfiguration(Block plant, Block ore, Block foliage, Block primaryDecorator, Block secondaryDecorator) {
        this.plant = plant;
        this.ore = ore;
        this.foliage = foliage;
        this.primaryDecorator = primaryDecorator;
        this.secondaryDecorator = secondaryDecorator;
    }
}