package com.schnurritv.sexmod;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;

public class ad extends aM {
  protected ResourceLocation[] b() {
    return new ResourceLocation[] { new ResourceLocation("sexmod", "geo/bee/bee.geo.json"), new ResourceLocation("sexmod", "geo/bee/armored.geo.json") };
  }
  
  public ResourceLocation c() {
    return new ResourceLocation("sexmod", "textures/entity/bee/bee.png");
  }
  
  public ResourceLocation a() {
    return new ResourceLocation("sexmod", "animations/bee/bee.animation.json");
  }
  
  public void a(Q paramQ, Integer paramInteger, AnimationEvent paramAnimationEvent) {
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
    //   30: ldc 'chest'
    //   32: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   35: astore #5
    //   37: aload #5
    //   39: ifnonnull -> 47
    //   42: return
    //   43: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   46: athrow
    //   47: aload #5
    //   49: aload_1
    //   50: getfield e : Lsoftware/bernie/geckolib3/core/controller/AnimationController;
    //   53: invokevirtual getCurrentAnimation : ()Lsoftware/bernie/geckolib3/core/builder/Animation;
    //   56: ifnull -> 84
    //   59: aload_1
    //   60: getfield e : Lsoftware/bernie/geckolib3/core/controller/AnimationController;
    //   63: invokevirtual getCurrentAnimation : ()Lsoftware/bernie/geckolib3/core/builder/Animation;
    //   66: getfield animationName : Ljava/lang/String;
    //   69: ldc 'chest'
    //   71: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   74: ifne -> 92
    //   77: goto -> 84
    //   80: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   83: athrow
    //   84: iconst_1
    //   85: goto -> 93
    //   88: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   91: athrow
    //   92: iconst_0
    //   93: invokeinterface setHidden : (Z)V
    //   98: return
    // Exception table:
    //   from	to	target	type
    //   0	18	18	java/lang/NullPointerException
    //   37	43	43	java/lang/NullPointerException
    //   47	77	80	java/lang/NullPointerException
    //   59	88	88	java/lang/NullPointerException
  }
  
  protected void a(Q paramQ, AnimationProcessor paramAnimationProcessor, AnimationEvent paramAnimationEvent) {
    // Byte code:
    //   0: aload_1
    //   1: getfield field_70170_p : Lnet/minecraft/world/World;
    //   4: instanceof com/c
    //   7: ifne -> 182
    //   10: aload_1
    //   11: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   14: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   17: if_acmpeq -> 61
    //   20: goto -> 27
    //   23: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   26: athrow
    //   27: aload_1
    //   28: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   31: getstatic com/schnurritv/sexmod/b1.ATTACK : Lcom/schnurritv/sexmod/b1;
    //   34: if_acmpeq -> 61
    //   37: goto -> 44
    //   40: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   43: athrow
    //   44: aload_1
    //   45: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   48: getstatic com/schnurritv/sexmod/b1.BOW : Lcom/schnurritv/sexmod/b1;
    //   51: if_acmpne -> 182
    //   54: goto -> 61
    //   57: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   60: athrow
    //   61: aload_3
    //   62: ldc software/bernie/geckolib3/model/provider/data/EntityModelData
    //   64: invokevirtual getExtraDataOfType : (Ljava/lang/Class;)Ljava/util/List;
    //   67: iconst_0
    //   68: invokeinterface get : (I)Ljava/lang/Object;
    //   73: checkcast software/bernie/geckolib3/model/provider/data/EntityModelData
    //   76: astore #4
    //   78: aload_2
    //   79: ldc 'neck'
    //   81: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   84: astore #5
    //   86: aload #5
    //   88: aload #4
    //   90: getfield netHeadYaw : F
    //   93: ldc 0.5
    //   95: fmul
    //   96: ldc 0.017453292
    //   98: fmul
    //   99: invokeinterface setRotationY : (F)V
    //   104: aload_2
    //   105: ldc 'head'
    //   107: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   110: astore #6
    //   112: aload #6
    //   114: aload #4
    //   116: getfield netHeadYaw : F
    //   119: ldc 0.017453292
    //   121: fmul
    //   122: invokeinterface setRotationY : (F)V
    //   127: aload #6
    //   129: fconst_1
    //   130: aload #4
    //   132: getfield headPitch : F
    //   135: ldc 0.017453292
    //   137: fmul
    //   138: fadd
    //   139: invokeinterface setRotationX : (F)V
    //   144: aload_2
    //   145: ldc 'body'
    //   147: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   150: ifnonnull -> 166
    //   153: aload_2
    //   154: ldc 'dd'
    //   156: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   159: goto -> 172
    //   162: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   165: athrow
    //   166: aload_2
    //   167: ldc 'body'
    //   169: invokevirtual getBone : (Ljava/lang/String;)Lsoftware/bernie/geckolib3/core/processor/IBone;
    //   172: astore #7
    //   174: aload #7
    //   176: fconst_0
    //   177: invokeinterface setRotationY : (F)V
    //   182: return
    // Exception table:
    //   from	to	target	type
    //   0	20	23	java/lang/NullPointerException
    //   10	37	40	java/lang/NullPointerException
    //   27	54	57	java/lang/NullPointerException
    //   112	162	162	java/lang/NullPointerException
  }
  
  public String[] b() {
    return new String[] { "armorHelmet" };
  }
  
  public String[] e() {
    return new String[] { "band", "feeler", "feeler2", "brow", "brow2", "brow3", "brow4" };
  }
  
  public String[] c() {
    return new String[] { "armorShoulderR", "armorShoulderL", "armorChest", "armorBoobs" };
  }
  
  public String[] g() {
    return new String[] { "boobsFlesh", "upperBodyL", "upperBodyR" };
  }
  
  public String[] h() {
    return new String[] { "armorBootyR", "armorBootyL", "armorPantsLowL", "armorPantsLowR", "armorPantsLowR", "armorPantsUpR", "armorPantsUpL", "armorHip" };
  }
  
  public String[] d() {
    return new String[] { "sideL", "sideR", "fleshL", "fleshR", "vagina", "curvesL", "curvesR", "kneeL", "kneeR" };
  }
  
  public String[] a() {
    return new String[] { "armorShoesL", "armorShoesR" };
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */