package com.schnurritv.sexmod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class cg extends cr {
  public cg(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  protected void d() {
    float f = ((Float)this.k.func_184212_Q().func_187225_a(Z.am)).floatValue();
    GlStateManager.func_179152_a(1.0F - f, 1.0F - f, 1.0F - f);
  }
  
  protected void a() {
    float f = ((Float)this.k.func_184212_Q().func_187225_a(Z.am)).floatValue();
    double d = 1.0D / (1.0D - f);
    GlStateManager.func_179139_a(d, d, d);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\cg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */