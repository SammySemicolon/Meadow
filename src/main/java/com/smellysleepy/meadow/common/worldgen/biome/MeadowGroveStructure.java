package com.smellysleepy.meadow.common.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
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

        int blockX = chunkPos.getBlockX(random.nextInt(16));
        int blockZ = chunkPos.getBlockZ(random.nextInt(16));

        int lakeRadius = random.nextIntBetweenInclusive(96, 128);
        var randomState = context.randomState();
        var chunkGenerator = context.chunkGenerator();
        var levelHeightAccessor = context.heightAccessor();


        int baseHeight = chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.OCEAN_FLOOR_WG, levelHeightAccessor, randomState);

//        if (baseHeight <= chunkGenerator.getSeaLevel() || baseHeight < chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState)) { // Underwater lakes don't make sense. Avoid Aquifers.
//            return Optional.empty();
//        }

        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, (piecesBuilder) -> {
            BlockPos lakeOrigin = new BlockPos(blockX, baseHeight, blockZ);
            createLakePieces(context, piecesBuilder, lakeRadius, lakeOrigin, 20, levelHeightAccessor);
        });
    }

    private static void createLakePieces(GenerationContext context, StructurePiecesBuilder piecesBuilder, int arenaFloorRadius, BlockPos arenaOrigin, int lakeDepth, LevelHeightAccessor levelHeightAccessor) {
        ChunkPos chunkPos = context.chunkPos();
        int radius = SectionPos.blockToSectionCoord(arenaFloorRadius) + 1;
        for (int chunkX = -radius; chunkX <= radius; chunkX++) {
            for (int chunkZ = -radius; chunkZ <= radius; chunkZ++) {
                BlockPos chunkWorldPos = new BlockPos(
                        SectionPos.sectionToBlockCoord(chunkPos.x + chunkX),
                        arenaOrigin.getY(),
                        SectionPos.sectionToBlockCoord(chunkPos.z + chunkZ));
                BoundingBox boundingBox = new BoundingBox(
                        chunkWorldPos.getX(), levelHeightAccessor.getMinBuildHeight(), chunkWorldPos.getZ(),
                        chunkWorldPos.getX() + 15, levelHeightAccessor.getMaxBuildHeight(), chunkWorldPos.getZ() + 15
                );
                piecesBuilder.addPiece(new MeadowGrovePiece(arenaOrigin, arenaFloorRadius, lakeDepth, boundingBox));
            }
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return MeadowStructureTypes.MEADOW_GROVE.get();
    }
}
