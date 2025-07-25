package com.smellysleepy.meadow.common.block.mineral.leaves;

import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.function.Supplier;

public class MineralLeavesBlock extends LeavesBlock implements BonemealableBlock {

    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;
    private final Supplier<LodestoneWorldParticleType> particleType;
    public final Color color;

    public MineralLeavesBlock(Properties pProperties, Supplier<LodestoneWorldParticleType> particleType, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature, Color color) {
        super(pProperties);
        this.particleType = particleType;
        this.bonemealFeature = bonemealFeature;
        this.color = color;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(6) == 0) {
            double posX = (double) pPos.getX() + 0.1f + pRandom.nextDouble() * 0.8f;
            double posY = (double) pPos.getY() + 0.1f + pRandom.nextDouble() * 0.8f;
            double posZ = (double) pPos.getZ() + 0.1f + pRandom.nextDouble() * 0.8f;
            var dust = StrangeFloraParticleEffects.mineralFloraShine(pLevel, new Vec3(posX, posY, posZ));
            dust.spawnParticles();
        }
        if (pRandom.nextInt(10) == 0) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                double posX = (double) pPos.getX() + pRandom.nextDouble();
                double posY = (double) pPos.getY() - 0.05D;
                double posZ = (double) pPos.getZ() + pRandom.nextDouble();
                MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ), particleType.get()).spawnParticles();

            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        var configuredFeatures = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        var holder = configuredFeatures.getHolder(bonemealFeature);
        holder.ifPresent(feature -> feature.value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos.below()));
    }
}
