package com.schnurritv.sexmod.networking;

import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class MovementStatePacket {
    private final int entityId;
    private final String state;

    public MovementStatePacket(int entityId, String state) {
        this.entityId = entityId;
        this.state = state;
    }

    public static void encode(MovementStatePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.state);
    }

    public static MovementStatePacket decode(FriendlyByteBuf buf) {
        return new MovementStatePacket(buf.readInt(), buf.readUtf());
    }

    public static void handle(MovementStatePacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                net.minecraft.world.entity.Entity entity = player.level().getEntity(msg.entityId);
                if (entity instanceof SexEntity sexEntity) {
                    sexEntity.getEntityData().set(SexEntity.MOVEMENT_STATE, msg.state);
                    if ("FOLLOW".equals(msg.state)) {
                        sexEntity.getEntityData().set(SexEntity.MASTER_UUID, player.getUUID().toString());
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
