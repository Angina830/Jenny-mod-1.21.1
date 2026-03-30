package com.schnurritv.sexmod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class SexEntity extends PathfinderMob implements GeoEntity {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<String> MASTER_UUID = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> IS_LOCKED = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> TARGET_POS = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Float> TARGET_ROTATION = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<String> OWN_UUID = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> CURRENT_MODEL = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> CURRENT_ACTION = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> PARTNER_UUID = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> MOVEMENT_STATE = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> HOME_POS = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIMATION_FOLLOW_UP = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> ANIMATION_TICKS = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> CLOTHING_STATE = SynchedEntityData.defineId(SexEntity.class, EntityDataSerializers.INT);

    protected SexEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 20.0D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MASTER_UUID, "");
        builder.define(IS_LOCKED, false);
        builder.define(TARGET_POS, "0|0|0");
        builder.define(TARGET_ROTATION, 0.0f);
        builder.define(OWN_UUID, UUID.randomUUID().toString());
        builder.define(CURRENT_MODEL, 0);
        builder.define(CURRENT_ACTION, SexModAnimation.NULL.name());
        builder.define(PARTNER_UUID, "null");
        builder.define(MOVEMENT_STATE, "WALK");
        builder.define(HOME_POS, "0|0|0");
        builder.define(ANIMATION_FOLLOW_UP, "null");
        builder.define(ANIMATION_TICKS, 0);
        builder.define(CLOTHING_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("MasterUUID", this.entityData.get(MASTER_UUID));
        compound.putBoolean("IsLocked", this.entityData.get(IS_LOCKED));
        compound.putString("TargetPos", this.entityData.get(TARGET_POS));
        compound.putFloat("TargetRotation", this.entityData.get(TARGET_ROTATION));
        compound.putString("OwnUUID", this.entityData.get(OWN_UUID));
        compound.putInt("CurrentModel", this.entityData.get(CURRENT_MODEL));
        compound.putString("CurrentAction", this.entityData.get(CURRENT_ACTION));
        compound.putString("PartnerUUID", this.entityData.get(PARTNER_UUID));
        compound.putString("MovementState", this.entityData.get(MOVEMENT_STATE));
        compound.putString("HomePos", this.entityData.get(HOME_POS));
        compound.putString("AnimationFollowUp", this.entityData.get(ANIMATION_FOLLOW_UP));
        compound.putInt("AnimationTicks", this.entityData.get(ANIMATION_TICKS));
        compound.putInt("ClothingState", this.entityData.get(CLOTHING_STATE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("MasterUUID")) this.entityData.set(MASTER_UUID, compound.getString("MasterUUID"));
        if (compound.contains("IsLocked")) this.entityData.set(IS_LOCKED, compound.getBoolean("IsLocked"));
        if (compound.contains("TargetPos")) this.entityData.set(TARGET_POS, compound.getString("TargetPos"));
        if (compound.contains("TargetRotation")) this.entityData.set(TARGET_ROTATION, compound.getFloat("TargetRotation"));
        if (compound.contains("OwnUUID")) this.entityData.set(OWN_UUID, compound.getString("OwnUUID"));
        if (compound.contains("CurrentModel")) this.entityData.set(CURRENT_MODEL, compound.getInt("CurrentModel"));
        if (compound.contains("CurrentAction")) this.entityData.set(CURRENT_ACTION, compound.getString("CurrentAction"));
        if (compound.contains("PartnerUUID")) this.entityData.set(PARTNER_UUID, compound.getString("PartnerUUID"));
        if (compound.contains("MovementState")) this.entityData.set(MOVEMENT_STATE, compound.getString("MovementState"));
        if (compound.contains("HomePos")) this.entityData.set(HOME_POS, compound.getString("HomePos"));
        if (compound.contains("AnimationFollowUp")) this.entityData.set(ANIMATION_FOLLOW_UP, compound.getString("AnimationFollowUp"));
        if (compound.contains("AnimationTicks")) this.entityData.set(ANIMATION_TICKS, compound.getInt("AnimationTicks"));
        if (compound.contains("ClothingState")) this.entityData.set(CLOTHING_STATE, compound.getInt("ClothingState"));
    }

    public void setSexModAnimation(SexModAnimation animation) {
        this.entityData.set(CURRENT_ACTION, animation.name());
        if (!this.level().isClientSide) {
            com.schnurritv.sexmod.networking.NetworkHandler.broadcastAnimationSync(this, animation);
        }
    }

    public SexModAnimation getSexModAnimation() {
        try {
            return SexModAnimation.valueOf(this.entityData.get(CURRENT_ACTION));
        } catch (IllegalArgumentException e) {
            return SexModAnimation.NULL;
        }
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.getEntityData().get(IS_LOCKED)) {
            float rot = this.getEntityData().get(TARGET_ROTATION);
            this.setYRot(rot);
            this.yRotO = rot;
            this.yBodyRot = rot;
            this.yBodyRotO = rot;
            this.yHeadRot = rot;
            this.yHeadRotO = rot;
            this.setXRot(0);
            this.getNavigation().stop();
        }

        if (this.level().isClientSide) {
            for (net.minecraft.world.entity.EquipmentSlot slot : net.minecraft.world.entity.EquipmentSlot.values()) {
                super.setItemSlot(slot, net.minecraft.world.item.ItemStack.EMPTY);
            }
        } else {
            handleAnimationSequencing();
        }
    }

    @Override
    public float getScale() {
        return 1.0f;
    }

    @Override
    public void travel(net.minecraft.world.phys.Vec3 pos) {
        if (this.getEntityData().get(IS_LOCKED)) {
            this.setDeltaMovement(0, 0, 0);
            return;
        }
        super.travel(pos);
    }

    protected void handleAnimationSequencing() {
        SexModAnimation current = getSexModAnimation();
        String followUpName = this.entityData.get(ANIMATION_FOLLOW_UP);

        if (current == SexModAnimation.DOGGYSTART && ("null".equals(followUpName) || followUpName.isEmpty())) {
            followUpName = SexModAnimation.DOGGYWAIT.name();
        }

        if ("null".equals(followUpName) || followUpName.isEmpty()) return;

        int length = current.getLength();
        if (length <= 0) return;

        int ticks = this.entityData.get(ANIMATION_TICKS) + 1;
        this.entityData.set(ANIMATION_TICKS, ticks);

        if (ticks >= length) {
            try {
                SexModAnimation followUp = SexModAnimation.valueOf(followUpName);
                setSexModAnimation(followUp);
                this.entityData.set(ANIMATION_TICKS, 0);
                this.entityData.set(ANIMATION_FOLLOW_UP, "null");
            } catch (IllegalArgumentException e) {
                this.entityData.set(ANIMATION_FOLLOW_UP, "null");
            }
        }
    }

    @Override
    public net.minecraft.world.item.ItemStack getItemBySlot(net.minecraft.world.entity.EquipmentSlot slot) {
        if (this.level().isClientSide) {
            return net.minecraft.world.item.ItemStack.EMPTY;
        }
        return super.getItemBySlot(slot);
    }

    @Override
    public void setItemSlot(net.minecraft.world.entity.EquipmentSlot slot, net.minecraft.world.item.ItemStack stack) {
        if (this.level().isClientSide) {
             super.setItemSlot(slot, net.minecraft.world.item.ItemStack.EMPTY);
             return;
        }
        super.setItemSlot(slot, stack);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public abstract String getModelName();

    public String getGeoFileName() {
        return getModelName();
    }

    public String getNudeGeoFileName() {
        return getGeoFileName();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "base", 5, state -> {
            SexModAnimation current = getSexModAnimation();
            String model = getModelName().toLowerCase();

            // --- Scene Animation priority ---
            if (current != SexModAnimation.NULL) {
                String animName = current.name().toLowerCase();
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation." + model + "." + animName));
            }

            // --- Movement Animation ---
            if (state.isMoving()) {
                String moveState = this.entityData.get(MOVEMENT_STATE);
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation." + model + "." + moveState.toLowerCase()));
            }

            // --- Idle ---
            return state.setAndContinue(RawAnimation.begin().thenLoop("animation." + model + ".idle"));
        }));

        controllers.add(new AnimationController<>(this, "eyes", 0, state -> {
            SexModAnimation current = getSexModAnimation();
            if (current != null && current.autoBlink) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation." + getModelName().toLowerCase() + ".blink"));
            }
            return state.setAndContinue(RawAnimation.begin());
        }));
    }
}
