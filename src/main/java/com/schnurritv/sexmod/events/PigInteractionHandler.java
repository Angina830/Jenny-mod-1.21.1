package com.schnurritv.sexmod.events;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.EntityRegistry;
import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.entity.pig.PigProxyEntity;
import com.schnurritv.sexmod.networking.NetworkHandler;
import com.schnurritv.sexmod.networking.OpenPigInteractionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Pig;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PigInteractionHandler {

    @SubscribeEvent
    public static void onAnimalInteract(PlayerInteractEvent.EntityInteract event) {
        net.minecraft.world.entity.Entity target = event.getTarget();
        String type = "";
        if (target instanceof net.minecraft.world.entity.animal.Pig) type = "pig";
        else if (target instanceof net.minecraft.world.entity.animal.Chicken) type = "chicken";
        else if (target instanceof net.minecraft.world.entity.animal.Sheep) type = "sheep";
        
        if (type.isEmpty()) return;
        if (event.getLevel().isClientSide) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        
        // Spawn hidden proxy entity
        PigProxyEntity proxy = EntityRegistry.PIG_PROXY.get().create(event.getLevel());
        if (proxy == null) return;

        proxy.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
        proxy.getEntityData().set(SexEntity.MASTER_UUID, player.getUUID().toString());
        proxy.getEntityData().set(PigProxyEntity.ANIMAL_TYPE, type);
        event.getLevel().addFreshEntity(proxy);

        // Tell client to open screen for this proxy
        NetworkHandler.INSTANCE.send(new OpenPigInteractionPacket(proxy.getId()), PacketDistributor.PLAYER.with(player));
        
        event.setCanceled(true);
        event.setCancellationResult(net.minecraft.world.InteractionResult.SUCCESS);
    }
}
