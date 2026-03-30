package com.schnurritv.sexmod;

import net.minecraft.util.ResourceLocation;

public class av extends aM {
  protected ResourceLocation[] b() {
    return new ResourceLocation[] { new ResourceLocation("sexmod", "geo/allie/allie.geo.json"), new ResourceLocation("sexmod", "geo/allie/armored.geo.json"), new ResourceLocation("sexmod", "geo/allie/allie.geo.json") };
  }
  
  public ResourceLocation b(Q paramQ) {
    try {
      if (paramQ.field_70170_p instanceof com.c)
        return this.b[0]; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Integer)paramQ.func_184212_Q().func_187225_a(Q.v)).intValue() > this.b.length) {
        System.out.println("Girl doesn't have an outfit Nr." + paramQ.func_184212_Q().func_187225_a(Q.v) + " so im just making her nude lol");
        return this.b[0];
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramQ instanceof V)
        return this.b[((Integer)paramQ.func_184212_Q().func_187225_a(Q.v)).intValue()]; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Integer)paramQ.func_184212_Q().func_187225_a(Q.v)).intValue() == 1)
        return this.b[2]; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return this.b[0];
  }
  
  public ResourceLocation c() {
    return new ResourceLocation("sexmod", "textures/entity/allie/allie.png");
  }
  
  public ResourceLocation a() {
    return new ResourceLocation("sexmod", "animations/allie/allie.animation.json");
  }
  
  public String[] b() {
    return new String[] { "armorHelmet" };
  }
  
  public String[] c() {
    return new String[] { "armorShoulderR", "armorShoulderL", "armorChest", "armorBoobs" };
  }
  
  public String[] g() {
    return new String[] { "boobsFlesh", "clothes", "clothesR", "clothesL" };
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\av.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */