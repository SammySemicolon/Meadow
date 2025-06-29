package com.smellysleepy.meadow.common.worldgen.structure.grove.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.move_to_lodestone_later.*;

import java.util.*;

public record DataEntry(int blockX, int blockZ, int height, int depth, MeadowGroveBiomeType biomeType, double biomeInfluence) {

    public static final Codec<DataEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(DataEntry::blockX),
            Codec.INT.fieldOf("z").forGetter(DataEntry::blockZ),
            Codec.INT.fieldOf("height").forGetter(DataEntry::height),
            Codec.INT.fieldOf("depth").forGetter(DataEntry::depth),
            MeadowGroveBiomeType.CODEC.fieldOf("biomeType").forGetter(DataEntry::biomeType),
            Codec.DOUBLE.fieldOf("biomeInfluence").forGetter(DataEntry::biomeInfluence)
    ).apply(instance, DataEntry::new));
}
