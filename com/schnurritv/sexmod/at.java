package com.schnurritv.sexmod;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class at extends Render<m> {
  static final double d = 0.1896224320030116D;
  
  static final double a = -0.5D;
  
  static final double b = 0.08742380916962415D;
  
  private static final ResourceLocation c = new ResourceLocation("textures/particle/particles.png");
  
  public at(RenderManager paramRenderManager) {
    super(paramRenderManager);
  }
  
  public void a(m paramm, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    aI aI = paramm.e();
    try {
      if (aI != null)
        try {
          if (!this.field_188301_f)
            try {
              if (aI.ag != 1.0F) {
                aI.W = paramm;
                ItemStack itemStack1 = (ItemStack)aI.func_184212_Q().func_187225_a(aI.as);
                if (!itemStack1.func_77973_b().equals(Items.field_190931_a)) {
                  float f1 = Minecraft.func_175610_ah();
                  if (f1 == 0.0F)
                    f1 = 0.1F; 
                  aI.ag += 60.0F / f1 * 0.01666F * 2.0F;
                  aI.ag = Math.min(1.0F, aI.ag);
                  EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
                  Vec3d vec3d1 = bZ.a(new Vec3d(((EntityPlayer)entityPlayerSP).field_70142_S, ((EntityPlayer)entityPlayerSP).field_70137_T, ((EntityPlayer)entityPlayerSP).field_70136_U), entityPlayerSP.func_174791_d(), paramFloat2);
                  Vec3d vec3d2 = new Vec3d(paramDouble1, paramDouble2, paramDouble3);
                  Vec3d vec3d3 = bZ.a(new Vec3d(aI.field_70142_S, aI.field_70137_T + 0.875D, aI.field_70136_U), aI.func_174791_d().func_72441_c(0.0D, 0.875D, 0.0D), paramFloat2);
                  vec3d3 = vec3d3.func_178788_d(vec3d1);
                  vec3d2 = bZ.a(vec3d2, vec3d3, aI.ag);
                  paramDouble1 = vec3d2.field_72450_a;
                  paramDouble2 = vec3d2.field_72448_b;
                  paramDouble3 = vec3d2.field_72449_c;
                } else {
                  aI.ag = 0.0F;
                } 
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)paramDouble1, (float)paramDouble2, (float)paramDouble3);
                GlStateManager.func_179091_B();
                GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
                func_180548_c(paramm);
                Tessellator tessellator = Tessellator.func_178181_a();
                BufferBuilder bufferBuilder = tessellator.func_178180_c();
                try {
                  GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  GlStateManager.func_179114_b(((this.field_76990_c.field_78733_k.field_74320_O == 2) ? -1 : true) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
                  if (this.field_188301_f) {
                    GlStateManager.func_179142_g();
                    GlStateManager.func_187431_e(func_188298_c(paramm));
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  if (!itemStack1.func_77973_b().equals(Items.field_190931_a)) {
                    GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
                    GlStateManager.func_179109_b(0.0F, -0.2F, 0.0F);
                    Minecraft.func_71410_x().func_175597_ag().func_178099_a((EntityLivingBase)aI, itemStack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
                    GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
                    GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  func_180548_c(paramm);
                  bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181710_j);
                  bufferBuilder.func_181662_b(-0.5D, -0.5D, 0.0D).func_187315_a(0.0625D, 0.1875D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
                  bufferBuilder.func_181662_b(0.5D, -0.5D, 0.0D).func_187315_a(0.125D, 0.1875D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
                  bufferBuilder.func_181662_b(0.5D, 0.5D, 0.0D).func_187315_a(0.125D, 0.125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
                  bufferBuilder.func_181662_b(-0.5D, 0.5D, 0.0D).func_187315_a(0.0625D, 0.125D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
                  tessellator.func_78381_a();
                  if (this.field_188301_f) {
                    GlStateManager.func_187417_n();
                    GlStateManager.func_179119_h();
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                try {
                  GlStateManager.func_179101_C();
                  GlStateManager.func_179121_F();
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                byte b = (aI.func_184591_cq() == EnumHandSide.RIGHT) ? 1 : -1;
                ItemStack itemStack2 = aI.func_184614_ca();
                if (!(itemStack2.func_77973_b() instanceof net.minecraft.item.ItemFishingRod))
                  b = -b; 
                aI.field_70177_z = aI.m().floatValue();
                aI.field_70761_aq = aI.m().floatValue();
                aI.field_70165_t = (aI.x()).field_72450_a;
                aI.field_70163_u = (aI.x()).field_72448_b;
                aI.field_70161_v = (aI.x()).field_72449_c;
                aI.field_70169_q = (aI.x()).field_72450_a;
                aI.field_70167_r = (aI.x()).field_72448_b;
                aI.field_70166_s = (aI.x()).field_72449_c;
                float f = (aI.field_70760_ar + (aI.field_70761_aq - aI.field_70760_ar) * paramFloat2) * 0.017453292F;
                double d1 = MathHelper.func_76126_a(f);
                double d2 = MathHelper.func_76134_b(f);
                double d3 = b * 0.35D;
                double d4 = aI.field_70169_q + (aI.field_70165_t - aI.field_70169_q) * paramFloat2 - d2 * d3 - d1 * 0.8D;
                double d5 = aI.field_70167_r + aI.func_70047_e() + (aI.field_70163_u - aI.field_70167_r) * paramFloat2 - 0.45D;
                double d6 = aI.field_70166_s + (aI.field_70161_v - aI.field_70166_s) * paramFloat2 - d1 * d3 + d2 * 0.8D;
                try {
                
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                double d7 = aI.func_70093_af() ? -0.1875D : 0.0D;
                double d8 = paramm.field_70169_q + (paramm.field_70165_t - paramm.field_70169_q) * paramFloat2 - Math.sin((aI.m().floatValue() + 90.0F) * 0.017453292519943295D) * 0.1896224320030116D - Math.sin(aI.m().floatValue() * 0.017453292519943295D) * 0.08742380916962415D;
                double d9 = paramm.field_70167_r + (paramm.field_70163_u - paramm.field_70167_r) * paramFloat2 + 0.25D + -0.5D;
                double d10 = paramm.field_70166_s + (paramm.field_70161_v - paramm.field_70166_s) * paramFloat2 + Math.cos((aI.m().floatValue() + 90.0F) * 0.017453292519943295D) * 0.1896224320030116D + Math.cos(aI.m().floatValue() * 0.017453292519943295D) * 0.08742380916962415D;
                double d11 = (float)(d4 - d8);
                double d12 = (float)(d5 - d9) + d7;
                double d13 = (float)(d6 - d10);
                GlStateManager.func_179090_x();
                GlStateManager.func_179140_f();
                if (itemStack1.func_77973_b().equals(Items.field_190931_a)) {
                  bufferBuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
                  for (byte b1 = 0; b1 <= 16; b1++) {
                    float f1 = b1 / 16.0F;
                    bufferBuilder.func_181662_b(paramDouble1 + d11 * f1, paramDouble2 + d12 * (f1 * f1 + f1) * 0.5D + 0.25D, paramDouble3 + d13 * f1).func_181669_b(0, 0, 0, 255).func_181675_d();
                  } 
                  tessellator.func_78381_a();
                } 
                GlStateManager.func_179145_e();
                GlStateManager.func_179098_w();
                super.func_76986_a(paramm, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
                return;
              } 
              return;
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @Nullable
  protected ResourceLocation a(m paramm) {
    return c;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\at.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */