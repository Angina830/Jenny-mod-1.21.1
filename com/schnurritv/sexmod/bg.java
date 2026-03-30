package com.schnurritv.sexmod;

import io.netty.buffer.ByteBuf;
import java.util.HashSet;
import java.util.UUID;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class bg implements IMessage {
  boolean c = false;
  
  HashSet<BlockPos> b = new HashSet<>();
  
  boolean a;
  
  public bg() {}
  
  public bg(HashSet<BlockPos> paramHashSet, boolean paramBoolean) {
    this.b = paramHashSet;
    this.a = paramBoolean;
  }
  
  public bg(BlockPos paramBlockPos, boolean paramBoolean) {
    this.b.add(paramBlockPos);
    this.a = paramBoolean;
  }
  
  public void fromBytes(ByteBuf paramByteBuf) {
    this.a = paramByteBuf.readBoolean();
    int i = paramByteBuf.readInt();
    byte b = 0;
    try {
      while (b < i) {
        this.b.add(new BlockPos(paramByteBuf.readInt(), paramByteBuf.readInt(), paramByteBuf.readInt()));
        b++;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.c = true;
  }
  
  public void toBytes(ByteBuf paramByteBuf) {
    paramByteBuf.writeBoolean(this.a);
    paramByteBuf.writeInt(this.b.size());
    for (BlockPos blockPos : this.b) {
      paramByteBuf.writeInt(blockPos.func_177958_n());
      paramByteBuf.writeInt(blockPos.func_177956_o());
      paramByteBuf.writeInt(blockPos.func_177952_p());
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a implements IMessageHandler<bg, IMessage> {
    public IMessage a(bg param1bg, MessageContext param1MessageContext) {
      try {
        if (!param1bg.c) {
          System.out.println("received an invalid Message @SendBlocks :(");
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (param1MessageContext.side.isClient()) {
          try {
            if (param1bg.a) {
              bX.b(param1bg.b);
            } else {
              bX.a(param1bg.b);
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return null;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
            UUID uUID1 = (param1MessageContext.getServerHandler()).field_147369_b.getPersistentID();
            UUID uUID2 = bF.a(uUID1);
            try {
              if (uUID2 == null)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            try {
              if (param1bg.b.size() != 1)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            World world = (param1MessageContext.getServerHandler()).field_147369_b.field_70170_p;
            for (BlockPos blockPos1 : param1bg.b) {
              IBlockState iBlockState = world.func_180495_p(blockPos1);
              BlockPos blockPos2 = null;
              if (iBlockState.func_177230_c() instanceof net.minecraft.block.BlockBed)
                blockPos2 = bZ.a(blockPos1, iBlockState); 
              if (iBlockState.func_177230_c() instanceof BlockChest) {
                BlockChest.Type type = ((BlockChest)iBlockState.func_177230_c()).field_149956_a;
                try {
                  if (world.func_180495_p(blockPos1.func_177978_c()).func_177230_c() instanceof BlockChest && type.equals(((BlockChest)world.func_180495_p(blockPos1.func_177978_c()).func_177230_c()).field_149956_a))
                    blockPos2 = blockPos1.func_177978_c(); 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  if (world.func_180495_p(blockPos1.func_177974_f()).func_177230_c() instanceof BlockChest && type.equals(((BlockChest)world.func_180495_p(blockPos1.func_177974_f()).func_177230_c()).field_149956_a))
                    blockPos2 = blockPos1.func_177974_f(); 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  if (world.func_180495_p(blockPos1.func_177968_d()).func_177230_c() instanceof BlockChest && type.equals(((BlockChest)world.func_180495_p(blockPos1.func_177968_d()).func_177230_c()).field_149956_a))
                    blockPos2 = blockPos1.func_177968_d(); 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  if (world.func_180495_p(blockPos1.func_177976_e()).func_177230_c() instanceof BlockChest && type.equals(((BlockChest)world.func_180495_p(blockPos1.func_177976_e()).func_177230_c()).field_149956_a))
                    blockPos2 = blockPos1.func_177976_e(); 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
              } 
              try {
                if (blockPos2 == null)
                  try {
                    if (iBlockState.func_177230_c() instanceof net.minecraft.block.BlockBed)
                      return; 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  }  
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (param1bg.a) {
                  try {
                    if (iBlockState.func_177230_c() instanceof net.minecraft.block.BlockBed) {
                      bF.b(uUID2, blockPos1);
                      bF.b(uUID2, blockPos2);
                    } else {
                      bF.a(uUID2, blockPos1);
                      bF.a(uUID2, blockPos2);
                    } 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                } else {
                  try {
                    if (iBlockState.func_177230_c() instanceof net.minecraft.block.BlockBed) {
                      bF.f(uUID2, blockPos1);
                      bF.f(uUID2, blockPos2);
                    } else {
                      bF.d(uUID2, blockPos1);
                      bF.d(uUID2, blockPos2);
                    } 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              HashSet<BlockPos> hashSet = new HashSet();
              try {
                hashSet.add(blockPos1);
                if (blockPos2 != null)
                  hashSet.add(blockPos2); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              bn.a.sendTo(new bg(hashSet, param1bg.a), (param1MessageContext.getServerHandler()).field_147369_b);
            } 
          });
      return null;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */