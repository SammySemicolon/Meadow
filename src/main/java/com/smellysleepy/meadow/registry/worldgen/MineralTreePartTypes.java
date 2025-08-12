package com.smellysleepy.meadow.registry.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePart;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.MineralTreePartType;
import com.smellysleepy.meadow.common.worldgen.feature.tree.mineral.parts.*;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Optional;

public class MineralTreePartTypes {

    public static final HashMap<ResourceLocation, MineralTreePartType<?>> PART_TYPES = new HashMap<>();

    public static final Codec<MineralTreePartType<?>> CODEC = ResourceLocation.CODEC.flatXmap(
            key -> Optional.ofNullable(PART_TYPES.get(key)).map(DataResult::success).orElseThrow(),
            type -> Optional.of(type.getId()).map(DataResult::success).orElseThrow());

    public static final MineralTreePartType<LeafBlobPart> LEAF_BLOB = register(MeadowMod.meadowPath("leaf_blob"), LeafBlobPart.CODEC);
    public static final MineralTreePartType<LeafDiamondPart> LEAF_DIAMOND = register(MeadowMod.meadowPath("leaf_diamond"), LeafDiamondPart.CODEC);
    public static final MineralTreePartType<StraightTrunkPart> STRAIGHT_TRUNK = register(MeadowMod.meadowPath("straight_trunk"), StraightTrunkPart.CODEC);
    public static final MineralTreePartType<ConvergingTrunkPart> CONVERGING_TRUNK = register(MeadowMod.meadowPath("converging_trunk"), ConvergingTrunkPart.CODEC);

    public static final MineralTreePartType<ThickStumpPart> THICK_STUMP = register(MeadowMod.meadowPath("thick_stump"), ThickStumpPart.CODEC);
    public static final MineralTreePartType<StumpFoldsPart> STUMP_FOLDS = register(MeadowMod.meadowPath("stump_folds"), StumpFoldsPart.CODEC);

    public static final MineralTreePartType<SplitBranchesPart> SPLITTING_BRANCHES = register(MeadowMod.meadowPath("splitting_branches"), SplitBranchesPart.CODEC);

    public static final MineralTreePartType<OffsetPart> OFFSET = register(MeadowMod.meadowPath("offset"), OffsetPart.CODEC);
    public static final MineralTreePartType<DirectionalOffset> DIRECTIONAL_OFFSET = register(MeadowMod.meadowPath("directional_offset"), DirectionalOffset.CODEC);
    public static final MineralTreePartType<ReturnPart> RETURN = register(MeadowMod.meadowPath("return"), ReturnPart.CODEC);
    public static final MineralTreePartType<RandomOffsetsPart> RANDOM_OFFSETS = register(MeadowMod.meadowPath("random_offsets"), RandomOffsetsPart.CODEC);

    public static void init() {

    }

    public static<T extends MineralTreePart> MineralTreePartType<T> register(ResourceLocation id, MapCodec<T> codec) {
        MineralTreePartType<T> type = new MineralTreePartType<>(id, codec);
        PART_TYPES.put(type.getId(), type);
        return type;
    }
}
