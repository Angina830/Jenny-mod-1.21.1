package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class b9 implements IWorldGenerator {
  public static final int b = 6;
  
  static final double c = 0.004000000189989805D;
  
  public static boolean a = true;
  
  public void generate(Random paramRandom, int paramInt1, int paramInt2, World paramWorld, IChunkGenerator paramIChunkGenerator, IChunkProvider paramIChunkProvider) {
    try {
      if (!a)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramWorld.func_175624_G() == WorldType.field_77138_c)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      switch (paramWorld.field_73011_w.getDimension()) {
        case 0:
          a(new au("jenny"), paramWorld, paramRandom, paramInt1, paramInt2, new HashSet<>(Arrays.asList(new Biome[] { Biomes.field_76772_c, Biomes.field_76767_f }, )), new Vec3i(9, 4, 9), 1, true, true, 120);
          a(new au("ellie"), paramWorld, paramRandom, paramInt1, paramInt2, new HashSet<>(Arrays.asList(new Biome[] { Biomes.field_150578_U, Biomes.field_150584_S, Biomes.field_76768_g, Biomes.field_150585_R }, )), new Vec3i(30, 27, 26), 9, true, false, 150);
          a(new aE("bia"), paramWorld, paramRandom, paramInt1, paramInt2, new HashSet<>(Arrays.asList(new Biome[] { Biomes.field_150583_P, Biomes.field_185448_Z }, )), new Vec3i(11, 9, 15), 2, true, true, 4);
          a(new au("luna"), paramWorld, paramRandom, paramInt1, paramInt2, new HashSet<>(Arrays.asList(new Biome[] { Biomes.field_76771_b, Biomes.field_150575_M }, )), new Vec3i(3, 7, 10), 0, false, false, 350);
          a(paramWorld, paramRandom, paramInt1, paramInt2);
          b(paramWorld, paramRandom, paramInt1, paramInt2);
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
    int i = 16 * paramInt1 + 3;
    int j = 16 * paramInt2 + 3;
    int k = paramRandom.nextInt(255);
    BlockPos blockPos = new BlockPos(i, k, j);
    ArrayList arrayList = new ArrayList();
    for (byte b = 0; b <= ag.K.func_177958_n(); b++) {
      for (byte b1 = -1; b1 <= ag.K.func_177956_o(); b1++) {
        byte b2 = 0;
        while (true)
          b2++; 
        continue;
      } 
    } 
    try {
      if (arrayList.size() != 0)
        try {
          if (arrayList.size() <= 4) {
            Rotation rotation;
            BlockPos blockPos1 = null;
            for (BlockPos blockPos2 : arrayList) {
              BlockPos blockPos3 = blockPos2;
              BlockPos blockPos4 = blockPos.func_177982_a(6, 0, 6);
              blockPos3 = blockPos3.func_177973_b((Vec3i)blockPos4);
              try {
                if (Math.abs(blockPos3.func_177958_n()) == Math.abs(blockPos3.func_177952_p()))
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (Math.abs(blockPos3.func_177958_n()) == Math.abs(blockPos3.func_177952_p()) - 1)
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (Math.abs(blockPos3.func_177958_n()) - 1 == Math.abs(blockPos3.func_177952_p()))
                  continue; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              blockPos1 = blockPos3;
            } 
            try {
              if (blockPos1 == null)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            Vec3i vec3i = new Vec3i(0, 0, 0);
            float f = 0.0F;
            if (blockPos1.func_177952_p() == -6) {
              rotation = Rotation.NONE;
              vec3d = ag.V;
              f = 180.0F;
            } else if (blockPos1.func_177958_n() == 5) {
              rotation = Rotation.CLOCKWISE_90;
              vec3d = ag.aP;
              vec3i = new Vec3i(ag.K.func_177958_n() - 1, 0, 0);
              f = -90.0F;
            } else if (blockPos1.func_177952_p() == 5) {
              rotation = Rotation.CLOCKWISE_180;
              vec3d = ag.O;
              vec3i = new Vec3i(ag.K.func_177958_n() - 1, 0, ag.K.func_177952_p() - 1);
            } else {
              rotation = Rotation.COUNTERCLOCKWISE_90;
              vec3d = ag.aU;
              vec3i = new Vec3i(0, 0, ag.K.func_177952_p() - 1);
              f = 90.0F;
            } 
            (new au("goblin")).a(paramWorld, blockPos.func_177982_a(0, -1, 0).func_177971_a(vec3i), rotation);
            vec3d.func_72441_c(vec3i.func_177958_n(), vec3i.func_177956_o(), vec3i.func_177952_p());
            Vec3d vec3d = new Vec3d(blockPos.func_177958_n() + vec3d.field_72450_a + 0.5D, blockPos.func_177956_o() + vec3d.field_72448_b, blockPos.func_177952_p() + vec3d.field_72449_c + 0.5D);
            ag ag = new ag(paramWorld, true, f, vec3d);
            ag.field_98038_p = true;
            paramWorld.func_72838_d((Entity)ag);
            System.out.println("generated @ " + i + " " + k + " " + j);
            paramWorld.func_72964_e(paramInt1, paramInt2).func_76630_e();
            return;
          } 
          return;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
    try {
      if (paramRandom.nextDouble() > 0.004000000189989805D)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    int i = paramInt1 * 16 + 8;
    int j = paramInt2 * 16 + 8;
    int k = bZ.a(paramWorld, i, j);
    try {
      if (paramWorld.func_180495_p(new BlockPos(i, k, j)).func_185904_a().func_76224_d())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    bF.a(paramWorld, new Vec3d(i, k, j));
  }
  
  private void a(WorldGenerator paramWorldGenerator, World paramWorld, Random paramRandom, int paramInt1, int paramInt2, HashSet<Biome> paramHashSet, Vec3i paramVec3i, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, int paramInt4) {
    try {
      if (paramRandom.nextInt(paramInt4) != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    int i = paramInt1 * 16 + (16 - paramVec3i.func_177958_n()) / 2;
    int j = paramInt2 * 16 + (16 - paramVec3i.func_177952_p()) / 2;
    Biome biome = paramWorld.field_73011_w.getBiomeForCoords(new BlockPos(i, 80, j));
    try {
      if (!paramHashSet.contains(biome))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    int k = Integer.MIN_VALUE;
    int m = Integer.MAX_VALUE;
    int n;
    for (n = i; n < i + paramVec3i.func_177958_n(); n++) {
      for (int i2 = j; i2 < j + paramVec3i.func_177952_p(); i2++) {
        int i3 = bZ.a(paramWorld, n, i2);
        try {
          if (paramBoolean2)
            try {
              if (paramWorld.func_180495_p(new BlockPos(n, i3, i2)).func_177230_c() == Blocks.field_150355_j)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        if (i3 > k)
          k = i3; 
        if (i3 < m)
          m = i3; 
      } 
    } 
    try {
      if (k - m > paramInt3)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    n = k;
    try {
      paramWorldGenerator.func_180709_b(paramWorld, paramRandom, new BlockPos(i, n, j));
      if (!paramBoolean1)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    boolean bool = true;
    for (int i1 = n - 1; bool; i1--) {
      bool = false;
      paramVec3i = new Vec3i(paramVec3i.func_177958_n() + 2, 0, paramVec3i.func_177952_p() + 2);
      i--;
      j--;
      for (int i2 = i; i2 < i + paramVec3i.func_177958_n(); i2++) {
        for (int i3 = j; i3 < j + paramVec3i.func_177952_p(); i3++) {
          BlockPos blockPos = new BlockPos(i2, i1, i3);
          IBlockState iBlockState = paramWorld.func_180495_p(blockPos);
          try {
            if (iBlockState.func_177230_c().func_176205_b((IBlockAccess)paramWorld, blockPos)) {
              try {
              
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              iBlockState = paramWorld.func_175678_i(blockPos) ? Blocks.field_150349_c.func_176223_P() : Blocks.field_150346_d.func_176223_P();
              paramWorld.func_175656_a(blockPos, iBlockState);
              bool = true;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
        } 
      } 
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */