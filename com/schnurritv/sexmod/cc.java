package com.schnurritv.sexmod;

import java.util.Arrays;
import java.util.HashSet;
import javax.annotation.Nullable;
import javax.vecmath.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cc extends bQ<aD> {
  static final HashSet<String> p = new HashSet<>(Arrays.asList(new String[] { 
          "colorSpots", "neck", "head", "snout", "midSectionR", "midSectionL", "innerCheekLR", "innerCheekRR", "gayL", "gayR", 
          "legR", "legL", "shinL", "toesL", "kneeL", "curvesL", "shinR", "toesR", "kneeR", "curvesR", 
          "sideL", "sideR", "hip", "torsoL", "torsoR", "armR", "lowerArmR", "ellbowR", "armL", "lowerArmL", 
          "ellbowL", "hornUL", "hornUR", "tail", "tail2", "tail3", "tail4", "tail5", "hornDL2", "hornDR2", 
          "hornDR3M", "hornDL3M", "frecklesAL1", "frecklesAL2", "frecklesAR1", "frecklesAR2", "frecklesHL1", "frecklesHL2", "frecklesHR1", "frecklesHR2" }));
  
  static final HashSet<String> t = new HashSet<>(Arrays.asList(new String[] { 
          "boobR", "boobL", "frontNeck", "Rside", "Lside", "frontAndInside", "innerCheekLL", "innerCheekRL", "layer", "layer2", 
          "down", "down2", "down3", "down4", "down5", "fuckhole", "hornDR3S", "hornDL3S", "assholeCoverUp", "assholeCoverUp2" }));
  
  public static boolean q = false;
  
  Minecraft s = Minecraft.func_71410_x();
  
  Vector3f r;
  
  public cc(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel, double paramDouble) {
    super(paramRenderManager, paramAnimatedGeoModel, paramDouble);
  }
  
  protected Vec3i a(String paramString) {
    EntityDataManager entityDataManager = this.k.func_184212_Q();
    EyeAndKoboldColor eyeAndKoboldColor = EyeAndKoboldColor.valueOf((String)entityDataManager.func_187225_a(aD.G));
    BlockPos blockPos = (BlockPos)entityDataManager.func_187225_a(aD.H);
    try {
      if (p.contains(paramString))
        return eyeAndKoboldColor.getMainColor(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (t.contains(paramString))
        return eyeAndKoboldColor.getSecondaryColor(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!"irisR".equals(paramString))
        try {
          return (Vec3i)(!"irisL".equals(paramString) ? n : blockPos);
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (Vec3i)blockPos;
  }
  
  protected ItemStack a(@Nullable ItemStack paramItemStack) {
    try {
      switch (a.a[this.k.h().ordinal()]) {
        case 1:
          try {
            if (((Boolean)this.k.func_184212_Q().func_187225_a(aD.aV)).booleanValue())
              return new ItemStack(Items.field_151036_c); 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return new ItemStack(Items.field_151035_b);
        case 2:
          try {
            if (((Boolean)this.k.func_184212_Q().func_187225_a(aD.av)).booleanValue())
              return new ItemStack(Items.field_151040_l); 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          break;
        case 3:
          return new ItemStack(Items.field_151040_l);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return paramItemStack;
  }
  
  public void a(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, double paramDouble) {
    try {
      if (this.k.field_70170_p instanceof com.c)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    String str = paramGeoBone.getName();
    if ("blowOpening".equals(str))
      paramDouble = 0.0D; 
    if ("mouth".equals(str)) {
      String[] arrayOfString = aJ.a(this.k);
      int i = Integer.parseInt(arrayOfString[6]);
      if (i == 1)
        paramDouble = -0.078125D; 
    } 
    super.a(paramBufferBuilder, paramGeoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramDouble);
  }
  
  protected void d() {
    float f = ((Float)this.k.func_184212_Q().func_187225_a(aD.aP)).floatValue();
    GlStateManager.func_179152_a(1.0F - f, 1.0F - f, 1.0F - f);
  }
  
  protected void a() {
    float f = ((Float)this.k.func_184212_Q().func_187225_a(aD.aP)).floatValue();
    double d = 1.0D / (1.0D - f);
    GlStateManager.func_179139_a(d, d, d);
  }
  
  protected ItemStack c() {
    String str = (String)this.k.func_184212_Q().func_187225_a(Q.f);
    try {
      if ("STARTBLOWJOB".equals(str))
        return new ItemStack(Items.field_151035_b); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("ANAL_START".equals(str))
        return new ItemStack(Items.field_151043_k, 3); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return null;
  }
  
  public void a(aD paramaD, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    String str = (String)paramaD.func_184212_Q().func_187225_a(aJ.G);
    try {
      if (paramaD.aR == null)
        paramaD.aR = str; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!paramaD.aR.equals(str)) {
        b();
        paramaD.aR = str;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.r = new Vector3f((float)paramDouble1, (float)paramDouble2, (float)paramDouble3);
    super.a(paramaD, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
  }
  
  protected void a(double paramDouble1, double paramDouble2, double paramDouble3) {
    EntityDataManager entityDataManager = this.k.func_184212_Q();
    String str = (String)entityDataManager.func_187225_a(aD.aG);
    try {
      if ("null".equals(str)) {
        super.a(paramDouble1, paramDouble2, paramDouble3);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EyeAndKoboldColor eyeAndKoboldColor = EyeAndKoboldColor.valueOf((String)entityDataManager.func_187225_a(aD.G));
    str = eyeAndKoboldColor.getTextColor() + " -" + str + "-";
    func_147906_a((Entity)this.k, this.k.F() + str, paramDouble1, paramDouble2 + this.k.z(), paramDouble3, 300);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */