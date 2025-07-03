package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.move_to_lodestone_later.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class InclineData {

    public static final Codec<InclineData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("isSource").forGetter(InclineData::isSource),
            Codec.INT.fieldOf("inclineHeight").forGetter(InclineData::getOverhangSize),
            Codec.DOUBLE.fieldOf("inclineIntensity").forGetter(InclineData::getInclineIntensity),
            Codec.INT.fieldOf("overhangSize").forGetter(InclineData::getOverhangSize),
            Codec.INT.fieldOf("baseSize").forGetter(InclineData::getBaseSize)
    ).apply(instance, InclineData::new));

    public static final Codec<Optional<InclineData>> OPTIONAL = CodecHelper.optionalCodec(InclineData.CODEC);

    private final boolean isSource; //Source inclines are always fully tall and propagate outwards
    private final int inclineHeight; //Height determines how high above the grove's ground the incline's peak is located
    private double inclineIntensity; //Intensity propagates outwards and is used to calculate overhangSize and baseSize
    private int overhangSize; //Overhang size determines how tall the peak is
    private int baseSize; //Base size determines how tall the incline's base is, connecting it to the rest of the terrain

    public InclineData(boolean isSource, int inclineHeight, double inclineIntensity, int overhangSize, int baseSize) {
        this.isSource = isSource;
        this.inclineHeight = inclineHeight;
        this.inclineIntensity = inclineIntensity;
        this.overhangSize = overhangSize;
        this.baseSize = baseSize;
    }

    public static InclineData source(int inclineHeight, double inclineIntensity) {
        return new InclineData(true, inclineHeight, inclineIntensity).updateValues(1);
    }

    public static InclineData propagation(InclineData sourceIncline) {
        return new InclineData(false, sourceIncline.getInclineHeight(), 0);
    }

    public InclineData(boolean isSource, int inclineHeight, double inclineIntensity) {
        this.isSource = isSource;
        this.inclineHeight = inclineHeight;
        this.inclineIntensity = inclineIntensity;
    }

    public boolean isSource() {
        return isSource;
    }

    public int getInclineHeight() {
        return inclineHeight;
    }

    public int getOverhangSize() {
        return overhangSize;
    }

    public int getBaseSize() {
        return baseSize;
    }

    public double getInclineIntensity() {
        return inclineIntensity;
    }

    public int getPropagationRange() {
        return Mth.floor(Mth.lerp(getPropagationStrength(), 4, 12));
    }

    public double getPropagationStrength() {
        double value = getInclineIntensity();
        if (value < 0.3f) {
            value = 0.8f;
        }
        return value;
    }

    public void applyPropagation(InclineData sourceIncline, double delta) {
        double newIntensity = sourceIncline.getPropagationStrength() * delta;
        if (inclineIntensity < newIntensity) {
            inclineIntensity = newIntensity;
            updateValues(newIntensity);
        }
    }

    public InclineData updateValues(double delta) {
        overhangSize = Mth.floor(Easing.QUAD_IN_OUT.clamped(delta, 0, getInclineHeight()));
        baseSize = Mth.floor(Easing.CUBIC_IN.clamped(delta, 0, getInclineHeight()));
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InclineData) obj;
        return this.overhangSize == that.overhangSize &&
                this.baseSize == that.baseSize &&
                Double.doubleToLongBits(this.inclineIntensity) == Double.doubleToLongBits(that.inclineIntensity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(overhangSize, baseSize, inclineIntensity);
    }

    @Override
    public String toString() {
        return "InclineData[" +
                "overhangSize=" + overhangSize + ", " +
                "baseSize=" + baseSize + ", " +
                "inclineIntensity=" + inclineIntensity + ']';
    }

}
