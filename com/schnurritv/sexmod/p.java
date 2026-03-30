package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class p implements IMessage {
  boolean b;
  
  UUID a;
  
  UUID c;
  
  public p() {
    this.b = false;
  }
  
  public p(UUID paramUUID1, UUID paramUUID2) {
    this.a = paramUUID1;
    this.c = paramUUID2;
    this.b = true;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.b = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.a.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c.toString());
  }
  
  public static class a implements IMessageHandler<p, IMessage> {
    public IMessage a(p param1p, MessageContext param1MessageContext) {
      try {
        if (param1p.b)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    ArrayList<Q> arrayList = Q.a(param1p.a);
                    for (Q q : arrayList) {
                      PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al();
                      try {
                        playerList.func_177451_a(param1p.c).func_70005_c_();
                      } catch (NullPointerException nullPointerException) {
                        System.out.println("couldn't find player with UUID: " + param1p.c);
                        System.out.println("could only find players with thsese UUID's:");
                        for (EntityPlayerMP entityPlayerMP : playerList.func_181057_v())
                          System.out.println(entityPlayerMP.func_70005_c_() + " " + entityPlayerMP.func_110124_au()); 
                        continue;
                      } 
                      try {
                        if (q instanceof aX)
                          ((aX)q).W = true; 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      q.d(param1p.c);
                    } 
                  });
              return null;
            } 
            System.out.println("received an invalid message @SetPlayerForGirl :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @SetPlayerForGirl :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\p.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */