package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.block.flora.*;
import com.smellysleepy.meadow.visual_effects.StrangeFloraParticleEffects;
import net.minecraft.core.*;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

public class TallMineralPlant extends AbstractTallStrangePlant {

    public final TagKey<Block> oreTag;
    public TallMineralPlant(Properties pProperties, TagKey<Block> oreTag) {
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
        if (pState.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            if (pRandom.nextInt(3) == 0) {
                for (int i = 0; i < 2; i++) {
                    double posX = (double) pPos.getX() + 0.1f + pRandom.nextDouble() * 0.8f;
                    double posY = (double) pPos.getY() + 0.2f + pRandom.nextDouble() * 1.6f;
                    double posZ = (double) pPos.getZ() + 0.1f + pRandom.nextDouble() * 0.8f;

                    var dust = StrangeFloraParticleEffects.mineralFlora(pLevel, new Vec3(posX, posY, posZ));
                    dust.getBuilder().setLifeDelay(i * 2);
                    dust.spawnParticles();
                }
            }
        }
    }

}
