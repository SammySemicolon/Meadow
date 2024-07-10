package com.smellysleepy.meadow.common.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MeadowGroveStructure extends Structure {

    public static final Codec<MeadowGroveStructure> CODEC = RecordCodecBuilder.<MeadowGroveStructure>mapCodec(builder ->
            builder.group(
                    settingsCodec(builder)
            ).apply(builder, MeadowGroveStructure::new)
    ).codec();

    public MeadowGroveStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        RandomState randomState = context.randomState();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        var levelHeightAccessor = context.heightAccessor();


        int blockX = chunkPos.getBlockX(random.nextInt(16));
        int blockZ = chunkPos.getBlockZ(random.nextInt(16));

        int baseHeight = chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);

        int blockY = baseHeight - random.nextIntBetweenInclusive(64, 128);
        var groveCenter = new BlockPos(blockX, blockY, blockZ);

        int groveRadius = (int) (random.nextIntBetweenInclusive(128, 192) * (1+random.nextFloat()));
        int groveHeight = random.nextIntBetweenInclusive(16, 20);
        int groveDepth = random.nextIntBetweenInclusive(12, 16);


        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG,
                (b) -> createGrovePieces(context, b, levelHeightAccessor, groveCenter, groveRadius, groveHeight, groveDepth));
    }

    @Override
    public void afterPlace(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pChunkGenerator, RandomSource pRandom, BoundingBox pBoundingBox, ChunkPos pChunkPos, PiecesContainer pPieces) {
        super.afterPlace(pLevel, pStructureManager, pChunkGenerator, pRandom, pBoundingBox, pChunkPos, pPieces);
    }

    private static void createGrovePieces(GenerationContext context, StructurePiecesBuilder piecesBuilder, LevelHeightAccessor levelHeightAccessor, BlockPos groveCenter, int groveRadius, int groveHeight, int groveDepth) {
        var chunkPos = context.chunkPos();
        int radius = SectionPos.blockToSectionCoord(groveRadius) + 1;
        for (int chunkX = -radius; chunkX <= radius; chunkX++) {
            for (int chunkZ = -radius; chunkZ <= radius; chunkZ++) {
                int x = SectionPos.sectionToBlockCoord(chunkPos.x + chunkX);
                int z = SectionPos.sectionToBlockCoord(chunkPos.z + chunkZ);
                var chunkStartPos = new BlockPos(x, groveCenter.getY(), z);
                BoundingBox boundingBox = new BoundingBox(
                        chunkStartPos.getX(), levelHeightAccessor.getMinBuildHeight(), chunkStartPos.getZ(),
                        chunkStartPos.getX() + 15, levelHeightAccessor.getMaxBuildHeight(), chunkStartPos.getZ() + 15
                );
                piecesBuilder.addPiece(new MeadowGrovePiece(groveCenter, groveRadius, groveHeight, groveDepth, boundingBox));
            }
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return MeadowStructureTypes.MEADOW_GROVE.get();
    }
}
