package com.smellysleepy.meadow.common.entity.goal;

import com.smellysleepy.meadow.common.entity.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.*;

public class ThoroughlyExaminePlayerGoal extends LookAtPlayerGoal {

    public int curiousCooldown;
    public int curiousTime;

    public ThoroughlyExaminePlayerGoal(MooMooCow cow, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(cow, pLookAtType, pLookDistance);
    }

    public ThoroughlyExaminePlayerGoal(MooMooCow cow, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability) {
        super(cow, pLookAtType, pLookDistance, pProbability);
    }

    public ThoroughlyExaminePlayerGoal(MooMooCow cow, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability, boolean pOnlyHorizontal) {
        super(cow, pLookAtType, pLookDistance, pProbability, pOnlyHorizontal);
    }

    @Override
    public void start() {
        lookTime = adjustedTickDelay(120 + mob.getRandom().nextInt(120));
        if (mob.getNavigation().isInProgress()) {
            mob.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        var cow = (MooMooCow) mob;
        if (curiousCooldown == 0 && curiousTime == 0) {
            if (mob.getRandom().nextFloat() < 0.05f) {
                cow.setIsCurious(true);
                curiousTime = adjustedTickDelay(80 + mob.getRandom().nextInt(80));
                lookTime += curiousTime;
                curiousCooldown = curiousTime + 40;
            }
        }
        if (curiousTime > 0) {
            curiousTime--;
            if (curiousTime == 0) {
                cow.setIsCurious(false);
            }
        } else if (curiousCooldown > 0) {
            curiousCooldown--;
        }
    }

    @Override
    public void stop() {
        super.stop();
        var cow = (MooMooCow) mob;
        cow.setIsCurious(false);
    }
}