package com.schnurritv.sexmod;

import com.google.common.base.Optional;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class m extends Entity {
  public static final int f = 15;
  
  private static final DataParameter<Integer> g = EntityDataManager.func_187226_a(m.class, DataSerializers.field_187192_b).func_187156_b().func_187161_a(111);
  
  private static final DataParameter<Optional<UUID>> l = EntityDataManager.func_187226_a(m.class, DataSerializers.field_187203_m).func_187156_b().func_187161_a(110);
  
  private boolean i;
  
  private int j;
  
  private int d;
  
  public int c;
  
  private int h;
  
  private int a;
  
  private float n;
  
  public Entity e;
  
  private a o = a.FLYING;
  
  private int m;
  
  private int k;
  
  public static aI b = null;
  
  public m(World paramWorld, aI paramaI, double paramDouble) {
    super(paramWorld);
    a(paramaI);
    a(paramDouble);
  }
  
  public m(World paramWorld) {
    super(paramWorld);
  }
  
  private void a(aI paramaI) {
    func_70105_a(0.25F, 0.25F);
    this.field_70158_ak = true;
    paramaI.W = this;
  }
  
  protected void func_70088_a() {
    func_184212_Q().func_187214_a(g, Integer.valueOf(0));
    func_184212_Q().func_187214_a(l, Optional.of(b.p()));
  }
  
  public AxisAlignedBB func_184177_bl() {
    return func_174813_aQ().func_186662_g(10.0D);
  }
  
  aI g() {
    Optional optional = (Optional)this.field_70180_af.func_187225_a(l);
    try {
      if (!optional.isPresent())
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Q q = Q.e((UUID)optional.get());
    try {
      if (q == null)
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!(q instanceof aI))
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (aI)q;
  }
  
  aI e() {
    Optional optional = (Optional)this.field_70180_af.func_187225_a(l);
    try {
      if (!optional.isPresent())
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Q q = Q.c((UUID)optional.get());
    try {
      if (!(q instanceof aI))
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (aI)q;
  }
  
  public void a(int paramInt) {
    this.k = paramInt;
  }
  
  public void b(int paramInt) {
    this.m = paramInt;
  }
  
  public void func_70030_z() {
    try {
      super.func_70030_z();
      if (this.field_70170_p.field_72995_K)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.e == null) {
        try {
          if (this.field_70122_E) {
            try {
              if (this.c == 0) {
                System.out.println("get it back owo");
                g().u();
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.c == 0) {
        System.out.println("get it back owo");
        g().u();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public void a(double paramDouble) {
    aI aI1 = g();
    try {
      if (aI1 == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    BlockPos blockPos = aI1.ad;
    float f1 = (float)Math.sqrt(aI1.func_174791_d().func_186679_c(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
    float f2 = -22.5F + 45.0F * f1 / 7.0F;
    float f3 = aI1.m().floatValue();
    float f4 = MathHelper.func_76134_b(-f3 * 0.017453292F - 3.1415927F);
    float f5 = MathHelper.func_76126_a(-f3 * 0.017453292F - 3.1415927F);
    float f6 = -MathHelper.func_76134_b(-f2 * 0.017453292F);
    float f7 = MathHelper.func_76126_a(-f2 * 0.017453292F);
    double d1 = aI1.field_70169_q + aI1.field_70165_t - aI1.field_70169_q - f5 * 0.3D;
    double d2 = aI1.field_70167_r + aI1.field_70163_u - aI1.field_70167_r + aI1.func_70047_e();
    double d3 = aI1.field_70166_s + aI1.field_70161_v - aI1.field_70166_s - f4 * 0.3D;
    func_70012_b(d1, d2, d3, f3, f2);
    this.field_70159_w = paramDouble * -f5;
    this.field_70181_x = paramDouble * MathHelper.func_76131_a(-(f7 / f6), -5.0F, 5.0F);
    this.field_70179_y = paramDouble * -f4;
    float f8 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
    this.field_70159_w *= 0.6D / f8 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
    this.field_70181_x *= 0.6D / f8 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
    this.field_70179_y *= 0.6D / f8 + 0.5D + this.field_70146_Z.nextGaussian() * 0.0045D;
    float f9 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.29577951308232D);
    this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, f9) * 57.29577951308232D);
    this.field_70126_B = this.field_70177_z;
    this.field_70127_C = this.field_70125_A;
  }
  
  public void func_184206_a(DataParameter<?> paramDataParameter) {
    if (g.equals(paramDataParameter)) {
      int i = ((Integer)func_184212_Q().func_187225_a(g)).intValue();
      try {
      
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      this.e = (i > 0) ? this.field_70170_p.func_73045_a(i - 1) : null;
    } 
    super.func_184206_a(paramDataParameter);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double paramDouble) {
    double d = 64.0D;
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (paramDouble < 4096.0D);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean) {}
  
  public void func_70071_h_() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial func_70071_h_ : ()V
    //   4: aload_0
    //   5: invokevirtual g : ()Lcom/schnurritv/sexmod/aI;
    //   8: ifnonnull -> 22
    //   11: aload_0
    //   12: invokevirtual func_70106_y : ()V
    //   15: goto -> 719
    //   18: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   21: athrow
    //   22: aload_0
    //   23: getfield field_70170_p : Lnet/minecraft/world/World;
    //   26: getfield field_72995_K : Z
    //   29: ifne -> 46
    //   32: aload_0
    //   33: invokespecial a : ()Z
    //   36: ifne -> 719
    //   39: goto -> 46
    //   42: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   45: athrow
    //   46: aload_0
    //   47: getfield i : Z
    //   50: ifeq -> 96
    //   53: goto -> 60
    //   56: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   59: athrow
    //   60: aload_0
    //   61: dup
    //   62: getfield j : I
    //   65: iconst_1
    //   66: iadd
    //   67: putfield j : I
    //   70: aload_0
    //   71: getfield j : I
    //   74: sipush #1200
    //   77: if_icmplt -> 96
    //   80: goto -> 87
    //   83: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   86: athrow
    //   87: aload_0
    //   88: invokevirtual func_70106_y : ()V
    //   91: return
    //   92: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   95: athrow
    //   96: fconst_0
    //   97: fstore_1
    //   98: new net/minecraft/util/math/BlockPos
    //   101: dup
    //   102: aload_0
    //   103: invokespecial <init> : (Lnet/minecraft/entity/Entity;)V
    //   106: astore_2
    //   107: aload_0
    //   108: getfield field_70170_p : Lnet/minecraft/world/World;
    //   111: aload_2
    //   112: invokevirtual func_180495_p : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
    //   115: astore_3
    //   116: aload_3
    //   117: invokeinterface func_185904_a : ()Lnet/minecraft/block/material/Material;
    //   122: getstatic net/minecraft/block/material/Material.field_151586_h : Lnet/minecraft/block/material/Material;
    //   125: if_acmpne -> 138
    //   128: aload_3
    //   129: aload_0
    //   130: getfield field_70170_p : Lnet/minecraft/world/World;
    //   133: aload_2
    //   134: invokestatic func_190973_f : (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)F
    //   137: fstore_1
    //   138: aload_0
    //   139: getfield o : Lcom/schnurritv/sexmod/m$a;
    //   142: getstatic com/schnurritv/sexmod/m$a.FLYING : Lcom/schnurritv/sexmod/m$a;
    //   145: if_acmpne -> 339
    //   148: aload_0
    //   149: getfield e : Lnet/minecraft/entity/Entity;
    //   152: ifnull -> 189
    //   155: goto -> 162
    //   158: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   161: athrow
    //   162: aload_0
    //   163: dconst_0
    //   164: putfield field_70159_w : D
    //   167: aload_0
    //   168: dconst_0
    //   169: putfield field_70181_x : D
    //   172: aload_0
    //   173: dconst_0
    //   174: putfield field_70179_y : D
    //   177: aload_0
    //   178: getstatic com/schnurritv/sexmod/m$a.HOOKED_IN_ENTITY : Lcom/schnurritv/sexmod/m$a;
    //   181: putfield o : Lcom/schnurritv/sexmod/m$a;
    //   184: return
    //   185: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   188: athrow
    //   189: fload_1
    //   190: fconst_0
    //   191: fcmpl
    //   192: ifle -> 243
    //   195: aload_0
    //   196: dup
    //   197: getfield field_70159_w : D
    //   200: ldc2_w 0.3
    //   203: dmul
    //   204: putfield field_70159_w : D
    //   207: aload_0
    //   208: dup
    //   209: getfield field_70181_x : D
    //   212: ldc2_w 0.2
    //   215: dmul
    //   216: putfield field_70181_x : D
    //   219: aload_0
    //   220: dup
    //   221: getfield field_70179_y : D
    //   224: ldc2_w 0.3
    //   227: dmul
    //   228: putfield field_70179_y : D
    //   231: aload_0
    //   232: getstatic com/schnurritv/sexmod/m$a.BOBBING : Lcom/schnurritv/sexmod/m$a;
    //   235: putfield o : Lcom/schnurritv/sexmod/m$a;
    //   238: return
    //   239: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   242: athrow
    //   243: aload_0
    //   244: getfield field_70170_p : Lnet/minecraft/world/World;
    //   247: getfield field_72995_K : Z
    //   250: ifne -> 264
    //   253: aload_0
    //   254: invokespecial h : ()V
    //   257: goto -> 264
    //   260: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   263: athrow
    //   264: aload_0
    //   265: getfield i : Z
    //   268: ifne -> 316
    //   271: aload_0
    //   272: getfield field_70122_E : Z
    //   275: ifne -> 316
    //   278: goto -> 285
    //   281: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   284: athrow
    //   285: aload_0
    //   286: getfield field_70123_F : Z
    //   289: ifne -> 316
    //   292: goto -> 299
    //   295: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   298: athrow
    //   299: aload_0
    //   300: dup
    //   301: getfield d : I
    //   304: iconst_1
    //   305: iadd
    //   306: putfield d : I
    //   309: goto -> 608
    //   312: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   315: athrow
    //   316: aload_0
    //   317: iconst_0
    //   318: putfield d : I
    //   321: aload_0
    //   322: dconst_0
    //   323: putfield field_70159_w : D
    //   326: aload_0
    //   327: dconst_0
    //   328: putfield field_70181_x : D
    //   331: aload_0
    //   332: dconst_0
    //   333: putfield field_70179_y : D
    //   336: goto -> 608
    //   339: aload_0
    //   340: getfield o : Lcom/schnurritv/sexmod/m$a;
    //   343: getstatic com/schnurritv/sexmod/m$a.HOOKED_IN_ENTITY : Lcom/schnurritv/sexmod/m$a;
    //   346: if_acmpne -> 469
    //   349: aload_0
    //   350: getfield e : Lnet/minecraft/entity/Entity;
    //   353: ifnull -> 468
    //   356: goto -> 363
    //   359: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   362: athrow
    //   363: aload_0
    //   364: getfield e : Lnet/minecraft/entity/Entity;
    //   367: getfield field_70128_L : Z
    //   370: ifeq -> 399
    //   373: goto -> 380
    //   376: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   379: athrow
    //   380: aload_0
    //   381: aconst_null
    //   382: putfield e : Lnet/minecraft/entity/Entity;
    //   385: aload_0
    //   386: getstatic com/schnurritv/sexmod/m$a.FLYING : Lcom/schnurritv/sexmod/m$a;
    //   389: putfield o : Lcom/schnurritv/sexmod/m$a;
    //   392: goto -> 468
    //   395: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   398: athrow
    //   399: aload_0
    //   400: aload_0
    //   401: getfield e : Lnet/minecraft/entity/Entity;
    //   404: getfield field_70165_t : D
    //   407: putfield field_70165_t : D
    //   410: aload_0
    //   411: getfield e : Lnet/minecraft/entity/Entity;
    //   414: getfield field_70131_O : F
    //   417: f2d
    //   418: dstore #4
    //   420: aload_0
    //   421: aload_0
    //   422: getfield e : Lnet/minecraft/entity/Entity;
    //   425: invokevirtual func_174813_aQ : ()Lnet/minecraft/util/math/AxisAlignedBB;
    //   428: getfield field_72338_b : D
    //   431: dload #4
    //   433: ldc2_w 0.8
    //   436: dmul
    //   437: dadd
    //   438: putfield field_70163_u : D
    //   441: aload_0
    //   442: aload_0
    //   443: getfield e : Lnet/minecraft/entity/Entity;
    //   446: getfield field_70161_v : D
    //   449: putfield field_70161_v : D
    //   452: aload_0
    //   453: aload_0
    //   454: getfield field_70165_t : D
    //   457: aload_0
    //   458: getfield field_70163_u : D
    //   461: aload_0
    //   462: getfield field_70161_v : D
    //   465: invokevirtual func_70107_b : (DDD)V
    //   468: return
    //   469: aload_0
    //   470: getfield o : Lcom/schnurritv/sexmod/m$a;
    //   473: getstatic com/schnurritv/sexmod/m$a.BOBBING : Lcom/schnurritv/sexmod/m$a;
    //   476: if_acmpne -> 608
    //   479: aload_0
    //   480: dup
    //   481: getfield field_70159_w : D
    //   484: ldc2_w 0.9
    //   487: dmul
    //   488: putfield field_70159_w : D
    //   491: aload_0
    //   492: dup
    //   493: getfield field_70179_y : D
    //   496: ldc2_w 0.9
    //   499: dmul
    //   500: putfield field_70179_y : D
    //   503: aload_0
    //   504: getfield field_70163_u : D
    //   507: aload_0
    //   508: getfield field_70181_x : D
    //   511: dadd
    //   512: aload_2
    //   513: invokevirtual func_177956_o : ()I
    //   516: i2d
    //   517: dsub
    //   518: fload_1
    //   519: f2d
    //   520: dsub
    //   521: dstore #4
    //   523: dload #4
    //   525: invokestatic abs : (D)D
    //   528: ldc2_w 0.01
    //   531: dcmpg
    //   532: ifge -> 549
    //   535: dload #4
    //   537: dload #4
    //   539: invokestatic signum : (D)D
    //   542: ldc2_w 0.1
    //   545: dmul
    //   546: dadd
    //   547: dstore #4
    //   549: aload_0
    //   550: dup
    //   551: getfield field_70181_x : D
    //   554: dload #4
    //   556: aload_0
    //   557: getfield field_70146_Z : Ljava/util/Random;
    //   560: invokevirtual nextFloat : ()F
    //   563: f2d
    //   564: dmul
    //   565: ldc2_w 0.2
    //   568: dmul
    //   569: dsub
    //   570: putfield field_70181_x : D
    //   573: aload_0
    //   574: getfield field_70170_p : Lnet/minecraft/world/World;
    //   577: getfield field_72995_K : Z
    //   580: ifne -> 608
    //   583: fload_1
    //   584: fconst_0
    //   585: fcmpl
    //   586: ifle -> 608
    //   589: goto -> 596
    //   592: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   595: athrow
    //   596: aload_0
    //   597: aload_2
    //   598: invokespecial a : (Lnet/minecraft/util/math/BlockPos;)V
    //   601: goto -> 608
    //   604: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   607: athrow
    //   608: aload_3
    //   609: invokeinterface func_185904_a : ()Lnet/minecraft/block/material/Material;
    //   614: getstatic net/minecraft/block/material/Material.field_151586_h : Lnet/minecraft/block/material/Material;
    //   617: if_acmpeq -> 639
    //   620: aload_0
    //   621: dup
    //   622: getfield field_70181_x : D
    //   625: ldc2_w 0.03
    //   628: dsub
    //   629: putfield field_70181_x : D
    //   632: goto -> 639
    //   635: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   638: athrow
    //   639: aload_0
    //   640: getstatic net/minecraft/entity/MoverType.SELF : Lnet/minecraft/entity/MoverType;
    //   643: aload_0
    //   644: getfield field_70159_w : D
    //   647: aload_0
    //   648: getfield field_70181_x : D
    //   651: aload_0
    //   652: getfield field_70179_y : D
    //   655: invokevirtual func_70091_d : (Lnet/minecraft/entity/MoverType;DDD)V
    //   658: aload_0
    //   659: invokespecial f : ()V
    //   662: ldc2_w 0.92
    //   665: dstore #4
    //   667: aload_0
    //   668: dup
    //   669: getfield field_70159_w : D
    //   672: ldc2_w 0.92
    //   675: dmul
    //   676: putfield field_70159_w : D
    //   679: aload_0
    //   680: dup
    //   681: getfield field_70181_x : D
    //   684: ldc2_w 0.92
    //   687: dmul
    //   688: putfield field_70181_x : D
    //   691: aload_0
    //   692: dup
    //   693: getfield field_70179_y : D
    //   696: ldc2_w 0.92
    //   699: dmul
    //   700: putfield field_70179_y : D
    //   703: aload_0
    //   704: aload_0
    //   705: getfield field_70165_t : D
    //   708: aload_0
    //   709: getfield field_70163_u : D
    //   712: aload_0
    //   713: getfield field_70161_v : D
    //   716: invokevirtual func_70107_b : (DDD)V
    //   719: return
    // Exception table:
    //   from	to	target	type
    //   0	18	18	java/lang/NullPointerException
    //   22	39	42	java/lang/NullPointerException
    //   32	53	56	java/lang/NullPointerException
    //   46	80	83	java/lang/NullPointerException
    //   60	92	92	java/lang/NullPointerException
    //   138	155	158	java/lang/NullPointerException
    //   148	185	185	java/lang/NullPointerException
    //   189	239	239	java/lang/NullPointerException
    //   243	257	260	java/lang/NullPointerException
    //   264	278	281	java/lang/NullPointerException
    //   271	292	295	java/lang/NullPointerException
    //   285	312	312	java/lang/NullPointerException
    //   339	356	359	java/lang/NullPointerException
    //   349	373	376	java/lang/NullPointerException
    //   363	395	395	java/lang/NullPointerException
    //   549	589	592	java/lang/NullPointerException
    //   583	601	604	java/lang/NullPointerException
    //   608	632	635	java/lang/NullPointerException
  }
  
  private boolean a() {
    return false;
  }
  
  private void f() {
    float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.29577951308232D);
    this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, f) * 57.29577951308232D);
    try {
      while (this.field_70125_A - this.field_70127_C < -180.0F)
        this.field_70127_C -= 360.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      while (this.field_70125_A - this.field_70127_C >= 180.0F)
        this.field_70127_C += 360.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      while (this.field_70177_z - this.field_70126_B < -180.0F)
        this.field_70126_B -= 360.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      while (this.field_70177_z - this.field_70126_B >= 180.0F)
        this.field_70126_B += 360.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
    this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
  }
  
  private void h() {
    Vec3d vec3d1 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    Vec3d vec3d2 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
    RayTraceResult rayTraceResult = this.field_70170_p.func_147447_a(vec3d1, vec3d2, false, true, false);
    vec3d1 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    vec3d2 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
    if (rayTraceResult != null)
      vec3d2 = new Vec3d(rayTraceResult.field_72307_f.field_72450_a, rayTraceResult.field_72307_f.field_72448_b, rayTraceResult.field_72307_f.field_72449_c); 
    Entity entity = null;
    List list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0D));
    double d = 0.0D;
    for (Entity entity1 : list) {
      try {
        if (a(entity1))
          try {
            if (entity1 != g() || this.d >= 5) {
              AxisAlignedBB axisAlignedBB = entity1.func_174813_aQ().func_186662_g(0.30000001192092896D);
              RayTraceResult rayTraceResult1 = axisAlignedBB.func_72327_a(vec3d1, vec3d2);
              if (rayTraceResult1 != null) {
                double d1 = vec3d1.func_72436_e(rayTraceResult1.field_72307_f);
                try {
                  if (d1 < d || d == 0.0D) {
                    entity = entity1;
                    d = d1;
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
              } 
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    if (entity != null)
      rayTraceResult = new RayTraceResult(entity); 
    try {
      if (rayTraceResult != null)
        try {
          if (rayTraceResult.field_72313_a != RayTraceResult.Type.MISS)
            try {
              if (rayTraceResult.field_72313_a == RayTraceResult.Type.ENTITY) {
                this.e = rayTraceResult.field_72308_g;
                c();
              } else {
                this.i = true;
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private void c() {
    func_184212_Q().func_187227_b(g, Integer.valueOf(this.e.func_145782_y() + 1));
  }
  
  private void a(BlockPos paramBlockPos) {
    WorldServer worldServer = (WorldServer)this.field_70170_p;
    byte b = 1;
    BlockPos blockPos = paramBlockPos.func_177984_a();
    try {
      if (this.field_70146_Z.nextFloat() < 0.25F)
        try {
          if (this.field_70170_p.func_175727_C(blockPos))
            b++; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.field_70146_Z.nextFloat() < 0.5F)
        try {
          if (!this.field_70170_p.func_175678_i(blockPos))
            b--; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.c > 0) {
        try {
          this.c--;
          if (this.c <= 0) {
            this.h = 0;
            this.a = 0;
          } else {
            this.field_70181_x -= 0.2D * this.field_70146_Z.nextFloat() * this.field_70146_Z.nextFloat();
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        try {
          if (this.a > 0) {
            this.a -= b;
            if (this.a > 0) {
              this.n = (float)(this.n + this.field_70146_Z.nextGaussian() * 4.0D);
              float f1 = this.n * 0.017453292F;
              float f2 = MathHelper.func_76126_a(f1);
              float f3 = MathHelper.func_76134_b(f1);
              double d1 = this.field_70165_t + (f2 * this.a * 0.1F);
              double d2 = (MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) + 1.0F);
              double d3 = this.field_70161_v + (f3 * this.a * 0.1F);
              IBlockState iBlockState = worldServer.func_180495_p(new BlockPos(d1, d2 - 1.0D, d3));
              try {
                if (iBlockState.func_185904_a() == Material.field_151586_h) {
                  try {
                    if (this.field_70146_Z.nextFloat() < 0.15F)
                      worldServer.func_175739_a(EnumParticleTypes.WATER_BUBBLE, d1, d2 - 0.10000000149011612D, d3, 1, f2, 0.1D, f3, 0.0D, new int[0]); 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                  float f4 = f2 * 0.04F;
                  float f5 = f3 * 0.04F;
                  worldServer.func_175739_a(EnumParticleTypes.WATER_WAKE, d1, d2, d3, 0, f5, 0.01D, -f4, 1.0D, new int[0]);
                  worldServer.func_175739_a(EnumParticleTypes.WATER_WAKE, d1, d2, d3, 0, -f5, 0.01D, f4, 1.0D, new int[0]);
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
            } else {
              this.field_70181_x = (-0.4F * MathHelper.func_151240_a(this.field_70146_Z, 0.6F, 1.0F));
              func_184185_a(SoundEvents.field_187609_F, 0.25F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
              double d = (func_174813_aQ()).field_72338_b + 0.5D;
              worldServer.func_175739_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, d, this.field_70161_v, (int)(1.0F + this.field_70130_N * 20.0F), this.field_70130_N, 0.0D, this.field_70130_N, 0.20000000298023224D, new int[0]);
              worldServer.func_175739_a(EnumParticleTypes.WATER_WAKE, this.field_70165_t, d, this.field_70161_v, (int)(1.0F + this.field_70130_N * 20.0F), this.field_70130_N, 0.0D, this.field_70130_N, 0.20000000298023224D, new int[0]);
              this.c = MathHelper.func_76136_a(this.field_70146_Z, 20, 40);
            } 
          } else if (this.h > 0) {
            this.h -= b;
            float f = 0.15F;
            if (this.h < 20) {
              f = (float)(f + (20 - this.h) * 0.05D);
            } else if (this.h < 40) {
              f = (float)(f + (40 - this.h) * 0.02D);
            } else if (this.h < 60) {
              f = (float)(f + (60 - this.h) * 0.01D);
            } 
            if (this.field_70146_Z.nextFloat() < f) {
              float f1 = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F) * 0.017453292F;
              float f2 = MathHelper.func_151240_a(this.field_70146_Z, 25.0F, 60.0F);
              double d1 = this.field_70165_t + (MathHelper.func_76126_a(f1) * f2 * 0.1F);
              double d2 = (MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) + 1.0F);
              double d3 = this.field_70161_v + (MathHelper.func_76134_b(f1) * f2 * 0.1F);
              IBlockState iBlockState = worldServer.func_180495_p(new BlockPos((int)d1, (int)d2 - 1, (int)d3));
              try {
                if (iBlockState.func_185904_a() == Material.field_151586_h)
                  worldServer.func_175739_a(EnumParticleTypes.WATER_SPLASH, d1, d2, d3, 2 + this.field_70146_Z.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
            } 
            try {
              if (this.h <= 0) {
                this.n = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F);
                this.a = MathHelper.func_76136_a(this.field_70146_Z, 20, 80);
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            this.h = MathHelper.func_76136_a(this.field_70146_Z, 100, 600);
            this.h -= this.k * 20 * 5;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  protected boolean a(Entity paramEntity) {
    try {
      if (!paramEntity.func_70067_L()) {
        try {
          if (paramEntity instanceof net.minecraft.entity.item.EntityItem);
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return false;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public void func_70014_b(NBTTagCompound paramNBTTagCompound) {}
  
  public void func_70037_a(NBTTagCompound paramNBTTagCompound) {}
  
  public int b() {
    try {
      if (!this.field_70170_p.field_72995_K && g() != null) {
        byte b = 0;
        ItemFishedEvent itemFishedEvent = null;
        try {
          if (this.e != null) {
            try {
              d();
              this.field_70170_p.func_72960_a(this, (byte)31);
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            b = (this.e instanceof net.minecraft.entity.item.EntityItem) ? 3 : 5;
          } else if (this.c > 0) {
            LootContext.Builder builder = new LootContext.Builder((WorldServer)this.field_70170_p);
            List list = this.field_70170_p.func_184146_ak().func_186521_a(LootTableList.field_186387_al).func_186462_a(this.field_70146_Z, builder.func_186471_a());
            for (ItemStack itemStack : list) {
              aI aI1 = g();
              aI1.b(itemStack);
            } 
            this.c = 9999;
            b = 1;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        if (this.i)
          b = 2; 
        try {
        
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return (itemFishedEvent == null) ? b : itemFishedEvent.getRodDamage();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return 0;
  }
  
  protected void d() {
    aI aI1 = g();
    if (aI1 != null) {
      double d1 = aI1.field_70165_t - this.field_70165_t;
      double d2 = aI1.field_70163_u - this.field_70163_u;
      double d3 = aI1.field_70161_v - this.field_70161_v;
      double d4 = 0.1D;
      this.e.field_70159_w += d1 * 0.1D;
      this.e.field_70181_x += d2 * 0.1D;
      this.e.field_70179_y += d3 * 0.1D;
    } 
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public void func_70020_e(NBTTagCompound paramNBTTagCompound) {}
  
  public NBTTagCompound func_189511_e(NBTTagCompound paramNBTTagCompound) {
    return null;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  enum a {
    FLYING, HOOKED_IN_ENTITY, BOBBING;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\m.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */