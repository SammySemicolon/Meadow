package com.smellysleepy.meadow.common.worldgen.feature.tree;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.Optional;

public class AbstractCalcifiedTreeFeatureConfiguration implements FeatureConfiguration {

    protected final Block sapling;
    protected final Block log;
    protected final Block partiallyCalcifiedLog;
    protected final Block calcifiedLog;

    public AbstractCalcifiedTreeFeatureConfiguration(Block sapling, Block log, Block partiallyCalcifiedLog, Block calcifiedLog) {
        this.sapling = sapling;
        this.log = log;
        this.partiallyCalcifiedLog = partiallyCalcifiedLog;
        this.calcifiedLog = calcifiedLog;
    }

    public Block getSapling() {
        return sapling;
    }

    public Block getLog() {
        return log;
    }

    public Block getPartiallyCalcifiedLog() {
        return partiallyCalcifiedLog;
    }

    public Block getCalcifiedLog() {
        return calcifiedLog;
    }
}
