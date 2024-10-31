package com.smellysleepy.meadow.common.entity;

import com.smellysleepy.meadow.common.entity.goal.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;

import javax.annotation.*;

public class MooMooCow extends Cow {

    private static final EntityDataAccessor<Boolean> DATA_CURIOUS_ID = SynchedEntityData.defineId(MooMooCow.class, EntityDataSerializers.BOOLEAN);

    private float curiousAngle;
    private float curiousAngleOld;

    public MooMooCow(EntityType<? extends Cow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(MeadowItemTagRegistry.MINERAL_FRUIT), false));
        this.goalSelector.addGoal(5, new ThoroughlyExaminePlayerGoal(this, Player.class, 3.0F, 0.06f));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CURIOUS_ID, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
    }

    @Override
    public void tick() {
        super.tick();
        this.curiousAngleOld = curiousAngle;
        if (isCurious()) {
            curiousAngle += (1.0F - curiousAngle) * 0.4F;
        } else {
            curiousAngle += (0.0F - curiousAngle) * 0.4F;
        }
    }

    public void setIsCurious(boolean pIsInterested) {
        this.entityData.set(DATA_CURIOUS_ID, pIsInterested);
    }

    public boolean isCurious() {
        return this.entityData.get(DATA_CURIOUS_ID);
    }

    public float getHeadRollAngle(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.curiousAngleOld, this.curiousAngle) * 0.1F * (float) Math.PI;
    }

    @Nullable
    public Cow getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return MeadowEntityRegistry.MOO_MOO.get().create(pLevel);
    }
}