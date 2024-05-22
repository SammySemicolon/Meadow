package com.smellysleepy.meadow.registry.common;

import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

public class MeadowItemProperties {
    public static Item.Properties DEFAULT_PROPERTIES() {
        return new LodestoneItemProperties(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
