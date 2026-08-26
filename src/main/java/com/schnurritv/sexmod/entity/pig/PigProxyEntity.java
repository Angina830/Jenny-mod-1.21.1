package com.schnurritv.sexmod.entity.pig;

import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import java.util.List;

public class PigProxyEntity extends BaseGirlEntity {

    public static final net.minecraft.network.syncher.EntityDataAccessor<String> ANIMAL_TYPE = net.minecraft.network.syncher.SynchedEntityData.defineId(PigProxyEntity.class, net.minecraft.network.syncher.EntityDataSerializers.STRING);

    public PigProxyEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }
    
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMAL_TYPE, "pig");
    }

    @Override
    public String getGirlName() {
        return this.entityData.get(ANIMAL_TYPE);
    }

    @Override
    public String getModelName() {
        return "jenny";
    }

    @Override
    public String getGeoFileName() {
        return "jennydressed";
    }

    @Override
    public String getNudeGeoFileName() {
        return "jennynude";
    }

    @Override
    public List<String> getAvailableActions() {
        return List.of("Doggy", "Stop");
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        // If we are not in a scene anymore (action became NULL), we should despawn
        if (getSexModAnimation() == com.schnurritv.sexmod.entity.SexModAnimation.NULL && !this.entityData.get(IS_LOCKED)) {
            // But only if we were actually used. We wait 10 seconds before timeout.
            if (this.tickCount > 200) {
                this.discard();
            }
        }
        
        // Find nearest vanilla animal and "lock" it if we are in a scene
        if (this.entityData.get(IS_LOCKED)) {
            List<net.minecraft.world.entity.LivingEntity> animals = this.level().getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, this.getBoundingBox().inflate(1.0), e -> 
                e instanceof net.minecraft.world.entity.animal.Pig || 
                e instanceof net.minecraft.world.entity.animal.Chicken || 
                e instanceof net.minecraft.world.entity.animal.Sheep
            );
            
            // Adjust height offset per animal
            double verticalOffset = -0.5;
            if ("chicken".equals(getGirlName())) verticalOffset = -0.1;
            if ("sheep".equals(getGirlName())) verticalOffset = -0.8;

            for (net.minecraft.world.entity.LivingEntity animal : animals) {
                animal.moveTo(this.getX(), this.getY() + verticalOffset, this.getZ(), this.getYRot(), this.getXRot());
                animal.setXRot(this.getXRot());
                animal.yBodyRot = this.yBodyRot;
                animal.yHeadRot = this.yHeadRot;
                if (animal instanceof net.minecraft.world.entity.Mob mob) {
                    mob.getNavigation().stop();
                }
                animal.setDeltaMovement(0, 0, 0);
            }
        }
    }
}
