package com.schnurritv.sexmod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class bo extends RenderLiving<bk> {
  private static final ResourceLocation a = new ResourceLocation("textures/entity/slime/slime.png");
  
  public bo(RenderManager paramRenderManager) {
    super(paramRenderManager, new aS(), 0.25F);
    func_177094_a(new aU(this));
  }
  
  public void a(bk parambk, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    this.field_76989_e = 0.25F * parambk.g();
    super.func_76986_a(parambk, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
  }
  
  protected void a(bk parambk, float paramFloat) {
    float f1 = 0.999F;
    GlStateManager.func_179152_a(0.999F, 0.999F, 0.999F);
    float f2 = parambk.g();
    float f3 = (parambk.c + (parambk.f - parambk.c) * paramFloat) / (f2 * 0.5F + 1.0F);
    float f4 = 1.0F / (f3 + 1.0F);
    GlStateManager.func_179152_a(f4 * f2, 1.0F / f4 * f2, f4 * f2);
  }
  
  protected ResourceLocation a(bk parambk) {
    return a;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */