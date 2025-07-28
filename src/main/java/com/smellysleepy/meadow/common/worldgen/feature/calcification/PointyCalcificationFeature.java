package com.smellysleepy.meadow.common.worldgen.feature.calcification;

import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PointyCalcificationFeature extends Feature<PointyCalcificationConfiguration> {
   public PointyCalcificationFeature() {
      super(PointyCalcificationConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<PointyCalcificationConfiguration> context) {
      PointyCalcificationConfiguration config = context.config();
      RandomSource random = context.random();
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      BlockPos.MutableBlockPos mutable = pos.mutable();

      int size = random.nextIntBetweenInclusive(config.minSize(), config.maxSize());
      boolean upwards = config.growsUpwards();
      var covering = upwards ? WorldgenHelper.fetchCoveringPositions(level, mutable, size) : WorldgenHelper.fetchHangingBlockPositions(level, mutable, size);
      var growDirection = upwards ? Direction.UP : Direction.DOWN;
      mainLoop:
      for (BlockPos blockPos : covering) {
         int distance = mutable.setY(blockPos.getY()).distManhattan(blockPos);
         int height = random.nextIntBetweenInclusive(config.minHeight(), config.maxHeight()) - distance;
         if (height <= 0) {
            continue;
         }
         for (int i = 0; i < height; i++) {
            mutable.move(growDirection);
            if (!level.getBlockState(mutable).canBeReplaced()) {
               continue mainLoop;
            }
         }
         BlockPos stalagmiteStart = blockPos.relative(growDirection);
         BlockState state = config.stalagmiteProvider().getState(random, stalagmiteStart);
         WorldgenHelper.growPointedCalcification(level, state.getBlock(), stalagmiteStart, growDirection, height, false);
      }
      return true;
   }

   public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
      return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(pos)) : state;
   }
}