package com.schnurritv.sexmod;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cD extends cr {
  float y = 0.0F;
  
  public cD(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  protected void b() {
    GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
    GlStateManager.func_179152_a(0.65F, 0.65F, 0.65F);
  }
  
  protected ItemStack a(@Nullable ItemStack paramItemStack) {
    ItemStack itemStack;
    switch (a.b[this.k.h().ordinal()]) {
      case 1:
      case 2:
        itemStack = ((aI)this.k).ar;
        this.k.func_184611_a(EnumHand.MAIN_HAND, itemStack);
        return itemStack;
    } 
    return paramItemStack;
  }
  
  boolean a() {
    return ((Boolean)this.k.func_184212_Q().func_187225_a(Q.c)).booleanValue();
  }
  
  protected void a(String paramString, GeoBone paramGeoBone) {
    try {
      if (Minecraft.func_71410_x().func_147113_T())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    String str = paramString;
    byte b = -1;
    try {
      switch (str.hashCode()) {
        case 3198432:
          if (str.equals("head"))
            b = 0; 
          break;
        case 2120576361:
          if (str.equals("backHair"))
            b = 1; 
          break;
        case -345841663:
          if (str.equals("frontHairL"))
            b = 2; 
          break;
        case -345841657:
          if (str.equals("frontHairR"))
            b = 3; 
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      switch (b) {
        case 0:
          this.y = paramGeoBone.getRotationX();
          break;
        case 1:
          try {
            if (!a() && this.y > 0.0F) {
              double d = (this.y / bZ.b(45.0F));
              float f = (float)bZ.b(0.0D, 0.75D, d);
              paramGeoBone.setPositionZ(f);
              paramGeoBone.setPositionY(f);
              paramGeoBone.setRotationX(-this.y);
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          break;
        case 2:
        case 3:
          try {
            if (a())
              break; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          paramGeoBone.setRotationX(-this.y);
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
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
    GlStateManager.func_179114_b(paramBoolean ? 60.0F : 150.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void a(boolean paramBoolean) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    GlStateManager.func_179114_b(paramBoolean ? 60.0F : 150.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void a(boolean paramBoolean1, boolean paramBoolean2) {
    try {
      if (paramBoolean1) {
        try {
          GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
          if (paramBoolean2) {
            GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.16F);
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        try {
          GlStateManager.func_179109_b(0.0F, 0.282F, 0.1F);
          if (paramBoolean2) {
            GlStateManager.func_179137_b(0.165D, -0.30000001192092896D, 0.0D);
            GlStateManager.func_179114_b(-135.0F, 1.0F, 0.0F, 0.0F);
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


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */