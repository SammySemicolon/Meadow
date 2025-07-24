package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.MeadowGroveGenerationConfiguration;
import com.smellysleepy.meadow.move_to_lodestone_later.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class InclineData {

    public static final StringRepresentable.EnumCodec<InclineType> TYPE_CODEC = StringRepresentable.fromEnum(InclineType::values);

    public static final Codec<InclineData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TYPE_CODEC.fieldOf("type").forGetter(InclineData::getType),
            Codec.INT.fieldOf("inclineHeight").forGetter(InclineData::getOverhangSize),
            Codec.DOUBLE.fieldOf("inclineIntensity").forGetter(InclineData::getInclineIntensity),
            Codec.INT.fieldOf("overhangSize").forGetter(InclineData::getOverhangSize),
            Codec.INT.fieldOf("baseSize").forGetter(InclineData::getBaseSize)
    ).apply(instance, InclineData::new));

    public static final Codec<Optional<InclineData>> OPTIONAL = CodecHelper.optionalCodec(InclineData.CODEC);

    public enum InclineType implements StringRepresentable{
        SOURCE("source"),
        ARTIFICIAL_SOURCE("artificial_source"),
        OVERHANG("overhang");

        public final String name;
        InclineType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    private final InclineType type; //Determines the type of incline

    private final int inclineHeight; //Height determines how high above the grove's ground the incline's peak is located
    private double inclineIntensity; //Intensity propagates outwards and is used to calculate overhangSize and baseSize

    private int overhangSize; //Overhang size determines how tall the peak is
    private int baseSize; //Base size determines how tall the incline's base is, connecting it to the rest of the terrain

    private final boolean[] neighboringInclines = new boolean[4]; //Tracks which neighboring inclines this incline has. Sources only consider other sources
    private int neighboringInclineCounter;

    public InclineData(InclineType type, int inclineHeight, double inclineIntensity, int overhangSize, int baseSize) {
        this.type = type;
        this.inclineHeight = inclineHeight;
        this.inclineIntensity = inclineIntensity;
        this.overhangSize = overhangSize;
        this.baseSize = baseSize;
    }

    public static InclineData artificialSource(InclineData sourceIncline) {
        return new InclineData(InclineType.ARTIFICIAL_SOURCE, sourceIncline.getInclineHeight(), sourceIncline.getInclineIntensity());
    }

    public static InclineData source(int inclineHeight, double inclineIntensity) {
        return new InclineData(InclineType.SOURCE, inclineHeight, inclineIntensity).updateValues(1);
    }

    public static InclineData overhang(InclineData sourceIncline) {
        return new InclineData(InclineType.OVERHANG, sourceIncline.getInclineHeight(), 0);
    }

    public InclineData(InclineType type, int inclineHeight, double inclineIntensity) {
        this.type = type;
        this.inclineHeight = inclineHeight;
        this.inclineIntensity = inclineIntensity;
    }

    public InclineType getType() {
        return type;
    }

    public boolean isSource() {
        return type.equals(InclineType.SOURCE);
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

    public int getNeighborCount() {
        return neighboringInclineCounter;
    }

    public boolean isLonely() {
        return neighboringInclineCounter < 2;
    }

    public int getPropagationRange() {
        return Mth.floor(Mth.lerp(getPropagationStrength(), 4, 12));
    }

    public double getPropagationStrength() {
        double value = getInclineIntensity();
        if (value < 0.3f) { //By artificially increasing the propagation strength at low values we aim to make smaller clumps of source inclines look good
            value = 0.8f;
        }
        return value;
    }

    public void checkNeighbors(MeadowGroveGenerationConfiguration config, DataCoordinate position) {
        MeadowGroveGenerationData data = config.getGenerationData();
        for (int i = 0; i < 4; i++) {
            var direction = Direction.from2DDataValue(i);
            var neighborPosition = position.move(direction);
            if (!data.hasData(neighborPosition)) {
                continue;
            }
            var neighboringIncline = data.getData(neighborPosition).getInclineData();
            if (neighboringIncline.isEmpty()) {
                continue;
            }
            checkNeighbor(neighboringIncline.get(), position, neighborPosition);
        }
    }

    public void checkNeighbor(InclineData neighboringData, DataCoordinate inclineCoordinate, DataCoordinate neighborCoordinate) {
        if (!getType().equals(neighboringData.getType())) {
            return;
        }
        var direction = inclineCoordinate.getDirectionTo(neighborCoordinate);
        int index = direction.get2DDataValue();
        if (neighboringInclines[index]) {
            return;
        }
        neighboringInclines[index] = true;
        neighboringInclineCounter++;
    }

    public void applyPropagation(InclineData sourceIncline, double delta) {
        double newIntensity = Mth.clamp(sourceIncline.getPropagationStrength() * delta, 0, 1);
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
