package com.schnurritv.sexmod.networking;

import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.entity.SexModAnimation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SceneActionPacket {
    private final int entityId;
    private final String action;

    public SceneActionPacket(int entityId, String action) {
        this.entityId = entityId;
        this.action = action;
    }

    public static void encode(SceneActionPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.action);
    }

    public static SceneActionPacket decode(FriendlyByteBuf buf) {
        return new SceneActionPacket(buf.readInt(), buf.readUtf());
    }

    public static void handle(SceneActionPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                net.minecraft.world.entity.Entity entity = player.level().getEntity(msg.entityId);
                if (entity instanceof com.schnurritv.sexmod.entity.BaseGirlEntity girl) {
                    switch (msg.action) {
                        case "Thrust" -> {
                            SexModAnimation current = girl.getSexModAnimation();
                            if (current == SexModAnimation.MISSIONARY_SLOW) {
                                girl.setSexModAnimation(SexModAnimation.MISSIONARY_FAST);
                            } else if (current == SexModAnimation.MISSIONARY_FAST) {
                                girl.setSexModAnimation(SexModAnimation.MISSIONARY_SLOW);
                            } else if (current == SexModAnimation.BLOWJOBSUCK) {
                                girl.setSexModAnimation(SexModAnimation.BLOWJOBTHRUST);
                            } else if (current == SexModAnimation.BLOWJOBTHRUST) {
                                girl.setSexModAnimation(SexModAnimation.BLOWJOBSUCK);
                            } else if (current == SexModAnimation.DOGGYWAIT || current == SexModAnimation.DOGGYSTART) {
                                girl.setSexModAnimation(SexModAnimation.DOGGYSLOW);
                                girl.getEntityData().set(SexEntity.ANIMATION_FOLLOW_UP, "null");
                                girl.getEntityData().set(SexEntity.ANIMATION_TICKS, 0);
                            } else if (current == SexModAnimation.DOGGYSLOW) {
                                girl.setSexModAnimation(SexModAnimation.DOGGYFAST);
                            } else if (current == SexModAnimation.DOGGYFAST) {
                                girl.setSexModAnimation(SexModAnimation.DOGGYSLOW);
                            } else if (current == SexModAnimation.PAIZURI_SLOW) {
                                girl.setSexModAnimation(SexModAnimation.PAIZURI_FAST);
                            } else if (current == SexModAnimation.PAIZURI_FAST) {
                                girl.setSexModAnimation(SexModAnimation.PAIZURI_SLOW);
                            }
                        }
                        case "Missionary" -> com.schnurritv.sexmod.scene.SceneManager.startMissionary(girl, player);
                        case "Doggy" -> com.schnurritv.sexmod.scene.SceneManager.startDoggy(girl, player);
                        case "Blowjob" -> com.schnurritv.sexmod.scene.SceneManager.startBlowjob(girl, player);
                        case "Boobjob" -> com.schnurritv.sexmod.scene.SceneManager.startBoobjob(girl, player);
                        case "Stop" -> com.schnurritv.sexmod.scene.SceneManager.stopScene(girl);
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
