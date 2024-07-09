package com.smellysleepy.meadow.common.worldgen.biome;

import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeadowGrovePiece extends StructurePiece {

    private final BlockPos origin;
    private final int radius;
    private final int lakeDepth;

    protected MeadowGrovePiece(BlockPos origin, int radius, int lakeDepth, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.origin = origin;
        this.radius = radius;
        this.lakeDepth = lakeDepth;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.origin = NbtUtils.readBlockPos(tag.getCompound("origin"));
        this.radius = tag.getInt("radius");
        this.lakeDepth = tag.getInt("lakeDepth");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("origin", NbtUtils.writeBlockPos(this.origin));
        tag.putInt("radius", this.radius);
        tag.putInt("lakeDepth", this.lakeDepth);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutableBlockPos = new BlockPos.MutableBlockPos();
        var stateProvider = new NoiseProvider(worldGenLevel.getSeed(),
                worldGenLevel.registryAccess().lookup(Registries.NOISE).orElseThrow().getOrThrow(Noises.BADLANDS_SURFACE).value(),
                0.5F,
                List.of(Blocks.GRAVEL.defaultBlockState(), Blocks.MUD.defaultBlockState(), Blocks.CLAY.defaultBlockState()));

        ChunkAccess chunk = worldGenLevel.getChunk(chunkPos.x, chunkPos.z);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);

                mutableBlockPos.set(blockX, 0, blockZ);

                int worldSurfaceY = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockX, blockZ);
                double radiusFrequency = 0.05;
                double noise = noiseSampler.noise(blockX * radiusFrequency, 0, blockZ * radiusFrequency) + 1; // 0-2 range, no negatives

                double localRadius = (int) Mth.clampedLerp(radius * 0.5, radius, noise * 0.5F);

                int blendWidth = 43;

                int rimSize = (int) (localRadius * 0.05);

                int fluidDepth = 1;

                BlockState[] topBlocks = getSurfaceBlocks(mutableBlockPos, blockX, blockZ, worldSurfaceY, chunk);

                blendTerrainChecked(mutableBlockPos, localRadius, blendWidth, worldSurfaceY, blockX, blockZ, chunk, topBlocks, unsafeBoundingBox);

                buildLakeChecked(worldGenLevel, random, mutableBlockPos, localRadius, blendWidth, rimSize, noiseSampler, fluidDepth, blockX, blockZ, chunk, stateProvider, topBlocks, unsafeBoundingBox);
            }
        }
        if (unsafeBoundingBox.valid()) {
            this.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }

    private void buildLakeChecked(WorldGenLevel worldGenLevel, RandomSource random, BlockPos.MutableBlockPos mutableBlockPos, double localRadius, int blendWidth, int rimSize, ImprovedNoise noiseSampler, int waterLevel, int blockX, int blockZ, ChunkAccess chunk, BlockStateProvider stateProvider, BlockState[] topBlocks, UnsafeBoundingBox unsafeBoundingBox) {
        if (mutableBlockPos.setY(origin.getY()).closerThan(origin, localRadius - blendWidth - rimSize)) {
            buildLake(worldGenLevel, random, localRadius, blendWidth, rimSize, mutableBlockPos, noiseSampler, waterLevel, blockX, blockZ, chunk, stateProvider, topBlocks, unsafeBoundingBox);
        }
    }

    private void buildLake(WorldGenLevel worldGenLevel, RandomSource random, double localRadius, int blendWidth, int rimSize, BlockPos.MutableBlockPos mutableBlockPos, ImprovedNoise noiseSampler, int fluidLevel, int blockX, int blockZ, ChunkAccess chunk, BlockStateProvider stateProvider, BlockState[] topBlocks, UnsafeBoundingBox unsafeBoundingBox) {
        double offset = localRadius - blendWidth - rimSize;
        double delta = (mutableBlockPos.setY(origin.getY()).distSqr(origin) - Mth.square(offset)) / Mth.square(localRadius - offset);

        float frequency = 0.05F;
        double depthNoise = ((noiseSampler.noise((mutableBlockPos.getX() + 100000) * frequency, 0, ((mutableBlockPos.getZ() + 100000) * frequency))) + 1) * 0.5;


        double depthOffset = Mth.clampedLerp(0, 10, depthNoise);
        int minGenY = origin.getY() - lakeDepth;

        int waterGenY = origin.getY() - fluidLevel;
        int depth = (int) Mth.clampedLerp(origin.getY(), minGenY - depthOffset, -delta);


        for (int y = origin.getY(); y >= depth - 1; y--) {
            mutableBlockPos.set(blockX, y, blockZ);

            if (y == depth - 1) {
                chunk.setBlockState(mutableBlockPos, Blocks.STONE.defaultBlockState(), false);
            } else if (y <= depth + 3) {

                if (y < waterGenY) {
                    chunk.setBlockState(mutableBlockPos, stateProvider.getState(random, mutableBlockPos), false);
                } else {
                    chunk.setBlockState(mutableBlockPos, topBlocks[Math.min(origin.getY() - y, topBlocks.length - 1)], false);

                }

            } else if (y > waterGenY) {
                chunk.setBlockState(mutableBlockPos, Blocks.AIR.defaultBlockState(), false);
            } else {
                chunk.setBlockState(mutableBlockPos, Blocks.WATER.defaultBlockState(), false);
                worldGenLevel.scheduleTick(mutableBlockPos.immutable(), Fluids.WATER, 0);
            }
            unsafeBoundingBox.encapsulate(mutableBlockPos);
        }
    }

    private void blendTerrainChecked(BlockPos.MutableBlockPos mutableBlockPos, double localRadius, int blendWidth, int worldSurfaceY, int blockX, int blockZ, ChunkAccess chunk, BlockState[] topBlocks, UnsafeBoundingBox unsafeBoundingBox) {
        if (mutableBlockPos.setY(origin.getY()).closerThan(origin, localRadius)) {
            blendTerrain(localRadius, blendWidth, mutableBlockPos, worldSurfaceY, blockX, blockZ, chunk, topBlocks, unsafeBoundingBox);
        }
    }

    private void blendTerrain(double localRadius, int blendWidth, BlockPos.MutableBlockPos mutableBlockPos, int worldSurfaceY, int blockX, int blockZ, ChunkAccess chunk, BlockState[] topBlocks, UnsafeBoundingBox unsafeBoundingBox) {
        double offset = localRadius - blendWidth;
        double delta = (mutableBlockPos.setY(origin.getY()).distSqr(origin) - Mth.square(offset)) / Mth.square(localRadius - offset);
        int height = (int) Mth.clampedLerp(origin.getY(), worldSurfaceY, delta);

        if (origin.getY() >= worldSurfaceY) {
            for (int y = worldSurfaceY; y <= height; y++) {
                mutableBlockPos.set(blockX, y, blockZ);
                chunk.setBlockState(mutableBlockPos, topBlocks[topBlocks.length - 1], false);
                unsafeBoundingBox.encapsulate(mutableBlockPos);
            }
        } else {
            for (int y = worldSurfaceY; y > height; y--) {
                mutableBlockPos.set(blockX, y, blockZ);
                chunk.setBlockState(mutableBlockPos, Blocks.AIR.defaultBlockState(), false);
                unsafeBoundingBox.encapsulate(mutableBlockPos);
            }
        }

        for (int y = 0; y < topBlocks.length; y++) {
            mutableBlockPos.set(blockX, height - y, blockZ);

            chunk.setBlockState(mutableBlockPos, topBlocks[y], false);
        }
    }

    private static BlockState @NotNull [] getSurfaceBlocks(BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int worldSurfaceY, ChunkAccess chunk) {
        BlockState[] topBlocks = new BlockState[]{
                Blocks.GRASS_BLOCK.defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.STONE.defaultBlockState(),
                Blocks.STONE.defaultBlockState()
        };

        for (int y = 0; y < topBlocks.length; y++) {
            mutableBlockPos.set(blockX, worldSurfaceY - 1 - y, blockZ);

            BlockState blockState = chunk.getBlockState(mutableBlockPos);

            if (blockState.isAir() || !blockState.getFluidState().isEmpty()) {
                continue;
            }

            topBlocks[y] = blockState;
        }
        return topBlocks;
    }
}