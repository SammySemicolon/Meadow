package com.smellysleepy.meadow.common.block.flora.mineral_flora;

import com.smellysleepy.meadow.common.worldgen.feature.tree.SimpleTreeGrower;
import com.smellysleepy.meadow.registry.tags.MeadowBlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MineralSapling extends SaplingBlock {
    public final TagKey<Block> tag;

    public MineralSapling(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> feature, TagKey<Block> tag) {
        this(new SimpleTreeGrower(feature), pProperties, tag);
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