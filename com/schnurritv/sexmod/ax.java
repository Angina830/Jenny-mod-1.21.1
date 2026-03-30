package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ax implements IMessage {
  boolean a;
  
  UUID b;
  
  public ax() {
    this.a = false;
  }
  
  public ax(UUID paramUUID) {
    this.b = paramUUID;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
  }
  
  public static class a implements IMessageHandler<ax, IMessage> {
    public IMessage a(ax param1ax, MessageContext param1MessageContext) {
      try {
        if (!param1ax.a) {
          System.out.println("received an invalid message @SendGirlToSex :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            ArrayList<Q> arrayList = Q.a(param1ax.b);
            for (Q q : arrayList) {
              try {
                if (q.field_70170_p.field_72995_K)
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (q instanceof bR)
                  ((bR)q).a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
            } 
          });
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */