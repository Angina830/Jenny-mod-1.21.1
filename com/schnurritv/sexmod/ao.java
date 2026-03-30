package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ao implements IMessage {
  boolean a = false;
  
  ItemStack[] d;
  
  UUID b;
  
  UUID c;
  
  public ao() {}
  
  public ao(UUID paramUUID1, UUID paramUUID2, ItemStack[] paramArrayOfItemStack) {
    this.b = paramUUID1;
    this.d = paramArrayOfItemStack;
    this.c = paramUUID2;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    int i = paramByteBuf.readInt();
    this.d = new ItemStack[i];
    byte b = 0;
    try {
      while (b < i) {
        this.d[b] = ByteBufUtils.readItemStack(paramByteBuf);
        b++;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c.toString());
    paramByteBuf.writeInt(this.d.length);
    for (ItemStack itemStack : this.d)
      ByteBufUtils.writeItemStack(paramByteBuf, itemStack); 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a implements IMessageHandler<ao, IMessage> {
    public IMessage a(ao param1ao, MessageContext param1MessageContext) {
      try {
        if (param1ao.a)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    ArrayList<Q> arrayList = Q.a(param1ao.b);
                    for (Q q : arrayList) {
                      try {
                        if (q.field_70170_p.field_72995_K)
                          continue; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      EntityPlayer entityPlayer = q.field_70170_p.func_152378_a(param1ao.c);
                      try {
                        if (entityPlayer == null)
                          return; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      InventoryPlayer inventoryPlayer = entityPlayer.field_71071_by;
                      byte b = 0;
                      try {
                        while (b < 36) {
                          inventoryPlayer.func_70299_a(b, param1ao.d[b]);
                          b++;
                        } 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      if (q instanceof aI) {
                        S s = (S)q;
                        s.H.setStackInSlot(0, param1ao.d[36]);
                        s.H.setStackInSlot(1, param1ao.d[37]);
                        s.H.setStackInSlot(2, param1ao.d[38]);
                        s.H.setStackInSlot(3, param1ao.d[39]);
                        s.H.setStackInSlot(4, param1ao.d[40]);
                        s.H.setStackInSlot(5, param1ao.d[41]);
                        s.H.setStackInSlot(6, param1ao.d[42]);
                      } else if (q instanceof S) {
                        S s = (S)q;
                        s.H.setStackInSlot(0, param1ao.d[36]);
                        s.H.setStackInSlot(1, param1ao.d[37]);
                        s.H.setStackInSlot(2, param1ao.d[38]);
                        s.H.setStackInSlot(3, param1ao.d[39]);
                        s.H.setStackInSlot(4, param1ao.d[40]);
                        s.H.setStackInSlot(5, param1ao.d[41]);
                      } 
                      if (q instanceof a1) {
                        a1 a1 = (a1)q;
                        byte b1 = 0;
                        try {
                          while (b1 < 27) {
                            a1.G.setStackInSlot(b1, param1ao.d[b1 + 36]);
                            b1++;
                          } 
                        } catch (NullPointerException nullPointerException) {
                          throw a(null);
                        } 
                      } 
                    } 
                  });
              return null;
            } 
            System.out.println("received an invalid message @UploadInventoryToServer :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @UploadInventoryToServer :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */