package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ae extends S implements as {
  static final float ac = 10.0F;
  
  static final int ad = 16;
  
  static final int V = 79;
  
  static final int X = 109;
  
  static final int W = 150;
  
  static final int ak = 20;
  
  static final int ag = 110;
  
  static final int ah = 4;
  
  int Y = -1;
  
  boolean ai = false;
  
  boolean ab = false;
  
  boolean al = false;
  
  int T = -1;
  
  int aa = -1;
  
  int am = -1;
  
  int af = -1;
  
  boolean an = false;
  
  Object[] Z;
  
  int U = -1;
  
  int ae = 1;
  
  boolean aj = false;
  
  public ae(World paramWorld) {
    super(paramWorld);
  }
  
  public ae(World paramWorld, boolean paramBoolean) {
    this(paramWorld);
    this.y = paramBoolean;
  }
  
  public void f() {
    e("Okay, I will be residing here then..");
    a(c.GIRLS_ELLIE_HUH[0], 6.0F);
  }
  
  public String F() {
    return "Ellie";
  }
  
  protected ResourceLocation func_184647_J() {
    return b8.b;
  }
  
  boolean v() {
    try {
      if (this.y)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (this.field_70170_p.func_180495_p(func_180425_c().func_177982_a(0, 2, 0)).func_177230_c() != Blocks.field_150350_a);
  }
  
  public float func_70047_e() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return v() ? 1.53F : 1.9F;
  }
  
  public float z() {
    return 0.4F;
  }
  
  public void b() {
    UUID uUID = B();
    try {
      if (uUID == null) {
        m();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null) {
        m();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f = entityPlayer.field_70177_z - 180.0F;
    a(f);
    b(b1.CARRY_INTRO);
    a(true);
  }
  
  public boolean y() {
    try {
      if (h() == b1.CARRY_INTRO)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return true;
  }
  
  public boolean a(EntityPlayer paramEntityPlayer, boolean paramBoolean) {
    try {
      if (paramBoolean) {
        a(paramEntityPlayer, this, new String[] { "action.names.cowgirl", "action.names.missionary" }, false);
        return true;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Integer)this.m.func_187225_a(v)).intValue() == 0) {
        a(paramEntityPlayer, this, new String[] { "action.names.dressup" }, true);
        return true;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    a(paramEntityPlayer, this, new String[] { "Face fuck" }, true);
    return true;
  }
  
  public void n() {
    super.n();
    e("stay safe darling~");
    a(c.GIRLS_ELLIE_SIGH[1], 6.0F);
  }
  
  public void a(String paramString, UUID paramUUID) {
    super.a(paramString, paramUUID);
    this.ai = true;
    String str = paramString;
    byte b = -1;
    try {
      switch (str.hashCode()) {
        case 1770340474:
          if (str.equals("action.names.missionary"))
            b = 0; 
          break;
        case 548925513:
          if (str.equals("action.names.cowgirl"))
            b = 1; 
          break;
        case 1506060468:
          if (str.equals("action.names.dressup"))
            b = 2; 
          break;
        case 1257948474:
          if (str.equals("action.names.strip"))
            b = 3; 
          break;
        case 173919546:
          if (str.equals("Face fuck"))
            b = 4; 
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      switch (b) {
        case 0:
          b(b1.HUGSELECTED);
          a("animationFollowUp", "Missionary");
          break;
        case 1:
          b(b1.HUGSELECTED);
          a("animationFollowUp", "cowgirl");
          break;
        case 2:
        case 3:
          b(b1.STRIP);
          a("animationFollowUp", "");
          break;
        case 4:
          a(true, true, paramUUID);
          bf.a(false);
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  protected void a(EntityPlayerMP paramEntityPlayerMP, boolean paramBoolean) {}
  
  public void b(b1 paramb1) {
    b1 b11 = h();
    try {
      if (paramb1 == b1.HUGSELECTED)
        try {
          if (!this.field_70170_p.field_72995_K)
            this.af = 79; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.MISSIONARY_CUM)
        try {
          if (paramb1 != b1.MISSIONARY_FAST) {
            try {
              if (paramb1 == b1.MISSIONARY_SLOW)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.COWGIRLCUM)
        try {
          if (paramb1 != b1.COWGIRLSLOW) {
            try {
              if (paramb1 == b1.COWGIRLFAST)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (b11 == b1.CARRY_CUM)
        try {
          if (paramb1 != b1.CARRY_SLOW) {
            try {
              if (paramb1 == b1.CARRY_FAST)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.CARRY_INTRO)
        this.Y = 0; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    super.b(paramb1);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70071_h_() {
    try {
      super.func_70071_h_();
      if (this.ab) {
        a((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, true);
        this.ab = false;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    o();
    h();
  }
  
  void h() {
    try {
      if (aC.c())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (h() != b1.CARRY_SLOW)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    aC.a();
  }
  
  void w() {
    try {
      if (this.Y == -1)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (++this.Y < 110)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      this.Y = -1;
      if (h() != b1.CARRY_INTRO)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    UUID uUID = B();
    try {
      if (uUID == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f = m().floatValue();
    Vec3d vec3d = x().func_178787_e(bZ.a(new Vec3d(0.0D, (2.5625F - entityPlayer.func_70047_e()), -0.3125D), 180.0F + f));
    entityPlayer.func_70634_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
  }
  
  void o() {
    try {
      if (h() != b1.SITDOWNIDLE)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, 10.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (func_70032_d((Entity)entityPlayer) > 1.5F)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (entityPlayer.getPersistentID().equals((Minecraft.func_71410_x()).field_71439_g.getPersistentID()))
        a5.a(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public void func_70619_bc() {
    super.func_70619_bc();
    z();
    s();
    q();
    r();
    p();
    t();
    x();
    y();
  }
  
  void z() {
    try {
      if (this.al)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.al = true;
    this.field_70145_X = false;
    func_189654_d(false);
  }
  
  protected void K() {
    String str = (String)this.m.func_187225_a(f);
    if ("Missionary".equals(str)) {
      this.m.func_187227_b(v, Integer.valueOf(0));
      b(b1.MISSIONARY_START);
      UUID uUID = B();
      try {
        if (uUID == null)
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
      try {
        if (entityPlayer == null) {
          D();
          return;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      entityPlayer.func_189654_d(true);
      entityPlayer.field_70145_X = true;
      Vec3d vec3d1 = x();
      entityPlayer.field_70177_z = m().floatValue();
      Vec3d vec3d2 = bZ.a(new Vec3d(0.0D, 0.0D, 0.1D), entityPlayer.field_70177_z);
      vec3d1 = vec3d1.func_178787_e(vec3d2);
      entityPlayer.func_70634_a(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c);
      bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer);
    } 
    if ("cowgirl".equals(str)) {
      this.m.func_187227_b(v, Integer.valueOf(0));
      b(b1.COWGIRLSTART);
      UUID uUID = B();
      try {
        if (uUID == null)
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
      try {
        if (entityPlayer == null) {
          D();
          return;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      entityPlayer.func_189654_d(true);
      entityPlayer.field_70145_X = true;
      Vec3d vec3d1 = x();
      entityPlayer.field_70177_z = m().floatValue() + 180.0F;
      Vec3d vec3d2 = bZ.a(new Vec3d(0.0D, 1.0D - entityPlayer.eyeHeight, -1.8125D), entityPlayer.field_70177_z);
      vec3d1 = vec3d1.func_178787_e(vec3d2);
      entityPlayer.func_70634_a(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c);
      bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer);
    } 
  }
  
  void y() {
    try {
      if (--this.T != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    K();
  }
  
  void x() {
    try {
      if (h() == b1.SITDOWNIDLE)
        try {
          if (this.T < 0) {
            EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, 10.0D);
            try {
              if (entityPlayer == null)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            try {
              if (func_70032_d((Entity)entityPlayer) > 1.5F)
                return; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            this.T = 20;
            d(entityPlayer.getPersistentID());
            return;
          } 
          return;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void t() {
    try {
      if (--this.aa != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    b(b1.HUGIDLE);
  }
  
  void p() {
    try {
      if (--this.am != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    b(b1.SITDOWNIDLE);
  }
  
  void r() {
    try {
      if (--this.af != 0)
        try {
          if (!this.an)
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      this.an = true;
      this.m.func_187227_b(c, Boolean.valueOf(false));
      b(b1.NULL);
      this.field_70145_X = false;
      func_189654_d(false);
      if (this.Z == null)
        this.Z = a(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.Z == null) {
        b("no bed in sight...");
        this.field_70170_p.func_184133_a(null, func_180425_c(), c.GIRLS_ELLIE_SIGH[0], SoundCategory.NEUTRAL, 6.0F, 1.0F);
        l();
        m();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(B());
    try {
      if (entityPlayer != null) {
        entityPlayer.func_189654_d(false);
        entityPlayer.field_70145_X = false;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d = (Vec3d)this.Z[0];
    int i = ((Integer)this.Z[1]).intValue();
    try {
      if (vec3d.func_72438_d(func_174791_d()) > 1.0D) {
        func_70661_as().func_75492_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, 0.3499999940395355D);
        c();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    a(vec3d);
    a(i);
    b(b1.SITDOWN);
    this.m.func_187227_b(c, Boolean.valueOf(true));
    this.am = 109;
    this.field_70145_X = true;
    func_189654_d(true);
    this.an = false;
    this.Z = null;
  }
  
  public void d() {
    super.d();
    this.aa = -1;
  }
  
  Object[] a() {
    byte b = -1;
    byte b1 = 0;
    Vec3d[][] arrayOfVec3d = { { new Vec3d(0.5D, 0.0D, -0.18D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D) }, { new Vec3d(0.5D, 0.0D, 1.18D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D) }, { new Vec3d(-0.18D, 0.0D, 0.5D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D) }, { new Vec3d(1.18D, 0.0D, 0.5D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 0.0D) } };
    int[] arrayOfInt = { 0, 180, -90, 90 };
    while (true) {
      BlockPos blockPos = a(func_180425_c(), ++b1);
      if (blockPos == null)
        return null; 
      Vec3d vec3d = new Vec3d(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
      for (byte b2 = 0; b2 < arrayOfVec3d.length; b2++) {
        Vec3d vec3d1 = vec3d.func_178787_e(arrayOfVec3d[b2][1]);
        Block block1 = this.field_70170_p.func_180495_p(new BlockPos(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c)).func_177230_c();
        Vec3d vec3d2 = vec3d.func_178787_e(arrayOfVec3d[b2][2]);
        Block block2 = this.field_70170_p.func_180495_p(new BlockPos(vec3d2.field_72450_a, vec3d2.field_72448_b, vec3d2.field_72449_c)).func_177230_c();
        try {
          if (block1 == Blocks.field_150350_a)
            try {
              if (block2 == Blocks.field_150324_C)
                if (b == -1) {
                  b = b2;
                } else {
                  double d1 = func_180425_c().func_177954_c((vec3d.func_178787_e(arrayOfVec3d[b][0])).field_72450_a, (vec3d.func_178787_e(arrayOfVec3d[b][0])).field_72448_b, (vec3d.func_178787_e(arrayOfVec3d[b][0])).field_72449_c);
                  double d2 = func_180425_c().func_177954_c((vec3d.func_178787_e(arrayOfVec3d[b2][0])).field_72450_a, (vec3d.func_178787_e(arrayOfVec3d[b2][0])).field_72448_b, (vec3d.func_178787_e(arrayOfVec3d[b2][0])).field_72449_c);
                  if (d2 < d1)
                    b = b2; 
                }  
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            }  
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
      if (b != -1) {
        Vec3d vec3d1 = vec3d.func_178787_e(arrayOfVec3d[b][0]);
        return new Object[] { vec3d1, Integer.valueOf(arrayOfInt[b]) };
      } 
    } 
  }
  
  void s() {
    try {
      if (func_70660_b(o.b) == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, 10.0D);
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    func_184596_c(o.b);
    d(entityPlayer.getPersistentID());
    float f = (float)(Math.atan2(this.field_70161_v - entityPlayer.field_70161_v, this.field_70165_t - entityPlayer.field_70165_t) * 57.29577951308232D);
    a(f);
    a(func_174791_d());
    this.m.func_187227_b(c, Boolean.valueOf(true));
    b(b1.DASH);
    this.U = 16;
    func_189654_d(true);
    this.field_70145_X = true;
    bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer);
    this.field_70714_bg.func_85156_a((EntityAIBase)this.k);
    this.field_70714_bg.func_85156_a((EntityAIBase)this.w);
  }
  
  void q() {
    try {
      if (--this.U != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    UUID uUID = B();
    try {
      if (uUID == null) {
        m();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = this.field_70170_p.func_152378_a(uUID);
    try {
      if (entityPlayer == null) {
        m();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    entityPlayer.func_189654_d(true);
    entityPlayer.field_70145_X = true;
    Vec3d vec3d1 = bZ.a(new Vec3d(0.0D, 0.0D, -0.5D), entityPlayer.field_70177_z);
    Vec3d vec3d2 = vec3d1.func_178787_e(entityPlayer.func_174791_d());
    a(vec3d2);
    a(entityPlayer.field_70177_z);
    b(b1.HUG);
    this.aa = 150;
  }
  
  void m() {
    this.m.func_187227_b(c, Boolean.valueOf(false));
    b(b1.NULL);
    d((UUID)null);
    this.field_70145_X = false;
    func_189654_d(false);
    this.an = false;
    this.aa = -1;
    this.U = -1;
    this.af = -1;
    this.Z = null;
  }
  
  protected boolean func_184645_a(EntityPlayer paramEntityPlayer, EnumHand paramEnumHand) {
    try {
      if (b(paramEntityPlayer))
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (B() != null)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.field_70170_p.field_72995_K)
        a(paramEntityPlayer, false); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return true;
  }
  
  protected b1 c(b1 paramb1) {
    try {
      if (paramb1 != b1.COWGIRLFAST) {
        try {
          if (paramb1 == b1.COWGIRLSLOW)
            return b1.COWGIRLCUM; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        return b1.COWGIRLCUM;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.MISSIONARY_FAST) {
        try {
          if (paramb1 == b1.MISSIONARY_SLOW)
            return b1.MISSIONARY_CUM; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        return b1.MISSIONARY_CUM;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.CARRY_SLOW)
        try {
          return (paramb1 != b1.CARRY_FAST) ? null : b1.CARRY_CUM;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b1.CARRY_CUM;
  }
  
  protected b1 a(b1 paramb1) {
    try {
      if (paramb1 == b1.COWGIRLSLOW)
        return b1.COWGIRLFAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.MISSIONARY_SLOW)
        return b1.MISSIONARY_FAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.CARRY_SLOW)
        return b1.CARRY_FAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return null;
  }
  
  protected <E extends software.bernie.geckolib3.core.IAnimatable> PlayState a(AnimationEvent<E> paramAnimationEvent) {
    try {
      if (this.field_70170_p instanceof com.c)
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    String str = paramAnimationEvent.getController().getName();
    byte b = -1;
    try {
      switch (str.hashCode()) {
        case 3128418:
          if (str.equals("eyes"))
            b = 0; 
          break;
        case -103677777:
          if (str.equals("movement"))
            b = 1; 
          break;
        case -1422950858:
          if (str.equals("action"))
            b = 2; 
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      double d;
      switch (b) {
        case 0:
          try {
            if (h() == b1.NULL) {
              try {
                if ((h()).autoBlink) {
                  a("animation.ellie.eyes", true, paramAnimationEvent);
                  break;
                } 
                a("animation.ellie.null", true, paramAnimationEvent);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.ellie.null", true, paramAnimationEvent);
        case 1:
          try {
            if (h() != b1.NULL) {
              a("animation.ellie.null", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          d = Math.abs(this.field_70169_q - this.field_70165_t) + Math.abs(this.field_70166_s - this.field_70161_v);
          try {
            if (d == 0.0D) {
              try {
              
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a(v() ? "animation.ellie.crouchidle" : "animation.ellie.idle", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (v()) {
              a("animation.ellie.crouchwalk", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            switch (a.a[o().ordinal()]) {
              case 1:
                a("animation.ellie.run", true, paramAnimationEvent);
                break;
              case 2:
                a("animation.ellie.fastwalk", true, paramAnimationEvent);
                break;
              case 3:
                a("animation.ellie.walk", true, paramAnimationEvent);
                break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          break;
        case 2:
          try {
            switch (a.b[h().ordinal()]) {
              case 1:
                a("animation.ellie.null", true, paramAnimationEvent);
                break;
              case 2:
                a("animation.ellie.strip", false, paramAnimationEvent);
                break;
              case 3:
                a("animation.ellie.dash", false, paramAnimationEvent);
                break;
              case 4:
                a("animation.ellie.hug", false, paramAnimationEvent);
                break;
              case 5:
                a("animation.ellie.hugidle", true, paramAnimationEvent);
                break;
              case 6:
                a("animation.ellie.hugselected", false, paramAnimationEvent);
                break;
              case 7:
                a("animation.ellie.sitdown", false, paramAnimationEvent);
                break;
              case 8:
                a("animation.ellie.sitdownidle", true, paramAnimationEvent);
                break;
              case 9:
                a("animation.ellie.cowgirlstart", false, paramAnimationEvent);
                break;
              case 10:
                a("animation.ellie.cowgirlslow2", true, paramAnimationEvent);
                break;
              case 11:
                a("animation.ellie.cowgirlfast", true, paramAnimationEvent);
                break;
              case 12:
                a("animation.ellie.cowgirlcum", true, paramAnimationEvent);
                break;
              case 13:
                a("animation.ellie.attack" + this.Q, false, paramAnimationEvent);
                break;
              case 14:
                a("animation.ellie.bowcharge", false, paramAnimationEvent);
                break;
              case 15:
                a("animation.ellie.ride", true, paramAnimationEvent);
                break;
              case 16:
                a("animation.ellie.sit", true, paramAnimationEvent);
                break;
              case 17:
                a("animation.ellie.throwpearl", false, paramAnimationEvent);
                break;
              case 18:
                a("animation.ellie.downed", true, paramAnimationEvent);
                break;
              case 19:
                a("animation.ellie.missionary_start", false, paramAnimationEvent);
                break;
              case 20:
                a("animation.ellie.missionary_slow", true, paramAnimationEvent);
                break;
              case 21:
                a("animation.ellie.missionary_fast", true, paramAnimationEvent);
                break;
              case 22:
                a("animation.ellie.missionary_cum", false, paramAnimationEvent);
                break;
              case 23:
                a("animation.ellie.carry_intro", false, paramAnimationEvent);
                break;
              case 24:
                a("animation.ellie.carry_slow" + this.ae, true, paramAnimationEvent);
                break;
              case 25:
                a("animation.ellie.carry_fast", true, paramAnimationEvent);
                break;
              case 26:
                a("animation.ellie.carry_cum", true, paramAnimationEvent);
                break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return PlayState.CONTINUE;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerControllers(AnimationData paramAnimationData) {
    try {
      super.registerControllers(paramAnimationData);
      if (this.d == null)
        j(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    AnimationController.ISoundListener iSoundListener = paramSoundKeyframeEvent -> {
        // Byte code:
        //   0: aload_1
        //   1: getfield sound : Ljava/lang/String;
        //   4: astore_2
        //   5: iconst_m1
        //   6: istore_3
        //   7: aload_2
        //   8: invokevirtual hashCode : ()I
        //   11: lookupswitch default -> 1076, -1961942550 -> 824, -1919624234 -> 779, -1919384284 -> 644, -1919384283 -> 659, -1919384282 -> 674, -1919384281 -> 689, -1919384280 -> 704, -1919384279 -> 734, -1729477776 -> 524, -1551050723 -> 584, -1550810774 -> 539, -1550810773 -> 554, -1550810772 -> 569, -1361514553 -> 1049, -676816985 -> 809, -558244113 -> 388, -300807046 -> 764, -300567096 -> 959, -300567095 -> 719, -188461382 -> 409, -157000319 -> 914, -156760369 -> 884, -91455426 -> 929, 98875 -> 1019, 106540102 -> 839, 106857100 -> 1034, 379621455 -> 629, 403702091 -> 749, 557828409 -> 794, 690895010 -> 1064, 866127672 -> 974, 866127673 -> 989, 1199274405 -> 614, 1199514355 -> 599, 1235311621 -> 494, 1235311622 -> 509, 1257971612 -> 479, 1258211563 -> 423, 1258211564 -> 437, 1258211565 -> 451, 1258211566 -> 465, 1259653724 -> 1004, 1459849139 -> 944, 1534823792 -> 854, 1645105121 -> 899, 2085797364 -> 869
        //   388: aload_2
        //   389: ldc 'becomeNude'
        //   391: invokevirtual equals : (Ljava/lang/Object;)Z
        //   394: ifeq -> 1076
        //   397: goto -> 404
        //   400: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   403: athrow
        //   404: iconst_0
        //   405: istore_3
        //   406: goto -> 1076
        //   409: aload_2
        //   410: ldc 'stripDone'
        //   412: invokevirtual equals : (Ljava/lang/Object;)Z
        //   415: ifeq -> 1076
        //   418: iconst_1
        //   419: istore_3
        //   420: goto -> 1076
        //   423: aload_2
        //   424: ldc 'hugMSG2'
        //   426: invokevirtual equals : (Ljava/lang/Object;)Z
        //   429: ifeq -> 1076
        //   432: iconst_2
        //   433: istore_3
        //   434: goto -> 1076
        //   437: aload_2
        //   438: ldc 'hugMSG3'
        //   440: invokevirtual equals : (Ljava/lang/Object;)Z
        //   443: ifeq -> 1076
        //   446: iconst_3
        //   447: istore_3
        //   448: goto -> 1076
        //   451: aload_2
        //   452: ldc 'hugMSG4'
        //   454: invokevirtual equals : (Ljava/lang/Object;)Z
        //   457: ifeq -> 1076
        //   460: iconst_4
        //   461: istore_3
        //   462: goto -> 1076
        //   465: aload_2
        //   466: ldc 'hugMSG5'
        //   468: invokevirtual equals : (Ljava/lang/Object;)Z
        //   471: ifeq -> 1076
        //   474: iconst_5
        //   475: istore_3
        //   476: goto -> 1076
        //   479: aload_2
        //   480: ldc 'hugDone'
        //   482: invokevirtual equals : (Ljava/lang/Object;)Z
        //   485: ifeq -> 1076
        //   488: bipush #6
        //   490: istore_3
        //   491: goto -> 1076
        //   494: aload_2
        //   495: ldc 'hugselectedMSG1'
        //   497: invokevirtual equals : (Ljava/lang/Object;)Z
        //   500: ifeq -> 1076
        //   503: bipush #7
        //   505: istore_3
        //   506: goto -> 1076
        //   509: aload_2
        //   510: ldc 'hugselectedMSG2'
        //   512: invokevirtual equals : (Ljava/lang/Object;)Z
        //   515: ifeq -> 1076
        //   518: bipush #8
        //   520: istore_3
        //   521: goto -> 1076
        //   524: aload_2
        //   525: ldc 'sitdownMSG1'
        //   527: invokevirtual equals : (Ljava/lang/Object;)Z
        //   530: ifeq -> 1076
        //   533: bipush #9
        //   535: istore_3
        //   536: goto -> 1076
        //   539: aload_2
        //   540: ldc 'cowgirlStartMSG0'
        //   542: invokevirtual equals : (Ljava/lang/Object;)Z
        //   545: ifeq -> 1076
        //   548: bipush #10
        //   550: istore_3
        //   551: goto -> 1076
        //   554: aload_2
        //   555: ldc 'cowgirlStartMSG1'
        //   557: invokevirtual equals : (Ljava/lang/Object;)Z
        //   560: ifeq -> 1076
        //   563: bipush #11
        //   565: istore_3
        //   566: goto -> 1076
        //   569: aload_2
        //   570: ldc 'cowgirlStartMSG2'
        //   572: invokevirtual equals : (Ljava/lang/Object;)Z
        //   575: ifeq -> 1076
        //   578: bipush #12
        //   580: istore_3
        //   581: goto -> 1076
        //   584: aload_2
        //   585: ldc 'cowgirlStartDone'
        //   587: invokevirtual equals : (Ljava/lang/Object;)Z
        //   590: ifeq -> 1076
        //   593: bipush #13
        //   595: istore_3
        //   596: goto -> 1076
        //   599: aload_2
        //   600: ldc 'cowgirlfastMSG1'
        //   602: invokevirtual equals : (Ljava/lang/Object;)Z
        //   605: ifeq -> 1076
        //   608: bipush #14
        //   610: istore_3
        //   611: goto -> 1076
        //   614: aload_2
        //   615: ldc 'cowgirlfastDone'
        //   617: invokevirtual equals : (Ljava/lang/Object;)Z
        //   620: ifeq -> 1076
        //   623: bipush #15
        //   625: istore_3
        //   626: goto -> 1076
        //   629: aload_2
        //   630: ldc 'cowgirlfastdomMSG1'
        //   632: invokevirtual equals : (Ljava/lang/Object;)Z
        //   635: ifeq -> 1076
        //   638: bipush #16
        //   640: istore_3
        //   641: goto -> 1076
        //   644: aload_2
        //   645: ldc 'cowgirlcumMSG1'
        //   647: invokevirtual equals : (Ljava/lang/Object;)Z
        //   650: ifeq -> 1076
        //   653: bipush #17
        //   655: istore_3
        //   656: goto -> 1076
        //   659: aload_2
        //   660: ldc 'cowgirlcumMSG2'
        //   662: invokevirtual equals : (Ljava/lang/Object;)Z
        //   665: ifeq -> 1076
        //   668: bipush #18
        //   670: istore_3
        //   671: goto -> 1076
        //   674: aload_2
        //   675: ldc 'cowgirlcumMSG3'
        //   677: invokevirtual equals : (Ljava/lang/Object;)Z
        //   680: ifeq -> 1076
        //   683: bipush #19
        //   685: istore_3
        //   686: goto -> 1076
        //   689: aload_2
        //   690: ldc 'cowgirlcumMSG4'
        //   692: invokevirtual equals : (Ljava/lang/Object;)Z
        //   695: ifeq -> 1076
        //   698: bipush #20
        //   700: istore_3
        //   701: goto -> 1076
        //   704: aload_2
        //   705: ldc 'cowgirlcumMSG5'
        //   707: invokevirtual equals : (Ljava/lang/Object;)Z
        //   710: ifeq -> 1076
        //   713: bipush #21
        //   715: istore_3
        //   716: goto -> 1076
        //   719: aload_2
        //   720: ldc 'missionary_cumMSG2'
        //   722: invokevirtual equals : (Ljava/lang/Object;)Z
        //   725: ifeq -> 1076
        //   728: bipush #22
        //   730: istore_3
        //   731: goto -> 1076
        //   734: aload_2
        //   735: ldc 'cowgirlcumMSG6'
        //   737: invokevirtual equals : (Ljava/lang/Object;)Z
        //   740: ifeq -> 1076
        //   743: bipush #23
        //   745: istore_3
        //   746: goto -> 1076
        //   749: aload_2
        //   750: ldc 'blackScreen'
        //   752: invokevirtual equals : (Ljava/lang/Object;)Z
        //   755: ifeq -> 1076
        //   758: bipush #24
        //   760: istore_3
        //   761: goto -> 1076
        //   764: aload_2
        //   765: ldc 'missionary_cumDone'
        //   767: invokevirtual equals : (Ljava/lang/Object;)Z
        //   770: ifeq -> 1076
        //   773: bipush #25
        //   775: istore_3
        //   776: goto -> 1076
        //   779: aload_2
        //   780: ldc 'cowgirlcumDone'
        //   782: invokevirtual equals : (Ljava/lang/Object;)Z
        //   785: ifeq -> 1076
        //   788: bipush #26
        //   790: istore_3
        //   791: goto -> 1076
        //   794: aload_2
        //   795: ldc 'carry_cumDone'
        //   797: invokevirtual equals : (Ljava/lang/Object;)Z
        //   800: ifeq -> 1076
        //   803: bipush #27
        //   805: istore_3
        //   806: goto -> 1076
        //   809: aload_2
        //   810: ldc 'attackSound'
        //   812: invokevirtual equals : (Ljava/lang/Object;)Z
        //   815: ifeq -> 1076
        //   818: bipush #28
        //   820: istore_3
        //   821: goto -> 1076
        //   824: aload_2
        //   825: ldc 'attackDone'
        //   827: invokevirtual equals : (Ljava/lang/Object;)Z
        //   830: ifeq -> 1076
        //   833: bipush #29
        //   835: istore_3
        //   836: goto -> 1076
        //   839: aload_2
        //   840: ldc 'pearl'
        //   842: invokevirtual equals : (Ljava/lang/Object;)Z
        //   845: ifeq -> 1076
        //   848: bipush #30
        //   850: istore_3
        //   851: goto -> 1076
        //   854: aload_2
        //   855: ldc 'openSexUi'
        //   857: invokevirtual equals : (Ljava/lang/Object;)Z
        //   860: ifeq -> 1076
        //   863: bipush #31
        //   865: istore_3
        //   866: goto -> 1076
        //   869: aload_2
        //   870: ldc 'missionary_slowMSG1'
        //   872: invokevirtual equals : (Ljava/lang/Object;)Z
        //   875: ifeq -> 1076
        //   878: bipush #32
        //   880: istore_3
        //   881: goto -> 1076
        //   884: aload_2
        //   885: ldc 'missionary_fastMSG1'
        //   887: invokevirtual equals : (Ljava/lang/Object;)Z
        //   890: ifeq -> 1076
        //   893: bipush #33
        //   895: istore_3
        //   896: goto -> 1076
        //   899: aload_2
        //   900: ldc 'missionary_startDone'
        //   902: invokevirtual equals : (Ljava/lang/Object;)Z
        //   905: ifeq -> 1076
        //   908: bipush #34
        //   910: istore_3
        //   911: goto -> 1076
        //   914: aload_2
        //   915: ldc 'missionary_fastDone'
        //   917: invokevirtual equals : (Ljava/lang/Object;)Z
        //   920: ifeq -> 1076
        //   923: bipush #35
        //   925: istore_3
        //   926: goto -> 1076
        //   929: aload_2
        //   930: ldc 'bedRustle'
        //   932: invokevirtual equals : (Ljava/lang/Object;)Z
        //   935: ifeq -> 1076
        //   938: bipush #36
        //   940: istore_3
        //   941: goto -> 1076
        //   944: aload_2
        //   945: ldc 'bedRustle1'
        //   947: invokevirtual equals : (Ljava/lang/Object;)Z
        //   950: ifeq -> 1076
        //   953: bipush #37
        //   955: istore_3
        //   956: goto -> 1076
        //   959: aload_2
        //   960: ldc 'missionary_cumMSG1'
        //   962: invokevirtual equals : (Ljava/lang/Object;)Z
        //   965: ifeq -> 1076
        //   968: bipush #38
        //   970: istore_3
        //   971: goto -> 1076
        //   974: aload_2
        //   975: ldc 'carry_introMSG1'
        //   977: invokevirtual equals : (Ljava/lang/Object;)Z
        //   980: ifeq -> 1076
        //   983: bipush #39
        //   985: istore_3
        //   986: goto -> 1076
        //   989: aload_2
        //   990: ldc 'carry_introMSG2'
        //   992: invokevirtual equals : (Ljava/lang/Object;)Z
        //   995: ifeq -> 1076
        //   998: bipush #40
        //   1000: istore_3
        //   1001: goto -> 1076
        //   1004: aload_2
        //   1005: ldc 'lipsound'
        //   1007: invokevirtual equals : (Ljava/lang/Object;)Z
        //   1010: ifeq -> 1076
        //   1013: bipush #41
        //   1015: istore_3
        //   1016: goto -> 1076
        //   1019: aload_2
        //   1020: ldc 'cum'
        //   1022: invokevirtual equals : (Ljava/lang/Object;)Z
        //   1025: ifeq -> 1076
        //   1028: bipush #42
        //   1030: istore_3
        //   1031: goto -> 1076
        //   1034: aload_2
        //   1035: ldc 'pound'
        //   1037: invokevirtual equals : (Ljava/lang/Object;)Z
        //   1040: ifeq -> 1076
        //   1043: bipush #43
        //   1045: istore_3
        //   1046: goto -> 1076
        //   1049: aload_2
        //   1050: ldc 'carry_slowDone'
        //   1052: invokevirtual equals : (Ljava/lang/Object;)Z
        //   1055: ifeq -> 1076
        //   1058: bipush #44
        //   1060: istore_3
        //   1061: goto -> 1076
        //   1064: aload_2
        //   1065: ldc 'carry_fastDone'
        //   1067: invokevirtual equals : (Ljava/lang/Object;)Z
        //   1070: ifeq -> 1076
        //   1073: bipush #45
        //   1075: istore_3
        //   1076: iload_3
        //   1077: tableswitch default -> 2538, 0 -> 1276, 1 -> 1337, 2 -> 1353, 3 -> 1373, 4 -> 1392, 5 -> 1417, 6 -> 1444, 7 -> 1470, 8 -> 1497, 9 -> 1539, 10 -> 1575, 11 -> 1589, 12 -> 1619, 13 -> 1663, 14 -> 1687, 15 -> 1750, 16 -> 1787, 17 -> 1819, 18 -> 1846, 19 -> 1872, 20 -> 1887, 21 -> 1904, 22 -> 1904, 23 -> 1940, 24 -> 1940, 25 -> 1957, 26 -> 1957, 27 -> 1957, 28 -> 1978, 29 -> 1988, 30 -> 2022, 31 -> 2042, 32 -> 2059, 33 -> 2147, 34 -> 2235, 35 -> 2262, 36 -> 2299, 37 -> 2321, 38 -> 2333, 39 -> 2348, 40 -> 2366, 41 -> 2386, 42 -> 2416, 43 -> 2438, 44 -> 2468, 45 -> 2504
        //   1276: aload_0
        //   1277: invokevirtual C : ()Z
        //   1280: ifeq -> 2538
        //   1283: goto -> 1290
        //   1286: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1289: athrow
        //   1290: aload_0
        //   1291: ldc 'currentModel'
        //   1293: aload_0
        //   1294: getfield m : Lnet/minecraft/network/datasync/EntityDataManager;
        //   1297: getstatic com/schnurritv/sexmod/ae.v : Lnet/minecraft/network/datasync/DataParameter;
        //   1300: invokevirtual func_187225_a : (Lnet/minecraft/network/datasync/DataParameter;)Ljava/lang/Object;
        //   1303: checkcast java/lang/Integer
        //   1306: invokevirtual intValue : ()I
        //   1309: iconst_1
        //   1310: if_icmpne -> 1329
        //   1313: goto -> 1320
        //   1316: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1319: athrow
        //   1320: ldc '0'
        //   1322: goto -> 1331
        //   1325: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1328: athrow
        //   1329: ldc '1'
        //   1331: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;)V
        //   1334: goto -> 2538
        //   1337: aload_0
        //   1338: aconst_null
        //   1339: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   1342: aload_0
        //   1343: invokevirtual D : ()V
        //   1346: aload_0
        //   1347: invokevirtual K : ()V
        //   1350: goto -> 2538
        //   1353: aload_0
        //   1354: ldc 'Hmm...'
        //   1356: invokevirtual b : (Ljava/lang/String;)V
        //   1359: aload_0
        //   1360: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_HMPH : [Lnet/minecraft/util/SoundEvent;
        //   1363: iconst_3
        //   1364: aaload
        //   1365: ldc 6.0
        //   1367: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1370: goto -> 2538
        //   1373: aload_0
        //   1374: ldc 'Hey!'
        //   1376: invokevirtual b : (Ljava/lang/String;)V
        //   1379: aload_0
        //   1380: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_HUH : [Lnet/minecraft/util/SoundEvent;
        //   1383: iconst_1
        //   1384: aaload
        //   1385: fconst_1
        //   1386: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1389: goto -> 2538
        //   1392: aload_0
        //   1393: ldc 'ellie.dialogue.mommyhorny'
        //   1395: iconst_0
        //   1396: anewarray java/lang/Object
        //   1399: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1402: invokevirtual b : (Ljava/lang/String;)V
        //   1405: aload_0
        //   1406: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_MOMMYHORNY : [Lnet/minecraft/util/SoundEvent;
        //   1409: ldc 0.5
        //   1411: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;F)V
        //   1414: goto -> 2538
        //   1417: aload_0
        //   1418: ldc 'ellie.dialogue.whattodo'
        //   1420: iconst_0
        //   1421: anewarray java/lang/Object
        //   1424: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1427: invokevirtual b : (Ljava/lang/String;)V
        //   1430: aload_0
        //   1431: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_HUH : [Lnet/minecraft/util/SoundEvent;
        //   1434: iconst_1
        //   1435: aaload
        //   1436: ldc 6.0
        //   1438: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1441: goto -> 2538
        //   1444: aload_0
        //   1445: invokevirtual L : ()Z
        //   1448: ifeq -> 2538
        //   1451: aload_0
        //   1452: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
        //   1455: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
        //   1458: iconst_1
        //   1459: invokevirtual a : (Lnet/minecraft/entity/player/EntityPlayer;Z)Z
        //   1462: pop
        //   1463: goto -> 2538
        //   1466: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1469: athrow
        //   1470: aload_0
        //   1471: ldc 'ellie.dialogue.iknow'
        //   1473: iconst_0
        //   1474: anewarray java/lang/Object
        //   1477: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1480: invokevirtual b : (Ljava/lang/String;)V
        //   1483: aload_0
        //   1484: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_HMPH : [Lnet/minecraft/util/SoundEvent;
        //   1487: iconst_3
        //   1488: aaload
        //   1489: ldc 6.0
        //   1491: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1494: goto -> 2538
        //   1497: aload_0
        //   1498: ldc 'ellie.dialogue.followmedarling'
        //   1500: iconst_0
        //   1501: anewarray java/lang/Object
        //   1504: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1507: invokevirtual b : (Ljava/lang/String;)V
        //   1510: aload_0
        //   1511: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_GIGGLE : [Lnet/minecraft/util/SoundEvent;
        //   1514: iconst_3
        //   1515: aaload
        //   1516: ldc 6.0
        //   1518: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1521: aload_0
        //   1522: invokevirtual L : ()Z
        //   1525: ifeq -> 2538
        //   1528: iconst_1
        //   1529: invokestatic a : (Z)V
        //   1532: goto -> 2538
        //   1535: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1538: athrow
        //   1539: aload_0
        //   1540: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_COMETOMOMMY : [Lnet/minecraft/util/SoundEvent;
        //   1543: ldc 0.5
        //   1545: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;F)V
        //   1548: aload_0
        //   1549: invokevirtual C : ()Z
        //   1552: ifeq -> 2538
        //   1555: aload_0
        //   1556: ldc 'ellie.dialogue.cometomommy'
        //   1558: iconst_0
        //   1559: anewarray java/lang/Object
        //   1562: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1565: invokevirtual b : (Ljava/lang/String;)V
        //   1568: goto -> 2538
        //   1571: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1574: athrow
        //   1575: aload_0
        //   1576: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_GIGGLE : [Lnet/minecraft/util/SoundEvent;
        //   1579: iconst_4
        //   1580: aaload
        //   1581: ldc 6.0
        //   1583: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1586: goto -> 2538
        //   1589: aload_0
        //   1590: invokevirtual C : ()Z
        //   1593: ifeq -> 2538
        //   1596: aload_0
        //   1597: ldc 'ellie.dialogue.like'
        //   1599: iconst_0
        //   1600: anewarray java/lang/Object
        //   1603: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1606: invokevirtual e : (Ljava/lang/String;)V
        //   1609: invokestatic b : ()V
        //   1612: goto -> 2538
        //   1615: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1618: athrow
        //   1619: aload_0
        //   1620: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   1623: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1626: ldc 6.0
        //   1628: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1631: aload_0
        //   1632: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1635: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1638: ldc 0.75
        //   1640: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1643: aload_0
        //   1644: invokevirtual L : ()Z
        //   1647: ifeq -> 2538
        //   1650: ldc2_w 0.02
        //   1653: invokestatic a : (D)V
        //   1656: goto -> 2538
        //   1659: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1662: athrow
        //   1663: aload_0
        //   1664: invokevirtual L : ()Z
        //   1667: ifeq -> 2538
        //   1670: aload_0
        //   1671: getstatic com/schnurritv/sexmod/b1.COWGIRLSLOW : Lcom/schnurritv/sexmod/b1;
        //   1674: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   1677: invokestatic a : ()V
        //   1680: goto -> 2538
        //   1683: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1686: athrow
        //   1687: aload_0
        //   1688: getfield aj : Z
        //   1691: ifeq -> 1706
        //   1694: aload_0
        //   1695: iconst_0
        //   1696: putfield aj : Z
        //   1699: goto -> 1718
        //   1702: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1705: athrow
        //   1706: aload_0
        //   1707: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   1710: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1713: ldc 6.0
        //   1715: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1718: aload_0
        //   1719: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1722: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1725: ldc 0.75
        //   1727: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1730: aload_0
        //   1731: invokevirtual L : ()Z
        //   1734: ifeq -> 2538
        //   1737: ldc2_w 0.04
        //   1740: invokestatic a : (D)V
        //   1743: goto -> 2538
        //   1746: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1749: athrow
        //   1750: aload_0
        //   1751: invokevirtual L : ()Z
        //   1754: ifne -> 1764
        //   1757: goto -> 2538
        //   1760: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1763: athrow
        //   1764: getstatic com/schnurritv/sexmod/bf.b : Z
        //   1767: ifeq -> 1777
        //   1770: goto -> 2538
        //   1773: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1776: athrow
        //   1777: aload_0
        //   1778: getstatic com/schnurritv/sexmod/b1.COWGIRLSLOW : Lcom/schnurritv/sexmod/b1;
        //   1781: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   1784: goto -> 2538
        //   1787: aload_0
        //   1788: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1791: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1794: ldc 0.75
        //   1796: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1799: aload_0
        //   1800: invokevirtual L : ()Z
        //   1803: ifeq -> 2538
        //   1806: ldc2_w 0.2
        //   1809: invokestatic a : (D)V
        //   1812: goto -> 2538
        //   1815: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1818: athrow
        //   1819: aload_0
        //   1820: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   1823: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1826: ldc 6.0
        //   1828: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1831: aload_0
        //   1832: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1835: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1838: ldc 0.75
        //   1840: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1843: goto -> 2538
        //   1846: aload_0
        //   1847: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_MOAN : [Lnet/minecraft/util/SoundEvent;
        //   1850: iconst_5
        //   1851: aaload
        //   1852: ldc 3.0
        //   1854: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1857: aload_0
        //   1858: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1861: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1864: ldc 0.75
        //   1866: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1869: goto -> 2538
        //   1872: aload_0
        //   1873: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   1876: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   1879: ldc 0.75
        //   1881: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   1884: goto -> 2538
        //   1887: aload_0
        //   1888: invokevirtual L : ()Z
        //   1891: ifeq -> 2538
        //   1894: invokestatic d : ()V
        //   1897: goto -> 2538
        //   1900: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1903: athrow
        //   1904: aload_0
        //   1905: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_GOODBOY : [Lnet/minecraft/util/SoundEvent;
        //   1908: ldc 0.5
        //   1910: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;F)V
        //   1913: aload_0
        //   1914: invokevirtual L : ()Z
        //   1917: ifeq -> 2538
        //   1920: aload_0
        //   1921: ldc 'ellie.dialogue.goodboy'
        //   1923: iconst_0
        //   1924: anewarray java/lang/Object
        //   1927: invokestatic func_135052_a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1930: invokevirtual e : (Ljava/lang/String;)V
        //   1933: goto -> 2538
        //   1936: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1939: athrow
        //   1940: aload_0
        //   1941: invokevirtual L : ()Z
        //   1944: ifeq -> 2538
        //   1947: invokestatic a : ()V
        //   1950: goto -> 2538
        //   1953: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1956: athrow
        //   1957: aload_0
        //   1958: invokevirtual L : ()Z
        //   1961: ifeq -> 2538
        //   1964: invokestatic b : ()V
        //   1967: aload_0
        //   1968: invokevirtual D : ()V
        //   1971: goto -> 2538
        //   1974: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   1977: athrow
        //   1978: aload_0
        //   1979: getstatic net/minecraft/init/SoundEvents.field_187727_dV : Lnet/minecraft/util/SoundEvent;
        //   1982: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   1985: goto -> 2538
        //   1988: aload_0
        //   1989: getstatic com/schnurritv/sexmod/b1.NULL : Lcom/schnurritv/sexmod/b1;
        //   1992: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   1995: aload_0
        //   1996: dup
        //   1997: getfield Q : I
        //   2000: iconst_1
        //   2001: iadd
        //   2002: dup_x1
        //   2003: putfield Q : I
        //   2006: iconst_3
        //   2007: if_icmpne -> 2538
        //   2010: aload_0
        //   2011: iconst_0
        //   2012: putfield Q : I
        //   2015: goto -> 2538
        //   2018: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2021: athrow
        //   2022: getstatic com/schnurritv/sexmod/bn.a : Lnet/minecraftforge/fml/common/network/simpleimpl/SimpleNetworkWrapper;
        //   2025: new com/schnurritv/sexmod/l
        //   2028: dup
        //   2029: aload_0
        //   2030: invokevirtual p : ()Ljava/util/UUID;
        //   2033: invokespecial <init> : (Ljava/util/UUID;)V
        //   2036: invokevirtual sendToServer : (Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;)V
        //   2039: goto -> 2538
        //   2042: aload_0
        //   2043: invokevirtual C : ()Z
        //   2046: ifeq -> 2538
        //   2049: invokestatic a : ()V
        //   2052: goto -> 2538
        //   2055: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2058: athrow
        //   2059: aload_0
        //   2060: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   2063: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2066: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   2069: aload_0
        //   2070: invokevirtual func_70681_au : ()Ljava/util/Random;
        //   2073: invokevirtual nextBoolean : ()Z
        //   2076: ifeq -> 2115
        //   2079: aload_0
        //   2080: invokevirtual func_70681_au : ()Ljava/util/Random;
        //   2083: invokevirtual nextBoolean : ()Z
        //   2086: ifeq -> 2115
        //   2089: goto -> 2096
        //   2092: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2095: athrow
        //   2096: aload_0
        //   2097: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_MOAN : [Lnet/minecraft/util/SoundEvent;
        //   2100: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2103: ldc 6.0
        //   2105: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2108: goto -> 2127
        //   2111: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2114: athrow
        //   2115: aload_0
        //   2116: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   2119: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2122: ldc 6.0
        //   2124: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2127: aload_0
        //   2128: invokevirtual L : ()Z
        //   2131: ifeq -> 2538
        //   2134: ldc2_w 0.02
        //   2137: invokestatic a : (D)V
        //   2140: goto -> 2538
        //   2143: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2146: athrow
        //   2147: aload_0
        //   2148: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   2151: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2154: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   2157: aload_0
        //   2158: invokevirtual func_70681_au : ()Ljava/util/Random;
        //   2161: invokevirtual nextBoolean : ()Z
        //   2164: ifne -> 2184
        //   2167: aload_0
        //   2168: invokevirtual func_70681_au : ()Ljava/util/Random;
        //   2171: invokevirtual nextBoolean : ()Z
        //   2174: ifeq -> 2203
        //   2177: goto -> 2184
        //   2180: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2183: athrow
        //   2184: aload_0
        //   2185: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_MOAN : [Lnet/minecraft/util/SoundEvent;
        //   2188: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2191: ldc 6.0
        //   2193: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2196: goto -> 2215
        //   2199: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2202: athrow
        //   2203: aload_0
        //   2204: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   2207: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2210: ldc 6.0
        //   2212: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2215: aload_0
        //   2216: invokevirtual L : ()Z
        //   2219: ifeq -> 2538
        //   2222: ldc2_w 0.05
        //   2225: invokestatic a : (D)V
        //   2228: goto -> 2538
        //   2231: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2234: athrow
        //   2235: aload_0
        //   2236: invokevirtual L : ()Z
        //   2239: ifne -> 2249
        //   2242: goto -> 2538
        //   2245: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2248: athrow
        //   2249: aload_0
        //   2250: getstatic com/schnurritv/sexmod/b1.MISSIONARY_SLOW : Lcom/schnurritv/sexmod/b1;
        //   2253: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   2256: invokestatic a : ()V
        //   2259: goto -> 2538
        //   2262: aload_0
        //   2263: invokevirtual L : ()Z
        //   2266: ifne -> 2276
        //   2269: goto -> 2538
        //   2272: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2275: athrow
        //   2276: getstatic com/schnurritv/sexmod/bf.b : Z
        //   2279: ifeq -> 2289
        //   2282: goto -> 2538
        //   2285: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2288: athrow
        //   2289: aload_0
        //   2290: getstatic com/schnurritv/sexmod/b1.MISSIONARY_SLOW : Lcom/schnurritv/sexmod/b1;
        //   2293: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   2296: goto -> 2538
        //   2299: aload_0
        //   2300: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   2303: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2306: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   2309: aload_0
        //   2310: getstatic com/schnurritv/sexmod/c.MISC_BEDRUSTLE : [Lnet/minecraft/util/SoundEvent;
        //   2313: iconst_0
        //   2314: aaload
        //   2315: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   2318: goto -> 2538
        //   2321: aload_0
        //   2322: getstatic com/schnurritv/sexmod/c.MISC_BEDRUSTLE : [Lnet/minecraft/util/SoundEvent;
        //   2325: iconst_1
        //   2326: aaload
        //   2327: invokevirtual a : (Lnet/minecraft/util/SoundEvent;)V
        //   2330: goto -> 2538
        //   2333: aload_0
        //   2334: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_AHH : [Lnet/minecraft/util/SoundEvent;
        //   2337: invokestatic a : ([Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/util/SoundEvent;
        //   2340: ldc 6.0
        //   2342: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2345: goto -> 2538
        //   2348: aload_0
        //   2349: ldc 'I'm hungry..'
        //   2351: invokevirtual e : (Ljava/lang/String;)V
        //   2354: aload_0
        //   2355: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_HMPH : [Lnet/minecraft/util/SoundEvent;
        //   2358: ldc 6.0
        //   2360: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;F)V
        //   2363: goto -> 2538
        //   2366: aload_0
        //   2367: ldc 'heh~'
        //   2369: invokevirtual e : (Ljava/lang/String;)V
        //   2372: aload_0
        //   2373: getstatic com/schnurritv/sexmod/c.GIRLS_ELLIE_GIGGLE : [Lnet/minecraft/util/SoundEvent;
        //   2376: iconst_3
        //   2377: aaload
        //   2378: ldc 6.0
        //   2380: invokevirtual a : (Lnet/minecraft/util/SoundEvent;F)V
        //   2383: goto -> 2538
        //   2386: aload_0
        //   2387: getstatic com/schnurritv/sexmod/c.GIRLS_ALLIE_LIPSOUND : [Lnet/minecraft/util/SoundEvent;
        //   2390: iconst_0
        //   2391: newarray int
        //   2393: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;[I)V
        //   2396: aload_0
        //   2397: invokevirtual L : ()Z
        //   2400: ifeq -> 2538
        //   2403: ldc2_w 0.02
        //   2406: invokestatic a : (D)V
        //   2409: goto -> 2538
        //   2412: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2415: athrow
        //   2416: aload_0
        //   2417: getstatic com/schnurritv/sexmod/c.MISC_INSERTS : [Lnet/minecraft/util/SoundEvent;
        //   2420: ldc 6.0
        //   2422: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;F)V
        //   2425: aload_0
        //   2426: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   2429: iconst_0
        //   2430: newarray int
        //   2432: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;[I)V
        //   2435: goto -> 2538
        //   2438: aload_0
        //   2439: getstatic com/schnurritv/sexmod/c.MISC_POUNDING : [Lnet/minecraft/util/SoundEvent;
        //   2442: iconst_0
        //   2443: newarray int
        //   2445: invokevirtual a : ([Lnet/minecraft/util/SoundEvent;[I)V
        //   2448: aload_0
        //   2449: invokevirtual L : ()Z
        //   2452: ifeq -> 2538
        //   2455: ldc2_w 0.04
        //   2458: invokestatic a : (D)V
        //   2461: goto -> 2538
        //   2464: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2467: athrow
        //   2468: aload_0
        //   2469: getfield ae : I
        //   2472: istore #4
        //   2474: aload_0
        //   2475: aload_0
        //   2476: invokevirtual func_70681_au : ()Ljava/util/Random;
        //   2479: iconst_4
        //   2480: invokevirtual nextInt : (I)I
        //   2483: iconst_1
        //   2484: iadd
        //   2485: putfield ae : I
        //   2488: aload_0
        //   2489: getfield ae : I
        //   2492: iload #4
        //   2494: if_icmpeq -> 2474
        //   2497: goto -> 2538
        //   2500: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2503: athrow
        //   2504: aload_0
        //   2505: invokevirtual L : ()Z
        //   2508: ifne -> 2518
        //   2511: goto -> 2538
        //   2514: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2517: athrow
        //   2518: getstatic com/schnurritv/sexmod/bf.b : Z
        //   2521: ifne -> 2538
        //   2524: aload_0
        //   2525: getstatic com/schnurritv/sexmod/b1.CARRY_SLOW : Lcom/schnurritv/sexmod/b1;
        //   2528: invokevirtual b : (Lcom/schnurritv/sexmod/b1;)V
        //   2531: goto -> 2538
        //   2534: invokestatic a : (Ljava/lang/NullPointerException;)Ljava/lang/NullPointerException;
        //   2537: athrow
        //   2538: return
        // Exception table:
        //   from	to	target	type
        //   7	397	400	java/lang/NullPointerException
        //   1076	1283	1286	java/lang/NullPointerException
        //   1276	1313	1316	java/lang/NullPointerException
        //   1290	1325	1325	java/lang/NullPointerException
        //   1444	1466	1466	java/lang/NullPointerException
        //   1497	1535	1535	java/lang/NullPointerException
        //   1539	1571	1571	java/lang/NullPointerException
        //   1589	1615	1615	java/lang/NullPointerException
        //   1619	1659	1659	java/lang/NullPointerException
        //   1663	1683	1683	java/lang/NullPointerException
        //   1687	1702	1702	java/lang/NullPointerException
        //   1718	1746	1746	java/lang/NullPointerException
        //   1750	1760	1760	java/lang/NullPointerException
        //   1764	1773	1773	java/lang/NullPointerException
        //   1787	1815	1815	java/lang/NullPointerException
        //   1887	1900	1900	java/lang/NullPointerException
        //   1904	1936	1936	java/lang/NullPointerException
        //   1940	1953	1953	java/lang/NullPointerException
        //   1957	1974	1974	java/lang/NullPointerException
        //   1988	2018	2018	java/lang/NullPointerException
        //   2042	2055	2055	java/lang/NullPointerException
        //   2059	2089	2092	java/lang/NullPointerException
        //   2079	2111	2111	java/lang/NullPointerException
        //   2127	2143	2143	java/lang/NullPointerException
        //   2147	2177	2180	java/lang/NullPointerException
        //   2167	2199	2199	java/lang/NullPointerException
        //   2215	2231	2231	java/lang/NullPointerException
        //   2235	2245	2245	java/lang/NullPointerException
        //   2262	2272	2272	java/lang/NullPointerException
        //   2276	2285	2285	java/lang/NullPointerException
        //   2386	2412	2412	java/lang/NullPointerException
        //   2438	2464	2464	java/lang/NullPointerException
        //   2474	2500	2500	java/lang/NullPointerException
        //   2504	2514	2514	java/lang/NullPointerException
        //   2518	2531	2534	java/lang/NullPointerException
      };
    this.d.registerSoundListener(iSoundListener);
    paramAnimationData.addAnimationController(this.d);
    paramAnimationData.addAnimationController(this.e);
    paramAnimationData.addAnimationController(this.b);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ae.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */