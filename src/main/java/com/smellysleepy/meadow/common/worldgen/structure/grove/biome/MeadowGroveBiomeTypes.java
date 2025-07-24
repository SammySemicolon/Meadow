package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification.CreepingCalcificationBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification.DeepCalcificationBiomeType;
import net.minecraft.resources.ResourceLocation;

public class MeadowGroveBiomeTypes {

    public static final MeadowGroveBiomeType MEADOW_FOREST = create("meadow_forest").build(AspenForestBiomeType::new);
    public static final MeadowGroveBiomeType FUNGAL_SHELVES = create("fungal_shelves").setWeight(0.85f).build(FungalGrowthBiomeType::new);
    public static final MeadowGroveBiomeType ROCKY_HILLS = create("rocky_hills").setWeight(0.7f).build(RockyHillsBiomeType::new);

    public static final MeadowGroveBiomeType CREEPING_CALCIFICATION = create("creeping_calcification").spawnsNaturally(false).build(CreepingCalcificationBiomeType::new);
    public static final MeadowGroveBiomeType DEEP_CALCIFICATION = create("deep_calcification").spawnsNaturally(false).build(DeepCalcificationBiomeType::new);

    public static void init() {

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

        public MeadowGroveBiomeType build(BiomeConstructor constructor) {
            return constructor.createBiome(id, spawnsNaturally, weight);
        }

        public interface BiomeConstructor {
            MeadowGroveBiomeType createBiome(ResourceLocation id, boolean spawnsNaturally, float weight);
        }
    }
}
