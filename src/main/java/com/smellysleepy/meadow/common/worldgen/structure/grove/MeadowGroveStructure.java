package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.old.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

public class MeadowGroveStructure extends Structure {

    public static final Codec<MeadowGroveStructure> CODEC = RecordCodecBuilder.<MeadowGroveStructure>mapCodec(builder ->
            builder.group(settingsCodec(builder))
                    .apply(builder, MeadowGroveStructure::new)
    ).codec();

    public MeadowGroveStructure(StructureSettings settings) {
        super(settings);
    }


    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        var chunkPos = context.chunkPos();
        var random = context.random();
        var randomState = context.randomState();
        var chunkGenerator = context.chunkGenerator();
        var levelHeightAccessor = context.heightAccessor();

        int blockX = chunkPos.getBlockX(random.nextInt(16));
        int blockZ = chunkPos.getBlockZ(random.nextInt(16));

        int baseHeight = chunkGenerator.getBaseHeight(blockX, blockZ, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);
        int heightOffset = RandomHelper.randomBetween(random, Easing.SINE_IN, 64, 128);

        int blockY = 160;
//        int blockY = baseHeight - heightOffset;
        var groveCenter = new BlockPos(blockX, blockY, blockZ);

        int groveRadius = random.nextIntBetweenInclusive(192, 256);
        int groveHeight = random.nextIntBetweenInclusive(32, 40);
        int groveDepth = random.nextIntBetweenInclusive(16, 20);
        var groveData = new MeadowGroveGenerationData(groveCenter, groveRadius, groveHeight, groveDepth);
        return Optional.of(new Structure.GenerationStub(groveCenter, (b) -> createGrovePieces(context, b, levelHeightAccessor, groveData)));
    }

    private void createGrovePieces(GenerationContext context, StructurePiecesBuilder piecesBuilder, LevelHeightAccessor levelHeightAccessor, MeadowGroveGenerationData groveData) {
        var chunkPos = context.chunkPos();
        int radius = SectionPos.blockToSectionCoord(groveData.getGroveRadius()) + 1;

        for (int chunkX = -radius; chunkX <= radius; chunkX++) {
            for (int chunkZ = -radius; chunkZ <= radius; chunkZ++) {
                int x = SectionPos.sectionToBlockCoord(chunkPos.x + chunkX);
                int z = SectionPos.sectionToBlockCoord(chunkPos.z + chunkZ);
                var boundingBox = getChunkBoundingBox(levelHeightAccessor, x, z);
                piecesBuilder.addPiece(new MeadowGrovePiece(groveData, boundingBox));
            }
        }
    }

    @Override
    public @NotNull StructureType<?> type() {
        return MeadowStructureTypes.MEADOW_GROVE.get();
    }


    public BoundingBox getChunkBoundingBox(LevelHeightAccessor level, int x, int z) {
        var chunkStartPos = new BlockPos(x, 0, z);
        int minHeight = level.getMinBuildHeight();
        int maxHeight = level.getMaxBuildHeight();
        return new BoundingBox(
                chunkStartPos.getX(), minHeight, chunkStartPos.getZ(),
                chunkStartPos.getX() + 15, maxHeight, chunkStartPos.getZ() + 15
        );
    }
}
