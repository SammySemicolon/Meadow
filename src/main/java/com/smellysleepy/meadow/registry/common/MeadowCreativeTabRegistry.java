package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;

public class MeadowCreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MeadowMod.MEADOW);

    public static final RegistryObject<CreativeModeTab> MEADOW_GROVE = CREATIVE_MODE_TABS.register("meadow_grove",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MeadowMod.MEADOW+ ".meadow_grove"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> MeadowItemRegistry.PARTIALLY_CALCIFIED_ASPEN_LOG.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> MINERAL_FLORA = CREATIVE_MODE_TABS.register("mineral_flora",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MeadowMod.MEADOW+ ".mineral_flora"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> MineralFloraRegistry.COAL_FLORA.grassBlockItem.get().getDefaultInstance()).build()
    );
}
