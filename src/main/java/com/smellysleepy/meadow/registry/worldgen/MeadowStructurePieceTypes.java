package com.smellysleepy.meadow.registry.worldgen;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.worldgen.biome.MeadowGroveDecoratorPiece;
import com.smellysleepy.meadow.common.worldgen.biome.MeadowGrovePiece;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MeadowStructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE.key(), MeadowMod.MEADOW);

    public static final RegistryObject<StructurePieceType> MEADOW_GROVE = STRUCTURE_PIECE_TYPES.register("meadow_grove", setPieceId(MeadowGrovePiece::new));
    public static final RegistryObject<StructurePieceType> MEADOW_GROVE_DECORATOR = STRUCTURE_PIECE_TYPES.register("meadow_grove_decorator", setPieceId(MeadowGroveDecoratorPiece::new));

    private static Supplier<StructurePieceType> setPieceId(StructurePieceType.ContextlessType type) {
        return () -> type;
    }
}