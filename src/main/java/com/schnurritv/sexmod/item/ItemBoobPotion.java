package com.schnurritv.sexmod.item;

import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBoobPotion extends Item {
    private final float targetScale;

    public ItemBoobPotion(Properties properties, float targetScale) {
        super(properties);
        this.targetScale = targetScale;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof BaseGirlEntity girl) {
            float currentScale = girl.getEntityData().get(SexEntity.BOOB_SCALE);
            if (currentScale != targetScale) {
                if (!player.level().isClientSide) {
                    girl.getEntityData().set(SexEntity.BOOB_SCALE, targetScale);
                    girl.playSound(SoundEvents.GENERIC_DRINK, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }
}
