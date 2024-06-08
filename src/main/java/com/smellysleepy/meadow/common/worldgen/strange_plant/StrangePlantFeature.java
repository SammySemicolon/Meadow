package com.smellysleepy.meadow.common.worldgen.strange_plant;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.synth.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.worldgen.*;

import java.util.*;

import static net.minecraft.tags.BlockTags.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class StrangePlantFeature extends Feature<StrangePlantFeatureConfiguration> {

    public static final LodestoneBlockFiller.LodestoneLayerToken PLANTS = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken COVERING = new LodestoneBlockFiller.LodestoneLayerToken();
    private static final PerlinSimplexNoise COVERING_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    public StrangePlantFeature() {
        super(StrangePlantFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<StrangePlantFeatureConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var rand = context.random();
        var config = context.config();
        var plant = config.plant.defaultBlockState();
        if (level.isEmptyBlock(pos.below()) || !plant.canSurvive(level, pos)) {
            return false;
        }
        LodestoneBlockFiller filler = new LodestoneBlockFiller().addLayers(PLANTS, COVERING);

        filler.getLayer(PLANTS).put(pos.above(), create(plant));
        generateCovering(level, config, filler, pos, 5);
        filler.fill(level);
        return true;
    }

    public static void generateCovering(ServerLevelAccessor level, StrangePlantFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos pos, int radius) {
        Map<Integer, Double> noiseValues = new HashMap<>();
        for (int i = 0; i <= 360; i++) {
            noiseValues.put(i, 1+COVERING_NOISE.getValue(pos.getX() + pos.getZ() + i * 0.02f, pos.getY() / 0.05f, true));
        }
        generateCovering(level, config, filler, pos, radius, noiseValues);
    }

    public static void generateCovering(ServerLevelAccessor level, StrangePlantFeatureConfiguration config, LodestoneBlockFiller filler, BlockPos center, int radius, Map<Integer, Double> noiseValues) {
        var rand = level.getRandom();
        int x = center.getX();
        int z = center.getZ();
        var mutable = new BlockPos.MutableBlockPos();

        int oreCounter = radius / 4;
        var ore = config.ore.defaultBlockState();
        var primaryDecorator = config.primaryDecorator.defaultBlockState();
        var secondaryDecorator = config.secondaryDecorator.defaultBlockState();

        for (int i = 0; i < radius * 2 + 1; i++) {
            for (int j = 0; j < radius * 2 + 1; j++) {
                int xp = x + i - radius;
                int zp = z + j - radius;
                double theta = 180 + 180 / Math.PI * Math.atan2(x - xp, z - zp);
                double naturalNoiseValue = Math.abs(noiseValues.get(Mth.floor(theta))) * radius;
                int floor = (int) Math.floor(pointDistancePlane(xp, zp, x, z));
                if (floor <= (Math.floor(naturalNoiseValue) - 1)) {
                    mutable.set(xp, center.getY(), zp);
                    int verticalRange = 4;
                    for (int k = 0; level.isStateAtPosition(mutable, s -> !s.canBeReplaced()) && k < verticalRange; ++k) {
                        mutable.move(Direction.UP);
                    }
                    for (int k = 0; level.isStateAtPosition(mutable, s -> s.canBeReplaced()) && k < verticalRange; ++k) {
                        mutable.move(Direction.DOWN);
                    }
                    if (level.getBlockState(mutable).is(MOSS_REPLACEABLE)) {
                        var block = primaryDecorator;
                        if (oreCounter == 0) {
                            oreCounter = 4;
                            block = ore;
                        }
                        else if (floor > 2 * radius / naturalNoiseValue) {
                            block = secondaryDecorator;
                        }
                        filler.getLayer(COVERING).put(mutable.immutable(), create(block).setForcePlace());
                        oreCounter--;
                    }
                }
            }
        }
    }

    public static float pointDistancePlane(double x1, double z1, double x2, double z2) {
        return (float) Math.hypot(x1 - x2, z1 - z2);
    }
}
