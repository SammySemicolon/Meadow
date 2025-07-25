package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.structure.grove.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.Supplier;

import java.util.function.Supplier;

public class MeadowStructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE.key(), MeadowMod.MEADOW);

    public static final Supplier<StructurePieceType> MEADOW_GROVE = STRUCTURE_PIECE_TYPES.register("meadow_grove", setPieceId(MeadowGrovePiece::new));

    private static Supplier<StructurePieceType> setPieceId(StructurePieceType.ContextlessType type) {
        return () -> type;
    }
}