package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.registry.common.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.data.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import java.util.*;
import java.util.function.*;

public class MeadowItemModelDatagen extends LodestoneItemModelProvider {
    public MeadowItemModelDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        var items = new HashSet<>(MeadowItemRegistry.ITEMS.getEntries());
        AbstractItemModelSmith.ItemModelSmithData data = new AbstractItemModelSmith.ItemModelSmithData(this, items::remove);
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, MeadowItemRegistry.MAGIC_STICK);
    }
}
