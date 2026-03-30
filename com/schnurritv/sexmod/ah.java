package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ah implements IMessage {
  boolean b = false;
  
  UUID c;
  
  ItemStack a;
  
  public ah() {}
  
  public ah(UUID paramUUID, ItemStack paramItemStack) {
    this.c = paramUUID;
    this.a = paramItemStack;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.c = UUID.fromString(ByteBufUtils.readUTF8String(paramByteBuf));
    this.a = ByteBufUtils.readItemStack(paramByteBuf);
    this.b = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    ByteBufUtils.writeUTF8String(paramByteBuf, this.c.toString());
    ByteBufUtils.writeItemStack(paramByteBuf, this.a);
  }
  
  public static class a implements IMessageHandler<ah, IMessage> {
    public IMessage a(ah param1ah, MessageContext param1MessageContext) {
      try {
        if (param1ah.b)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    InventoryPlayer inventoryPlayer = (FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_177451_a(param1ah.c)).field_71071_by;
                    for (byte b = 0; b < inventoryPlayer.func_70302_i_(); b++) {
                      ItemStack itemStack = inventoryPlayer.func_70301_a(b);
                      try {
                        if (itemStack.func_77973_b().equals(param1ah.a.func_77973_b())) {
                          itemStack.func_190918_g(param1ah.a.func_190916_E());
                          break;
                        } 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                    } 
                  });
              return null;
            } 
            System.out.println("recieved an unvalid message @RemoveItems :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("recieved an unvalid message @RemoveItems :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ah.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */