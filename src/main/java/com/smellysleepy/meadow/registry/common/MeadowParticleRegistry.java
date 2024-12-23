package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.core.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

public class MeadowParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MeadowMod.MEADOW);

    public static RegistryObject<LodestoneWorldParticleType> SHINY_GLIMMER = PARTICLES.register("shiny_glimmer", LodestoneWorldParticleType::new);

    public static RegistryObject<LodestoneWorldParticleType> ASPEN_LEAVES = PARTICLES.register("falling_leaves", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> AMETHYST_LEAF = PARTICLES.register("amethyst_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> COAL_LEAF = PARTICLES.register("coal_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> LAPIS_LEAF = PARTICLES.register("lapis_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> REDSTONE_LEAF = PARTICLES.register("redstone_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> COPPER_LEAF = PARTICLES.register("copper_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> IRON_LEAF = PARTICLES.register("iron_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> GOLD_LEAF = PARTICLES.register("gold_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> DIAMOND_LEAF = PARTICLES.register("diamond_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> EMERALD_LEAF = PARTICLES.register("emerald_leaf", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> NETHERITE_LEAF = PARTICLES.register("netherite_leaf", LodestoneWorldParticleType::new);


    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ASPEN_LEAVES.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SHINY_GLIMMER.get(), LodestoneWorldParticleType.Factory::new);

        event.registerSpriteSet(AMETHYST_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(COAL_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(LAPIS_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(REDSTONE_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(COPPER_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(IRON_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(GOLD_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(DIAMOND_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(EMERALD_LEAF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(NETHERITE_LEAF.get(), LodestoneWorldParticleType.Factory::new);
    }
}
