package com.schnurritv.sexmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest2;

public class k extends EntityAIWatchClosest2 {
  public boolean a = true;
  
  public k(EntityLiving paramEntityLiving, Class<? extends Entity> paramClass, float paramFloat1, float paramFloat2) {
    super(paramEntityLiving, paramClass, paramFloat1, paramFloat2);
  }
  
  public void func_75246_d() {
    try {
      if (this.a)
        super.func_75246_d(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\k.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */