package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class c2 extends cr {
  static final float C = 8.0F;
  
  static final float H = 1.68F;
  
  static final float F = 5.0F;
  
  static Collection<c2> I = new ArrayList<>();
  
  double B = 0.0D;
  
  double A = 0.0D;
  
  double E = 0.0D;
  
  double J = 0.0D;
  
  float D = 0.0F;
  
  float L = 0.0F;
  
  float y;
  
  float G;
  
  double z = 0.0D;
  
  double K = 0.0D;
  
  public c2(RenderManager paramRenderManager, AnimatedGeoModel paramAnimatedGeoModel) {
    super(paramRenderManager, paramAnimatedGeoModel);
    I.add(this);
  }
  
  protected void b() {
    GlStateManager.func_179109_b(0.0F, -1.1F, 0.0F);
    GlStateManager.func_179152_a(0.7F, 0.7F, 0.7F);
  }
  
  protected void a(String paramString, GeoBone paramGeoBone) {
    try {
      if (((Boolean)this.r.func_184212_Q().func_187225_a(Q.c)).booleanValue())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("tail".equals(paramString))
        a(paramGeoBone, 0.0F, 0.0F, 1.0F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("body".equals(paramString))
        a(paramGeoBone); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.r.h() == b1.BOW)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("armL".equals(paramString))
        a(paramGeoBone, 0.0F, -0.34906584F, 0.15F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.r.h() == b1.ATTACK)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("armR".equals(paramString))
        a(paramGeoBone, 0.0F, 0.34906584F, 0.15F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void a(GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3) {
    double d1 = this.B - this.E;
    double d2 = this.A - this.J;
    double d3 = 0.017453292519943295D * this.r.field_70177_z;
    Vec2f vec2f = new Vec2f((float)(d1 * Math.cos(d3) + d2 * Math.sin(d3)), (float)(-d1 * Math.sin(d3) + d2 * Math.cos(d3)));
    this.y = vec2f.field_189983_j * -8.0F;
    this.G = vec2f.field_189982_i * 8.0F;
    this.y = bZ.b(this.y, -1.68F, 1.68F);
    this.G = bZ.b(this.G, -1.68F, 1.68F);
    this.y = bZ.a(this.D, this.y, this.w);
    this.G = bZ.a(this.L, this.G, this.w);
    paramGeoBone.setRotationX(paramFloat1 + this.y * paramFloat3);
    paramGeoBone.setRotationZ(paramFloat2 + this.G * paramFloat3);
  }
  
  void a(GeoBone paramGeoBone) {
    double d1 = this.B - this.E;
    double d2 = this.A - this.J;
    try {
      this.K = (Math.abs(d1) + Math.abs(d2)) * 5.0D;
      this.K = bZ.b((float)this.K, 0.0F, 1.0F);
      paramGeoBone.setPositionY((float)bZ.c(5.0D, 0.0D, bZ.b(this.z, this.K, this.w)));
      if (this.r instanceof V)
        ((V)this.r).an = (float)bZ.c(0.30000001192092896D, 0.0D, bZ.b(this.z, this.K, this.w)); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void c() {
    try {
      if (this.r == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      this.D = this.y;
      this.L = this.G;
      this.z = this.K;
      if (this.r.u() == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = ((Q)this.k).field_70170_p.func_152378_a(this.r.u());
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.E = this.B;
    this.J = this.A;
    this.B = entityPlayer.field_70165_t;
    this.A = entityPlayer.field_70161_v;
  }
  
  public void a(Q paramQ, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    super.a(paramQ, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  @EventBusSubscriber
  public static class a {
    @SubscribeEvent
    public void a(TickEvent.ClientTickEvent param1ClientTickEvent) {
      for (c2 c2 : c2.I)
        c2.c(); 
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\c2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */