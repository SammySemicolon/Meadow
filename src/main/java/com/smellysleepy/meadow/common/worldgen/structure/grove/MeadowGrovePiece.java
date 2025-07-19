package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.GroveFeatureProvider;
import com.smellysleepy.meadow.common.worldgen.structure.grove.feature.PlacedGroveFeatures;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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

//        GroveFeatureProvider featureProvider = biomeType.createSurfaceFeatures(random);


        for (DataEntry data : generationData.getData(chunkPos)) {
            int blockX = data.getBlockX();
            int blockY = groveCenter.getY() + data.getCenterOffset();
            int blockZ = data.getBlockZ();
            var biomeType = data.biomeType();
            double biomeInfluence = data.biomeInfluence();
            int height = data.getHeight();
            int depth = data.getDepth();
            int openHeight = data.getOpenHeight();
            int openDepth = data.getOpenDepth();
            if (height <= 0 && depth <= 0) {
                continue;
            }
            int filledHeight = height - openHeight;
            int filledDepth = depth - openDepth;
            BlockState ceilingState = Blocks.STONE.defaultBlockState();
            BlockState surfaceState = biomeType.getSurfaceBlock();

            mutable.set(blockX, blockY, blockZ);

            Optional<InclineData> optional = data.getInclineData();
            if (optional.isPresent()) {
                InclineData inclineData = optional.get();
                int foundationSize = inclineData.getBaseSize();
                int overhangSize = inclineData.getOverhangSize();
                int inclineHeight = inclineData.getInclineHeight() + Mth.floor(openDepth * 0.8f);

                mutable.move(Direction.DOWN, openDepth);
                mutable.move(Direction.UP, inclineHeight);
                for (int i = 0; i < overhangSize; i++) {
                    float delta = i / (float)overhangSize;
                    BlockState state = biomeType.getSurfaceBlock(delta);
                    level.setBlock(mutable, state, 2);
                    mutable.move(Direction.DOWN);
                }
                mutable.setY(blockY - openDepth);
                for (int i = 0; i < foundationSize; i++) {
                    mutable.move(Direction.UP);
                    float delta = 1 - (i / (float)foundationSize);
                    BlockState state = biomeType.getSurfaceBlock(delta);
                    level.setBlock(mutable, state, 2);
                }
            }

            mutable.setY(blockY);
            for (int i = 0; i < height; i++) {
                mutable.move(Direction.UP);
                if (i >= openHeight) {
                    level.setBlock(mutable, ceilingState, 2);
                }
            }
            mutable.setY(blockY);
            for (int i = 0; i <= depth; i++) {
                if (i >= openDepth) {
                    float delta = (i-openDepth) / (float)filledDepth;
                    BlockState state = biomeType.getSurfaceBlock(delta);
                    level.setBlock(mutable, state, 2);
                }
                mutable.move(Direction.DOWN);
            }
        }
    }
}