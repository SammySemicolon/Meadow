package com.smellysleepy.meadow.common.worldgen.feature.tree.mineral;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class MineralTreeFeatureConfiguration implements FeatureConfiguration {

    public static final Codec<MineralTreeFeatureConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("sapling").forGetter(obj -> obj.sapling),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("leaves").forGetter(obj -> obj.leaves),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("hangingLeaves").forGetter(obj -> obj.hangingLeaves),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grass").forGetter(obj -> obj.grass),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("flora").forGetter(obj -> obj.flora),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ore").forGetter(obj -> obj.ore),
                            MineralTreePart.CODEC.listOf().fieldOf("parts").forGetter(obj -> obj.parts))
                    .apply(inst, MineralTreeFeatureConfiguration::new));

    public final Block sapling;
    public final Block leaves;
    public final Block hangingLeaves;
    public final Block grass;
    public final Block flora;
    public final Block ore;
    public final List<MineralTreePart> parts;

    public MineralTreeFeatureConfiguration(MineralFloraRegistryBundle bundle, List<MineralTreePart> parts) {
        this(bundle.saplingBlock.get(), bundle.leavesBlock.get(), bundle.hangingLeavesBlock.get(), bundle.grassBlock.get(), bundle.floraBlock.get(), bundle.oreBlock, parts);
    }
    public MineralTreeFeatureConfiguration(Block sapling, Block leaves, Block hangingLeaves, Block grass, Block flora, Block ore, List<MineralTreePart> parts) {
        this.sapling = sapling;
        this.leaves = leaves;
        this.hangingLeaves = hangingLeaves;
        this.grass = grass;
        this.flora = flora;
        this.ore = ore;
        this.parts = parts;
    }
}