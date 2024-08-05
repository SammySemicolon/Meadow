package com.smellysleepy.meadow.common.worldgen.feature.calcification;

import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import com.smellysleepy.meadow.common.worldgen.feature.patch.LayeredPatchConfiguration;
import com.smellysleepy.meadow.registry.common.MeadowBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Set;

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

      int size = random.nextIntBetweenInclusive(config.minSize, config.maxSize);
      Set<BlockPos> covering = WorldgenHelper.fetchCoveringPositions(level, mutable, size);

      for (BlockPos blockPos : covering) {
         int distance = mutable.setY(blockPos.getY()).distManhattan(blockPos);
         int height = random.nextIntBetweenInclusive(config.minHeight, config.maxHeight) - distance;
         if (height <= 0) {
            continue;
         }
         BlockPos stalagmiteStart = blockPos.above();
         BlockState state = config.stalagmiteProvider.getState(random, stalagmiteStart);
         WorldgenHelper.growPointedCalcification(level, state.getBlock(), stalagmiteStart, Direction.UP, height, false);
      }
      return true;
   }

   public static BlockState copyWaterloggedFrom(LevelReader pLevel, BlockPos pPos, BlockState pState) {
      return pState.hasProperty(BlockStateProperties.WATERLOGGED) ? pState.setValue(BlockStateProperties.WATERLOGGED, pLevel.isWaterAt(pPos)) : pState;
   }
}