package com.schnurritv.sexmod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class bw extends bi {
  public bw(RenderManager paramRenderManager, AnimatedGeoModel<T> paramAnimatedGeoModel, double paramDouble) {
    super(paramRenderManager, paramAnimatedGeoModel, paramDouble);
  }
  
  public void a(GeoModel paramGeoModel, Q paramQ, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    R r = (R)paramQ;
    try {
      if (paramQ.h() == b1.NULL)
        try {
          if (!paramQ.y)
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    r.M = (r.M == 1.0F) ? r.M : (r.M - 0.01F);
    paramFloat5 = r.M;
    try {
      GlStateManager.func_179152_a(paramFloat5, paramFloat5, paramFloat5);
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    GlStateManager.func_179109_b(0.0F, (paramFloat5 == 1.0F) ? 0.0F : (3.0F - paramFloat5 * 3.0F), 0.0F);
    super.a(paramGeoModel, paramQ, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  protected void a(double paramDouble1, double paramDouble2, double paramDouble3) {
    try {
      if (this.k.h() == b1.NULL)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Q)this.k).y)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ((this.k.h()).hideNameTag)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ((this.i.func_175598_ae()).field_78734_h == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    func_147906_a((Entity)this.k, this.k.F(), paramDouble1, paramDouble2 + this.k.z(), paramDouble3, 300);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */