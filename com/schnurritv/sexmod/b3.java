package com.schnurritv.sexmod;

import java.awt.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class b3 extends GeoEntityRenderer<bM> {
  public static final Color b = new Color(223, 206, 155);
  
  bM a;
  
  public b3(RenderManager paramRenderManager, AnimatedGeoModel<bM> paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
  }
  
  public void a(GeoModel paramGeoModel, bM parambM, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    this.a = parambM;
    super.render(paramGeoModel, parambM, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public void renderRecursively(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    String str = paramGeoBone.getName();
    if ("shell".equals(str)) {
      paramFloat1 = b.getRed() / 255.0F;
      paramFloat2 = b.getGreen() / 255.0F;
      paramFloat3 = b.getBlue() / 255.0F;
    } 
    if ("colorSpots".equals(str)) {
      Vec3i vec3i = EyeAndKoboldColor.safeValueOf((String)this.a.func_184212_Q().func_187225_a(bM.e)).getMainColor();
      paramFloat1 = vec3i.func_177958_n() / 255.0F;
      paramFloat2 = vec3i.func_177956_o() / 255.0F;
      paramFloat3 = vec3i.func_177952_p() / 255.0F;
    } 
    super.renderRecursively(paramBufferBuilder, paramGeoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */