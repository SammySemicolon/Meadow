package com.smellysleepy.meadow.common.worldgen.feature.patch;

import com.smellysleepy.meadow.common.block.aspen.AspenGrassVariantHelper;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;
import java.util.Set;

public class LayeredPatchFeature extends Feature<LayeredPatchConfiguration> {
   public LayeredPatchFeature() {
      super(LayeredPatchConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<LayeredPatchConfiguration> context) {
      LayeredPatchConfiguration config = context.config();
      RandomSource random = context.random();
      BlockPos pos = context.origin();
      WorldGenLevel level = context.level();
      BlockPos.MutableBlockPos mutable = pos.mutable();

      List<Integer> patchSizes = config.patchSizes;
      List<Block> plants = config.plants;
      for (int i = 0; i < Math.max(patchSizes.size(), plants.size()); i++) {
         int size = patchSizes.get(Math.min(i, patchSizes.size()-1));
         Block plant = plants.get(Math.min(i, plants.size()-1));
         Set<BlockPos> covering = WorldgenHelper.fetchCoveringPositions(level, mutable, size);
         for (BlockPos blockPos : covering) {
            BlockPos coveragePos = blockPos.above();
            BlockState state = plant.defaultBlockState();
            boolean needsWater = state.hasProperty(BlockStateProperties.WATERLOGGED) || !state.getFluidState().isEmpty();
            boolean hasSpace = true;
            mutable = coveragePos.mutable();
            for (int j = 0; j < 4; j++) {
               if ((needsWater && level.getFluidState(mutable).isEmpty()) || (!needsWater && !level.getBlockState(mutable).isAir())) {
                  hasSpace = false;
                  break;
               }
               mutable.move(Direction.UP);
            }

            if (hasSpace) {
               if (plant.canSurvive(state, level, coveragePos)) {
                  state = AspenGrassVariantHelper.getStateForBushPlacement(level, coveragePos, state);
                  if (plant instanceof DoublePlantBlock) {
                     DoublePlantBlock.placeAt(level, state, coveragePos, 3);
                  } else {
                     level.setBlock(coveragePos, copyWaterloggedFrom(level, coveragePos, state), 3);
                  }
               }
            }
         }
         int offsetSize = Mth.floor(size/2f);
         mutable.move(random.nextIntBetweenInclusive(-offsetSize, offsetSize), 0, random.nextIntBetweenInclusive(-offsetSize, offsetSize));
      }

      return true;
   }

   //TODO: move this to some sorta lodestone helper function
   public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
      return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(pos)) : state;
   }
}