package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.client.*;
import net.minecraft.core.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.particle.type.*;

public class MeadowParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MeadowMod.MEADOW);

    public static RegistryObject<LodestoneParticleType> MEADOW_LEAVES = PARTICLES.register("meadow_leaves", LodestoneParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(MEADOW_LEAVES.get(), LodestoneParticleType.Factory::new);
    }
}
