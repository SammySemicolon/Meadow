package com.smellysleepy.meadow.common.block;

import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.Supplier;

public class CalcifiedLogBlock extends LodestoneLogBlock {

    public CalcifiedLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
    }
}