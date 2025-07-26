package com.smellysleepy.meadow.data.worldgen;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.mineral.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.*;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.function.*;

public class MeadowBiomeModifications {
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {

        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            if (bundle.equals(MineralFloraRegistry.NETHERITE_FLORA) || bundle.equals(MineralFloraRegistry.AMETHYST_FLORA)) {
                continue;
            }
            register(context, bundle.id.getPath() + "_tree", () ->
                    addFeatureModifier(context,
                            getPlacedHolderSet(context, bundle.placedTreeFeature),
                            MeadowBiomeTagRegistry.HAS_MINERAL_TREES, GenerationStep.Decoration.UNDERGROUND_DECORATION));
        }
    }

    @SafeVarargs
    public static HolderSet<PlacedFeature> getPlacedHolderSet(BootstrapContext<?> context, ResourceKey<PlacedFeature>... placedFeatures) {
        List<Holder<PlacedFeature>> holders = new ArrayList<>();
        for (ResourceKey<PlacedFeature> feature : placedFeatures) {
            holders.add(context.lookup(Registries.PLACED_FEATURE).getOrThrow(feature));
        }
        return HolderSet.direct(holders);
    }

    private static BiomeModifiers.AddFeaturesBiomeModifier addFeatureModifier(BootstrapContext<BiomeModifier> context, HolderSet<PlacedFeature> placedSet, TagKey<Biome> biomeTag, GenerationStep.Decoration decoration) {
        return new BiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomeTag), placedSet, decoration);
    }

    private static void register(BootstrapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MeadowMod.meadowModPath(name)), modifier.get());
    }
}