package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class bu implements IMessage {
  boolean c = false;
  
  String b;
  
  UUID a;
  
  UUID e;
  
  boolean d;
  
  public bu() {}
  
  public bu(String paramString, UUID paramUUID1, UUID paramUUID2, boolean paramBoolean) {
    this.b = paramString;
    this.a = paramUUID1;
    this.e = paramUUID2;
    this.d = paramBoolean;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = ByteBufUtils.readUTF8String(paramByteBuf);
    this.a = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.e = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.d = paramByteBuf.readBoolean();
    this.c = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b);
    ByteBufUtils.writeUTF8String(paramByteBuf, this.a.toString());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.e.toString());
    paramByteBuf.writeBoolean(this.d);
  }
  
  public static class a implements IMessageHandler<bu, IMessage> {
    public IMessage a(bu param1bu, MessageContext param1MessageContext) {
      try {
        if (!param1bu.c) {
          System.out.println("received an invalid message @SexPrompt :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1MessageContext.side.equals(Side.CLIENT)) {
          b7.b.a(new b7.a(param1bu.b, param1bu.a, param1bu.e, param1bu.d));
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            World world = (param1MessageContext.getServerHandler()).field_147369_b.field_70170_p;
            EntityPlayer entityPlayer1 = world.func_152378_a(param1bu.e);
            EntityPlayer entityPlayer2 = world.func_152378_a(param1bu.a);
            try {
              if (entityPlayer1 == null) {
                System.out.println("Sex prompt invalid -> female player not found");
                return;
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            try {
              if (entityPlayer2 == null) {
                System.out.println("Sex prompt invalid -> male player not found");
                return;
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            try {
            
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            bn.a.sendTo(new bu(param1bu.b, param1bu.a, param1bu.e, param1bu.d), param1bu.d ? (EntityPlayerMP)entityPlayer1 : (EntityPlayerMP)entityPlayer2);
          });
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */