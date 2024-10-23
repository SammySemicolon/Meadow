package com.smellysleepy.meadow.common.block.meadow.flora.grass;

import com.smellysleepy.meadow.move_to_lodestone_later.SimplexNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MeadowGrassVariantHelper {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 15);

    public static float getFractalNoise(int iteration, float size, BlockPos pos) {
        return iteration == 0 ? 0 : ((SimplexNoise.noise(
                (pos.getX() + (iteration * size)) / size,
                (pos.getY() + (iteration * size)) / size,
                (pos.getZ() + (iteration * size)) / size)
                + 1.0f) * 0.5f) + getFractalNoise(iteration - 1, size, pos);
    }

    public static float fractalNoise(int iterations, float size, BlockPos pos) {
        return getFractalNoise(iterations, size, pos) / iterations;
    }

    public static int calcVariant(BlockPos pos) {
        int noiseOffset = ((int) ((fractalNoise(3, 64.0f, pos) * 120f) % 30.0f)) % 30;
        return Mth.abs((15-noiseOffset));
    }

    public static int calcVariantOrBeneath(LevelAccessor accessor, BlockPos pos) {
        BlockState state = accessor.getBlockState(pos.below());
        if (state.hasProperty(VARIANT)) {
            return state.getValue(VARIANT);
        }
        return calcVariant(pos);
    }

    public static BlockState getStateForPlacement(BlockPos pos, BlockState state) {
        if (state != null) {
            if (state.hasProperty(VARIANT)) {
                state = state.setValue(VARIANT, calcVariant(pos));
            }
        }
        return state;
    }
    public static BlockState getStateForBushPlacement(LevelAccessor accessor, BlockPos pos, BlockState state) {
        if (state != null) {
            if (state.hasProperty(VARIANT)) {
                state = state.setValue(VARIANT, calcVariantOrBeneath(accessor, pos));
            }
        }
        return state;
    }
}
