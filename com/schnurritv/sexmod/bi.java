package com.schnurritv.sexmod;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class bi<T extends Q & IAnimatable> extends GeoEntityRenderer<T> {
  static final float c = 1.5F;
  
  protected double m;
  
  static boolean a = false;
  
  protected T k;
  
  protected Minecraft i;
  
  protected static HashMap<UUID, ResourceLocation> h = new HashMap<>();
  
  Color e = new Color(245, 199, 165);
  
  Color f = new Color(245, 157, 169);
  
  boolean g = false;
  
  protected HashSet<String> j = new HashSet<>();
  
  float d = 0.0F;
  
  public static BufferBuilder l;
  
  Matrix4f b = null;
  
  public bi(RenderManager paramRenderManager, AnimatedGeoModel<T> paramAnimatedGeoModel, double paramDouble) {
    super(paramRenderManager, paramAnimatedGeoModel);
    this.m = paramDouble;
    this.i = Minecraft.func_71410_x();
    this.field_76989_e = 0.2F;
  }
  
  protected ResourceLocation a(T paramT) throws IOException {
    ResourceLocation resourceLocation;
    try {
      if (((Q)paramT).field_70170_p instanceof com.c || paramT.B() == null) {
        resourceLocation = h.get(this.i.func_110432_I().func_148256_e().getId());
        try {
          if (resourceLocation == null)
            return a(this.i.func_110432_I().func_148256_e().getId(), ((Q)paramT).field_70170_p); 
        } catch (IOException iOException) {
          throw a(null);
        } 
      } else {
        resourceLocation = h.get(paramT.B());
        try {
          if (resourceLocation == null)
            return a(paramT.B(), ((Q)paramT).field_70170_p); 
        } catch (IOException iOException) {
          throw a(null);
        } 
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
    return resourceLocation;
  }
  
  protected ResourceLocation a(UUID paramUUID, World paramWorld) throws IOException {
    BufferedImage bufferedImage;
    try {
      bufferedImage = af.a(paramUUID);
      Graphics graphics = bufferedImage.getGraphics();
      graphics.setColor(this.e);
      graphics.fillRect(0, 0, 4, 3);
      graphics.setColor(this.f);
      graphics.fillRect(4, 0, 3, 3);
    } catch (Exception exception) {
      try {
        if (!this.g)
          this.g = true; 
      } catch (Exception exception1) {
        throw a(null);
      } 
      bufferedImage = ImageIO.read(this.i.func_110442_L().func_110536_a(new ResourceLocation("sexmod", "textures/player/steve.png")).func_110527_b());
    } 
    h.put(paramUUID, this.field_76990_c.field_78724_e.func_110578_a("player" + paramUUID, new DynamicTexture(bufferedImage)));
    return h.get(paramUUID);
  }
  
  protected void d() {}
  
  protected void a() {}
  
  float a(World paramWorld, Vec3d paramVec3d, float paramFloat1, float paramFloat2) {
    RayTraceResult rayTraceResult = a(paramVec3d, paramVec3d.func_178787_e(bZ.a(new Vec3d(0.0D, 0.0D, -4.0D), paramFloat1, paramFloat2)), paramWorld);
    try {
      if (rayTraceResult == null)
        return 4.0F; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    Vec3d vec3d = rayTraceResult.field_72307_f;
    try {
      if (vec3d == null)
        return 4.0F; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return (float)paramVec3d.func_72438_d(vec3d);
  }
  
  boolean a(T paramT, EntityPlayer paramEntityPlayer) {
    try {
      if (paramT instanceof U)
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    World world = ((Q)paramT).field_70170_p;
    Vec3d vec3d1 = paramT.func_174791_d();
    float f1 = ((Q)paramT).field_70130_N * 1.5F;
    float f2 = ((Q)paramT).field_70131_O * 1.5F;
    Vec3d vec3d2 = paramEntityPlayer.func_174791_d().func_72441_c(0.0D, paramEntityPlayer.func_70047_e(), 0.0D);
    int i = this.i.field_71474_y.field_74320_O;
    try {
      if (i != 0)
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    if (i > 0) {
      float f3 = paramEntityPlayer.field_70177_z;
      float f4 = paramEntityPlayer.field_70125_A;
      if (i == 2)
        f4 += 180.0F; 
      float f5 = 4.0F;
      Vec3d vec3d = vec3d2.func_72441_c((MathHelper.func_76126_a(f3 * 0.017453292F) * MathHelper.func_76134_b(f4 * 0.017453292F) * f5), (MathHelper.func_76126_a(f4 * 0.017453292F) * f5), (-MathHelper.func_76134_b(f3 * 0.017453292F) * MathHelper.func_76134_b(f4 * 0.017453292F) * f5));
      BlockPos blockPos = new BlockPos(vec3d);
      boolean bool = world.func_175623_d(blockPos);
      if (!bool) {
        vec3d2 = vec3d;
      } else if (world.func_175623_d(blockPos.func_177982_a(0, 1, 0))) {
        vec3d2 = new Vec3d(vec3d.field_72450_a, (blockPos.func_177956_o() + 1), vec3d.field_72449_c);
      } 
    } 
    Vec3d[] arrayOfVec3d = new Vec3d[8];
    arrayOfVec3d[0] = vec3d1.func_72441_c((-f1 / 2.0F), 0.0D, (-f1 / 2.0F));
    arrayOfVec3d[1] = vec3d1.func_72441_c((-f1 / 2.0F), 0.0D, (f1 / 2.0F));
    arrayOfVec3d[2] = vec3d1.func_72441_c((f1 / 2.0F), 0.0D, (-f1 / 2.0F));
    arrayOfVec3d[3] = vec3d1.func_72441_c((f1 / 2.0F), 0.0D, (f1 / 2.0F));
    arrayOfVec3d[4] = vec3d1.func_72441_c((-f1 / 2.0F), f2, (-f1 / 2.0F));
    arrayOfVec3d[5] = vec3d1.func_72441_c((-f1 / 2.0F), f2, (f1 / 2.0F));
    arrayOfVec3d[6] = vec3d1.func_72441_c((f1 / 2.0F), f2, (-f1 / 2.0F));
    arrayOfVec3d[7] = vec3d1.func_72441_c((f1 / 2.0F), f2, (f1 / 2.0F));
    for (Vec3d vec3d : arrayOfVec3d) {
      RayTraceResult rayTraceResult = a(vec3d2, vec3d, world);
      try {
        if (rayTraceResult == null)
          return true; 
      } catch (IllegalStateException illegalStateException) {
        throw a(null);
      } 
      IBlockState iBlockState = world.func_180495_p(rayTraceResult.func_178782_a());
      try {
        if (iBlockState.func_185895_e())
          return true; 
      } catch (IllegalStateException illegalStateException) {
        throw a(null);
      } 
      try {
        if (iBlockState.func_177230_c().func_180664_k() != BlockRenderLayer.SOLID)
          return true; 
      } catch (IllegalStateException illegalStateException) {
        throw a(null);
      } 
    } 
    return false;
  }
  
  HashSet<String> a(Boolean paramBoolean) {
    HashSet<String> hashSet1;
    if (paramBoolean.booleanValue()) {
      hashSet1 = u.c();
    } else {
      hashSet1 = this.k.I();
    } 
    HashSet<String> hashSet2 = new HashSet();
    for (String str : hashSet1) {
      b_.a a = b_.d(str);
      try {
        if (a == null)
          continue; 
      } catch (IllegalStateException illegalStateException) {
        throw a(null);
      } 
      hashSet2.addAll(a.g());
    } 
    return hashSet2;
  }
  
  public void a(GeoModel paramGeoModel, T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    try {
      if (this.i.field_71439_g != null)
        try {
          if (!((Q)paramT).y)
            try {
              if (!a(paramT, (EntityPlayer)this.i.field_71439_g))
                return; 
            } catch (IOException iOException) {
              throw a(null);
            }  
        } catch (IOException iOException) {
          throw a(null);
        }  
    } catch (IOException iOException) {
      throw a(null);
    } 
    GlStateManager.func_179091_B();
    a(paramT, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
    renderLate(paramT, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
    BufferBuilder bufferBuilder = Tessellator.func_178181_a().func_178180_c();
    bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181712_l);
    GeoBone geoBone = null;
    func_110776_a(Objects.<ResourceLocation>requireNonNull(getEntityTexture((EntityLivingBase)this.k)));
    this.j.clear();
    this.j = a(Boolean.valueOf(((Q)paramT).y));
    d();
    for (GeoBone geoBone1 : paramGeoModel.topLevelBones) {
      if (geoBone1.getName().equals("steve")) {
        geoBone = geoBone1;
        continue;
      } 
      renderRecursively(bufferBuilder, geoBone1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
    } 
    try {
      Tessellator.func_178181_a().func_78381_a();
      a();
      if (geoBone != null) {
        bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181712_l);
        try {
          (Minecraft.func_71410_x()).field_71446_o.func_110577_a(a(this.k));
        } catch (IOException iOException) {
          iOException.printStackTrace();
        } 
        renderRecursively(bufferBuilder, geoBone, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
        Tessellator.func_178181_a().func_78381_a();
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    renderAfter(paramT, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
    GlStateManager.func_179101_C();
    GlStateManager.func_179089_o();
  }
  
  protected void a(double paramDouble1, double paramDouble2, double paramDouble3) {
    try {
      if (((Q)this.k).y)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if ((this.k.h()).hideNameTag)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if ((this.i.func_175598_ae()).field_78734_h == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    func_147906_a((Entity)this.k, this.k.F(), paramDouble1, paramDouble2 + this.k.z(), paramDouble3, 300);
  }
  
  public void a(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: putfield k : Lcom/schnurritv/sexmod/Q;
    //   5: aload_0
    //   6: getfield k : Lcom/schnurritv/sexmod/Q;
    //   9: getfield field_70170_p : Lnet/minecraft/world/World;
    //   12: instanceof com/c
    //   15: ifne -> 577
    //   18: aload_0
    //   19: getfield k : Lcom/schnurritv/sexmod/Q;
    //   22: invokevirtual y : ()Z
    //   25: ifeq -> 88
    //   28: goto -> 35
    //   31: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   34: athrow
    //   35: aload_0
    //   36: getfield k : Lcom/schnurritv/sexmod/Q;
    //   39: instanceof com/schnurritv/sexmod/U
    //   42: ifeq -> 72
    //   45: goto -> 52
    //   48: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   51: athrow
    //   52: aload_0
    //   53: getfield i : Lnet/minecraft/client/Minecraft;
    //   56: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   59: getfield field_74320_O : I
    //   62: ifeq -> 88
    //   65: goto -> 72
    //   68: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   71: athrow
    //   72: aload_0
    //   73: dload_2
    //   74: dload #4
    //   76: dload #6
    //   78: invokevirtual a : (DDD)V
    //   81: goto -> 88
    //   84: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   87: athrow
    //   88: aload_1
    //   89: invokevirtual func_184212_Q : ()Lnet/minecraft/network/datasync/EntityDataManager;
    //   92: astore #10
    //   94: iconst_0
    //   95: istore #11
    //   97: aload #10
    //   99: getstatic com/schnurritv/sexmod/Q.p : Lnet/minecraft/network/datasync/DataParameter;
    //   102: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   105: checkcast java/lang/String
    //   108: ldc ''
    //   110: invokevirtual equals : (Ljava/lang/Object;)Z
    //   113: ifne -> 362
    //   116: aload_0
    //   117: getfield k : Lcom/schnurritv/sexmod/Q;
    //   120: getfield field_70170_p : Lnet/minecraft/world/World;
    //   123: aload #10
    //   125: getstatic com/schnurritv/sexmod/Q.p : Lnet/minecraft/network/datasync/DataParameter;
    //   128: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   131: checkcast java/lang/String
    //   134: invokestatic fromString : (Ljava/lang/String;)Ljava/util/UUID;
    //   137: invokevirtual func_152378_a : (Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;
    //   140: astore #12
    //   142: aload #12
    //   144: ifnull -> 362
    //   147: aload #12
    //   149: invokevirtual func_184218_aH : ()Z
    //   152: ifeq -> 362
    //   155: goto -> 162
    //   158: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   161: athrow
    //   162: aload #12
    //   164: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   167: instanceof net/minecraft/entity/passive/EntityHorse
    //   170: ifeq -> 362
    //   173: goto -> 180
    //   176: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   179: athrow
    //   180: aload #12
    //   182: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   185: checkcast net/minecraft/entity/passive/EntityHorse
    //   188: invokevirtual func_110257_ck : ()Z
    //   191: ifeq -> 362
    //   194: goto -> 201
    //   197: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   200: athrow
    //   201: aload #12
    //   203: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   206: checkcast net/minecraft/entity/EntityLiving
    //   209: astore #13
    //   211: aload_0
    //   212: getfield i : Lnet/minecraft/client/Minecraft;
    //   215: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   218: astore #14
    //   220: aload #13
    //   222: invokevirtual func_70040_Z : ()Lnet/minecraft/util/math/Vec3d;
    //   225: astore #15
    //   227: new net/minecraft/util/math/Vec3d
    //   230: dup
    //   231: aload #12
    //   233: getfield field_70142_S : D
    //   236: aload #12
    //   238: getfield field_70137_T : D
    //   241: aload #12
    //   243: getfield field_70136_U : D
    //   246: invokespecial <init> : (DDD)V
    //   249: aload #12
    //   251: invokevirtual func_174791_d : ()Lnet/minecraft/util/math/Vec3d;
    //   254: fload #9
    //   256: f2d
    //   257: invokestatic a : (Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/math/Vec3d;
    //   260: astore #16
    //   262: new net/minecraft/util/math/Vec3d
    //   265: dup
    //   266: aload #14
    //   268: getfield field_70142_S : D
    //   271: aload #14
    //   273: getfield field_70137_T : D
    //   276: aload #14
    //   278: getfield field_70136_U : D
    //   281: invokespecial <init> : (DDD)V
    //   284: aload #14
    //   286: invokevirtual func_174791_d : ()Lnet/minecraft/util/math/Vec3d;
    //   289: fload #9
    //   291: f2d
    //   292: invokestatic a : (Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/math/Vec3d;
    //   295: astore #17
    //   297: aload #16
    //   299: aload #17
    //   301: invokevirtual func_178788_d : (Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
    //   304: astore #17
    //   306: aload #17
    //   308: getfield field_72450_a : D
    //   311: aload #15
    //   313: getfield field_72450_a : D
    //   316: ldc2_w -0.5
    //   319: dmul
    //   320: dadd
    //   321: dstore_2
    //   322: aload #17
    //   324: getfield field_72448_b : D
    //   327: ldc2_w 0.15000000596046448
    //   330: dadd
    //   331: dstore #4
    //   333: aload #17
    //   335: getfield field_72449_c : D
    //   338: aload #15
    //   340: getfield field_72449_c : D
    //   343: ldc2_w -0.5
    //   346: dmul
    //   347: dadd
    //   348: dstore #6
    //   350: aload_1
    //   351: aload #13
    //   353: getfield field_70761_aq : F
    //   356: putfield field_70761_aq : F
    //   359: iconst_1
    //   360: istore #11
    //   362: iload #11
    //   364: ifne -> 577
    //   367: aload #10
    //   369: getstatic com/schnurritv/sexmod/Q.c : Lnet/minecraft/network/datasync/DataParameter;
    //   372: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   375: checkcast java/lang/Boolean
    //   378: invokevirtual booleanValue : ()Z
    //   381: ifeq -> 577
    //   384: goto -> 391
    //   387: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   390: athrow
    //   391: aload_1
    //   392: instanceof com/schnurritv/sexmod/U
    //   395: ifeq -> 442
    //   398: goto -> 405
    //   401: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   404: athrow
    //   405: aload_1
    //   406: checkcast com/schnurritv/sexmod/U
    //   409: invokevirtual n : ()Z
    //   412: ifeq -> 442
    //   415: goto -> 422
    //   418: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   421: athrow
    //   422: aload_0
    //   423: getfield i : Lnet/minecraft/client/Minecraft;
    //   426: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   429: getfield field_74320_O : I
    //   432: ifne -> 531
    //   435: goto -> 442
    //   438: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   441: athrow
    //   442: new net/minecraft/util/math/Vec3d
    //   445: dup
    //   446: aload_0
    //   447: getfield i : Lnet/minecraft/client/Minecraft;
    //   450: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   453: getfield field_70142_S : D
    //   456: aload_0
    //   457: getfield i : Lnet/minecraft/client/Minecraft;
    //   460: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   463: getfield field_70137_T : D
    //   466: aload_0
    //   467: getfield i : Lnet/minecraft/client/Minecraft;
    //   470: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   473: getfield field_70136_U : D
    //   476: invokespecial <init> : (DDD)V
    //   479: aload_0
    //   480: getfield i : Lnet/minecraft/client/Minecraft;
    //   483: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   486: invokevirtual func_174791_d : ()Lnet/minecraft/util/math/Vec3d;
    //   489: fload #9
    //   491: f2d
    //   492: invokestatic a : (Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/math/Vec3d;
    //   495: astore #12
    //   497: aload_0
    //   498: getfield k : Lcom/schnurritv/sexmod/Q;
    //   501: invokevirtual x : ()Lnet/minecraft/util/math/Vec3d;
    //   504: aload #12
    //   506: invokevirtual func_178788_d : (Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
    //   509: astore #13
    //   511: aload #13
    //   513: getfield field_72450_a : D
    //   516: dstore_2
    //   517: aload #13
    //   519: getfield field_72448_b : D
    //   522: dstore #4
    //   524: aload #13
    //   526: getfield field_72449_c : D
    //   529: dstore #6
    //   531: aload #10
    //   533: getstatic com/schnurritv/sexmod/Q.z : Lnet/minecraft/network/datasync/DataParameter;
    //   536: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   539: checkcast java/lang/Float
    //   542: invokevirtual floatValue : ()F
    //   545: fstore #12
    //   547: aload_1
    //   548: fload #12
    //   550: putfield field_70177_z : F
    //   553: aload_1
    //   554: fload #12
    //   556: putfield field_70760_ar : F
    //   559: aload_1
    //   560: fload #12
    //   562: putfield field_70761_aq : F
    //   565: aload_1
    //   566: fload #12
    //   568: putfield field_70758_at : F
    //   571: aload_1
    //   572: fload #12
    //   574: putfield field_70759_as : F
    //   577: aload_1
    //   578: invokevirtual func_110167_bD : ()Z
    //   581: ifeq -> 608
    //   584: aload_0
    //   585: aload_1
    //   586: dload_2
    //   587: dload #4
    //   589: aload_0
    //   590: getfield m : D
    //   593: dadd
    //   594: dload #6
    //   596: fload #9
    //   598: invokevirtual a : (Lcom/schnurritv/sexmod/Q;DDDF)V
    //   601: goto -> 608
    //   604: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   607: athrow
    //   608: invokestatic func_179094_E : ()V
    //   611: dload_2
    //   612: dload #4
    //   614: dload #6
    //   616: invokestatic func_179137_b : (DDD)V
    //   619: sipush #2896
    //   622: invokestatic glDisable : (I)V
    //   625: fconst_1
    //   626: fconst_1
    //   627: fconst_1
    //   628: ldc 0.5
    //   630: invokestatic func_179131_c : (FFFF)V
    //   633: invokestatic func_179108_z : ()V
    //   636: invokestatic func_179147_l : ()V
    //   639: getstatic net/minecraft/client/renderer/GlStateManager$SourceFactor.SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;
    //   642: getstatic net/minecraft/client/renderer/GlStateManager$DestFactor.ONE_MINUS_SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$DestFactor;
    //   645: invokestatic func_187401_a : (Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;)V
    //   648: aload_1
    //   649: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   652: ifnull -> 680
    //   655: aload_1
    //   656: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   659: invokevirtual shouldRiderSit : ()Z
    //   662: ifeq -> 680
    //   665: goto -> 672
    //   668: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   671: athrow
    //   672: iconst_1
    //   673: goto -> 681
    //   676: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   679: athrow
    //   680: iconst_0
    //   681: istore #10
    //   683: new software/bernie/geckolib3/model/provider/data/EntityModelData
    //   686: dup
    //   687: invokespecial <init> : ()V
    //   690: astore #11
    //   692: aload #11
    //   694: iload #10
    //   696: putfield isSitting : Z
    //   699: aload #11
    //   701: aload_1
    //   702: invokevirtual func_70631_g_ : ()Z
    //   705: putfield isChild : Z
    //   708: aload_1
    //   709: getfield field_70760_ar : F
    //   712: aload_1
    //   713: getfield field_70761_aq : F
    //   716: fload #9
    //   718: invokestatic lerpYaw : (FFF)F
    //   721: fstore #12
    //   723: aload_1
    //   724: getfield field_70758_at : F
    //   727: aload_1
    //   728: getfield field_70759_as : F
    //   731: fload #9
    //   733: invokestatic lerpYaw : (FFF)F
    //   736: fstore #13
    //   738: fload #13
    //   740: fload #12
    //   742: fsub
    //   743: fstore #14
    //   745: iload #10
    //   747: ifeq -> 866
    //   750: aload_1
    //   751: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   754: instanceof net/minecraft/entity/EntityLivingBase
    //   757: ifeq -> 866
    //   760: goto -> 767
    //   763: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   766: athrow
    //   767: aload_1
    //   768: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   771: checkcast net/minecraft/entity/EntityLivingBase
    //   774: astore #15
    //   776: aload #15
    //   778: getfield field_70760_ar : F
    //   781: aload #15
    //   783: getfield field_70761_aq : F
    //   786: fload #9
    //   788: invokestatic lerpYaw : (FFF)F
    //   791: fstore #12
    //   793: fload #13
    //   795: fload #12
    //   797: fsub
    //   798: fstore #14
    //   800: fload #14
    //   802: invokestatic func_76142_g : (F)F
    //   805: fstore #16
    //   807: fload #16
    //   809: ldc -85.0
    //   811: fcmpg
    //   812: ifge -> 819
    //   815: ldc -85.0
    //   817: fstore #16
    //   819: fload #16
    //   821: ldc 85.0
    //   823: fcmpl
    //   824: iflt -> 831
    //   827: ldc 85.0
    //   829: fstore #16
    //   831: fload #13
    //   833: fload #16
    //   835: fsub
    //   836: fstore #12
    //   838: fload #16
    //   840: fload #16
    //   842: fmul
    //   843: ldc 2500.0
    //   845: fcmpl
    //   846: ifle -> 859
    //   849: fload #12
    //   851: fload #16
    //   853: ldc 0.2
    //   855: fmul
    //   856: fadd
    //   857: fstore #12
    //   859: fload #13
    //   861: fload #12
    //   863: fsub
    //   864: fstore #14
    //   866: aload_1
    //   867: getfield field_70127_C : F
    //   870: aload_1
    //   871: getfield field_70125_A : F
    //   874: fload #9
    //   876: invokestatic lerp : (FFF)F
    //   879: fstore #15
    //   881: aload_0
    //   882: aload_1
    //   883: fload #9
    //   885: invokevirtual handleRotationFloat : (Lnet/minecraft/entity/EntityLivingBase;F)F
    //   888: fstore #16
    //   890: aload_0
    //   891: aload_1
    //   892: fload #16
    //   894: fload #12
    //   896: fload #9
    //   898: invokevirtual a : (Lcom/schnurritv/sexmod/Q;FFF)V
    //   901: fconst_0
    //   902: fstore #17
    //   904: fconst_0
    //   905: fstore #18
    //   907: iload #10
    //   909: ifne -> 981
    //   912: aload_1
    //   913: invokevirtual func_70089_S : ()Z
    //   916: ifeq -> 981
    //   919: goto -> 926
    //   922: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   925: athrow
    //   926: aload_1
    //   927: getfield field_184618_aE : F
    //   930: aload_1
    //   931: getfield field_70721_aZ : F
    //   934: fload #9
    //   936: invokestatic lerp : (FFF)F
    //   939: fstore #17
    //   941: aload_1
    //   942: getfield field_184619_aG : F
    //   945: aload_1
    //   946: getfield field_70721_aZ : F
    //   949: fconst_1
    //   950: fload #9
    //   952: fsub
    //   953: fmul
    //   954: fsub
    //   955: fstore #18
    //   957: aload_1
    //   958: invokevirtual func_70631_g_ : ()Z
    //   961: ifeq -> 971
    //   964: fload #18
    //   966: ldc 3.0
    //   968: fmul
    //   969: fstore #18
    //   971: fload #17
    //   973: fconst_1
    //   974: fcmpl
    //   975: ifle -> 981
    //   978: fconst_1
    //   979: fstore #17
    //   981: aload #11
    //   983: fload #15
    //   985: fneg
    //   986: putfield headPitch : F
    //   989: aload #11
    //   991: fload #14
    //   993: fneg
    //   994: putfield netHeadYaw : F
    //   997: new software/bernie/geckolib3/core/event/predicate/AnimationEvent
    //   1000: dup
    //   1001: aload_1
    //   1002: fload #18
    //   1004: fload #17
    //   1006: fload #9
    //   1008: fload #17
    //   1010: ldc -0.15
    //   1012: fcmpl
    //   1013: ifle -> 1031
    //   1016: fload #17
    //   1018: ldc 0.15
    //   1020: fcmpg
    //   1021: iflt -> 1035
    //   1024: goto -> 1031
    //   1027: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1030: athrow
    //   1031: iconst_1
    //   1032: goto -> 1036
    //   1035: iconst_0
    //   1036: aload #11
    //   1038: invokestatic singletonList : (Ljava/lang/Object;)Ljava/util/List;
    //   1041: invokespecial <init> : (Lsoftware/bernie/geckolib3/core/IAnimatable;FFFZLjava/util/List;)V
    //   1044: astore #19
    //   1046: aload_0
    //   1047: invokespecial getGeoModelProvider : ()Lsoftware/bernie/geckolib3/model/provider/GeoModelProvider;
    //   1050: astore #20
    //   1052: aload #20
    //   1054: aload_1
    //   1055: invokevirtual getModelLocation : (Ljava/lang/Object;)Lnet/minecraft/util/ResourceLocation;
    //   1058: astore #21
    //   1060: aload #20
    //   1062: aload #21
    //   1064: invokevirtual getModel : (Lnet/minecraft/util/ResourceLocation;)Lsoftware/bernie/geckolib3/geo/render/built/GeoModel;
    //   1067: astore #22
    //   1069: aload #20
    //   1071: instanceof software/bernie/geckolib3/core/IAnimatableModel
    //   1074: ifeq -> 1107
    //   1077: aload #20
    //   1079: checkcast software/bernie/geckolib3/core/IAnimatableModel
    //   1082: aload_1
    //   1083: aload_1
    //   1084: invokevirtual func_110124_au : ()Ljava/util/UUID;
    //   1087: invokevirtual hashCode : ()I
    //   1090: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1093: aload #19
    //   1095: invokeinterface setLivingAnimations : (Ljava/lang/Object;Ljava/lang/Integer;Lsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1100: goto -> 1107
    //   1103: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1106: athrow
    //   1107: invokestatic func_179094_E : ()V
    //   1110: fconst_0
    //   1111: ldc 0.01
    //   1113: fconst_0
    //   1114: invokestatic func_179109_b : (FFF)V
    //   1117: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   1120: getfield field_71446_o : Lnet/minecraft/client/renderer/texture/TextureManager;
    //   1123: aload_0
    //   1124: aload_1
    //   1125: invokevirtual getEntityTexture : (Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/ResourceLocation;
    //   1128: invokevirtual func_110577_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   1131: aload_0
    //   1132: aload_1
    //   1133: fload #9
    //   1135: invokevirtual getRenderColor : (Ljava/lang/Object;F)Lsoftware/bernie/geckolib3/core/util/Color;
    //   1138: astore #23
    //   1140: aload_0
    //   1141: aload_1
    //   1142: fload #9
    //   1144: invokevirtual setDoRenderBrightness : (Lnet/minecraft/entity/EntityLivingBase;F)Z
    //   1147: istore #24
    //   1149: aload_0
    //   1150: aload #22
    //   1152: aload_1
    //   1153: fload #9
    //   1155: aload #23
    //   1157: invokevirtual getRed : ()I
    //   1160: i2f
    //   1161: ldc 255.0
    //   1163: fdiv
    //   1164: aload #23
    //   1166: invokevirtual getBlue : ()I
    //   1169: i2f
    //   1170: ldc 255.0
    //   1172: fdiv
    //   1173: aload #23
    //   1175: invokevirtual getGreen : ()I
    //   1178: i2f
    //   1179: ldc 255.0
    //   1181: fdiv
    //   1182: aload #23
    //   1184: invokevirtual getAlpha : ()I
    //   1187: i2f
    //   1188: ldc 255.0
    //   1190: fdiv
    //   1191: invokevirtual a : (Lsoftware/bernie/geckolib3/geo/render/built/GeoModel;Lcom/schnurritv/sexmod/Q;FFFFF)V
    //   1194: iload #24
    //   1196: ifeq -> 1209
    //   1199: invokestatic unset : ()V
    //   1202: goto -> 1209
    //   1205: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1208: athrow
    //   1209: aload_0
    //   1210: getfield layerRenderers : Ljava/util/List;
    //   1213: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1218: astore #25
    //   1220: aload #25
    //   1222: invokeinterface hasNext : ()Z
    //   1227: ifeq -> 1265
    //   1230: aload #25
    //   1232: invokeinterface next : ()Ljava/lang/Object;
    //   1237: checkcast software/bernie/geckolib3/renderers/geo/GeoLayerRenderer
    //   1240: astore #26
    //   1242: aload #26
    //   1244: aload_1
    //   1245: fload #18
    //   1247: fload #17
    //   1249: fload #9
    //   1251: fload #18
    //   1253: fload #14
    //   1255: fload #15
    //   1257: aload #23
    //   1259: invokevirtual render : (Lnet/minecraft/entity/EntityLivingBase;FFFFFFLsoftware/bernie/geckolib3/core/util/Color;)V
    //   1262: goto -> 1220
    //   1265: sipush #2896
    //   1268: invokestatic glEnable : (I)V
    //   1271: invokestatic func_179084_k : ()V
    //   1274: invokestatic func_179133_A : ()V
    //   1277: invokestatic func_179121_F : ()V
    //   1280: invokestatic func_179121_F : ()V
    //   1283: return
    // Exception table:
    //   from	to	target	type
    //   0	28	31	java/lang/IllegalStateException
    //   18	45	48	java/lang/IllegalStateException
    //   35	65	68	java/lang/IllegalStateException
    //   52	81	84	java/lang/IllegalStateException
    //   142	155	158	java/lang/IllegalStateException
    //   147	173	176	java/lang/IllegalStateException
    //   162	194	197	java/lang/IllegalStateException
    //   362	384	387	java/lang/IllegalStateException
    //   367	398	401	java/lang/IllegalStateException
    //   391	415	418	java/lang/IllegalStateException
    //   405	435	438	java/lang/IllegalStateException
    //   577	601	604	java/lang/IllegalStateException
    //   608	665	668	java/lang/IllegalStateException
    //   655	676	676	java/lang/IllegalStateException
    //   745	760	763	java/lang/IllegalStateException
    //   907	919	922	java/lang/IllegalStateException
    //   981	1024	1027	java/lang/IllegalStateException
    //   1069	1100	1103	java/lang/IllegalStateException
    //   1149	1202	1205	java/lang/IllegalStateException
  }
  
  protected void a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3) {
    try {
      super.applyRotations((EntityLivingBase)paramT, paramFloat1, paramFloat2, paramFloat3);
      if (!(paramT instanceof U))
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    UUID uUID = ((U)paramT).u();
    try {
      if (uUID == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = ((Q)paramT).field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.func_184613_cA())
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    float f1 = entityPlayer.func_184599_cB() + paramFloat3;
    float f2 = MathHelper.func_76131_a(f1 * f1 / 100.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(f2 * (-90.0F - entityPlayer.field_70125_A), 1.0F, 0.0F, 0.0F);
    Vec3d vec3d = entityPlayer.func_70676_i(paramFloat3);
    double d1 = entityPlayer.field_70159_w * entityPlayer.field_70159_w + entityPlayer.field_70179_y * entityPlayer.field_70179_y;
    double d2 = vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c;
    try {
      if (d1 > 0.0D && d2 > 0.0D) {
        double d3 = (entityPlayer.field_70159_w * vec3d.field_72450_a + entityPlayer.field_70179_y * vec3d.field_72449_c) / Math.sqrt(d1) * Math.sqrt(d2);
        double d4 = entityPlayer.field_70159_w * vec3d.field_72449_c - entityPlayer.field_70179_y * vec3d.field_72450_a;
        GlStateManager.func_179114_b((float)(Math.signum(d4) * Math.acos(d3)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
  }
  
  protected void a(BufferBuilder paramBufferBuilder, String paramString, GeoBone paramGeoBone) {}
  
  protected void a(Q paramQ, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
    Entity entity = paramQ.func_110166_bE();
    paramDouble2 -= (1.6D - paramQ.field_70131_O) * 0.5D;
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder bufferBuilder = tessellator.func_178180_c();
    double d1 = bZ.a(entity.field_70126_B, entity.field_70177_z, paramFloat * 0.5F) * 0.01745329238474369D;
    double d2 = bZ.a(entity.field_70127_C, entity.field_70125_A, paramFloat * 0.5F) * 0.01745329238474369D;
    double d3 = Math.cos(d1);
    double d4 = Math.sin(d1);
    double d5 = Math.sin(d2);
    if (entity instanceof net.minecraft.entity.EntityHanging) {
      d3 = 0.0D;
      d4 = 0.0D;
      d5 = -1.0D;
    } 
    double d6 = Math.cos(d2);
    double d7 = bZ.b(entity.field_70169_q, entity.field_70165_t, paramFloat) - d3 * 0.7D - d4 * 0.5D * d6;
    double d8 = bZ.b(entity.field_70167_r + entity.func_70047_e() * 0.7D, entity.field_70163_u + entity.func_70047_e() * 0.7D, paramFloat) - d5 * 0.5D - 0.25D;
    double d9 = bZ.b(entity.field_70166_s, entity.field_70161_v, paramFloat) - d4 * 0.7D + d3 * 0.5D * d6;
    double d10 = bZ.a(paramQ.field_70760_ar, paramQ.field_70761_aq, paramFloat) * 0.01745329238474369D + 1.5707963267948966D;
    d3 = Math.cos(d10) * paramQ.field_70130_N * 0.4D;
    d4 = Math.sin(d10) * paramQ.field_70130_N * 0.4D;
    double d11 = bZ.b(paramQ.field_70169_q, paramQ.field_70165_t, paramFloat) + d3;
    double d12 = bZ.b(paramQ.field_70167_r, paramQ.field_70163_u, paramFloat);
    double d13 = bZ.b(paramQ.field_70166_s, paramQ.field_70161_v, paramFloat) + d4;
    paramDouble1 += d3;
    paramDouble3 += d4;
    double d14 = (float)(d7 - d11);
    double d15 = (float)(d8 - d12);
    double d16 = (float)(d9 - d13);
    GlStateManager.func_179090_x();
    GlStateManager.func_179140_f();
    GlStateManager.func_179129_p();
    bufferBuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
    byte b;
    for (b = 0; b <= 24; b++) {
      float f1 = 0.5F;
      float f2 = 0.4F;
      float f3 = 0.3F;
      if (b % 2 == 0) {
        f1 *= 0.7F;
        f2 *= 0.7F;
        f3 *= 0.7F;
      } 
      float f4 = b / 24.0F;
      bufferBuilder.func_181662_b(paramDouble1 + d14 * f4 + 0.0D, paramDouble2 + d15 * (f4 * f4 + f4) * 0.5D + ((24.0F - b) / 18.0F + 0.125F), paramDouble3 + d16 * f4).func_181666_a(f1, f2, f3, 1.0F).func_181675_d();
      bufferBuilder.func_181662_b(paramDouble1 + d14 * f4 + 0.025D, paramDouble2 + d15 * (f4 * f4 + f4) * 0.5D + ((24.0F - b) / 18.0F + 0.125F) + 0.025D, paramDouble3 + d16 * f4).func_181666_a(f1, f2, f3, 1.0F).func_181675_d();
    } 
    tessellator.func_78381_a();
    bufferBuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
    for (b = 0; b <= 24; b++) {
      float f1 = 0.5F;
      float f2 = 0.4F;
      float f3 = 0.3F;
      if (b % 2 == 0) {
        f1 *= 0.7F;
        f2 *= 0.7F;
        f3 *= 0.7F;
      } 
      float f4 = b / 24.0F;
      bufferBuilder.func_181662_b(paramDouble1 + d14 * f4 + 0.0D, paramDouble2 + d15 * (f4 * f4 + f4) * 0.5D + ((24.0F - b) / 18.0F + 0.125F) + 0.025D, paramDouble3 + d16 * f4).func_181666_a(f1, f2, f3, 1.0F).func_181675_d();
      bufferBuilder.func_181662_b(paramDouble1 + d14 * f4 + 0.025D, paramDouble2 + d15 * (f4 * f4 + f4) * 0.5D + ((24.0F - b) / 18.0F + 0.125F), paramDouble3 + d16 * f4 + 0.025D).func_181666_a(f1, f2, f3, 1.0F).func_181675_d();
    } 
    tessellator.func_78381_a();
    GlStateManager.func_179145_e();
    GlStateManager.func_179098_w();
    GlStateManager.func_179089_o();
  }
  
  public void renderRecursively(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    try {
      if (((Q)this.k).field_70170_p instanceof com.c)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    String str = paramGeoBone.getName();
    try {
      if (str.equals("weapon"))
        try {
          if (this.k instanceof S)
            a(paramBufferBuilder, paramGeoBone); 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        }  
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (str.equals("itemRenderer"))
        try {
          if (this.k.h() == b1.PAYMENT)
            b(paramBufferBuilder, paramGeoBone); 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        }  
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      l = paramBufferBuilder;
      a(paramBufferBuilder, str, paramGeoBone);
      MATRIX_STACK.push();
      MATRIX_STACK.translate(paramGeoBone);
      MATRIX_STACK.moveToPivot(paramGeoBone);
      MATRIX_STACK.rotate(paramGeoBone);
      MATRIX_STACK.scale(paramGeoBone);
      MATRIX_STACK.moveBackFromPivot(paramGeoBone);
      if ("Head2".equals(str))
        try {
          if (!b()) {
            MATRIX_STACK.pop();
            return;
          } 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        }  
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (!a(str)) {
        MATRIX_STACK.pop();
        return;
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    if (!paramGeoBone.isHidden) {
      Vector4f vector4f = a(str, paramFloat1, paramFloat2, paramFloat3);
      paramFloat1 = vector4f.x;
      paramFloat2 = vector4f.y;
      paramFloat3 = vector4f.z;
      double d = vector4f.w;
      if (!this.j.contains(str))
        for (GeoCube geoCube : paramGeoBone.childCubes) {
          MATRIX_STACK.push();
          GlStateManager.func_179094_E();
          a(paramBufferBuilder, geoCube, paramFloat1, paramFloat2, paramFloat3, paramFloat4, d);
          GlStateManager.func_179121_F();
          MATRIX_STACK.pop();
        }  
      for (GeoBone geoBone : paramGeoBone.childBones) {
        try {
          if (d == 0.0D) {
            renderRecursively(paramBufferBuilder, geoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
            continue;
          } 
        } catch (IllegalStateException illegalStateException) {
          throw a(null);
        } 
        a(paramBufferBuilder, geoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4, d);
      } 
    } 
    try {
      MATRIX_STACK.pop();
    } catch (IllegalStateException illegalStateException) {}
  }
  
  Vector4f a(float paramFloat1, float paramFloat2, float paramFloat3) {
    return new Vector4f(paramFloat1, paramFloat2, paramFloat3, 0.0F);
  }
  
  boolean a(String paramString) {
    try {
      if (!paramString.startsWith("armor"))
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return this.k instanceof S;
  }
  
  protected Vector4f a(String paramString, float paramFloat1, float paramFloat2, float paramFloat3) {
    int i;
    float f2;
    float f3;
    float f4;
    try {
      if (!paramString.startsWith("armor"))
        return a(paramFloat1, paramFloat2, paramFloat3); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (!(this.k instanceof S))
        return a(paramFloat1, paramFloat2, paramFloat3); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      if (((Integer)((Q)this.k).m.func_187225_a(Q.v)).intValue() == 0)
        return a(paramFloat1, paramFloat2, paramFloat3); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    GeoModelProvider geoModelProvider = getGeoModelProvider();
    try {
      if (!(geoModelProvider instanceof aM))
        return a(paramFloat1, paramFloat2, paramFloat3); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    aM aM = (aM)geoModelProvider;
    ItemStack itemStack = aM.a((Q)this.k, paramString);
    try {
      if (!(itemStack.func_77973_b() instanceof ItemArmor))
        return a(paramFloat1, paramFloat2, paramFloat3); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
    ItemArmor.ArmorMaterial armorMaterial = itemArmor.func_82812_d();
    float f1 = 0.0F;
    switch (a.a[armorMaterial.ordinal()]) {
      case 1:
        f1 = 1.0F;
        break;
      case 2:
      case 3:
        f1 = 2.0F;
        break;
      case 4:
        f1 = 4.0F;
        i = itemArmor.func_82814_b(itemStack);
        f2 = (i >> 16 & 0xFF) / 255.0F;
        f3 = (i >> 8 & 0xFF) / 255.0F;
        f4 = (i & 0xFF) / 255.0F;
        paramFloat1 *= f2;
        paramFloat2 *= f3;
        paramFloat3 *= f4;
        break;
    } 
    return new Vector4f(paramFloat1, paramFloat2, paramFloat3, 72.0F * f1 / 4096.0F);
  }
  
  public void a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    this.b = (Matrix4f)MATRIX_STACK.getModelMatrix().clone();
  }
  
  public void a(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, double paramDouble) {
    try {
      if (((Q)this.k).field_70170_p instanceof com.c)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    String str = paramGeoBone.getName();
    try {
      if (str.equals("weapon"))
        a(paramBufferBuilder, paramGeoBone); 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      a(paramBufferBuilder, paramGeoBone.getName(), paramGeoBone);
      MATRIX_STACK.push();
      MATRIX_STACK.translate(paramGeoBone);
      MATRIX_STACK.moveToPivot(paramGeoBone);
      MATRIX_STACK.rotate(paramGeoBone);
      MATRIX_STACK.scale(paramGeoBone);
      MATRIX_STACK.moveBackFromPivot(paramGeoBone);
      if (!paramGeoBone.isHidden) {
        if (!this.j.contains(str))
          for (GeoCube geoCube : paramGeoBone.childCubes) {
            MATRIX_STACK.push();
            GlStateManager.func_179094_E();
            a(paramBufferBuilder, geoCube, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramDouble);
            GlStateManager.func_179121_F();
            MATRIX_STACK.pop();
          }  
        for (GeoBone geoBone : paramGeoBone.childBones)
          a(paramBufferBuilder, geoBone, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramDouble); 
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    MATRIX_STACK.pop();
  }
  
  protected boolean b() {
    try {
      if (!this.k.L())
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
    
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return (this.i.field_71474_y.field_74320_O != 0);
  }
  
  public void a(BufferBuilder paramBufferBuilder, GeoCube paramGeoCube, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, double paramDouble) {
    // Byte code:
    //   0: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   3: aload_2
    //   4: invokevirtual moveToPivot : (Lsoftware/bernie/geckolib3/geo/render/built/GeoCube;)V
    //   7: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   10: aload_2
    //   11: invokevirtual rotate : (Lsoftware/bernie/geckolib3/geo/render/built/GeoCube;)V
    //   14: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   17: aload_2
    //   18: invokevirtual moveBackFromPivot : (Lsoftware/bernie/geckolib3/geo/render/built/GeoCube;)V
    //   21: aload_2
    //   22: getfield quads : [Lsoftware/bernie/geckolib3/geo/render/built/GeoQuad;
    //   25: astore #9
    //   27: aload #9
    //   29: arraylength
    //   30: istore #10
    //   32: iconst_0
    //   33: istore #11
    //   35: iload #11
    //   37: iload #10
    //   39: if_icmpge -> 466
    //   42: aload #9
    //   44: iload #11
    //   46: aaload
    //   47: astore #12
    //   49: aload #12
    //   51: ifnonnull -> 61
    //   54: goto -> 460
    //   57: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   60: athrow
    //   61: new javax/vecmath/Vector3f
    //   64: dup
    //   65: aload #12
    //   67: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   70: invokevirtual func_177958_n : ()I
    //   73: i2f
    //   74: aload #12
    //   76: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   79: invokevirtual func_177956_o : ()I
    //   82: i2f
    //   83: aload #12
    //   85: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   88: invokevirtual func_177952_p : ()I
    //   91: i2f
    //   92: invokespecial <init> : (FFF)V
    //   95: astore #13
    //   97: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   100: invokevirtual getNormalMatrix : ()Ljavax/vecmath/Matrix3f;
    //   103: aload #13
    //   105: invokevirtual transform : (Ljavax/vecmath/Tuple3f;)V
    //   108: aload_2
    //   109: getfield size : Ljavax/vecmath/Vector3f;
    //   112: getfield y : F
    //   115: fconst_0
    //   116: fcmpl
    //   117: ifeq -> 139
    //   120: aload_2
    //   121: getfield size : Ljavax/vecmath/Vector3f;
    //   124: getfield z : F
    //   127: fconst_0
    //   128: fcmpl
    //   129: ifne -> 175
    //   132: goto -> 139
    //   135: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   138: athrow
    //   139: aload #13
    //   141: invokevirtual getX : ()F
    //   144: fconst_0
    //   145: fcmpg
    //   146: ifge -> 175
    //   149: goto -> 156
    //   152: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   155: athrow
    //   156: aload #13
    //   158: dup
    //   159: getfield x : F
    //   162: ldc -1.0
    //   164: fmul
    //   165: putfield x : F
    //   168: goto -> 175
    //   171: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   174: athrow
    //   175: aload_2
    //   176: getfield size : Ljavax/vecmath/Vector3f;
    //   179: getfield x : F
    //   182: fconst_0
    //   183: fcmpl
    //   184: ifeq -> 206
    //   187: aload_2
    //   188: getfield size : Ljavax/vecmath/Vector3f;
    //   191: getfield z : F
    //   194: fconst_0
    //   195: fcmpl
    //   196: ifne -> 242
    //   199: goto -> 206
    //   202: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   205: athrow
    //   206: aload #13
    //   208: invokevirtual getY : ()F
    //   211: fconst_0
    //   212: fcmpg
    //   213: ifge -> 242
    //   216: goto -> 223
    //   219: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   222: athrow
    //   223: aload #13
    //   225: dup
    //   226: getfield y : F
    //   229: ldc -1.0
    //   231: fmul
    //   232: putfield y : F
    //   235: goto -> 242
    //   238: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   241: athrow
    //   242: aload_2
    //   243: getfield size : Ljavax/vecmath/Vector3f;
    //   246: getfield x : F
    //   249: fconst_0
    //   250: fcmpl
    //   251: ifeq -> 273
    //   254: aload_2
    //   255: getfield size : Ljavax/vecmath/Vector3f;
    //   258: getfield y : F
    //   261: fconst_0
    //   262: fcmpl
    //   263: ifne -> 309
    //   266: goto -> 273
    //   269: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   272: athrow
    //   273: aload #13
    //   275: invokevirtual getZ : ()F
    //   278: fconst_0
    //   279: fcmpg
    //   280: ifge -> 309
    //   283: goto -> 290
    //   286: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   289: athrow
    //   290: aload #13
    //   292: dup
    //   293: getfield z : F
    //   296: ldc -1.0
    //   298: fmul
    //   299: putfield z : F
    //   302: goto -> 309
    //   305: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   308: athrow
    //   309: aload #12
    //   311: getfield vertices : [Lsoftware/bernie/geckolib3/geo/render/built/GeoVertex;
    //   314: astore #14
    //   316: aload #14
    //   318: arraylength
    //   319: istore #15
    //   321: iconst_0
    //   322: istore #16
    //   324: iload #16
    //   326: iload #15
    //   328: if_icmpge -> 460
    //   331: aload #14
    //   333: iload #16
    //   335: aaload
    //   336: astore #17
    //   338: new javax/vecmath/Vector4f
    //   341: dup
    //   342: aload #17
    //   344: getfield position : Ljavax/vecmath/Vector3f;
    //   347: invokevirtual getX : ()F
    //   350: aload #17
    //   352: getfield position : Ljavax/vecmath/Vector3f;
    //   355: invokevirtual getY : ()F
    //   358: aload #17
    //   360: getfield position : Ljavax/vecmath/Vector3f;
    //   363: invokevirtual getZ : ()F
    //   366: fconst_1
    //   367: invokespecial <init> : (FFFF)V
    //   370: astore #18
    //   372: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   375: invokevirtual getModelMatrix : ()Ljavax/vecmath/Matrix4f;
    //   378: aload #18
    //   380: invokevirtual transform : (Ljavax/vecmath/Tuple4f;)V
    //   383: aload_1
    //   384: aload #18
    //   386: invokevirtual getX : ()F
    //   389: f2d
    //   390: aload #18
    //   392: invokevirtual getY : ()F
    //   395: f2d
    //   396: aload #18
    //   398: invokevirtual getZ : ()F
    //   401: f2d
    //   402: invokevirtual func_181662_b : (DDD)Lnet/minecraft/client/renderer/BufferBuilder;
    //   405: aload #17
    //   407: getfield textureU : F
    //   410: f2d
    //   411: dload #7
    //   413: dadd
    //   414: aload #17
    //   416: getfield textureV : F
    //   419: f2d
    //   420: invokevirtual func_187315_a : (DD)Lnet/minecraft/client/renderer/BufferBuilder;
    //   423: fload_3
    //   424: fload #4
    //   426: fload #5
    //   428: fload #6
    //   430: invokevirtual func_181666_a : (FFFF)Lnet/minecraft/client/renderer/BufferBuilder;
    //   433: aload #13
    //   435: invokevirtual getX : ()F
    //   438: aload #13
    //   440: invokevirtual getY : ()F
    //   443: aload #13
    //   445: invokevirtual getZ : ()F
    //   448: invokevirtual func_181663_c : (FFF)Lnet/minecraft/client/renderer/BufferBuilder;
    //   451: invokevirtual func_181675_d : ()V
    //   454: iinc #16, 1
    //   457: goto -> 324
    //   460: iinc #11, 1
    //   463: goto -> 35
    //   466: return
    // Exception table:
    //   from	to	target	type
    //   49	57	57	java/lang/IllegalStateException
    //   97	132	135	java/lang/IllegalStateException
    //   120	149	152	java/lang/IllegalStateException
    //   139	168	171	java/lang/IllegalStateException
    //   175	199	202	java/lang/IllegalStateException
    //   187	216	219	java/lang/IllegalStateException
    //   206	235	238	java/lang/IllegalStateException
    //   242	266	269	java/lang/IllegalStateException
    //   254	283	286	java/lang/IllegalStateException
    //   273	302	305	java/lang/IllegalStateException
  }
  
  protected ItemStack c() {
    String str = (String)((Q)this.k).m.func_187225_a(Q.f);
    byte b = -1;
    try {
      switch (str.hashCode()) {
        case 95761198:
          if (str.equals("doggy"))
            b = 0; 
          break;
        case -20842805:
          if (str.equals("blowjob"))
            b = 1; 
          break;
        case 109773592:
          if (str.equals("strip"))
            b = 2; 
          break;
        case 64419037:
          if (str.equals("boobjob"))
            b = 3; 
          break;
        case 2014427283:
          if (str.equals("touch_boobs"))
            b = 4; 
          break;
        case 113766:
          if (str.equals("sex"))
            b = 5; 
          break;
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    try {
      switch (b) {
        case 0:
          return new ItemStack(Items.field_151045_i, 2);
        case 1:
          return new ItemStack(Items.field_151166_bC, 3);
        case 2:
          return new ItemStack(Items.field_151043_k, 1);
        case 3:
          return new ItemStack(Items.field_151079_bi, 2);
        case 4:
          return new ItemStack(Items.field_151115_aP, 2, 1);
        case 5:
          return new ItemStack(Items.field_151115_aP, 3, 0);
      } 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return null;
  }
  
  protected void b(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone) {
    ItemStack itemStack = c();
    try {
      if (itemStack == null)
        return; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    ItemRenderer itemRenderer = Minecraft.func_71410_x().func_175597_ag();
    byte b = 0;
    while (true) {
      try {
        if (b < itemStack.func_190916_E()) {
          try {
            GlStateManager.func_179094_E();
            Tessellator.func_178181_a().func_78381_a();
            bE.a(IGeoRenderer.MATRIX_STACK, paramGeoBone);
            GL11.glEnable(2896);
            GL11.glRotated(paramGeoBone.getRotationX() + 2.5D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(paramGeoBone.getRotationY(), 0.0D, 1.0D, 0.0D);
            GL11.glRotated(paramGeoBone.getRotationZ(), 1.0D, 0.0D, 0.0D);
            switch (b) {
              case 1:
                GL11.glRotated(-15.0D, 0.0D, 0.0D, 1.0D);
                GlStateManager.func_179137_b(0.0D, 0.0D, -0.025D);
                break;
              case 2:
                GL11.glRotated(15.0D, 0.0D, 0.0D, 1.0D);
                GlStateManager.func_179137_b(0.0D, 0.0D, 0.025D);
                break;
            } 
          } catch (IllegalStateException illegalStateException) {
            throw a(null);
          } 
          GlStateManager.func_179152_a(((Q)this.k).t, ((Q)this.k).t, ((Q)this.k).t);
          itemRenderer.func_178099_a((EntityLivingBase)this.k, new ItemStack(itemStack.func_77973_b(), 1), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
          func_110776_a(Objects.<ResourceLocation>requireNonNull(getEntityTexture((EntityLivingBase)this.k)));
          paramBufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181712_l);
          GL11.glDisable(2896);
          GlStateManager.func_179121_F();
          b++;
          continue;
        } 
      } catch (IllegalStateException illegalStateException) {
        throw a(null);
      } 
      break;
    } 
  }
  
  protected ItemStack a(@Nullable ItemStack paramItemStack) {
    return paramItemStack;
  }
  
  protected void a(BufferBuilder paramBufferBuilder, GeoBone paramGeoBone) {
    // Byte code:
    //   0: aload_0
    //   1: getfield k : Lcom/schnurritv/sexmod/Q;
    //   4: ifnonnull -> 12
    //   7: return
    //   8: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   11: athrow
    //   12: aload_0
    //   13: getfield k : Lcom/schnurritv/sexmod/Q;
    //   16: instanceof com/schnurritv/sexmod/S
    //   19: ifne -> 27
    //   22: return
    //   23: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   26: athrow
    //   27: aload_0
    //   28: getfield k : Lcom/schnurritv/sexmod/Q;
    //   31: invokevirtual func_184212_Q : ()Lnet/minecraft/network/datasync/EntityDataManager;
    //   34: astore_3
    //   35: aload_0
    //   36: getfield k : Lcom/schnurritv/sexmod/Q;
    //   39: checkcast com/schnurritv/sexmod/S
    //   42: astore #4
    //   44: aload_3
    //   45: getstatic com/schnurritv/sexmod/S.N : Lnet/minecraft/network/datasync/DataParameter;
    //   48: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   51: checkcast java/lang/Integer
    //   54: invokevirtual intValue : ()I
    //   57: istore #5
    //   59: aload #4
    //   61: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   64: getstatic com/schnurritv/sexmod/b1.BOW : Lcom/schnurritv/sexmod/b1;
    //   67: if_acmpeq -> 82
    //   70: aload_0
    //   71: fconst_0
    //   72: putfield d : F
    //   75: goto -> 82
    //   78: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   81: athrow
    //   82: aconst_null
    //   83: astore #6
    //   85: iload #5
    //   87: iconst_1
    //   88: if_icmpne -> 106
    //   91: aload_3
    //   92: getstatic com/schnurritv/sexmod/S.M : Lnet/minecraft/network/datasync/DataParameter;
    //   95: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   98: checkcast net/minecraft/item/ItemStack
    //   101: astore #6
    //   103: goto -> 124
    //   106: iload #5
    //   108: iconst_2
    //   109: if_icmpne -> 124
    //   112: aload_3
    //   113: getstatic com/schnurritv/sexmod/S.G : Lnet/minecraft/network/datasync/DataParameter;
    //   116: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   119: checkcast net/minecraft/item/ItemStack
    //   122: astore #6
    //   124: aload_0
    //   125: aload #6
    //   127: invokevirtual a : (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;
    //   130: astore #6
    //   132: aload #6
    //   134: ifnonnull -> 142
    //   137: return
    //   138: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   141: athrow
    //   142: aload #6
    //   144: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   147: getstatic net/minecraft/init/Items.field_151031_f : Lnet/minecraft/item/ItemBow;
    //   150: invokevirtual equals : (Ljava/lang/Object;)Z
    //   153: ifeq -> 222
    //   156: aload #4
    //   158: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   161: getstatic com/schnurritv/sexmod/b1.BOW : Lcom/schnurritv/sexmod/b1;
    //   164: if_acmpne -> 222
    //   167: goto -> 174
    //   170: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   173: athrow
    //   174: aload_0
    //   175: dup
    //   176: getfield d : F
    //   179: ldc 0.015
    //   181: fadd
    //   182: putfield d : F
    //   185: aload #4
    //   187: aload_0
    //   188: getfield d : F
    //   191: fneg
    //   192: ldc 20.0
    //   194: fmul
    //   195: aload #6
    //   197: invokevirtual func_77988_m : ()I
    //   200: i2f
    //   201: fadd
    //   202: invokestatic round : (F)I
    //   205: invokevirtual b : (I)V
    //   208: aload #4
    //   210: aload #6
    //   212: invokevirtual a : (Lnet/minecraft/item/ItemStack;)V
    //   215: goto -> 222
    //   218: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   221: athrow
    //   222: invokestatic func_179094_E : ()V
    //   225: invokestatic func_178181_a : ()Lnet/minecraft/client/renderer/Tessellator;
    //   228: invokevirtual func_78381_a : ()V
    //   231: getstatic com/schnurritv/sexmod/bi.MATRIX_STACK : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   234: aload_2
    //   235: invokestatic a : (Lsoftware/bernie/geckolib3/util/MatrixStack;Lsoftware/bernie/geckolib3/geo/render/built/GeoBone;)V
    //   238: sipush #2896
    //   241: invokestatic glEnable : (I)V
    //   244: aload #6
    //   246: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
    //   249: instanceof net/minecraft/item/ItemBow
    //   252: ifeq -> 274
    //   255: aload #4
    //   257: getfield J : I
    //   260: i2f
    //   261: fconst_1
    //   262: fconst_0
    //   263: fconst_0
    //   264: invokestatic glRotatef : (FFFF)V
    //   267: goto -> 358
    //   270: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   273: athrow
    //   274: aload #4
    //   276: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   279: getstatic com/schnurritv/sexmod/b1.ATTACK : Lcom/schnurritv/sexmod/b1;
    //   282: if_acmpne -> 346
    //   285: aload #4
    //   287: getfield Q : I
    //   290: ifne -> 346
    //   293: goto -> 300
    //   296: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   299: athrow
    //   300: aload #4
    //   302: getfield R : Lnet/minecraft/util/math/Vec3d;
    //   305: getfield field_72450_a : D
    //   308: aload #4
    //   310: getfield R : Lnet/minecraft/util/math/Vec3d;
    //   313: getfield field_72448_b : D
    //   316: aload #4
    //   318: getfield R : Lnet/minecraft/util/math/Vec3d;
    //   321: getfield field_72449_c : D
    //   324: invokestatic func_179137_b : (DDD)V
    //   327: aload #4
    //   329: getfield F : I
    //   332: i2f
    //   333: fconst_1
    //   334: fconst_0
    //   335: fconst_0
    //   336: invokestatic glRotatef : (FFFF)V
    //   339: goto -> 358
    //   342: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   345: athrow
    //   346: aload #4
    //   348: getfield O : I
    //   351: i2f
    //   352: fconst_1
    //   353: fconst_0
    //   354: fconst_0
    //   355: invokestatic glRotatef : (FFFF)V
    //   358: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   361: invokevirtual func_175597_ag : ()Lnet/minecraft/client/renderer/ItemRenderer;
    //   364: aload_0
    //   365: getfield k : Lcom/schnurritv/sexmod/Q;
    //   368: aload #6
    //   370: getstatic net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType.THIRD_PERSON_RIGHT_HAND : Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;
    //   373: invokevirtual func_178099_a : (Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V
    //   376: aload_0
    //   377: aload_0
    //   378: aload_0
    //   379: getfield k : Lcom/schnurritv/sexmod/Q;
    //   382: invokevirtual getEntityTexture : (Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/ResourceLocation;
    //   385: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   388: checkcast net/minecraft/util/ResourceLocation
    //   391: invokevirtual func_110776_a : (Lnet/minecraft/util/ResourceLocation;)V
    //   394: aload_1
    //   395: bipush #7
    //   397: getstatic net/minecraft/client/renderer/vertex/DefaultVertexFormats.field_181712_l : Lnet/minecraft/client/renderer/vertex/VertexFormat;
    //   400: invokevirtual func_181668_a : (ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
    //   403: sipush #2896
    //   406: invokestatic glDisable : (I)V
    //   409: invokestatic func_179121_F : ()V
    //   412: return
    // Exception table:
    //   from	to	target	type
    //   0	8	8	java/lang/IllegalStateException
    //   12	23	23	java/lang/IllegalStateException
    //   59	75	78	java/lang/IllegalStateException
    //   132	138	138	java/lang/IllegalStateException
    //   142	167	170	java/lang/IllegalStateException
    //   156	215	218	java/lang/IllegalStateException
    //   222	270	270	java/lang/IllegalStateException
    //   274	293	296	java/lang/IllegalStateException
    //   285	342	342	java/lang/IllegalStateException
  }
  
  RayTraceResult a(Vec3d paramVec3d1, Vec3d paramVec3d2, World paramWorld) {
    // Byte code:
    //   0: aload_1
    //   1: getfield field_72450_a : D
    //   4: invokestatic isNaN : (D)Z
    //   7: ifne -> 44
    //   10: aload_1
    //   11: getfield field_72448_b : D
    //   14: invokestatic isNaN : (D)Z
    //   17: ifne -> 44
    //   20: goto -> 27
    //   23: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   26: athrow
    //   27: aload_1
    //   28: getfield field_72449_c : D
    //   31: invokestatic isNaN : (D)Z
    //   34: ifeq -> 50
    //   37: goto -> 44
    //   40: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   43: athrow
    //   44: aconst_null
    //   45: areturn
    //   46: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   49: athrow
    //   50: aload_2
    //   51: getfield field_72450_a : D
    //   54: invokestatic isNaN : (D)Z
    //   57: ifne -> 94
    //   60: aload_2
    //   61: getfield field_72448_b : D
    //   64: invokestatic isNaN : (D)Z
    //   67: ifne -> 94
    //   70: goto -> 77
    //   73: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   76: athrow
    //   77: aload_2
    //   78: getfield field_72449_c : D
    //   81: invokestatic isNaN : (D)Z
    //   84: ifeq -> 100
    //   87: goto -> 94
    //   90: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   93: athrow
    //   94: aconst_null
    //   95: areturn
    //   96: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   99: athrow
    //   100: aload_2
    //   101: getfield field_72450_a : D
    //   104: invokestatic func_76128_c : (D)I
    //   107: istore #4
    //   109: aload_2
    //   110: getfield field_72448_b : D
    //   113: invokestatic func_76128_c : (D)I
    //   116: istore #5
    //   118: aload_2
    //   119: getfield field_72449_c : D
    //   122: invokestatic func_76128_c : (D)I
    //   125: istore #6
    //   127: aload_1
    //   128: getfield field_72450_a : D
    //   131: invokestatic func_76128_c : (D)I
    //   134: istore #7
    //   136: aload_1
    //   137: getfield field_72448_b : D
    //   140: invokestatic func_76128_c : (D)I
    //   143: istore #8
    //   145: aload_1
    //   146: getfield field_72449_c : D
    //   149: invokestatic func_76128_c : (D)I
    //   152: istore #9
    //   154: new net/minecraft/util/math/BlockPos
    //   157: dup
    //   158: iload #7
    //   160: iload #8
    //   162: iload #9
    //   164: invokespecial <init> : (III)V
    //   167: astore #10
    //   169: aload_3
    //   170: aload #10
    //   172: invokevirtual func_180495_p : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   175: astore #11
    //   177: aload #11
    //   179: aload_3
    //   180: aload #10
    //   182: invokeinterface func_185890_d : (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/AxisAlignedBB;
    //   187: getstatic net/minecraft/block/Block.field_185506_k : Lnet/minecraft/util/math/AxisAlignedBB;
    //   190: if_acmpeq -> 233
    //   193: aload #11
    //   195: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   200: invokevirtual func_180664_k : ()Lnet/minecraft/util/BlockRenderLayer;
    //   203: getstatic net/minecraft/util/BlockRenderLayer.SOLID : Lnet/minecraft/util/BlockRenderLayer;
    //   206: if_acmpne -> 233
    //   209: goto -> 216
    //   212: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   215: athrow
    //   216: aload #11
    //   218: aload_3
    //   219: aload #10
    //   221: aload_1
    //   222: aload_2
    //   223: invokeinterface func_185910_a : (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;
    //   228: areturn
    //   229: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   232: athrow
    //   233: sipush #200
    //   236: istore #12
    //   238: iload #12
    //   240: iinc #12, -1
    //   243: iflt -> 1010
    //   246: aload_1
    //   247: getfield field_72450_a : D
    //   250: invokestatic isNaN : (D)Z
    //   253: ifne -> 297
    //   256: goto -> 263
    //   259: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   262: athrow
    //   263: aload_1
    //   264: getfield field_72448_b : D
    //   267: invokestatic isNaN : (D)Z
    //   270: ifne -> 297
    //   273: goto -> 280
    //   276: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   279: athrow
    //   280: aload_1
    //   281: getfield field_72449_c : D
    //   284: invokestatic isNaN : (D)Z
    //   287: ifeq -> 303
    //   290: goto -> 297
    //   293: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   296: athrow
    //   297: aconst_null
    //   298: areturn
    //   299: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   302: athrow
    //   303: iload #7
    //   305: iload #4
    //   307: if_icmpne -> 344
    //   310: iload #8
    //   312: iload #5
    //   314: if_icmpne -> 344
    //   317: goto -> 324
    //   320: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   323: athrow
    //   324: iload #9
    //   326: iload #6
    //   328: if_icmpne -> 344
    //   331: goto -> 338
    //   334: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   337: athrow
    //   338: aconst_null
    //   339: areturn
    //   340: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   343: athrow
    //   344: iconst_1
    //   345: istore #13
    //   347: iconst_1
    //   348: istore #14
    //   350: iconst_1
    //   351: istore #15
    //   353: ldc2_w 999.0
    //   356: dstore #16
    //   358: ldc2_w 999.0
    //   361: dstore #18
    //   363: ldc2_w 999.0
    //   366: dstore #20
    //   368: iload #4
    //   370: iload #7
    //   372: if_icmple -> 385
    //   375: iload #7
    //   377: i2d
    //   378: dconst_1
    //   379: dadd
    //   380: dstore #16
    //   382: goto -> 405
    //   385: iload #4
    //   387: iload #7
    //   389: if_icmpge -> 402
    //   392: iload #7
    //   394: i2d
    //   395: dconst_0
    //   396: dadd
    //   397: dstore #16
    //   399: goto -> 405
    //   402: iconst_0
    //   403: istore #13
    //   405: iload #5
    //   407: iload #8
    //   409: if_icmple -> 422
    //   412: iload #8
    //   414: i2d
    //   415: dconst_1
    //   416: dadd
    //   417: dstore #18
    //   419: goto -> 442
    //   422: iload #5
    //   424: iload #8
    //   426: if_icmpge -> 439
    //   429: iload #8
    //   431: i2d
    //   432: dconst_0
    //   433: dadd
    //   434: dstore #18
    //   436: goto -> 442
    //   439: iconst_0
    //   440: istore #14
    //   442: iload #6
    //   444: iload #9
    //   446: if_icmple -> 459
    //   449: iload #9
    //   451: i2d
    //   452: dconst_1
    //   453: dadd
    //   454: dstore #20
    //   456: goto -> 479
    //   459: iload #6
    //   461: iload #9
    //   463: if_icmpge -> 476
    //   466: iload #9
    //   468: i2d
    //   469: dconst_0
    //   470: dadd
    //   471: dstore #20
    //   473: goto -> 479
    //   476: iconst_0
    //   477: istore #15
    //   479: ldc2_w 999.0
    //   482: dstore #22
    //   484: ldc2_w 999.0
    //   487: dstore #24
    //   489: ldc2_w 999.0
    //   492: dstore #26
    //   494: aload_2
    //   495: getfield field_72450_a : D
    //   498: aload_1
    //   499: getfield field_72450_a : D
    //   502: dsub
    //   503: dstore #28
    //   505: aload_2
    //   506: getfield field_72448_b : D
    //   509: aload_1
    //   510: getfield field_72448_b : D
    //   513: dsub
    //   514: dstore #30
    //   516: aload_2
    //   517: getfield field_72449_c : D
    //   520: aload_1
    //   521: getfield field_72449_c : D
    //   524: dsub
    //   525: dstore #32
    //   527: iload #13
    //   529: ifeq -> 544
    //   532: dload #16
    //   534: aload_1
    //   535: getfield field_72450_a : D
    //   538: dsub
    //   539: dload #28
    //   541: ddiv
    //   542: dstore #22
    //   544: iload #14
    //   546: ifeq -> 561
    //   549: dload #18
    //   551: aload_1
    //   552: getfield field_72448_b : D
    //   555: dsub
    //   556: dload #30
    //   558: ddiv
    //   559: dstore #24
    //   561: iload #15
    //   563: ifeq -> 578
    //   566: dload #20
    //   568: aload_1
    //   569: getfield field_72449_c : D
    //   572: dsub
    //   573: dload #32
    //   575: ddiv
    //   576: dstore #26
    //   578: dload #22
    //   580: ldc2_w -0.0
    //   583: dcmpl
    //   584: ifne -> 592
    //   587: ldc2_w -1.0E-4
    //   590: dstore #22
    //   592: dload #24
    //   594: ldc2_w -0.0
    //   597: dcmpl
    //   598: ifne -> 606
    //   601: ldc2_w -1.0E-4
    //   604: dstore #24
    //   606: dload #26
    //   608: ldc2_w -0.0
    //   611: dcmpl
    //   612: ifne -> 620
    //   615: ldc2_w -1.0E-4
    //   618: dstore #26
    //   620: dload #22
    //   622: dload #24
    //   624: dcmpg
    //   625: ifge -> 705
    //   628: dload #22
    //   630: dload #26
    //   632: dcmpg
    //   633: ifge -> 705
    //   636: goto -> 643
    //   639: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   642: athrow
    //   643: iload #4
    //   645: iload #7
    //   647: if_icmple -> 667
    //   650: goto -> 657
    //   653: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   656: athrow
    //   657: getstatic net/minecraft/util/EnumFacing.WEST : Lnet/minecraft/util/EnumFacing;
    //   660: goto -> 670
    //   663: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   666: athrow
    //   667: getstatic net/minecraft/util/EnumFacing.EAST : Lnet/minecraft/util/EnumFacing;
    //   670: astore #34
    //   672: new net/minecraft/util/math/Vec3d
    //   675: dup
    //   676: dload #16
    //   678: aload_1
    //   679: getfield field_72448_b : D
    //   682: dload #30
    //   684: dload #22
    //   686: dmul
    //   687: dadd
    //   688: aload_1
    //   689: getfield field_72449_c : D
    //   692: dload #32
    //   694: dload #22
    //   696: dmul
    //   697: dadd
    //   698: invokespecial <init> : (DDD)V
    //   701: astore_1
    //   702: goto -> 827
    //   705: dload #24
    //   707: dload #26
    //   709: dcmpg
    //   710: ifge -> 775
    //   713: iload #5
    //   715: iload #8
    //   717: if_icmple -> 737
    //   720: goto -> 727
    //   723: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   726: athrow
    //   727: getstatic net/minecraft/util/EnumFacing.DOWN : Lnet/minecraft/util/EnumFacing;
    //   730: goto -> 740
    //   733: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   736: athrow
    //   737: getstatic net/minecraft/util/EnumFacing.UP : Lnet/minecraft/util/EnumFacing;
    //   740: astore #34
    //   742: new net/minecraft/util/math/Vec3d
    //   745: dup
    //   746: aload_1
    //   747: getfield field_72450_a : D
    //   750: dload #28
    //   752: dload #24
    //   754: dmul
    //   755: dadd
    //   756: dload #18
    //   758: aload_1
    //   759: getfield field_72449_c : D
    //   762: dload #32
    //   764: dload #24
    //   766: dmul
    //   767: dadd
    //   768: invokespecial <init> : (DDD)V
    //   771: astore_1
    //   772: goto -> 827
    //   775: iload #6
    //   777: iload #9
    //   779: if_icmple -> 792
    //   782: getstatic net/minecraft/util/EnumFacing.NORTH : Lnet/minecraft/util/EnumFacing;
    //   785: goto -> 795
    //   788: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   791: athrow
    //   792: getstatic net/minecraft/util/EnumFacing.SOUTH : Lnet/minecraft/util/EnumFacing;
    //   795: astore #34
    //   797: new net/minecraft/util/math/Vec3d
    //   800: dup
    //   801: aload_1
    //   802: getfield field_72450_a : D
    //   805: dload #28
    //   807: dload #26
    //   809: dmul
    //   810: dadd
    //   811: aload_1
    //   812: getfield field_72448_b : D
    //   815: dload #30
    //   817: dload #26
    //   819: dmul
    //   820: dadd
    //   821: dload #20
    //   823: invokespecial <init> : (DDD)V
    //   826: astore_1
    //   827: aload_1
    //   828: getfield field_72450_a : D
    //   831: invokestatic func_76128_c : (D)I
    //   834: aload #34
    //   836: getstatic net/minecraft/util/EnumFacing.EAST : Lnet/minecraft/util/EnumFacing;
    //   839: if_acmpne -> 850
    //   842: iconst_1
    //   843: goto -> 851
    //   846: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   849: athrow
    //   850: iconst_0
    //   851: isub
    //   852: istore #7
    //   854: aload_1
    //   855: getfield field_72448_b : D
    //   858: invokestatic func_76128_c : (D)I
    //   861: aload #34
    //   863: getstatic net/minecraft/util/EnumFacing.UP : Lnet/minecraft/util/EnumFacing;
    //   866: if_acmpne -> 877
    //   869: iconst_1
    //   870: goto -> 878
    //   873: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   876: athrow
    //   877: iconst_0
    //   878: isub
    //   879: istore #8
    //   881: aload_1
    //   882: getfield field_72449_c : D
    //   885: invokestatic func_76128_c : (D)I
    //   888: aload #34
    //   890: getstatic net/minecraft/util/EnumFacing.SOUTH : Lnet/minecraft/util/EnumFacing;
    //   893: if_acmpne -> 904
    //   896: iconst_1
    //   897: goto -> 905
    //   900: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   903: athrow
    //   904: iconst_0
    //   905: isub
    //   906: istore #9
    //   908: new net/minecraft/util/math/BlockPos
    //   911: dup
    //   912: iload #7
    //   914: iload #8
    //   916: iload #9
    //   918: invokespecial <init> : (III)V
    //   921: astore #10
    //   923: aload_3
    //   924: aload #10
    //   926: invokevirtual func_180495_p : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   929: astore #35
    //   931: aload #35
    //   933: invokeinterface func_185904_a : ()Lnet/minecraft/block/material/Material;
    //   938: getstatic net/minecraft/block/material/Material.field_151567_E : Lnet/minecraft/block/material/Material;
    //   941: if_acmpeq -> 967
    //   944: aload #35
    //   946: aload_3
    //   947: aload #10
    //   949: invokeinterface func_185890_d : (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/AxisAlignedBB;
    //   954: getstatic net/minecraft/block/Block.field_185506_k : Lnet/minecraft/util/math/AxisAlignedBB;
    //   957: if_acmpeq -> 1007
    //   960: goto -> 967
    //   963: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   966: athrow
    //   967: aload #35
    //   969: invokeinterface func_177230_c : ()Lnet/minecraft/block/Block;
    //   974: invokevirtual func_180664_k : ()Lnet/minecraft/util/BlockRenderLayer;
    //   977: getstatic net/minecraft/util/BlockRenderLayer.SOLID : Lnet/minecraft/util/BlockRenderLayer;
    //   980: if_acmpne -> 1007
    //   983: goto -> 990
    //   986: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   989: athrow
    //   990: aload #35
    //   992: aload_3
    //   993: aload #10
    //   995: aload_1
    //   996: aload_2
    //   997: invokeinterface func_185910_a : (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;
    //   1002: areturn
    //   1003: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1006: athrow
    //   1007: goto -> 238
    //   1010: aconst_null
    //   1011: areturn
    // Exception table:
    //   from	to	target	type
    //   0	20	23	java/lang/IllegalStateException
    //   10	37	40	java/lang/IllegalStateException
    //   27	46	46	java/lang/IllegalStateException
    //   50	70	73	java/lang/IllegalStateException
    //   60	87	90	java/lang/IllegalStateException
    //   77	96	96	java/lang/IllegalStateException
    //   177	209	212	java/lang/IllegalStateException
    //   193	229	229	java/lang/IllegalStateException
    //   238	256	259	java/lang/IllegalStateException
    //   246	273	276	java/lang/IllegalStateException
    //   263	290	293	java/lang/IllegalStateException
    //   280	299	299	java/lang/IllegalStateException
    //   303	317	320	java/lang/IllegalStateException
    //   310	331	334	java/lang/IllegalStateException
    //   324	340	340	java/lang/IllegalStateException
    //   620	636	639	java/lang/IllegalStateException
    //   628	650	653	java/lang/IllegalStateException
    //   643	663	663	java/lang/IllegalStateException
    //   705	720	723	java/lang/IllegalStateException
    //   713	733	733	java/lang/IllegalStateException
    //   775	788	788	java/lang/IllegalStateException
    //   827	846	846	java/lang/IllegalStateException
    //   854	873	873	java/lang/IllegalStateException
    //   881	900	900	java/lang/IllegalStateException
    //   931	960	963	java/lang/IllegalStateException
    //   944	983	986	java/lang/IllegalStateException
    //   967	1003	1003	java/lang/IllegalStateException
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */