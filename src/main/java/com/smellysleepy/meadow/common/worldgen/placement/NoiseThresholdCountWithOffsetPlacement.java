package com.smellysleepy.meadow.common.worldgen.placement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.placement.*;

public class NoiseThresholdCountWithOffsetPlacement extends RepeatingPlacement {
   public static final Codec<NoiseThresholdCountWithOffsetPlacement> CODEC = RecordCodecBuilder.create((obj) -> obj.group(
           Codec.DOUBLE.fieldOf("noise_level").forGetter((placement) -> placement.noiseLevel),
           Codec.INT.fieldOf("below_noise").forGetter((placement) -> placement.belowNoise),
           Codec.INT.fieldOf("above_noise").forGetter((placement) -> placement.aboveNoise),
           Codec.INT.fieldOf("noise_offset").forGetter((placement) -> placement.noiseOffset)
   ).apply(obj, NoiseThresholdCountWithOffsetPlacement::new));
   private final double noiseLevel;
   private final int belowNoise;
   private final int aboveNoise;
   private final int noiseOffset;

   private NoiseThresholdCountWithOffsetPlacement(double noiseLevel, int belowNoise, int aboveNoise, int noiseOffset) {
      this.noiseLevel = noiseLevel;
      this.belowNoise = belowNoise;
      this.aboveNoise = aboveNoise;
      this.noiseOffset = noiseOffset;
   }

   public static NoiseThresholdCountWithOffsetPlacement of(double pNoiseLevel, int pBelowNoise, int pAboveNoise, int noiseOffset) {
      return new NoiseThresholdCountWithOffsetPlacement(pNoiseLevel, pBelowNoise, pAboveNoise, noiseOffset);
   }

   protected int count(RandomSource pRandom, BlockPos pPos) {
      double d0 = Biome.BIOME_INFO_NOISE.getValue((double)(pPos.getX() + noiseOffset) / 200.0D, (double)(pPos.getZ() + noiseOffset) / 200.0D, false);
      return d0 < this.noiseLevel ? this.belowNoise : this.aboveNoise;
   }

   public PlacementModifierType<?> type() {
      return MeadowPlacementFillerRegistry.NOISE_WITH_OFFSET;
   }
}