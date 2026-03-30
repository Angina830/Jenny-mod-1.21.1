package com.schnurritv.sexmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class a5 extends GuiScreen {
  private static boolean a = false;
  
  private static double e = 0.0D;
  
  static ResourceLocation b = new ResourceLocation("sexmod", "textures/gui/transitionscreen.png");
  
  static ResourceLocation d = new ResourceLocation("sexmod", "textures/gui/mirroredtransitionscreen.png");
  
  static ResourceLocation c = new ResourceLocation("sexmod", "textures/gui/blackscreen.png");
  
  public static boolean b() {
    return a;
  }
  
  public static void a() {
    a = true;
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  @SubscribeEvent
  public void a(RenderGameOverlayEvent paramRenderGameOverlayEvent) {
    float f;
    try {
      if (!a)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramRenderGameOverlayEvent.getType() != RenderGameOverlayEvent.ElementType.TEXT)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Minecraft minecraft = Minecraft.func_71410_x();
    e += (minecraft.func_193989_ak() * 0.75F);
    int i = minecraft.field_71474_y.field_74335_Z;
    if (i == 1) {
      f = (float)bZ.b(-1800.0D, 1000.0D, 0.5D * Math.cos(e / 25.0D) + 0.5D);
    } else if (i == 2) {
      f = (float)bZ.b(-900.0D, 750.0D, 0.5D * Math.cos(e / 25.0D) + 0.5D);
    } else {
      f = (float)bZ.b(-900.0D, 600.0D, 0.5D * Math.cos(e / 25.0D) + 0.5D);
    } 
    try {
      GlStateManager.func_179094_E();
      if (i == 1)
        GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (i == 2)
        GlStateManager.func_179139_a(1.5D, 1.5D, 1.5D); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      minecraft.field_71446_o.func_110577_a(b);
      func_175174_a(f, 0.0F, 0, (int)(e * 1.5D), 256, 256);
      func_175174_a(f, 256.0F, 0, (int)(e * 1.5D), 256, 256);
      func_175174_a(f, 512.0F, 0, (int)(e * 1.5D), 256, 256);
      minecraft.field_71446_o.func_110577_a(d);
      func_175174_a(f + 600.0F, 0.0F, 0, (int)(e * 1.5D), 256, 256);
      func_175174_a(f + 600.0F, 256.0F, 0, (int)(e * 1.5D), 256, 256);
      func_175174_a(f + 600.0F, 512.0F, 0, (int)(e * 1.5D), 256, 256);
      minecraft.field_71446_o.func_110577_a(c);
      func_175174_a(f + 200.0F, 0.0F, 0, 0, 400, 256);
      func_175174_a(f + 200.0F, 256.0F, 0, 0, 400, 256);
      func_175174_a(f + 200.0F, 512.0F, 0, 0, 400, 256);
      if (e > 30.0D)
        aC.d(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (e > 69.0D) {
        e = 0.0D;
        a = false;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    GlStateManager.func_179121_F();
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */