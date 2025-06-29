package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.synth.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;
import java.util.stream.*;

public class MeadowGroveBiomeType {

    public static final HashMap<ResourceLocation, MeadowGroveBiomeType> BIOME_TYPES = new HashMap<>();

    public static final Codec<MeadowGroveBiomeType> CODEC = ResourceLocation.CODEC
            .xmap(BIOME_TYPES::get, MeadowGroveBiomeType::getId);

    public static final MeadowGroveBiomeType MEADOW_FOREST = create("meadow_forest").build();
    public static final MeadowGroveBiomeType ROCKY_HILLS = create("rocky_hills").build();
    public static final MeadowGroveBiomeType FUNGUS_SHELVES = create("fungus_shelves").setWeight(0.8f).build();

    public static final MeadowGroveBiomeType CALCIFIED_OUTSKIRTS = create("blooming_calcification").setWeight(0.5f).build();
    public static final MeadowGroveBiomeType CAVERNOUS_CALCIFICATION = create("cavernous_calcification").spawnsNaturally(false).build();
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

    private static MeadowGroveBiomeTypeBuilder create(String id) {
        return create(MeadowMod.meadowModPath(id));
    }

    private static MeadowGroveBiomeTypeBuilder create(ResourceLocation id) {
        return new MeadowGroveBiomeTypeBuilder(id);
    }

    public static class MeadowGroveBiomeTypeBuilder {
        private final ResourceLocation id;
        private boolean spawnsNaturally = true;
        private float weight = 1f;

        public MeadowGroveBiomeTypeBuilder(ResourceLocation id) {
            this.id = id;
        }

        public MeadowGroveBiomeTypeBuilder spawnsNaturally(boolean spawnsNaturally) {
            this.spawnsNaturally = spawnsNaturally;
            return this;
        }

        public MeadowGroveBiomeTypeBuilder setWeight(float weight) {
            this.weight = weight;
            return this;
        }

        public MeadowGroveBiomeType build() {
            return new MeadowGroveBiomeType(id, spawnsNaturally, weight);
        }
    }
}