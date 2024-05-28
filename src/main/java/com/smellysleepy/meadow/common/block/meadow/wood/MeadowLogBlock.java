package com.smellysleepy.meadow.common.block.meadow.wood;

import net.minecraft.world.level.block.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.*;

public class MeadowLogBlock extends LodestoneLogBlock {

    public MeadowLogBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
    }
    public MeadowLogBlock(Properties properties) {
        this(properties, null);
    }
}
