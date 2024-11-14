package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.effect.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MeadowMobEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MeadowMod.MEADOW);

    public static final RegistryObject<MobEffect> AMETHYST_FRUIT_EFFECT = EFFECTS.register("amethyst_tranquility", AmethystFruitEffect::new);

    public static final RegistryObject<MobEffect> COPPER_FRUIT_EFFECT = EFFECTS.register("copper_decay", CopperFruitEffect::new);
    public static final RegistryObject<MobEffect> IRON_FRUIT_EFFECT = EFFECTS.register("ironskin", IronFruitEffect::new);
    public static final RegistryObject<MobEffect> GOLD_FRUIT_EFFECT = EFFECTS.register("gold_streak", GoldFruitEffect::new);
    public static final RegistryObject<MobEffect> DIAMOND_FRUIT_EFFECT = EFFECTS.register("diamond_prosperity", DiamondFruitEffect::new);
    public static final RegistryObject<MobEffect> EMERALD_FRUIT_EFFECT = EFFECTS.register("emerald_blessing", EmeraldFruitEffect::new);
    public static final RegistryObject<MobEffect> NETHERITE_FRUIT_EFFECT = EFFECTS.register("netherite_bastion", NetheriteFruitEffect::new);
}
