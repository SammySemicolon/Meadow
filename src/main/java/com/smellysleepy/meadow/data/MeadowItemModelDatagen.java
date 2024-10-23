package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.block.mineral_flora.MineralFloraRegistryBundle;
import com.smellysleepy.meadow.registry.common.*;
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
        Set<Supplier<? extends Item>> items = new HashSet<>(MeadowItemRegistry.ITEMS.getEntries());
        AbstractItemModelSmith.ItemModelSmithData data = new AbstractItemModelSmith.ItemModelSmithData(this, items::remove);

        items.removeIf(i -> i.get() instanceof BlockItem);

        setTexturePath("mineral_fruit/");
        for (MineralFloraRegistryBundle bundle : MineralFloraRegistry.MINERAL_FLORA.values()) {
            ItemModelSmithTypes.GENERATED_ITEM.act(data, bundle.fruitItem);
        }
        setTexturePath("");

        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }
}
