package com.schnurritv.sexmod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ce extends cr {
  public ce(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  protected void a(boolean paramBoolean, ItemStack paramItemStack) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    GlStateManager.func_179114_b(paramBoolean ? 290.0F : 90.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void b() {
    GlStateManager.func_179109_b(0.0F, -0.6F, 0.0F);
    GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
  }
  
  protected void a(boolean paramBoolean1, boolean paramBoolean2) {
    try {
      if (paramBoolean1) {
        try {
          GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179109_b(0.0F, -0.14F, -0.17F);
          if (paramBoolean2) {
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179137_b(0.067D, 0.0D, 0.0D);
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        try {
          if (paramBoolean2) {
            GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179109_b(0.0F, 0.165F, 0.0F);
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */