package com.schnurritv.sexmod.entity.ai;

import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.PathType;
import java.util.EnumSet;

public class SexModFollowGoal extends Goal {
    private final BaseGirlEntity girl;
    private Player owner;
    private final double speedModifier;
    private final float startDistance;
    private final float stopDistance;
    private int timeToRecalcPath;

    public SexModFollowGoal(BaseGirlEntity girl, double speedModifier, float startDistance, float stopDistance) {
        this.girl = girl;
        this.speedModifier = speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.girl.getEntityData().get(SexEntity.IS_LOCKED)) return false;
        String masterUuidStr = this.girl.getEntityData().get(SexEntity.MASTER_UUID);
        if (masterUuidStr == null || masterUuidStr.isEmpty()) return false;
        
        try {
            java.util.UUID masterUuid = java.util.UUID.fromString(masterUuidStr);
            this.owner = this.girl.level().getPlayerByUUID(masterUuid);
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (this.owner == null) return false;
        if (this.owner.isSpectator()) return false;
        if (this.girl.distanceToSqr(this.owner) < (double)(this.startDistance * this.startDistance)) return false;
        return "FOLLOW".equals(this.girl.getEntityData().get(SexEntity.MOVEMENT_STATE));
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.owner = null;
        this.girl.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.girl.getLookControl().setLookAt(this.owner, 10.0F, (float)this.girl.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.girl.isLeashed() && !this.girl.isPassenger()) {
                if (this.girl.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                } else {
                    this.girl.getNavigation().moveTo(this.owner, this.speedModifier);
                }
            }
        }
    }

    private void teleportToOwner() {
        net.minecraft.core.BlockPos pos = this.owner.blockPosition();
        for(int i = 0; i < 10; ++i) {
            int j = this.randomInt(-3, 3);
            int k = this.randomInt(-1, 1);
            int l = this.randomInt(-3, 3);
            if (this.maybeTeleport(pos.getX() + j, pos.getY() + k, pos.getZ() + l)) {
                return;
            }
        }
    }

    private boolean maybeTeleport(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new net.minecraft.core.BlockPos(x, y, z))) {
            return false;
        } else {
            this.girl.moveTo((double)x + 0.5D, (double)y, (double)z + 0.5D, this.girl.getYRot(), this.girl.getXRot());
            this.girl.getNavigation().stop();
            return true;
        }
    }

    private boolean canTeleportTo(net.minecraft.core.BlockPos pos) {
        PathType pathtype = net.minecraft.world.level.pathfinder.WalkNodeEvaluator.getPathTypeStatic(this.girl, pos);
        if (pathtype != PathType.WALKABLE) {
            return false;
        } else {
            return this.girl.level().noCollision(this.girl, this.girl.getBoundingBox().move(pos.subtract(this.girl.blockPosition())));
        }
    }

    private int randomInt(int min, int max) {
        return this.girl.getRandom().nextInt(max - min + 1) + min;
    }
}
