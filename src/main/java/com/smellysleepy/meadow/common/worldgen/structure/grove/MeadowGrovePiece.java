package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.common.worldgen.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.*;
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

import static team.lodestar.lodestone.LodestoneLib.LOGGER;

public class MeadowGrovePiece extends StructurePiece {
    protected MeadowGroveGenerationData groveData;

    protected MeadowGrovePiece(MeadowGroveGenerationData groveData, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveData = groveData;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveData = MeadowGroveGenerationData.load(tag);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext pContext, @NotNull CompoundTag pTag) {
        groveData.save(pTag);
    }

    @Override
    public void postProcess(WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(level.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutable = new BlockPos.MutableBlockPos();

        BlockPos groveCenter = groveData.getGroveCenter();
        int groveRadius = groveData.getGroveRadius();
        int groveHeight = groveData.getGroveHeight();
        int groveDepth = groveData.getGroveDepth();
        for (int i = 0; i < 256; i++) {
            int blockX = chunkPos.getBlockX(i % 16);
            int blockY = groveCenter.getY();
            int blockZ = chunkPos.getBlockZ(i / 16);
            mutable.set(blockX, blockY, blockZ);
            double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;

            int localRadius = (int) Mth.clampedLerp(groveRadius * 0.4f, groveRadius, noise);
            int localHeight = (int) Mth.clampedLerp(groveHeight * 0.5f, groveHeight, noise);
            int localDepth = (int) Mth.clampedLerp(groveDepth * 0.6f, groveDepth, noise);

            if (!mutable.closerThan(groveCenter, localRadius)) {
                continue;
            }
            double distance = mutable.distSqr(groveCenter);
            double delta = distance / localRadius;
            int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
            int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);
            if (height <= 0 && depth <= 0) {
                continue;
            }
            for (int j = 0; j < height; j++) {
                mutable.move(Direction.UP);
                level.setBlock(mutable, Blocks.RED_STAINED_GLASS.defaultBlockState(), 2);
            }
            for (int j = 0; j < depth; j++) {
                level.setBlock(mutable, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 2);
                mutable.move(Direction.DOWN);
            }
        }
    }

    private int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.QUAD_IN, 0.05f, localHeight, delta);
    }

    private int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, localDepth, delta);
    }

    private int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float noiseFrequency, double localValue, double delta) {
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos, 100000, noiseFrequency); // Random Noise based on position
        double deltaScalar = easing.clamped(depthNoise / 2f, 0.9f, 1.1f); //Slight delta scaling based on noise
        double carverDelta = delta * deltaScalar; //Carver delta based on distance from center and delta scaling, approaches 1 towards the end of the grove
        return (int) Easing.SINE_IN_OUT.clamped(carverDelta, localValue, 0); //Final interpolation to get the desired height/depth
    }
}
