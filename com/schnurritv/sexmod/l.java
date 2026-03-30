package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class l implements IMessage {
  boolean a;
  
  UUID b;
  
  public l() {}
  
  public l(UUID paramUUID) {
    this.b = paramUUID;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.toString());
  }
  
  public static class a implements IMessageHandler<l, IMessage> {
    public IMessage a(l param1l, MessageContext param1MessageContext) {
      try {
        if (param1l.a)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    ArrayList<Q> arrayList = Q.a(param1l.b);
                    for (Q q : arrayList) {
                      try {
                        if (q.field_70170_p.field_72995_K)
                          continue; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      try {
                        if (q.h() != b1.THROW_PEARL) {
                          q.b(b1.THROW_PEARL);
                          q.a((float)Math.atan2(q.field_70161_v - q.a.field_72449_c, q.field_70165_t - q.a.field_72450_a) * 57.29578F + 90.0F);
                          q.a(q.func_174791_d());
                          q.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(true));
                          q.h = null;
                          continue;
                        } 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      if (q.h == null) {
                        float f = (float)q.func_174791_d().func_72438_d(q.a);
                        q.h = new bc(q.field_70170_p, (EntityLivingBase)q);
                        q.h.func_70186_c(q.a.field_72450_a - q.field_70165_t, q.a.field_72448_b - q.field_70163_u, q.a.field_72449_c - q.field_70161_v, Math.min(4.0F, f * 0.1F), 0.0F);
                        q.field_70170_p.func_72838_d((Entity)q.h);
                        continue;
                      } 
                      WorldServer worldServer = (WorldServer)q.field_70170_p;
                      byte b = 0;
                      try {
                        while (b < 32) {
                          worldServer.func_180505_a(EnumParticleTypes.PORTAL, false, q.field_70165_t, q.field_70163_u + bY.b.nextDouble() * 2.0D, q.field_70161_v, 32, 0.2D, 0.2D, 0.2D, bY.b.nextGaussian(), new int[0]);
                          b++;
                        } 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      q.func_70107_b(q.a.field_72450_a, q.a.field_72448_b, q.a.field_72449_c);
                      q.h = null;
                      q.b(b1.NULL);
                      q.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(false));
                      q.n();
                    } 
                  });
              return null;
            } 
            System.out.println("received an invalid message @SendCompanionHome :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @SendCompanionHome :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\l.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */