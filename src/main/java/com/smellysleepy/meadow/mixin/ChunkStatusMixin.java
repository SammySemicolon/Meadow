package com.smellysleepy.meadow.mixin;

import com.smellysleepy.meadow.common.worldgen.structure.grove.MeadowGroveInjection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

//    @Inject(method = "lambda$static$10", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private static void injectCragTerrain(ChunkStatus chunkStatus, ServerLevel serverLevel, ChunkGenerator chunkGenerator, List<ChunkAccess> list, ChunkAccess chunkAccess, CallbackInfo ci, WorldGenRegion worldGenRegion) {
//        MeadowGroveInjection.injectGrove(serverLevel, chunkAccess, worldGenRegion);
//    }
}