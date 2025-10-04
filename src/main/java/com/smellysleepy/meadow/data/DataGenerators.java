package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.data.block.*;
import com.smellysleepy.meadow.data.item.*;
import com.smellysleepy.meadow.data.recipe.*;
import com.smellysleepy.meadow.data.worldgen.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MeadowMod.MEADOW, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var provider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        var itemModelsProvider = new MeadowItemModelDatagen(output, helper);
        var blockStatesProvider = new MeadowBlockStateDatagen(output, helper, itemModelsProvider);

        var blockTagsProvider = new MeadowBlockTagDatagen(output, provider, helper);
        var itemTagsProvider = new MeadowItemTagDatagen(output, provider, blockTagsProvider.contentsGetter(), helper);


        generator.addProvider(event.includeClient(), blockStatesProvider);
        generator.addProvider(event.includeClient(), itemModelsProvider);

        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), itemTagsProvider);


        generator.addProvider(event.includeClient(), new MeadowLangDatagen(output));

        generator.addProvider(event.includeServer(), new MeadowRecipes(output, provider));

        generator.addProvider(event.includeClient(), new MeadowBiomeTagDatagen(output, provider, helper));
        generator.addProvider(event.includeServer(), new MeadowBlockLootTableDatagen(output, provider));

        generator.addProvider(event.includeServer(), new MeadowWorldgenRegistryDatagen(output, provider));
    }
}
