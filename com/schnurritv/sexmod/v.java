package com.schnurritv.sexmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class v extends GuiScreen {
  EntityLivingBase[] b;
  
  int a = 0;
  
  static float c = 0.0F;
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    super.func_73863_a(paramInt1, paramInt2, paramFloat);
    this.field_146292_n.clear();
    if (this.b == null) {
      this.b = new EntityLivingBase[] { (EntityLivingBase)new aX((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new ae((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new T((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new aF((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new aO((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new R((World)this.field_146297_k.field_71441_e), (EntityLivingBase)new aI((World)this.field_146297_k.field_71441_e), (EntityLivingBase)this.field_146297_k.field_71439_g };
      byte b = 0;
      try {
        while (b < this.b.length - 1) {
          ((Q)this.b[b]).y = true;
          b++;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    a(this.field_146294_l / 2, this.field_146295_m / 2 + 20, 30, this.b[this.a]);
    this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 30, this.field_146295_m / 2 - 10, 20, 20, ">"));
    this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 50, this.field_146295_m / 2 - 10, 20, 20, "<"));
    this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 30, this.field_146295_m / 2 + 30, 60, 20, "pick"));
  }
  
  protected void func_146284_a(GuiButton paramGuiButton) {
    try {
      if (">".equals(paramGuiButton.field_146126_j))
        try {
          if (++this.a >= this.b.length)
            this.a = 0; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("<".equals(paramGuiButton.field_146126_j))
        try {
          if (--this.a < 0)
            this.a = this.b.length - 1; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    if (paramGuiButton.field_146127_k == 0) {
      bn.a.sendToServer(new a8(this.a));
      EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
      try {
        entityPlayerSP.func_71053_j();
        ((EntityPlayer)entityPlayerSP).eyeHeight = entityPlayerSP.getDefaultEyeHeight();
        if (!((EntityPlayer)entityPlayerSP).field_71075_bZ.field_75101_c)
          ((EntityPlayer)entityPlayerSP).field_71075_bZ.field_75101_c = ((EntityPlayer)entityPlayerSP).field_71075_bZ.field_75098_d; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  public static void a(int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase) {
    float f1 = paramEntityLivingBase.field_70761_aq;
    float f2 = paramEntityLivingBase.field_70177_z;
    float f3 = paramEntityLivingBase.field_70125_A;
    float f4 = paramEntityLivingBase.field_70758_at;
    float f5 = paramEntityLivingBase.field_70759_as;
    try {
      if (!(paramEntityLivingBase instanceof EntityPlayer)) {
        paramEntityLivingBase.field_70165_t = 0.0D;
        paramEntityLivingBase.field_70163_u = 0.0D;
        paramEntityLivingBase.field_70161_v = 0.0D;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    paramEntityLivingBase.field_70761_aq = 0.0F;
    paramEntityLivingBase.field_70177_z = 0.0F;
    paramEntityLivingBase.field_70125_A = 0.0F;
    paramEntityLivingBase.field_70758_at = 0.0F;
    paramEntityLivingBase.field_70759_as = 0.0F;
    float f6 = Minecraft.func_175610_ah();
    if (f6 == 0.0F)
      f6 = 0.1F; 
    c += 60.0F / f6;
    GlStateManager.func_179142_g();
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(paramInt1, paramInt2, 50.0F);
    GlStateManager.func_179152_a(-paramInt3, paramInt3, paramInt3);
    GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
    RenderHelper.func_74519_b();
    GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(c, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
    RenderManager renderManager = Minecraft.func_71410_x().func_175598_ae();
    renderManager.func_178631_a(180.0F);
    renderManager.func_178633_a(false);
    renderManager.func_188391_a((Entity)paramEntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.2345679F, false);
    renderManager.func_178633_a(true);
    GlStateManager.func_179121_F();
    RenderHelper.func_74518_a();
    GlStateManager.func_179101_C();
    GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
    GlStateManager.func_179090_x();
    GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    paramEntityLivingBase.field_70761_aq = f1;
    paramEntityLivingBase.field_70177_z = f2;
    paramEntityLivingBase.field_70125_A = f3;
    paramEntityLivingBase.field_70758_at = f4;
    paramEntityLivingBase.field_70759_as = f5;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\v.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */