package com.smellysleepy.meadow.registry.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.MineralTreePartType;
import com.smellysleepy.meadow.common.worldgen.tree.mineral.parts.*;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Optional;

public class MineralTreePartTypes {

    public static final HashMap<ResourceLocation, MineralTreePartType<?>> PART_TYPES = new HashMap<>();

    public static final Codec<MineralTreePartType<?>> CODEC = ResourceLocation.CODEC.flatXmap(
            key -> Optional.ofNullable(PART_TYPES.get(key)).map(DataResult::success).orElseThrow(),
            type -> Optional.of(type.getId()).map(DataResult::success).orElseThrow());

    public static final MineralTreePartType<LeafBlobPart> LEAF_BLOB = register(MeadowMod.meadowModPath("leaf_blob"), LeafBlobPart.CODEC);
    public static final MineralTreePartType<PuffyLeavesPart> PUFFY_LEAVES = register(MeadowMod.meadowModPath("puffy_leaves"), PuffyLeavesPart.CODEC);
    public static final MineralTreePartType<StraightTrunkPart> STRAIGHT_TRUNK = register(MeadowMod.meadowModPath("straight_trunk"), StraightTrunkPart.CODEC);
    public static final MineralTreePartType<ConvergingTrunkPart> CONVERGING_TRUNK = register(MeadowMod.meadowModPath("converging_trunk"), ConvergingTrunkPart.CODEC);
    public static final MineralTreePartType<SplitBranchesPart> SPLITTING_BRANCHES = register(MeadowMod.meadowModPath("splitting_branches"), SplitBranchesPart.CODEC);

    public static final MineralTreePartType<OffsetPart> OFFSET = register(MeadowMod.meadowModPath("offset"), OffsetPart.CODEC);

    public static void init() {

    }

    public static<T extends MineralTreePart> MineralTreePartType<T> register(ResourceLocation id, Codec<T> codec) {
        MineralTreePartType<T> type = new MineralTreePartType<>(id, codec);
        PART_TYPES.put(type.getId(), type);
        return type;
    }
}
