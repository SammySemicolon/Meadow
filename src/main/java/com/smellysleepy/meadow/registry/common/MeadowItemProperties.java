package com.smellysleepy.meadow.registry.common;

import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

public class MeadowItemProperties {
    public static Item.Properties DEFAULT_PROPERTIES() {
        return new LodestoneItemProperties(MeadowCreativeTabRegistry.MEADOW_GROVE);
    }
    public static Item.Properties MINERAL_FLORA_PROPERTIES() {
        return new LodestoneItemProperties(MeadowCreativeTabRegistry.MINERAL_FLORA);
    }
}
