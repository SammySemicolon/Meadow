package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.ArrayList;
import java.util.List;

public class MeadowGroveDecoratorPiece extends StructurePiece {

    public List<Pair<BlockPos, ResourceLocation>> bufferedFeatures;

    protected MeadowGroveDecoratorPiece(MeadowGrovePiece source, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE_DECORATOR.get(), 0, boundingBox);
        this.bufferedFeatures = source.bufferedFeatures;
    }

    public MeadowGroveDecoratorPiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE_DECORATOR.get(), tag);
        CompoundTag features = tag.getCompound("features");
        int count = features.getInt("count");

        bufferedFeatures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            CompoundTag feature = features.getCompound("feature_"+i);
            BlockPos blockPos = NbtUtils.readBlockPos(feature.getCompound("featurePosition"));
            ResourceLocation resourceKeyId = new ResourceLocation(feature.getString("featureType"));
            bufferedFeatures.add(Pair.of(blockPos, resourceKeyId));
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
        CompoundTag features = new CompoundTag();

        features.putInt("count", bufferedFeatures.size());
        for (int i = 0; i < bufferedFeatures.size(); i++) {
            var bufferedFeature = bufferedFeatures.get(i);
            CompoundTag feature = new CompoundTag();

           feature.put("featurePosition", NbtUtils.writeBlockPos(bufferedFeature.getFirst()));
           feature.putString("featureType", bufferedFeature.getFirst().toString());
           features.put("feature_"+i, feature);
        }
        compoundTag.put("features", features);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource randomSource, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        for (Pair<BlockPos, ResourceLocation> pair : bufferedFeatures) {
            var pos = pair.getFirst();
            var location = pair.getSecond();
            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(Registries.CONFIGURED_FEATURE, location);
            Holder<ConfiguredFeature<?, ?>> holder = worldGenLevel.registryAccess()
                    .registryOrThrow(Registries.CONFIGURED_FEATURE)
                    .getHolder(key).orElseThrow();
            holder.get().place(worldGenLevel, chunkGenerator, randomSource, pos);
        }
    }
}
