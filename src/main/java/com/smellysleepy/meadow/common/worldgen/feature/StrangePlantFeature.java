package com.smellysleepy.meadow.common.worldgen.feature;

import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import net.minecraft.core.*;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.systems.worldgen.*;

import java.util.*;

import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

public class StrangePlantFeature extends Feature<StrangePlantFeatureConfiguration> {

    public static final LodestoneBlockFiller.LodestoneLayerToken PLANTS = new LodestoneBlockFiller.LodestoneLayerToken();
    public static final LodestoneBlockFiller.LodestoneLayerToken COVERING = new LodestoneBlockFiller.LodestoneLayerToken();

    public StrangePlantFeature() {
        super(StrangePlantFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<StrangePlantFeatureConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var rand = context.random();
        var config = context.config();
        var plant = config.flower.defaultBlockState();
        if (level.isEmptyBlock(pos.below()) || !plant.canSurvive(level, pos)) {
            return false;
        }
        var mutable = pos.mutable();
        for (int j = 0; j < 4; j++) {
            if (!level.getBlockState(mutable).isAir()) {
                return false;
            }
            mutable.move(Direction.UP);
        }
        var filler = new LodestoneBlockFiller().addLayers(PLANTS, COVERING);
        var coveringLayer = filler.getLayer(COVERING);
        var plantLayer = filler.getLayer(PLANTS);
        var blockEntry = create(config.block.defaultBlockState()).setForcePlace().build();
        var oreEntry = create(config.ore.defaultBlockState()).setForcePlace().build();
        var grassEntry = create(config.grass.defaultBlockState()).setForcePlace().build();

        Set<BlockPos> covering = WorldgenHelper.fetchCoveringPositions(level, pos, 6);
        for (BlockPos blockPos : covering) {
            coveringLayer.put(blockPos, blockEntry);
        }

        Set<BlockPos> oreCovering = WorldgenHelper.fetchCoveringPositions(level, pos, 2);
        for (BlockPos blockPos : oreCovering) {
            coveringLayer.put(blockPos, oreEntry);
        }
        mutable = pos.mutable();
        for (int i = 0; i < 4; i++) {
            Set<BlockPos> foliageCovering = WorldgenHelper.fetchCoveringPositions(level, mutable, 3);
            for (BlockPos blockPos : foliageCovering) {
                if (rand.nextFloat() < 0.4f) {
                    continue;
                }
                plantLayer.put(blockPos.above(), grassEntry);
            }
            int offset = 2 + i;
            mutable.move(rand.nextIntBetweenInclusive(-offset, offset), 0, rand.nextIntBetweenInclusive(-offset, offset));
        }

        if (plant.hasProperty(DoublePlantBlock.HALF)) {
            plantLayer.put(pos, create(plant).setForcePlace());
            plantLayer.put(pos.above(), create(plant.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)).setForcePlace());
        }
        else {
            plantLayer.put(pos, create(plant).setForcePlace());
        }

        filler.fill(level);
        return true;
    }
}
