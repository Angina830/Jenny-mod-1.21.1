package com.schnurritv.sexmod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public class b4 extends EntityAIBase {
  protected EntityLiving c;
  
  protected BlockPos g = BlockPos.field_177992_a;
  
  protected BlockDoor a;
  
  boolean b;
  
  float f;
  
  float d;
  
  int e = 10;
  
  public b4(EntityLiving paramEntityLiving) {
    this.c = paramEntityLiving;
    if (!(paramEntityLiving.func_70661_as() instanceof PathNavigateGround))
      throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal"); 
  }
  
  public boolean func_75250_a() {
    boolean bool = true;
    for (byte b = -3; b < 5; b++) {
      for (byte b1 = -3; b1 < 5; b1++) {
        IBlockState iBlockState = this.c.field_70170_p.func_180495_p(this.c.func_180425_c().func_177982_a(b, 0, b1));
        try {
          if (iBlockState.func_177230_c() instanceof BlockDoor && iBlockState.func_185904_a() == Material.field_151575_d) {
            bool = false;
            break;
          } 
        } catch (IllegalArgumentException illegalArgumentException) {
          throw a(null);
        } 
      } 
      try {
        if (!bool)
          break; 
      } catch (IllegalArgumentException illegalArgumentException) {
        throw a(null);
      } 
    } 
    try {
      if (bool)
        return false; 
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    PathNavigateGround pathNavigateGround = (PathNavigateGround)this.c.func_70661_as();
    Path path = pathNavigateGround.func_75505_d();
    try {
      if (path != null)
        try {
          if (!path.func_75879_b() && pathNavigateGround.func_179686_g()) {
            for (byte b1 = 0; b1 < Math.min(path.func_75873_e() + 2, path.func_75874_d()); b1++) {
              PathPoint pathPoint = path.func_75877_a(b1);
              try {
                this.g = new BlockPos(pathPoint.field_75839_a, pathPoint.field_75837_b + 1, pathPoint.field_75838_c);
                if (this.c.func_70092_e(this.g.func_177958_n(), this.c.field_70163_u, this.g.func_177952_p()) <= 2.25D)
                  try {
                    this.a = a(this.g);
                    if (this.a != null)
                      return true; 
                  } catch (IllegalArgumentException illegalArgumentException) {
                    throw a(null);
                  }  
              } catch (IllegalArgumentException illegalArgumentException) {
                throw a(null);
              } 
            } 
            try {
              this.g = (new BlockPos((Entity)this.c)).func_177984_a();
              this.a = a(this.g);
            } catch (IllegalArgumentException illegalArgumentException) {
              throw a(null);
            } 
            return (this.a != null);
          } 
        } catch (IllegalArgumentException illegalArgumentException) {
          throw a(null);
        }  
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return false;
  }
  
  public boolean func_75253_b() {
    try {
    
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return (this.e >= 0);
  }
  
  public void func_75249_e() {
    this.b = false;
    this.f = (float)((this.g.func_177958_n() + 0.5F) - this.c.field_70165_t);
    this.d = (float)((this.g.func_177952_p() + 0.5F) - this.c.field_70161_v);
    this.a.func_176512_a(this.c.field_70170_p, this.g, true);
  }
  
  public void func_75246_d() {
    float f1 = (float)((this.g.func_177958_n() + 0.5F) - this.c.field_70165_t);
    float f2 = (float)((this.g.func_177952_p() + 0.5F) - this.c.field_70161_v);
    float f3 = this.f * f1 + this.d * f2;
    try {
      if (f3 < 0.0F)
        try {
          if (--this.e <= 0) {
            this.a.func_176512_a(this.c.field_70170_p, this.g, false);
            this.b = true;
          } 
        } catch (IllegalArgumentException illegalArgumentException) {
          throw a(null);
        }  
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
  }
  
  public void func_75251_c() {
    this.e = 10;
  }
  
  private BlockDoor a(BlockPos paramBlockPos) {
    IBlockState iBlockState = this.c.field_70170_p.func_180495_p(paramBlockPos);
    Block block = iBlockState.func_177230_c();
    try {
      if (block instanceof BlockDoor)
        try {
          if (iBlockState.func_185904_a() == Material.field_151575_d);
        } catch (IllegalArgumentException illegalArgumentException) {
          throw a(null);
        }  
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return null;
  }
  
  private static IllegalArgumentException a(IllegalArgumentException paramIllegalArgumentException) {
    return paramIllegalArgumentException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */