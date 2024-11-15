package com.smellysleepy.meadow.mixin;

import com.smellysleepy.meadow.common.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {

    @Shadow
    @Final
    Enchantment enchantment;

    @Unique
    LootContext meadow$lootContext;

    @Inject(method = "run", at = @At(value = "HEAD"))
    private void run(ItemStack pStack, LootContext pContext, CallbackInfoReturnable<ItemStack> cir) {
        meadow$lootContext = pContext;
    }
    @ModifyArg(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$Formula;calculateNewCount(Lnet/minecraft/util/RandomSource;II)I"), index = 2)
    private int meadow$applyFortune(int pEnchantmentLevel) {
        if (this.enchantment == Enchantments.BLOCK_FORTUNE) {
            Entity entity = meadow$lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);

            if (entity instanceof LivingEntity livingEntity) {
                return pEnchantmentLevel + DiamondFruitEffect.getFortuneBonus(livingEntity);
            }
        }
        return pEnchantmentLevel;
    }
}