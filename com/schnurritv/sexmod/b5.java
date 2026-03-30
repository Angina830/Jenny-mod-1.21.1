package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class b5 implements IMessage {
  boolean a;
  
  boolean b;
  
  public b5(boolean paramBoolean) {
    this.b = paramBoolean;
    this.a = true;
  }
  
  public b5() {
    this.a = false;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = paramByteBuf.readBoolean();
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeBoolean(this.b);
    this.a = true;
  }
  
  public static class a implements IMessageHandler<b5, IMessage> {
    public IMessage a(b5 param1b5, MessageContext param1MessageContext) {
      try {
        if (param1b5.a && param1MessageContext.side == Side.CLIENT) {
          bf.a(param1b5.b);
          try {
            (Minecraft.func_71410_x()).field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
          } catch (Exception exception) {}
          try {
            if (param1b5.b)
              aC.d(); 
          } catch (Exception exception) {
            throw a(null);
          } 
        } else {
          System.out.println("received an invalid message @SetPlayerMovement :(");
        } 
      } catch (Exception exception) {
        throw a(null);
      } 
      return null;
    }
    
    private static Exception a(Exception param1Exception) {
      return param1Exception;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */