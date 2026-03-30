package com.schnurritv.sexmod;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class g extends AnimatedGeoModel<w> {
  ResourceLocation a = null;
  
  public ResourceLocation b(w paramw) {
    return new ResourceLocation("sexmod", "geo/allie/lamp.geo.json");
  }
  
  public ResourceLocation c(w paramw) {
    try {
      if (this.a != null)
        return this.a; 
    } catch (IOException iOException) {
      throw a(null);
    } 
    try {
      Minecraft minecraft = Minecraft.func_71410_x();
      BufferedImage bufferedImage = af.a(minecraft.field_71439_g.getPersistentID());
      Graphics graphics = bufferedImage.getGraphics();
      graphics.setColor(new Color(185, 254, 255));
      graphics.fillRect(0, 0, 2, 2);
      graphics.setColor(new Color(255, 255, 255));
      graphics.fillRect(2, 0, 1, 2);
      graphics.setColor(new Color(0, 0, 0));
      graphics.fillRect(3, 0, 1, 2);
      this.a = minecraft.field_71446_o.func_110578_a("alliesLamp", new DynamicTexture(bufferedImage));
    } catch (IOException iOException) {
      iOException.printStackTrace();
      this.a = new ResourceLocation("sexmod", "textures/entity/allie/lamp.png");
    } 
    return this.a;
  }
  
  public ResourceLocation a(w paramw) {
    return new ResourceLocation("sexmod", "animations/allie/lamp.animation.json");
  }
  
  private static IOException a(IOException paramIOException) {
    return paramIOException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\g.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */