package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class bv extends GeoItemRenderer<z> {
  private static final ResourceLocation e = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
  
  private final E a = new E();
  
  static final float d = 10.0F;
  
  static final float r = 1.5F;
  
  static final float l = 0.175F;
  
  static final float b = 0.1F;
  
  static final float i = 0.04F;
  
  static final float p = 8.0F;
  
  static final float f = 6.0F;
  
  static final float q = 1.3F;
  
  static final Vector2f[] o = new Vector2f[] { new Vector2f(1.0F, 0.0F), new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(0.5F, 0.5F), new Vector2f(0.75F, 0.25F), new Vector2f(0.25F, 0.75F), new Vector2f(0.25F, 0.75F) };
  
  static boolean k = false;
  
  Minecraft n = Minecraft.func_71410_x();
  
  Vector2f g;
  
  double h = 0.0D;
  
  EntityPlayer c;
  
  ItemStack j;
  
  static HashMap<ItemStack, Vector3f> m = new HashMap<>();
  
  public bv() {
    super(new d());
  }
  
  public static boolean c() {
    return k;
  }
  
  public static void b() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    k = !k;
  }
  
  public void a(z paramz, ItemStack paramItemStack) {
    EntityPlayer entityPlayer = null;
    for (EntityPlayer entityPlayer1 : this.n.field_71441_e.field_73010_i) {
      if (entityPlayer1.field_71071_by.field_70462_a.contains(paramItemStack)) {
        entityPlayer = entityPlayer1;
        break;
      } 
      if (entityPlayer1.field_71071_by.field_184439_c.contains(paramItemStack)) {
        entityPlayer = entityPlayer1;
        break;
      } 
    } 
    if (entityPlayer != null) {
      double d1 = entityPlayer.field_70165_t - entityPlayer.field_70142_S;
      double d2 = entityPlayer.field_70161_v - entityPlayer.field_70136_U;
      double d3 = 0.017453292519943295D * entityPlayer.field_70177_z;
      this.g = new Vector2f((float)(d1 * Math.cos(d3) + d2 * Math.sin(d3)), (float)(-d1 * Math.sin(d3) + d2 * Math.cos(d3)));
    } else {
      this.g = new Vector2f(0.0F, 0.0F);
    } 
    try {
      if (!Minecraft.func_71410_x().func_147113_T())
        this.h = ((Minecraft.func_71410_x()).field_71439_g.field_70173_aa + this.n.func_184121_ak()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.j = paramItemStack;
    this.c = entityPlayer;
    super.render(paramz, paramItemStack);
  }
  
  public void renderRecursively(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if ("staff".equals(paramGeoBone.getName())) {
      GlStateManager.func_179094_E();
      Tessellator.func_178181_a().func_78381_a();
      bE.a(IGeoRenderer.MATRIX_STACK, paramGeoBone);
      GlStateManager.func_179137_b(0.0D, 1.5D + 0.001D * Math.sin(0.005D * this.h) + 0.001D, 0.0D);
      Vector3f vector3f = m.get(this.j);
      GlStateManager.func_179139_a(d(), d(), d());
      if (vector3f == null)
        vector3f = new Vector3f(0.0F, 0.0F, 0.0F); 
      try {
      
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      vector3f.add((Tuple3f)new Vector3f(this.g.x, (this.c == null) ? 0.0F : (float)(this.c.field_70163_u - this.c.field_70137_T), this.g.y));
      GlStateManager.func_179114_b(vector3f.z * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(vector3f.x * 10.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-vector3f.y * 10.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b((float)(this.h * 0.10000000149011612D), 1.0F, 1.0F, 1.0F);
      m.put(this.j, vector3f);
      this.n.func_110434_K().func_110577_a(e);
      this.a.func_78088_a((Entity)(Minecraft.func_71410_x()).field_71439_g, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
      if (this.c != null)
        a(); 
      this.n.func_110434_K().func_110577_a((new d()).a(null));
      paramBufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181712_l);
    } 
    super.renderRecursively(paramBufferBuilder, paramGeoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  void a() {
    ArrayList<Integer> arrayList = new ArrayList();
    ArrayList<Vec3d> arrayList1 = new ArrayList();
    for (Vector4d vector4d : aD.af) {
      arrayList.add(Integer.valueOf((int)vector4d.getW()));
      arrayList1.add(new Vec3d(vector4d.getX(), vector4d.getY(), vector4d.getZ()));
    } 
    try {
      if (arrayList.size() == 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (k) {
        a(arrayList, arrayList1);
      } else {
        a(arrayList);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void a(List<Integer> paramList, List<Vec3d> paramList1) {
    for (byte b = 0; b < paramList.size(); b++) {
      float f1 = bZ.a(this.c.field_70758_at, this.c.field_70759_as, this.n.func_184121_ak());
      float f2 = bZ.a(this.c.field_70127_C, this.c.field_70125_A, this.n.func_184121_ak());
      Vec3d vec3d1 = bZ.a(new Vec3d(this.c.field_70169_q, this.c.field_70167_r + this.c.func_70047_e(), this.c.field_70166_s), this.c.func_174791_d().func_72441_c(0.0D, this.c.func_70047_e(), 0.0D), this.n.func_184121_ak());
      Vec3d vec3d2 = vec3d1.func_178788_d(paramList1.get(b));
      vec3d2 = bZ.a(vec3d2, -f2, f1);
      double d1 = Math.abs(vec3d2.field_72450_a) + Math.abs(vec3d2.field_72449_c) + Math.abs(vec3d2.field_72448_b);
      double d2 = -vec3d2.field_72450_a / d1;
      double d3 = -vec3d2.field_72448_b / d1;
      double d4 = vec3d2.field_72449_c / d1;
      d2 = a(d2);
      d3 = a(d3);
      d4 = a(d4);
      d2 *= 1.2999999523162842D;
      d3 *= 1.2999999523162842D;
      d4 *= 1.2999999523162842D;
      a(((Integer)paramList.get(b)).intValue(), (float)d2, (float)d3, (float)d4);
    } 
  }
  
  void a(List<Integer> paramList) {
    float f1 = 1.0F / paramList.size();
    float f2 = 0.0F;
    for (byte b = 0; b < paramList.size(); b++) {
      f2 += f1;
      b(((Integer)paramList.get(b)).intValue(), 1.0F - f2, 0.0F + f2, (float)bZ.b(0.800000011920929D, 1.2000000476837158D, b / paramList.size()));
    } 
  }
  
  double a(double paramDouble) {
    return paramDouble * Math.sqrt(1.0D - paramDouble * paramDouble / 2.0D);
  }
  
  double d() {
    return 0.17499999701976776D + 0.025D * Math.sin(0.005D * this.h) + 0.025D;
  }
  
  void b(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
    a(new ItemStack(Blocks.field_150325_L, 1, paramInt), paramFloat1, paramFloat2, paramFloat3);
  }
  
  void a(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
    b(new ItemStack(Blocks.field_150325_L, 1, paramInt), paramFloat1, paramFloat2, paramFloat3);
  }
  
  void b(ItemStack paramItemStack, float paramFloat1, float paramFloat2, float paramFloat3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179137_b(0.0D, 1.5D + 0.001D * Math.sin(0.005D * this.h) + 0.001D, 0.0D);
    GlStateManager.func_179152_a(0.04F, 0.04F, 0.04F);
    GlStateManager.func_179109_b(paramFloat1 * 6.0F, paramFloat2 * 6.0F, paramFloat3 * 6.0F);
    this.n.func_175597_ag().func_178099_a((EntityLivingBase)(Minecraft.func_71410_x()).field_71439_g, paramItemStack, ItemCameraTransforms.TransformType.NONE);
    GlStateManager.func_179121_F();
  }
  
  void a(ItemStack paramItemStack, float paramFloat1, float paramFloat2, float paramFloat3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179137_b(0.0D, 1.5D + 0.001D * Math.sin(0.005D * this.h) + 0.001D, 0.0D);
    GlStateManager.func_179152_a(0.04F, 0.04F, 0.04F);
    GlStateManager.func_179114_b((float)(this.h * 8.0D * paramFloat3), 0.0F, paramFloat1, paramFloat2);
    GlStateManager.func_179109_b(6.0F, 0.0F, 0.0F);
    this.n.func_175597_ag().func_178099_a((EntityLivingBase)(Minecraft.func_71410_x()).field_71439_g, paramItemStack, ItemCameraTransforms.TransformType.NONE);
    GlStateManager.func_179121_F();
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */