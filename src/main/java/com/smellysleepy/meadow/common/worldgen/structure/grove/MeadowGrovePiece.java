package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.common.block.meadow.flora.grass.MeadowGrassVariantHelper;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.CalcifiedRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.LakeRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.SpecialMeadowRegion;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraftforge.common.Tags;
import team.lodestar.lodestone.systems.easing.Easing;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeadowGrovePiece extends StructurePiece {

    private final BlockPos groveCenter;
    private final List<SpecialMeadowRegion> specialRegions;
    private final int groveRadius;
    private final int groveHeight;
    private final int groveDepth;

    public boolean hasGenerated;

    public final List<Pair<BlockPos, ResourceLocation>> bufferedFeatures = new ArrayList<>();

    protected MeadowGrovePiece(BlockPos groveCenter, List<SpecialMeadowRegion> specialRegions, int groveRadius, int groveHeight, int groveDepth, BoundingBox boundingBox) {
        super(MeadowStructurePieceTypes.MEADOW_GROVE.get(), 0, boundingBox);
        this.groveCenter = groveCenter;
        this.specialRegions = specialRegions;
        this.groveRadius = groveRadius;
        this.groveHeight = groveHeight;
        this.groveDepth = groveDepth;
    }

    public MeadowGrovePiece(CompoundTag tag) {
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
            SpecialMeadowRegion region = SpecialMeadowRegion.DECODER.get(type).apply(compound);
            this.specialRegions.add(region);
        }

        CompoundTag features = tag.getCompound("features");
        int featureCount = features.getInt("count");
        bufferedFeatures.clear();
        for (int i = 0; i < featureCount; i++) {
            CompoundTag feature = features.getCompound("feature_"+i);
            BlockPos blockPos = NbtUtils.readBlockPos(feature.getCompound("featurePosition"));
            ResourceLocation resourceKeyId = new ResourceLocation(feature.getString("featureType"));
            bufferedFeatures.add(Pair.of(blockPos, resourceKeyId));
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
        for (int i = 0; i < bufferedFeatures.size(); i++) {
            var bufferedFeature = bufferedFeatures.get(i);
            CompoundTag feature = new CompoundTag();

            feature.put("featurePosition", NbtUtils.writeBlockPos(bufferedFeature.getFirst()));
            feature.putString("featureType", bufferedFeature.getFirst().toString());
            featuresTag.put("feature_"+i, feature);
        }
        tag.put("features", featuresTag);

    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource randomSource, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        try {
            carveGroveShape(this, worldGenLevel, randomSource, worldGenLevel.getChunk(chunkPos.x, chunkPos.z), chunkPos);
        } catch (Exception exception) {
            float f = 0;
        }
        for (Pair<BlockPos, ResourceLocation> pair : bufferedFeatures) {
            var pos = pair.getFirst();
            var location = pair.getSecond();
            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(Registries.CONFIGURED_FEATURE, location);
            Holder<ConfiguredFeature<?, ?>> holder = worldGenLevel.registryAccess()
                    .registryOrThrow(Registries.CONFIGURED_FEATURE)
                    .getHolder(key).orElseThrow();
            holder.get().place(worldGenLevel, chunkGenerator, randomSource, pos);
        }
        bufferedFeatures.clear();
    }

    public static void carveGroveShape(MeadowGrovePiece grovePiece, WorldGenLevel worldGenLevel, RandomSource random, ChunkAccess chunk, ChunkPos chunkPos) {
        grovePiece.hasGenerated = true;
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutableBlockPos = new BlockPos.MutableBlockPos();

        int groveRadius = grovePiece.groveRadius;
        int groveHeight = grovePiece.groveHeight;
        int groveDepth = grovePiece.groveDepth;

        int pearlflowerCount = random.nextIntBetweenInclusive(2, 4);
        List<Pair<Integer, Integer>> pearlflower = new ArrayList<>();

        int step = 16 / pearlflowerCount;
        for (int i = 0; i < pearlflowerCount; i++) {
            int x = chunkPos.getBlockX(random.nextIntBetweenInclusive(i*step, (i+1)*step));
            int z = chunkPos.getBlockZ(random.nextIntBetweenInclusive(0, 16));
            pearlflower.add(Pair.of(x, z));
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);

                boolean hasPearlflower = pearlflower.stream().anyMatch(p -> p.getFirst().equals(blockX) && p.getSecond().equals(blockZ));

                mutableBlockPos.set(blockX, 0, blockZ);

                double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;
                double depthNoise = WorldgenHelper.getNoise(noiseSampler, blockZ, blockX, 100000, 0.025f) * 0.5f;

                double localRadius = (int) Mth.clampedLerp(groveRadius * 0.4, groveRadius, noise);
                double localHeight = (int) Mth.clampedLerp(groveHeight * 0.5, groveHeight, noise);
                double localDepth = (int) Mth.clampedLerp(groveDepth * 0.6, groveDepth, depthNoise);

                int blendWidth = 48;
                int rimSize = (int) (localRadius * 0.075);

                grovePiece.buildGroveLayer(
                        chunk, noiseSampler, unsafeBoundingBox, mutableBlockPos, random,
                        hasPearlflower, localRadius, localHeight, localDepth, blendWidth, rimSize, blockX, blockZ);
            }
        }
        if (unsafeBoundingBox.valid()) {
            grovePiece.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }


    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(ChunkAccess chunk, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockPos.MutableBlockPos pos, RandomSource random,
                                                                                           boolean hasPearlflower, double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {

        int centerY = groveCenter.getY();
        pos.setY(centerY);
        double shapeRadius = MeadowGroveShape.CIRCLE.distSqr(groveCenter, pos, localRadius);
        double radius = localRadius * shapeRadius;
        double offset = radius - blendWidth - rimSize;
        if (!pos.closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        double distance = pos.distSqr(groveCenter);
        double sqrtDistance = Math.sqrt(distance);
        double delta = (distance - Mth.square(offset)) / Mth.square(radius - offset);
        double flatDelta = -(distance / Mth.square(radius - offset));
        double shellDelta = Mth.clamp(delta >= 0 ?(-delta*2) / 0.4f : delta / 0.3f, -2, 0);

        int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
        int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);
        int flatDepth = getGroveDepth(noiseSampler, pos, localDepth, flatDelta);
        int ceilingAirPocketEndPoint = centerY + height;
        int surfaceAirPocketEndPoint = centerY - depth;

        double shellOffset = Mth.clamp(shellDelta + 1, -1, 0);
        int topShellHeightOffset = getGroveHeight(noiseSampler, pos, localHeight, shellOffset);
        int bottomShellDepthOffset = getGroveDepth(noiseSampler, pos, localDepth, shellOffset);
        int topShellStart = shellDelta > -1 ? centerY : centerY + topShellHeightOffset;
        int bottomShellStart = shellDelta > -1 ? centerY : centerY - bottomShellDepthOffset;
        int shellHeight = getGroveHeight(noiseSampler, pos, localHeight, shellDelta) - topShellHeightOffset;
        int shellDepth = getGroveDepth(noiseSampler, pos, localDepth, shellDelta) - bottomShellDepthOffset;

        double calcificationNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 25000, 0.02f) * 0.5f;
        var calcifiedRegionOptional = getClosestRegion(CalcifiedRegion.class, blockX, blockZ, calcificationNoise, radius);

        double lakeNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 75000, 0.01f) * 0.5f;
        double relativeLakeRadius = radius * 0.7f + groveRadius * 0.3f;
        var lakeRegionOptional = getClosestRegion(LakeRegion.class, blockX, blockZ, lakeNoise, relativeLakeRadius);

        var ceilingPatternBlock = chooseSediment(noiseSampler, blockX, ceilingAirPocketEndPoint, blockZ);
        var ceilingPattern = getCeilingPattern(chunk, ceilingPatternBlock, pos, ceilingAirPocketEndPoint);

        var surfacePatternData = getSurfaceBlockPattern(chunk, noiseSampler, pos, calcifiedRegionOptional.orElse(null), sqrtDistance, offset, surfaceAirPocketEndPoint);
        var surfacePattern = surfacePatternData.getFirst();
        var surfaceFeature = surfacePatternData.getSecond();

        boolean placeWater = false;
        double waterDelta = 0;
        int waterStartingPoint = 0;
        boolean useLakeGrass = false;
        double lakeGrassDelta = 0;

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

        int ceilingHeight = Mth.clamp(height < 2 ? 0 : Mth.floor((2 + height) * 2), 0, ceilingPattern.size() - 1);
        int surfaceDepth = Mth.clamp(depth < 6 ? 0 : Mth.floor(depth / 2f), 0, surfacePattern.size() - 1);

        int highestCeilingPoint = ceilingAirPocketEndPoint + ceilingHeight;
        int lowestSurfacePoint = surfaceAirPocketEndPoint - surfaceDepth;

        double rampNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 75000, 0.01f) / 2;
        int rampStartingHeight = centerY - flatDepth;
        int rampHeight = Mth.ceil(flatDepth * Easing.QUINTIC_IN_OUT.clamped(rampNoise, 0.8f, 1.2f));
        List<BlockState> rampBlockPattern = getRampBlockPattern(chunk, noiseSampler, pos, sqrtDistance, offset, rampStartingHeight).getFirst();

        createHorizontalShell(chunk, pos, unsafeBoundingBox, blockX, blockZ, ceilingPattern, shellHeight, topShellStart, surfacePattern, shellDepth, bottomShellStart);

        carveOutShape(chunk, random, pos, unsafeBoundingBox, hasPearlflower, blockX, blockZ, centerY,
                ceilingPattern, highestCeilingPoint, ceilingAirPocketEndPoint, ceilingHeight,
                surfacePattern, lowestSurfacePoint, surfaceAirPocketEndPoint, surfaceDepth,
                placeWater, waterDelta, useLakeGrass, lakeGrassDelta, waterStartingPoint,
                calcifiedRegionOptional.orElse(null), surfaceFeature);


        addRamps(chunk, noiseSampler, random, pos, rampBlockPattern, hasPearlflower, rampStartingHeight, blockX, blockZ, rampHeight, rampNoise, sqrtDistance, offset);
        return Optional.empty();
    }

    private void createHorizontalShell(ChunkAccess chunk, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, int blockX, int blockZ,
                                       List<BlockState> ceilingPattern, int shellHeight, int topShellStart,
                                       List<BlockState> surfacePattern, int shellDepth, int bottomShellStart) {
        //Top half of horizontal shell
        if (shellHeight > 0) {
            int horizontalShellEnd = topShellStart + shellHeight;
            for (int y = topShellStart; y < horizontalShellEnd; y++) {
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                int index = Mth.clamp(y - topShellStart, 0, ceilingPattern.size()-1);
                chunk.setBlockState(pos, ceilingPattern.get(index), true);
            }
        }
        //Bottom half of horizontal shell
        if (shellDepth > 0) {
            int horizontalShellEnd = bottomShellStart - shellDepth;
            for (int y = bottomShellStart; y > horizontalShellEnd; y--) {
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                int index = Mth.clamp(bottomShellStart - y, 0, surfacePattern.size()-1);
                chunk.setBlockState(pos, surfacePattern.get(index), true);
            }
        }
    }

    private void carveOutShape(ChunkAccess chunk, RandomSource random, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, boolean hasPearlflower,
                               int blockX, int blockZ, int centerY,
                               List<BlockState> ceilingPattern, int highestCeilingPoint, int ceilingAirPocketEndPoint, int ceilingHeight,
                               List<BlockState> surfacePattern, int lowestSurfacePoint, int surfaceAirPocketEndPoint, int surfaceDepth,
                               boolean placeWater, double waterDelta, boolean useLakeGrass, double lakeGrassDelta, int waterStartingPoint,
                               Pair<CalcifiedRegion, Double> calcifiedRegion, ResourceKey<ConfiguredFeature<?, ?>> surfaceFeature) {
        //Ceiling Carver
        if (centerY < highestCeilingPoint) {
            for (int y = centerY; y < highestCeilingPoint; y++) {
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                if (y >= ceilingAirPocketEndPoint) {
                    int index = Mth.clamp(y - ceilingAirPocketEndPoint, 0, Math.min(ceilingPattern.size()-1, ceilingHeight));
                    chunk.setBlockState(pos, ceilingPattern.get(index), true);
                    continue;
                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        //Surface Carver
        if (centerY > lowestSurfacePoint) {
            for (int y = centerY; y > lowestSurfacePoint; y--) {
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceAirPocketEndPoint + 1) {
                    Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> feature;
                    if (surfaceFeature != null) {
                        feature = Pair.of(pos.immutable(), surfaceFeature);
                    }
                    else if (hasPearlflower) {
                        feature = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER);
                    }
                    else {
                        if (placeWater) {
                            feature = createWaterFeatures(random, pos, waterDelta);
                        } else if (useLakeGrass) {
                            feature = createLakeFeatures(random, pos, lakeGrassDelta);
                        } else {
                            feature = createSurfaceFeatures(random, pos, calcifiedRegion);
                        }
                    }
                    if (feature != null) {
                        bufferedFeatures.add(feature.mapSecond(ResourceKey::location));
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
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
    }

    private void addRamps(ChunkAccess chunk, ImprovedNoise noiseSampler, RandomSource random, BlockPos.MutableBlockPos pos, List<BlockState> rampBlockPattern, boolean hasPearlflower, int yLevel, int blockX, int blockZ, int rampHeight, double noise, double sqrtDistance, double offset) {
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
                if (hasPearlflower) {
                    feature = Pair.of(pos.immutable(), MeadowConfiguredFeatureRegistry.CONFIGURED_PEARLFLOWER);
                }
                else {
                    feature = createRampFeatures(random, pos);
                }
                if (feature != null) {
                    bufferedFeatures.add(feature.mapSecond(ResourceKey::location));
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

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createRampFeatures(RandomSource randomSource, BlockPos.MutableBlockPos pos) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;
        float rand = randomSource.nextFloat();
        if (rand < 0.02f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE;
        } else if (rand < 0.03f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE;
        } else if (rand < 0.05f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH;
        }
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createLakeFeatures(RandomSource randomSource, BlockPos.MutableBlockPos pos, double delta) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;
        double rand = randomSource.nextFloat() * delta;

        if (rand < 0.005f) {
            feature = VegetationFeatures.FLOWER_DEFAULT;
        } else if (rand < 0.01f) {
            feature = VegetationFeatures.PATCH_GRASS;
        } else if (rand < 0.0125f) {
            feature = VegetationFeatures.PATCH_TALL_GRASS;
        } else if (rand < 0.0175f) {
            feature = VegetationFeatures.PATCH_SUGAR_CANE;
        } else if (rand < 0.02f) {
            feature = TreeFeatures.AZALEA_TREE;
        } else if (rand < 0.03f) {
            feature = CaveFeatures.MOSS_VEGETATION;
        }
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }
    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createWaterFeatures(RandomSource randomSource, BlockPos.MutableBlockPos pos, double delta) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;
        double rand = randomSource.nextFloat() * delta;
        if (rand < 0.005f) {
            feature = AquaticFeatures.WARM_OCEAN_VEGETATION;
        } else if (rand < 0.015f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_LAKE_PATCH;
        } else if (rand < 0.025f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_LAKE_PATCH;
        } else if (rand < 0.035f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_LAKE_PATCH;
        }
        if (feature != null) {
            return Pair.of(pos.immutable(), feature);
        }
        return null;
    }

    private Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>> createSurfaceFeatures(RandomSource randomSource, BlockPos.MutableBlockPos pos, @Nullable Pair<CalcifiedRegion, Double> calcification) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;

        if (calcification != null) {
            double delta = calcification.getSecond();
            float rand = randomSource.nextFloat();
            if (delta > 0.05f) {
                if (rand < 0.01f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES;
                } else if (rand < 0.03f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES;
                }
                else {
                    feature = calcification.getFirst().chooseFeature(randomSource);
                }
            }
            else {
                if (rand < 0.02f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_CALCIFIED_STALAGMITES;
                } else if (rand < 0.05f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_STALAGMITES;
                }
            }
        } else {
            int featureTypeOffset = groveCenter.getY() - pos.getY();
            float start = groveDepth * 0.3f;
            float midpoint = groveDepth * 0.5f;
            float end = groveDepth * 0.7f;

            float rand = randomSource.nextFloat();
            if (rand < 0.02f) {
                feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH;
            } else {
                rand = randomSource.nextFloat();
                if (featureTypeOffset > start && featureTypeOffset < midpoint) {
                    if (rand < 0.03f) {
                        feature = MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE;
                    } else if (rand < 0.04f) {
                        feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE;
                    }
                }
                if (featureTypeOffset >= midpoint && featureTypeOffset < end) {
                    if (rand < 0.02f) {
                        feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE;
                    }
                }

            }
            if (featureTypeOffset >= end) {
                rand = randomSource.nextFloat();
                if (rand < 0.005f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_LARGE_MEADOW_PATCH;
                } else if (rand < 0.0075f) {
                    feature = MeadowConfiguredFeatureRegistry.CONFIGURED_MEADOW_PATCH;
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

    private List<BlockState> getCeilingPattern(ChunkAccess chunk, BlockState patternState, BlockPos.MutableBlockPos pos, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
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
        return pattern;
    }

    private Pair<List<BlockState>, ResourceKey<ConfiguredFeature<?, ?>>> getRampBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, double sqrtDistance, double offset, int startingY) {
        return getSurfaceBlockPattern(chunk, noiseSampler, pos, null, sqrtDistance, offset, startingY);
    }

    private Pair<List<BlockState>, ResourceKey<ConfiguredFeature<?, ?>>> getSurfaceBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, Pair<CalcifiedRegion, Double> calcification, double sqrtDistance, double offset, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        ResourceKey<ConfiguredFeature<?, ?>> feature = null;
        double crackDelta = makeCracks(noiseSampler, pos.getX(), pos.getZ(), sqrtDistance, offset);
        boolean useCracks = crackDelta > 0;
        if (calcification != null && calcification.getSecond() < 0.03f) { //calcification start
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING;
        }
        if (calcification != null && calcification.getSecond() >= 0.03f) { //calcification inside
            if (!useCracks) {
                crackDelta = makeCracks(noiseSampler, pos.getX(), pos.getZ(), 0.025f);
            }
            double delta = calcification.getSecond();

            if (crackDelta >= 0f && crackDelta < 0.2f) {
                feature = MeadowConfiguredFeatureRegistry.CONFIGURED_CALCIFIED_COVERING;
            } else if ((crackDelta >= 0.2f && crackDelta < 0.4f) || delta < 0.04f) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState());
            } else if ((crackDelta >= 0.4f) || delta < 0.06f) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_ROCK.get().defaultBlockState());
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
                pattern.add(MeadowGrassVariantHelper.getStateForPlacement(pos.setY(startingY), MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState()));
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

    public double makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, double distance, double offset) {
        if (distance > offset * 0.8f) {
            float stateNoiseOffset = (float) ((distance - offset * 0.8f) / (offset * 0.2f)) * 0.25f;
            return makeCracks(noiseSampler, blockX, blockZ, stateNoiseOffset);
        }
        return -1f;
    }
    public double makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, float stateNoiseOffset) {
        double stateNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 75000, 0.15f) / 2;
        float bottomThreshold = 0.45f - stateNoiseOffset;
        if (stateNoise > bottomThreshold && stateNoise < 0.5f) {
            return (stateNoise-bottomThreshold)/(0.5f-bottomThreshold);
        }
        float topThreshold = 0.55f + stateNoiseOffset;
        if (stateNoise < topThreshold && stateNoise > 0.5f) {
            return 1 - (stateNoise-topThreshold)/(0.5f-topThreshold);
        }

        return -1f;
    }
}