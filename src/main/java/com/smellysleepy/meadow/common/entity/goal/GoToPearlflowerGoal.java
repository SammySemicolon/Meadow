package com.smellysleepy.meadow.common.entity.goal;

import com.smellysleepy.meadow.common.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.logging.*;

public class GoToPearlflowerGoal extends Goal {

    private final MooMooCow cow;
    private final Level level;
    private BlockPos objectOfInterest;
    private int timeSinceTheJourneyBegan;

    public GoToPearlflowerGoal(MooMooCow cow) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        this.cow = cow;
        this.level = cow.level();
    }

    @Override
    public boolean canUse() {
        return cow.doesTheBeastHunger();
    }

    @Override
    public boolean canContinueToUse() {
        return objectOfInterest != null && !cow.hasRestriction() && cow.doesTheBeastHunger() && !cow.closerThan(objectOfInterest, 1);
    }

    @Override
    public void start() {
        timeSinceTheJourneyBegan = 0;
        BlockPos pearlflower = cow.findPearlFlower(level, 16);
        if (pearlflower != null) {
            objectOfInterest = pearlflower;
        }
        else {
            cow.pearlflowerTimer = 0;
        }
    }

    @Override
    public void stop() {
        timeSinceTheJourneyBegan = 0;
        objectOfInterest = null;
        cow.getNavigation().stop();
        cow.getNavigation().resetMaxVisitedNodesMultiplier();
    }

    @Override
    public void tick() {
        if (objectOfInterest != null) {
            timeSinceTheJourneyBegan++;
            if (timeSinceTheJourneyBegan > adjustedTickDelay(1200)) {
                objectOfInterest = null;
            } else if (!cow.getNavigation().isInProgress()) {
                if (cow.isTooFarAway(objectOfInterest)) {
                    objectOfInterest = null;
                }
                else if (cow.closerThan(objectOfInterest, 3f)) {
                    cow.pathfindDirectlyTowards(objectOfInterest);
                }
                else {
                    cow.pathfindRandomlyTowards(objectOfInterest);
                }
            }
        }
    }
}