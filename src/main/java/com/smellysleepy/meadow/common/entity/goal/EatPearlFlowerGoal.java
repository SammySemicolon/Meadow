package com.smellysleepy.meadow.common.entity.goal;

import com.smellysleepy.meadow.common.block.pearlflower.PearlFlowerReplacementHandler;
import com.smellysleepy.meadow.common.entity.*;
import com.smellysleepy.meadow.registry.common.tags.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.*;

import java.util.*;

public class EatPearlFlowerGoal extends Goal {

    private final MooMooCow cow;
    private final Level level;
    private BlockPos objectOfInterest;
    private int eatAnimationTimer;

    public EatPearlFlowerGoal(MooMooCow cow) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        this.cow = cow;
        this.level = cow.level();
    }

    @Override
    public boolean canUse() {
        return cow.lastKnownPearlflowerPosition != null &&
                level.getBlockState(cow.lastKnownPearlflowerPosition).is(MeadowBlockTagRegistry.MOOMOO_EDIBLE) &&
                !cow.hasRestriction() && cow.doesTheBeastHunger() && cow.closerThan(cow.lastKnownPearlflowerPosition, 1.5f);
    }

    @Override
    public void start() {
        BlockPos pearlflower = cow.findPearlFlower(level, 4);
        if (pearlflower != null) {
            eatAnimationTimer = adjustedTickDelay(40);
            objectOfInterest = pearlflower;
            level.broadcastEntityEvent(cow, (byte)10);
            cow.getNavigation().stop();
        }
        else {
            cow.pearlflowerTimer = 0;
        }
    }

    @Override
    public void tick() {
        eatAnimationTimer = Math.max(0, eatAnimationTimer - 1);
        if (eatAnimationTimer == this.adjustedTickDelay(4)) {
            final Level level = cow.level();
            if (objectOfInterest != null) {
                final BlockState blockState = level.getBlockState(objectOfInterest);
                if (blockState.is(MeadowBlockTagRegistry.MOOMOO_EDIBLE)) {
                    Block block = blockState.getBlock();
                    level.destroyBlock(objectOfInterest, false);
                    PearlFlowerReplacementHandler.performExchange(block, level, objectOfInterest, blockState);
                    cow.ate();
                }
            }
        }
    }

    public void stop() {
        eatAnimationTimer = 0;
        objectOfInterest = null;
        cow.pearlflowerTimer = 0;
    }

    public boolean canContinueToUse() {
        return eatAnimationTimer > 0;
    }

    public int getEatAnimationTimer() {
        return eatAnimationTimer;
    }
}