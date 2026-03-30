package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;

public class an extends aM<ag> {
  final float d = 60.0F;
  
  Minecraft c = Minecraft.func_71410_x();
  
  protected ResourceLocation[] b() {
    return new ResourceLocation[] { new ResourceLocation("sexmod", "geo/goblin/goblin.geo.json"), new ResourceLocation("sexmod", "geo/goblin/goblin.geo.json") };
  }
  
  public ResourceLocation c() {
    return new ResourceLocation("sexmod", "textures/entity/goblin/goblin.png");
  }
  
  public ResourceLocation a() {
    return new ResourceLocation("sexmod", "animations/goblin/goblin.animation.json");
  }
  
  protected boolean a(ag paramag) {
    UUID uUID = paramag.B();
    if (uUID == null)
      uUID = paramag.r(); 
    try {
      if (uUID == null)
        return true; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    World world = paramag.field_70170_p;
    AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)world.func_152378_a(uUID);
    try {
      if (abstractClientPlayer == null)
        return true; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return "default".equals(abstractClientPlayer.func_175154_l());
  }
  
  public void a(ag paramag, Integer paramInteger, AnimationEvent paramAnimationEvent) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: aload_3
    //   4: invokespecial a : (Lcom/schnurritv/sexmod/Q;Ljava/lang/Integer;Lsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   7: aload_1
    //   8: getfield field_70170_p : Lnet/minecraft/world/World;
    //   11: instanceof com/c
    //   14: ifeq -> 22
    //   17: return
    //   18: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   21: athrow
    //   22: aload_0
    //   23: invokevirtual getAnimationProcessor : ()Lsoftware/bernie/geckolib3/core/processor/AnimationProcessor;
    //   26: astore #4
    //   28: aload #4
    //   30: ldc 'preggy'
    //   32: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   35: astore #5
    //   37: aload #5
    //   39: aload_1
    //   40: invokevirtual func_184212_Q : ()Lnet/minecraft/network/datasync/EntityDataManager;
    //   43: getstatic com/schnurritv/sexmod/ag.aA : Lnet/minecraft/network/datasync/DataParameter;
    //   46: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   49: checkcast java/lang/Boolean
    //   52: invokevirtual booleanValue : ()Z
    //   55: ifne -> 66
    //   58: iconst_1
    //   59: goto -> 67
    //   62: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   65: athrow
    //   66: iconst_0
    //   67: invokeinterface setHidden : (Z)V
    //   72: aload #4
    //   74: ldc 'body'
    //   76: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   79: astore #6
    //   81: aload #4
    //   83: ldc 'head'
    //   85: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   88: astore #7
    //   90: aload_1
    //   91: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   94: astore #8
    //   96: aload #8
    //   98: getstatic com/schnurritv/sexmod/b1.AWAIT_PICK_UP : Lcom/schnurritv/sexmod/b1;
    //   101: if_acmpeq -> 119
    //   104: aload #8
    //   106: getstatic com/schnurritv/sexmod/b1.VANISH : Lcom/schnurritv/sexmod/b1;
    //   109: if_acmpne -> 135
    //   112: goto -> 119
    //   115: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   118: athrow
    //   119: aload_0
    //   120: aload_1
    //   121: aload #6
    //   123: aload #7
    //   125: invokevirtual a : (Lcom/schnurritv/sexmod/ag;Lsoftware/bernie/geckolib3/core/processor/IBone;Lsoftware/bernie/geckolib3/core/processor/IBone;)V
    //   128: goto -> 135
    //   131: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   134: athrow
    //   135: aload #8
    //   137: getstatic com/schnurritv/sexmod/b1.SIT : Lcom/schnurritv/sexmod/b1;
    //   140: if_acmpne -> 157
    //   143: aload_0
    //   144: aload_1
    //   145: aload #7
    //   147: invokevirtual a : (Lcom/schnurritv/sexmod/ag;Lsoftware/bernie/geckolib3/core/processor/IBone;)V
    //   150: goto -> 157
    //   153: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   156: athrow
    //   157: aload #8
    //   159: getstatic com/schnurritv/sexmod/b1.BREEDING_SLOW_2 : Lcom/schnurritv/sexmod/b1;
    //   162: if_acmpeq -> 195
    //   165: aload #8
    //   167: getstatic com/schnurritv/sexmod/b1.BREEDING_FAST_2 : Lcom/schnurritv/sexmod/b1;
    //   170: if_acmpeq -> 195
    //   173: goto -> 180
    //   176: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   179: athrow
    //   180: aload #8
    //   182: getstatic com/schnurritv/sexmod/b1.BREEDING_CUM_2 : Lcom/schnurritv/sexmod/b1;
    //   185: if_acmpne -> 239
    //   188: goto -> 195
    //   191: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   194: athrow
    //   195: aload_0
    //   196: getfield c : Lnet/minecraft/client/Minecraft;
    //   199: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   202: getfield field_74320_O : I
    //   205: ifne -> 239
    //   208: goto -> 215
    //   211: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   214: athrow
    //   215: aload #6
    //   217: aload #6
    //   219: invokeinterface getPositionY : ()F
    //   224: ldc 1.5
    //   226: fadd
    //   227: invokeinterface setPositionY : (F)V
    //   232: goto -> 239
    //   235: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   238: athrow
    //   239: aload #8
    //   241: getstatic com/schnurritv/sexmod/b1.START_THROWING : Lcom/schnurritv/sexmod/b1;
    //   244: if_acmpne -> 302
    //   247: aload_0
    //   248: getfield c : Lnet/minecraft/client/Minecraft;
    //   251: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   254: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   257: aload_1
    //   258: invokevirtual r : ()Ljava/util/UUID;
    //   261: invokevirtual equals : (Ljava/lang/Object;)Z
    //   264: ifeq -> 290
    //   267: goto -> 274
    //   270: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   273: athrow
    //   274: aload_0
    //   275: aload #6
    //   277: aload #4
    //   279: aload_1
    //   280: invokevirtual b : (Lsoftware/bernie/geckolib3/core/processor/IBone;Lsoftware/bernie/geckolib3/core/processor/AnimationProcessor;Lcom/schnurritv/sexmod/ag;)V
    //   283: goto -> 310
    //   286: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   289: athrow
    //   290: aload_0
    //   291: aload #6
    //   293: aload #4
    //   295: aload_1
    //   296: invokevirtual a : (Lsoftware/bernie/geckolib3/core/processor/IBone;Lsoftware/bernie/geckolib3/core/processor/AnimationProcessor;Lcom/schnurritv/sexmod/ag;)V
    //   299: goto -> 310
    //   302: aload #6
    //   304: iconst_0
    //   305: invokeinterface setHidden : (Z)V
    //   310: aload #6
    //   312: invokeinterface isHidden : ()Z
    //   317: ifne -> 335
    //   320: aload #8
    //   322: getstatic com/schnurritv/sexmod/b1.START_THROWING : Lcom/schnurritv/sexmod/b1;
    //   325: if_acmpeq -> 350
    //   328: goto -> 335
    //   331: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   334: athrow
    //   335: aload #8
    //   337: getstatic com/schnurritv/sexmod/b1.THROWN : Lcom/schnurritv/sexmod/b1;
    //   340: if_acmpne -> 699
    //   343: goto -> 350
    //   346: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   349: athrow
    //   350: aload_1
    //   351: invokevirtual func_174791_d : ()Lnet/minecraft/util/math/Vec3d;
    //   354: aload_1
    //   355: getfield field_70142_S : D
    //   358: aload_1
    //   359: getfield field_70137_T : D
    //   362: aload_1
    //   363: getfield field_70136_U : D
    //   366: invokevirtual func_178786_a : (DDD)Lnet/minecraft/util/math/Vec3d;
    //   369: astore #9
    //   371: new net/minecraft/util/math/Vec3d
    //   374: dup
    //   375: aload #9
    //   377: getfield field_72450_a : D
    //   380: invokestatic abs : (D)D
    //   383: aload #9
    //   385: getfield field_72448_b : D
    //   388: invokestatic abs : (D)D
    //   391: aload #9
    //   393: getfield field_72449_c : D
    //   396: invokestatic abs : (D)D
    //   399: invokespecial <init> : (DDD)V
    //   402: astore #10
    //   404: aload #10
    //   406: getfield field_72450_a : D
    //   409: aload #10
    //   411: getfield field_72450_a : D
    //   414: aload #10
    //   416: getfield field_72448_b : D
    //   419: dadd
    //   420: aload #10
    //   422: getfield field_72449_c : D
    //   425: dadd
    //   426: ddiv
    //   427: dstore #11
    //   429: aload #10
    //   431: getfield field_72448_b : D
    //   434: aload #10
    //   436: getfield field_72450_a : D
    //   439: aload #10
    //   441: getfield field_72448_b : D
    //   444: dadd
    //   445: aload #10
    //   447: getfield field_72449_c : D
    //   450: dadd
    //   451: ddiv
    //   452: dstore #13
    //   454: aload #10
    //   456: getfield field_72449_c : D
    //   459: aload #10
    //   461: getfield field_72450_a : D
    //   464: aload #10
    //   466: getfield field_72448_b : D
    //   469: dadd
    //   470: aload #10
    //   472: getfield field_72449_c : D
    //   475: dadd
    //   476: ddiv
    //   477: dstore #15
    //   479: new net/minecraft/util/math/Vec3d
    //   482: dup
    //   483: aload #9
    //   485: getfield field_72450_a : D
    //   488: dconst_0
    //   489: dcmpl
    //   490: ifle -> 501
    //   493: iconst_1
    //   494: goto -> 502
    //   497: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   500: athrow
    //   501: iconst_m1
    //   502: i2d
    //   503: dload #11
    //   505: dmul
    //   506: aload #9
    //   508: getfield field_72448_b : D
    //   511: dconst_0
    //   512: dcmpl
    //   513: ifle -> 520
    //   516: iconst_1
    //   517: goto -> 521
    //   520: iconst_m1
    //   521: i2d
    //   522: dload #13
    //   524: dmul
    //   525: aload #9
    //   527: getfield field_72449_c : D
    //   530: dconst_0
    //   531: dcmpl
    //   532: ifle -> 539
    //   535: iconst_1
    //   536: goto -> 540
    //   539: iconst_m1
    //   540: i2d
    //   541: dload #15
    //   543: dmul
    //   544: invokespecial <init> : (DDD)V
    //   547: astore #17
    //   549: aload #17
    //   551: getfield field_72448_b : D
    //   554: ldc2_w 2.0
    //   557: ddiv
    //   558: ldc2_w 0.5
    //   561: dadd
    //   562: dstore #18
    //   564: ldc2_w -180.0
    //   567: dconst_0
    //   568: dload #18
    //   570: invokestatic b : (DDD)D
    //   573: d2f
    //   574: fstore #20
    //   576: fload #20
    //   578: invokestatic isNaN : (F)Z
    //   581: ifeq -> 588
    //   584: ldc -90.0
    //   586: fstore #20
    //   588: dload #18
    //   590: ldc2_w 0.5
    //   593: dcmpg
    //   594: ifge -> 605
    //   597: fconst_0
    //   598: goto -> 616
    //   601: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   604: athrow
    //   605: dconst_0
    //   606: ldc2_w 16.0
    //   609: dload #18
    //   611: dneg
    //   612: invokestatic b : (DDD)D
    //   615: d2f
    //   616: fstore #21
    //   618: fload #21
    //   620: invokestatic isNaN : (F)Z
    //   623: ifeq -> 629
    //   626: fconst_0
    //   627: fstore #21
    //   629: ldc2_w 4.0
    //   632: ldc2_w 1.5707963267948966
    //   635: dload #18
    //   637: ldc2_w 2.0
    //   640: dmul
    //   641: ldc2_w 3.141592653589793
    //   644: dmul
    //   645: dadd
    //   646: invokestatic sin : (D)D
    //   649: ldc2_w 4.0
    //   652: dmul
    //   653: dsub
    //   654: d2f
    //   655: fstore #22
    //   657: fload #22
    //   659: invokestatic isNaN : (F)Z
    //   662: ifeq -> 669
    //   665: ldc 8.0
    //   667: fstore #22
    //   669: aload #6
    //   671: fload #20
    //   673: invokestatic b : (F)F
    //   676: invokeinterface setRotationX : (F)V
    //   681: aload #6
    //   683: fload #21
    //   685: invokeinterface setPositionY : (F)V
    //   690: aload #6
    //   692: fload #22
    //   694: invokeinterface setPositionZ : (F)V
    //   699: aload #8
    //   701: getstatic com/schnurritv/sexmod/b1.START_THROWING : Lcom/schnurritv/sexmod/b1;
    //   704: if_acmpeq -> 722
    //   707: aload #8
    //   709: getstatic com/schnurritv/sexmod/b1.PICK_UP : Lcom/schnurritv/sexmod/b1;
    //   712: if_acmpne -> 736
    //   715: goto -> 722
    //   718: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   721: athrow
    //   722: aload_0
    //   723: aload #4
    //   725: aload_1
    //   726: invokevirtual a : (Lsoftware/bernie/geckolib3/core/processor/AnimationProcessor;Lcom/schnurritv/sexmod/ag;)V
    //   729: goto -> 736
    //   732: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   735: athrow
    //   736: return
    // Exception table:
    //   from	to	target	type
    //   0	18	18	java/lang/NullPointerException
    //   37	62	62	java/lang/NullPointerException
    //   96	112	115	java/lang/NullPointerException
    //   104	128	131	java/lang/NullPointerException
    //   135	150	153	java/lang/NullPointerException
    //   157	173	176	java/lang/NullPointerException
    //   165	188	191	java/lang/NullPointerException
    //   180	208	211	java/lang/NullPointerException
    //   195	232	235	java/lang/NullPointerException
    //   239	267	270	java/lang/NullPointerException
    //   247	286	286	java/lang/NullPointerException
    //   310	328	331	java/lang/NullPointerException
    //   320	343	346	java/lang/NullPointerException
    //   479	497	497	java/lang/NullPointerException
    //   588	601	601	java/lang/NullPointerException
    //   699	715	718	java/lang/NullPointerException
    //   707	729	732	java/lang/NullPointerException
  }
  
  void a(AnimationProcessor paramAnimationProcessor, ag paramag) {
    UUID uUID = paramag.r();
    try {
      if (uUID == null)
        paramag.B(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (uUID == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = paramag.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f1 = bZ.a(entityPlayer.field_184618_aE, entityPlayer.field_70721_aZ, this.c.func_184121_ak());
    float f2 = entityPlayer.field_184619_aG;
    float f3 = (float)Math.sin(f2);
    IBone iBone1 = paramAnimationProcessor.getBone("LeftLeg");
    IBone iBone2 = paramAnimationProcessor.getBone("RightLeg");
    float f4 = bZ.b(60.0F * f3 * f1);
    iBone1.setRotationX(f4);
    iBone2.setRotationX(-f4);
  }
  
  void a(ag paramag, IBone paramIBone) {
    EntityPlayer entityPlayer = paramag.field_70170_p.func_72890_a((Entity)paramag, 15.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d1 = entityPlayer.func_174791_d();
    Vec3d vec3d2 = paramag.func_174791_d();
    Vec3d vec3d3 = vec3d1.func_178788_d(vec3d2);
    float f1 = paramag.field_70177_z;
    boolean bool = false;
    try {
      switch ((int)f1) {
        case 0:
          try {
          
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          bool = (vec3d1.field_72449_c > vec3d2.field_72449_c) ? true : false;
          break;
        case 180:
          try {
          
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          bool = (vec3d1.field_72449_c < vec3d2.field_72449_c) ? true : false;
          break;
        case 90:
          try {
          
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          bool = (vec3d1.field_72450_a < vec3d2.field_72450_a) ? true : false;
          break;
        case -90:
          try {
          
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          bool = (vec3d1.field_72450_a > vec3d2.field_72450_a) ? true : false;
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!bool) {
        paramIBone.setRotationY(0.0F);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f2 = 0.0F;
    switch ((int)f1) {
      case 180:
        f2 = 90.0F;
        break;
      case 90:
        f2 = 180.0F;
        break;
      case 0:
        f2 = -90.0F;
        break;
    } 
    float f3 = (float)-(MathHelper.func_181159_b(vec3d3.field_72449_c, vec3d3.field_72450_a) * 57.29577951308232D + f2);
    float f4 = bZ.b((float)(entityPlayer.func_70047_e() + vec3d1.field_72448_b - paramag.func_70047_e() + vec3d2.field_72448_b), -0.75F, 0.75F);
    paramIBone.setRotationY(bZ.b(f3));
    paramIBone.setRotationX(f4);
  }
  
  void a(ag paramag, IBone paramIBone1, IBone paramIBone2) {
    EntityPlayer entityPlayer = paramag.field_70170_p.func_72890_a((Entity)paramag, 15.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d1 = entityPlayer.func_174791_d();
    Vec3d vec3d2 = paramag.func_174791_d();
    Vec3d vec3d3 = vec3d1.func_178788_d(vec3d2);
    float f1 = (float)-(Math.atan2(vec3d3.field_72449_c, vec3d3.field_72450_a) * 57.29577951308232D) + 90.0F;
    float f2 = bZ.b((float)(entityPlayer.func_70047_e() + vec3d1.field_72448_b - paramag.func_70047_e() + vec3d2.field_72448_b), -0.75F, 0.75F);
    paramIBone1.setRotationY(bZ.b(f1));
    paramIBone2.setRotationX(f2);
  }
  
  void a(IBone paramIBone, AnimationProcessor paramAnimationProcessor, ag paramag) {
    try {
      if (paramag.ag) {
        paramIBone.setHidden(true);
      } else {
        paramIBone.setHidden(false);
        paramAnimationProcessor.getBone("steve").setHidden(true);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void b(IBone paramIBone, AnimationProcessor paramAnimationProcessor, ag paramag) {
    try {
      if (paramag.ag) {
        paramIBone.setHidden(true);
      } else {
        try {
        
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        paramIBone.setHidden((paramag.ab < 15));
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!paramag.ag)
        paramAnimationProcessor.getBone("steve").setHidden(true); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\an.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */