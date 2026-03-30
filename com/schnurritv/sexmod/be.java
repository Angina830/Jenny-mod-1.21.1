package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class be implements IMessage {
  boolean a = false;
  
  boolean b;
  
  public be() {}
  
  public be(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = paramByteBuf.readBoolean();
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeBoolean(this.b);
  }
  
  public static class a implements IMessageHandler<be, IMessage> {
    public IMessage a(be param1be, MessageContext param1MessageContext) {
      try {
        if (param1be.a)
          try {
            if (!param1MessageContext.side.isClient()) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    UUID uUID = bF.a((param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                    try {
                      if (uUID == null)
                        return; 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    bF.a(uUID, param1be.b);
                  });
              return null;
            } 
            System.out.println("received an invalid message @SetTribeFollowMode :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @SetTribeFollowMode :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\be.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */