package com.smellysleepy.meadow.mixin;

import com.smellysleepy.meadow.common.effect.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {

  @ModifyArg(at = @At(value = "INVOKE", target = "net/minecraftforge/common/IForgeShearable.onSheared(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)Ljava/util/List;", remap = false), method = "interactLivingEntity")
  private int meadow$applyFortuneToShears(Player player, ItemStack stack, Level level, BlockPos pos,
                                          int fortune) {
    return fortune + DiamondFruitEffect.getFortuneBonus(player);
  }
}