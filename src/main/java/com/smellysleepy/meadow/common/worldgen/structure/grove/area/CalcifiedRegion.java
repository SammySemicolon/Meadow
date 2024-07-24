package com.smellysleepy.meadow.common.worldgen.structure.grove.area;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.MineralFeatureDistribution;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec2;
import team.lodestar.lodestone.systems.easing.Easing;

public class CalcifiedRegion extends SpecialMeadowRegion {

    public static final ResourceLocation TYPE = addType(MeadowMod.meadowModPath("calcification"), CalcifiedRegion::deserialize);

    private final ResourceKey<ConfiguredFeature<?, ?>> treeFeature;
    private final ResourceKey<ConfiguredFeature<?, ?>> plantFeature;
    private final ResourceKey<ConfiguredFeature<?, ?>> oreFeature;
    private final ResourceKey<ConfiguredFeature<?, ?>> patchFeature;

    public CalcifiedRegion(Vec2 direction, double size, MineralFeatureDistribution distribution) {
        this(direction, size, distribution.plant, distribution.ore, distribution.patch, distribution.tree);
    }

    public CalcifiedRegion(Vec2 direction, double size,
                           ResourceKey<ConfiguredFeature<?, ?>> treeFeature,
                           ResourceKey<ConfiguredFeature<?, ?>> plantFeature,
                           ResourceKey<ConfiguredFeature<?, ?>> oreFeature,
                           ResourceKey<ConfiguredFeature<?, ?>> patchFeature) {
        super(TYPE, direction, size);
        this.treeFeature = treeFeature;
        this.plantFeature = plantFeature;
        this.oreFeature = oreFeature;
        this.patchFeature = patchFeature;
    }

    public static CalcifiedRegion deserialize(CompoundTag tag) {
        return new CalcifiedRegion(
                new Vec2(tag.getFloat("directionalOffsetX"), tag.getFloat("directionalOffsetZ")), tag.getDouble("size"),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("treeFeature"))),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("plantFeature"))),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("oreFeature"))),
                ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(tag.getString("patchFeature"))));
    }

    @Override
    public CompoundTag serialize(CompoundTag tag) {
        tag.putString("treeFeature", treeFeature.location().toString());
        tag.putString("plantFeature", plantFeature.location().toString());
        tag.putString("oreFeature", oreFeature.location().toString());
        tag.putString("patchFeature", patchFeature.location().toString());
        return tag;
    }

    @Override
    public double getNoiseVariance(double noise) {
        return Easing.SINE_IN_OUT.clamped(noise, 0.5f, 1f);
    }

    public ResourceKey<ConfiguredFeature<?, ?>> chooseFeature(RandomSource randomSource) {
        float rand = randomSource.nextFloat();
        if (rand < 0.0025f) {
            return treeFeature;
        } else if (rand < 0.01f) {
            return plantFeature;
        } else if (rand < 0.04f) {
            return patchFeature;
        } else if (rand < 0.06f) {
            return oreFeature;
        }
        return null;
    }
}
