package com.smellysleepy.meadow.common.item;

import com.smellysleepy.meadow.common.worldgen.strange_plant.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.feature.*;

public class MagicalDeveloperStickItem extends Item {
    public MagicalDeveloperStickItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel() instanceof ServerLevel serverLevel) {
            final BlockPos clickedPos = pContext.getClickedPos();
            ResourceKey<ConfiguredFeature<?, ?>> resourcekey = MeadowConfiguredFeatureRegistry.LAZURITE_ROSE;
            Holder<ConfiguredFeature<?, ?>> holder = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(resourcekey).orElse(null);

            holder.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.random, clickedPos);
        }
        return super.useOn(pContext);
    }
}