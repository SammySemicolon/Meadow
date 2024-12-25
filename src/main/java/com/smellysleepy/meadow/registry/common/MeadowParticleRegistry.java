package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import net.minecraft.core.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

public class MeadowParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MeadowMod.MEADOW);

    public static RegistryObject<LodestoneWorldParticleType> SHINY_GLIMMER = PARTICLES.register("shiny_glimmer", LodestoneWorldParticleType::new);

    public static RegistryObject<LodestoneWorldParticleType> ASPEN_LEAVES = PARTICLES.register("falling_leaves", LodestoneWorldParticleType::new);


    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ASPEN_LEAVES.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SHINY_GLIMMER.get(), LodestoneWorldParticleType.Factory::new);
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            event.registerSpriteSet(bundle.particle.get(), LodestoneWorldParticleType.Factory::new);
        }
    }
}
