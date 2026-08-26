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
                        case "House" -> {
                            String homePosStr = girl.getEntityData().get(SexEntity.HOME_POS);
                            net.minecraft.world.level.Level level = player.level();
                            net.minecraft.core.BlockPos targetPos;

                            if ("0|0|0".equals(homePosStr)) {
                                // Generate unique remote coordinates "in the middle of nowhere"
                                java.util.UUID uuid = girl.getUUID();
                                long seed = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
                                java.util.Random rand = new java.util.Random(seed);
                                
                                int x = (rand.nextBoolean() ? 1 : -1) * (50000 + rand.nextInt(100000));
                                int z = (rand.nextBoolean() ? 1 : -1) * (50000 + rand.nextInt(100000));
                                int y = 200;
                                
                                targetPos = new net.minecraft.core.BlockPos(x, y, z);
                                girl.getEntityData().set(SexEntity.HOME_POS, x + "|" + y + "|" + z);
                                
                                // Generate 5x5 hollow room made of white terracotta
                                for (int dx = -2; dx <= 2; dx++) {
                                    for (int dy = -1; dy <= 3; dy++) {
                                        for (int dz = -2; dz <= 2; dz++) {
                                            net.minecraft.core.BlockPos p = targetPos.offset(dx, dy, dz);
                                            if (dx == -2 || dx == 2 || dy == -1 || dy == 3 || dz == -2 || dz == 2) {
                                                level.setBlock(p, net.minecraft.world.level.block.Blocks.WHITE_TERRACOTTA.defaultBlockState(), 3);
                                            } else {
                                                level.setBlock(p, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
                                            }
                                        }
                                    }
                                }
                            } else {
                                String[] parts = homePosStr.split("\\|");
                                targetPos = new net.minecraft.core.BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                            }

                            // Store return position
                            girl.getEntityData().set(SexEntity.PRE_HOUSE_POS, player.getX() + "|" + player.getY() + "|" + player.getZ());
                            
                            // Teleport player and girl inside
                            player.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
                            girl.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, girl.getYRot(), girl.getXRot());
                            girl.getNavigation().stop();
                            girl.setDeltaMovement(0, 0, 0);
                        }
                        case "Leave House" -> {
                            String prePosStr = girl.getEntityData().get(SexEntity.PRE_HOUSE_POS);
                            if (!"0|0|0".equals(prePosStr)) {
                                String[] parts = prePosStr.split("\\|");
                                try {
                                    double px = Double.parseDouble(parts[0]);
                                    double py = Double.parseDouble(parts[1]);
                                    double pz = Double.parseDouble(parts[2]);
                                    player.teleportTo(px, py, pz);
                                    girl.moveTo(px, py, pz, girl.getYRot(), girl.getXRot());
                                    girl.getNavigation().stop();
                                    girl.setDeltaMovement(0, 0, 0);
                                    girl.getEntityData().set(SexEntity.PRE_HOUSE_POS, "0|0|0");
                                } catch (Exception ignored) {}
                            }
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
