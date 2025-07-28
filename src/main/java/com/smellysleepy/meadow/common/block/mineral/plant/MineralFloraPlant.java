package com.smellysleepy.meadow.common.block.mineral.plant;

import com.smellysleepy.meadow.registry.common.MeadowTags;
import net.minecraft.core.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MineralFloraPlant extends TallGrassBlock {

    public final TagKey<Block> oreTag;

    public MineralFloraPlant(Properties properties, TagKey<Block> oreTag) {
        super(properties);
        this.oreTag = oreTag;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(MeadowTags.BlockTags.MINERAL_FLORA_CAN_PLACE_ON) || state.is(oreTag);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }
}