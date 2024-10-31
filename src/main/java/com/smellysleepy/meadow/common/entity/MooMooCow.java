package com.smellysleepy.meadow.common.entity;

import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;

import javax.annotation.*;

public class MooMooCow extends Cow {

    public MooMooCow(EntityType<? extends Cow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(MeadowItemTagRegistry.MINERAL_FRUIT), false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
    }

    @Nullable
    public Cow getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return MeadowEntityRegistry.MOO_MOO.get().create(pLevel);
    }
}
