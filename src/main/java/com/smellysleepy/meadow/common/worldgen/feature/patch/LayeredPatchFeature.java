package com.smellysleepy.meadow.common.worldgen.feature.patch;

import com.mojang.serialization.Codec;
import com.smellysleepy.meadow.common.worldgen.WorldgenHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;

import java.util.List;
import java.util.Set;

import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.create;

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
         Set<BlockPos> covering = WorldgenHelper.generateCovering(level, mutable, size);
         for (BlockPos blockPos : covering) {
            BlockPos above = blockPos.above();
            BlockState state = plant.defaultBlockState();
            boolean hasSpace = true;
            mutable = above.mutable();
            for (int j = 0; j < 4; j++) {
               if (!level.getBlockState(mutable).isAir()) {
                  hasSpace = false;
                  break;
               }
               mutable.move(Direction.UP);
            }

            if (hasSpace) {
               if (plant.canSurvive(state, level, above)) {
                  if (plant instanceof DoublePlantBlock) {
                     DoublePlantBlock.placeAt(level, state, above, 3);
                  } else {
                     level.setBlock(above, state, 3);
                  }
               }
            }
         }
         int offsetSize = Mth.floor(size/2f);
         mutable.move(random.nextIntBetweenInclusive(-offsetSize, offsetSize), 0, random.nextIntBetweenInclusive(-offsetSize, offsetSize));
      }

      return true;
   }
}