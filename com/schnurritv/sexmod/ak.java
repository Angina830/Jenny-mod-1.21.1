package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ak implements IMessage {
  Boolean a = Boolean.valueOf(false);
  
  BlockPos b;
  
  public ak() {}
  
  public ak(BlockPos paramBlockPos) {
    this.b = paramBlockPos;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.b = new BlockPos(paramByteBuf.readInt(), paramByteBuf.readInt(), paramByteBuf.readInt());
    this.a = Boolean.valueOf(true);
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeInt(this.b.func_177958_n());
    paramByteBuf.writeInt(this.b.func_177956_o());
    paramByteBuf.writeInt(this.b.func_177952_p());
  }
  
  public static class a implements IMessageHandler<ak, IMessage> {
    public IMessage a(ak param1ak, MessageContext param1MessageContext) {
      try {
        if (param1ak.a.booleanValue())
          try {
            if (param1MessageContext.side.equals(Side.SERVER)) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    EntityPlayerMP entityPlayerMP = (param1MessageContext.getServerHandler()).field_147369_b;
                    UUID uUID = bF.a(entityPlayerMP.getPersistentID());
                    try {
                      if (uUID == null) {
                        System.out.println("not tribe for player");
                        return;
                      } 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    int i = bF.c(uUID);
                    int j = (int)Math.floor(bF.o(uUID).size() / 2.0D);
                    try {
                      if (i > j) {
                        entityPlayerMP.func_145747_a((ITextComponent)new TextComponentString(String.format("Ur Tribe will only work for you, if %severyone%s of them has a %sbed", new Object[] { TextFormatting.RED, TextFormatting.WHITE, TextFormatting.RED })));
                        entityPlayerMP.func_145747_a((ITextComponent)new TextComponentString(String.format("%s%d/%d Beds", new Object[] { TextFormatting.YELLOW, Integer.valueOf(j), Integer.valueOf(i) })));
                        return;
                      } 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    World world = ((EntityPlayer)entityPlayerMP).field_70170_p;
                    BlockPos blockPos = a(world, param1ak.b);
                    HashSet<BlockPos> hashSet = ay.a(world, blockPos, uUID);
                    bn.a.sendTo(new bg(hashSet, true), (param1MessageContext.getServerHandler()).field_147369_b);
                  });
              return null;
            } 
            System.out.println("received an invalid Message @FallTree :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid Message @FallTree :(");
      return null;
    }
    
    BlockPos a(World param1World, BlockPos param1BlockPos) {
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(0, -1, 0)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(1, -1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(1, -1, 0)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(-1, -1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(-1, -1, 0)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(0, -1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(0, -1, 1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(0, -1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(0, -1, -1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(-1, -1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(-1, -1, -1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(1, -1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(1, -1, 1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(-1, -1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(-1, -1, 1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1World.func_180495_p(param1BlockPos.func_177982_a(1, -1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
          return a(param1World, param1BlockPos.func_177982_a(1, -1, -1)); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      return param1BlockPos;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */