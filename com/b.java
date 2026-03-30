package com;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.EnumPacketDirection;

public class b extends NetHandlerPlayClient {
  public b(Minecraft paramMinecraft) {
    super(paramMinecraft, paramMinecraft.field_71462_r, new a(EnumPacketDirection.CLIENTBOUND), paramMinecraft.func_110432_I().func_148256_e());
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */