package com.schnurritv.sexmod.scene;

import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexModAnimation;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class SceneManager {

    private static BlockPos getBedFoot(BaseGirlEntity girl) {
        Level level = girl.level();
        BlockPos pos = girl.blockPosition();
        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, 1, 2))) {
            net.minecraft.world.level.block.state.BlockState state = level.getBlockState(p);
            if (state.getBlock() instanceof BedBlock) {
                if (state.getValue(BedBlock.PART) == net.minecraft.world.level.block.state.properties.BedPart.HEAD) {
                    return p.relative(state.getValue(BedBlock.FACING).getOpposite());
                }
                return p;
            }
        }
        return null;
    }

    private static void preparePlayerForScene(BaseGirlEntity girl, Player player, BlockPos bedPos) {
        double x, y, z;
        float rot;

        if (bedPos != null) {
            x = bedPos.getX() + 0.5;
            y = bedPos.getY() + 0.5625;
            z = bedPos.getZ() + 0.5;
            net.minecraft.world.level.block.state.BlockState state = girl.level().getBlockState(bedPos);
            rot = girl.getYRot();
            if (state.getBlock() instanceof BedBlock) {
                rot = state.getValue(BedBlock.FACING).toYRot();
            }
        } else {
            x = girl.getX();
            y = girl.getY() + 0.5;
            z = girl.getZ();
            rot = girl.getYRot();
        }

        // Apply custom offsets based on animal type
        String animal = girl.getGirlName();
        if ("pig".equals(animal) || "chicken".equals(animal) || "sheep".equals(animal)) {
            double h = 0.5; // Default (pig)
            double back = 0.2;
            
            if ("chicken".equals(animal)) {
                h = 0.5;
                back = 0.3;
            } else if ("sheep".equals(animal)) {
                h = 0.7;
                back = 0.3;
            }
            
            // Apply a block lift for bedless scenes
            if (bedPos == null) {
                y = girl.getY() + h;
            }

            // Apply backward offset
            double rad = Math.toRadians(rot);
            x += Math.sin(rad) * back;
            z -= Math.cos(rad) * back;
        }

        girl.teleportTo(x, y, z);
        girl.setYRot(rot);

        // Remove the -0.1 offset if no bed is present to prevent falling into blocks
        double playerY = (bedPos != null) ? y - 0.1 : y;

        player.teleportTo(x, playerY, z);
        player.setYRot(rot);
        player.setXRot(15.0f);

        girl.getEntityData().set(SexEntity.IS_LOCKED, true);
        girl.getEntityData().set(SexEntity.PARTNER_UUID, player.getUUID().toString());
        girl.getEntityData().set(SexEntity.TARGET_ROTATION, rot);
        girl.getEntityData().set(SexEntity.TARGET_POS, x + "|" + playerY + "|" + z);
        girl.getEntityData().set(SexEntity.CLOTHING_STATE, 1);
    }

    public static void startMissionary(BaseGirlEntity girl, Player player) {
        if (girl.level().isClientSide) return;

        BlockPos bedPos = getBedFoot(girl);
        String name = girl.getGirlName();
        if (bedPos == null && !"pig".equals(name) && !"chicken".equals(name) && !"sheep".equals(name)) {
            player.displayClientMessage(Component.literal("This action requires a bed nearby!"), true);
            return;
        }

        preparePlayerForScene(girl, player, bedPos);
        girl.setSexModAnimation(SexModAnimation.MISSIONARY_START);
        girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, SexModAnimation.MISSIONARY_SLOW.name());
        girl.getEntityData().set(SexEntity.ANIMATION_TICKS, 0);
    }

    public static void startBlowjob(BaseGirlEntity girl, Player player) {
        if (girl.level().isClientSide) return;
        preparePlayerForScene(girl, player, girl.blockPosition());
        girl.setSexModAnimation(SexModAnimation.BLOWJOBINTRO);
        girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, SexModAnimation.BLOWJOBSUCK.name());
        girl.getEntityData().set(SexEntity.ANIMATION_TICKS, 0);
    }

    public static void startBoobjob(BaseGirlEntity girl, Player player) {
        if (girl.level().isClientSide) return;
        preparePlayerForScene(girl, player, girl.blockPosition());
        girl.setSexModAnimation(SexModAnimation.PAIZURI_START);
        girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, SexModAnimation.PAIZURI_SLOW.name());
        girl.getEntityData().set(SexEntity.ANIMATION_TICKS, 0);
    }

    public static void startDoggy(BaseGirlEntity girl, Player player) {
        if (girl.level().isClientSide) return;
        
        BlockPos bedPos = getBedFoot(girl);
        String name = girl.getGirlName();
        if (bedPos == null && !"pig".equals(name) && !"chicken".equals(name) && !"sheep".equals(name)) {
            player.displayClientMessage(Component.literal("This action requires a bed nearby!"), true);
            return;
        }

        preparePlayerForScene(girl, player, bedPos);
        girl.setSexModAnimation(SexModAnimation.DOGGYGOONBED);
        girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, SexModAnimation.DOGGYSTART.name());
        girl.getEntityData().set(SexEntity.ANIMATION_TICKS, 0);
    }

    public static void stopScene(BaseGirlEntity girl) {
        if (girl.level().isClientSide) return;
        girl.getEntityData().set(SexEntity.IS_LOCKED, false);
        girl.getEntityData().set(SexEntity.PARTNER_UUID, "null");
        girl.setSexModAnimation(SexModAnimation.NULL);
        girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, "null");
        girl.getEntityData().set(SexEntity.CLOTHING_STATE, 0);
    }
}
