package com.schnurritv.sexmod;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class bx extends GuiScreen {
  a1 c;
  
  EntityPlayer b;
  
  boolean a;
  
  static final ResourceLocation d = new ResourceLocation("sexmod", "textures/gui/girlinventory.png");
  
  double e = 0.0D;
  
  public bx(a1 parama1, EntityPlayer paramEntityPlayer) {
    this.c = parama1;
    this.b = paramEntityPlayer;
    this.a = !"".equals(parama1.func_184212_Q().func_187225_a(Q.p));
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    super.func_73863_a(paramInt1, paramInt2, paramFloat);
    this.field_146292_n.clear();
    ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
    int i = scaledResolution.func_78326_a();
    try {
      this.e = Math.min(1.0D, this.e + (this.field_146297_k.func_193989_ak() / 5.0F));
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.field_146292_n.add(new GuiButton(0, i / 2 - 119 + (int)(100.0D - 100.0D * this.e), 30, (int)(this.e * 100.0D), 20, this.a ? I18n.func_135052_a("action.names.stopfollowme", new Object[0]) : I18n.func_135052_a("action.names.followme", new Object[0])));
    this.field_146292_n.add(new GuiButton(1, i / 2 + 19, 30, (int)(this.e * 100.0D), 20, I18n.func_135052_a("action.names.gohome", new Object[0])));
    this.field_146297_k.field_71446_o.func_110577_a(d);
    func_73729_b(i / 2 - 7, 61 - (int)(15.0D - this.e * 15.0D), 32, 0, 15, 15);
    this.field_146292_n.add(new GuiButton(2, i / 2 - 10, 59 - (int)(15.0D - this.e * 15.0D), 20, 20, ""));
    func_73729_b(i / 2 - 20, 20, ((Boolean)this.c.func_184212_Q().func_187225_a(a1.F)).booleanValue() ? 0 : 40, 130, 40, 40);
  }
  
  protected void func_73864_a(int paramInt1, int paramInt2, int paramInt3) throws IOException {
    ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
    int i = scaledResolution.func_78326_a();
    try {
      if (((Boolean)this.c.func_184212_Q().func_187225_a(a1.F)).booleanValue())
        try {
          if (paramInt1 >= i / 2 - 20)
            try {
              if (paramInt1 <= i / 2 + 20)
                try {
                  if (paramInt2 >= 20)
                    try {
                      if (paramInt2 <= 60) {
                        bn.a.sendToServer(new I(this.c.p(), this.b.getPersistentID()));
                        func_146281_b();
                      } 
                    } catch (IOException iOException) {
                      throw a(null);
                    }  
                } catch (IOException iOException) {
                  throw a(null);
                }  
            } catch (IOException iOException) {
              throw a(null);
            }  
        } catch (IOException iOException) {
          throw a(null);
        }  
    } catch (IOException iOException) {
      throw a(null);
    } 
    super.func_73864_a(paramInt1, paramInt2, paramInt3);
  }
  
  protected void func_146284_a(GuiButton paramGuiButton) throws IOException {
    try {
      super.func_146284_a(paramGuiButton);
      if (paramGuiButton.field_146127_k == 0) {
        try {
          if (this.a) {
            bn.a.sendToServer(new b(this.c.p(), "master", ""));
            this.b.func_145747_a((ITextComponent)new TextComponentString(I18n.func_135052_a("bee.dialogue.sad", new Object[0])));
          } else {
            bn.a.sendToServer(new b(this.c.p(), "master", this.b.getPersistentID().toString()));
            this.b.func_145747_a((ITextComponent)new TextComponentString(I18n.func_135052_a("bee.dialogue.exited", new Object[0])));
          } 
        } catch (IOException iOException) {
          throw a(null);
        } 
        try {
        
        } catch (IOException iOException) {
          throw a(null);
        } 
        this.a = !this.a;
        this.b.func_71053_j();
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
    try {
      if (paramGuiButton.field_146127_k == 1) {
        bn.a.sendToServer(new l(this.c.p()));
        this.b.func_71053_j();
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
    try {
      if (paramGuiButton.field_146127_k == 2) {
        bn.a.sendToServer(new c9(this.c.p(), new Vec3d(this.c.field_70165_t, this.c.field_70163_u, this.c.field_70161_v)));
        this.b.func_71053_j();
        this.b.func_145747_a((ITextComponent)new TextComponentString(I18n.func_135052_a("bee.dialogue.home", new Object[0])));
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */