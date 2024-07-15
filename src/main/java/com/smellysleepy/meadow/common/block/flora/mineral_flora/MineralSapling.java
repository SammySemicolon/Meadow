package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.block.meadow.flora.MeadowSaplingBlock;
import com.smellysleepy.meadow.common.worldgen.tree.MeadowTreeGrower;
import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class MineralSapling extends SaplingBlock {
    public final TagKey<Block> tag;

    public MineralSapling(Properties pProperties, TagKey<Block> tag) {
        this(new MeadowTreeGrower(), pProperties, tag);
    }

    public MineralSapling(AbstractTreeGrower grower, Properties pProperties, TagKey<Block> tag) {
        super(grower, pProperties);
        this.tag = tag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(MeadowBlockTagRegistry.STRANGE_FLORA_GROUND) || pState.is(tag);
    }
}