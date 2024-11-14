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

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {

    @Shadow
    @Final
    Enchantment enchantment;

    @ModifyVariable(method = "run", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I"), ordinal = 2)
    private int meadow$applyFortune(int enchantmentLevel, ItemStack stack, LootContext lootContext) {
        if (this.enchantment == Enchantments.BLOCK_FORTUNE) {
            Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);

            if (entity instanceof LivingEntity livingEntity) {
                return enchantmentLevel + DiamondFruitEffect.getFortuneBonus(livingEntity);
            }
        }
        return enchantmentLevel;
    }
}