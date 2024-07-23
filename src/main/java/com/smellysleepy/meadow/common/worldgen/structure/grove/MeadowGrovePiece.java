package com.smellysleepy.meadow.common.worldgen.structure.grove;

import com.mojang.datafixers.util.Pair;
import com.smellysleepy.meadow.UnsafeBoundingBox;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.CalcifiedRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.LakeRegion;
import com.smellysleepy.meadow.common.worldgen.structure.grove.area.SpecialMeadowRegion;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowConfiguredFeatureRegistry;
import com.smellysleepy.meadow.registry.worldgen.MeadowStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

    public List<Pair<BlockPos, ResourceLocation>> bufferedFeatures = new ArrayList<>();

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
        this.specialRegions = new ArrayList<>();
        CompoundTag regionsTag = tag.getCompound("regions");
        int count = regionsTag.getInt("count");
        for (int i = 0; i < count; i++) {
            CompoundTag compound = regionsTag.getCompound("specialRegion_" + i);
            ResourceLocation type = ResourceLocation.tryParse(compound.getString("type"));
            SpecialMeadowRegion region = SpecialMeadowRegion.DECODER.get(type).apply(compound);
            this.specialRegions.add(region);
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.put("groveCenter", NbtUtils.writeBlockPos(groveCenter));
        tag.putInt("groveRadius", groveRadius);
        tag.putInt("groveHeight", groveHeight);

        CompoundTag regionsTag = new CompoundTag();
        regionsTag.putInt("count", specialRegions.size());
        for (int i = 0; i < specialRegions.size(); i++) {
            var region = specialRegions.get(i);
            regionsTag.put("specialRegion_" + i, region.serialize());
        }

        tag.put("regions", regionsTag);
    }

    @Override
    public void postProcess(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        var noiseSampler = new ImprovedNoise(new XoroshiroRandomSource(worldGenLevel.getSeed()));
        var unsafeBoundingBox = new UnsafeBoundingBox();
        var mutableBlockPos = new BlockPos.MutableBlockPos();
        ChunkAccess chunk = worldGenLevel.getChunk(chunkPos.x, chunkPos.z);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = chunkPos.getBlockX(x);
                int blockZ = chunkPos.getBlockZ(z);

                mutableBlockPos.set(blockX, 0, blockZ);

                double noise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 0.05f) * 0.5f;
                double depthNoise = WorldgenHelper.getNoise(noiseSampler, blockZ, blockX, 100000, 0.025f) * 0.5f;

                double localRadius = (int) Mth.clampedLerp(groveRadius * 0.4, groveRadius, noise);
                double localHeight = (int) Mth.clampedLerp(groveHeight * 0.5, groveHeight, noise);
                double localDepth = (int) Mth.clampedLerp(groveDepth * 0.6, groveDepth, depthNoise);

                int blendWidth = 48;
                int rimSize = (int) (localRadius * 0.05);

                buildGroveLayer(
                        chunk, noiseSampler, unsafeBoundingBox, mutableBlockPos, random,
                        localRadius, localHeight, localDepth, blendWidth, rimSize, blockX, blockZ);
            }
        }
        if (unsafeBoundingBox.valid()) {
            this.boundingBox = unsafeBoundingBox.toBoundingBox();
        }
    }

    private Optional<Pair<BlockPos, ResourceKey<ConfiguredFeature<?, ?>>>> buildGroveLayer(ChunkAccess chunk, ImprovedNoise noiseSampler, UnsafeBoundingBox unsafeBoundingBox, BlockPos.MutableBlockPos pos, RandomSource random,
                                                                                           double localRadius, double localHeight, double localDepth, int blendWidth, int rimSize, int blockX, int blockZ) {
        double offset = localRadius - blendWidth - rimSize;
        if (!pos.setY(groveCenter.getY()).closerThan(groveCenter, offset)) {
            return Optional.empty();
        }
        int centerY = groveCenter.getY();
        double distance = pos.setY(centerY).distSqr(groveCenter);
        double sqrtDistance = Math.sqrt(distance);
        double delta = (distance - Mth.square(offset)) / Mth.square(localRadius - offset);
        double flatDelta = -(distance / Mth.square(localRadius - offset));
        int flatDepth = getGroveDepth(noiseSampler, pos, localDepth, flatDelta);

        int height = getGroveHeight(noiseSampler, pos, localHeight, delta);
        int depth = getGroveDepth(noiseSampler, pos, localDepth, delta);

        int ceilingLimit = centerY + height;
        int surfaceLimit = centerY - depth;

        double calcificationNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 25000, 0.02f) * 0.5f;
        Optional<Pair<CalcifiedRegion, Double>> calcifiedRegionOptional = getClosestRegion(CalcifiedRegion.class, blockX, blockZ, calcificationNoise, localRadius);

        double lakeNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 75000, 0.01f) * 0.5f;
        double relativeLakeRadius = localRadius * 0.7f + groveRadius * 0.3f;
        Optional<Pair<LakeRegion, Double>> lakeRegionOptional = getClosestRegion(LakeRegion.class, blockX, blockZ, lakeNoise, relativeLakeRadius);

        BlockState ceilingPatternBlock = chooseSediment(noiseSampler, blockX, ceilingLimit, blockZ);

        List<BlockState> ceilingPattern = getCeilingPattern(chunk, ceilingPatternBlock, pos, ceilingLimit);
        List<BlockState> surfacePattern = getSurfaceBlockPattern(chunk, noiseSampler, pos, calcifiedRegionOptional.orElse(null), sqrtDistance, offset, surfaceLimit);

        boolean placeWater = false;
        int waterStartingPoint = 0;

        if (lakeRegionOptional.isPresent()) {
            var pair = lakeRegionOptional.get();
            var lakeRegion = pair.getFirst();
            double lakeDelta = pair.getSecond();
            int terrainDepth = (int) (groveDepth * lakeRegion.getSurfaceLevel());
            int waterDepth = (int) (groveDepth * lakeRegion.getWaterLevel());
            int lakeDepth;
            if (lakeDelta < 0.2f) {
                lakeDepth = depth - Mth.floor(Easing.SINE_IN.ease(lakeDelta, 0, depth-terrainDepth, 0.2f));
            }
            else {
                double lakeCarverDepth = lakeRegion.getLakeDepth() * (waterDepth + flatDepth);
                float carverDelta = (lakeDelta > 0.7f ? Easing.CUBIC_OUT : Easing.BACK_IN_OUT).ease((lakeDelta - 0.2f) / 0.8f, 0, 1);
                lakeDepth = Mth.floor(Easing.SINE_OUT.ease(carverDelta, terrainDepth, lakeCarverDepth));
            }
            surfaceLimit = centerY - lakeDepth;
            if (lakeDelta >= 0.4f) {
                placeWater = true;
                waterStartingPoint = centerY - waterDepth;
            }
            Block[] blocks = new Block[]{
                    Blocks.WHITE_STAINED_GLASS,
                    Blocks.ORANGE_STAINED_GLASS,
                    Blocks.MAGENTA_STAINED_GLASS,
                    Blocks.LIGHT_BLUE_STAINED_GLASS,
                    Blocks.YELLOW_STAINED_GLASS,
                    Blocks.LIME_STAINED_GLASS,
                    Blocks.PINK_STAINED_GLASS,
                    Blocks.GRAY_STAINED_GLASS,
                    Blocks.LIGHT_GRAY_STAINED_GLASS,
                    Blocks.CYAN_STAINED_GLASS
            };
            if (calcifiedRegionOptional.isEmpty()) {
                Block block = blocks[Mth.clamp(Mth.floor(lakeDelta*10), 0, 10)];


//                    if (lakeDelta > 0.8f) {
//                        block = Blocks.STONE;
//                    }
                surfacePattern.set(0, block.defaultBlockState());
            }

        }

        int ceilingCoverage = Mth.clamp(height < 2 ? 0 : Mth.floor((2 + height) * 2), 0, ceilingPattern.size() - 1);
        int surfaceCoverage = Mth.clamp(depth < 6 ? 0 : Mth.floor(depth / 2f), 0, surfacePattern.size() - 1);

        int surfacePlacementDepth = surfaceLimit - surfaceCoverage;
        int ceilingPlacementHeight = ceilingLimit + ceilingCoverage;

        int ceilingShellOffset = (height > 8 ? Mth.floor((height - 8) * 0.2f) : 0);
        int ceilingShellLimit = ceilingPlacementHeight + 6 - ceilingShellOffset;
        int ceilingShellStart = centerY + ceilingShellOffset;

        int surfaceShellOffset = (depth > 6 ? Mth.floor((depth - 6) * 0.6f) : 0);
        int surfaceShellLimit = surfacePlacementDepth - 6 + surfaceShellOffset;
        int surfaceShellStart = centerY - surfaceShellOffset;

        double rampNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 75000, 0.01f) / 2;
        int rampYLevel = centerY - flatDepth;
        int rampHeight = Mth.ceil(flatDepth * Easing.QUINTIC_IN_OUT.clamped(rampNoise, 0.8f, 1.2f));
        List<BlockState> rampBlockPattern = getRampBlockPattern(chunk, noiseSampler, pos, sqrtDistance, offset, rampYLevel);

        createShell(chunk, pos, unsafeBoundingBox, ceilingPatternBlock, blockX, blockZ, ceilingShellStart, ceilingShellLimit, surfaceShellStart, surfaceShellLimit);

        if (centerY < ceilingPlacementHeight) {
            for (int y = centerY; y < ceilingPlacementHeight; y++) { // ceiling
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);
                if (y >= ceilingLimit) {
                    int index = Mth.clamp(y - ceilingLimit, 0, ceilingCoverage);
                    chunk.setBlockState(pos, ceilingPattern.get(index), true);
                    continue;
                }
                chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), true);
            }
        }
        if (centerY > surfacePlacementDepth) {
            for (int y = centerY; y > surfacePlacementDepth; y--) { //surface
                pos.set(blockX, y, blockZ);
                unsafeBoundingBox.encapsulate(pos);

                if (y == surfaceLimit + 1) {
                    var feature = createSurfaceFeatures(random, pos, calcifiedRegionOptional.orElse(null));
                    if (feature != null) {
                        bufferedFeatures.add(feature.mapSecond(ResourceKey::location));
                    }
                }

                if (y <= surfaceLimit) {
                    int index = Mth.clamp(surfaceLimit - y, 0, surfaceCoverage);
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
        createRamps(chunk, noiseSampler, random, pos, rampBlockPattern, rampYLevel, blockX, blockZ, rampHeight, rampNoise, sqrtDistance, offset);
        return Optional.empty();
    }

    private void createShell(ChunkAccess chunk, BlockPos.MutableBlockPos pos, UnsafeBoundingBox unsafeBoundingBox, BlockState ceilingPatternBlock, int blockX, int blockZ, int ceilingShellStart, int ceilingShellLimit, int surfaceShellStart, int surfaceShellLimit) {
        int cutoffCounter = 0;
        for (int y = ceilingShellStart; y < ceilingShellLimit; y++) { // top shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);

            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.canBeReplaced()) {
                if (cutoffCounter++ >= 6) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, ceilingPatternBlock, true);
        }

        cutoffCounter = 0;
        for (int y = surfaceShellStart; y > surfaceShellLimit; y--) { // bottom shell
            pos.set(blockX, y, blockZ);
            unsafeBoundingBox.encapsulate(pos);
            BlockState blockState = chunk.getBlockState(pos);
            if (blockState.canBeReplaced()) {
                if (cutoffCounter++ >= 4) {
                    break;
                }
                continue;
            }
            chunk.setBlockState(pos, Blocks.STONE.defaultBlockState(), true);
        }
    }

    private void createRamps(ChunkAccess chunk, ImprovedNoise noiseSampler, RandomSource random, BlockPos.MutableBlockPos pos, List<BlockState> rampBlockPattern, int yLevel, int blockX, int blockZ, int rampHeight, double noise, double sqrtDistance, double offset) {
        boolean isAtEdge = sqrtDistance > offset * 0.95f;
        float noiseOffset = Easing.QUAD_OUT.clamped((sqrtDistance - offset * 0.6f) / (offset * 0.4f), 0, 0.3f);
        float topThreshold = 0.6f - noiseOffset;
        float bottomThreshold = 0.625f - noiseOffset;
        if (noise < topThreshold || noise < bottomThreshold) {
            noise = getSmoothedRampNoise(noiseSampler, noise, blockX, blockZ);
        }

        if (sqrtDistance < offset * 0.6f) {
            noise *= (sqrtDistance - offset * 0.6f) / offset * 0.6f;
        }

        if (noise >= topThreshold) {
            int topCutoff = Mth.floor(rampHeight * Easing.CIRC_OUT.clamped(getRampCutIn(noise, topThreshold), 0, 1));
            int rampTop = yLevel + rampHeight;

            if (!isAtEdge) {
                var feature = createRampFeatures(random, pos.set(blockX, rampTop + 1, blockZ));
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
            int bottomCutoff = Mth.floor(rampHeight * Easing.QUAD_IN_OUT.clamped(getRampCutIn(noise, bottomThreshold), 0, 1));
            pos.set(blockX, yLevel, blockZ);
            while (chunk.getBlockState(pos).canBeReplaced()) {
                pos.move(Direction.DOWN);
                if (pos.getY() % 2 == 0) {
                    rampHeight++;
                }
            }
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
        if (rand < 0.01f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE;
        } else if (rand < 0.02f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_ASPEN_TREE;
        } else if (rand < 0.025f) {
            feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH;
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
            if (delta > 0.05f) {
                feature = calcification.getFirst().chooseFeature(randomSource, 1.25f);
            }
        } else {
            int featureTypeOffset = groveCenter.getY() - pos.getY();
            float start = groveDepth * 0.3f;
            float midpoint = groveDepth * 0.5f;
            float end = groveDepth * 0.7f;

            if (randomSource.nextFloat() < 0.02f) {
                feature = MeadowConfiguredFeatureRegistry.CONFIGURED_SMALL_MEADOW_PATCH;
            } else {
                float rand = randomSource.nextFloat();
                if (featureTypeOffset > start && featureTypeOffset < midpoint) {
                    if (rand < 0.015f) {
                        feature = MeadowConfiguredFeatureRegistry.CONFIGURED_ASPEN_TREE;
                    } else if (rand < 0.03f) {
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
                float rand = randomSource.nextFloat();
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

    public <T extends SpecialMeadowRegion> Optional<Pair<T, Double>> getClosestRegion(Class<T> regionClass, int blockX, int blockZ, double noise, double localRadius) {
        if (specialRegions.isEmpty()) {
            return Optional.empty();
        }
        double lowestDistance = Double.MAX_VALUE;
        T closestArea = null;
        for (SpecialMeadowRegion specialRegion : specialRegions) {
            if (regionClass.isInstance(specialRegion)) {
                double distance = specialRegion.getDistance(groveCenter, blockX, blockZ, localRadius);
                if (distance < lowestDistance) {
                    double threshold = specialRegion.getThreshold(noise, localRadius);
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
        double threshold = closestArea.getThreshold(noise, localRadius);
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
        double depthNoise = WorldgenHelper.getNoise(noiseSampler, pos.getX(), pos.getZ(), 100000, frequency) * 0.5f;

        double depthOffset = easing.clamped(depthNoise, 0, (value * depthOffsetScalar));
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
        for (int i = 0; i < 8; i++) {
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

    private List<BlockState> getRampBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, double sqrtDistance, double offset, int startingY) {
        return getSurfaceBlockPattern(chunk, noiseSampler, pos, null, sqrtDistance, offset, startingY);
    }

    private List<BlockState> getSurfaceBlockPattern(ChunkAccess chunk, ImprovedNoise noiseSampler, BlockPos.MutableBlockPos pos, Pair<CalcifiedRegion, Double> calcification, double sqrtDistance, double offset, int startingY) {
        ArrayList<BlockState> pattern = new ArrayList<>();
        boolean useCracks = makeCracks(noiseSampler, pos.getX(), pos.getZ(), sqrtDistance, offset);
        if (calcification != null) {
            if (!useCracks) {
                useCracks = makeCracks(noiseSampler, pos.getX(), pos.getZ(), 0.025f);
            }
            double delta = calcification.getSecond();
            if (useCracks || delta < 0.04f) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_EARTH.get().defaultBlockState());
            } else if (delta < 0.08f) {
                pattern.add(MeadowBlockRegistry.CALCIFIED_ROCK.get().defaultBlockState());
            }
            pattern.add(Blocks.STONE.defaultBlockState());
            pattern.add(Blocks.STONE.defaultBlockState());
        }
        else {
            if (useCracks) {
                for (int i = 0; i < 3; i++) {
                    pattern.add(Blocks.STONE.defaultBlockState());
                }
            } else {
                pattern.add(MeadowBlockRegistry.MEADOW_GRASS_BLOCK.get().defaultBlockState());
                pattern.add(Blocks.DIRT.defaultBlockState());
                pattern.add(Blocks.DIRT.defaultBlockState());
            }
        }
        for (int i = 3; i < 8; i++) {
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
        return pattern;
    }

    public boolean makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, double distance, double offset) {
        if (distance > offset * 0.8f) {
            float stateNoiseOffset = (float) ((distance - offset * 0.8f) / (offset * 0.2f)) * 0.25f;
            return makeCracks(noiseSampler, blockX, blockZ, stateNoiseOffset);
        }
        return false;
    }
    public boolean makeCracks(ImprovedNoise noiseSampler, int blockX, int blockZ, float stateNoiseOffset) {
        double stateNoise = WorldgenHelper.getNoise(noiseSampler, blockX, blockZ, 75000, 0.15f) / 2;
        return stateNoise > 0.45f - stateNoiseOffset && stateNoise < 0.55f + stateNoiseOffset;
    }

}