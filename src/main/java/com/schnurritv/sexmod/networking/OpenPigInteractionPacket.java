package com.schnurritv.sexmod.networking;

import com.schnurritv.sexmod.client.gui.InteractionScreen;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;

public class OpenPigInteractionPacket {
    private final int entityId;

    public OpenPigInteractionPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(OpenPigInteractionPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static OpenPigInteractionPacket decode(FriendlyByteBuf buf) {
        return new OpenPigInteractionPacket(buf.readInt());
    }

    public static void handle(OpenPigInteractionPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                net.minecraft.world.entity.Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                if (entity instanceof BaseGirlEntity girl) {
                    Minecraft.getInstance().setScreen(new InteractionScreen(girl, girl.getAvailableActions()));
                }
            });
        });
        ctx.setPacketHandled(true);
    }
}
