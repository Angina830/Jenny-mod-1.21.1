package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Vector2f;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.util.MatrixStack;

public class ag extends aJ {
  public static final Vec3i K = new Vec3i(11, 6, 11);
  
  public static final Vec3d V = new Vec3d(5.0D, 1.0D, 9.0D);
  
  public static final Vec3d ai = new Vec3d(3.0D, -1.0D, 6.0D);
  
  public static final Vec3d aP = new Vec3d(1.0D, 1.0D, 5.0D);
  
  public static final Vec3d T = new Vec3d(-6.0D, -1.0D, 3.0D);
  
  public static final Vec3d O = new Vec3d(5.0D, 1.0D, 1.0D);
  
  public static final Vec3d M = new Vec3d(-3.0D, -1.0D, -6.0D);
  
  public static final Vec3d aU = new Vec3d(9.0D, 1.0D, 5.0D);
  
  public static final Vec3d N = new Vec3d(0.0D, -1.0D, -4.0D);
  
  public static final Vec3d aw = new Vec3d(1.0D, -1.0D, -3.0D);
  
  public static final Vec3d aq = new Vec3d(-1.0D, -1.0D, -3.0D);
  
  public static final Vec3d aa = new Vec3d(6.0D, -1.0D, -3.0D);
  
  public static final int aC = 39;
  
  public static final int U = 15;
  
  public static final int ay = 8400;
  
  static final int L = 45;
  
  static final int aI = 32000;
  
  static final int an = 26;
  
  static final int am = 205;
  
  static final int aL = 100;
  
  static final int af = 1200;
  
  static final int ad = 30;
  
  static final int al = 37;
  
  static final float ak = 2.0F;
  
  static final int aH = 5;
  
  static final int J = 100;
  
  static final int ao = 20;
  
  static final float S = 0.825F;
  
  static final Vector2f av = new Vector2f(0.5F, 0.99F);
  
  static final HashSet<Item> aD = new HashSet<>(Arrays.asList(new Item[] { 
          Items.field_151013_M, Items.field_151136_bY, Items.field_151043_k, Items.field_151153_ao, Items.field_151006_E, Items.field_151011_C, Items.field_151005_D, Items.field_151010_B, Items.field_151150_bK, (Item)Items.field_151169_ag, 
          (Item)Items.field_151151_aj, (Item)Items.field_151171_ah, (Item)Items.field_151149_ai, Items.field_151043_k, Items.field_151074_bl, Item.func_150898_a(Blocks.field_150340_R), Item.func_150898_a(Blocks.field_150352_o) }));
  
  public static final DataParameter<String> aJ = EntityDataManager.func_187226_a(ag.class, DataSerializers.field_187194_d).func_187156_b().func_187161_a(114);
  
  public static final DataParameter<String> X = EntityDataManager.func_187226_a(ag.class, DataSerializers.field_187194_d).func_187156_b().func_187161_a(115);
  
  public static final DataParameter<ItemStack> aj = EntityDataManager.func_187226_a(ag.class, DataSerializers.field_187196_f).func_187156_b().func_187161_a(116);
  
  public static final DataParameter<Boolean> ae = EntityDataManager.func_187226_a(ag.class, DataSerializers.field_187198_h).func_187156_b().func_187161_a(117);
  
  public static final DataParameter<Boolean> aA = EntityDataManager.func_187226_a(ag.class, DataSerializers.field_187198_h).func_187156_b().func_187161_a(118);
  
  public boolean ag = false;
  
  public boolean aG = false;
  
  public float Y = 0.0F;
  
  public long ah = -1L;
  
  public Vec3d aM = Vec3d.field_186680_a;
  
  List<UUID> aN = new ArrayList<>();
  
  int W = 31520;
  
  int az = -1;
  
  public int ab = -1;
  
  boolean au = false;
  
  BlockPos aB = null;
  
  int aF = 0;
  
  int aK = 0;
  
  int ar = 0;
  
  int aE = -1;
  
  int aS = 0;
  
  long ax = 0L;
  
  List<ag> R = new ArrayList<>();
  
  int at = -1;
  
  int ac = -1;
  
  b1 ap = null;
  
  public float as = 1.0F;
  
  int aR = -1;
  
  MatrixStack aO = new MatrixStack();
  
  boolean aQ = true;
  
  boolean Z = true;
  
  boolean P = false;
  
  String aT = "";
  
  boolean Q = false;
  
  public ag(World paramWorld) {
    super(paramWorld);
    func_70105_a(av.x, av.y);
  }
  
  public ag(World paramWorld, @Nonnull String paramString, int paramInt) {
    this(paramWorld);
    this.m.func_187227_b(X, paramString);
    this.m.func_187227_b(I, a(new StringBuilder(), paramInt));
  }
  
  public ag(World paramWorld, boolean paramBoolean, float paramFloat, Vec3d paramVec3d) {
    this(paramWorld);
    if (!paramBoolean)
      return; 
    this.m.func_187227_b(I, a(new StringBuilder()));
    this.Y = paramFloat;
    this.aM = paramVec3d;
    this.aG = true;
    a(paramVec3d);
    a(paramFloat);
    b(b1.SIT);
    a(true);
    func_70107_b(paramVec3d.field_72450_a, paramVec3d.field_72448_b, paramVec3d.field_72449_c);
  }
  
  public void d() {
    super.d();
    a((UUID)null);
    this.field_70145_X = false;
    func_189654_d(false);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.m.func_187214_a(aJ, "");
    this.m.func_187214_a(X, "");
    this.m.func_187214_a(aj, ItemStack.field_190927_a);
    this.m.func_187214_a(ae, Boolean.valueOf(false));
    this.m.func_187214_a(aA, Boolean.valueOf(false));
  }
  
  public void func_70106_y() {
    try {
      super.func_70106_y();
      a((UUID)null);
      if (this.field_70170_p.field_72995_K)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    ItemStack itemStack = (ItemStack)this.m.func_187225_a(aj);
    try {
      if (itemStack == ItemStack.field_190927_a)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityItem entityItem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, itemStack);
    this.field_70170_p.func_72838_d((Entity)entityItem);
  }
  
  public void a(String paramString, UUID paramUUID) {
    try {
      if ("take ur stuff back".equals(paramString))
        b(b1.START_THROWING); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if ("use her".equals(paramString))
        c(paramUUID); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  public void c(UUID paramUUID) {
    this.at = 0;
    a5.a();
    bf.a(false);
    d(paramUUID);
  }
  
  public void b(UUID paramUUID) {
    this.ac = 0;
    a5.a();
    bf.a(false);
    d(paramUUID);
  }
  
  public String F() {
    return "Goblin";
  }
  
  public float func_70047_e() {
    return 0.75F;
  }
  
  public float z() {
    return 0.1F;
  }
  
  public void a(UUID paramUUID) {
    try {
      if (paramUUID == null) {
        this.m.func_187227_b(aJ, "");
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.m.func_187227_b(aJ, paramUUID.toString());
  }
  
  @Nullable
  public UUID r() {
    String str = (String)this.m.func_187225_a(aJ);
    try {
      if ("".equals(str))
        return null; 
    } catch (Exception exception) {
      throw a(null);
    } 
    try {
      return UUID.fromString((String)this.m.func_187225_a(aJ));
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  protected String a(StringBuilder paramStringBuilder) {
    a(paramStringBuilder, 3);
    a(paramStringBuilder, 2);
    a(paramStringBuilder, 2);
    c(paramStringBuilder, 99);
    c(paramStringBuilder, 99);
    a(paramStringBuilder, 5);
    a(paramStringBuilder, (aA.values()).length - 1);
    a(paramStringBuilder, (x.values()).length - 1);
    a(paramStringBuilder, (K.values()).length - 1);
    c(paramStringBuilder, 1);
    return paramStringBuilder.toString();
  }
  
  protected String b(StringBuilder paramStringBuilder) {
    a(paramStringBuilder, 3);
    a(paramStringBuilder, 2);
    a(paramStringBuilder, 2);
    a(paramStringBuilder, 99);
    a(paramStringBuilder, 99);
    a(paramStringBuilder, 5);
    a(paramStringBuilder, (aA.values()).length - 1);
    a(paramStringBuilder, (x.values()).length - 1);
    a(paramStringBuilder, (K.values()).length - 1);
    c(paramStringBuilder, 0);
    return paramStringBuilder.toString();
  }
  
  protected String a(StringBuilder paramStringBuilder, int paramInt) {
    a(paramStringBuilder, 3);
    a(paramStringBuilder, 2);
    a(paramStringBuilder, 2);
    a(paramStringBuilder, 99);
    a(paramStringBuilder, 99);
    a(paramStringBuilder, 5);
    a(paramStringBuilder, (aA.values()).length - 1);
    c(paramStringBuilder, paramInt);
    a(paramStringBuilder, (K.values()).length - 1);
    c(paramStringBuilder, 0);
    return paramStringBuilder.toString();
  }
  
  public void func_70014_b(NBTTagCompound paramNBTTagCompound) {
    try {
      super.func_70014_b(paramNBTTagCompound);
      paramNBTTagCompound.func_74778_a("bodyColor", (String)this.m.func_187225_a(G));
      paramNBTTagCompound.func_74768_a("eyeColorX", ((BlockPos)this.m.func_187225_a(H)).func_177958_n());
      paramNBTTagCompound.func_74768_a("eyeColorY", ((BlockPos)this.m.func_187225_a(H)).func_177956_o());
      paramNBTTagCompound.func_74768_a("eyeColorZ", ((BlockPos)this.m.func_187225_a(H)).func_177952_p());
      paramNBTTagCompound.func_74778_a("model", (String)this.m.func_187225_a(I));
      paramNBTTagCompound.func_74778_a("girlID", (String)this.m.func_187225_a(B));
      paramNBTTagCompound.func_74778_a("queen", (String)this.m.func_187225_a(X));
      paramNBTTagCompound.func_74757_a("isQueen", this.aG);
      paramNBTTagCompound.func_74757_a("isTamed", ((Boolean)this.m.func_187225_a(ae)).booleanValue());
      paramNBTTagCompound.func_74768_a("robTicks", this.W);
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    paramNBTTagCompound.func_74757_a("preggo", ((Boolean)this.m.func_187225_a(aA)).booleanValue());
    paramNBTTagCompound.func_74776_a("throneRot", this.Y);
    paramNBTTagCompound.func_74780_a("thronePosX", this.aM.field_72450_a);
    paramNBTTagCompound.func_74780_a("thronePosY", this.aM.field_72448_b);
    paramNBTTagCompound.func_74780_a("thronePosZ", this.aM.field_72449_c);
    paramNBTTagCompound.func_74772_a("impregnationTick", this.ah);
    byte b = 0;
    try {
      while (b < this.aN.size()) {
        paramNBTTagCompound.func_74778_a("guard" + b, ((UUID)this.aN.get(b)).toString());
        b++;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  public void func_70037_a(NBTTagCompound paramNBTTagCompound) {
    try {
      super.func_70037_a(paramNBTTagCompound);
      this.m.func_187227_b(G, paramNBTTagCompound.func_74779_i("bodyColor"));
      this.m.func_187227_b(H, new BlockPos(paramNBTTagCompound.func_74762_e("eyeColorX"), paramNBTTagCompound.func_74762_e("eyeColorY"), paramNBTTagCompound.func_74762_e("eyeColorZ")));
      this.m.func_187227_b(I, paramNBTTagCompound.func_74779_i("model"));
      this.m.func_187227_b(B, paramNBTTagCompound.func_74779_i("girlID"));
      this.m.func_187227_b(X, paramNBTTagCompound.func_74779_i("queen"));
      this.m.func_187227_b(ae, Boolean.valueOf(paramNBTTagCompound.func_74767_n("isTamed")));
      this.aG = paramNBTTagCompound.func_74767_n("isQueen");
      this.W = paramNBTTagCompound.func_74762_e("robTicks");
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.Y = paramNBTTagCompound.func_74760_g("throneRot");
    this.aM = new Vec3d(paramNBTTagCompound.func_74769_h("thronePosX"), paramNBTTagCompound.func_74769_h("thronePosY"), paramNBTTagCompound.func_74769_h("thronePosZ"));
    byte b = 0;
    try {
      while (!"".equals(paramNBTTagCompound.func_74779_i("guard" + b))) {
        this.aN.add(UUID.fromString(paramNBTTagCompound.func_74779_i("guard" + b)));
        b++;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.m.func_187227_b(aA, Boolean.valueOf(paramNBTTagCompound.func_74767_n("preggo")));
    this.ah = paramNBTTagCompound.func_74763_f("impregnationTick");
  }
  
  protected boolean func_184645_a(EntityPlayer paramEntityPlayer, EnumHand paramEnumHand) {
    try {
      if (this.field_70170_p.field_72995_K)
        return true; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.aG)
        return true; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() == b1.RUN) {
        try {
          if (func_70032_d((Entity)paramEntityPlayer) > 3.5D) {
            paramEntityPlayer.func_146105_b((ITextComponent)new TextComponentString("get a bit closer..."), true);
          } else {
            a(paramEntityPlayer.func_174791_d());
            a(paramEntityPlayer.field_70177_z);
            b(b1.CATCH);
            this.m.func_187227_b(f, "bj");
            a(paramEntityPlayer.getPersistentID());
            d(paramEntityPlayer.getPersistentID());
            func_70661_as().func_75499_g();
            this.field_70159_w = 0.0D;
            this.field_70181_x = 0.0D;
            this.field_70179_y = 0.0D;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        return true;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (d(paramEntityPlayer.getPersistentID())) {
        paramEntityPlayer.func_146105_b((ITextComponent)new TextComponentString("you are already carrying a Goblin"), true);
      } else {
        a(paramEntityPlayer.getPersistentID());
        b(b1.PICK_UP);
        this.az = 45;
        a(false);
        this.m.func_187227_b(ae, Boolean.valueOf(true));
        func_70661_as().func_75499_g();
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return true;
  }
  
  boolean d(UUID paramUUID) {
    try {
      if (paramUUID == null)
        return false; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      for (Q q : Q.f()) {
        try {
          if (!(q instanceof ag))
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (q.field_70170_p.field_72995_K)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (!q.field_70128_L && paramUUID.equals(((ag)q).r()))
            return true; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {}
    return false;
  }
  
  protected void func_184651_r() {
    this.w = new k((EntityLiving)this, (Class)EntityPlayer.class, 2.0F, 1.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(3, new b4((EntityLiving)this));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)this.w);
  }
  
  public void func_70619_bc() {
    super.func_70619_bc();
    y();
    C();
    m();
    T();
    f();
    O();
    q();
    o();
    x();
    F();
    H();
    J();
    z();
    N();
  }
  
  public boolean func_70067_L() {
    b1 b11 = h();
    try {
      if (b11 == b1.RUN)
        return super.func_70067_L(); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.AWAIT_PICK_UP)
        return super.func_70067_L(); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (r() != null)
        return false; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (b11 != b1.NULL)
        return false; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return super.func_70067_L();
  }
  
  void b(EntityPlayer paramEntityPlayer) {
    Vec3d vec3d1 = new Vec3d(paramEntityPlayer.field_70165_t, paramEntityPlayer.field_70163_u + paramEntityPlayer.eyeHeight, paramEntityPlayer.field_70161_v);
    Vec3d vec3d2 = new Vec3d(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v);
    double d1 = vec3d2.func_72438_d(vec3d1);
    double d2 = vec3d1.field_72448_b - vec3d2.field_72448_b;
    this.field_70125_A = (float)-(Math.sin(d2 / d1) * 57.29577951308232D);
  }
  
  void N() {
    // Byte code:
    //   0: aload_0
    //   1: getfield m : Lnet/minecraft/network/datasync/EntityDataManager;
    //   4: getstatic com/schnurritv/sexmod/ag.ae : Lnet/minecraft/network/datasync/DataParameter;
    //   7: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   10: checkcast java/lang/Boolean
    //   13: invokevirtual booleanValue : ()Z
    //   16: ifne -> 24
    //   19: return
    //   20: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   23: athrow
    //   24: aload_0
    //   25: invokevirtual B : ()Ljava/util/UUID;
    //   28: ifnull -> 36
    //   31: return
    //   32: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   35: athrow
    //   36: aload_0
    //   37: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   40: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   43: if_acmpeq -> 51
    //   46: return
    //   47: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   50: athrow
    //   51: aload_0
    //   52: getfield field_70170_p : Lnet/minecraft/world/World;
    //   55: aload_0
    //   56: ldc2_w 15.0
    //   59: invokevirtual func_72890_a : (Lnet/minecraft/entity/Entity;D)Lnet/minecraft/entity/player/EntityPlayer;
    //   62: astore_1
    //   63: aload_1
    //   64: ifnull -> 101
    //   67: aload_1
    //   68: aload_0
    //   69: invokevirtual func_70032_d : (Lnet/minecraft/entity/Entity;)F
    //   72: fconst_2
    //   73: fcmpg
    //   74: ifge -> 101
    //   77: goto -> 84
    //   80: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   83: athrow
    //   84: aload_0
    //   85: aload_1
    //   86: invokevirtual b : (Lnet/minecraft/entity/player/EntityPlayer;)V
    //   89: aload_0
    //   90: invokevirtual func_70661_as : ()Lnet/minecraft/pathfinding/PathNavigate;
    //   93: invokevirtual func_75499_g : ()V
    //   96: return
    //   97: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   100: athrow
    //   101: aload_0
    //   102: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   105: ifnull -> 167
    //   108: aload_0
    //   109: aload_0
    //   110: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   113: invokevirtual func_177958_n : ()I
    //   116: i2d
    //   117: aload_0
    //   118: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   121: invokevirtual func_177956_o : ()I
    //   124: i2d
    //   125: aload_0
    //   126: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   129: invokevirtual func_177952_p : ()I
    //   132: i2d
    //   133: invokevirtual func_70011_f : (DDD)D
    //   136: aload_0
    //   137: invokevirtual p : ()D
    //   140: dcmpl
    //   141: ifgt -> 167
    //   144: goto -> 151
    //   147: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   150: athrow
    //   151: aload_0
    //   152: getfield aF : I
    //   155: bipush #100
    //   157: if_icmple -> 295
    //   160: goto -> 167
    //   163: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   166: athrow
    //   167: aload_0
    //   168: invokevirtual func_70681_au : ()Ljava/util/Random;
    //   171: invokevirtual nextBoolean : ()Z
    //   174: ifeq -> 192
    //   177: goto -> 184
    //   180: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   183: athrow
    //   184: iconst_1
    //   185: goto -> 193
    //   188: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   191: athrow
    //   192: iconst_m1
    //   193: aload_0
    //   194: invokevirtual func_70681_au : ()Ljava/util/Random;
    //   197: iconst_5
    //   198: invokevirtual nextInt : (I)I
    //   201: imul
    //   202: istore_2
    //   203: aload_0
    //   204: invokevirtual func_70681_au : ()Ljava/util/Random;
    //   207: invokevirtual nextBoolean : ()Z
    //   210: ifeq -> 221
    //   213: iconst_1
    //   214: goto -> 222
    //   217: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   220: athrow
    //   221: iconst_m1
    //   222: aload_0
    //   223: invokevirtual func_70681_au : ()Ljava/util/Random;
    //   226: iconst_5
    //   227: invokevirtual nextInt : (I)I
    //   230: imul
    //   231: istore_3
    //   232: aload_0
    //   233: getfield field_70170_p : Lnet/minecraft/world/World;
    //   236: aload_0
    //   237: invokevirtual func_180425_c : ()Lnet/minecraft/util/math/BlockPos;
    //   240: invokevirtual func_177958_n : ()I
    //   243: iload_2
    //   244: iadd
    //   245: aload_0
    //   246: invokevirtual func_180425_c : ()Lnet/minecraft/util/math/BlockPos;
    //   249: invokevirtual func_177952_p : ()I
    //   252: iload_3
    //   253: iadd
    //   254: invokestatic a : (Lnet/minecraft/world/World;II)I
    //   257: istore #4
    //   259: aload_0
    //   260: new net/minecraft/util/math/BlockPos
    //   263: dup
    //   264: aload_0
    //   265: invokevirtual func_180425_c : ()Lnet/minecraft/util/math/BlockPos;
    //   268: invokevirtual func_177958_n : ()I
    //   271: iload_2
    //   272: iadd
    //   273: iload #4
    //   275: aload_0
    //   276: invokevirtual func_180425_c : ()Lnet/minecraft/util/math/BlockPos;
    //   279: invokevirtual func_177952_p : ()I
    //   282: iload_3
    //   283: iadd
    //   284: invokespecial <init> : (III)V
    //   287: putfield aB : Lnet/minecraft/util/math/BlockPos;
    //   290: aload_0
    //   291: iconst_0
    //   292: putfield aF : I
    //   295: aload_0
    //   296: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   299: aload_0
    //   300: invokevirtual func_180425_c : ()Lnet/minecraft/util/math/BlockPos;
    //   303: invokevirtual func_177951_i : (Lnet/minecraft/util/math/Vec3i;)D
    //   306: invokestatic sqrt : (D)D
    //   309: ldc2_w 2.0
    //   312: dcmpl
    //   313: ifle -> 362
    //   316: aload_0
    //   317: invokevirtual func_70661_as : ()Lnet/minecraft/pathfinding/PathNavigate;
    //   320: aload_0
    //   321: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   324: invokevirtual func_177958_n : ()I
    //   327: i2d
    //   328: aload_0
    //   329: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   332: invokevirtual func_177956_o : ()I
    //   335: i2d
    //   336: aload_0
    //   337: getfield aB : Lnet/minecraft/util/math/BlockPos;
    //   340: invokevirtual func_177952_p : ()I
    //   343: i2d
    //   344: ldc2_w 0.30000001192092896
    //   347: invokevirtual func_75492_a : (DDDD)Z
    //   350: pop
    //   351: aload_0
    //   352: invokevirtual c : ()V
    //   355: goto -> 372
    //   358: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   361: athrow
    //   362: aload_0
    //   363: dup
    //   364: getfield aF : I
    //   367: iconst_1
    //   368: iadd
    //   369: putfield aF : I
    //   372: return
    // Exception table:
    //   from	to	target	type
    //   0	20	20	java/util/ConcurrentModificationException
    //   24	32	32	java/util/ConcurrentModificationException
    //   36	47	47	java/util/ConcurrentModificationException
    //   63	77	80	java/util/ConcurrentModificationException
    //   67	97	97	java/util/ConcurrentModificationException
    //   101	144	147	java/util/ConcurrentModificationException
    //   108	160	163	java/util/ConcurrentModificationException
    //   151	177	180	java/util/ConcurrentModificationException
    //   167	188	188	java/util/ConcurrentModificationException
    //   203	217	217	java/util/ConcurrentModificationException
    //   295	358	358	java/util/ConcurrentModificationException
  }
  
  double p() {
    return Math.sqrt(800.0D);
  }
  
  void z() {
    try {
      if (h() != b1.STAND_UP)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.aK < 37)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.aK = 0;
    b(b1.NULL);
  }
  
  void J() {
    try {
      if (h() != b1.THROWN)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!this.field_70122_E)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.ar < 30)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.ar = 0;
    b(b1.STAND_UP);
  }
  
  void H() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!((Boolean)this.m.func_187225_a(aA)).booleanValue())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.ah + 8400L < this.field_70170_p.func_82737_E())
        this.m.func_187227_b(aA, Boolean.valueOf(false)); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  void F() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.R.isEmpty())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    boolean bool = false;
    for (ag ag1 : this.R) {
      if (((Boolean)ag1.func_184212_Q().func_187225_a(ae)).booleanValue())
        bool = true; 
    } 
    try {
      if (!bool)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    b("Farewell my knight. You are welcome once I am breedable again.");
    for (ag ag1 : this.R) {
      try {
        if (!((Boolean)ag1.func_184212_Q().func_187225_a(ae)).booleanValue())
          ag1.b(b1.VANISH); 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
    } 
    this.R.clear();
    d((UUID)null);
  }
  
  void x() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.aR == -1)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.aR < 100)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.aR = -1;
    UUID uUID = B();
    try {
      if (uUID == null) {
        D();
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null) {
        D();
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    d((UUID)null);
    for (ag ag3 : this.R)
      ag3.d((UUID)null); 
    List<ag> list = P();
    float f = this.Y + 180.0F;
    Vec3d vec3d1 = this.aM.func_178787_e(a(aw, f));
    Vec3d vec3d2 = this.aM.func_178787_e(a(aq, f));
    Vec3d vec3d3 = this.aM.func_178787_e(a(N, f));
    ag ag1 = list.get(0);
    ag ag2 = list.get(1);
    ag1.a(vec3d1);
    ag2.a(vec3d2);
    ag1.a(0.0F);
    ag2.a(0.0F);
    ag1.a(true);
    ag2.a(true);
    ag1.b(b1.AWAIT_PICK_UP);
    ag2.b(b1.AWAIT_PICK_UP);
    ag1.func_189654_d(false);
    ag2.func_189654_d(false);
    entityPlayer.func_189654_d(false);
    ag1.field_70145_X = false;
    ag2.field_70145_X = false;
    entityPlayer.field_70145_X = false;
    entityPlayer.field_70177_z = f;
    entityPlayer.field_70125_A = 30.0F;
    entityPlayer.func_70634_a(vec3d3.field_72450_a, vec3d3.field_72448_b, vec3d3.field_72449_c);
    bn.a.sendTo(new b5(true), (EntityPlayerMP)entityPlayer);
    b("Thanks to you, my clan is soon going to get a few new members! In return I will bear of one of my guards to serve as your personal Onahole. Choose wisely~");
  }
  
  void o() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.aE == -1)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.aE < 205)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.aE = -1;
    UUID uUID = B();
    try {
      if (uUID == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Vec3d vec3d = a(new Vec3d(0.0D, 0.15625D - entityPlayer.func_70047_e(), -0.8859375D), this.Y - 180.0F);
    vec3d = vec3d.func_178787_e(x());
    entityPlayer.func_70634_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
  }
  
  public static Vec3d a(Vec3d paramVec3d, float paramFloat) {
    return a(paramVec3d, 0.0F, paramFloat);
  }
  
  public static Vec3d a(Vec3d paramVec3d, float paramFloat1, float paramFloat2) {
    Vec3d vec3d = new Vec3d(paramVec3d.field_72450_a, paramVec3d.field_72448_b * Math.cos(paramFloat1 * 0.017453292519943295D) - paramVec3d.field_72449_c * Math.sin(paramFloat1 * 0.017453292519943295D), paramVec3d.field_72448_b * Math.sin(paramFloat1 * 0.017453292519943295D) + paramVec3d.field_72449_c * Math.cos(paramFloat1 * 0.017453292519943295D));
    return new Vec3d(-Math.sin((paramFloat2 + 90.0F) * 0.017453292519943295D) * vec3d.field_72450_a - Math.sin(paramFloat2 * 0.017453292519943295D) * vec3d.field_72449_c, vec3d.field_72448_b, Math.cos((paramFloat2 + 90.0F) * 0.017453292519943295D) * vec3d.field_72450_a + Math.cos(paramFloat2 * 0.017453292519943295D) * vec3d.field_72449_c);
  }
  
  void q() {
    Vec3d vec3d1;
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() != b1.JUMP_0)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.aS < 26)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.aS = 0;
    switch ((int)this.Y) {
      case 90:
        vec3d1 = this.aM.func_178787_e(T);
        break;
      case 180:
        vec3d1 = this.aM.func_178787_e(M);
        break;
      case -90:
        vec3d1 = this.aM.func_178787_e(aa);
        break;
      default:
        vec3d1 = this.aM.func_178787_e(ai);
        break;
    } 
    UUID uUID = B();
    try {
      if (uUID == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    a(vec3d1);
    a(this.Y);
    b(b1.BREEDING_INTRO_0);
    this.field_70145_X = true;
    func_189654_d(true);
    Vec3d vec3d2 = a(new Vec3d(0.0D, 0.44375D - entityPlayer.eyeHeight, -0.7875D), this.Y - 180.0F);
    entityPlayer.field_70145_X = true;
    entityPlayer.func_189654_d(true);
    entityPlayer.func_70634_a(vec3d2.field_72450_a + vec3d1.field_72450_a, vec3d2.field_72448_b + vec3d1.field_72448_b, vec3d2.field_72449_c + vec3d1.field_72449_c);
    List<ag> list = P();
    if (list.size() >= 1) {
      ag ag1 = list.get(0);
      ag1.a(vec3d1);
      ag1.a(this.Y);
      ag1.b(b1.BREEDING_INTRO_1);
      ag1.field_70145_X = true;
      ag1.func_189654_d(true);
    } 
    if (list.size() >= 2) {
      ag ag1 = list.get(1);
      ag1.a(vec3d1);
      ag1.a(this.Y);
      ag1.b(b1.BREEDING_INTRO_2);
      ag1.field_70145_X = true;
      ag1.func_189654_d(true);
    } 
    this.aE = 0;
  }
  
  AxisAlignedBB a(Vec3d paramVec3d1, Vec3d paramVec3d2) {
    return new AxisAlignedBB(paramVec3d1.field_72450_a, paramVec3d1.field_72448_b, paramVec3d1.field_72449_c, paramVec3d2.field_72450_a, paramVec3d2.field_72448_b, paramVec3d2.field_72449_c);
  }
  
  void O() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (B() != null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Vec3d vec3d1 = null;
    switch ((int)this.Y) {
      case 0:
        vec3d1 = O;
        break;
      case 90:
        vec3d1 = aU;
        break;
      case 180:
        vec3d1 = V;
        break;
      case -90:
        vec3d1 = aP;
        break;
    } 
    try {
      if (vec3d1 == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Vec3d vec3d2 = this.aM.func_178786_a(0.5D, 0.0D, 0.5D).func_178788_d(vec3d1);
    AxisAlignedBB axisAlignedBB = a(vec3d2, vec3d2.func_72441_c(K.func_177958_n(), K.func_177956_o(), K.func_177952_p()));
    List<EntityPlayer> list = this.field_70170_p.func_72872_a(EntityPlayer.class, axisAlignedBB);
    try {
      if (list.isEmpty())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = list.get(0);
    try {
      if (!entityPlayer.field_70122_E)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (((Boolean)this.m.func_187225_a(aA)).booleanValue()) {
        try {
          if (this.ax + 1200L < this.field_70170_p.func_82737_E()) {
            entityPlayer.func_146105_b((ITextComponent)new TextComponentString("The Queen is still pregnant - so no breeding for you uwu"), true);
            this.ax = this.field_70170_p.func_82737_E();
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID = entityPlayer.getPersistentID();
    Vec3d vec3d3 = entityPlayer.func_174791_d();
    float f = entityPlayer.field_70177_z + 180.0F;
    bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer);
    d(uUID);
    b(b1.JUMP_0);
    a(vec3d3);
    a(f);
    a(true);
    List<ag> list1 = P();
    if (list1.size() > 0) {
      ag ag1 = list1.get(0);
      ag1.d(uUID);
      ag1.b(b1.JUMP_1);
      ag1.a(vec3d3);
      ag1.a(f);
      ag1.a(true);
      if (list1.size() > 1) {
        ag ag2 = list1.get(1);
        ag2.d(uUID);
        ag2.b(b1.JUMP_2);
        ag2.a(vec3d3);
        ag2.a(f);
        ag2.a(true);
      } 
    } 
  }
  
  List<ag> P() {
    try {
      if (this.R.size() > 1)
        return this.R; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    for (ag ag3 : this.R)
      this.field_70170_p.func_72900_e((Entity)ag3); 
    this.R.clear();
    ag ag1 = new ag(this.field_70170_p, p().toString(), b());
    ag1.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70170_p.func_72838_d((Entity)ag1);
    this.R.add(ag1);
    ag ag2 = new ag(this.field_70170_p, p().toString(), b());
    ag2.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70170_p.func_72838_d((Entity)ag2);
    this.R.add(ag2);
    return this.R;
  }
  
  void y() {
    try {
      if (this.au)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      this.field_70145_X = false;
      func_189654_d(false);
      if (!this.aG)
        try {
          if (!((Boolean)this.m.func_187225_a(ae)).booleanValue())
            try {
              if (!((String)this.m.func_187225_a(X)).equals(""))
                try {
                  if (h() == b1.NULL)
                    this.field_70170_p.func_72900_e((Entity)this); 
                } catch (ConcurrentModificationException concurrentModificationException) {
                  throw a(null);
                }  
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            }  
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.au = true;
  }
  
  void M() {
    try {
      if (this.ab == -1)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.ab++;
    if (this.ab == 15) {
      Vec3d vec3d1 = a();
      float f1 = c();
      float f2 = w();
      func_70634_a(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c);
      Vec3d vec3d2 = a(new Vec3d(0.0D, 0.0D, 1.5D), f1, f2);
      try {
        this.field_70159_w = vec3d2.field_72450_a;
        this.field_70181_x = vec3d2.field_72448_b;
        this.field_70179_y = vec3d2.field_72449_c;
        if (!this.field_70170_p.field_72995_K) {
          a(f2);
          this.field_70145_X = false;
          func_189654_d(false);
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
    } 
    try {
      if (this.ab == 39) {
        this.ab = -1;
        b(b1.THROWN);
        d((UUID)null);
        a((UUID)null);
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  Vec3d a() {
    UUID uUID = r();
    try {
      if (uUID == null)
        return func_174791_d(); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return func_174791_d(); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return entityPlayer.func_174791_d().func_72441_c(0.0D, entityPlayer.func_70047_e(), 0.0D).func_178787_e(a(new Vec3d(0.4000000059604645D, 0.0D, 0.0D), c(), w()));
  }
  
  float w() {
    UUID uUID = r();
    try {
      if (uUID == null)
        return 0.0F; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return 0.0F; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return entityPlayer.field_70759_as;
  }
  
  float c() {
    UUID uUID = r();
    try {
      if (uUID == null)
        return 0.0F; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return 0.0F; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return entityPlayer.field_70125_A;
  }
  
  void f() {
    boolean bool;
    try {
      if (!this.field_70122_E)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() != b1.RUN)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, 100.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    double d = 20.0D;
    do {
      if (d <= 0.0D)
        return; 
      Vec3d vec3d1 = func_174791_d().func_178788_d(entityPlayer.func_174791_d());
      Vec3d vec3d2 = new Vec3d(Math.abs(vec3d1.field_72450_a), Math.abs(vec3d1.field_72448_b), Math.abs(vec3d1.field_72449_c));
      double d1 = vec3d2.field_72450_a / (vec3d2.field_72450_a + vec3d2.field_72449_c);
      double d2 = vec3d2.field_72449_c / (vec3d2.field_72450_a + vec3d2.field_72449_c);
      try {
      
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      Vec3d vec3d3 = func_174791_d().func_178787_e(new Vec3d(((vec3d1.field_72450_a > 0.0D) ? true : -1) * d1 * d, 0.0D, ((vec3d1.field_72449_c > 0.0D) ? true : -1) * d2 * d));
      PathNavigate pathNavigate = func_70661_as();
      pathNavigate.func_75499_g();
      bool = pathNavigate.func_75492_a(vec3d3.field_72450_a, vec3d3.field_72448_b, vec3d3.field_72449_c, 0.824999988079071D);
      d--;
    } while (!bool);
  }
  
  protected void func_70664_aZ() {
    try {
      if (h() == b1.RUN)
        try {
          if (!t())
            return; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    super.func_70664_aZ();
  }
  
  boolean t() {
    PathNavigate pathNavigate = func_70661_as();
    Path path = pathNavigate.func_75505_d();
    try {
      if (path == null)
        return true; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    int i = path.func_75873_e();
    int j = path.func_75874_d();
    try {
      if (j != i)
        try {
          if (j - 1 != i) {
            PathPoint pathPoint1 = path.func_75877_a(i);
            PathPoint pathPoint2 = path.func_75877_a(i + 1);
            try {
            
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
            return (pathPoint2.field_75837_b - pathPoint1.field_75837_b == 1);
          } 
          return true;
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return true;
  }
  
  void T() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (((Boolean)this.m.func_187225_a(ae)).booleanValue())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (((Boolean)this.m.func_187225_a(aA)).booleanValue())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() != b1.SIT)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.W < 32000)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, 3000.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.field_70122_E)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (entityPlayer.field_70160_al)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Integer integer = a(entityPlayer);
    try {
      if (integer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Vec3d vec3d1 = entityPlayer.func_174791_d();
    Vec3d vec3d2 = func_174791_d();
    Vec3d vec3d3 = vec3d1.func_178788_d(vec3d2);
    double d = Math.sqrt(vec3d3.field_72450_a * vec3d3.field_72450_a + vec3d3.field_72449_c * vec3d3.field_72449_c);
    try {
      if (d > 100.0D)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    ItemStack itemStack = entityPlayer.field_71071_by.func_70301_a(integer.intValue()).func_77946_l();
    ag ag1 = new ag(this.field_70170_p, p().toString(), b());
    Vec3d vec3d4 = a(new Vec3d(0.0D, 0.0D, -0.20000000298023224D), entityPlayer.field_70759_as);
    ag1.func_70107_b(entityPlayer.field_70165_t + vec3d4.field_72450_a, entityPlayer.field_70163_u, entityPlayer.field_70161_v + vec3d4.field_72449_c);
    ag1.b(b1.RUN);
    this.field_70170_p.func_72838_d((Entity)ag1);
    ag1.m.func_187227_b(aj, itemStack);
    entityPlayer.func_145747_a((ITextComponent)new TextComponentString(String.format("<%s> I got your %s hehe~", new Object[] { ag1.F(), itemStack.func_82833_r() })));
    entityPlayer.field_71071_by.func_70304_b(integer.intValue());
    this.W = 0;
  }
  
  int b() {
    return Integer.parseInt(a(this)[7]);
  }
  
  @Nullable
  Integer a(EntityPlayer paramEntityPlayer) {
    NonNullList nonNullList = paramEntityPlayer.field_71071_by.field_70462_a;
    ArrayList<Integer> arrayList = new ArrayList();
    for (byte b = 0; b < nonNullList.size(); b++) {
      ItemStack itemStack = (ItemStack)nonNullList.get(b);
      try {
        if (itemStack != ItemStack.field_190927_a)
          try {
            if (aD.contains(itemStack.func_77973_b()))
              arrayList.add(Integer.valueOf(b)); 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
    } 
    try {
      if (arrayList.isEmpty())
        return null; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return arrayList.get(func_70681_au().nextInt(arrayList.size()));
  }
  
  void m() {
    try {
      if (!this.aG)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (B() != null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    a(this.aM);
    a(this.Y);
    a(true);
    func_189654_d(true);
    b(b1.SIT);
  }
  
  public void func_70071_h_() {
    try {
      G();
      M();
      super.func_70071_h_();
      Q();
      E();
      h();
      if (this.field_70170_p.field_72995_K)
        try {
          L();
          S();
          if (r() != null)
            this.field_70145_X = true; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  void G() {
    b1 b11 = h();
    try {
      if (this.ap != b1.START_THROWING)
        try {
          if (b11 == b1.START_THROWING)
            this.ab = 0; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.ap = b11;
  }
  
  public void func_70015_d(int paramInt) {
    try {
      if (r() == null)
        super.func_70015_d(paramInt); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  void h() {
    try {
      if (h() != b1.VANISH)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      this.as -= 0.05F;
      if (this.as > 0.0F)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.field_70170_p.func_72900_e((Entity)this);
  }
  
  void E() {
    try {
      if (((Boolean)this.m.func_187225_a(ae)).booleanValue())
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() != b1.THROWN)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!this.field_70122_E)
        try {
          if (!func_70090_H())
            return; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      this.as = (float)(this.as - 0.05D);
      if (this.as > 0.0F)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.field_70170_p.field_72995_K)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    b(b1.NULL);
    d((UUID)null);
    a((UUID)null);
    this.field_70170_p.func_72900_e((Entity)this);
  }
  
  @SideOnly(Side.CLIENT)
  void L() {
    try {
      if (this.at == -1)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.at != 15)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.at = -1;
    b(b1.PAIZURI_START);
    (Minecraft.func_71410_x()).field_71439_g.func_71053_j();
  }
  
  @SideOnly(Side.CLIENT)
  void S() {
    try {
      if (this.ac == -1)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (++this.ac != 15)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    this.ac = -1;
    b(b1.NELSON_INTRO);
    Minecraft minecraft = Minecraft.func_71410_x();
    minecraft.field_71439_g.func_71053_j();
    minecraft.field_71474_y.field_74320_O = 2;
  }
  
  public void b(b1 paramb1) {
    b1 b11 = h();
    try {
      if (b11 == b1.PAIZURI_CUM)
        try {
          if (paramb1 != b1.PAIZURI_SLOW) {
            try {
              if (paramb1 == b1.PAIZURI_FAST)
                return; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.NELSON_CUM)
        try {
          if (paramb1 != b1.NELSON_SLOW) {
            try {
              if (paramb1 == b1.NELSON_FAST)
                return; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.BREEDING_CUM_0)
        try {
          if (paramb1 != b1.BREEDING_SLOW_0) {
            try {
              if (paramb1 == b1.BREEDING_FAST_0)
                return; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.START_THROWING)
        try {
          if (!this.field_70170_p.field_72995_K) {
            d(r());
            s();
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.PAIZURI_START)
        try {
          if (!this.field_70170_p.field_72995_K)
            I(); 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.NELSON_INTRO)
        try {
          if (!this.field_70170_p.field_72995_K)
            B(); 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (h() == b1.PAIZURI_CUM)
        try {
          if (paramb1 == b1.NULL)
            try {
              if (!this.field_70170_p.field_72995_K)
                R(); 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            }  
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.BREEDING_CUM_0) {
        this.m.func_187227_b(aA, Boolean.valueOf(true));
        this.ah = this.field_70170_p.func_82737_E();
        this.ax = this.field_70170_p.func_82737_E();
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.BREEDING_CUM_0)
        this.aR = 0; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.NELSON_CUM)
        this.m.func_187227_b(aA, Boolean.valueOf(true)); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.NELSON_CUM)
        try {
          if (paramb1 != b1.NELSON_CUM)
            this.m.func_187227_b(aA, Boolean.valueOf(false)); 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    super.b(paramb1);
  }
  
  void R() {
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(B());
    try {
      if (entityPlayer != null)
        aZ.a.a((EntityPlayerMP)entityPlayer); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      d((UUID)null);
      a(false);
      this.field_70145_X = false;
      func_189654_d(false);
      this.m.func_187227_b(aj, ItemStack.field_190927_a);
      if (!((Boolean)this.m.func_187225_a(ae)).booleanValue()) {
        func_70634_a(this.a.field_72450_a, this.a.field_72448_b, this.a.field_72449_c);
        this.field_70170_p.func_72900_e((Entity)this);
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  void B() {
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(B());
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    a((UUID)null);
    a(entityPlayer.func_174791_d());
    a(entityPlayer.field_70177_z);
    a(true);
    this.field_70145_X = true;
    func_189654_d(true);
    entityPlayer.func_189654_d(true);
    entityPlayer.field_70145_X = true;
    d(entityPlayer.getPersistentID());
  }
  
  void I() {
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(B());
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    a((UUID)null);
    a(entityPlayer.func_174791_d());
    a(entityPlayer.field_70177_z + 180.0F);
    a(true);
    this.field_70145_X = true;
    func_189654_d(true);
    entityPlayer.func_189654_d(true);
    entityPlayer.field_70145_X = true;
    d(entityPlayer.getPersistentID());
    entityPlayer.func_70634_a(entityPlayer.field_70165_t, entityPlayer.field_70163_u - 0.5D, entityPlayer.field_70161_v);
    entityPlayer.field_70125_A = 70.0F;
    entityPlayer.field_70127_C = 70.0F;
  }
  
  void s() {
    ItemStack itemStack = (ItemStack)this.m.func_187225_a(aj);
    try {
      if (itemStack == ItemStack.field_190927_a)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(B());
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    entityPlayer.field_71071_by.func_70441_a(itemStack.func_77946_l());
    this.m.func_187227_b(aj, ItemStack.field_190927_a);
  }
  
  void C() {
    try {
      if (h() != b1.PICK_UP)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID = r();
    try {
      if (uUID == null) {
        this.az = -1;
        b(b1.NULL);
        a((UUID)null);
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null) {
        this.az = -1;
        b(b1.NULL);
        a((UUID)null);
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      func_70107_b(entityPlayer.field_70165_t, entityPlayer.field_70163_u, entityPlayer.field_70161_v);
      if (func_174791_d().func_72438_d(entityPlayer.func_174791_d()) > 10.0D) {
        this.az = -1;
        b(b1.NULL);
        a((UUID)null);
        return;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (this.az-- != 0)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    b(b1.SHOULDER_IDLE);
    this.field_70145_X = true;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean y() {
    try {
      if (h() != b1.NULL)
        return false; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (r() != null)
        return false; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!((Boolean)this.m.func_187225_a(ae)).booleanValue())
        try {
          if (!(Minecraft.func_71410_x()).field_71439_g.func_70685_l((Entity)this))
            return false; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
    
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return (r() == null);
  }
  
  void Q() {
    try {
      if (h() != b1.SHOULDER_IDLE)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID = r();
    try {
      if (uUID == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    func_70107_b(entityPlayer.field_70165_t, entityPlayer.field_70163_u, entityPlayer.field_70161_v);
    this.field_70145_X = true;
    func_189654_d(true);
  }
  
  protected b1 a(b1 paramb1) {
    try {
      switch (b.a[paramb1.ordinal()]) {
        case 1:
        case 2:
          return b1.PAIZURI_FAST;
        case 3:
          return b1.BREEDING_FAST_0;
        case 4:
          return b1.BREEDING_FAST_2;
        case 5:
          return b1.NELSON_FAST;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return null;
  }
  
  protected b1 c(b1 paramb1) {
    try {
      switch (b.a[paramb1.ordinal()]) {
        case 2:
        case 6:
        case 7:
          return b1.PAIZURI_CUM;
        case 8:
          return b1.BREEDING_CUM_1;
        case 4:
        case 9:
          return b1.BREEDING_CUM_2;
        case 5:
        case 10:
          return b1.NELSON_CUM;
        case 3:
        case 11:
          for (ag ag1 : this.R)
            ag1.c(paramb1); 
          return b1.BREEDING_CUM_0;
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return null;
  }
  
  public boolean v() {
    Block block = this.field_70170_p.func_180495_p(func_180425_c().func_177982_a(0, 1, 0)).func_177230_c();
    try {
    
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    return !block.func_176205_b((IBlockAccess)this.field_70170_p, func_180425_c().func_177982_a(0, 1, 0));
  }
  
  public void func_180430_e(float paramFloat1, float paramFloat2) {
    b1 b11 = h();
    try {
      if (b11 != b1.THROWN)
        try {
          if (b11 != b1.START_THROWING) {
            super.func_180430_e(paramFloat1, paramFloat2);
            return;
          } 
          return;
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        }  
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
  }
  
  protected <E extends software.bernie.geckolib3.core.IAnimatable> PlayState a(AnimationEvent<E> paramAnimationEvent) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual toString : ()Ljava/lang/String;
    //   4: astore_3
    //   5: iconst_m1
    //   6: istore #4
    //   8: aload_3
    //   9: invokevirtual hashCode : ()I
    //   12: lookupswitch default -> 51, 96882 -> 32
    //   32: aload_3
    //   33: ldc 'asd'
    //   35: invokevirtual equals : (Ljava/lang/Object;)Z
    //   38: ifeq -> 51
    //   41: goto -> 48
    //   44: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   47: athrow
    //   48: iconst_0
    //   49: istore #4
    //   51: iload #4
    //   53: lookupswitch default -> 78, 0 -> 72
    //   72: ldc 'sdsasdas'
    //   74: astore_2
    //   75: goto -> 81
    //   78: ldc 'gdssfsdf'
    //   80: astore_2
    //   81: aload_2
    //   82: ldc 'aasd'
    //   84: invokevirtual equals : (Ljava/lang/Object;)Z
    //   87: ifeq -> 401
    //   90: new java/util/ArrayList
    //   93: dup
    //   94: invokespecial <init> : ()V
    //   97: astore_3
    //   98: aconst_null
    //   99: astore #4
    //   101: aload_3
    //   102: invokeinterface iterator : ()Ljava/util/Iterator;
    //   107: astore #5
    //   109: aload #5
    //   111: invokeinterface hasNext : ()Z
    //   116: ifeq -> 401
    //   119: aload #5
    //   121: invokeinterface next : ()Ljava/lang/Object;
    //   126: checkcast software/bernie/geckolib3/geo/render/built/GeoQuad
    //   129: astore #6
    //   131: aload #6
    //   133: ifnonnull -> 143
    //   136: goto -> 109
    //   139: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   142: athrow
    //   143: new javax/vecmath/Vector3f
    //   146: dup
    //   147: aload #6
    //   149: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   152: invokevirtual func_177958_n : ()I
    //   155: i2f
    //   156: aload #6
    //   158: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   161: invokevirtual func_177956_o : ()I
    //   164: i2f
    //   165: aload #6
    //   167: getfield normal : Lnet/minecraft/util/math/Vec3i;
    //   170: invokevirtual func_177952_p : ()I
    //   173: i2f
    //   174: invokespecial <init> : (FFF)V
    //   177: astore #7
    //   179: aload_0
    //   180: getfield aO : Lsoftware/bernie/geckolib3/util/MatrixStack;
    //   183: invokevirtual getNormalMatrix : ()Ljavax/vecmath/Matrix3f;
    //   186: aload #7
    //   188: invokevirtual transform : (Ljavax/vecmath/Tuple3f;)V
    //   191: aload #4
    //   193: getfield size : Ljavax/vecmath/Vector3f;
    //   196: getfield y : F
    //   199: fconst_0
    //   200: fcmpl
    //   201: ifeq -> 224
    //   204: aload #4
    //   206: getfield size : Ljavax/vecmath/Vector3f;
    //   209: getfield z : F
    //   212: fconst_0
    //   213: fcmpl
    //   214: ifne -> 260
    //   217: goto -> 224
    //   220: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   223: athrow
    //   224: aload #7
    //   226: invokevirtual getX : ()F
    //   229: fconst_0
    //   230: fcmpg
    //   231: ifge -> 260
    //   234: goto -> 241
    //   237: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   240: athrow
    //   241: aload #7
    //   243: dup
    //   244: getfield x : F
    //   247: ldc -1.0
    //   249: fmul
    //   250: putfield x : F
    //   253: goto -> 260
    //   256: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   259: athrow
    //   260: aload #4
    //   262: getfield size : Ljavax/vecmath/Vector3f;
    //   265: getfield x : F
    //   268: fconst_0
    //   269: fcmpl
    //   270: ifeq -> 293
    //   273: aload #4
    //   275: getfield size : Ljavax/vecmath/Vector3f;
    //   278: getfield z : F
    //   281: fconst_0
    //   282: fcmpl
    //   283: ifne -> 329
    //   286: goto -> 293
    //   289: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   292: athrow
    //   293: aload #7
    //   295: invokevirtual getY : ()F
    //   298: fconst_0
    //   299: fcmpg
    //   300: ifge -> 329
    //   303: goto -> 310
    //   306: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   309: athrow
    //   310: aload #7
    //   312: dup
    //   313: getfield y : F
    //   316: ldc -1.0
    //   318: fmul
    //   319: putfield y : F
    //   322: goto -> 329
    //   325: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   328: athrow
    //   329: aload #4
    //   331: getfield size : Ljavax/vecmath/Vector3f;
    //   334: getfield x : F
    //   337: fconst_0
    //   338: fcmpl
    //   339: ifeq -> 362
    //   342: aload #4
    //   344: getfield size : Ljavax/vecmath/Vector3f;
    //   347: getfield y : F
    //   350: fconst_0
    //   351: fcmpl
    //   352: ifne -> 398
    //   355: goto -> 362
    //   358: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   361: athrow
    //   362: aload #7
    //   364: invokevirtual getZ : ()F
    //   367: fconst_0
    //   368: fcmpg
    //   369: ifge -> 398
    //   372: goto -> 379
    //   375: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   378: athrow
    //   379: aload #7
    //   381: dup
    //   382: getfield z : F
    //   385: ldc -1.0
    //   387: fmul
    //   388: putfield z : F
    //   391: goto -> 398
    //   394: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   397: athrow
    //   398: goto -> 109
    //   401: aload_0
    //   402: getfield field_70170_p : Lnet/minecraft/world/World;
    //   405: instanceof com/c
    //   408: ifeq -> 419
    //   411: getstatic software/bernie/geckolib3/core/PlayState.STOP : Lsoftware/bernie/geckolib3/core/PlayState;
    //   414: areturn
    //   415: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   418: athrow
    //   419: aload_0
    //   420: getfield d : Lsoftware/bernie/geckolib3/core/controller/AnimationController;
    //   423: ifnonnull -> 437
    //   426: aload_0
    //   427: invokevirtual j : ()V
    //   430: goto -> 437
    //   433: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   436: athrow
    //   437: aload_1
    //   438: invokevirtual getController : ()Lsoftware/bernie/geckolib3/core/controller/AnimationController;
    //   441: invokevirtual getName : ()Ljava/lang/String;
    //   444: astore_3
    //   445: iconst_m1
    //   446: istore #4
    //   448: aload_3
    //   449: invokevirtual hashCode : ()I
    //   452: lookupswitch default -> 537, -1422950858 -> 525, -103677777 -> 510, 3128418 -> 488
    //   488: aload_3
    //   489: ldc 'eyes'
    //   491: invokevirtual equals : (Ljava/lang/Object;)Z
    //   494: ifeq -> 537
    //   497: goto -> 504
    //   500: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   503: athrow
    //   504: iconst_0
    //   505: istore #4
    //   507: goto -> 537
    //   510: aload_3
    //   511: ldc 'movement'
    //   513: invokevirtual equals : (Ljava/lang/Object;)Z
    //   516: ifeq -> 537
    //   519: iconst_1
    //   520: istore #4
    //   522: goto -> 537
    //   525: aload_3
    //   526: ldc 'action'
    //   528: invokevirtual equals : (Ljava/lang/Object;)Z
    //   531: ifeq -> 537
    //   534: iconst_2
    //   535: istore #4
    //   537: iload #4
    //   539: tableswitch default -> 1671, 0 -> 564, 1 -> 607, 2 -> 810
    //   564: aload_0
    //   565: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   568: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   571: if_acmpeq -> 596
    //   574: goto -> 581
    //   577: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   580: athrow
    //   581: aload_0
    //   582: ldc 'animation.goblin.null'
    //   584: iconst_1
    //   585: aload_1
    //   586: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   589: goto -> 1671
    //   592: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   595: athrow
    //   596: aload_0
    //   597: ldc 'animation.goblin.blink'
    //   599: iconst_1
    //   600: aload_1
    //   601: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   604: goto -> 1671
    //   607: aload_0
    //   608: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   611: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   614: if_acmpeq -> 632
    //   617: aload_0
    //   618: ldc 'animation.goblin.null'
    //   620: iconst_1
    //   621: aload_1
    //   622: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   625: goto -> 1671
    //   628: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   631: athrow
    //   632: aload_0
    //   633: getfield field_70169_q : D
    //   636: aload_0
    //   637: getfield field_70165_t : D
    //   640: dsub
    //   641: invokestatic abs : (D)D
    //   644: aload_0
    //   645: getfield field_70166_s : D
    //   648: aload_0
    //   649: getfield field_70161_v : D
    //   652: dsub
    //   653: invokestatic abs : (D)D
    //   656: dadd
    //   657: dstore #5
    //   659: aload_0
    //   660: getfield m : Lnet/minecraft/network/datasync/EntityDataManager;
    //   663: getstatic com/schnurritv/sexmod/ag.c : Lnet/minecraft/network/datasync/DataParameter;
    //   666: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
    //   669: checkcast java/lang/Boolean
    //   672: invokevirtual booleanValue : ()Z
    //   675: ifne -> 799
    //   678: dload #5
    //   680: dconst_0
    //   681: dcmpl
    //   682: ifle -> 799
    //   685: goto -> 692
    //   688: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   691: athrow
    //   692: aload_0
    //   693: getfield field_70122_E : Z
    //   696: ifeq -> 788
    //   699: goto -> 706
    //   702: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   705: athrow
    //   706: aload_0
    //   707: getfield field_70167_r : D
    //   710: invokestatic abs : (D)D
    //   713: aload_0
    //   714: getfield field_70163_u : D
    //   717: invokestatic abs : (D)D
    //   720: dsub
    //   721: invokestatic abs : (D)D
    //   724: ldc2_w 0.10000000149011612
    //   727: dcmpg
    //   728: ifge -> 788
    //   731: goto -> 738
    //   734: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   737: athrow
    //   738: dload #5
    //   740: ldc2_w 0.20000000298023224
    //   743: dcmpl
    //   744: ifle -> 769
    //   747: goto -> 754
    //   750: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   753: athrow
    //   754: aload_0
    //   755: ldc 'animation.goblin.walk'
    //   757: iconst_1
    //   758: aload_1
    //   759: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   762: goto -> 777
    //   765: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   768: athrow
    //   769: aload_0
    //   770: ldc 'animation.goblin.walk'
    //   772: iconst_1
    //   773: aload_1
    //   774: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   777: aload_0
    //   778: aload_0
    //   779: getfield field_70759_as : F
    //   782: putfield field_70177_z : F
    //   785: goto -> 1671
    //   788: aload_0
    //   789: ldc 'animation.goblin.fly'
    //   791: iconst_1
    //   792: aload_1
    //   793: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   796: goto -> 1671
    //   799: aload_0
    //   800: ldc 'animation.goblin.idle'
    //   802: iconst_1
    //   803: aload_1
    //   804: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   807: goto -> 1671
    //   810: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   813: astore #7
    //   815: aload #7
    //   817: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
    //   820: invokevirtual getPersistentID : ()Ljava/util/UUID;
    //   823: aload_0
    //   824: invokevirtual r : ()Ljava/util/UUID;
    //   827: invokevirtual equals : (Ljava/lang/Object;)Z
    //   830: ifeq -> 860
    //   833: aload #7
    //   835: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
    //   838: getfield field_74320_O : I
    //   841: ifne -> 860
    //   844: goto -> 851
    //   847: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   850: athrow
    //   851: ldc '1'
    //   853: goto -> 862
    //   856: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   859: athrow
    //   860: ldc '3'
    //   862: astore #8
    //   864: getstatic com/schnurritv/sexmod/ag$b.a : [I
    //   867: aload_0
    //   868: invokevirtual h : ()Lcom/schnurritv/sexmod/b1;
    //   871: invokevirtual ordinal : ()I
    //   874: iaload
    //   875: tableswitch default -> 1671, 1 -> 1296, 2 -> 1243, 3 -> 1384, 4 -> 1429, 5 -> 1573, 6 -> 1274, 7 -> 1285, 8 -> 1529, 9 -> 1485, 10 -> 1618, 11 -> 1440, 12 -> 1036, 13 -> 1051, 14 -> 1062, 15 -> 1085, 16 -> 1096, 17 -> 1129, 18 -> 1152, 19 -> 1175, 20 -> 1198, 21 -> 1221, 22 -> 1232, 23 -> 1307, 24 -> 1318, 25 -> 1329, 26 -> 1340, 27 -> 1351, 28 -> 1362, 29 -> 1373, 30 -> 1496, 31 -> 1507, 32 -> 1518, 33 -> 1540, 34 -> 1540, 35 -> 1551, 36 -> 1562, 37 -> 1663
    //   1036: aload_0
    //   1037: ldc 'animation.goblin.null'
    //   1039: iconst_1
    //   1040: aload_1
    //   1041: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1044: goto -> 1671
    //   1047: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1050: athrow
    //   1051: aload_0
    //   1052: ldc 'animation.goblin.shoulder_idle'
    //   1054: iconst_1
    //   1055: aload_1
    //   1056: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1059: goto -> 1671
    //   1062: aload_0
    //   1063: ldc 'animation.goblin.pick_up_%sperson'
    //   1065: iconst_1
    //   1066: anewarray java/lang/Object
    //   1069: dup
    //   1070: iconst_0
    //   1071: aload #8
    //   1073: aastore
    //   1074: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1077: iconst_1
    //   1078: aload_1
    //   1079: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1082: goto -> 1671
    //   1085: aload_0
    //   1086: ldc 'animation.goblin.sit'
    //   1088: iconst_1
    //   1089: aload_1
    //   1090: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1093: goto -> 1671
    //   1096: aload_0
    //   1097: getfield field_70122_E : Z
    //   1100: ifeq -> 1118
    //   1103: aload_0
    //   1104: ldc 'animation.goblin.running'
    //   1106: iconst_1
    //   1107: aload_1
    //   1108: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1111: goto -> 1671
    //   1114: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1117: athrow
    //   1118: aload_0
    //   1119: ldc 'animation.goblin.fly'
    //   1121: iconst_1
    //   1122: aload_1
    //   1123: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1126: goto -> 1671
    //   1129: aload_0
    //   1130: ldc 'animation.goblin.catch_%sperson'
    //   1132: iconst_1
    //   1133: anewarray java/lang/Object
    //   1136: dup
    //   1137: iconst_0
    //   1138: aload #8
    //   1140: aastore
    //   1141: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1144: iconst_1
    //   1145: aload_1
    //   1146: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1149: goto -> 1671
    //   1152: aload_0
    //   1153: ldc 'animation.goblin.catch_%spersonBj'
    //   1155: iconst_1
    //   1156: anewarray java/lang/Object
    //   1159: dup
    //   1160: iconst_0
    //   1161: aload #8
    //   1163: aastore
    //   1164: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1167: iconst_1
    //   1168: aload_1
    //   1169: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1172: goto -> 1671
    //   1175: aload_0
    //   1176: ldc 'animation.goblin.catch_%spersonBj_idle'
    //   1178: iconst_1
    //   1179: anewarray java/lang/Object
    //   1182: dup
    //   1183: iconst_0
    //   1184: aload #8
    //   1186: aastore
    //   1187: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1190: iconst_1
    //   1191: aload_1
    //   1192: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1195: goto -> 1671
    //   1198: aload_0
    //   1199: ldc 'animation.goblin.throw_%sperson'
    //   1201: iconst_1
    //   1202: anewarray java/lang/Object
    //   1205: dup
    //   1206: iconst_0
    //   1207: aload #8
    //   1209: aastore
    //   1210: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1213: iconst_1
    //   1214: aload_1
    //   1215: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1218: goto -> 1671
    //   1221: aload_0
    //   1222: ldc 'animation.goblin.thrown'
    //   1224: iconst_1
    //   1225: aload_1
    //   1226: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1229: goto -> 1671
    //   1232: aload_0
    //   1233: ldc 'animation.goblin.paizuri_start'
    //   1235: iconst_1
    //   1236: aload_1
    //   1237: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1240: goto -> 1671
    //   1243: aload_0
    //   1244: new java/lang/StringBuilder
    //   1247: dup
    //   1248: invokespecial <init> : ()V
    //   1251: ldc 'animation.goblin.paizuri_slow'
    //   1253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1256: aload_0
    //   1257: getfield aT : Ljava/lang/String;
    //   1260: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1263: invokevirtual toString : ()Ljava/lang/String;
    //   1266: iconst_1
    //   1267: aload_1
    //   1268: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1271: goto -> 1671
    //   1274: aload_0
    //   1275: ldc 'animation.goblin.paizuri_fast'
    //   1277: iconst_1
    //   1278: aload_1
    //   1279: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1282: goto -> 1671
    //   1285: aload_0
    //   1286: ldc 'animation.goblin.paizuri_fast_countinues'
    //   1288: iconst_1
    //   1289: aload_1
    //   1290: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1293: goto -> 1671
    //   1296: aload_0
    //   1297: ldc 'animation.goblin.paizuri_idle'
    //   1299: iconst_1
    //   1300: aload_1
    //   1301: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1304: goto -> 1671
    //   1307: aload_0
    //   1308: ldc 'animation.goblin.paizuri_cum'
    //   1310: iconst_1
    //   1311: aload_1
    //   1312: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1315: goto -> 1671
    //   1318: aload_0
    //   1319: ldc 'animation.goblin.jump_1'
    //   1321: iconst_1
    //   1322: aload_1
    //   1323: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1326: goto -> 1671
    //   1329: aload_0
    //   1330: ldc 'animation.goblin.jump_2'
    //   1332: iconst_1
    //   1333: aload_1
    //   1334: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1337: goto -> 1671
    //   1340: aload_0
    //   1341: ldc 'animation.goblin.jump_3'
    //   1343: iconst_1
    //   1344: aload_1
    //   1345: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1348: goto -> 1671
    //   1351: aload_0
    //   1352: ldc 'animation.goblin.breeding_intro_1'
    //   1354: iconst_1
    //   1355: aload_1
    //   1356: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1359: goto -> 1671
    //   1362: aload_0
    //   1363: ldc 'animation.goblin.breeding_intro_2'
    //   1365: iconst_1
    //   1366: aload_1
    //   1367: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1370: goto -> 1671
    //   1373: aload_0
    //   1374: ldc 'animation.goblin.breeding_intro_3'
    //   1376: iconst_1
    //   1377: aload_1
    //   1378: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1381: goto -> 1671
    //   1384: aload_0
    //   1385: new java/lang/StringBuilder
    //   1388: dup
    //   1389: invokespecial <init> : ()V
    //   1392: ldc 'animation.goblin.breeding_slow_1'
    //   1394: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1397: aload_0
    //   1398: getfield aQ : Z
    //   1401: ifeq -> 1413
    //   1404: ldc 'l'
    //   1406: goto -> 1415
    //   1409: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1412: athrow
    //   1413: ldc 'r'
    //   1415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1418: invokevirtual toString : ()Ljava/lang/String;
    //   1421: iconst_1
    //   1422: aload_1
    //   1423: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1426: goto -> 1671
    //   1429: aload_0
    //   1430: ldc 'animation.goblin.breeding_slow_3'
    //   1432: iconst_1
    //   1433: aload_1
    //   1434: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1437: goto -> 1671
    //   1440: aload_0
    //   1441: new java/lang/StringBuilder
    //   1444: dup
    //   1445: invokespecial <init> : ()V
    //   1448: ldc 'animation.goblin.breeding_fast_1'
    //   1450: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1453: aload_0
    //   1454: getfield Q : Z
    //   1457: ifeq -> 1469
    //   1460: ldc 'c'
    //   1462: goto -> 1471
    //   1465: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1468: athrow
    //   1469: ldc 's'
    //   1471: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1474: invokevirtual toString : ()Ljava/lang/String;
    //   1477: iconst_1
    //   1478: aload_1
    //   1479: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1482: goto -> 1671
    //   1485: aload_0
    //   1486: ldc 'animation.goblin.breeding_fast_3'
    //   1488: iconst_1
    //   1489: aload_1
    //   1490: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1493: goto -> 1671
    //   1496: aload_0
    //   1497: ldc 'animation.goblin.breeding_cum_1'
    //   1499: iconst_1
    //   1500: aload_1
    //   1501: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1504: goto -> 1671
    //   1507: aload_0
    //   1508: ldc 'animation.goblin.breeding_cum_2'
    //   1510: iconst_1
    //   1511: aload_1
    //   1512: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1515: goto -> 1671
    //   1518: aload_0
    //   1519: ldc 'animation.goblin.breeding_cum_3'
    //   1521: iconst_1
    //   1522: aload_1
    //   1523: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1526: goto -> 1671
    //   1529: aload_0
    //   1530: ldc 'animation.goblin.breeding_2'
    //   1532: iconst_1
    //   1533: aload_1
    //   1534: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1537: goto -> 1671
    //   1540: aload_0
    //   1541: ldc 'animation.goblin.await_pick_up'
    //   1543: iconst_1
    //   1544: aload_1
    //   1545: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1548: goto -> 1671
    //   1551: aload_0
    //   1552: ldc 'animation.goblin.stand_up'
    //   1554: iconst_0
    //   1555: aload_1
    //   1556: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1559: goto -> 1671
    //   1562: aload_0
    //   1563: ldc 'animation.goblin.nelson_intro'
    //   1565: iconst_1
    //   1566: aload_1
    //   1567: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1570: goto -> 1671
    //   1573: aload_0
    //   1574: new java/lang/StringBuilder
    //   1577: dup
    //   1578: invokespecial <init> : ()V
    //   1581: ldc 'animation.goblin.nelson_slow'
    //   1583: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1586: aload_0
    //   1587: getfield Z : Z
    //   1590: ifeq -> 1602
    //   1593: ldc ''
    //   1595: goto -> 1604
    //   1598: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1601: athrow
    //   1602: ldc '2'
    //   1604: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1607: invokevirtual toString : ()Ljava/lang/String;
    //   1610: iconst_1
    //   1611: aload_1
    //   1612: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1615: goto -> 1671
    //   1618: aload_0
    //   1619: new java/lang/StringBuilder
    //   1622: dup
    //   1623: invokespecial <init> : ()V
    //   1626: ldc 'animation.goblin.nelson_fast'
    //   1628: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1631: aload_0
    //   1632: getfield P : Z
    //   1635: ifeq -> 1647
    //   1638: ldc 'c'
    //   1640: goto -> 1649
    //   1643: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1646: athrow
    //   1647: ldc 's'
    //   1649: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1652: invokevirtual toString : ()Ljava/lang/String;
    //   1655: iconst_1
    //   1656: aload_1
    //   1657: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1660: goto -> 1671
    //   1663: aload_0
    //   1664: ldc 'animation.goblin.nelson_cum'
    //   1666: iconst_1
    //   1667: aload_1
    //   1668: invokevirtual a : (Ljava/lang/String;ZLsoftware/bernie/geckolib3/core/event/predicate/AnimationEvent;)V
    //   1671: getstatic software/bernie/geckolib3/core/PlayState.CONTINUE : Lsoftware/bernie/geckolib3/core/PlayState;
    //   1674: areturn
    // Exception table:
    //   from	to	target	type
    //   8	41	44	java/util/ConcurrentModificationException
    //   131	139	139	java/util/ConcurrentModificationException
    //   179	217	220	java/util/ConcurrentModificationException
    //   204	234	237	java/util/ConcurrentModificationException
    //   224	253	256	java/util/ConcurrentModificationException
    //   260	286	289	java/util/ConcurrentModificationException
    //   273	303	306	java/util/ConcurrentModificationException
    //   293	322	325	java/util/ConcurrentModificationException
    //   329	355	358	java/util/ConcurrentModificationException
    //   342	372	375	java/util/ConcurrentModificationException
    //   362	391	394	java/util/ConcurrentModificationException
    //   401	415	415	java/util/ConcurrentModificationException
    //   419	430	433	java/util/ConcurrentModificationException
    //   448	497	500	java/util/ConcurrentModificationException
    //   537	574	577	java/util/ConcurrentModificationException
    //   564	592	592	java/util/ConcurrentModificationException
    //   607	628	628	java/util/ConcurrentModificationException
    //   659	685	688	java/util/ConcurrentModificationException
    //   678	699	702	java/util/ConcurrentModificationException
    //   692	731	734	java/util/ConcurrentModificationException
    //   706	747	750	java/util/ConcurrentModificationException
    //   738	765	765	java/util/ConcurrentModificationException
    //   815	844	847	java/util/ConcurrentModificationException
    //   833	856	856	java/util/ConcurrentModificationException
    //   864	1047	1047	java/util/ConcurrentModificationException
    //   1096	1114	1114	java/util/ConcurrentModificationException
    //   1384	1409	1409	java/util/ConcurrentModificationException
    //   1440	1465	1465	java/util/ConcurrentModificationException
    //   1573	1598	1598	java/util/ConcurrentModificationException
    //   1618	1643	1643	java/util/ConcurrentModificationException
  }
  
  @SideOnly(Side.CLIENT)
  public void registerControllers(AnimationData paramAnimationData) {
    try {
      super.registerControllers(paramAnimationData);
      if (this.d == null)
        j(); 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    AnimationController.ISoundListener iSoundListener = paramSoundKeyframeEvent -> {
        String str = paramSoundKeyframeEvent.sound;
        byte b = -1;
        try {
          switch (str.hashCode()) {
            case 555758782:
              if (str.equals("catchEh"))
                b = 0; 
              break;
            case 1911168065:
              if (str.equals("catchAkward"))
                b = 1; 
              break;
            case 1508781609:
              if (str.equals("catchWell"))
                b = 2; 
              break;
            case -1906421909:
              if (str.equals("catchRather"))
                b = 3; 
              break;
            case 555759027:
              if (str.equals("catchMe"))
                b = 4; 
              break;
            case 1508225245:
              if (str.equals("catchDone"))
                b = 5; 
              break;
            case 1937367685:
              if (str.equals("catchBjDone"))
                b = 6; 
              break;
            case -257095285:
              if (str.equals("paizuriChoice"))
                b = 7; 
              break;
            case -85206549:
              if (str.equals("paizuriBoth"))
                b = 8; 
              break;
            case -1471320445:
              if (str.equals("paizruiUse"))
                b = 9; 
              break;
            case 214655774:
              if (str.equals("paizuriSwitch"))
                b = 10; 
              break;
            case 110550847:
              if (str.equals("touch"))
                b = 11; 
              break;
            case 106857100:
              if (str.equals("pound"))
                b = 12; 
              break;
            case -362733489:
              if (str.equals("paizuri_startDone"))
                b = 13; 
              break;
            case 1179204456:
              if (str.equals("paizuriFastDone"))
                b = 14; 
              break;
            case -2086748547:
              if (str.equals("paizuriFastReady"))
                b = 15; 
              break;
            case -773653315:
              if (str.equals("paizuriFastContinuesReady"))
                b = 16; 
              break;
            case 1216022981:
              if (str.equals("smallPound"))
                b = 17; 
              break;
            case -1471338293:
              if (str.equals("paizruiCam"))
                b = 18; 
              break;
            case 403702091:
              if (str.equals("blackScreen"))
                b = 19; 
              break;
            case 989222323:
              if (str.equals("paizuriCumDone"))
                b = 20; 
              break;
            case 403932052:
              if (str.equals("cumSound"))
                b = 21; 
              break;
            case -1255179071:
              if (str.equals("jumpCam"))
                b = 22; 
              break;
            case -1502522566:
              if (str.equals("breedingHmm"))
                b = 23; 
              break;
            case -816950732:
              if (str.equals("breedingFound"))
                b = 24; 
              break;
            case 414606590:
              if (str.equals("breedingEnough"))
                b = 25; 
              break;
            case 666280273:
              if (str.equals("breedingCam2"))
                b = 26; 
              break;
            case 642185664:
              if (str.equals("breedingIntroDone"))
                b = 27; 
              break;
            case -1257666335:
              if (str.equals("breeding_slow1Done"))
                b = 28; 
              break;
            case -2057479322:
              if (str.equals("breeding_fast1Done"))
                b = 29; 
              break;
            case 655269439:
              if (str.equals("breeding_fast1Ready"))
                b = 30; 
              break;
            case 98875:
              if (str.equals("cum"))
                b = 31; 
              break;
            case -1999384079:
              if (str.equals("breeding_intro_3Done"))
                b = 32; 
              break;
            case -1532770072:
              if (str.equals("breeding_3_wiggle"))
                b = 33; 
              break;
            case 1942611875:
              if (str.equals("breeding_fast_3Done"))
                b = 34; 
              break;
            case -2000307600:
              if (str.equals("breeding_intro_2Done"))
                b = 35; 
              break;
            case 1258509061:
              if (str.equals("breeding_cumCam"))
                b = 36; 
              break;
            case -1583482882:
              if (str.equals("neslon_introDone"))
                b = 37; 
              break;
            case 408494917:
              if (str.equals("nelson_slowDone"))
                b = 38; 
              break;
            case 45790400:
              if (str.equals("neslon_fastSwitch"))
                b = 39; 
              break;
            case -1402440057:
              if (str.equals("neslon_fastBackSwitch"))
                b = 40; 
              break;
            case 662391035:
              if (str.equals("nelsonFastDone"))
                b = 41; 
              break;
            case -1186189829:
              if (str.equals("nelson_cumDone"))
                b = 42; 
              break;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          EntityPlayerSP entityPlayerSP;
          switch (b) {
            case 0:
              e("ehh..");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 1:
              e("awkward..");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 2:
              e("well...");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 3:
              e("would you rather have this stupid... thing?");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 4:
              e("...or use me?~");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 5:
              try {
                if ("bj".equals(this.m.func_187225_a(f)))
                  b(b1.CATCH_BJ); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 6:
              b(b1.CATCH_BJ_IDLE);
              if (L()) {
                EntityPlayerSP entityPlayerSP1 = (Minecraft.func_71410_x()).field_71439_g;
                a((EntityPlayer)entityPlayerSP1, this, new String[] { "use her", "take ur stuff back" }, (ItemStack[])null, false);
              } 
              break;
            case 7:
              e("good choice!~");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 8:
              e("...for both of us!");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 9:
              e("now use me like a fuck toy!~");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 10:
              try {
                if (func_70681_au().nextBoolean())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
              
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              this.aT = "".equals(this.aT) ? "2" : "";
              break;
            case 11:
              a(c.MISC_TOUCH, 3.0F);
              break;
            case 12:
              try {
                a(c.MISC_POUNDING, new int[0]);
                if (L())
                  aC.a(0.03999999910593033D); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 13:
              try {
                b(b1.PAIZURI_IDLE);
                if (L())
                  aC.a(); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 14:
              b(b1.PAIZURI_SLOW);
              break;
            case 15:
              try {
                if (!L())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (bf.b)
                  b(b1.PAIZURI_FAST_CONTINUES); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 16:
              try {
                if (L())
                  try {
                    if (bf.b)
                      A(); 
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 17:
              try {
                a(c.MISC_POUNDING, 0.25F);
                if (L())
                  aC.a(0.019999999552965164D); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 18:
              try {
                if (!L())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
              ((EntityPlayer)entityPlayerSP).field_70125_A = 70.0F;
              ((EntityPlayer)entityPlayerSP).field_70127_C = 70.0F;
              break;
            case 19:
              try {
                if (L())
                  a5.a(); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 20:
              b(b1.NULL);
              break;
            case 21:
              a(c.MISC_SMALLINSERTS, 3.0F);
              break;
            case 22:
              if (L()) {
                Minecraft minecraft = Minecraft.func_71410_x();
                minecraft.field_71439_g.field_70177_z = m().floatValue() + 170.0F;
                minecraft.field_71439_g.field_70125_A = -20.0F;
                minecraft.field_71439_g.field_70759_as = minecraft.field_71439_g.field_70177_z;
                minecraft.field_71474_y.field_74320_O = 2;
              } 
              break;
            case 23:
              if (L()) {
                Minecraft minecraft = Minecraft.func_71410_x();
                minecraft.field_71439_g.field_70177_z = m().floatValue() + 180.0F;
                minecraft.field_71439_g.field_70125_A = -15.0F;
                minecraft.field_71439_g.field_70759_as = minecraft.field_71439_g.field_70177_z;
                minecraft.field_71474_y.field_74320_O = 0;
              } 
              e("hmm...");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 24:
              e("guess we found a worthy breeding partner!");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 25:
              e("Eh.. go pin him down, before he runs off!");
              a(c.MISC_PLOB, new int[0]);
              break;
            case 26:
              if (L()) {
                Minecraft minecraft = Minecraft.func_71410_x();
                minecraft.field_71474_y.field_74320_O = 2;
                minecraft.field_71439_g.field_70177_z = m().floatValue() - 120.0F;
                minecraft.field_71439_g.field_70125_A = -30.0F;
              } 
            case 27:
              try {
                b(b1.BREEDING_SLOW_0);
                if (L())
                  aC.a(); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 28:
              try {
                if (func_70681_au().nextBoolean()) {
                  try {
                  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  } 
                  this.aQ = !this.aQ;
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (L())
                  try {
                    if (bf.b) {
                      b(b1.BREEDING_FAST_0);
                      this.Q = false;
                    } 
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  }  
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 29:
              try {
                b(b1.BREEDING_SLOW_0);
                if (!L())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              this.Q = false;
              break;
            case 30:
              try {
                if (!L())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (bf.b) {
                  this.Q = true;
                  A();
                  this.d.tickOffset = 0.0D;
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 31:
              a(c.MISC_SMALLINSERTS, 2.0F);
              break;
            case 32:
              b(b1.BREEDING_SLOW_2);
              break;
            case 33:
              try {
                if (func_70681_au().nextBoolean())
                  this.d.tickOffset = 0.0D; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 34:
              try {
                if (!L())
                  break; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (!bf.b)
                  b(b1.BREEDING_SLOW_2); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 35:
              b(b1.BREEDING_1);
              break;
            case 36:
              if (L()) {
                Minecraft minecraft = Minecraft.func_71410_x();
                minecraft.field_71474_y.field_74320_O = 0;
                minecraft.field_71439_g.field_70177_z = m().floatValue() + 180.0F;
                minecraft.field_71439_g.field_70125_A = -15.0F;
                minecraft.field_71439_g.field_70759_as = minecraft.field_71439_g.field_70177_z;
                minecraft.field_71474_y.field_74320_O = 0;
              } 
              break;
            case 37:
              try {
                b(b1.NELSON_SLOW);
                if (L())
                  aC.a(); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 38:
              try {
                if (func_70681_au().nextBoolean()) {
                  try {
                  
                  } catch (ConcurrentModificationException concurrentModificationException) {
                    throw a(null);
                  } 
                  this.Z = !this.Z;
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 39:
              try {
                if (!L()) {
                  this.P = true;
                  return;
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (bf.b)
                  this.P = true; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 40:
              try {
                if (!L()) {
                  this.d.tickOffset = 0.0D;
                  break;
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              try {
                if (bf.b)
                  this.d.tickOffset = 0.0D; 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 41:
              try {
                this.P = false;
                if (L())
                  b(b1.NELSON_SLOW); 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
            case 42:
              try {
                if (L()) {
                  D();
                  b(b1.NULL);
                } 
              } catch (ConcurrentModificationException concurrentModificationException) {
                throw a(null);
              } 
              break;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
      };
    this.d.registerSoundListener(iSoundListener);
    this.e.transitionLengthTicks = 10.0D;
    paramAnimationData.addAnimationController(this.d);
    paramAnimationData.addAnimationController(this.e);
    paramAnimationData.addAnimationController(this.b);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  public static class a {
    static Minecraft a = null;
    
    @SubscribeEvent
    public void a(LivingAttackEvent param1LivingAttackEvent) {
      try {
        if (param1LivingAttackEvent.getSource() == DamageSource.field_76380_i)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      EntityLivingBase entityLivingBase = param1LivingAttackEvent.getEntityLiving();
      try {
        if (!(entityLivingBase instanceof ag))
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      ag ag = (ag)entityLivingBase;
      try {
        if (ag.r() != null)
          param1LivingAttackEvent.setCanceled(true); 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void a(InputEvent.KeyInputEvent param1KeyInputEvent) {
      try {
        if (a == null)
          a = Minecraft.func_71410_x(); 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      try {
        if (a.field_71462_r instanceof aY)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      try {
        if (!ClientProxy.keyBindings[0].func_151468_f())
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      ag ag = null;
      UUID uUID = (Minecraft.func_71410_x()).field_71439_g.getPersistentID();
      try {
        for (Q q : Q.f()) {
          if (q.field_70170_p.field_72995_K) {
            try {
              if (!(q instanceof ag))
                continue; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
            ag ag1 = (ag)q;
            if (uUID.equals(ag1.r())) {
              ag = ag1;
              break;
            } 
          } 
        } 
      } catch (ConcurrentModificationException concurrentModificationException) {}
      try {
        if (ag == null)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      try {
        if (ag.h() != b1.SHOULDER_IDLE)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      Minecraft.func_71410_x().func_147108_a(new aY(ag));
    }
    
    private static ConcurrentModificationException a(ConcurrentModificationException param1ConcurrentModificationException) {
      return param1ConcurrentModificationException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */