package com.schnurritv.sexmod.networking;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.entity.SexModAnimation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    private static final int VERSION = 1;
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Main.MODID, "main"))
            .networkProtocolVersion(VERSION)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(AnimationSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(AnimationSyncPacket::encode)
                .decoder(AnimationSyncPacket::decode)
                .consumerMainThread(AnimationSyncPacket::handle)
                .add();

        INSTANCE.messageBuilder(MovementStatePacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(MovementStatePacket::encode)
                .decoder(MovementStatePacket::decode)
                .consumerMainThread(MovementStatePacket::handle)
                .add();

        INSTANCE.messageBuilder(SceneActionPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SceneActionPacket::encode)
                .decoder(SceneActionPacket::decode)
                .consumerMainThread(SceneActionPacket::handle)
                .add();
    }

    public static void broadcastAnimationSync(SexEntity entity, SexModAnimation animation) {
        INSTANCE.send(new AnimationSyncPacket(entity.getId(), animation.name()), net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY.with(entity));
    }

    public static void sendMovementStateUpdate(int entityId, String state) {
        INSTANCE.send(new MovementStatePacket(entityId, state), net.minecraftforge.network.PacketDistributor.SERVER.noArg());
    }

    public static void sendSceneAction(int entityId, String action) {
        INSTANCE.send(new SceneActionPacket(entityId, action), net.minecraftforge.network.PacketDistributor.SERVER.noArg());
    }
}
