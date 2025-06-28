package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.*;
import net.minecraft.resources.*;

import java.util.*;

public class MeadowGroveBiomeType {

    public static final HashMap<ResourceLocation, MeadowGroveBiomeType> BIOME_TYPES = new HashMap<>();

    public static final MeadowGroveBiomeType MEADOW_FOREST = new MeadowGroveBiomeType("meadow_forest");
    public static final MeadowGroveBiomeType ROCKY_HILLS = new MeadowGroveBiomeType("rocky_hills");
    public static final MeadowGroveBiomeType FUNGUS_SHELVES = new MeadowGroveBiomeType("fungus_shelves");
    public static final MeadowGroveBiomeType CALCIFIED_OUTSKIRTS = new MeadowGroveBiomeType("blooming_calcification");
    public static final MeadowGroveBiomeType CAVERNOUS_CALCIFICATION = new MeadowGroveBiomeType("cavernous_calcification", false);

    protected final ResourceLocation id;
    protected final boolean spawnsNaturally;
    protected final int seed;

    public MeadowGroveBiomeType(String id) {
        this(id, true);
    }
    public MeadowGroveBiomeType(String id, boolean spawnsNaturally) {
        this(MeadowMod.meadowModPath(id), spawnsNaturally);
    }

    public MeadowGroveBiomeType(ResourceLocation id, boolean spawnsNaturally) {
        this.id = id;
        this.spawnsNaturally = spawnsNaturally;
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
}
