package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;

public class MeadowCreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MeadowMod.MEADOW);

    public static final RegistryObject<CreativeModeTab> CONTENT = CREATIVE_MODE_TABS.register("meadow_content",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MeadowMod.MEADOW))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> MeadowItemRegistry.ASPEN_SAPLING.get().getDefaultInstance()).build()
    );
}
