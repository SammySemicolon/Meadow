package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.smellysleepy.meadow.registry.worldgen.MeadowStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class MeadowGroveInjection {

    public static void injectGrove(ServerLevel serverLevel, ChunkAccess chunkAccess, WorldGenRegion worldGenRegion) {
        ChunkPos chunkPos = chunkAccess.getPos();
        int x = SectionPos.sectionToBlockCoord(chunkPos.x);
        int z = SectionPos.sectionToBlockCoord(chunkPos.z);
        BoundingBox boundingBox = new BoundingBox(
                x, serverLevel.getMinBuildHeight(), z,
                x + 15, serverLevel.getMaxBuildHeight(), z + 15
        );

        StructureStart structureStart = chunkAccess.getStartForStructure(worldGenRegion.registryAccess().registryOrThrow(Registries.STRUCTURE).getOrThrow(MeadowStructures.MEADOW_GROVE));
        if (structureStart != null) {
            for (StructurePiece piece : structureStart.getPieces()) {
                if (piece.getBoundingBox().intersects(boundingBox)) {
                    if (piece instanceof MeadowGrovePiece grovePiece) {
                        MeadowGrovePiece.carveGroveShape(grovePiece, serverLevel, worldGenRegion.getRandom(), chunkAccess, chunkPos);
                    }
                }
            }
        }
    }
}
