package com.schnurritv.sexmod;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cx extends AnimatedGeoModel<bI> {
  public ResourceLocation c(bI parambI) {
    try {
      if (parambI.d)
        return new ResourceLocation("sexmod", "geo/cross.geo.json"); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b_.a(parambI.a());
  }
  
  public ResourceLocation b(bI parambI) {
    try {
      if (parambI.d)
        return new ResourceLocation("sexmod", "textures/cross.png"); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b_.b(parambI.a());
  }
  
  public ResourceLocation a(bI parambI) {
    return new ResourceLocation("sexmod", "animations/slime/slime.animation.json");
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */