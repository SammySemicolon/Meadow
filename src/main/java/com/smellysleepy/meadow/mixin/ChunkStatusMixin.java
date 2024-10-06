package com.smellysleepy.meadow.mixin;

import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

//    @Inject(method = "lambda$static$10", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private static void injectCragTerrain(ChunkStatus chunkStatus, ServerLevel serverLevel, ChunkGenerator chunkGenerator, List<ChunkAccess> list, ChunkAccess chunkAccess, CallbackInfo ci, WorldGenRegion worldGenRegion) {
//        MeadowGroveInjection.injectGrove(serverLevel, chunkAccess, worldGenRegion);
//    }
}