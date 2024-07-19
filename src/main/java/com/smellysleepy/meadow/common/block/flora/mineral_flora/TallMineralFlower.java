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

public class TallMineralFlower extends AbstractTallStrangePlant {

    public final TagKey<Block> oreTag;
    public TallMineralFlower(Properties pProperties, TagKey<Block> oreTag) {
        super(pProperties);
        this.oreTag = oreTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(oreTag);
    }
}
