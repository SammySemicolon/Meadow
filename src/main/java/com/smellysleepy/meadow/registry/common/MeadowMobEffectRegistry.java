package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.effect.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.*;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class MeadowMobEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MeadowMod.MEADOW);

    public static final DeferredHolder<MobEffect, MobEffect> AMETHYST_FRUIT_EFFECT = EFFECTS.register("amethyst_disruption", AmethystFruitEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> COPPER_FRUIT_EFFECT = EFFECTS.register("copper_weathering", CopperFruitEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> IRON_FRUIT_EFFECT = EFFECTS.register("ironskin", IronFruitEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> GOLD_FRUIT_EFFECT = EFFECTS.register("gold_grandiosity", GoldFruitEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> DIAMOND_FRUIT_EFFECT = EFFECTS.register("diamond_opulence", DiamondFruitEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> EMERALD_FRUIT_EFFECT = EFFECTS.register("emerald_prospect", EmeraldFruitEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> NETHERITE_FRUIT_EFFECT = EFFECTS.register("netherite_bulwark", NetheriteFruitEffect::new);
}
