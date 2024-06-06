package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.client.*;
import net.minecraft.core.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

public class MeadowParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MeadowMod.MEADOW);

    public static RegistryObject<LodestoneWorldParticleType> ASPEN_LEAVES = PARTICLES.register("aspen_leaves", LodestoneWorldParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ASPEN_LEAVES.get(), LodestoneWorldParticleType.Factory::new);
    }
}
