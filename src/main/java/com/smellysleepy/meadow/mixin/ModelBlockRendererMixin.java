package com.smellysleepy.meadow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.PearllampBlock;
import com.smellysleepy.meadow.common.block.meadow.leaves.HangingMeadowLeavesBlock;
import com.smellysleepy.meadow.common.block.meadow.leaves.MeadowLeavesBlock;
import com.smellysleepy.meadow.common.block.mineral_flora.MineralLeavesBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBlockRenderer.class)
@SuppressWarnings("MethodMayBeStatic")
public class ModelBlockRendererMixin {
    @WrapOperation(
            method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getLightEmission(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I", remap = false),
            remap = false
    )

    private int framedblocks$forceAmbientOcclusionOnLightEmittingFramedBlocks(BlockState state, BlockGetter level, BlockPos pos, Operation<Integer> original, @Local BakedModel model, @Local RenderType layer) {
        Block block = state.getBlock();
        if (block instanceof PearllampBlock || block instanceof HangingMeadowLeavesBlock) {
            return 0;
        }
        return original.call(state, level, pos);
    }
}