package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.*;
import org.jetbrains.annotations.*;

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
            int blockX = data.getBlockX();
            int blockY = groveCenter.getY();
            int blockZ = data.getBlockZ();
            int height = data.getHeight();
            int depth = data.getDepth();
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

            level.setBlock(mutable, block.defaultBlockState(), 2);

            Optional<InclineData> optional = data.getInclineData();
            if (optional.isPresent()) {
                InclineData inclineData = optional.get();
                int baseSize = inclineData.getBaseSize();
                int overhangSize = inclineData.getOverhangSize();
                int inclineHeight = inclineData.getInclineHeight();

                mutable.move(Direction.UP, inclineHeight);
                for (int i = 0; i < overhangSize; i++) {
                    var overhangBlock = inclineData.isSource() ? block : Blocks.RED_STAINED_GLASS;
                    level.setBlock(mutable, overhangBlock.defaultBlockState(), 2);
                    mutable.move(Direction.DOWN);
                }
                mutable.setY(blockY);
                for (int i = 0; i < baseSize; i++) {
                    mutable.move(Direction.UP);
                    var baseBlock = inclineData.isSource() ? block : Blocks.GREEN_STAINED_GLASS;
                    level.setBlock(mutable, baseBlock.defaultBlockState(), 2);
                }
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
