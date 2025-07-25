package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;

import javax.annotation.*;
import java.util.*;

public final class DataEntry {

    public static final Codec<DataEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(DataEntry::getBlockX),
            Codec.INT.fieldOf("z").forGetter(DataEntry::getBlockZ),
            MeadowGroveBiomeType.CODEC.fieldOf("biomeType").forGetter(DataEntry::biomeType),
            Codec.DOUBLE.fieldOf("biomeInfluence").forGetter(DataEntry::biomeInfluence),
            Codec.INT.fieldOf("centerOffset").forGetter(DataEntry::getCenterOffset),
            Codec.INT.fieldOf("height").forGetter(DataEntry::getHeight),
            Codec.INT.fieldOf("depth").forGetter(DataEntry::getDepth),
            Codec.INT.fieldOf("openHeight").forGetter(DataEntry::getOpenHeight),
            Codec.INT.fieldOf("openDepth").forGetter(DataEntry::getOpenDepth),
            InclineData.OPTIONAL.fieldOf("inclineData").forGetter(DataEntry::getInclineData)
    ).apply(instance, (x, z, biomeType, biomeInfluence, centerOffset, height, depth, openHeight, openDepth, inclineData) -> new DataEntry(x, z, biomeType, biomeInfluence, centerOffset, height, depth, openHeight, openDepth, inclineData.orElse(null))));

    private final int blockX;
    private final int blockZ;
    private final DataCoordinate dataCoordinate;
    private final MeadowGroveBiomeType biomeType;
    private final double biomeInfluence;
    private final int centerOffset;

    private int height;
    private int depth;
    private int openHeight;
    private int openDepth;
    private InclineData inclineData;

    public DataEntry(int blockX, int blockZ, MeadowGroveBiomeType biomeType, double biomeInfluence, int centerOffset, int height, int depth, int openHeight, int openDepth, @Nullable InclineData inclineData) {
        this.blockX = blockX;
        this.blockZ = blockZ;
        this.dataCoordinate = new DataCoordinate(blockX, blockZ);
        this.biomeType = biomeType;
        this.biomeInfluence = biomeInfluence;
        this.centerOffset = centerOffset;
        this.height = height;
        this.depth = depth;
        this.openHeight = openHeight;
        this.openDepth = openDepth;
        this.inclineData = inclineData;
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public DataCoordinate getDataCoordinate() {
        return dataCoordinate;
    }

    public MeadowGroveBiomeType biomeType() {
        return biomeType;
    }

    public double biomeInfluence() {
        return biomeInfluence;
    }

    public int getCenterOffset() {
        return centerOffset;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getOpenHeight() {
        return openHeight;
    }

    public int getOpenDepth() {
        return openDepth;
    }

    public boolean isOpen() {
        return openHeight > 0 || openDepth > 0;
    }

    public Optional<InclineData> getInclineData() {
        return Optional.ofNullable(inclineData);
    }

    public InclineData setInclineData(InclineData inclineData) {
        this.inclineData = inclineData;
        return inclineData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DataEntry) obj;
        return this.blockX == that.blockX &&
                this.blockZ == that.blockZ &&
                this.height == that.height &&
                this.depth == that.depth &&
                Objects.equals(this.inclineData, that.inclineData) &&
                Objects.equals(this.biomeType, that.biomeType) &&
                Double.doubleToLongBits(this.biomeInfluence) == Double.doubleToLongBits(that.biomeInfluence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockX, blockZ, height, depth, inclineData, biomeType, biomeInfluence);
    }

    @Override
    public String toString() {
        return "DataEntry[" +
                "blockX=" + blockX + ", " +
                "blockZ=" + blockZ + ", " +
                "height=" + height + ", " +
                "depth=" + depth + ", " +
                "inclineData=" + inclineData + ", " +
                "biomeType=" + biomeType + ", " +
                "biomeInfluence=" + biomeInfluence + ']';
    }


}
