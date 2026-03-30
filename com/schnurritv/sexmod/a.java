package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class a implements IMessage {
  boolean d;
  
  UUID a;
  
  UUID b;
  
  String c;
  
  public a() {}
  
  public a(UUID paramUUID1, UUID paramUUID2, String paramString) {
    this.a = paramUUID1;
    this.b = paramUUID2;
    this.c = paramString;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = ByteBufUtils.readUTF8String(paramByteBuf);
    this.d = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.a.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c);
  }
  
  public static class b implements IMessageHandler<a, IMessage> {
    public IMessage a(a param1a, MessageContext param1MessageContext) {
      try {
        if (param1a.d)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    U u = U.b(param1a.a);
                    try {
                      if (u == null)
                        return; 
                    } catch (Exception exception) {
                      throw a(null);
                    } 
                    if (!FMLCommonHandler.instance().getMinecraftServerInstance().func_71262_S())
                      try {
                        for (Q q : Q.f()) {
                          try {
                            if (!(q instanceof U))
                              continue; 
                          } catch (Exception exception) {
                            throw a(null);
                          } 
                          u = (U)q;
                          try {
                            if (!u.field_70170_p.field_72995_K)
                              try {
                                if (u.u().equals(param1a.a))
                                  break; 
                              } catch (Exception exception) {
                                throw a(null);
                              }  
                          } catch (Exception exception) {
                            throw a(null);
                          } 
                        } 
                      } catch (Exception exception) {} 
                    u.b(param1a.c, param1a.b);
                  });
              return null;
            } 
            System.out.println("received an invalid message @StartStandingSexAnimation :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @StartStandingSexAnimation :(");
      return null;
    }
    
    private static Exception a(Exception param1Exception) {
      return param1Exception;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */