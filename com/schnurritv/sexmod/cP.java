package com.schnurritv.sexmod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.vecmath.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cP extends bQ<ag> {
  static final Vec3i s = new Vec3i(255, 255, 255);
  
  static final float G = -420.69F;
  
  static final float B = 8.0F;
  
  static final float E = 3.0F;
  
  static final Vec3d w = new Vec3d(10.0D, -20.0D, -10.0D);
  
  static final float v = 0.1F;
  
  static final HashSet<String> z = new HashSet<>(Arrays.asList(new String[] { 
          "preggy", "shoeL", "shoeR", "frontAndInside", "Lside", "Rside", "cheekR", "cheekL", "fuckhole", "head", 
          "nose", "neck", "armL", "lowerArmL", "armR", "lowerArmR", "torso", "LegL", "LegR", "shinL", 
          "shinR" }));
  
  static final HashSet<String> F = new HashSet<>(Arrays.asList(new String[] { "lashR", "lashL", "closedR", "closedL", "browL", "browR", "closedL", "closedL" }));
  
  static final HashSet<String> J = new HashSet<>(Arrays.asList(new String[] { "LegR", "shinR", "boobR", "boobR1", "boobR2" }));
  
  static Minecraft x;
  
  float I = 0.0F;
  
  boolean A = false;
  
  boolean y = false;
  
  float t = 0.0F;
  
  float H = 0.0F;
  
  float D = 0.0F;
  
  float C = 0.0F;
  
  float r = 0.0F;
  
  float u = 0.0F;
  
  float q = 0.0F;
  
  float p = 0.0F;
  
  public cP(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel, double paramDouble) {
    super(paramRenderManager, paramAnimatedGeoModel, paramDouble);
    x = Minecraft.func_71410_x();
  }
  
  protected ResourceLocation a(ag paramag) throws IOException {
    ResourceLocation resourceLocation;
    UUID uUID = paramag.B();
    if (uUID == null)
      uUID = paramag.r(); 
    try {
      if (paramag.field_70170_p instanceof com.c || uUID == null) {
        resourceLocation = h.get(x.func_110432_I().func_148256_e().getId());
        try {
          if (resourceLocation == null)
            return a(x.func_110432_I().func_148256_e().getId(), paramag.field_70170_p); 
        } catch (IOException iOException) {
          throw b(null);
        } 
      } else {
        resourceLocation = h.get(uUID);
        try {
          if (resourceLocation == null)
            return a(uUID, paramag.field_70170_p); 
        } catch (IOException iOException) {
          throw b(null);
        } 
      } 
    } catch (IOException iOException) {
      throw b(null);
    } 
    return resourceLocation;
  }
  
  public void b(ag paramag, float paramFloat) {
    x.func_175598_ae().func_188391_a((Entity)paramag, 0.0D, 0.0D, 0.0D, -420.69F, paramFloat, false);
  }
  
  private void a(float paramFloat) {
    if (x.func_175606_aa() instanceof EntityPlayer) {
      EntityPlayer entityPlayer = (EntityPlayer)x.func_175606_aa();
      float f1 = entityPlayer.field_70140_Q - entityPlayer.field_70141_P;
      float f2 = -(entityPlayer.field_70140_Q + f1 * paramFloat);
      float f3 = entityPlayer.field_71107_bF + (entityPlayer.field_71109_bG - entityPlayer.field_71107_bF) * paramFloat;
      float f4 = MathHelper.func_76126_a(f2 * 3.1415927F) * f3 * 0.5F;
      GlStateManager.func_179137_b(Math.cos(x.field_71439_g.field_70177_z * 0.017453292519943295D) * f4, Math.abs(MathHelper.func_76134_b(f2 * 3.1415927F) * f3), Math.sin(x.field_71439_g.field_70177_z * 0.017453292519943295D) * f4);
    } 
  }
  
  public void a(GeoModel paramGeoModel, ag paramag, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    super.a(paramGeoModel, paramag, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramag.as);
  }
  
  public void func_76979_b(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    try {
      if (!(paramEntity instanceof ag)) {
        super.func_76979_b(paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    ag ag = (ag)paramEntity;
    try {
      if (ag.h() != b1.PICK_UP)
        try {
          if (ag.h() != b1.SHOULDER_IDLE) {
            super.func_76979_b(paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
            return;
          } 
          return;
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
  }
  
  Vec3d a(World paramWorld, ag paramag, UUID paramUUID, double paramDouble1, double paramDouble2, double paramDouble3) {
    try {
      if (paramWorld == null)
        return new Vec3d(paramDouble1, paramDouble2, paramDouble3); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (paramUUID == null)
        return new Vec3d(paramDouble1, paramDouble2, paramDouble3); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (paramag == null)
        return new Vec3d(paramDouble1, paramDouble2, paramDouble3); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    EntityPlayer entityPlayer = paramWorld.func_152378_a(paramUUID);
    try {
      if (entityPlayer == null)
        return new Vec3d(paramDouble1, paramDouble2, paramDouble3); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    Vec3d vec3d1 = entityPlayer.func_174791_d();
    Vec3d vec3d2 = x.field_71439_g.func_174791_d();
    paramag.field_70760_ar = entityPlayer.field_70758_at;
    paramag.field_70761_aq = entityPlayer.field_70759_as;
    paramag.b(b1.START_THROWING);
    return vec3d1.func_178788_d(vec3d2);
  }
  
  public void a(ag paramag, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    // Byte code:
    //   0: aload_0
    //   1: ldc -420.69
    //   3: fload #8
    //   5: fcmpl
    //   6: ifne -> 34
    //   9: aload_1
    //   10: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   13: getstatic com/schnurritv/sexmod/b1.SHOULDER_IDLE : Lcom/schnurritv/sexmod/b1;
    //   16: if_acmpne -> 34
    //   19: goto -> 26
    //   22: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   25: athrow
    //   26: iconst_1
    //   27: goto -> 35
    //   30: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   33: athrow
    //   34: iconst_0
    //   35: putfield A : Z
    //   38: aload_0
    //   39: ldc -420.69
    //   41: fload #8
    //   43: fcmpl
    //   44: ifne -> 72
    //   47: aload_1
    //   48: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   51: getstatic com/schnurritv/sexmod/b1.PICK_UP : Lcom/schnurritv/sexmod/b1;
    //   54: if_acmpne -> 72
    //   57: goto -> 64
    //   60: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   63: athrow
    //   64: iconst_1
    //   65: goto -> 73
    //   68: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   71: athrow
    //   72: iconst_0
    //   73: putfield y : Z
    //   76: aload_0
    //   77: fload #9
    //   79: putfield I : F
    //   82: aload_0
    //   83: fload #8
    //   85: putfield t : F
    //   88: aload_1
    //   89: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   92: astore #10
    //   94: aload_0
    //   95: ldc 15.0
    //   97: putfield H : F
    //   100: aload_1
    //   101: invokevirtual r : ()Ljava/util/UUID;
    //   104: astore #11
    //   106: aload_1
    //   107: getfield ag : Z
    //   110: ifeq -> 151
    //   113: aload_0
    //   114: aload_1
    //   115: getfield field_70170_p : Lnet/minecraft/world/World;
    //   118: aload_1
    //   119: aload #11
    //   121: dload_2
    //   122: dload #4
    //   124: dload #6
    //   126: invokevirtual a : (Lnet/minecraft/world/World;Lcom/schnurritv/sexmod/ag;Ljava/util/UUID;DDD)Lnet/minecraft/util/math/Vec3d;
    //   129: astore #12
    //   131: aload #12
    //   133: getfield field_72450_a : D
    //   136: dstore_2
    //   137: aload #12
    //   139: getfield field_72448_b : D
    //   142: dstore #4
    //   144: aload #12
    //   146: getfield field_72449_c : D
    //   149: dstore #6
    //   151: aload #10
    //   153: getstatic com/schnurritv/sexmod/b1.THROWN : Lcom/schnurritv/sexmod/b1;
    //   156: if_acmpeq -> 174
    //   159: aload #10
    //   161: getstatic com/schnurritv/sexmod/b1.START_THROWING : Lcom/schnurritv/sexmod/b1;
    //   164: if_acmpne -> 255
    //   167: goto -> 174
    //   170: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   173: athrow
    //   174: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   177: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   180: getfield field_74320_O : I
    //   183: ifne -> 227
    //   186: goto -> 193
    //   189: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   192: athrow
    //   193: fload #8
    //   195: ldc -420.69
    //   197: fcmpl
    //   198: ifne -> 227
    //   201: goto -> 208
    //   204: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   207: athrow
    //   208: aload_1
    //   209: getfield ag : Z
    //   212: ifne -> 227
    //   215: goto -> 222
    //   218: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   221: athrow
    //   222: return
    //   223: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   226: athrow
    //   227: aload_1
    //   228: getfield ag : Z
    //   231: ifne -> 255
    //   234: aload_1
    //   235: invokevirtual m : ()Ljava/lang/Float;
    //   238: invokevirtual floatValue : ()F
    //   241: fstore #12
    //   243: aload_1
    //   244: fload #12
    //   246: putfield field_70760_ar : F
    //   249: aload_1
    //   250: fload #12
    //   252: putfield field_70761_aq : F
    //   255: aload_1
    //   256: aload #10
    //   258: invokestatic a : (Lcom/schnurritv/sexmod/ag;Lcom/schnurritv/sexmod/b1;)Z
    //   261: ifeq -> 637
    //   264: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   267: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   270: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   273: aload #11
    //   275: invokevirtual equals : (Ljava/lang/Object;)Z
    //   278: ifeq -> 456
    //   281: goto -> 288
    //   284: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   287: athrow
    //   288: ldc -420.69
    //   290: fload #8
    //   292: fcmpl
    //   293: ifeq -> 308
    //   296: goto -> 303
    //   299: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   302: athrow
    //   303: return
    //   304: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   307: athrow
    //   308: aload_1
    //   309: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   312: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   315: getfield field_70177_z : F
    //   318: ldc 180.0
    //   320: fadd
    //   321: putfield field_70761_aq : F
    //   324: aload_1
    //   325: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   328: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   331: getfield field_70177_z : F
    //   334: ldc 180.0
    //   336: fadd
    //   337: putfield field_70760_ar : F
    //   340: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   343: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   346: invokevirtual func_70040_Z : ()Lnet/minecraft/util/math/Vec3d;
    //   349: astore #12
    //   351: invokestatic func_179094_E : ()V
    //   354: aload #12
    //   356: getfield field_72450_a : D
    //   359: aload #12
    //   361: getfield field_72448_b : D
    //   364: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   367: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   370: invokevirtual func_70047_e : ()F
    //   373: f2d
    //   374: dadd
    //   375: aload #12
    //   377: getfield field_72449_c : D
    //   380: invokestatic func_179137_b : (DDD)V
    //   383: new net/minecraft/util/math/Vec3d
    //   386: dup
    //   387: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   390: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   393: getfield field_70125_A : F
    //   396: invokestatic abs : (F)F
    //   399: fneg
    //   400: f2d
    //   401: dconst_0
    //   402: dconst_0
    //   403: invokespecial <init> : (DDD)V
    //   406: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   409: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   412: getfield field_70177_z : F
    //   415: invokestatic a : (Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;
    //   418: astore #13
    //   420: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   423: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   426: getfield field_70125_A : F
    //   429: aload #13
    //   431: getfield field_72450_a : D
    //   434: d2f
    //   435: fconst_0
    //   436: aload #13
    //   438: getfield field_72449_c : D
    //   441: d2f
    //   442: invokestatic func_179114_b : (FFFF)V
    //   445: dconst_0
    //   446: dstore_2
    //   447: dconst_0
    //   448: dstore #4
    //   450: dconst_0
    //   451: dstore #6
    //   453: goto -> 975
    //   456: aload_1
    //   457: getfield ag : Z
    //   460: ifeq -> 499
    //   463: aload #11
    //   465: ifnull -> 499
    //   468: goto -> 475
    //   471: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   474: athrow
    //   475: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   478: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   481: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   484: aload #11
    //   486: invokevirtual equals : (Ljava/lang/Object;)Z
    //   489: ifeq -> 605
    //   492: goto -> 499
    //   495: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   498: athrow
    //   499: aload #11
    //   501: ifnull -> 579
    //   504: goto -> 511
    //   507: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   510: athrow
    //   511: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   514: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   517: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   520: aload #11
    //   522: invokevirtual equals : (Ljava/lang/Object;)Z
    //   525: ifne -> 579
    //   528: goto -> 535
    //   531: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   534: athrow
    //   535: aload_1
    //   536: getfield field_70170_p : Lnet/minecraft/world/World;
    //   539: aload #11
    //   541: invokevirtual func_152378_a : (Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;
    //   544: astore #12
    //   546: aload #12
    //   548: ifnull -> 576
    //   551: aload_1
    //   552: aload #12
    //   554: getfield field_70177_z : F
    //   557: putfield field_70761_aq : F
    //   560: aload_1
    //   561: aload #12
    //   563: getfield field_70177_z : F
    //   566: putfield field_70760_ar : F
    //   569: goto -> 576
    //   572: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   575: athrow
    //   576: goto -> 605
    //   579: aload_1
    //   580: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   583: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   586: getfield field_70177_z : F
    //   589: putfield field_70761_aq : F
    //   592: aload_1
    //   593: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   596: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   599: getfield field_70177_z : F
    //   602: putfield field_70760_ar : F
    //   605: aload_0
    //   606: aload_1
    //   607: fload #9
    //   609: invokevirtual a : (Lcom/schnurritv/sexmod/ag;F)Lnet/minecraft/util/math/Vec3d;
    //   612: astore #12
    //   614: aload #12
    //   616: getfield field_72450_a : D
    //   619: dstore_2
    //   620: aload #12
    //   622: getfield field_72448_b : D
    //   625: dstore #4
    //   627: aload #12
    //   629: getfield field_72449_c : D
    //   632: dstore #6
    //   634: goto -> 975
    //   637: aload_0
    //   638: getfield A : Z
    //   641: ifeq -> 766
    //   644: aload_0
    //   645: fload #9
    //   647: invokespecial a : (F)V
    //   650: new net/minecraft/util/math/Vec3d
    //   653: dup
    //   654: ldc -0.1
    //   656: ldc 0.2
    //   658: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   661: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   664: getfield field_74334_X : F
    //   667: ldc 110.0
    //   669: fdiv
    //   670: invokestatic a : (FFF)F
    //   673: f2d
    //   674: dconst_0
    //   675: dconst_0
    //   676: invokespecial <init> : (DDD)V
    //   679: astore #12
    //   681: aload #12
    //   683: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   686: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   689: getfield field_70177_z : F
    //   692: invokestatic a : (Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;
    //   695: astore #12
    //   697: aload #12
    //   699: getfield field_72450_a : D
    //   702: dstore_2
    //   703: aload #12
    //   705: getfield field_72448_b : D
    //   708: dstore #4
    //   710: aload #12
    //   712: getfield field_72449_c : D
    //   715: dstore #6
    //   717: aload_1
    //   718: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   721: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   724: getfield field_70177_z : F
    //   727: putfield field_70761_aq : F
    //   730: aload_1
    //   731: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   734: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   737: getfield field_70126_B : F
    //   740: putfield field_70760_ar : F
    //   743: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   746: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   749: invokevirtual func_70093_af : ()Z
    //   752: ifeq -> 763
    //   755: dload #4
    //   757: ldc2_w 0.075
    //   760: dsub
    //   761: dstore #4
    //   763: goto -> 975
    //   766: aload #10
    //   768: getstatic com/schnurritv/sexmod/b1.SHOULDER_IDLE : Lcom/schnurritv/sexmod/b1;
    //   771: if_acmpne -> 914
    //   774: aload #11
    //   776: ifnonnull -> 791
    //   779: goto -> 786
    //   782: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   785: athrow
    //   786: return
    //   787: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   790: athrow
    //   791: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   794: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   797: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   800: aload #11
    //   802: invokevirtual equals : (Ljava/lang/Object;)Z
    //   805: ifeq -> 832
    //   808: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   811: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   814: getfield field_74320_O : I
    //   817: ifne -> 832
    //   820: goto -> 827
    //   823: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   826: athrow
    //   827: return
    //   828: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   831: athrow
    //   832: aload_1
    //   833: getfield field_70170_p : Lnet/minecraft/world/World;
    //   836: aload #11
    //   838: invokevirtual func_152378_a : (Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;
    //   841: astore #12
    //   843: aload #12
    //   845: ifnonnull -> 853
    //   848: return
    //   849: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   852: athrow
    //   853: aload_0
    //   854: aload #12
    //   856: fload #9
    //   858: invokevirtual a : (Lnet/minecraft/entity/player/EntityPlayer;F)Ljavax/vecmath/Vector4f;
    //   861: astore #13
    //   863: aload #13
    //   865: getfield x : F
    //   868: f2d
    //   869: dstore_2
    //   870: aload #13
    //   872: getfield y : F
    //   875: f2d
    //   876: dstore #4
    //   878: aload #13
    //   880: getfield z : F
    //   883: f2d
    //   884: dstore #6
    //   886: aload_1
    //   887: aload #13
    //   889: getfield w : F
    //   892: putfield field_70761_aq : F
    //   895: aload #12
    //   897: invokevirtual func_70093_af : ()Z
    //   900: ifeq -> 911
    //   903: dload #4
    //   905: ldc2_w 0.32
    //   908: dsub
    //   909: dstore #4
    //   911: goto -> 975
    //   914: aload #10
    //   916: getstatic com/schnurritv/sexmod/b1.PICK_UP : Lcom/schnurritv/sexmod/b1;
    //   919: if_acmpne -> 975
    //   922: aload #11
    //   924: ifnull -> 975
    //   927: goto -> 934
    //   930: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   933: athrow
    //   934: aload_1
    //   935: getfield field_70170_p : Lnet/minecraft/world/World;
    //   938: aload #11
    //   940: invokevirtual func_152378_a : (Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;
    //   943: astore #12
    //   945: aload #12
    //   947: ifnull -> 975
    //   950: aload_1
    //   951: aload #12
    //   953: getfield field_70758_at : F
    //   956: putfield field_70760_ar : F
    //   959: aload_1
    //   960: aload #12
    //   962: getfield field_70759_as : F
    //   965: putfield field_70761_aq : F
    //   968: goto -> 975
    //   971: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   974: athrow
    //   975: aload_0
    //   976: aload_1
    //   977: dload_2
    //   978: dload #4
    //   980: dload #6
    //   982: fload #8
    //   984: fload #9
    //   986: invokespecial a : (Lcom/schnurritv/sexmod/Q;DDDFF)V
    //   989: aload_1
    //   990: aload #10
    //   992: invokestatic a : (Lcom/schnurritv/sexmod/ag;Lcom/schnurritv/sexmod/b1;)Z
    //   995: ifeq -> 1051
    //   998: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   1001: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   1004: getfield field_74320_O : I
    //   1007: ifne -> 1051
    //   1010: goto -> 1017
    //   1013: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1016: athrow
    //   1017: getstatic com/schnurritv/sexmod/cP.x : Lnet/minecraft/client/Minecraft;
    //   1020: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   1023: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   1026: aload #11
    //   1028: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1031: ifeq -> 1051
    //   1034: goto -> 1041
    //   1037: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1040: athrow
    //   1041: invokestatic func_179121_F : ()V
    //   1044: goto -> 1051
    //   1047: invokestatic b : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1050: athrow
    //   1051: return
    // Exception table:
    //   from	to	target	type
    //   0	19	22	java/lang/NullPointerException
    //   9	30	30	java/lang/NullPointerException
    //   35	57	60	java/lang/NullPointerException
    //   47	68	68	java/lang/NullPointerException
    //   151	167	170	java/lang/NullPointerException
    //   159	186	189	java/lang/NullPointerException
    //   174	201	204	java/lang/NullPointerException
    //   193	215	218	java/lang/NullPointerException
    //   208	223	223	java/lang/NullPointerException
    //   255	281	284	java/lang/NullPointerException
    //   264	296	299	java/lang/NullPointerException
    //   288	304	304	java/lang/NullPointerException
    //   456	468	471	java/lang/NullPointerException
    //   463	492	495	java/lang/NullPointerException
    //   475	504	507	java/lang/NullPointerException
    //   499	528	531	java/lang/NullPointerException
    //   546	569	572	java/lang/NullPointerException
    //   766	779	782	java/lang/NullPointerException
    //   774	787	787	java/lang/NullPointerException
    //   791	820	823	java/lang/NullPointerException
    //   808	828	828	java/lang/NullPointerException
    //   843	849	849	java/lang/NullPointerException
    //   914	927	930	java/lang/NullPointerException
    //   945	968	971	java/lang/NullPointerException
    //   975	1010	1013	java/lang/NullPointerException
    //   998	1034	1037	java/lang/NullPointerException
    //   1017	1044	1047	java/lang/NullPointerException
  }
  
  public static boolean a(ag paramag, b1 paramb1) {
    try {
      if (paramb1 == b1.START_THROWING)
        try {
          if (!paramag.ag)
            return false; 
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (x.field_71474_y.field_74320_O != 0)
        try {
          if (paramb1 != b1.START_THROWING) {
            try {
              if (paramb1 == b1.PICK_UP)
                return false; 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
          } else {
            return false;
          } 
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      switch (a.a[paramb1.ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
          return true;
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return false;
  }
  
  Vec3d a(ag paramag, float paramFloat) {
    UUID uUID = paramag.r();
    try {
      if (uUID == null)
        return Vec3d.field_186680_a; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    EntityPlayer entityPlayer = paramag.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return Vec3d.field_186680_a; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    Vec3d vec3d1 = bZ.a(new Vec3d(entityPlayer.field_70169_q, entityPlayer.field_70167_r, entityPlayer.field_70166_s), entityPlayer.func_174791_d(), paramFloat);
    Vec3d vec3d2 = bZ.a(new Vec3d(x.field_71439_g.field_70169_q, x.field_71439_g.field_70167_r, x.field_71439_g.field_70166_s), x.field_71439_g.func_174791_d(), paramFloat);
    return vec3d1.func_178788_d(vec3d2);
  }
  
  Vector4f a(EntityPlayer paramEntityPlayer, float paramFloat) {
    EntityPlayerSP entityPlayerSP = x.field_71439_g;
    float f = bZ.a(paramEntityPlayer.field_70760_ar, paramEntityPlayer.field_70761_aq, paramFloat);
    Vec3d vec3d1 = bZ.a(new Vec3d(paramEntityPlayer.field_70142_S, paramEntityPlayer.field_70137_T, paramEntityPlayer.field_70136_U), paramEntityPlayer.func_174791_d(), paramFloat);
    Vec3d vec3d2 = bZ.a(new Vec3d(((EntityPlayer)entityPlayerSP).field_70142_S, ((EntityPlayer)entityPlayerSP).field_70137_T, ((EntityPlayer)entityPlayerSP).field_70136_U), entityPlayerSP.func_174791_d(), paramFloat);
    Vec3d vec3d3 = vec3d1.func_178788_d(vec3d2);
    return new Vector4f((float)vec3d3.field_72450_a, (float)vec3d3.field_72448_b, (float)vec3d3.field_72449_c, f);
  }
  
  protected Vec3i a(String paramString) {
    String[] arrayOfString = aJ.a(this.k);
    try {
      if (arrayOfString.length < 8)
        return n; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (paramString.contains("band"))
        return s; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (!paramString.contains("eyeColor")) {
        try {
          if (paramString.contains("eyeColor2"))
            return b(arrayOfString[8]); 
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        } 
      } else {
        return b(arrayOfString[8]);
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (!paramString.contains("variant"))
        try {
          if (!paramString.contains("boob")) {
            try {
              if (paramString.contains("hair"))
                return d(arrayOfString[6]); 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            try {
              if (z.contains(paramString))
                return c(arrayOfString[7]); 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            try {
              if (F.contains(paramString))
                return d(arrayOfString[6]); 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            return n;
          } 
          return c(arrayOfString[7]);
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return c(arrayOfString[7]);
  }
  
  Vec3i b(String paramString) {
    return K.values()[Integer.parseInt(paramString)].a();
  }
  
  Vec3i c(String paramString) {
    return x.values()[Integer.parseInt(paramString)].a();
  }
  
  Vec3i d(String paramString) {
    return aA.values()[Integer.parseInt(paramString)].a();
  }
  
  protected void a(BufferBuilder paramBufferBuilder, String paramString, GeoBone paramGeoBone) {
    try {
      if (this.k.field_70170_p instanceof com.c)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    String[] arrayOfString = aJ.a(this.k);
    try {
      if (arrayOfString.length < 8)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    String str = paramString;
    byte b = -1;
    try {
      switch (str.hashCode()) {
        case 3105718:
          if (str.equals("earL"))
            b = 0; 
          break;
        case 3105724:
          if (str.equals("earR"))
            b = 1; 
          break;
        case 3194850:
          if (str.equals("hair"))
            b = 2; 
          break;
        case 3029410:
          if (str.equals("body"))
            b = 3; 
          break;
        case 2364452:
          if (str.equals("LegR"))
            b = 4; 
          break;
        case 93921650:
          if (str.equals("boobR"))
            b = 5; 
          break;
        case -1383396097:
          if (str.equals("boobR1"))
            b = 6; 
          break;
        case -1383396096:
          if (str.equals("boobR2"))
            b = 7; 
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      switch (b) {
        case 0:
          a(paramGeoBone, arrayOfString[0], arrayOfString[1], arrayOfString[3]);
          break;
        case 1:
          a(paramGeoBone, arrayOfString[0], arrayOfString[2], arrayOfString[4]);
          break;
        case 2:
          b(paramGeoBone, arrayOfString[5]);
          break;
        case 3:
          paramGeoBone.setPivotY(-0.15F);
          a(paramGeoBone);
          break;
        case 4:
          a(paramGeoBone, 25.0F, 25.0F);
          break;
        case 5:
          a(paramGeoBone, 30.0F, 30.0F);
          break;
        case 6:
          a(paramGeoBone, 10.0F, 15.0F);
          break;
        case 7:
          a(paramGeoBone, 5.0F, 3.0F);
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (paramString.contains("crown"))
        a(paramGeoBone, arrayOfString[9]); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
  }
  
  void a(GeoBone paramGeoBone, String paramString) {
    int i = Integer.parseInt(paramString);
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    paramGeoBone.setHidden((i == 0));
  }
  
  void a(GeoBone paramGeoBone, float paramFloat1, float paramFloat2) {
    try {
      if (x.func_147113_T())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (!this.A)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    paramGeoBone.setRotationX(paramGeoBone.getRotationX() + bZ.b(bZ.b(this.p, -paramFloat1, paramFloat1)));
    paramGeoBone.setRotationZ(paramGeoBone.getRotationZ() + bZ.b(bZ.b(this.q, -paramFloat2, paramFloat2)));
  }
  
  void a(GeoBone paramGeoBone) {
    try {
      if (this.t == -420.69F)
        try {
          if (this.k.h() == b1.SHOULDER_IDLE) {
            UUID uUID = this.k.r();
            try {
              if (uUID == null)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            EntityPlayer entityPlayer = this.k.field_70170_p.func_152378_a(uUID);
            try {
              if (entityPlayer == null)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            float f = -(x.func_175598_ae()).field_78732_j;
            try {
              paramGeoBone.setPivotY(8.0F);
              if (x.func_147113_T())
                return; 
            } catch (NullPointerException nullPointerException) {
              throw b(null);
            } 
            paramGeoBone.setRotationX(paramGeoBone.getRotationX() + bZ.b(f));
            return;
          } 
          return;
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
  }
  
  void b(GeoBone paramGeoBone, String paramString) {
    int i = Integer.parseInt(paramString);
    b(paramGeoBone, i);
  }
  
  void a(GeoBone paramGeoBone, String paramString1, String paramString2, String paramString3) {
    GeoBone geoBone1 = b(paramGeoBone, Integer.parseInt(paramString1));
    GeoBone geoBone2 = b(geoBone1, Integer.parseInt(paramString2));
    int i = Integer.parseInt(paramString3);
    i = (int)(0.01F * i * i);
    List list = geoBone2.childBones;
    int j = list.size();
    int k = Math.round(i / 100.0F * j);
    Random random = new Random(i);
    ArrayList<Integer> arrayList = new ArrayList();
    for (byte b = 0; b < k; b++) {
      int m = random.nextInt(j);
      try {
        if (!arrayList.contains(Integer.valueOf(m))) {
          arrayList.add(Integer.valueOf(m));
        } else {
          b--;
        } 
      } catch (NullPointerException nullPointerException) {
        throw b(null);
      } 
    } 
    geoBone2.childBones.forEach(paramGeoBone -> paramGeoBone.setHidden(true));
    arrayList.forEach(paramInteger -> a(paramGeoBone, paramInteger.intValue()));
  }
  
  protected Vec3i a(Vec3i paramVec3i) {
    try {
      if (this.t == -420.69F)
        try {
          if (a(this.k, this.k.h())) {
            float f = bZ.b(this.H, 2.0F, 15.0F) / 15.0F;
            return new Vec3i((paramVec3i.func_177958_n() * f), (paramVec3i.func_177956_o() * f), (paramVec3i.func_177952_p() * f));
          } 
          return paramVec3i;
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return paramVec3i;
  }
  
  protected ItemStack a(@Nullable ItemStack paramItemStack) {
    b1 b1 = this.k.h();
    try {
      if (b1 != b1.RUN)
        try {
          return (b1 != b1.CATCH) ? paramItemStack : (ItemStack)this.k.func_184212_Q().func_187225_a(ag.aj);
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return (ItemStack)this.k.func_184212_Q().func_187225_a(ag.aj);
  }
  
  protected float a() {
    try {
      if (this.k.h() == b1.CATCH)
        return 0.5F; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return 1.0F;
  }
  
  protected Vec3d a(ItemStack paramItemStack) {
    try {
      if (paramItemStack == null)
        return Vec3d.field_186680_a; 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (!(paramItemStack.func_77973_b() instanceof net.minecraft.item.ItemBlock))
        try {
          return (paramItemStack.func_77976_d() != 1) ? new Vec3d(180.0D, 0.0D, 0.0D) : super.a(paramItemStack);
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return super.a(paramItemStack);
  }
  
  public void a(BufferBuilder paramBufferBuilder, GeoCube paramGeoCube, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, double paramDouble) {
    try {
      if (this.A)
        try {
          if (!J.contains(paramGeoBone.getName()))
            return; 
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    try {
      if (!this.j.contains(paramGeoBone.getName()))
        super.a(paramBufferBuilder, paramGeoCube, paramGeoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramDouble); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
  }
  
  private static Exception b(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */