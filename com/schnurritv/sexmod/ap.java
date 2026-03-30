package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ap implements IMessage {
  boolean a;
  
  Vec3d b;
  
  public ap() {}
  
  public ap(Vec3d paramVec3d) {
    this.b = paramVec3d;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = new Vec3d(paramByteBuf.readDouble(), paramByteBuf.readDouble(), paramByteBuf.readDouble());
    this.a = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeDouble(this.b.field_72450_a);
    paramByteBuf.writeDouble(this.b.field_72448_b);
    paramByteBuf.writeDouble(this.b.field_72449_c);
  }
  
  public static class a implements IMessageHandler<ap, IMessage> {
    public IMessage a(ap param1ap, MessageContext param1MessageContext) {
      try {
        if (param1ap.a)
          try {
            if (param1MessageContext.side == Side.SERVER) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    World world = (param1MessageContext.getServerHandler()).field_147369_b.field_70170_p;
                    EntityItem entityItem1 = new EntityItem(world, param1ap.b.field_72450_a, param1ap.b.field_72448_b, param1ap.b.field_72449_c, new ItemStack(Items.field_151045_i, bY.b.nextInt(2) + 1));
                    EntityItem entityItem2 = new EntityItem(world, param1ap.b.field_72450_a, param1ap.b.field_72448_b, param1ap.b.field_72449_c, new ItemStack(Items.field_151166_bC, bY.b.nextInt(2) + 1));
                    EntityItem entityItem3 = new EntityItem(world, param1ap.b.field_72450_a, param1ap.b.field_72448_b, param1ap.b.field_72449_c, new ItemStack(Items.field_151043_k, bY.b.nextInt(2) + 1));
                    world.func_72838_d((Entity)entityItem1);
                    world.func_72838_d((Entity)entityItem2);
                    world.func_72838_d((Entity)entityItem3);
                  });
              return null;
            } 
            System.out.println("received an invalid message @MakeRichWish :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid message @MakeRichWish :(");
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */