package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class aw extends aM {
  static final float d = 1.2F;
  
  static final float c = 1.0F;
  
  protected ResourceLocation[] b() {
    return new ResourceLocation[] { new ResourceLocation("sexmod", "geo/kobold/kobold.geo.json"), new ResourceLocation("sexmod", "geo/kobold/kobold.geo.json") };
  }
  
  public ResourceLocation c() {
    return new ResourceLocation("sexmod", "textures/entity/kobold/kobold.png");
  }
  
  public ResourceLocation a() {
    return new ResourceLocation("sexmod", "animations/kobold/kobold.animation.json");
  }
  
  public void a(Q paramQ, Integer paramInteger, AnimationEvent paramAnimationEvent) {
    try {
      super.a(paramQ, paramInteger, paramAnimationEvent);
      if (paramQ.field_70170_p instanceof com.c)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    AnimationProcessor animationProcessor = getAnimationProcessor();
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      animationProcessor.getBone("crown").setHidden(!((Boolean)paramQ.func_184212_Q().func_187225_a(aD.ak)).booleanValue());
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    animationProcessor.getBone("egg").setHidden(!((aD)paramQ).aM);
    String[] arrayOfString = aJ.a(paramQ);
    try {
      d(animationProcessor, arrayOfString[0]);
      b(animationProcessor, arrayOfString[1]);
      a(animationProcessor, arrayOfString[2], 0.75F, 1.35F, new String[] { "boobL", "boobR" });
      a(animationProcessor, arrayOfString[3], 1.0F, 1.2F, new String[] { "eyeL", "eyeR" });
      a(animationProcessor, arrayOfString[3], 1.0F, 1.2F);
      a(animationProcessor, arrayOfString[4]);
      f(animationProcessor, arrayOfString[5]);
      c(animationProcessor, arrayOfString[6]);
      switch (a.a[paramQ.h().ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
          animationProcessor.getBone("tounge").setHidden(false);
          break;
        default:
          animationProcessor.getBone("tounge").setHidden(true);
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    b(paramQ, animationProcessor);
  }
  
  void b(Q paramQ, AnimationProcessor paramAnimationProcessor) {
    IBone iBone;
    try {
      if (paramQ.d.getAnimationState() != AnimationState.Transitioning)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    switch (a.a[paramQ.h().ordinal()]) {
      case 2:
      case 3:
      case 4:
        iBone = paramAnimationProcessor.getBone("body");
        iBone.setPositionZ(11.43F + ((Float)paramQ.func_184212_Q().func_187225_a(aD.aP)).floatValue() * -7.0F);
        return;
      case 5:
      case 6:
      case 7:
      case 8:
        iBone = paramAnimationProcessor.getBone("body");
        iBone.setPositionX(1.78F + ((Float)paramQ.func_184212_Q().func_187225_a(aD.aP)).floatValue() * -1.5F);
        iBone.setPositionY(13.07F + ((Float)paramQ.func_184212_Q().func_187225_a(aD.aP)).floatValue() * -11.0F);
        iBone.setPositionZ(2.05F + ((Float)paramQ.func_184212_Q().func_187225_a(aD.aP)).floatValue() * -8.0F);
        return;
      case 9:
      case 10:
      case 11:
      case 12:
        iBone = paramAnimationProcessor.getBone("body");
        iBone.setPositionX(0.0F);
        iBone.setPositionY(2.85F);
        iBone.setPositionZ(-7.0F + ((Float)paramQ.func_184212_Q().func_187225_a(aD.aP)).floatValue() * 4.7F);
        return;
    } 
  }
  
  void c(AnimationProcessor paramAnimationProcessor, String paramString) {
    int i = Integer.parseInt(paramString);
    IBone iBone1 = paramAnimationProcessor.getBone("backpack");
    IBone iBone2 = paramAnimationProcessor.getBone("tailpack");
    try {
      if (i == 0) {
        iBone1.setHidden(false);
        iBone2.setHidden(true);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void f(AnimationProcessor paramAnimationProcessor, String paramString) {
    int i = Integer.parseInt(paramString);
    IBone iBone1 = paramAnimationProcessor.getBone("frecklesHR1");
    IBone iBone2 = paramAnimationProcessor.getBone("frecklesHR2");
    IBone iBone3 = paramAnimationProcessor.getBone("frecklesHL1");
    IBone iBone4 = paramAnimationProcessor.getBone("frecklesHL2");
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone3.setHidden((i != 1));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone1.setHidden((i != 1));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone4.setHidden((i != 2));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    iBone2.setHidden((i != 2));
  }
  
  void a(AnimationProcessor paramAnimationProcessor, String paramString) {
    int i = Integer.parseInt(paramString);
    IBone iBone1 = paramAnimationProcessor.getBone("frecklesAR1");
    IBone iBone2 = paramAnimationProcessor.getBone("frecklesAR2");
    IBone iBone3 = paramAnimationProcessor.getBone("frecklesAL1");
    IBone iBone4 = paramAnimationProcessor.getBone("frecklesAL2");
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone3.setHidden((i != 1));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone1.setHidden((i != 1));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      iBone4.setHidden((i != 2));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    iBone2.setHidden((i != 2));
  }
  
  void a(AnimationProcessor paramAnimationProcessor, String paramString, float paramFloat1, float paramFloat2) {
    try {
      if (Minecraft.func_71410_x().func_147113_T())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f = Float.parseFloat(paramString);
    f /= 100.0F;
    f = paramFloat1 + (paramFloat2 - paramFloat1) * f - 1.0F;
    IBone iBone1 = paramAnimationProcessor.getBone("eyeL");
    iBone1.setPositionX(iBone1.getPositionX() + f);
    IBone iBone2 = paramAnimationProcessor.getBone("eyeR");
    iBone2.setPositionX(iBone2.getPositionX() - f);
  }
  
  void a(AnimationProcessor paramAnimationProcessor, String paramString, float paramFloat1, float paramFloat2, String... paramVarArgs) {
    float f = Float.parseFloat(paramString);
    f /= 100.0F;
    f = paramFloat1 + (paramFloat2 - paramFloat1) * f;
    for (String str : paramVarArgs) {
      IBone iBone = paramAnimationProcessor.getBone(str);
      iBone.setScaleX(f);
      iBone.setScaleY(f);
      iBone.setScaleZ(f);
    } 
  }
  
  void b(AnimationProcessor paramAnimationProcessor, String paramString) {
    List<IBone> list1 = e(paramAnimationProcessor, "hornDL");
    List<IBone> list2 = e(paramAnimationProcessor, "hornDR");
    a(list1);
    a(list2);
    int i = (new Integer(paramString)).intValue();
    paramAnimationProcessor.getBone("hornDL" + i).setHidden(false);
    paramAnimationProcessor.getBone("hornDR" + i).setHidden(false);
  }
  
  void d(AnimationProcessor paramAnimationProcessor, String paramString) {
    List<IBone> list1 = e(paramAnimationProcessor, "hornUL");
    List<IBone> list2 = e(paramAnimationProcessor, "hornUR");
    a(list1);
    a(list2);
    int i = (new Integer(paramString)).intValue();
    paramAnimationProcessor.getBone("hornUL" + i).setHidden(false);
    paramAnimationProcessor.getBone("hornUR" + i).setHidden(false);
  }
  
  List<IBone> e(AnimationProcessor paramAnimationProcessor, String paramString) {
    ArrayList<IBone> arrayList = new ArrayList();
    for (byte b = 0;; b++) {
      IBone iBone = paramAnimationProcessor.getBone(paramString + b);
      try {
        if (iBone == null)
          return arrayList; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      arrayList.add(iBone);
    } 
  }
  
  void a(List<IBone> paramList) {
    for (IBone iBone : paramList)
      iBone.setHidden(true); 
  }
  
  protected void a(Q paramQ, AnimationProcessor paramAnimationProcessor, AnimationEvent paramAnimationEvent) {
    try {
      if (paramQ.field_70170_p instanceof com.c)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      switch (a.a[paramQ.h().ordinal()]) {
        case 13:
          try {
            if (Math.abs(paramQ.field_70169_q - paramQ.field_70165_t) + Math.abs(paramQ.field_70166_s - paramQ.field_70161_v) < 0.0D)
              break; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (paramQ.field_70122_E)
              try {
                if (Math.abs(Math.abs(paramQ.field_70167_r) - Math.abs(paramQ.field_70163_u)) > 0.10000000149011612D)
                  break; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              }  
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (!((aD)paramQ).p())
              break; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
        default:
          return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityModelData entityModelData = paramAnimationEvent.getExtraDataOfType(EntityModelData.class).get(0);
    IBone iBone1 = paramAnimationProcessor.getBone("head");
    try {
      iBone1.setRotationY(entityModelData.netHeadYaw * 0.017453292F);
      iBone1.setRotationX(entityModelData.headPitch * 0.017453292F);
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    IBone iBone2 = (paramAnimationProcessor.getBone("body") == null) ? paramAnimationProcessor.getBone("dd") : paramAnimationProcessor.getBone("body");
    iBone2.setRotationY(0.0F);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\aw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */