package com.smellysleepy.meadow.common.worldgen.feature;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.block.mineral_flora.MineralFloraRegistryBundle;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class StrangePlantFeatureConfiguration implements FeatureConfiguration {

    public static final Codec<StrangePlantFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plant").forGetter(obj -> obj.flower),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("foliage").forGetter(obj -> obj.grass),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(obj -> obj.block),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ore").forGetter(obj -> obj.ore))
                    .apply(inst, StrangePlantFeatureConfiguration::new));

    public final Block flower;
    public final Block grass;
    public final Block block;
    public final Block ore;

    public StrangePlantFeatureConfiguration(MineralFloraRegistryBundle bundle, Block ore) {
        this(bundle.flowerBlock.get(), bundle.floraBlock.get(), bundle.grassBlock.get(), ore);
    }
    public StrangePlantFeatureConfiguration(Block flower, Block grass, Block block, Block ore) {
        this.flower = flower;
        this.grass = grass;
        this.block = block;
        this.ore = ore;
    }
}