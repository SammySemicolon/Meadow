package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

import java.util.function.Supplier;

public class MeadowParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MeadowMod.MEADOW);

    public static Supplier<LodestoneWorldParticleType> SHINY_GLIMMER = PARTICLES.register("shiny_glimmer", LodestoneWorldParticleType::new);

    public static Supplier<LodestoneWorldParticleType> ASPEN_LEAVES = PARTICLES.register("aspen_leaf", LodestoneWorldParticleType::new);


    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        for (DeferredHolder<ParticleType<?>, ? extends ParticleType<?>> entry : PARTICLES.getEntries()) {
            event.registerSpriteSet((LodestoneWorldParticleType)entry.get(), LodestoneWorldParticleType.Factory::new);
        }
    }
}
