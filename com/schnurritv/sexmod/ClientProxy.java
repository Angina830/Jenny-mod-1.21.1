package com.schnurritv.sexmod;

import com.c;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
  public static KeyBinding[] keyBindings;
  
  public void preInitRegistries(FMLPreInitializationEvent paramFMLPreInitializationEvent) {
    super.preInitRegistries(paramFMLPreInitializationEvent);
    aH.a();
  }
  
  public void initRegistries(FMLInitializationEvent paramFMLInitializationEvent) throws IOException {
    keyBindings = new KeyBinding[1];
    keyBindings[0] = new KeyBinding("Interact with your goblin", 34, "Sex mod");
    for (KeyBinding keyBinding : keyBindings)
      ClientRegistry.registerKeyBinding(keyBinding); 
    Main.setConfigs();
    c.a();
    NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new i());
    M.a(true);
    bn.b();
    Minecraft minecraft = Minecraft.func_71410_x();
    RenderManager renderManager = minecraft.func_175598_ae();
    c c = new c();
    renderManager.func_188391_a((Entity)new aX((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new ae((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new aF((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new T((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new R((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new T((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new aO((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new aI((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new aD((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    renderManager.func_188391_a((Entity)new ag((World)c), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
    b7.b = new b7();
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */