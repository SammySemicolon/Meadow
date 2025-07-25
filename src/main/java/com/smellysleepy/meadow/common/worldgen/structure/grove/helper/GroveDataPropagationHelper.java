package com.smellysleepy.meadow.common.worldgen.structure.grove.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.smellysleepy.meadow.common.worldgen.structure.grove.MeadowGroveGenerationConfiguration;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataCoordinate;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.DataEntry;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.InclineData;
import com.smellysleepy.meadow.common.worldgen.structure.grove.data.MeadowGroveGenerationData;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class GroveDataPropagationHelper {

    public static void propagate(MeadowGroveGenerationConfiguration config, PropagationHandler handler, int range, int blockX, int blockZ) {
        DataCoordinate start = new DataCoordinate(blockX, blockZ);

        MeadowGroveGenerationData generationData = config.getGenerationData();
        List<Set<DataCoordinate>> list = Lists.newArrayList();
        for (int j = 0; j < range; ++j) {
            list.add(Sets.newHashSet());
        }

        list.get(0).add(start);


        for (int i = 1; i < range; ++i) {
            Set<DataCoordinate> set = list.get(i - 1);
            Set<DataCoordinate> set1 = list.get(i);
            double delta = 1 - i / (float)range;
            for (DataCoordinate dataCoordinate : set) {
                for (int j = 0; j < 4; j++) {
                    Direction direction = Direction.from2DDataValue(j);
                    DataCoordinate neighboringPosition = dataCoordinate.move(direction);
                    if (set.contains(neighboringPosition) || set1.contains(neighboringPosition)) {
                        continue;
                    }

                    if (generationData.hasData(neighboringPosition)) {
                        DataEntry target = generationData.getData(neighboringPosition);
                        if (!target.isOpen()) {
                            continue;
                        }
                        int hash = Objects.hash(blockX, blockZ);
                        float rate = Mth.lerp((Mth.sin(hash % 6.28f) + 1) * 0.5f, 0.7f, 1.3f);
                        int offsetX = blockX - neighboringPosition.x();
                        int offsetZ = blockZ - neighboringPosition.z();
                        double angle = hash + Math.atan2(offsetZ, offsetX);

                        double sine = Mth.lerp((Math.sin(angle * rate) + 1) * 0.5f, 0.6f, 1.4f);
                        double offsetDelta = sine * delta;
                        var success = handler.propagate(target, offsetDelta);
                        if (!success) {
                            continue;
                        }
                    }
                    set1.add(neighboringPosition);
                }
            }
        }
    }

    public interface PropagationHandler {
        boolean propagate(DataEntry target, double delta);
    }
}
