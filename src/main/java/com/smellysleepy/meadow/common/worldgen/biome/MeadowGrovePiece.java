package com.smellysleepy.meadow.common.worldgen.biome;

import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
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
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.List;

public class MeadowGrovePiece extends StructurePiece {

    private final BlockPos groveCenter;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    protected MeadowGrovePiece(BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveCenter = groveCenter;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveHeight;
    }

    public MeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveCenter = NbtUtils.readBlockPos(tag.getCompound("origin"));
        this.groveRadius = tag.getInt("groveRadius");
        this.groveHeight = tag.getInt("groveHeight");
        this.groveDepth = tag.getInt("groveDepth");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("groveCenter", NbtUtils.writeBlockPos(groveCenter));
        tag.putInt("groveRadius", groveRadius);
        tag.putInt("groveHeight", groveHeight);
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

                double noise = getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;
                double depthNoise = getNoise(noiseSampler, blockZ, blockX, 100000, 0.025f) * 0.5f;

                double localRadius = (int) Mth.clampedLerp(groveRadius * 0.4, groveRadius, noise);
                double localHeight = (int) Mth.clampedLerp(groveHeight * 0.5, groveHeight, noise);
                double localDepth = (int) Mth.clampedLerp(groveDepth * 0.6, groveDepth, depthNoise);

                int blendWidth = 43;

                int rimSize = (int) (localRadius * 0.05);

                BlockState[] topBlocks = getCeilingBlocks();
                BlockState[] bottomBlocks = getSurfaceBlocks();

                buildGrove(worldGenLevel, chunk, stateProvider, mutableBlockPos, random,
                        noiseSampler, unsafeBoundingBox, topBlocks, bottomBlocks,
                        localRadius, localHeight, localDepth, blendWidth, rimSize, blockX, blockZ);
            }
        }
        if (unsafeBoundingBox.valid()) {
            this.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }

    private void buildGrove(WorldGenLevel worldGenLevel, ChunkAccess chunk, BlockStateProvider stateProvider, BlockPos.MutableBlockPos pos,
                            RandomSource random, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockState[] ceilingCovering, BlockState[] surfaceCovering,
                            double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {
        if (!pos.setY(groveCenter.getY()).closerThan(groveCenter, localRadius - blendWidth - rimSize)) {
            return;
        }

            int centerY = groveCenter.getY();
        double offset = localRadius - blendWidth - rimSize;
        double delta = (pos.setY(centerY).distSqr(groveCenter) - Mth.square(offset)) / Mth.square(localRadius - offset);

        int upperDepth = getUpperDepth(noiseSampler, pos, localHeight, delta);
        int lowerDepth = getLowerDepth(noiseSampler, pos, localDepth, delta);

        int ceilingBlocksLength = Math.min(ceilingCovering.length - 1, upperDepth < 6 ? 0 : Mth.floor(upperDepth/3f));
        int surfaceBlocksLength = Math.min(surfaceCovering.length - 1, lowerDepth < 6 ? 0 : Mth.floor(lowerDepth/2f));

        int ceilingLimit = centerY + upperDepth;
        for (int y = centerY; y < ceilingLimit+ceilingBlocksLength; y++) { // top half
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);
            if (y >= ceilingLimit) {
                int index = Mth.clamp(y-ceilingLimit, 0, ceilingBlocksLength);
                chunk.setBlockState(pos, ceilingCovering[index], true);
                continue;
            }

            chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
        }

        int surfaceLimit = centerY - lowerDepth;
        for (int y = centerY; y > surfaceLimit-surfaceBlocksLength; y--) { //bottom half
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);
            if (y <= surfaceLimit) {
                int index = Mth.clamp(surfaceLimit-y, 0, surfaceBlocksLength);
                chunk.setBlockState(pos, surfaceCovering[index], true);
                continue;
            }

            chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
        }
    }

    private int getUpperDepth(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getDepth(noiseSampler, pos, Easing.QUAD_IN, 0.05f, 1f, localHeight, delta);
    }
    private int getLowerDepth(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getDepth(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, 0.5f, localHeight, delta);
    }

    private int getDepth(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float frequency, double depthOffsetScalar, double localHeight, double delta) {
        double depthNoise = getNoise(noiseSampler, pos.getX(), pos.getZ(), 100000, frequency) * 0.5f;

        double depthOffset = easing.clamped(depthNoise, 0, (localHeight*depthOffsetScalar));
        return (int) Easing.SINE_IN_OUT.clamped(-delta, 0, groveHeight/2f+depthOffset);

//        double depthOffset = Mth.clampedLerp(0, localHeight * depthOffsetScalar, depthNoise);
//        return (int) Mth.clampedLerp(0, groveHeight/2f+depthOffset, -delta);
    }

    public double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseFrequency) {
        return getNoise(noiseSampler, blockX, blockZ, 0, noiseFrequency);
    }

    public double getNoise(ImprovedNoise noiseSampler, int blockX, int blockZ, int offset, float noiseFrequency) {
        return noiseSampler.noise((blockX+offset) * noiseFrequency, 0, (blockZ+offset) * noiseFrequency) + 1;
    }

    private static BlockState[] getCeilingBlocks(BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int startY, int direction, ChunkAccess chunk) {
        BlockState[] blockStates = {
                Blocks.STONE.defaultBlockState(),
                Blocks.STONE.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.STONE.defaultBlockState()
        };
    }

    private static BlockState[] getSurfaceBlocks(BlockPos.MutableBlockPos mutableBlockPos, int blockX, int blockZ, int startY, int direction, ChunkAccess chunk) {
        BlockState[] blockStates = {
                MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.DIRT.defaultBlockState(),
                Blocks.STONE.defaultBlockState(),
                Blocks.TUFF.defaultBlockState(),
                Blocks.STONE.defaultBlockState()
        };
        
    }
    private static BlockState[] getFillerBlocks(BlockPos.MutableBlockPos mutableBlockPos, BlockState[] blockSet, int blockX, int blockZ, int startY, int direction, ChunkAccess chunk) {
        for (int y = 0; y < blockSet.length; y++) {
            mutableBlockPos.set(blockX, startY + y * direction, blockZ);

            BlockState blockState = chunk.getBlockState(mutableBlockPos);

            if (blockState.isAir() || !blockState.getFluidState().isEmpty()) {
                continue;
            }
            blockSet[y] = blockState;
        }
        return blockSet;
    }
}