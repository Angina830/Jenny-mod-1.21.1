package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ay {
  public static final int c = 30;
  
  BlockPos b;
  
  a e;
  
  HashSet<BlockPos> f;
  
  List<aD> d = new ArrayList<>();
  
  EnumFacing a = EnumFacing.NORTH;
  
  public ay(BlockPos paramBlockPos, a parama, HashSet<BlockPos> paramHashSet) {
    this.b = paramBlockPos;
    this.e = parama;
    this.f = paramHashSet;
  }
  
  public ay(BlockPos paramBlockPos, a parama, HashSet<BlockPos> paramHashSet, EnumFacing paramEnumFacing) {
    this.b = paramBlockPos;
    this.e = parama;
    this.f = paramHashSet;
    this.a = paramEnumFacing;
  }
  
  public EnumFacing d() {
    return this.a;
  }
  
  public BlockPos f() {
    return this.b;
  }
  
  public a b() {
    return this.e;
  }
  
  public HashSet<BlockPos> e() {
    return this.f;
  }
  
  public void c(BlockPos paramBlockPos) {
    this.f.add(paramBlockPos);
  }
  
  public void a(HashSet<BlockPos> paramHashSet) {
    this.f.addAll(paramHashSet);
  }
  
  public void a(BlockPos paramBlockPos) {
    this.f.remove(paramBlockPos);
  }
  
  public void b(HashSet<BlockPos> paramHashSet) {
    try {
      if (!paramHashSet.isEmpty())
        this.f.removeAll(paramHashSet); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public boolean b(BlockPos paramBlockPos) {
    return this.f.contains(paramBlockPos);
  }
  
  public boolean a(aD paramaD) {
    try {
      if (this.e.a <= this.d.size())
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.d.add(paramaD);
    return true;
  }
  
  public List<aD> c() {
    return this.d;
  }
  
  public void a() {
    for (aD aD : this.d) {
      try {
        if (aD.B() == null) {
          aD.func_189654_d(false);
          aD.field_70145_X = false;
          aD.b(b1.NULL);
          aD.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(false));
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    this.d.clear();
  }
  
  public void b(aD paramaD) {
    this.d.remove(paramaD);
  }
  
  public boolean g() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (this.e.a <= this.d.size());
  }
  
  public boolean c(aD paramaD) {
    return this.d.contains(paramaD);
  }
  
  public static HashSet<BlockPos> a(World paramWorld, BlockPos paramBlockPos, UUID paramUUID) {
    BlockPos blockPos1;
    for (blockPos1 = paramBlockPos; !b(paramWorld, blockPos1); blockPos1 = paramBlockPos.func_177977_b());
    BlockPos blockPos2;
    for (blockPos2 = paramBlockPos; !c(paramWorld, blockPos2); blockPos2 = blockPos2.func_177984_a());
    HashSet<BlockPos> hashSet1 = new HashSet();
    int i = blockPos2.func_177956_o() - blockPos1.func_177956_o();
    byte b = 0;
    try {
      while (b <= i) {
        hashSet1.add(blockPos1.func_177982_a(0, b, 0));
        b++;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    HashSet<BlockPos> hashSet2 = a(paramWorld, blockPos1);
    HashSet<BlockPos> hashSet3 = new HashSet();
    for (BlockPos blockPos : hashSet2) {
      try {
        if (blockPos.func_177958_n() == blockPos1.func_177958_n())
          try {
            if (blockPos.func_177952_p() == blockPos1.func_177952_p())
              hashSet3.add(blockPos); 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    for (BlockPos blockPos : hashSet3)
      hashSet2.remove(blockPos); 
    hashSet1.addAll(hashSet2);
    HashSet<BlockPos> hashSet4 = new HashSet();
    for (BlockPos blockPos : hashSet1) {
      for (ay ay2 : bF.m(paramUUID)) {
        HashSet<BlockPos> hashSet = ay2.e();
        try {
          if (hashSet.contains(blockPos)) {
            hashSet4.add(blockPos);
            break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } 
    hashSet1.removeAll(hashSet4);
    ay ay1 = new ay(blockPos1, a.FALL_TREE, hashSet1);
    bF.b(paramUUID, ay1);
    return hashSet1;
  }
  
  static boolean c(World paramWorld, BlockPos paramBlockPos) {
    Block block = paramWorld.func_180495_p(paramBlockPos.func_177984_a()).func_177230_c();
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return !(block instanceof net.minecraft.block.BlockLog);
  }
  
  static boolean b(World paramWorld, BlockPos paramBlockPos) {
    IBlockState iBlockState = paramWorld.func_180495_p(paramBlockPos.func_177977_b());
    try {
      if (!(iBlockState instanceof net.minecraft.block.BlockLog))
        try {
          if (iBlockState.func_185904_a() != Material.field_151579_a);
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return false;
  }
  
  static HashSet<BlockPos> a(World paramWorld, BlockPos paramBlockPos) {
    return a(paramWorld, paramBlockPos, new HashSet<>());
  }
  
  static HashSet<BlockPos> a(World paramWorld, BlockPos paramBlockPos, HashSet<BlockPos> paramHashSet) {
    try {
      if (paramHashSet.contains(paramBlockPos))
        return new HashSet<>(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      paramHashSet.add(paramBlockPos);
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 0, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 0, 0), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 0, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 0, 0), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(0, 0, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(0, 0, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(0, 0, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(0, 0, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 0, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 0, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 0, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 0, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 0, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 0, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 0, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 0, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(0, 1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(0, 1, 0), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 1, 0), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 1, 0)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 1, 0), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(0, 1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(0, 1, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(0, 1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(0, 1, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 1, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 1, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(-1, 1, 1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(-1, 1, 1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_180495_p(paramBlockPos.func_177982_a(1, 1, -1)).func_177230_c() instanceof net.minecraft.block.BlockLog)
        paramHashSet.addAll(a(paramWorld, paramBlockPos.func_177982_a(1, 1, -1), paramHashSet)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return paramHashSet;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public enum a {
    FALL_TREE(1),
    MINE(3);
    
    int a;
    
    a(int param1Int1) {
      this.a = param1Int1;
    }
    
    int a() {
      return this.a;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */