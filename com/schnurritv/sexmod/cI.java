package com.schnurritv.sexmod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cI extends cr {
  public cI(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  protected void b() {
    GlStateManager.func_179109_b(0.0F, -1.5F, 0.0F);
  }
  
  protected void a(boolean paramBoolean, ItemStack paramItemStack) {
    try {
      super.a(paramBoolean, paramItemStack);
      switch (a.a[paramItemStack.func_77973_b().func_77661_b(paramItemStack).ordinal()]) {
        case 1:
        case 2:
          return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      GlStateManager.func_179114_b(paramBoolean ? 90.0F : 180.0F, 1.0F, 0.0F, 0.0F);
      if (paramBoolean)
        GlStateManager.func_179109_b(0.0F, 0.289F, -0.1F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  protected void a(boolean paramBoolean) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      GlStateManager.func_179114_b(paramBoolean ? 90.0F : 180.0F, 1.0F, 0.0F, 0.0F);
      if (paramBoolean)
        GlStateManager.func_179137_b(0.2D, -0.2D, 0.0D); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  protected void a(boolean paramBoolean1, boolean paramBoolean2) {
    try {
      if (paramBoolean1) {
        try {
          GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
          if (paramBoolean2) {
            GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(50.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.228F);
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        try {
          GlStateManager.func_179109_b(0.0F, 0.282F, 0.141F);
          if (paramBoolean2) {
            GlStateManager.func_179137_b(0.165D, -0.44999998807907104D, 0.0D);
            GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(-27.0F, 0.0F, 1.0F, 0.0F);
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


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */