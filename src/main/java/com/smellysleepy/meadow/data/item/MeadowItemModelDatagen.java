package com.smellysleepy.meadow.data.item;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.mineral.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.item.*;
import net.minecraft.data.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.ItemModelSmithData;
import team.lodestar.lodestone.systems.datagen.providers.*;

import java.util.*;
import java.util.function.*;

public class MeadowItemModelDatagen extends LodestoneItemModelProvider {
    public MeadowItemModelDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MeadowMod.MEADOW, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Supplier<? extends Item>> items = new HashSet<>(MeadowItemRegistry.ITEMS.getEntries());
        ItemModelSmithData data = new ItemModelSmithData(this, items::remove);

        items.removeIf(i -> i.get() instanceof BlockItem);

        setTexturePath("mineral_fruit/");
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA_TYPES.values()) {
            ItemModelSmithTypes.GENERATED_ITEM.act(data, bundle.fruitItem);
            ItemModelSmithTypes.GENERATED_ITEM.act(data, bundle.candyItem);
            ItemModelSmithTypes.GENERATED_ITEM.act(data, bundle.pastryItem);
        }
        setTexturePath("");

        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }
}
