package com.schnurritv.sexmod.entity.ai;

import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class SexModMoveToTargetGoal extends Goal {
    private final BaseGirlEntity girl;
    private final double speedModifier;
    private double x;
    private double y;
    private double z;

    public SexModMoveToTargetGoal(BaseGirlEntity girl, double speedModifier) {
        this.girl = girl;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.girl.getEntityData().get(SexEntity.IS_LOCKED)) return false;
        String targetPosStr = this.girl.getEntityData().get(SexEntity.TARGET_POS);
        if ("0|0|0".equals(targetPosStr)) return false;

        String[] parts = targetPosStr.split("\\|");
        this.x = Double.parseDouble(parts[0]);
        this.y = Double.parseDouble(parts[1]);
        this.z = Double.parseDouble(parts[2]);

        return this.girl.distanceToSqr(this.x, this.y, this.z) > 1.0D;
    }

    @Override
    public void start() {
        this.girl.getNavigation().moveTo(this.x, this.y, this.z, this.speedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.girl.getNavigation().isDone() && this.girl.distanceToSqr(this.x, this.y, this.z) > 1.0D;
    }
}
