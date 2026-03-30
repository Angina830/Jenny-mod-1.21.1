package com.schnurritv.sexmod.networking;

import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.entity.SexModAnimation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class AnimationSyncPacket {
    private final int entityId;
    private final String animationName;

    public AnimationSyncPacket(int entityId, String animationName) {
        this.entityId = entityId;
        this.animationName = animationName;
    }

    public static void encode(AnimationSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.animationName);
    }

    public static AnimationSyncPacket decode(FriendlyByteBuf buf) {
        return new AnimationSyncPacket(buf.readInt(), buf.readUtf());
    }

    public static void handle(AnimationSyncPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.level != null) {
                net.minecraft.world.entity.Entity entity = mc.level.getEntity(msg.entityId);
                if (entity instanceof SexEntity sexEntity) {
                    try {
                        sexEntity.setSexModAnimation(SexModAnimation.valueOf(msg.animationName));
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
