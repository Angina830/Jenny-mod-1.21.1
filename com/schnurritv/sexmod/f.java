package com.schnurritv.sexmod;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class f extends BlockFire {
  public static final Block a = (Block)new f();
  
  public void func_180650_b(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState, Random paramRandom) {}
  
  public static void a() {
    a.setRegistryName("sexmod", "fire");
    a.func_149663_c("fire");
    MinecraftForge.EVENT_BUS.register(f.class);
  }
  
  @SubscribeEvent
  public static void a(RegistryEvent.Register<Block> paramRegister) {
    paramRegister.getRegistry().register((IForgeRegistryEntry)a);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */