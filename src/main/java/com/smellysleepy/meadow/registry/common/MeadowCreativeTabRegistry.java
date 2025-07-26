package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

public class MeadowCreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MeadowMod.MEADOW);

    public static final Supplier<CreativeModeTab> MEADOW_GROVE = CREATIVE_MODE_TABS.register("meadow_grove",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MeadowMod.MEADOW+ ".meadow_grove"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get().getDefaultInstance()).build()
    );

    public static final Supplier<CreativeModeTab> MINERAL_FLORA = CREATIVE_MODE_TABS.register("mineral_flora",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MeadowMod.MEADOW+ ".mineral_flora"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> MineralFloraRegistry.COAL_FLORA.grassBlockItem.get().getDefaultInstance()).build()
    );
}
