package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class c9 implements IMessage {
  boolean a;
  
  UUID b;
  
  Vec3d c;
  
  public c9() {}
  
  public c9(UUID paramUUID, Vec3d paramVec3d) {
    this.b = paramUUID;
    this.c = paramVec3d;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = new Vec3d(paramByteBuf.readDouble(), paramByteBuf.readDouble(), paramByteBuf.readDouble());
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
    paramByteBuf.writeDouble(this.c.field_72450_a);
    paramByteBuf.writeDouble(this.c.field_72448_b);
    paramByteBuf.writeDouble(this.c.field_72449_c);
  }
  
  public static class a implements IMessageHandler<c9, IMessage> {
    public IMessage a(c9 param1c9, MessageContext param1MessageContext) {
      try {
        if (!param1c9.a) {
          System.out.println("received an invalid message @SetNewHome :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            ArrayList<Q> arrayList = Q.a(param1c9.b);
            try {
              if (arrayList.isEmpty())
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            for (Q q : arrayList)
              q.a = new Vec3d(param1c9.c.field_72450_a, Math.floor(param1c9.c.field_72448_b), param1c9.c.field_72449_c); 
          });
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\c9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */