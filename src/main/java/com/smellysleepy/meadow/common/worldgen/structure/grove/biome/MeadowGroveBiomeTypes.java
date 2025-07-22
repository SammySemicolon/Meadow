package com.smellysleepy.meadow.common.worldgen.structure.grove.biome;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification.BloomingCalcificationBiomeType;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.calcification.WildCalcificationBiomeType;
import net.minecraft.resources.ResourceLocation;

public class MeadowGroveBiomeTypes {

    public static final MeadowGroveBiomeType MEADOW_FOREST = create("meadow_forest").build(AspenForestBiomeType::new);
    public static final MeadowGroveBiomeType ROCKY_HILLS = create("rocky_hills").build(RockyHillsBiomeType::new);
    public static final MeadowGroveBiomeType FUNGAL_SHELVES = create("fungal_shelves").setWeight(0.8f).build(FungalGrowthBiomeType::new);

    public static final MeadowGroveBiomeType BLOOMING_CALCIFICATION = create("blooming_calcification").spawnsNaturally(false).setWeight(0.2f).build(BloomingCalcificationBiomeType::new);
    public static final MeadowGroveBiomeType WILD_CALCIFICATION = create("wild_calcification").spawnsNaturally(false).build(WildCalcificationBiomeType::new);

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
