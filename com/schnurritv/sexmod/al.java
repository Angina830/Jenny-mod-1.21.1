package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class al implements IMessage {
  boolean a = false;
  
  UUID d;
  
  String c;
  
  int b;
  
  public al() {}
  
  public al(UUID paramUUID, String paramString) {
    this.d = paramUUID;
    this.c = paramString;
    this.b = 1;
  }
  
  public al(UUID paramUUID, String paramString, int paramInt) {
    this.d = paramUUID;
    this.c = paramString;
    this.b = paramInt;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.d = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = ByteBufUtils.readUTF8String(paramByteBuf);
    this.b = paramByteBuf.readInt();
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.d.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c);
    paramByteBuf.writeInt(this.b);
  }
  
  public static class a implements IMessageHandler<al, IMessage> {
    public IMessage a(al param1al, MessageContext param1MessageContext) {
      try {
        if (param1al.a)
          try {
            if (param1MessageContext.side.equals(Side.CLIENT)) {
              ArrayList<Q> arrayList = Q.a(param1al.d);
              for (Q q : arrayList) {
                try {
                  if (!q.field_70170_p.field_72995_K)
                    continue; 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                byte b = 0;
                try {
                  while (b < param1al.b) {
                    Q.a(EnumParticleTypes.func_186831_a(param1al.c), q);
                    b++;
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
              } 
              return null;
            } 
            System.out.println("received an invalid message @SpawnParticle :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @SpawnParticle :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\al.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */