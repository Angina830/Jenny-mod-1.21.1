package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class bk extends EntityLiving {
  public static int e = 8400;
  
  public static List<bk> h = new ArrayList<>();
  
  private static final DataParameter<Integer> b = EntityDataManager.func_187226_a(bk.class, DataSerializers.field_187192_b).func_187156_b().func_187161_a(111);
  
  private static final DataParameter<Integer> a = EntityDataManager.func_187226_a(bk.class, DataSerializers.field_187192_b).func_187156_b().func_187161_a(110);
  
  public float d;
  
  public float f;
  
  public float c;
  
  private boolean g;
  
  public bk(World paramWorld) {
    super(paramWorld);
  }
  
  protected void func_184651_r() {
    this.field_70714_bg.func_75776_a(1, new d(this));
    this.field_70714_bg.func_75776_a(5, new a(this));
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(a, Integer.valueOf(1));
    this.field_70180_af.func_187214_a(b, Integer.valueOf(0));
  }
  
  public void func_180430_e(float paramFloat1, float paramFloat2) {}
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  protected void a(int paramInt, boolean paramBoolean) {
    try {
      this.field_70180_af.func_187227_b(a, Integer.valueOf(paramInt));
      func_70105_a(0.51000005F * paramInt, 0.51000005F * paramInt);
      func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((paramInt * paramInt));
      func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a((0.2F + 0.1F * paramInt));
      if (paramBoolean)
        func_70606_j(func_110138_aP()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.field_70728_aV = paramInt;
  }
  
  public int g() {
    return ((Integer)this.field_70180_af.func_187225_a(a)).intValue();
  }
  
  public static void a(DataFixer paramDataFixer) {
    EntityLiving.func_189752_a(paramDataFixer, bk.class);
  }
  
  public void func_70014_b(NBTTagCompound paramNBTTagCompound) {
    super.func_70014_b(paramNBTTagCompound);
    paramNBTTagCompound.func_74768_a("Size", g() - 1);
    paramNBTTagCompound.func_74757_a("wasOnGround", this.g);
    paramNBTTagCompound.func_74768_a("ageInTicks", ((Integer)this.field_70180_af.func_187225_a(b)).intValue());
  }
  
  public void func_70037_a(NBTTagCompound paramNBTTagCompound) {
    super.func_70037_a(paramNBTTagCompound);
    int i = paramNBTTagCompound.func_74762_e("Size");
    if (i < 0)
      i = 0; 
    a(i + 1, false);
    this.g = paramNBTTagCompound.func_74767_n("wasOnGround");
    this.field_70180_af.func_187227_b(b, Integer.valueOf(paramNBTTagCompound.func_74762_e("ageInTicks")));
  }
  
  public boolean d() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (g() <= 1);
  }
  
  protected EnumParticleTypes a() {
    return EnumParticleTypes.SLIME;
  }
  
  public static ArrayList<bk> a(Vec3d paramVec3d) {
    ArrayList<bk> arrayList = a(paramVec3d, 0.1D);
    if (arrayList.isEmpty())
      arrayList = a(paramVec3d, 0.5D); 
    return arrayList;
  }
  
  private static ArrayList<bk> a(Vec3d paramVec3d, double paramDouble) {
    ArrayList<bk> arrayList = new ArrayList();
    try {
      for (bk bk1 : h) {
        try {
          if (bk1 == null)
            continue; 
        } catch (Exception exception) {
          throw a(null);
        } 
        double d = Math.abs(bk1.field_70169_q - paramVec3d.field_72450_a) + Math.abs(bk1.field_70167_r - paramVec3d.field_72448_b) + Math.abs(bk1.field_70166_s - paramVec3d.field_72449_c);
        try {
          if (bk1.field_70170_p != null)
            try {
              if (d < paramDouble)
                arrayList.add(bk1); 
            } catch (Exception exception) {
              throw a(null);
            }  
        } catch (Exception exception) {
          throw a(null);
        } 
      } 
    } catch (Exception exception) {
      System.out.println("couldnt find slimes at distance " + paramDouble);
    } 
    return arrayList;
  }
  
  public Vec3d i() {
    return new Vec3d(this.field_70169_q, this.field_70167_r, this.field_70166_s);
  }
  
  void a(EnumParticleTypes paramEnumParticleTypes) {
    double d1 = bY.b.nextGaussian() * 0.02D;
    double d2 = bY.b.nextGaussian() * 0.02D;
    double d3 = bY.b.nextGaussian() * 0.02D;
    this.field_70170_p.func_175688_a(paramEnumParticleTypes, this.field_70165_t + (bY.b.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 0.15D + (bY.b.nextFloat() * this.field_70131_O), this.field_70161_v + (bY.b.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d1, d2, d3, new int[0]);
  }
  
  public void func_70071_h_() {
    try {
      this.field_70180_af.func_187227_b(b, Integer.valueOf(((Integer)this.field_70180_af.func_187225_a(b)).intValue() + 1));
      if (this.field_70170_p.field_72995_K) {
        try {
          if (((Integer)this.field_70180_af.func_187225_a(b)).intValue() > e * 0.95D) {
            a(EnumParticleTypes.CLOUD);
          } else {
            try {
              if (((Integer)this.field_70180_af.func_187225_a(b)).intValue() > e * 0.7D)
                try {
                  if (this.field_70173_aa % 10 == 0)
                    a(EnumParticleTypes.VILLAGER_HAPPY); 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                }  
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else if (((Integer)this.field_70180_af.func_187225_a(b)).intValue() > e) {
        aF aF = new aF(this.field_70170_p);
        aF.func_70080_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
        this.field_70170_p.func_72838_d((Entity)aF);
        aF.a(SoundEvents.field_187604_bf);
        this.field_70170_p.func_72900_e((Entity)this);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      this.f += (this.d - this.f) * 0.5F;
      this.c = this.f;
      super.func_70071_h_();
      if (this.field_70122_E && !this.g) {
        int i = g();
        if (j())
          i = 0; 
        for (byte b = 0; b < i * 8; b++) {
          float f1 = this.field_70146_Z.nextFloat() * 6.2831855F;
          float f2 = this.field_70146_Z.nextFloat() * 0.5F + 0.5F;
          float f3 = MathHelper.func_76126_a(f1) * i * 0.5F * f2;
          float f4 = MathHelper.func_76134_b(f1) * i * 0.5F * f2;
          World world = this.field_70170_p;
          EnumParticleTypes enumParticleTypes = a();
          double d1 = this.field_70165_t + f3;
          double d2 = this.field_70161_v + f4;
          world.func_175688_a(enumParticleTypes, d1, (func_174813_aQ()).field_72338_b, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        } 
        func_184185_a(k(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        this.d = -0.5F;
      } else {
        try {
          if (!this.field_70122_E)
            try {
              if (this.g)
                this.d = 1.0F; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.g = this.field_70122_E;
    f();
  }
  
  protected void f() {
    this.d *= 0.6F;
  }
  
  protected int e() {
    return this.field_70146_Z.nextInt(100) + 50;
  }
  
  protected bk h() {
    return new bk(this.field_70170_p);
  }
  
  public void func_184206_a(DataParameter<?> paramDataParameter) {
    if (a.equals(paramDataParameter)) {
      int i = g();
      try {
        func_70105_a(0.51000005F * i, 0.51000005F * i);
        this.field_70177_z = this.field_70759_as;
        this.field_70761_aq = this.field_70759_as;
        if (func_70090_H())
          try {
            if (this.field_70146_Z.nextInt(20) == 0)
              func_71061_d_(); 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    super.func_184206_a(paramDataParameter);
  }
  
  public void func_70106_y() {
    int i = g();
    try {
      if (!this.field_70170_p.field_72995_K)
        try {
          if (i > 1 && func_110143_aJ() <= 0.0F) {
            int j = 2 + this.field_70146_Z.nextInt(3);
            for (byte b = 0; b < j; b++) {
              float f1 = ((b % 2) - 0.5F) * i / 4.0F;
              float f2 = ((b / 2) - 0.5F) * i / 4.0F;
              bk bk1 = h();
              try {
                if (func_145818_k_())
                  bk1.func_96094_a(func_95999_t()); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (func_104002_bU())
                  bk1.func_110163_bv(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              bk1.a(i / 2, true);
              bk1.func_70012_b(this.field_70165_t + f1, this.field_70163_u + 0.5D, this.field_70161_v + f2, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
              this.field_70170_p.func_72838_d((Entity)bk1);
            } 
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    super.func_70106_y();
  }
  
  public float func_70047_e() {
    return 0.625F * this.field_70131_O;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource paramDamageSource) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return d() ? SoundEvents.field_187898_fy : SoundEvents.field_187880_fp;
  }
  
  protected SoundEvent func_184615_bR() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return d() ? SoundEvents.field_187896_fx : SoundEvents.field_187874_fm;
  }
  
  protected SoundEvent k() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return d() ? SoundEvents.field_187900_fz : SoundEvents.field_187886_fs;
  }
  
  protected Item func_146068_u() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (g() == 1) ? Items.field_151123_aH : null;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (g() == 1) ? LootTableList.field_186378_ac : LootTableList.field_186419_a;
  }
  
  protected float func_70599_aP() {
    return 0.4F * g();
  }
  
  public int func_70646_bf() {
    return 0;
  }
  
  protected boolean b() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (g() > 0);
  }
  
  protected void func_70664_aZ() {
    this.field_70181_x = 0.41999998688697815D;
    this.field_70160_al = true;
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance paramDifficultyInstance, @Nullable IEntityLivingData paramIEntityLivingData) {
    a(1, true);
    return super.func_180482_a(paramDifficultyInstance, paramIEntityLivingData);
  }
  
  protected SoundEvent c() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return d() ? SoundEvents.field_189110_fE : SoundEvents.field_187882_fq;
  }
  
  protected boolean j() {
    return false;
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  static class c extends EntityMoveHelper {
    private float c;
    
    private int b;
    
    private final bk a;
    
    private boolean d;
    
    public c(bk param1bk) {
      super(param1bk);
      this.a = param1bk;
      this.c = 180.0F * param1bk.field_70177_z / 3.1415927F;
    }
    
    public void a(float param1Float, boolean param1Boolean) {
      this.c = param1Float;
      this.d = param1Boolean;
    }
    
    public void a(double param1Double) {
      this.field_75645_e = param1Double;
      this.field_188491_h = EntityMoveHelper.Action.MOVE_TO;
    }
    
    public void func_75641_c() {
      try {
        this.field_75648_a.field_70177_z = func_75639_a(this.field_75648_a.field_70177_z, this.c, 90.0F);
        this.field_75648_a.field_70759_as = this.field_75648_a.field_70177_z;
        this.field_75648_a.field_70761_aq = this.field_75648_a.field_70177_z;
        if (this.field_188491_h != EntityMoveHelper.Action.MOVE_TO) {
          this.field_75648_a.func_191989_p(0.0F);
        } else {
          try {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
            if (this.field_75648_a.field_70122_E) {
              try {
                this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
                if (this.b-- <= 0) {
                  try {
                    this.b = this.a.e();
                    if (this.d)
                      this.b /= 3; 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                  float f = bY.b.nextInt(360);
                  try {
                    ((c)this.a.func_70605_aq()).a(f, false);
                    this.a.func_70683_ar().func_75660_a();
                    if (this.a.b())
                      this.a.func_184185_a(this.a.c(), this.a.func_70599_aP(), ((this.a.func_70681_au().nextFloat() - this.a.func_70681_au().nextFloat()) * 0.2F + 1.0F) * 0.8F); 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                } else {
                  this.a.field_70702_br = 0.0F;
                  this.a.field_191988_bg = 0.0F;
                  this.field_75648_a.func_70659_e(0.0F);
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
            } else {
              this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
  
  static class a extends EntityAIBase {
    private final bk a;
    
    public a(bk param1bk) {
      this.a = param1bk;
      func_75248_a(5);
    }
    
    public boolean func_75250_a() {
      return true;
    }
    
    public void func_75246_d() {
      ((bk.c)this.a.func_70605_aq()).a(1.0D);
    }
  }
  
  static class d extends EntityAIBase {
    private final bk a;
    
    public d(bk param1bk) {
      this.a = param1bk;
      func_75248_a(5);
      ((PathNavigateGround)param1bk.func_70661_as()).func_179693_d(true);
    }
    
    public boolean func_75250_a() {
      try {
        if (!this.a.func_70090_H()) {
          try {
            if (this.a.func_180799_ab());
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return false;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    }
    
    public void func_75246_d() {
      try {
        if (this.a.func_70681_au().nextFloat() < 0.8F)
          this.a.func_70683_ar().func_75660_a(); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      ((bk.c)this.a.func_70605_aq()).a(1.2D);
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
  
  static class b extends EntityAIBase {
    private final bk a;
    
    private float b;
    
    private int c;
    
    public b(bk param1bk) {
      this.a = param1bk;
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      // Byte code:
      //   0: aload_0
      //   1: getfield a : Lcom/schnurritv/sexmod/bk;
      //   4: invokevirtual func_70638_az : ()Lnet/minecraft/entity/EntityLivingBase;
      //   7: ifnonnull -> 89
      //   10: aload_0
      //   11: getfield a : Lcom/schnurritv/sexmod/bk;
      //   14: getfield field_70122_E : Z
      //   17: ifne -> 81
      //   20: goto -> 27
      //   23: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
      //   26: athrow
      //   27: aload_0
      //   28: getfield a : Lcom/schnurritv/sexmod/bk;
      //   31: invokevirtual func_70090_H : ()Z
      //   34: ifne -> 81
      //   37: goto -> 44
      //   40: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
      //   43: athrow
      //   44: aload_0
      //   45: getfield a : Lcom/schnurritv/sexmod/bk;
      //   48: invokevirtual func_180799_ab : ()Z
      //   51: ifne -> 81
      //   54: goto -> 61
      //   57: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
      //   60: athrow
      //   61: aload_0
      //   62: getfield a : Lcom/schnurritv/sexmod/bk;
      //   65: getstatic net/minecraft/init/MobEffects.field_188424_y : Lnet/minecraft/potion/Potion;
      //   68: invokevirtual func_70644_a : (Lnet/minecraft/potion/Potion;)Z
      //   71: ifeq -> 89
      //   74: goto -> 81
      //   77: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
      //   80: athrow
      //   81: iconst_1
      //   82: goto -> 90
      //   85: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
      //   88: athrow
      //   89: iconst_0
      //   90: ireturn
      // Exception table:
      //   from	to	target	type
      //   0	20	23	java/lang/NullPointerException
      //   10	37	40	java/lang/NullPointerException
      //   27	54	57	java/lang/NullPointerException
      //   44	74	77	java/lang/NullPointerException
      //   61	85	85	java/lang/NullPointerException
    }
    
    public void func_75246_d() {
      try {
        if (--this.c <= 0) {
          this.c = 40 + this.a.func_70681_au().nextInt(60);
          this.b = this.a.func_70681_au().nextInt(360);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      ((bk.c)this.a.func_70605_aq()).a(this.b, false);
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */