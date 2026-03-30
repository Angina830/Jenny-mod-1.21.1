package com.schnurritv.sexmod;

import java.io.IOException;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
  public void preInitRegistries(FMLPreInitializationEvent paramFMLPreInitializationEvent) {
    GameRegistry.registerWorldGenerator(new b9(), 0);
    bb.a();
    o.b();
    L.a();
    w.a();
    z.a();
    aB.a();
    a7.a();
    f.a();
    a0.a();
  }
  
  public void initRegistries(FMLInitializationEvent paramFMLInitializationEvent) throws IOException {
    Main.setConfigs();
    c.a();
    NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new i());
    M.a(false);
    bn.b();
  }
  
  public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) throws IOException {}
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */