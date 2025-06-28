package com.smellysleepy.meadow.common.entity;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.entity.goal.*;
import com.smellysleepy.meadow.registry.common.*;
import com.smellysleepy.meadow.registry.common.item.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
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
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MooMooCow extends Cow implements IForgeShearable {

    private static final EntityDataAccessor<Boolean> DATA_CURIOUS_ID = SynchedEntityData.defineId(MooMooCow.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_EXTRA_FLUFFY_ID = SynchedEntityData.defineId(MooMooCow.class, EntityDataSerializers.BOOLEAN);

    private float curiousAngle;
    private float curiousAngleOld;

    private int eatAnimationTimer;

    public int pearlflowerTimer;
    public BlockPos lastKnownPearlflowerPosition;

    public EatPearlFlowerGoal eatGoal;

    public MooMooCow(EntityType<? extends Cow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ARMOR, 4.0);
    }

    public static boolean checkMooMooSpawnRules(EntityType<? extends Animal> pAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        boolean brightEnoughToSpawn = isBrightEnoughToSpawn(pLevel, pPos);
        boolean b = pLevel.getBlockState(pPos.below()).is(MeadowBlockTagRegistry.MOOMOO_CAN_SPAWN_ON);
        return b && brightEnoughToSpawn;
    }

    @Override
    protected void registerGoals() {
        var eatGoal = this.eatGoal = new EatPearlFlowerGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(MeadowItemTagRegistry.MINERAL_FRUIT), false));
        this.goalSelector.addGoal(5, new ThoroughlyExaminePlayerGoal(this, Player.class, 3.0F, 0.06f));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(7, eatGoal);
        this.goalSelector.addGoal(8, new GoToPearlflowerGoal(this));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CURIOUS_ID, false);
        this.entityData.define(DATA_EXTRA_FLUFFY_ID, false);
    }

    @Override
    protected void customServerAiStep() {
        eatAnimationTimer = eatGoal.getEatAnimationTimer();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide) {
            this.eatAnimationTimer = Math.max(0, this.eatAnimationTimer - 1);
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();
        pearlflowerTimer++;
        curiousAngleOld = curiousAngle;
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

    @Override
    public void ate() {
        super.ate();
        setIsExtraFluffy(true);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        return isExtraFluffy();
    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        setIsExtraFluffy(false);
        level.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);
        return List.of(new ItemStack(MeadowItemRegistry.CLUMP_OF_FUR.get(), level.random.nextInt(2, 5)));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            this.eatAnimationTimer = 40;
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public float getHeadEatPositionScale(float pPartialTick) {
        if (this.eatAnimationTimer <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTimer >= 4 && this.eatAnimationTimer <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTimer < 4 ? ((float)this.eatAnimationTimer - pPartialTick) / 4.0F : -((float)(this.eatAnimationTimer - 40) - pPartialTick) / 4.0F;
        }
    }

    public float getHeadEatAngleScale(float pPartialTick) {
        if (this.eatAnimationTimer > 4 && this.eatAnimationTimer <= 36) {
            float f = ((float)(this.eatAnimationTimer - 4) - pPartialTick) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatAnimationTimer > 0 ? ((float)Math.PI / 5F) : this.getXRot() * ((float)Math.PI / 180F);
        }
    }

    public boolean pathfindDirectlyTowards(BlockPos pPos) {
        navigation.setMaxVisitedNodesMultiplier(10.0F);
        navigation.moveTo(pPos.getX(), pPos.getY(), pPos.getZ(), 1.0D);
        return navigation.getPath() != null && navigation.getPath().canReach();
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

    public void setIsExtraFluffy(boolean isSheared) {
        this.entityData.set(DATA_EXTRA_FLUFFY_ID, isSheared);
    }

    public boolean isExtraFluffy() {
        return this.entityData.get(DATA_EXTRA_FLUFFY_ID);
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

    public boolean doesTheBeastHunger() {
        return pearlflowerTimer > 100;
    }
    
    public BlockPos findPearlFlower(Level level, int range) {
        if (lastKnownPearlflowerPosition != null) {
            if (level.getBlockState(lastKnownPearlflowerPosition).is(MeadowBlockTagRegistry.MOOMOO_EDIBLE)) {
                return lastKnownPearlflowerPosition;
            }
            lastKnownPearlflowerPosition = null;
        }
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
                            var above = level.getBlockState(mutable.above());
                            if (above.canBeReplaced()) {
                                break;
                            }
                            if (above.is(MeadowBlockTagRegistry.MOOMOO_EDIBLE)) {
                                break;
                            }
                            if (!state.canBeReplaced()) {
                                mutable.move(Direction.UP);
                            }
                        }

                        var state = level.getBlockState(mutable);
                        if (!state.canBeReplaced()) {
                            set1.add(mutable.immutable());
                            var above = level.getBlockState(mutable.move(Direction.UP));
                            if (above.is(MeadowBlockTagRegistry.MOOMOO_EDIBLE)) {
                                lastKnownPearlflowerPosition = mutable.immutable();
                                return lastKnownPearlflowerPosition;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}