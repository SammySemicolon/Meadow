package com.smellysleepy.meadow.common.entity.goal;

import com.google.common.collect.*;
import com.smellysleepy.meadow.common.block.meadow.flora.pearlflower.*;
import com.smellysleepy.meadow.common.entity.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

public class GoToPearlflowerGoal extends Goal {

    private final MooMooCow cow;
    private BlockPos objectOfInterest;
    private int timeSinceTheJourneyBegan;

    public GoToPearlflowerGoal(MooMooCow cow) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        this.cow = cow;
    }

    @Override
    public boolean canUse() {
        return cow.theBeastHungers();
    }

    @Override
    public boolean canContinueToUse() {
        return objectOfInterest != null && !cow.hasRestriction() && cow.theBeastHungers() && !cow.closerThan(objectOfInterest, 1);
    }

    @Override
    public void start() {
        timeSinceTheJourneyBegan = 0;
        BlockPos pearlflower = cow.findPearlFlower(16);
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