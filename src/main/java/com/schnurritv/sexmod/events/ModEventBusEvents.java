package com.schnurritv.sexmod.events;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.EntityRegistry;
import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.client.renderer.GirlRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.JENNY.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.ELLIE.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.SLIME.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.BEE.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.BIA.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.ALLIE.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.GOBLIN.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.KOBOLD.get(), SexEntity.createAttributes().build());
        event.put(EntityRegistry.CAT.get(), SexEntity.createAttributes().build());
    }

    @Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.JENNY.get(), ctx -> new GirlRenderer<>(ctx, "jenny"));
            event.registerEntityRenderer(EntityRegistry.ELLIE.get(), ctx -> new GirlRenderer<>(ctx, "ellie"));
            event.registerEntityRenderer(EntityRegistry.SLIME.get(), ctx -> new GirlRenderer<>(ctx, "slime"));
            event.registerEntityRenderer(EntityRegistry.BEE.get(), ctx -> new GirlRenderer<>(ctx, "bee"));
            event.registerEntityRenderer(EntityRegistry.BIA.get(), ctx -> new GirlRenderer<>(ctx, "bia"));
            event.registerEntityRenderer(EntityRegistry.ALLIE.get(), ctx -> new GirlRenderer<>(ctx, "allie"));
            event.registerEntityRenderer(EntityRegistry.GOBLIN.get(), ctx -> new GirlRenderer<>(ctx, "goblin"));
            event.registerEntityRenderer(EntityRegistry.KOBOLD.get(), ctx -> new GirlRenderer<>(ctx, "kobold"));
            event.registerEntityRenderer(EntityRegistry.CAT.get(), ctx -> new GirlRenderer<>(ctx, "cat"));
        }
    }
}
