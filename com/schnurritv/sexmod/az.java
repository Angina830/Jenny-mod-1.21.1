package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class az extends GuiContainer {
  static final ResourceLocation a = new ResourceLocation("sexmod", "textures/gui/girlinventory.png");
  
  UUID c;
  
  Q d;
  
  UUID b;
  
  public az(Q paramQ, InventoryPlayer paramInventoryPlayer, UUID paramUUID) {
    super(new bG(paramQ, paramInventoryPlayer, paramUUID));
    this.c = paramUUID;
    this.d = paramQ;
    this.b = paramInventoryPlayer.field_70458_d.getPersistentID();
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    func_146276_q_();
    super.func_73863_a(paramInt1, paramInt2, paramFloat);
    func_191948_b(paramInt1, paramInt2);
  }
  
  public void func_146281_b() {
    super.func_146281_b();
    for (bG bG : bG.d) {
      if (bG.c.equals(this.c)) {
        ItemStack[] arrayOfItemStack = new ItemStack[42];
        (Minecraft.func_71410_x()).field_71439_g.field_71071_by.field_70462_a.toArray((Object[])arrayOfItemStack);
        arrayOfItemStack[36] = bG.func_75139_a(0).func_75211_c();
        arrayOfItemStack[37] = bG.func_75139_a(1).func_75211_c();
        arrayOfItemStack[38] = bG.func_75139_a(2).func_75211_c();
        arrayOfItemStack[39] = bG.func_75139_a(3).func_75211_c();
        arrayOfItemStack[40] = bG.func_75139_a(4).func_75211_c();
        arrayOfItemStack[41] = bG.func_75139_a(5).func_75211_c();
        bn.a.sendToServer(new ao(this.d.p(), this.b, arrayOfItemStack));
      } 
    } 
  }
  
  protected void func_146976_a(float paramFloat, int paramInt1, int paramInt2) {
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.field_146297_k.field_71446_o.func_110577_a(a);
    func_73729_b(this.field_146294_l / 2 - 88, this.field_146295_m / 2 - 7 - 24, 33, 16, 176, 114);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\az.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */