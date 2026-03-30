package com.schnurritv.sexmod;

import javax.vecmath.Vector3f;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class c1 extends cr {
  Vector3f B = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f E = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f y = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f C = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f z = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f A = new Vector3f(0.0F, 0.0F, 0.0F);
  
  Vector3f D = new Vector3f(0.0F, 0.0F, 0.0F);
  
  public c1(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  protected void b() {
    GlStateManager.func_179109_b(0.0F, -1.25F, 0.0F);
    GlStateManager.func_179152_a(0.8F, 0.8F, 0.8F);
  }
  
  protected void a(String paramString, GeoBone paramGeoBone) {
    try {
      if ("slime".equals(paramString)) {
        this.y = new Vector3f(paramGeoBone.getRotationX(), paramGeoBone.getRotationY(), paramGeoBone.getRotationZ());
        this.B = new Vector3f(paramGeoBone.getScaleX(), paramGeoBone.getScaleY(), paramGeoBone.getScaleZ());
        this.E = new Vector3f(paramGeoBone.getPositionX(), paramGeoBone.getPositionY(), paramGeoBone.getPositionZ());
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("upperBody".equals(paramString))
        this.A = new Vector3f(paramGeoBone.getRotationX(), paramGeoBone.getRotationY(), paramGeoBone.getRotationZ()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("torso".equals(paramString))
        this.C = new Vector3f(paramGeoBone.getRotationX(), paramGeoBone.getRotationY(), paramGeoBone.getRotationZ()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("head".equals(paramString))
        this.D = new Vector3f(paramGeoBone.getRotationX(), paramGeoBone.getRotationY(), paramGeoBone.getRotationZ()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("boobs".equals(paramString))
        this.z = new Vector3f(paramGeoBone.getRotationX(), paramGeoBone.getRotationY(), paramGeoBone.getRotationZ()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("figure".equals(paramString)) {
        paramGeoBone.setRotationX(this.y.x);
        paramGeoBone.setRotationY(this.y.y);
        paramGeoBone.setRotationZ(this.y.z);
        paramGeoBone.setScaleX(this.B.x);
        paramGeoBone.setScaleY(this.B.y);
        paramGeoBone.setScaleZ(this.B.z);
        paramGeoBone.setPositionX(this.E.x);
        paramGeoBone.setPositionY(this.E.y);
        paramGeoBone.setPositionZ(this.E.z);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("dress".equals(paramString)) {
        paramGeoBone.setRotationX(this.A.x);
        paramGeoBone.setRotationY(this.A.y);
        paramGeoBone.setRotationZ(this.A.z);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("hat".equals(paramString)) {
        paramGeoBone.setRotationX(this.D.x);
        paramGeoBone.setRotationY(this.D.y);
        paramGeoBone.setRotationZ(this.D.z);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("boobsSlime".equals(paramString)) {
        paramGeoBone.setRotationX(this.z.x);
        paramGeoBone.setRotationY(this.z.y);
        paramGeoBone.setRotationZ(this.z.z);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  protected void a(boolean paramBoolean, ItemStack paramItemStack) {
    try {
      if (paramBoolean) {
        GlStateManager.func_179114_b(200.0F, 1.0F, 0.0F, 0.0F);
      } else {
        GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      } 
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
            GlStateManager.func_179114_b(35.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.228F);
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        try {
          GlStateManager.func_179114_b(30.34F, 1.0F, 0.0F, 0.0F);
          if (paramBoolean2) {
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -0.068F, 0.18F);
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


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\c1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */