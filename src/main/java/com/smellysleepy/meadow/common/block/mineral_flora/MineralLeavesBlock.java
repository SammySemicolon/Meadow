package com.smellysleepy.meadow.common.block.mineral_flora;

import com.smellysleepy.meadow.visual_effects.MeadowParticleEffects;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class MineralLeavesBlock extends LeavesBlock implements BonemealableBlock {

    private final Supplier<Block> hangingLeaves;
    public final Color color;

    public MineralLeavesBlock(Properties pProperties, Supplier<Block> hangingLeaves, Color color) {
        super(pProperties);
        this.hangingLeaves = hangingLeaves;
        this.color = color;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(10) == 0) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                double posX = (double)pPos.getX() + pRandom.nextDouble();
                double posY = (double)pPos.getY() - 0.05D;
                double posZ = (double)pPos.getZ() + pRandom.nextDouble();

                var leaves = MeadowParticleEffects.fallingLeaves(pLevel, new Vec3(posX, posY, posZ));
                leaves.getBuilder().setColorData(ColorParticleData.create(color).build());
                leaves.spawnParticles();
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos.below(), hangingLeaves.get().defaultBlockState(), 3);
    }
}
