package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;

import java.util.*;

import static team.lodestar.lodestone.LodestoneLib.LOGGER;

public class MeadowGroveGenerationConfiguration {

    public static final Codec<MeadowGroveGenerationConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("groveCenter").forGetter(MeadowGroveGenerationConfiguration::getGroveCenter),
            Codec.INT.fieldOf("groveRadius").forGetter(MeadowGroveGenerationConfiguration::getGroveRadius),
            Codec.INT.fieldOf("groveHeight").forGetter(MeadowGroveGenerationConfiguration::getGroveHeight),
            Codec.INT.fieldOf("groveDepth").forGetter(MeadowGroveGenerationConfiguration::getGroveDepth),
            Codec.FLOAT.fieldOf("biomeSize").forGetter(MeadowGroveGenerationConfiguration::getBiomeSize),
            MeadowGroveBiomeType.CODEC.listOf().fieldOf("enabledBiomes").forGetter(MeadowGroveGenerationConfiguration::getEnabledBiomes),
            MeadowGroveGenerationData.CODEC.fieldOf("generationData").forGetter(MeadowGroveGenerationConfiguration::getGenerationData)
    ).apply(instance, MeadowGroveGenerationConfiguration::new));

    private final BlockPos groveCenter;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    private final float biomeSize;

    private final List<MeadowGroveBiomeType> enabledBiomes;
    private final List<MeadowGroveBiomeType> naturalBiomes;

    private final MeadowGroveGenerationData generationData;

    public MeadowGroveGenerationConfiguration(BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth, float biomeSize, List<MeadowGroveBiomeType> enabledBiomes, MeadowGroveGenerationData generationData) {
        this.groveCenter = groveCenter;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveDepth;
        this.biomeSize = biomeSize;
        this.enabledBiomes = enabledBiomes;
        this.naturalBiomes = enabledBiomes.stream().filter(MeadowGroveBiomeType::spawnsNaturally).toList();
        this.generationData = generationData;
    }

    public MeadowGroveGenerationConfiguration(BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth, float biomeSize, List<MeadowGroveBiomeType> enabledBiomes) {
        this(groveCenter, groveRadius, groveHeight, groveDepth, biomeSize, enabledBiomes, new MeadowGroveGenerationData());
    }

    public BlockPos getGroveCenter() {
        return groveCenter;
    }

    public int getGroveRadius() {
        return groveRadius;
    }

    public int getGroveHeight() {
        return groveHeight;
    }

    public int getGroveDepth() {
        return groveDepth;
    }

    public float getBiomeSize() {
        return biomeSize;
    }

    public List<MeadowGroveBiomeType> getEnabledBiomes() {
        return enabledBiomes;
    }

    public List<MeadowGroveBiomeType> getNaturalBiomes() {
        return naturalBiomes;
    }

    public MeadowGroveGenerationData getGenerationData() {
        return generationData;
    }

    public void save(CompoundTag tag) {
        MeadowGroveGenerationConfiguration.CODEC
                .encodeStart(NbtOps.INSTANCE, this)
                .resultOrPartial(LOGGER::error)
                .ifPresent(p -> tag.put("groveData", p));
    }

    public static MeadowGroveGenerationConfiguration load(CompoundTag tag) {
        return MeadowGroveGenerationConfiguration.CODEC.parse(NbtOps.INSTANCE, tag.get("groveData")).resultOrPartial(LOGGER::error).orElse(null);
    }
}