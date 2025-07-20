package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.mojang.serialization.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import net.minecraft.resources.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

import java.util.*;

public abstract class MeadowGroveBiomeType {

    public static final HashMap<ResourceLocation, MeadowGroveBiomeType> BIOME_TYPES = new HashMap<>();

    public static final Codec<MeadowGroveBiomeType> CODEC = ResourceLocation.CODEC
            .xmap(BIOME_TYPES::get, MeadowGroveBiomeType::getId);

    protected final ResourceLocation id;

    protected final boolean spawnsNaturally;
    protected final int seed;
    protected final float weight;

    public MeadowGroveBiomeType(ResourceLocation id, boolean spawnsNaturally, float weight) {
        this.id = id;
        this.spawnsNaturally = spawnsNaturally;
        this.weight = weight;
        this.seed = BIOME_TYPES.size() * 10000;
        BIOME_TYPES.put(id, this);
    }

    public final BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler, float depth) {
        if (depth == 0) {
            return getSurfaceBlock(data, noiseSampler);
        }
        return getSurfaceLayerBlock(depth);
    }

    public abstract BlockState getSurfaceBlock(DataEntry data, ImprovedNoise noiseSampler);

    public abstract BlockState getSurfaceLayerBlock(float depth);

    public GroveFeatureProvider createSurfaceFeatures(RandomSource random) {
        GroveFeatureProvider.GroveFeatureProviderBuilder builder = GroveFeatureProvider.create(this);
        createSurfaceFeatures(random, builder);
        return builder.build();
    }

    protected abstract void createSurfaceFeatures(RandomSource random, GroveFeatureProvider.GroveFeatureProviderBuilder builder);

    public ResourceLocation getId() {
        return id;
    }

    public boolean spawnsNaturally() {
        return spawnsNaturally;
    }

    public int getSeed() {
        return seed;
    }

    public float getWeight() {
        return weight;
    }
}