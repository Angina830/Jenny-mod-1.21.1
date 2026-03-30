package com.schnurritv.sexmod.entity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.schnurritv.sexmod.client.gui.InteractionScreen;
import com.schnurritv.sexmod.entity.ai.SexModFollowGoal;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;
import java.util.List;

public abstract class BaseGirlEntity extends SexEntity {

    protected final ItemStackHandler inventory = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            updateSyncedInventory();
        }
    };

    private final LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    public static final EntityDataAccessor<ItemStack> ITEM_0 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> ITEM_1 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> ITEM_2 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> ITEM_3 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> ITEM_4 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<ItemStack> ITEM_5 = SynchedEntityData.defineId(BaseGirlEntity.class, EntityDataSerializers.ITEM_STACK);

    protected BaseGirlEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new com.schnurritv.sexmod.entity.ai.SexModMoveToTargetGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new SexModFollowGoal(this, 1.25D, 10.0F, 2.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ITEM_0, ItemStack.EMPTY);
        builder.define(ITEM_1, ItemStack.EMPTY);
        builder.define(ITEM_2, ItemStack.EMPTY);
        builder.define(ITEM_3, ItemStack.EMPTY);
        builder.define(ITEM_4, ItemStack.EMPTY);
        builder.define(ITEM_5, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Inventory", inventory.serializeNBT(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Inventory")) {
            inventory.deserializeNBT(this.registryAccess(), compound.getCompound("Inventory"));
        }
    }

    protected void updateSyncedInventory() {
        if (this.getEntityData().get(SexEntity.IS_LOCKED)) {
            this.entityData.set(ITEM_0, ItemStack.EMPTY);
            this.entityData.set(ITEM_1, ItemStack.EMPTY);
            this.entityData.set(ITEM_2, ItemStack.EMPTY);
            this.entityData.set(ITEM_3, ItemStack.EMPTY);
            this.entityData.set(ITEM_4, ItemStack.EMPTY);
            this.entityData.set(ITEM_5, ItemStack.EMPTY);
            return;
        }
        this.entityData.set(ITEM_0, inventory.getStackInSlot(0));
        this.entityData.set(ITEM_1, inventory.getStackInSlot(1));
        this.entityData.set(ITEM_2, inventory.getStackInSlot(2));
        this.entityData.set(ITEM_3, inventory.getStackInSlot(3));
        this.entityData.set(ITEM_4, inventory.getStackInSlot(4));
        this.entityData.set(ITEM_5, inventory.getStackInSlot(5));
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onSyncedDataUpdated(net.minecraft.network.syncher.EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (IS_LOCKED.equals(key)) {
            updateSyncedInventory();
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.level().isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().setScreen(new InteractionScreen(this, getAvailableActions()));
            });
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    public List<String> getAvailableActions() {
        return List.of("Follow", "Stay");
    }

    public abstract String getGirlName();

    @Override
    public String getModelName() {
        return getGirlName();
    }
}
