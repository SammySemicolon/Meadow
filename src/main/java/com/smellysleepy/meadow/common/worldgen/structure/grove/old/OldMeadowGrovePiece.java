package com.smellysleepy.meadow.common.worldgen.structure.grove.old;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import com.smellysleepy.meadow.common.worldgen.structure.grove.MeadowGroveFeatureDistribution.FeatureDataGetter;
import com.smellysleepy.meadow.common.worldgen.structure.grove.old.area.*;
import com.smellysleepy.meadow.registry.common.block.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraftforge.common.Tags;
import team.lodestar.lodestone.systems.easing.Easing;

import javax.annotation.Nullable;
import java.util.*;

public class OldMeadowGrovePiece extends StructurePiece {

    private final BlockPos groveCenter;
    private final List<SpecialMeadowRegion> specialRegions;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    public boolean hasGenerated;

    public final HashMap<BlockPos, ResourceLocation> bufferedFeatures = new HashMap<>();

    protected OldMeadowGrovePiece(BlockPos groveCenter, List<SpecialMeadowRegion> specialRegions, int groveRadius, int groveHeight, int groveDepth, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveCenter = groveCenter;
        this.specialRegions = specialRegions;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveDepth;
    }

    public OldMeadowGrovePiece(CompoundTag tag) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), tag);
        this.groveCenter = NbtUtils.readBlockPos(tag.getCompound("groveCenter"));
        this.groveRadius = tag.getInt("groveRadius");
        this.groveHeight = tag.getInt("groveHeight");
        this.groveDepth = tag.getInt("groveDepth");
        this.hasGenerated = tag.getBoolean("hasGenerated");

        this.specialRegions = new ArrayList<>();

        CompoundTag regionsTag = tag.getCompound("regions");
        int regionCount = regionsTag.getInt("count");
        for (int i = 0; i < regionCount; i++) {
            CompoundTag compound = regionsTag.getCompound("specialRegion_" + i);
            ResourceLocation type = ResourceLocation.tryParse(compound.getString("type"));
            SpecialMeadowRegion region = MeadowRegionTypes.DECODER.get(type).apply(compound);
            this.specialRegions.add(region);
        }

        CompoundTag features = tag.getCompound("features");
        int featureCount = features.getInt("count");
        bufferedFeatures.clear();
        for (int i = 0; i < featureCount; i++) {
            CompoundTag feature = features.getCompound("feature_"+i);
            BlockPos blockPos = NbtUtils.readBlockPos(feature.getCompound("featurePosition"));
            ResourceLocation resourceKeyId = new ResourceLocation(feature.getString("featureType"));
            bufferedFeatures.put(blockPos, resourceKeyId);
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("groveCenter", NbtUtils.writeBlockPos(groveCenter));
        tag.putInt("groveRadius", groveRadius);
        tag.putInt("groveHeight", groveHeight);
        tag.putInt("groveDepth", groveDepth);
        tag.putBoolean("hasGenerated", hasGenerated);

        CompoundTag regionsTag = new CompoundTag();
        regionsTag.putInt("count", specialRegions.size());
        for (int i = 0; i < specialRegions.size(); i++) {
            var region = specialRegions.get(i);
            regionsTag.put("specialRegion_" + i, region.serialize());
        }

        tag.put("regions", regionsTag);

        CompoundTag featuresTag = new CompoundTag();

        featuresTag.putInt("count", bufferedFeatures.size());
        int i = 0;
        for (Map.Entry<BlockPos, ResourceLocation> entry : bufferedFeatures.entrySet()) {
            CompoundTag feature = new CompoundTag();

            feature.put("featurePosition", NbtUtils.writeBlockPos(entry.getKey()));
            feature.putString("featureType", entry.getValue().toString());
            featuresTag.put("feature_"+i, feature);
            i++;
        }
        tag.put("features", featuresTag);

    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource randomSource, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        carveGroveShape(this, worldGenLevel, randomSource, worldGenLevel.getChunk(chunkPos.x, chunkPos.z), chunkPos);
        for (Map.Entry<BlockPos, ResourceLocation> entry : bufferedFeatures.entrySet()) {
            var pos = entry.getKey();
            var location = entry.getValue();
            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(Registries.CONFIGURED_FEATURE, location);
            Holder<ConfiguredFeature<?, ?>> holder = worldGenLevel.registryAccess()
                    .registryOrThrow(Registries.CONFIGURED_FEATURE)
                    .getHolder(key).orElseThrow();
            holder.get().place(worldGenLevel, chunkGenerator, randomSource, pos);
        }
        bufferedFeatures.clear();
    }

    public static void carveGroveShape(OldMeadowGrovePiece grovePiece, WorldGenLevel worldGenLevel, RandomSource random, ChunkAccess chunk, ChunkPos chunkPos) {
        grovePiece.hasGenerated = true;
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutableBlockPos = new BlockPos.MutableBlockPos();

        int groveRadius = grovePiece.groveRadius;
        int groveHeight = grovePiece.groveHeight;
        int groveDepth = grovePiece.groveDepth;

        var surfaceFeatureDistribution = MeadowGroveFeatureDistribution.makeDistribution(random);
        var rampFeatureDistribution = MeadowGroveFeatureDistribution.makeDistribution(random);

        FeatureDataGetter featureGetter = new FeatureDataGetter(surfaceFeatureDistribution);
        FeatureDataGetter rampFeatureGetter = new FeatureDataGetter(rampFeatureDistribution);
        for (int i = 0; i < 256; i++) {
            int blockX = chunkPos.getBlockX(i%16);
            int blockZ = chunkPos.getBlockZ(i/16);

            mutableBlockPos.set(blockX, 0, blockZ);

            double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;
            double depthNoise = WorldgenHelper.getNoise(noiseSampler, blockZ, blockX, 100000, 0.025f) * 0.5f;

            double localRadius = (int) Mth.clampedLerp(groveRadius * 0.4, groveRadius, noise);
            double localHeight = (int) Mth.clampedLerp(groveHeight * 0.5, groveHeight, noise);
            double localDepth = (int) Mth.clampedLerp(groveDepth * 0.6, groveDepth, depthNoise);

            int blendWidth = 48;
            int rimSize = (int) (localRadius * 0.2);

            grovePiece.buildGroveLayer(
                    chunk, noiseSampler, unsafeBoundingBox, mutableBlockPos, random,
                    featureGetter, rampFeatureGetter, localRadius, localHeight, localDepth, blendWidth, rimSize);
            featureGetter.nextIndex();
            rampFeatureGetter.nextIndex();
        }
        if (unsafeBoundingBox.valid()) {
            grovePiece.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }


    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(ChunkAccess chunk, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockPos.MutableBlockPos pos, RandomSource random,
                                                                                           FeatureDataGetter featureGetter, FeatureDataGetter rampFeatureGetter, double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize) {

        int centerY = groveCenter.getY();
        pos.setY(centerY);
        double shapeRadius = MeadowGroveShape.CIRCLE.distSqr(groveCenter, pos, localRadius);
        double radius = localRadius * shapeRadius;
        double offset = radius - blendWidth - rimSize;
        if (!pos.closerThan(groveCenter, radius)) {
            return Optional.empty();
        }
        double distance = pos.distSqr(groveCenter);
        double sqrtDistance = Math.sqrt(distance);
        double delta = (distance - Mth.square(offset)) / Mth.square(radius - offset);
        double flatDelta = -(distance / Mth.square(radius - offset));
        double shellDelta = Mth.clampedLerp(-1, 0, Math.pow((sqrtDistance / (offset+rimSize)) * (sqrtDistance / radius), 3f));
        int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
        int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);
        int flatDepth = getGroveDepth(noiseSampler, pos, localDepth, flatDelta);
        int ceilingAirPocketEndPoint = centerY + height;
        int surfaceAirPocketEndPoint = centerY - depth;
        if (delta > 0) {
            flatDepth = 0;
            shellDelta += delta*0.8f;
        }
        int shellHeight = getGroveHeight(noiseSampler, pos, localHeight, shellDelta);
        int shellDepth = getGroveDepth(noiseSampler, pos, localDepth, shellDelta);

        double calcificationNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 25000, 0.02f) * 0.5f;
        var calcifiedRegionOptional = getClosestRegion(CalcifiedRegion.class, pos.getX(), pos.getZ(), calcificationNoise, radius);

        double lakeNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 50000, 0.01f) * 0.5f;
        double relativeLakeRadius = radius * 0.7f + groveRadius * 0.3f;
        var lakeRegionOptional = getClosestRegion(LakeRegion.class, pos.getX(), pos.getZ(), lakeNoise, relativeLakeRadius);

        var ceilingPatternBlock = chooseSediment(noiseSampler, pos.getX(), ceilingAirPocketEndPoint, pos.getZ());
        var ceilingPatternData = getCeilingPattern(chunk, random, noiseSampler, ceilingPatternBlock, pos, sqrtDistance, offset, ceilingAirPocketEndPoint);
        var ceilingPattern = ceilingPatternData.getFirst();
        var ceilingFeature = ceilingPatternData.getSecond();

        var surfacePatternData = getSurfaceBlockPattern(chunk, noiseSampler, pos, calcifiedRegionOptional.orElse(null), sqrtDistance, offset, surfaceAirPocketEndPoint);
        var surfacePattern = surfacePatternData.getFirst();
        var surfaceFeature = surfacePatternData.getSecond();

        boolean placeWater = false;
        double waterDelta = 0;
        int waterStartingPoint = 0;
        boolean useLakeGrass = false;
        double lakeGrassDelta = 0;

        int ceilingHeight = height < 2 ? 0 : (int) Mth.clampedLerp(Mth.floor((2 + height) * 2), 5, height/10f);
        int surfaceDepth = depth < 6 ? 0 : Mth.floor(depth / 2f);

        if (lakeRegionOptional.isPresent()) {
            var pair = lakeRegionOptional.get();
            var lakeRegion = pair.getFirst();
            double lakeDelta = pair.getSecond();
            int terrainDepth = (int) (groveDepth * lakeRegion.getSurfaceLevel());
            int waterDepth = (int) (groveDepth * lakeRegion.getWaterLevel());
            int lakeDepth;
            if (lakeDelta < 0.2f) {
                lakeDepth = depth - Mth.floor(Easing.SINE_IN.ease(lakeDelta, 0, depth - terrainDepth, 0.2f));
            } else {
                double lakeCarverDepth = lakeRegion.getLakeDepth() * (waterDepth + flatDepth);
                float carverDelta = (lakeDelta > 0.7f ? Easing.CUBIC_OUT : Easing.BACK_IN_OUT).ease((lakeDelta - 0.2f) / 0.8f, 0, 1);
                lakeDepth = Mth.floor(Easing.SINE_OUT.ease(carverDelta, terrainDepth, lakeCarverDepth));
            }
            surfaceAirPocketEndPoint = centerY - lakeDepth;
            float depthOffsetDelta = Easing.CUBIC_OUT.clamped(lakeDelta * 4f, 0, 1);
            if (lakeDelta > 0.8f) {
                depthOffsetDelta = Easing.CUBIC_OUT.clamped((lakeDelta-0.8f)/0.2f, depthOffsetDelta, 0);
            }
            surfaceDepth += (int) Mth.lerp(depthOffsetDelta, 6, 12) - lakeDepth/3;
            boolean isNearWater = lakeDelta >= 0.3f;
            if (isNearWater) {
                useLakeGrass = true;
                lakeGrassDelta = (lakeDelta-0.3f) / 0.7f;
            }
            if (lakeDelta >= 0.5f && lakeDepth > waterDepth) {
                placeWater = true;
                waterDelta = (lakeDelta-0.5f)/0.5f;
                waterStartingPoint = centerY - waterDepth;
            }
            if (calcifiedRegionOptional.isEmpty() || calcifiedRegionOptional.get().getSecond() < 0.03f) {
                if (isNearWater) {
                    Block block = Blocks.GRASS_BLOCK;
                    if (lakeGrassDelta > 0.5f) {
                        block = Blocks.STONE;
                    } else if (lakeGrassDelta > 0.4f) {
                        block = Blocks.GRAVEL;
                    } else if (lakeGrassDelta > 0.3f && placeWater) {
                        block = Blocks.DIRT;
                    } else if (lakeGrassDelta < 0.1f) {
                        block = Blocks.ROOTED_DIRT;
                    }
                    surfacePattern.set(0, block.defaultBlockState());
                }
            }
        }

        int highestCeilingPoint = ceilingAirPocketEndPoint + ceilingHeight;
        int lowestSurfacePoint = surfaceAirPocketEndPoint - surfaceDepth;

        double rampNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 75000, 0.01f) / 2;
        int rampStartingHeight = centerY - flatDepth;
        int rampHeight = Mth.ceil(flatDepth * Easing.QUINTIC_IN_OUT.clamped(rampNoise, 0.8f, 1.2f));
        List<BlockState> rampBlockPattern = getRampBlockPattern(chunk, noiseSampler, pos, sqrtDistance, offset, rampStartingHeight).getFirst();

        createHorizontalShell(chunk, pos, unsafeBoundingBox, shellDelta, ceilingPattern, shellHeight, centerY, surfacePattern, shellDepth, centerY);

        if (!pos.closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        carveOutShape(chunk, random, pos, unsafeBoundingBox, featureGetter, centerY,
                ceilingPattern, highestCeilingPoint, ceilingAirPocketEndPoint, ceilingHeight,
                surfacePattern, lowestSurfacePoint, surfaceAirPocketEndPoint, surfaceDepth,
                placeWater, waterDelta, useLakeGrass, lakeGrassDelta, waterStartingPoint,
                calcifiedRegionOptional.orElse(null), surfaceFeature, ceilingFeature);
        if (!pos.closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        addRamps(chunk, noiseSampler, random, pos, rampBlockPattern, rampFeatureGetter, rampStartingHeight, pos.getX(), pos.getZ(), rampHeight, rampNoise, sqrtDistance, offset);
        return Optional.empty();
    }

    private void createHorizontalShell(ChunkAccess chunk, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, double shellDelta,
                                       List<BlockState> ceilingPattern, int shellHeight, int topShellStart,
                                       List<BlockState> surfacePattern, int shellDepth, int bottomShellStart) {
        //Top half of horizontal shell
        if (shellHeight > 0) {
            int horizontalShellEnd = topShellStart + shellHeight;
            for (int y = topShellStart; y < horizontalShellEnd; y++) {
                pos.setY(y);
                unsafeBoundingBox.encapsulate(pos);
                int index = Mth.clamp(y - topShellStart, 0, ceilingPattern.size()-1);
                if (shellDelta > -0.3f && chunk.getBlockState(pos).isAir()) {
                    continue;
                }
                chunk.setBlockState(pos, ceilingPattern.get(index), true);
            }
        }
        //Bottom half of horizontal shell
        if (shellDepth > 0) {
            int horizontalShellEnd = bottomShellStart - shellDepth;
            for (int y = bottomShellStart; y > horizontalShellEnd; y--) {
                pos.setY(y);
                unsafeBoundingBox.encapsulate(pos);
                int index = Mth.clamp(bottomShellStart - y, 0, surfacePattern.size()-1);
                chunk.setBlockState(pos, surfacePattern.get(index), true);
            }
        }
    }

    private void carveOutShape(ChunkAccess chunk, RandomSource random, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, FeatureDataGetter featureGetter, int centerY,
                               List<BlockState> ceilingPattern, int highestCeilingPoint, int ceilingAirPocketEndPoint, int ceilingHeight,
                               List<BlockState> surfacePattern, int lowestSurfacePoint, int surfaceAirPocketEndPoint, int surfaceDepth,
                               boolean placeWater, double waterDelta, boolean useLakeGrass, double lakeGrassDelta, int waterStartingPoint,
                               Pair<CalcifiedRegion, Double> calcifiedRegion, ResourceKey<ConfiguredFeature<?, ?>> surfaceFeature, ResourceKey<ConfiguredFeature<?, ?>> ceilingFeature) {
        //Ceiling Carver
        if (centerY < highestCeilingPoint) {
            for (int y = centerY; y < highestCeilingPoint; y++) {
                pos.setY(y);
                unsafeBoundingBox.encapsulate(pos);
                if (y == ceilingAirPocketEndPoint - 1) {
                    Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> feature = null;
                    if (ceilingFeature != null) {
                        feature = Pair.of(pos.immutable(), ceilingFeature);
                    }
                    if (feature != null) {
                        addFeature(feature);
                    }
                }
                else if (y >= ceilingAirPocketEndPoint) {
                    int index = Mth.clamp(y - ceilingAirPocketEndPoint, 0, Math.min(ceilingPattern.size()-1, ceilingHeight));
                    chunk.setBlockState(pos, ceilingPattern.get(index), true);
                    continue;
                }
//                if (chunk.getBlockState(pos).is(MeadowBlockTagRegistry.MEADOW_GROVE_IRREPLACEABLE)) {
//                    continue;
//                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        //Surface Carver
        if (centerY > lowestSurfacePoint) {
            for (int y = centerY; y > lowestSurfacePoint; y--) {
                pos.setY(y);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceAirPocketEndPoint + 1) {
                    Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> feature = null;
                    if (surfaceFeature != null) {
                        feature = Pair.of(pos.immutable(), surfaceFeature);
                    }
                    else if (featureGetter.test(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER)) {
                        feature = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER);
                    }
                    else {
                        if (placeWater) {
                            feature = createWaterFeatures(featureGetter, pos);
                        } else if (useLakeGrass) {
                            feature = createLakeFeatures(featureGetter, pos);
                        } else {
                            feature = createSurfaceFeatures(featureGetter, pos, calcifiedRegion);
                        }
                    }
                    if (feature != null) {
                        addFeature(feature);
                    }
                }

                if (y <= surfaceAirPocketEndPoint) {
                    int index = Mth.clamp(surfaceAirPocketEndPoint - y, 0, Math.min(surfaceDepth, surfacePattern.size()-1));
                    BlockState state = surfacePattern.get(index);
                    chunk.setBlockState(pos, state, true);
                    continue;
                }

                if (placeWater && y <= waterStartingPoint) {
                    chunk.setBlockState(pos, Blocks.WATER.defaultBlockState(), true);
                    continue;
                }
//                if (chunk.getBlockState(pos).is(MeadowBlockTagRegistry.MEADOW_GROVE_IRREPLACEABLE)) {
//                    continue;
//                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
    }

    private void addRamps(ChunkAccess chunk, ImprovedNoise noiseSampler, RandomSource random, BlockPos.MutableBlockPos pos, List<BlockState> rampBlockPattern, FeatureDataGetter featureGetter, int yLevel, int blockX, int blockZ, int rampHeight, double noise, double sqrtDistance, double offset) {
        boolean isAtEdge = sqrtDistance > offset * 0.95f;
        float noiseOffset = Easing.QUAD_OUT.clamped((sqrtDistance - offset * 0.6f) / (offset * 0.4f), 0, 0.3f);
        float topThreshold = 0.6f - noiseOffset;
        float bottomThreshold = 0.625f - noiseOffset;
        noise = (noise + getSmoothedRampNoise(noiseSampler, noise, blockX, blockZ)) * 0.5f;

        if (sqrtDistance < offset * 0.6f) {
            noise *= (sqrtDistance - offset * 0.6f) / offset * 0.6f;
        }

        if (noise >= topThreshold) {
            Easing easing = noise >= (1-topThreshold*topThreshold) ? Easing.QUAD_IN_OUT : Easing.QUARTIC_OUT;
            int topCutoff = Mth.floor(rampHeight * Easing.SINE_OUT.ease(easing.clamped(getRampCutIn(noise, topThreshold), 0, 1), 0, 1));
            int rampTop = yLevel + rampHeight;

            if (!isAtEdge) {
                pos.set(blockX, rampTop + 1, blockZ);
                Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> feature;
                if (featureGetter.test(MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER)) {
                    feature = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER);
                }
                else {
                    feature = createRampFeatures(featureGetter, pos);
                }
                if (feature != null) {
                    addFeature(feature);
                }
            }
            for (int i = 0; i < rampHeight; i++) {
                if (i > topCutoff) {
                    break;
                }
                int y = rampTop - i;
                pos.set(blockX, y, blockZ);
                if (chunk.getBlockState(pos).canBeReplaced()) {
                    BlockState state = Blocks.DIRT.defaultBlockState();
                    if (isAtEdge) {
                        state = Blocks.STONE.defaultBlockState();
                    } else if (i > 0 || chunk.getBlockState(pos.above()).isAir()) {
                        if (i < rampBlockPattern.size()) {
                            state = rampBlockPattern.get(i);
                        } else {
                            state = Blocks.STONE.defaultBlockState();
                        }
                    }
                    chunk.setBlockState(pos, state, true);
                }
            }
        }

        if (noise >= bottomThreshold) {
            pos.set(blockX, yLevel, blockZ);
            while (chunk.getBlockState(pos).canBeReplaced()) {
                pos.move(Direction.DOWN);
                rampHeight++;
            }
            int bottomCutoff = Mth.floor(rampHeight * Easing.QUAD_IN_OUT.clamped(getRampCutIn(noise, bottomThreshold), 0, 1));
            yLevel = pos.getY();
            for (int i = 0; i < rampHeight; i++) {
                if (i > bottomCutoff) {
                    break;
                }
                pos.set(blockX, yLevel + i, blockZ);
            }
        }
    }

    private void addFeature(Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> featureData) {
        bufferedFeatures.put(featureData.getFirst(), featureData.getSecond().location());
    }

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createRampFeatures(FeatureDataGetter featureGetter, BlockPos.MutableBlockPos pos) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = featureGetter.choose(8,
                MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE,
                MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE,
                MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_PATCH
        );
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createLakeFeatures(FeatureDataGetter featureGetter, BlockPos.MutableBlockPos pos) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = featureGetter.choose(4,
                CaveFeatures.MOSS_PATCH,
                TreeFeatures.AZALEA_TREE,
                VegetationFeatures.PATCH_SUGAR_CANE,
                VegetationFeatures.PATCH_TALL_GRASS,
                VegetationFeatures.PATCH_GRASS);
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }
    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createWaterFeatures(FeatureDataGetter featureGetter, BlockPos.MutableBlockPos pos) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = featureGetter.choose(10,
                AquaticFeatures.WARM_OCEAN_VEGETATION,
                MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_LAKE_PATCH,
                MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_LAKE_PATCH,
                MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_LAKE_PATCH
        );
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createSurfaceFeatures(FeatureDataGetter featureGetter, BlockPos.MutableBlockPos pos, @Nullable Pair<CalcifiedRegion, Double> calcification) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;

        if (calcification != null) {
            double delta = calcification.getSecond();
            int leniency = Mth.ceil(delta * 10);
            feature = featureGetter.choose(leniency,
                    MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES, MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES
            );
        } else {
            int featureTypeOffset = groveCenter.getY() - pos.getY();
            float start = groveDepth * 0.3f;
            float midpoint = groveDepth * 0.5f;
            float end = groveDepth * 0.7f;

            feature = featureGetter.choose(
                    MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_PATCH, MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_PATCH
            );
            if (feature == null) {
                if (featureTypeOffset > start && featureTypeOffset < midpoint) {
                    feature = featureGetter.choose(2,
                            MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE, MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE
                    );
                }
                else if (featureTypeOffset < end) {
                    feature = featureGetter.choose(2,
                            MeadowConfiguredFeatureRegistry.CONFIGURED_THIN_ASPEN_TREE, MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_ASPEN_PATCH);
                }
                else if (featureTypeOffset >= end) {
                    feature = featureGetter.choose(4,
                            MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE,
                            MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_ASPEN_PATCH,
                            MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_PATCH
                    );
                }
            }
        }
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    public double getSmoothedRampNoise(ImprovedNoise noiseSampler, double centralNoise, int blockX, int blockZ) {
        int radius = 4;
        int coverage = 1 + radius * 2;
        double[][] noiseValues = new double[coverage][coverage];
        int x = 4;
        int z = 4;
        double highestNoise = centralNoise;

        int emergencyCounter = 0;
        while (true) {
            if (emergencyCounter > coverage) {
                return highestNoise;
            }
            boolean foundHigher = false;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int offsetX = Mth.clamp(x - 1 + i * 2, 0, coverage - 1);
                    int offsetZ = Mth.clamp(z - j + i * 2, 0, coverage - 1);

                    double noise;
                    if (noiseValues[offsetX][offsetZ] == 0) {
                        noise = noiseValues[offsetX][offsetZ] = getRampNoise(noiseSampler, blockX + offsetX, blockZ + offsetZ) / 2;
                    } else {
                        noise = noiseValues[offsetX][offsetZ];
                    }
                    if (noise > highestNoise) {
                        foundHigher = true;
                        highestNoise = noise;
                        x = offsetX;
                        z = offsetZ;
                    }
                }
            }
            emergencyCounter++;
            if (!foundHigher) {
                return highestNoise;
            }
        }
    }

    public double getRampNoise(ImprovedNoise noiseSampler, int blockX, int blockZ) {
        return WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 50000, 0.02f);
    }

    public double getRampCutIn(double noise, float threshold) {
        return (noise - threshold) / (1 - threshold);
    }

    public <T extends SpecialMeadowRegion> Optional<Pair<T, Double>> getClosestRegion(Class<T> regionClass, int blockX, int blockZ, double noise, double radius) {
        if (specialRegions.isEmpty()) {
            return Optional.empty();
        }
        double lowestDistance = Double.MAX_VALUE;
        T closestArea = null;
        for (SpecialMeadowRegion specialRegion : specialRegions) {
            if (regionClass.isInstance(specialRegion)) {
                double distance = specialRegion.getDistance(groveCenter, blockX, blockZ, radius);
                if (distance < lowestDistance) {
                    double threshold = Mth.square(specialRegion.getThreshold(noise, radius));
                    if (distance <= threshold) {
                        lowestDistance = distance;
                        closestArea = regionClass.cast(specialRegion);
                    }
                }
            }
        }
        if (closestArea == null) {
            return Optional.empty();
        }
        double threshold = closestArea.getThreshold(noise, radius);
        double distance = Math.sqrt(lowestDistance);
        if (distance <= threshold) {
            double second = 1 - (distance / threshold);
            return Optional.of(Pair.of(closestArea, second));
        }
        return Optional.empty();
    }

    private int getGroveHeight(ImprovedNoise noiseSampler, BlockPos pos, double localHeight, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.QUAD_IN, 0.05f, 1f, localHeight, groveHeight, delta);
    }

    private int getGroveDepth(ImprovedNoise noiseSampler, BlockPos pos, double localDepth, double delta) {
        return getVerticalSize(noiseSampler, pos, Easing.EXPO_IN_OUT, 0.025f, 0.5f, localDepth, groveDepth, delta);
    }

    private int getVerticalSize(ImprovedNoise noiseSampler, BlockPos pos, Easing easing, float frequency, double depthOffsetScalar, double value, double total, double delta) {
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 100000, frequency);
        for (int i = 0; i < 4; i++) {
            var direction = Direction.from2DDataValue(i);
            depthNoise += WorldgenHelper.getNoise(noiseSampler, pos.getX()+direction.getStepX(), pos.getZ()+direction.getStepZ(), 100000, frequency);
        }

        double depthOffset = easing.clamped(depthNoise * 0.1f, 0, (value * depthOffsetScalar));
        return (int) Easing.SINE_IN_OUT.clamped(-delta, 0, total / 2f + depthOffset);
    }

    public static BlockState chooseSediment(ImprovedNoise noiseSampler, int blockX, int startingY, int blockZ) {
        float yScalar = (Mth.clamp(startingY, -50, 50) + 50) / 100f;
        BlockState rock = Blocks.STONE.defaultBlockState();
        if (yScalar != 0 && yScalar != 1) {
            double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.2f);
            if (noise > 1 - yScalar && noise < 1 + yScalar) {
                rock = Blocks.DEEPSLATE.defaultBlockState();
            }
        } else if (yScalar == 0) {
            rock = Blocks.DEEPSLATE.defaultBlockState();
        }
        return rock;
    }

    private Pair<List<BlockState>, ResourceKey<ConfiguredFeature<?, ?>>> getCeilingPattern(ChunkAccess chunk, RandomSource random, ImprovedNoise noiseSampler, BlockState patternState, BlockPos.MutableBlockPos pos, double sqrtDistance, double offset, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;

        double noise = (WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 0.15f) * 0.15f
                + WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 0.3f) * 0.35f)
                * Easing.QUINTIC_OUT.clamped(WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 0.125f) * 0.5f, 0,1);
        double crackDelta = makeCracks(noiseSampler, pos.getX(), pos.getZ(), sqrtDistance, offset, 0.5f);
        double calcification = Math.max(noise, crackDelta);
        if (sqrtDistance > offset * 0.8f) {
            double delta = (sqrtDistance-offset*0.8f) / offset * 0.2f;
            calcification *= delta;
        }
        else if (sqrtDistance < offset * 0.4f) {
            double delta = (sqrtDistance) / offset * 0.4f;
            calcification *= delta;
        }

        if (calcification > 0.4f && calcification < 0.5f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING;
        }
        for (int i = 0; i < 5; i++) {
            if (calcification > 0.125f * (i+4)) {
                if (i >= 1) {;
                    if (random.nextFloat() < i*0.2f) {
                        feature = random.nextFloat()<i*0.1f ? MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALACTITES : MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALACTITES;
                    }
                }
                pattern.add(MeadowBlockRegistry.CALCIFIED_ROCK.get().defaultBlockState());
            }
        }

        final int extraBlocks = 12-pattern.size();
        for (int i = 0; i < extraBlocks; i++) {
            if (i > 1 && i <= 3) {
                pattern.add(Blocks.TUFF.defaultBlockState());
                continue;
            }
            if (i > 5) {
                BlockState existingBlock = chunk.getBlockState(pos.setY(startingY + i));
                if (existingBlock.is(Tags.Blocks.ORES)) {
                    pattern.add(existingBlock);
                    continue;
                }
            }
            pattern.add(patternState);
        }
        return Pair.of(pattern, feature);
    }

    private Pair<List<BlockState>, ResourceKey<ConfiguredFeature<?, ?>>> getRampBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, double sqrtDistance, double offset, int startingY) {
        return getSurfaceBlockPattern(chunk, noiseSampler, pos, null, sqrtDistance, offset, startingY);
    }

    private Pair<List<BlockState>, ResourceKey<ConfiguredFeature<?, ?>>> getSurfaceBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, Pair<CalcifiedRegion, Double> calcification, double sqrtDistance, double offset, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;
        double crackDelta = makeCracks(noiseSampler, pos.getX(), pos.getZ(), sqrtDistance, offset, 0.8f);
        boolean useCracks = crackDelta > 0;
        if (calcification != null && calcification.getSecond() < 0.04f) { //calcification start
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING;
        }
        if (calcification != null && calcification.getSecond() >= 0.03f) { //calcification inside
            if (!useCracks) {
                crackDelta = makeCracks(noiseSampler, pos.getX(), pos.getZ(), 0.025f);
            }
            double delta = calcification.getSecond();

            if (crackDelta >= 0f && crackDelta < 0.5f) {
                feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING;
            }
            if ((crackDelta >= 0.4f) || (delta >= 0.06f && delta < 0.08f)) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_ROCK.get().defaultBlockState());
            }
            if (crackDelta >= 0.2f || (delta >= 0.04f && delta < 0.06f)) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState());
            }
            pattern.add(Blocks.STONE.defaultBlockState());
            pattern.add(Blocks.STONE.defaultBlockState());
        }
        else { //regular grass region
            if (useCracks) {
                for (int i = 0; i < 3; i++) {
                    pattern.add(Blocks.STONE.defaultBlockState());
                }
            } else {
                pattern.add(AspenGrassVariantHelper.getStateForPlacement(pos.setY(startingY), MeadowBlockRegistry.ASPEN_GRASS_BLOCK.get().defaultBlockState()));
                pattern.add(Blocks.DIRT.defaultBlockState());
                pattern.add(Blocks.DIRT.defaultBlockState());
            }
        }
        for (int i = 3; i < 10; i++) {
            if (i > 3 && i <= 6) {
                pattern.add(Blocks.TUFF.defaultBlockState());
                continue;
            }
            BlockState existingBlock = chunk.getBlockState(pos.setY(startingY - i));
            if (existingBlock.is(Tags.Blocks.ORES)) {
                pattern.add(existingBlock);
                continue;
            }
            pattern.add(Blocks.STONE.defaultBlockState());
        }
        return Pair.of(pattern, feature);
    }

    public double makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, double distance, double totalDistance, double minDistance) {
        final double threshold = totalDistance * minDistance;
        if (distance > threshold) {
            float stateNoiseOffset = (float) ((distance - threshold) / (1 - threshold)) * 0.25f;
            return makeCracks(noiseSampler, blockX, blockZ, stateNoiseOffset);
        }
        return -1f;
    }
    public double makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, float noiseThresholdOffset) {
        double stateNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 75000, 0.15f) / 2;
        float bottomThreshold = 0.45f - noiseThresholdOffset;
        if (stateNoise > bottomThreshold && stateNoise < 0.5f) {
            return (stateNoise-bottomThreshold)/(0.5f-bottomThreshold);
        }
        float topThreshold = 0.55f + noiseThresholdOffset;
        if (stateNoise < topThreshold && stateNoise > 0.5f) {
            return 1 - (stateNoise-topThreshold)/(0.5f-topThreshold);
        }

        return -1f;
    }
}