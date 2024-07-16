package com.smellysleepy.meadow.common.worldgen.tree.mineral;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.flora.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.common.MineralFloraRegistry;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class MineralTreeFeatureConfiguration implements FeatureConfiguration {

    public static final Codec<MineralTreeFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            MineralFloraRegistry.CODEC.fieldOf("bundle").forGetter(obj -> obj.bundle),
                            MineralTreePart.CODEC.listOf().fieldOf("parts").forGetter(obj -> obj.parts))
                    .apply(inst, MineralTreeFeatureConfiguration::new));

    public final MineralFloraRegistryBundle bundle;
    public final List<MineralTreePart> parts;

    public MineralTreeFeatureConfiguration(MineralFloraRegistryBundle bundle, List<MineralTreePart> parts) {
        this.bundle = bundle;
        this.parts = parts;
    }
}