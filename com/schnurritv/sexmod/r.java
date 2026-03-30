package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class r implements IMessage {
  boolean c;
  
  UUID a;
  
  NBTTagCompound b;
  
  public r() {}
  
  public r(UUID paramUUID, NBTTagCompound paramNBTTagCompound) {
    this.a = paramUUID;
    this.b = paramNBTTagCompound;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.b = ByteBufUtils.readTag(paramByteBuf);
    this.c = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.a.toString());
    ByteBufUtils.writeTag(paramByteBuf, this.b);
  }
  
  public static class a implements IMessageHandler<r, IMessage> {
    public IMessage a(r param1r, MessageContext param1MessageContext) {
      try {
        if (!param1r.c) {
          System.out.println("received an invalid message @UpdateEquipment :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            ArrayList<Q> arrayList = Q.a(param1r.a);
            for (Q q : arrayList) {
              try {
                if (q instanceof S)
                  ((S)q).H.deserializeNBT(param1r.b); 
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


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\r.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */