package com.smellysleepy.meadow.registry.common;

import com.smellysleepy.meadow.MeadowMod;
import com.smellysleepy.meadow.common.sound.CalcifiedSoundType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MeadowSoundRegistry {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MeadowMod.MEADOW);

    public static Supplier<SoundEvent> CALCIFICATION_MOTIF = register(SoundEvent.createVariableRangeEvent(MeadowMod.meadowModPath("calcification_motif")));

    public static final SoundType CALCIFIED_WOOD = new CalcifiedSoundType(1f, 0.8f, ()->SoundEvents.CHERRY_WOOD_BREAK, ()->SoundEvents.CHERRY_WOOD_STEP, ()->SoundEvents.CHERRY_WOOD_PLACE, ()->SoundEvents.CHERRY_WOOD_HIT, ()->SoundEvents.CHERRY_WOOD_FALL);
    public static final SoundType CALCIFIED_FUNGUS = new CalcifiedSoundType(1f, 0.8f, ()->SoundEvents.NETHER_WOOD_BREAK, ()->SoundEvents.NETHER_WOOD_STEP, ()->SoundEvents.NETHER_WOOD_PLACE, ()->SoundEvents.NETHER_WOOD_HIT, ()->SoundEvents.NETHER_WOOD_FALL);
    public static final SoundType CALCIFIED_ROCK = new CalcifiedSoundType(1f, 0.8f, ()->SoundEvents.DEEPSLATE_BREAK, ()->SoundEvents.NETHERRACK_STEP, ()->SoundEvents.NETHERRACK_PLACE, ()->SoundEvents.NETHERRACK_HIT, ()->SoundEvents.NETHERRACK_FALL);
    public static final SoundType CALCIFIED_BRICK = new CalcifiedSoundType(1f, 0.8f, ()->SoundEvents.DEEPSLATE_TILES_BREAK, ()->SoundEvents.DEEPSLATE_TILES_STEP, ()->SoundEvents.DEEPSLATE_TILES_PLACE, ()->SoundEvents.DEEPSLATE_TILES_HIT, ()->SoundEvents.DEEPSLATE_TILES_FALL);
    public static final SoundType CALCIFIED_COVERING = new CalcifiedSoundType(2f, 1.1f, ()->SoundEvents.GRASS_BREAK, ()->SoundEvents.VINE_STEP, ()->SoundEvents.GRASS_PLACE, ()->SoundEvents.GRASS_HIT, ()->SoundEvents.GRASS_FALL);


    public static final SoundType HEAVY_CALCIFIED_BRICK = new CalcifiedSoundType(1f, 0.7f, ()->SoundEvents.NETHER_BRICKS_BREAK, ()->SoundEvents.NETHER_BRICKS_STEP, ()->SoundEvents.NETHER_BRICKS_PLACE, ()->SoundEvents.NETHER_BRICKS_HIT, ()->SoundEvents.NETHER_BRICKS_FALL);

    public static final SoundType ASPEN_WOOD = new DeferredSoundType(1f, 0.8f, ()->SoundEvents.CHERRY_WOOD_BREAK, ()->SoundEvents.CHERRY_WOOD_STEP, ()->SoundEvents.CHERRY_WOOD_PLACE, ()->SoundEvents.CHERRY_WOOD_HIT, ()->SoundEvents.CHERRY_WOOD_FALL);

    public static Supplier<SoundEvent> register(SoundEvent soundEvent) {
        return SOUNDS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }
}

