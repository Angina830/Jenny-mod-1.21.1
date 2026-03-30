package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import java.util.UUID;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class t implements IMessage {
  boolean c = false;
  
  BlockPos a;
  
  EnumFacing b;
  
  public t() {}
  
  public t(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
    this.a = paramBlockPos;
    this.b = paramEnumFacing;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = new BlockPos(paramByteBuf.readInt(), paramByteBuf.readInt(), paramByteBuf.readInt());
    this.b = EnumFacing.func_176739_a(ByteBufUtils.readUTF8String(paramByteBuf));
    this.c = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeInt(this.a.func_177958_n());
    paramByteBuf.writeInt(this.a.func_177956_o());
    paramByteBuf.writeInt(this.a.func_177952_p());
    ByteBufUtils.writeUTF8String(paramByteBuf, this.b.func_176610_l());
  }
  
  public static class a implements IMessageHandler<t, IMessage> {
    public IMessage a(t param1t, MessageContext param1MessageContext) {
      try {
        if (param1t.c)
          try {
            if (param1MessageContext.side.equals(Side.SERVER)) {
              FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                    EntityPlayerMP entityPlayerMP = (param1MessageContext.getServerHandler()).field_147369_b;
                    UUID uUID = bF.a(entityPlayerMP.getPersistentID());
                    try {
                      if (uUID == null)
                        return; 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    int i = bF.c(uUID);
                    int j = (int)Math.floor(bF.o(uUID).size() / 2.0D);
                    try {
                      if (i > j) {
                        entityPlayerMP.func_145747_a((ITextComponent)new TextComponentString(String.format("sUr Tribe will only work for you, if %severyone%s of them has a %sbed", new Object[] { TextFormatting.RED, TextFormatting.WHITE, TextFormatting.RED })));
                        entityPlayerMP.func_145747_a((ITextComponent)new TextComponentString(String.format("%s%d/%d Beds", new Object[] { TextFormatting.YELLOW, Integer.valueOf(j), Integer.valueOf(i) })));
                        return;
                      } 
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
                    } 
                    HashSet<BlockPos> hashSet = a(param1t.a, param1t.b);
                    World world = (param1MessageContext.getServerHandler()).field_147369_b.field_70170_p;
                    for (BlockPos blockPos : hashSet) {
                      IBlockState iBlockState = world.func_180495_p(blockPos);
                      try {
                        if (iBlockState.func_177230_c().func_176195_g(iBlockState, world, blockPos) < 0.0F) {
                          entityPlayerMP.func_146105_b((ITextComponent)new TextComponentString("This area contains Bedrock and cannot be mined"), true);
                          return;
                        } 
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                    } 
                    ay ay = new ay(param1t.a, ay.a.MINE, hashSet, param1t.b);
                    bF.b(uUID, ay);
                    bn.a.sendTo(new bg(hashSet, true), (param1MessageContext.getServerHandler()).field_147369_b);
                  });
              return null;
            } 
            System.out.println("received an invalid Message @Mine :(");
            return null;
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      System.out.println("received an invalid Message @Mine :(");
      return null;
    }
    
    HashSet<BlockPos> a(BlockPos param1BlockPos, EnumFacing param1EnumFacing) {
      HashSet<BlockPos> hashSet = new HashSet();
      BlockPos blockPos = param1BlockPos;
      for (byte b = 0; b < 30; b++) {
        hashSet.add(blockPos.func_177973_b((Vec3i)a(param1EnumFacing)));
        hashSet.add(blockPos.func_177973_b((Vec3i)a(param1EnumFacing)).func_177984_a());
        hashSet.add(blockPos.func_177973_b((Vec3i)a(param1EnumFacing)).func_177984_a().func_177984_a());
        hashSet.add(blockPos);
        hashSet.add(blockPos.func_177984_a());
        hashSet.add(blockPos.func_177984_a().func_177984_a());
        hashSet.add(blockPos.func_177971_a((Vec3i)a(param1EnumFacing)));
        hashSet.add(blockPos.func_177971_a((Vec3i)a(param1EnumFacing)).func_177984_a());
        hashSet.add(blockPos.func_177971_a((Vec3i)a(param1EnumFacing)).func_177984_a().func_177984_a());
        blockPos = blockPos.func_177971_a(param1EnumFacing.func_176730_m());
      } 
      return hashSet;
    }
    
    BlockPos a(EnumFacing param1EnumFacing) {
      Vec3i vec3i = param1EnumFacing.func_176730_m();
      return new BlockPos(vec3i.func_177952_p(), vec3i.func_177956_o(), -vec3i.func_177958_n());
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\t.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */