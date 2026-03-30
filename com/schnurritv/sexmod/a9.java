package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class a9 implements IMessage {
  boolean b = false;
  
  UUID a;
  
  public a9() {}
  
  public a9(UUID paramUUID) {
    this.a = paramUUID;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.b = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.a.toString());
  }
  
  public static class a implements IMessageHandler<a9, IMessage> {
    public IMessage a(a9 param1a9, MessageContext param1MessageContext) {
      try {
        if (param1a9.b)
          try {
            if (param1MessageContext.side.equals(Side.SERVER)) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    ArrayList<Q> arrayList = Q.a(param1a9.a);
                    for (Q q : arrayList) {
                      try {
                        if (q.field_70170_p.field_72995_K)
                          continue; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      try {
                        if (!(q instanceof aI))
                          continue; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      aI aI = (aI)q;
                      aI.o();
                    } 
                  });
              return null;
            } 
            System.out.println("received an invalid message @CatThrowAwayItem :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @CatThrowAwayItem :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */