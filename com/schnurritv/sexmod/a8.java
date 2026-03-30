package com.schnurritv.sexmod;

import com.google.common.base.Optional;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class a8 implements IMessage {
  boolean a = false;
  
  int b;
  
  public a8() {}
  
  public a8(int paramInt) {
    this.b = paramInt;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = paramByteBuf.readInt();
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeInt(this.b);
  }
  
  public static class a implements IMessageHandler<a8, IMessage> {
    public IMessage a(a8 param1a8, MessageContext param1MessageContext) {
      try {
        if (param1a8.a)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    aP aP;
                    X x;
                    a2 a2;
                    W w;
                    V v;
                    ar ar;
                    EntityPlayerMP entityPlayerMP = (param1MessageContext.getServerHandler()).field_147369_b;
                    World world = ((EntityPlayer)entityPlayerMP).field_70170_p;
                    UUID uUID = (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID();
                    U u = U.b(uUID);
                    if (u != null) {
                      try {
                        for (Q q : Q.f()) {
                          try {
                            if (!q.field_70170_p.field_72995_K)
                              try {
                                if (q.p().equals(u.p()))
                                  world.func_72900_e((Entity)q); 
                              } catch (ConcurrentModificationException concurrentModificationException) {
                                throw a(null);
                              }  
                          } catch (ConcurrentModificationException concurrentModificationException) {
                            throw a(null);
                          } 
                        } 
                      } catch (ConcurrentModificationException concurrentModificationException) {}
                      u.p();
                      U.V.remove(uUID);
                      Q.f().remove(u);
                      u.a(Optional.absent());
                    } 
                    ac ac = null;
                    switch (param1a8.b) {
                      case 0:
                        ac = new ac(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 1:
                        aP = new aP(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 2:
                        x = new X(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 3:
                        a2 = new a2(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 4:
                        w = new W(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 5:
                        v = new V(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                      case 6:
                        ar = new ar(world, (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID());
                        break;
                    } 
                    try {
                      if (ar != null) {
                        ar.func_189654_d(true);
                        ar.field_70145_X = true;
                        ar.field_70159_w = 0.0D;
                        ar.field_70181_x = 0.0D;
                        ar.field_70179_y = 0.0D;
                        ar.func_70107_b(((EntityPlayer)entityPlayerMP).field_70165_t, ((EntityPlayer)entityPlayerMP).field_70163_u + 69.0D, ((EntityPlayer)entityPlayerMP).field_70161_v);
                        world.func_72838_d((Entity)ar);
                        ar.x();
                      } 
                    } catch (ConcurrentModificationException concurrentModificationException) {
                      throw a(null);
                    } 
                  });
              return null;
            } 
            System.out.println("received an invalid message @UpdatePlayerModel :(");
            return null;
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @UpdatePlayerModel :(");
      return null;
    }
    
    private static ConcurrentModificationException a(ConcurrentModificationException param1ConcurrentModificationException) {
      return param1ConcurrentModificationException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */