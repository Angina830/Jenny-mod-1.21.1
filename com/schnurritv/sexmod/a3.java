package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class a3 implements IMessage {
  boolean a = false;
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {}
  
  public static class a implements IMessageHandler<a3, IMessage> {
    public IMessage a(a3 param1a3, MessageContext param1MessageContext) {
      try {
        if (param1a3.a)
          try {
            if (param1MessageContext.side == Side.CLIENT) {
              a();
              return null;
            } 
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void a() {
      Minecraft minecraft = Minecraft.func_71410_x();
      minecraft.func_152344_a(() -> param1Minecraft.func_147108_a(new v()));
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */