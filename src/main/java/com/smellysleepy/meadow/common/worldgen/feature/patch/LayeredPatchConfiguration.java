package com.smellysleepy.meadow.common.worldgen.feature.patch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class LayeredPatchConfiguration implements FeatureConfiguration {

    public static final Codec<LayeredPatchConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            Codec.list(BuiltInRegistries.BLOCK.byNameCodec()).fieldOf("plants").forGetter(obj -> obj.plants),
                            Codec.list(Codec.INT).fieldOf("patchSizes").forGetter(obj -> obj.patchSizes))
                    .apply(inst, LayeredPatchConfiguration::new)
            );
    public final List<Block> plants;
    public final List<Integer> patchSizes;

    public LayeredPatchConfiguration(MineralFloraRegistryBundle bundle, List<Integer> patchSizes) {
        this(List.of(bundle.floraBlock.get()), patchSizes);
    }
    public LayeredPatchConfiguration(List<Block> plants, List<Integer> patchSizes) {
        this.plants = plants;
        this.patchSizes = patchSizes;
    }
}