package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.datafixers.util.*;
import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.*;
import net.minecraft.world.level.levelgen.synth.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class MeadowGrovePiece extends StructurePiece {
    protected MeadowGroveGenerationConfiguration groveData;

    protected MeadowGrovePiece(MeadowGroveGenerationConfiguration groveData, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveData = groveData;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveData = MeadowGroveGenerationConfiguration.load(tag);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext pContext, @NotNull CompoundTag pTag) {
        groveData.save(pTag);
    }

    @Override
    public void postProcess(WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutable = new BlockPos.MutableBlockPos();

        BlockPos groveCenter = groveData.getGroveCenter();
        MeadowGroveGenerationData generationData = groveData.getGenerationData();
        for (DataEntry data : generationData.getData(chunkPos)) {
            int blockX = data.blockX();
            int blockY = groveCenter.getY();
            int blockZ = data.blockZ();
            int height = data.height();
            int depth = data.depth();
            var biomeType = data.biomeType();
            double influence = data.biomeInfluence();
            if (height <= 0 && depth <= 0) {
                continue;
            }
            Block block;
            if (biomeType == MeadowGroveBiomeType.MEADOW_FOREST) {
                block = Blocks.YELLOW_WOOL;
            } else if (biomeType == MeadowGroveBiomeType.ROCKY_HILLS) {
                block = Blocks.COBBLESTONE;
            } else if (biomeType == MeadowGroveBiomeType.FUNGUS_SHELVES) {
                block = Blocks.MUSHROOM_STEM;
            } else if (biomeType == MeadowGroveBiomeType.CALCIFIED_OUTSKIRTS) {
                block = Blocks.BLUE_WOOL;
            } else if (biomeType == MeadowGroveBiomeType.CAVERNOUS_CALCIFICATION) {
                block = Blocks.PURPLE_WOOL;
            } else {
                throw new IllegalArgumentException("Unknown biome type: " + biomeType);
            }
            mutable.set(blockX, blockY, blockZ);
            for (int j = 0; j < influence * 40; j++) {
                level.setBlock(mutable, block.defaultBlockState(), 2);
                mutable.move(Direction.UP, 1);
            }
//            for (int j = 0; j < height; j++) {
//                mutable.move(Direction.UP);
//                level.setBlock(mutable, Blocks.RED_STAINED_GLASS.defaultBlockState(), 2);
//            }
//            mutable.setY(blockY);
//            for (int j = 0; j < depth; j++) {
//                level.setBlock(mutable, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 2);
//                mutable.move(Direction.DOWN);
//            }
        }
    }
}
