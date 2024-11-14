package com.smellysleepy.meadow.mixin;

import com.smellysleepy.meadow.common.effect.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

  @Inject(method = "gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V", at = @At("HEAD"), cancellable = true)
  private void meadow$blockVibrations(GameEvent pEvent, Vec3 pPosition, GameEvent.Context pContext, CallbackInfo ci) {
    if (pContext.sourceEntity() instanceof LivingEntity livingEntity) {
      if (AmethystFruitEffect.blockVibrations(livingEntity)) {
        ci.cancel();
      }
    }
  }
}