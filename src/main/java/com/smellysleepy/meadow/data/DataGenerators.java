package com.smellysleepy.meadow.data;

import com.smellysleepy.meadow.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MeadowMod.MEADOW, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        MeadowItemModelDatagen itemModelsProvider = new MeadowItemModelDatagen(output, helper);

        generator.addProvider(event.includeClient(), new MeadowBlockStateDatagen(output, helper, itemModelsProvider));
        generator.addProvider(event.includeClient(), itemModelsProvider);

    }
}
