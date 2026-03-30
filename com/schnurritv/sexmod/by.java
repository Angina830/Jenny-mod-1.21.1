package com.schnurritv.sexmod;

import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class by extends bj {
  S m;
  
  EntityLivingBase r;
  
  Entity q;
  
  double k = 3.4028234663852886E38D;
  
  Vec3d p = Vec3d.field_186680_a;
  
  int j = 0;
  
  int l = 0;
  
  int n = 0;
  
  int i = 0;
  
  int o = 0;
  
  public by(S paramS) {
    super(paramS);
    this.m = paramS;
  }
  
  public void func_75246_d() {
    try {
      super.func_75246_d();
      this.k = this.m.func_70032_d((Entity)this.b);
      this.p = this.b.func_174791_d();
      if (this.m.h() == b1.BOW)
        this.m.b(b1.NULL); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  boolean a(EntityLivingBase paramEntityLivingBase) {
    Vec3d vec3d = this.m.func_174791_d();
    try {
      if (!(paramEntityLivingBase instanceof Q))
        try {
          if (this.l <= 0)
            try {
              if (paramEntityLivingBase != null)
                try {
                  if (paramEntityLivingBase.field_70170_p != null)
                    try {
                      if (!this.m.equals(paramEntityLivingBase))
                        try {
                          if (paramEntityLivingBase.func_70089_S())
                            try {
                              if (vec3d.func_72438_d(this.b.func_174791_d()) < 15.0D)
                                try {
                                  if (vec3d.func_72438_d(paramEntityLivingBase.func_174791_d()) < 20.0D)
                                    try {
                                      if (!paramEntityLivingBase.equals(this.b));
                                    } catch (NullPointerException nullPointerException) {
                                      throw a(null);
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
                    } catch (NullPointerException nullPointerException) {
                      throw a(null);
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
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return false;
  }
  
  protected void a(bj.a parama) {
    double d;
    Vec3d vec3d;
    switch (a.a[parama.ordinal()]) {
      case 1:
        this.m.func_70671_ap().func_75651_a((Entity)this.r, 30.0F, 30.0F);
        d = this.m.func_70032_d((Entity)this.r);
        try {
          this.h.func_75499_g();
          if (d < 1.9D)
            try {
              if (--this.n <= 0) {
                e();
                break;
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          if (this.m.H.getStackInSlot(1).func_77973_b() instanceof net.minecraft.item.ItemBow)
            try {
              if (this.m.func_70635_at().func_75522_a((Entity)this.r))
                try {
                  if (++this.i > 0)
                    try {
                      if (d > 6.0D) {
                        try {
                          this.e.func_187227_b(S.N, Integer.valueOf(2));
                          this.m.b(b1.BOW);
                          if (++this.i >= 32) {
                            this.i = -20;
                            d();
                            this.m.b(b1.NULL);
                          } 
                        } catch (NullPointerException nullPointerException) {
                          throw a(null);
                        } 
                        this.k = this.m.func_70032_d((Entity)this.b);
                        this.p = this.b.func_174791_d();
                        return;
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
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          if (d < 2.0D) {
            this.e.func_187227_b(S.N, Integer.valueOf(1));
            this.h.func_75497_a((Entity)this.r, 0.5D);
            this.m.a(Q.a.WALK);
            break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        this.e.func_187227_b(S.N, Integer.valueOf(1));
        this.h.func_75497_a((Entity)this.r, 0.7D);
        this.m.a(Q.a.RUN);
        break;
      case 2:
        this.e.func_187227_b(S.N, Integer.valueOf(0));
        d = this.m.func_70032_d((Entity)this.b);
        try {
          if (this.h.func_111269_d() > d) {
            try {
              this.h.func_75499_g();
              if (!this.m.S) {
                this.h.func_75497_a((Entity)this.b, 0.5D);
                c();
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            b();
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        this.j = 300;
        c();
        break;
      case 3:
        try {
          this.e.func_187227_b(S.N, Integer.valueOf(0));
          if (!this.m.S) {
            if (++this.j > 200 + bY.b.nextInt(100)) {
              this.j = 0;
              Vec3d vec3d1 = this.b.func_174791_d();
              Vec3d vec3d2 = new Vec3d(vec3d1.field_72450_a + 1.0D + (bY.b.nextFloat() * 3.0F), vec3d1.field_72448_b, vec3d1.field_72449_c + 1.0D + (bY.b.nextFloat() * 3.0F));
              this.h.func_75499_g();
              this.h.func_75492_a(vec3d2.field_72450_a, vec3d2.field_72448_b, vec3d2.field_72449_c, 0.5D);
            } 
            c();
            break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          if (this.m.func_70032_d((Entity)this.b) > 10.0F)
            b(); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        break;
      case 4:
        try {
          if (this.m.func_184218_aH()) {
            this.m.b(b1.SIT);
            break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        this.m.func_189654_d(true);
        this.m.field_70145_X = true;
        vec3d = this.b.func_174791_d().func_178786_a((this.q.func_70040_Z()).field_72450_a * 0.5D, 0.0D, (this.q.func_70040_Z()).field_72449_c * 0.5D);
        this.m.func_70080_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 0.0F, 0.0F);
        this.m.field_70159_w = 0.0D;
        this.m.field_70181_x = 0.0D;
        this.m.field_70179_y = 0.0D;
        this.m.b(b1.RIDE);
        break;
      case 5:
        this.h.func_75499_g();
        break;
    } 
  }
  
  protected bj.a a() {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: getfield l : I
    //   5: iconst_1
    //   6: isub
    //   7: putfield l : I
    //   10: aload_0
    //   11: getfield m : Lcom/schnurritv/sexmod/S;
    //   14: getfield S : Z
    //   17: ifne -> 37
    //   20: aload_0
    //   21: getfield m : Lcom/schnurritv/sexmod/S;
    //   24: invokevirtual B : ()Ljava/util/UUID;
    //   27: ifnull -> 45
    //   30: goto -> 37
    //   33: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   36: athrow
    //   37: getstatic com/schnurritv/sexmod/bj$a.DOWNED : Lcom/schnurritv/sexmod/bj$a;
    //   40: areturn
    //   41: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   44: athrow
    //   45: aload_0
    //   46: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   49: invokevirtual func_184218_aH : ()Z
    //   52: ifeq -> 138
    //   55: aload_0
    //   56: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   59: invokevirtual func_184187_bx : ()Lnet/minecraft/entity/Entity;
    //   62: astore_1
    //   63: aload_0
    //   64: getfield m : Lcom/schnurritv/sexmod/S;
    //   67: invokevirtual func_184218_aH : ()Z
    //   70: ifne -> 122
    //   73: aload_0
    //   74: getfield m : Lcom/schnurritv/sexmod/S;
    //   77: aload_1
    //   78: invokevirtual func_184220_m : (Lnet/minecraft/entity/Entity;)Z
    //   81: ifne -> 122
    //   84: goto -> 91
    //   87: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   90: athrow
    //   91: aload_1
    //   92: instanceof net/minecraft/entity/passive/EntityHorse
    //   95: ifeq -> 135
    //   98: goto -> 105
    //   101: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   104: athrow
    //   105: aload_1
    //   106: checkcast net/minecraft/entity/passive/EntityHorse
    //   109: invokevirtual func_110257_ck : ()Z
    //   112: ifeq -> 135
    //   115: goto -> 122
    //   118: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   121: athrow
    //   122: aload_0
    //   123: aload_1
    //   124: putfield q : Lnet/minecraft/entity/Entity;
    //   127: getstatic com/schnurritv/sexmod/bj$a.RIDE : Lcom/schnurritv/sexmod/bj$a;
    //   130: areturn
    //   131: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   134: athrow
    //   135: goto -> 239
    //   138: aload_0
    //   139: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   142: invokevirtual func_184218_aH : ()Z
    //   145: ifne -> 165
    //   148: aload_0
    //   149: getfield m : Lcom/schnurritv/sexmod/S;
    //   152: invokevirtual func_184218_aH : ()Z
    //   155: ifne -> 199
    //   158: goto -> 165
    //   161: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   164: athrow
    //   165: aload_0
    //   166: getfield f : Lcom/schnurritv/sexmod/bj$a;
    //   169: getstatic com/schnurritv/sexmod/bj$a.RIDE : Lcom/schnurritv/sexmod/bj$a;
    //   172: if_acmpne -> 239
    //   175: goto -> 182
    //   178: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   181: athrow
    //   182: aload_0
    //   183: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   186: invokevirtual func_184218_aH : ()Z
    //   189: ifne -> 239
    //   192: goto -> 199
    //   195: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   198: athrow
    //   199: aload_0
    //   200: getfield m : Lcom/schnurritv/sexmod/S;
    //   203: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
    //   206: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
    //   209: aload_0
    //   210: getfield m : Lcom/schnurritv/sexmod/S;
    //   213: invokevirtual func_184210_p : ()V
    //   216: aload_0
    //   217: getfield m : Lcom/schnurritv/sexmod/S;
    //   220: iconst_0
    //   221: putfield field_70145_X : Z
    //   224: aload_0
    //   225: getfield m : Lcom/schnurritv/sexmod/S;
    //   228: iconst_0
    //   229: invokevirtual func_189654_d : (Z)V
    //   232: goto -> 239
    //   235: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   238: athrow
    //   239: aload_0
    //   240: aload_0
    //   241: getfield r : Lnet/minecraft/entity/EntityLivingBase;
    //   244: invokevirtual a : (Lnet/minecraft/entity/EntityLivingBase;)Z
    //   247: ifeq -> 258
    //   250: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   253: areturn
    //   254: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   257: athrow
    //   258: aload_0
    //   259: getfield m : Lcom/schnurritv/sexmod/S;
    //   262: invokevirtual func_189748_bU : ()Lnet/minecraft/util/DamageSource;
    //   265: astore_2
    //   266: aload_2
    //   267: ifnull -> 299
    //   270: aload_2
    //   271: invokevirtual func_76346_g : ()Lnet/minecraft/entity/Entity;
    //   274: checkcast net/minecraft/entity/EntityLivingBase
    //   277: astore_1
    //   278: aload_0
    //   279: aload_1
    //   280: invokevirtual a : (Lnet/minecraft/entity/EntityLivingBase;)Z
    //   283: ifeq -> 299
    //   286: aload_0
    //   287: aload_1
    //   288: putfield r : Lnet/minecraft/entity/EntityLivingBase;
    //   291: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   294: areturn
    //   295: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   298: athrow
    //   299: aload_0
    //   300: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   303: invokevirtual func_110144_aD : ()Lnet/minecraft/entity/EntityLivingBase;
    //   306: astore_1
    //   307: aload_0
    //   308: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   311: getfield field_70173_aa : I
    //   314: aload_0
    //   315: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   318: invokevirtual func_142013_aG : ()I
    //   321: isub
    //   322: sipush #140
    //   325: if_icmpge -> 356
    //   328: aload_0
    //   329: aload_1
    //   330: invokevirtual a : (Lnet/minecraft/entity/EntityLivingBase;)Z
    //   333: ifeq -> 356
    //   336: goto -> 343
    //   339: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   342: athrow
    //   343: aload_0
    //   344: aload_1
    //   345: putfield r : Lnet/minecraft/entity/EntityLivingBase;
    //   348: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   351: areturn
    //   352: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   355: athrow
    //   356: aload_0
    //   357: getfield f : Lcom/schnurritv/sexmod/bj$a;
    //   360: getstatic com/schnurritv/sexmod/bj$a.FOLLOW : Lcom/schnurritv/sexmod/bj$a;
    //   363: if_acmpeq -> 573
    //   366: aload_0
    //   367: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   370: invokevirtual func_189748_bU : ()Lnet/minecraft/util/DamageSource;
    //   373: astore_2
    //   374: aload_2
    //   375: ifnull -> 407
    //   378: aload_2
    //   379: invokevirtual func_76346_g : ()Lnet/minecraft/entity/Entity;
    //   382: checkcast net/minecraft/entity/EntityLivingBase
    //   385: astore_1
    //   386: aload_0
    //   387: aload_1
    //   388: invokevirtual a : (Lnet/minecraft/entity/EntityLivingBase;)Z
    //   391: ifeq -> 407
    //   394: aload_0
    //   395: aload_1
    //   396: putfield r : Lnet/minecraft/entity/EntityLivingBase;
    //   399: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   402: areturn
    //   403: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   406: athrow
    //   407: aload_0
    //   408: getfield m : Lcom/schnurritv/sexmod/S;
    //   411: invokevirtual func_174791_d : ()Lnet/minecraft/util/math/Vec3d;
    //   414: astore_3
    //   415: new net/minecraft/util/math/AxisAlignedBB
    //   418: dup
    //   419: aload_3
    //   420: getfield field_72450_a : D
    //   423: ldc2_w 5.0
    //   426: dsub
    //   427: aload_3
    //   428: getfield field_72448_b : D
    //   431: ldc2_w 2.0
    //   434: dsub
    //   435: aload_3
    //   436: getfield field_72449_c : D
    //   439: ldc2_w 5.0
    //   442: dsub
    //   443: aload_3
    //   444: getfield field_72450_a : D
    //   447: ldc2_w 5.0
    //   450: dadd
    //   451: aload_3
    //   452: getfield field_72448_b : D
    //   455: ldc2_w 2.0
    //   458: dadd
    //   459: aload_3
    //   460: getfield field_72449_c : D
    //   463: ldc2_w 5.0
    //   466: dadd
    //   467: invokespecial <init> : (DDDDDD)V
    //   470: astore #4
    //   472: aload_0
    //   473: getfield m : Lcom/schnurritv/sexmod/S;
    //   476: getfield field_70170_p : Lnet/minecraft/world/World;
    //   479: ldc net/minecraft/entity/monster/EntityMob
    //   481: aload #4
    //   483: invokevirtual func_72872_a : (Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;
    //   486: astore #5
    //   488: aload #5
    //   490: aload_0
    //   491: <illegal opcode> compare : (Lcom/schnurritv/sexmod/by;)Ljava/util/Comparator;
    //   496: invokeinterface sort : (Ljava/util/Comparator;)V
    //   501: aload #5
    //   503: invokeinterface iterator : ()Ljava/util/Iterator;
    //   508: astore #6
    //   510: aload #6
    //   512: invokeinterface hasNext : ()Z
    //   517: ifeq -> 573
    //   520: aload #6
    //   522: invokeinterface next : ()Ljava/lang/Object;
    //   527: checkcast net/minecraft/entity/monster/EntityMob
    //   530: astore #7
    //   532: aload_0
    //   533: aload #7
    //   535: invokevirtual a : (Lnet/minecraft/entity/EntityLivingBase;)Z
    //   538: ifeq -> 570
    //   541: aload #7
    //   543: instanceof net/minecraft/entity/monster/EntityCreeper
    //   546: ifne -> 570
    //   549: goto -> 556
    //   552: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   555: athrow
    //   556: aload_0
    //   557: aload #7
    //   559: putfield r : Lnet/minecraft/entity/EntityLivingBase;
    //   562: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   565: areturn
    //   566: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   569: athrow
    //   570: goto -> 510
    //   573: aload_0
    //   574: getfield m : Lcom/schnurritv/sexmod/S;
    //   577: aload_0
    //   578: getfield b : Lnet/minecraft/entity/player/EntityPlayer;
    //   581: invokevirtual func_70032_d : (Lnet/minecraft/entity/Entity;)F
    //   584: fstore_3
    //   585: fload_3
    //   586: ldc 5.0
    //   588: fcmpl
    //   589: ifle -> 600
    //   592: iconst_1
    //   593: goto -> 601
    //   596: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   599: athrow
    //   600: iconst_0
    //   601: istore #4
    //   603: iload #4
    //   605: ifne -> 662
    //   608: aload_0
    //   609: getfield f : Lcom/schnurritv/sexmod/bj$a;
    //   612: getstatic com/schnurritv/sexmod/bj$a.FOLLOW : Lcom/schnurritv/sexmod/bj$a;
    //   615: if_acmpne -> 662
    //   618: goto -> 625
    //   621: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   624: athrow
    //   625: aload_0
    //   626: dup
    //   627: getfield o : I
    //   630: iconst_1
    //   631: iadd
    //   632: dup_x1
    //   633: putfield o : I
    //   636: bipush #60
    //   638: if_icmple -> 659
    //   641: goto -> 648
    //   644: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   647: athrow
    //   648: iconst_0
    //   649: istore #4
    //   651: aload_0
    //   652: iconst_0
    //   653: putfield o : I
    //   656: goto -> 662
    //   659: iconst_1
    //   660: istore #4
    //   662: iload #4
    //   664: ifeq -> 697
    //   667: aload_0
    //   668: getfield f : Lcom/schnurritv/sexmod/bj$a;
    //   671: getstatic com/schnurritv/sexmod/bj$a.ATTACK : Lcom/schnurritv/sexmod/bj$a;
    //   674: if_acmpne -> 697
    //   677: goto -> 684
    //   680: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   683: athrow
    //   684: aload_0
    //   685: bipush #60
    //   687: putfield l : I
    //   690: goto -> 697
    //   693: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   696: athrow
    //   697: iload #4
    //   699: ifeq -> 710
    //   702: getstatic com/schnurritv/sexmod/bj$a.FOLLOW : Lcom/schnurritv/sexmod/bj$a;
    //   705: areturn
    //   706: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
    //   709: athrow
    //   710: getstatic com/schnurritv/sexmod/bj$a.IDLE : Lcom/schnurritv/sexmod/bj$a;
    //   713: areturn
    // Exception table:
    //   from	to	target	type
    //   0	30	33	java/lang/NullPointerException
    //   20	41	41	java/lang/NullPointerException
    //   63	84	87	java/lang/NullPointerException
    //   73	98	101	java/lang/NullPointerException
    //   91	115	118	java/lang/NullPointerException
    //   105	131	131	java/lang/NullPointerException
    //   138	158	161	java/lang/NullPointerException
    //   148	175	178	java/lang/NullPointerException
    //   165	192	195	java/lang/NullPointerException
    //   182	232	235	java/lang/NullPointerException
    //   239	254	254	java/lang/NullPointerException
    //   278	295	295	java/lang/NullPointerException
    //   307	336	339	java/lang/NullPointerException
    //   328	352	352	java/lang/NullPointerException
    //   386	403	403	java/lang/NullPointerException
    //   532	549	552	java/lang/NullPointerException
    //   541	566	566	java/lang/NullPointerException
    //   585	596	596	java/lang/NullPointerException
    //   603	618	621	java/lang/NullPointerException
    //   608	641	644	java/lang/NullPointerException
    //   662	677	680	java/lang/NullPointerException
    //   667	690	693	java/lang/NullPointerException
    //   697	706	706	java/lang/NullPointerException
  }
  
  public void d() {
    EntityArrow entityArrow = a();
    double d1 = this.r.field_70165_t - this.m.field_70165_t;
    double d2 = (this.r.func_174813_aQ()).field_72338_b + (this.r.field_70131_O / 3.0F) - entityArrow.field_70163_u;
    double d3 = this.r.field_70161_v - this.m.field_70161_v;
    double d4 = MathHelper.func_76133_a(d1 * d1 + d3 * d3);
    entityArrow.func_70186_c(d1, d2 + d4 * 0.20000000298023224D, d3, 1.6F, 2.0F);
    this.m.func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (this.m.func_70681_au().nextFloat() * 0.4F + 0.8F));
    this.m.field_70170_p.func_72838_d((Entity)entityArrow);
    entityArrow.func_70239_b(4.5D);
  }
  
  protected EntityArrow a() {
    EntityTippedArrow entityTippedArrow = new EntityTippedArrow(this.m.field_70170_p, (EntityLivingBase)this.m);
    ItemStack itemStack = this.m.H.getStackInSlot(1);
    double d = EnchantmentHelper.func_77506_a(Enchantments.field_185309_u, itemStack);
    int i = EnchantmentHelper.func_77506_a(Enchantments.field_185310_v, itemStack);
    int j = EnchantmentHelper.func_77506_a(Enchantments.field_185311_w, itemStack);
    try {
      if (d != 0.0D)
        entityTippedArrow.func_70239_b(entityTippedArrow.func_70242_d() + d * 0.5D + 0.5D); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (i != 0)
        entityTippedArrow.func_70240_a(i); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (j != 0)
        entityTippedArrow.func_70015_d(100); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (EntityArrow)entityTippedArrow;
  }
  
  void e() {
    this.m.b(b1.ATTACK);
    this.e.func_187227_b(S.N, Integer.valueOf(1));
    ItemStack itemStack = this.m.H.getStackInSlot(0);
    Multimap multimap = itemStack.func_111283_C(EntityEquipmentSlot.MAINHAND);
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (AttributeModifier attributeModifier : multimap.get(SharedMonsterAttributes.field_111264_e.func_111108_a()))
      f1 = (float)attributeModifier.func_111164_d(); 
    for (AttributeModifier attributeModifier : multimap.get(SharedMonsterAttributes.field_188790_f.func_111108_a()))
      f2 = (float)attributeModifier.func_111164_d(); 
    f2 = Math.max(f2, 0.5F);
    float f3 = EnchantmentHelper.func_152377_a(itemStack, this.r.func_70668_bt());
    int i = EnchantmentHelper.func_77506_a(Enchantments.field_180313_o, itemStack);
    int j = EnchantmentHelper.func_77506_a(Enchantments.field_77334_n, itemStack);
    int k = EnchantmentHelper.func_77506_a(Enchantments.field_191530_r, itemStack);
    this.r.func_70653_a((Entity)this.m, i * 0.5F, MathHelper.func_76126_a(this.m.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.m.field_70177_z * 0.017453292F));
    this.r.func_70015_d(j * 4);
    if (k != 0) {
      float f = 0.5F;
      if (k == 2) {
        f = 0.67F;
      } else if (k == 3) {
        f = 0.75F;
      } 
      for (EntityLivingBase entityLivingBase : this.m.field_70170_p.func_72872_a(EntityLivingBase.class, this.r.func_174813_aQ().func_72314_b(1.0D, 0.25D, 1.0D))) {
        try {
          if (entityLivingBase != this.m)
            try {
              if (entityLivingBase != this.b)
                try {
                  if (entityLivingBase != this.r)
                    try {
                      if (!this.m.func_184191_r((Entity)entityLivingBase))
                        try {
                          if (this.m.func_70068_e((Entity)entityLivingBase) < 9.0D) {
                            entityLivingBase.func_70653_a((Entity)this.m, 0.4F, MathHelper.func_76126_a(this.m.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.m.field_70177_z * 0.017453292F));
                            entityLivingBase.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this.m), (f1 + f3) * f);
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
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } 
    this.r.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this.m), f1 + f3);
    this.n = Math.round(Math.abs(f2) / 3.373494F * 20.0F);
  }
  
  protected double c() {
    double d = super.c();
    if (this.m.S)
      d = 0.0D; 
    this.h.func_75489_a(d);
    this.m.a(this.m.o());
    return d;
  }
  
  public void func_75251_c() {
    super.func_75251_c();
    this.m.func_184212_Q().func_187227_b(S.N, Integer.valueOf(0));
  }
  
  void c() {
    try {
      if (!this.m.field_70122_E)
        try {
          if (!this.m.func_70090_H())
            try {
              if (this.m.field_70159_w + this.m.field_70179_y == 0.0D)
                try {
                  if (this.m.field_70181_x > 0.0D) {
                    Vec3d vec3d = new Vec3d(0.0D, 0.0D, 0.10000000149011612D);
                    vec3d = bZ.a(vec3d, this.m.field_70177_z);
                    this.m.field_70159_w = vec3d.field_72450_a;
                    this.m.field_70179_y = vec3d.field_72449_c;
                    return;
                  } 
                  return;
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
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
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  @EventBusSubscriber
  public static class b {
    @SubscribeEvent
    public void a(LivingHurtEvent param1LivingHurtEvent) {
      if (param1LivingHurtEvent.getEntityLiving() instanceof S) {
        S s = (S)param1LivingHurtEvent.getEntityLiving();
        try {
          if (s.S) {
            param1LivingHurtEvent.setCanceled(true);
          } else {
            try {
              if (s.func_110143_aJ() - param1LivingHurtEvent.getAmount() < 0.0F)
                try {
                  if (!((String)s.func_184212_Q().func_187225_a(S.p)).equals("")) {
                    s.S = true;
                    s.b(b1.DOWNED);
                    param1LivingHurtEvent.setAmount(s.func_110143_aJ() - 1.0F);
                    s.func_70661_as().func_75499_g();
                  } 
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
      } 
    }
    
    @SubscribeEvent
    public void a(LivingHealEvent param1LivingHealEvent) {
      if (param1LivingHealEvent.getEntityLiving() instanceof S) {
        S s = (S)param1LivingHealEvent.getEntityLiving();
        try {
          if (s.S)
            try {
              if (s.func_110143_aJ() + param1LivingHealEvent.getAmount() >= s.func_110138_aP()) {
                s.S = false;
                s.b(b1.NULL);
              } 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    }
    
    @SubscribeEvent
    public void a(LivingDeathEvent param1LivingDeathEvent) {
      if (param1LivingDeathEvent.getEntityLiving() instanceof S) {
        S s = (S)param1LivingDeathEvent.getEntityLiving();
        try {
          if (s.field_70170_p.field_72995_K)
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        for (byte b1 = 0; b1 < 6; b1++) {
          Item item = s.H.getStackInSlot(b1).func_77973_b();
          try {
            if (item != Items.field_190931_a)
              s.func_145779_a(item, 1); 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
        } 
      } 
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\by.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */