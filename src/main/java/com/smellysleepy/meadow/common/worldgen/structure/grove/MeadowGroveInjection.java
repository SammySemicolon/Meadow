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

    public static void injectGrove(ServerLevel serverLevel, ChunkAccess access, WorldGenRegion worldGenRegion) {
        ChunkPos chunkpos = access.getPos();

        for (int j = -8; j <= 8; ++j) {
            for (int k = -8; k <= 8; ++k) {
                ChunkPos chunkPos = new ChunkPos(chunkpos.x + j, chunkpos.z + k);
                ChunkAccess chunkaccess = worldGenRegion.getChunk(chunkPos.x, chunkPos.z);

                int x = SectionPos.sectionToBlockCoord(chunkPos.x);
                int z = SectionPos.sectionToBlockCoord(chunkPos.z);
                BoundingBox boundingBox = new BoundingBox(
                        x, serverLevel.getMinBuildHeight(), z,
                        x + 15, serverLevel.getMaxBuildHeight(), z + 15
                );

                StructureStart structureStart = chunkaccess.getStartForStructure(worldGenRegion.registryAccess().registryOrThrow(Registries.STRUCTURE).getOrThrow(MeadowStructures.MEADOW_GROVE));
                if (structureStart != null) {
                    for (StructurePiece piece : structureStart.getPieces()) {
                        if (piece.getBoundingBox().intersects(boundingBox)) {
                            if (piece instanceof MeadowGrovePiece grovePiece) {
                                MeadowGrovePiece.carveGroveShape(grovePiece, worldGenRegion, worldGenRegion.getRandom(), chunkaccess, chunkPos);
                            }
                        }
                    }
                }
            }
        }
    }
}
