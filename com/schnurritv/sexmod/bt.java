package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class bt implements IMessage {
  boolean a;
  
  UUID b;
  
  UUID c;
  
  public bt() {
    this.a = false;
  }
  
  public bt(UUID paramUUID) {
    this.b = paramUUID;
    this.c = UUID.randomUUID();
    this.a = true;
  }
  
  public bt(UUID paramUUID1, UUID paramUUID2) {
    this.b = paramUUID1;
    this.c = paramUUID2;
    this.a = true;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c.toString());
  }
  
  public static class a implements IMessageHandler<bt, IMessage> {
    public IMessage a(bt param1bt, MessageContext param1MessageContext) {
      try {
        if (!param1bt.a) {
          System.out.println("received an invalid message @ClearAnimationCache :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      ArrayList<Q> arrayList = Q.a(param1bt.b);
      try {
        if (arrayList.isEmpty()) {
          b(param1bt, param1MessageContext);
        } else {
          a(arrayList, param1bt, param1MessageContext);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      return null;
    }
    
    void a(ArrayList<Q> param1ArrayList, bt param1bt, MessageContext param1MessageContext) {
      try {
        if (param1MessageContext.side.isServer()) {
          FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> b(param1ArrayList, param1bt, param1MessageContext));
          return;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (a(param1bt))
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      for (Q q : param1ArrayList) {
        try {
          if (q.d != null)
            q.g(); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    }
    
    @SideOnly(Side.CLIENT)
    boolean a(bt param1bt) {
      return (Minecraft.func_71410_x()).field_71439_g.getPersistentID().equals(param1bt.c);
    }
    
    void b(ArrayList<Q> param1ArrayList, bt param1bt, MessageContext param1MessageContext) {
      for (Q q : param1ArrayList) {
        if (q.field_70170_p != null) {
          try {
            if (q.field_70170_p.field_72995_K)
              continue; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          (q.h()).ticksPlaying = 0;
          bn.a.sendToAllTracking(new bt(param1bt.b, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID()), (Entity)q);
          return;
        } 
      } 
    }
    
    void b(bt param1bt, MessageContext param1MessageContext) {
      U u = U.b(param1bt.b);
      try {
        if (param1MessageContext.side.isServer()) {
          (u.h()).ticksPlaying = 0;
          FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> bn.a.sendToAllAround(new bt(param1bt.b, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID()), new NetworkRegistry.TargetPoint(param1U.field_71093_bK, param1U.field_70165_t, (param1U.j()).field_72448_b, param1U.field_70161_v, 15.0D)));
          return;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (a(param1bt))
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (u.d != null)
          u.g(); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */