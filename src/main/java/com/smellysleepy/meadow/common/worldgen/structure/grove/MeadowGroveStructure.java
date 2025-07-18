package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.biome.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.helper.*;
import com.smellysleepy.meadow.registry.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.*;
import net.minecraft.world.level.levelgen.synth.*;
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

        List<MeadowGroveBiomeType> enabledBiomes = GroveBiomeHelper.pickBiomes(random);
        int groveRadius = random.nextIntBetweenInclusive(128, 256);
        int groveHeight = random.nextIntBetweenInclusive(40, 56);
        int groveDepth = random.nextIntBetweenInclusive(16, 20);
        float biomeSize = RandomHelper.randomBetween(random, Easing.CUBIC_IN_OUT, 0.01f, 0.03f);
        float inclineSize = RandomHelper.randomBetween(random, Easing.CUBIC_IN_OUT, 0.1f, 0.2f);
        int minimumInclineHeight = RandomHelper.randomBetween(random, Easing.CUBIC_IN_OUT, 4, 8);
        int maximumInclineHeight = RandomHelper.randomBetween(random, Easing.CUBIC_IN_OUT, 12, 24);

        var groveData = new MeadowGroveGenerationConfiguration(groveCenter, enabledBiomes, groveRadius, groveHeight, groveDepth, biomeSize, inclineSize, minimumInclineHeight, maximumInclineHeight);
        return Optional.of(new Structure.GenerationStub(groveCenter, (b) -> createGrovePieces(b, context, groveData)));
    }

    private void createGrovePieces(StructurePiecesBuilder piecesBuilder, GenerationContext context, MeadowGroveGenerationConfiguration config) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(context.seed()));
        var levelHeightAccessor = context.heightAccessor();
        var chunkPos = context.chunkPos();
        int radius = SectionPos.blockToSectionCoord(config.getGroveRadius()) + 1;

        MeadowGroveGenerationData generationData = config.getGenerationData();
        for (int chunkX = -radius; chunkX <= radius; chunkX++) {
            for (int chunkZ = -radius; chunkZ <= radius; chunkZ++) {
                ChunkPos offsetChunkPos = new ChunkPos(chunkPos.x + chunkX, chunkPos.z + chunkZ);
                int x = SectionPos.sectionToBlockCoord(offsetChunkPos.x);
                int z = SectionPos.sectionToBlockCoord(offsetChunkPos.z);
                var boundingBox = getChunkBoundingBox(levelHeightAccessor, x, z);
                generationData.compute(config, noiseSampler, offsetChunkPos);
                piecesBuilder.addPiece(new MeadowGrovePiece(config, boundingBox));
            }
        }

        generationData.propagate(config);
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
