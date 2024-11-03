package com.smellysleepy.meadow.common.entity;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.entity.goal.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;

public class MooMooCow extends Cow {

    private static final EntityDataAccessor<Boolean> DATA_CURIOUS_ID = SynchedEntityData.defineId(MooMooclass, EntityDataSerializers.BOOLEAN);

    private float curiousAngle;
    private float curiousAngleOld;

    public int pearlflowerTimer;
    public BlockPos lastKnownPearlflowerPosition;

    public MooMooCow(EntityType<? extends Cow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.15F);
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
        this.goalSelector.addGoal(7, new GoToPearlflowerGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CURIOUS_ID, false);
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

    @Override
    public Cow getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return MeadowEntityRegistry.MOO_MOO.get().create(pLevel);
    }

    public void pathfindRandomlyTowards(BlockPos goTo) {
        Vec3 goToBottomCenter = Vec3.atBottomCenterOf(goTo);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int yDifference = (int)goToBottomCenter.y - blockpos.getY();
        if (yDifference > 2) {
            i = 4;
        } else if (yDifference < -2) {
            i = -4;
        }

        int radius = 6;
        int yRadius = 8;
        int distance = blockpos.distManhattan(goTo);
        if (distance < 15) {
            radius = distance / 2;
            yRadius = distance / 2;
        }

        Vec3 pos = AirRandomPos.getPosTowards(this, radius, yRadius, i, goToBottomCenter, (float)Math.PI / 10F);
        if (pos != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(pos.x, pos.y, pos.z, 1.0D);
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

    public boolean closerThan(BlockPos pos, double distance) {
        return position().closerThan(pos.getCenter(), distance);
    }

    public boolean isTooFarAway(BlockPos pos) {
        return !closerThan(pos, 32);
    }
    
    public BlockPos findPearlFlower() {
        var level = level();
        int range = 20;
        int verticalRange = 4;
        List<Set<BlockPos>> sets = Lists.newArrayList();
        var mutable = new BlockPos.MutableBlockPos();
        for (int j = 0; j < range; ++j) {
            sets.add(Sets.newHashSet());
        }
        sets.get(0).add(blockPosition());
        for (int l = 1; l < range; ++l) {
            Set<BlockPos> set = sets.get(l - 1);
            Set<BlockPos> set1 = sets.get(l);

            for (BlockPos pos : set) {
                for (int i = 0; i < 4; i++) {
                    var direction = Direction.from2DDataValue(i);
                    mutable.setWithOffset(pos, direction);
                    if (!set.contains(mutable) && !set1.contains(mutable)) {
                        for (int j = 0; j < verticalRange; j++) {
                            var state = level.getBlockState(mutable);
                            if (state.canBeReplaced()) {
                                mutable.move(Direction.DOWN);
                            }
                        }
                        for (int j = 0; j < verticalRange; j++) {
                            var state = level.getBlockState(mutable);
                            if (!state.canBeReplaced()) {
                                mutable.move(Direction.UP);
                            }
                        }

                        var state = level.getBlockState(mutable);
                        if (state.is(MeadowBlockTagRegistry.PEARLFLOWER_CAN_PLACE_ON)) {
                            var above = level.getBlockState(mutable.move(Direction.UP));
                            if (above.is(MeadowBlockTagRegistry.MOOMOO_EDIBLE)) {
                                return mutable.immutable();
                            }
                            set1.add(mutable.immutable());
                        }
                    }
                }
            }
        }
        return null;
    }
}