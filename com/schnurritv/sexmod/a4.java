package com.schnurritv.sexmod;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class a4 extends ModelBase implements b0 {
  private final ModelRenderer a = new ModelRenderer(this);
  
  public a4() {
    this.a.func_78793_a(-5.0F, 2.5F, 0.0F);
    this.a.field_78804_l.add(new ModelBox(this.a, 0, 0, -2.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F, false));
  }
  
  public void func_78088_a(Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    this.a.func_78785_a(paramFloat6);
  }
  
  public void a(ModelRenderer paramModelRenderer, float paramFloat1, float paramFloat2, float paramFloat3) {
    paramModelRenderer.field_78795_f = paramFloat1;
    paramModelRenderer.field_78796_g = paramFloat2;
    paramModelRenderer.field_78808_h = paramFloat3;
  }
  
  public ModelRenderer a() {
    return this.a;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */