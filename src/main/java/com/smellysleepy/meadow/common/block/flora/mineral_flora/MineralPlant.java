package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.block.flora.*;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.*;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.Vec3;

public class MineralPlant extends AbstractStrangePlant {

    public final TagKey<Block> oreTag;
    public MineralPlant(Properties pProperties, TagKey<Block> oreTag) {
        super(pProperties);
        this.oreTag = oreTag;
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(oreTag);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextInt(3) == 0) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(pLevel, blockpos), Direction.UP)) {
                for (int i = 0; i < 2; i++) {
                    double posX = (double) pPos.getX() + 0.1f + pRandom.nextDouble() * 0.8f;
                    double posY = (double) pPos.getY() + 0.1f + pRandom.nextDouble() * 0.8f;
                    double posZ = (double) pPos.getZ() + 0.1f + pRandom.nextDouble() * 0.8f;

                    var dust = StrangeFloraParticleEffects.mineralFlora(pLevel, new Vec3(posX, posY, posZ));
                    dust.getBuilder().setLifeDelay(i*2);
                    dust.spawnParticles();
                }
            }
        }
    }
}
