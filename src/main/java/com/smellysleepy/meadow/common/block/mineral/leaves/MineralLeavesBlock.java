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

    public MineralLeavesBlock(Properties properties, Supplier<LodestoneWorldParticleType> particleType, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature, Color color) {
        super(properties);
        this.particleType = particleType;
        this.bonemealFeature = bonemealFeature;
        this.color = color;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(6) == 0) {
            double posX = (double) pos.getX() + 0.1f + random.nextDouble() * 0.8f;
            double posY = (double) pos.getY() + 0.1f + random.nextDouble() * 0.8f;
            double posZ = (double) pos.getZ() + 0.1f + random.nextDouble() * 0.8f;
            var dust = StrangeFloraParticleEffects.mineralFloraShine(level, new Vec3(posX, posY, posZ));
            dust.spawnParticles();
        }
        if (random.nextInt(10) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                double posX = (double) pos.getX() + random.nextDouble();
                double posY = (double) pos.getY() - 0.05D;
                double posZ = (double) pos.getZ() + random.nextDouble();
                MeadowParticleEffects.fallingLeaves(level, new Vec3(posX, posY, posZ), particleType.get()).spawnParticles();

            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        var holder = configuredFeatures.getHolder(bonemealFeature);
        holder.ifPresent(feature -> feature.value().place(level, level.getChunkSource().getGenerator(), random, pos.below()));
    }
}
