package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class a2 extends U {
  boolean aj = false;
  
  int ai = 0;
  
  protected a2(World paramWorld) {
    super(paramWorld);
  }
  
  public a2(World paramWorld, UUID paramUUID) {
    super(paramWorld, paramUUID);
  }
  
  public float z() {
    return 1.6F;
  }
  
  public float func_70047_e() {
    return 1.64F;
  }
  
  public boolean z() {
    return false;
  }
  
  public boolean o() {
    return true;
  }
  
  public b0 a(int paramInt) {
    return new cF();
  }
  
  public String b(int paramInt) {
    return "textures/entity/slime/hand.png";
  }
  
  public void b(String paramString, UUID paramUUID) {
    try {
      if ("action.names.blowjob".equals(paramString)) {
        a(0, b1.SUCKBLOWJOB);
        b(b1.SUCKBLOWJOB);
        c(paramUUID);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public boolean a(EntityPlayer paramEntityPlayer) {
    a(paramEntityPlayer, this, new String[] { "action.names.blowjob" }, false);
    return true;
  }
  
  public void b(b1 paramb1) {
    try {
      if (h() == b1.CUMBLOWJOB)
        try {
          if (paramb1 != b1.THRUSTBLOWJOB) {
            try {
              if (paramb1 == b1.SUCKBLOWJOB)
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
      if (h() == b1.DOGGYCUM)
        try {
          if (paramb1 != b1.DOGGYFAST) {
            try {
              if (paramb1 == b1.DOGGYSLOW)
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
    super.b(paramb1);
  }
  
  protected b1 a(b1 paramb1) {
    try {
      if (paramb1 == b1.SUCKBLOWJOB)
        return b1.THRUSTBLOWJOB; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.DOGGYSLOW)
        return b1.DOGGYFAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return null;
  }
  
  protected b1 c(b1 paramb1) {
    try {
      if (paramb1 != b1.SUCKBLOWJOB) {
        try {
          if (paramb1 == b1.THRUSTBLOWJOB)
            return b1.CUMBLOWJOB; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        return b1.CUMBLOWJOB;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.DOGGYSLOW)
        try {
          return (paramb1 != b1.DOGGYFAST) ? null : b1.DOGGYCUM;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b1.DOGGYCUM;
  }
  
  public void func_70619_bc() {
    try {
      super.func_70619_bc();
      if (h() != b1.WAITDOGGY)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer1 = c();
    try {
      if (entityPlayer1 == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (entityPlayer1.func_174791_d().func_72438_d(j()) > 1.0D)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer1);
    d(entityPlayer1.getPersistentID());
    entityPlayer1.field_70177_z = m().floatValue();
    this.r = m().floatValue();
    entityPlayer1.func_70107_b((j()).field_72450_a, (j()).field_72448_b, (j()).field_72449_c);
    entityPlayer1.func_191958_b(0.0F, 0.0F, 0.0F, 0.0F);
    a(0.0D, 0.0D, 0.4D, 0.0F, 60.0F);
    b(b1.DOGGYSTART);
    entityPlayer1.func_189654_d(true);
    entityPlayer1.field_70145_X = true;
    EntityPlayer entityPlayer2 = this.field_70170_p.func_152378_a(u());
    entityPlayer2.func_189654_d(true);
    entityPlayer1.field_70145_X = true;
    entityPlayer1.field_71075_bZ.field_75100_b = true;
    entityPlayer2.field_71075_bZ.field_75100_b = true;
  }
  
  protected <E extends software.bernie.geckolib3.core.IAnimatable> PlayState a(AnimationEvent<E> paramAnimationEvent) {
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
      switch (b) {
        case 0:
          try {
            if (h() != b1.NULL) {
              try {
                if ((h()).autoBlink) {
                  a("animation.slime.fhappy", true, paramAnimationEvent);
                  break;
                } 
                a("animation.slime.null", true, paramAnimationEvent);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.slime.null", true, paramAnimationEvent);
        case 1:
          try {
            if (h() != b1.NULL) {
              a("animation.slime.null", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (this.Y) {
              a("animation.slime.sit", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (this.e.getCurrentAnimation() != null)
              try {
                if ((this.e.getCurrentAnimation()).animationName.contains("fly"))
                  try {
                    if (this.ag) {
                      try {
                      
                      } catch (NullPointerException nullPointerException) {
                        throw a(null);
                      } 
                      this.aj = !this.aj;
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
            if (!this.ag) {
              try {
              
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a("animation.slime.fly" + (this.aj ? "2" : ""), true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (Math.abs(this.ad.x) + Math.abs(this.ad.y) > 0.0F) {
              try {
                if (this.U) {
                  a("animation.slime.run", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (this.ad.y >= -0.1F) {
                  a("animation.slime.walk", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a("animation.slime.backwards_walk", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.slime.idle", true, paramAnimationEvent);
          break;
        case 2:
          try {
            if (h() == b1.NULL) {
              a("animation.slime.null", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            switch (a.a[h().ordinal()]) {
              case 1:
                a("animation.slime.undress", false, paramAnimationEvent);
                break;
              case 2:
                a("animation.slime.dress", false, paramAnimationEvent);
                break;
              case 3:
                a("animation.slime.strip", false, paramAnimationEvent);
                break;
              case 4:
                a("animation.slime.blowjobsuck", true, paramAnimationEvent);
                break;
              case 5:
                a("animation.slime.blowjobthrust", true, paramAnimationEvent);
                break;
              case 6:
                a("animation.slime.blowjobcum", false, paramAnimationEvent);
                break;
              case 7:
                a("animation.slime.doggygoonbed", false, paramAnimationEvent);
                break;
              case 8:
                a("animation.slime.doggywait", true, paramAnimationEvent);
                break;
              case 9:
                a("animation.slime.doggystart", false, paramAnimationEvent);
                break;
              case 10:
                a("animation.slime.doggyslow", true, paramAnimationEvent);
                break;
              case 11:
                a("animation.slime.doggyfast", true, paramAnimationEvent);
                break;
              case 12:
                a("animation.slime.doggycum", false, paramAnimationEvent);
                break;
              case 13:
                a("animation.slime.attack" + this.Q, false, paramAnimationEvent);
                break;
              case 14:
                a("animation.slime.bowcharge", false, paramAnimationEvent);
                break;
              case 15:
                a("animation.slime.ride", true, paramAnimationEvent);
                break;
              case 16:
                a("animation.slime.sit", true, paramAnimationEvent);
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
  
  public void registerControllers(AnimationData paramAnimationData) {
    try {
      if (this.d == null)
        j(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    AnimationController.ISoundListener iSoundListener = paramSoundKeyframeEvent -> {
        String str1 = paramSoundKeyframeEvent.sound;
        String str2 = str1;
        byte b = -1;
        try {
          switch (str2.hashCode()) {
            case -1961942550:
              if (str2.equals("attackDone"))
                b = 0; 
              break;
            case -291196098:
              if (str2.equals("undress"))
                b = 1; 
              break;
            case 95849015:
              if (str2.equals("dress"))
                b = 2; 
              break;
            case 1988710681:
              if (str2.equals("sexUiOn"))
                b = 3; 
              break;
            case 1662545087:
              if (str2.equals("bjiMSG10"))
                b = 4; 
              break;
            case 1662545088:
              if (str2.equals("bjiMSG11"))
                b = 5; 
              break;
            case 1662545089:
              if (str2.equals("bjiMSG12"))
                b = 6; 
              break;
            case -74758116:
              if (str2.equals("bjtMSG1"))
                b = 7; 
              break;
            case -85156797:
              if (str2.equals("bjiDone"))
                b = 8; 
              break;
            case -74998066:
              if (str2.equals("bjtDone"))
                b = 9; 
              break;
            case 441346873:
              if (str2.equals("doggyfastReady"))
                b = 10; 
              break;
            case 1982646231:
              if (str2.equals("bjtReady"))
                b = 11; 
              break;
            case -90457973:
              if (str2.equals("bjcMSG1"))
                b = 12; 
              break;
            case -90457972:
              if (str2.equals("bjcMSG2"))
                b = 13; 
              break;
            case -2038339680:
              if (str2.equals("doggyslowMSG2"))
                b = 14; 
              break;
            case -1370194640:
              if (str2.equals("bjcBlackScreen"))
                b = 15; 
              break;
            case -90697923:
              if (str2.equals("bjcDone"))
                b = 16; 
              break;
            case 1092262223:
              if (str2.equals("doggyCumDone"))
                b = 17; 
              break;
            case 2106063356:
              if (str2.equals("doggyGoOnBedMSG1"))
                b = 18; 
              break;
            case 2105823406:
              if (str2.equals("doggyGoOnBedDone"))
                b = 19; 
              break;
            case -1648851740:
              if (str2.equals("doggystartMSG1"))
                b = 20; 
              break;
            case -1648851739:
              if (str2.equals("doggystartMSG2"))
                b = 21; 
              break;
            case -1648851738:
              if (str2.equals("doggystartMSG3"))
                b = 22; 
              break;
            case -1648851737:
              if (str2.equals("doggystartMSG4"))
                b = 23; 
              break;
            case -1648851736:
              if (str2.equals("doggystartMSG5"))
                b = 24; 
              break;
            case -1649091690:
              if (str2.equals("doggystartDone"))
                b = 25; 
              break;
            case -2038339681:
              if (str2.equals("doggyslowMSG1"))
                b = 26; 
              break;
            case 14069882:
              if (str2.equals("doggyfastMSG1"))
                b = 27; 
              break;
            case 13829932:
              if (str2.equals("doggyfastDone"))
                b = 28; 
              break;
            case -572151107:
              if (str2.equals("doggycumMSG1"))
                b = 29; 
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          int i;
          switch (b) {
            case 0:
              try {
                if (++this.Q == 3)
                  this.Q = 0; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 1:
              try {
                if (C()) {
                  this.m.func_187227_b(v, Integer.valueOf(0));
                  D();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 2:
              try {
                if (C()) {
                  this.m.func_187227_b(v, Integer.valueOf(1));
                  b((b1)null);
                  D();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 3:
              try {
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 4:
              try {
                if (L())
                  a(-0.4D, -0.8D, -0.2D, 60.0F, -3.0F); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 5:
              try {
                a(SoundEvents.field_187886_fs, 0.5F);
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 6:
              try {
                if (bY.b.nextInt(5) == 0)
                  a(SoundEvents.field_187882_fq, 0.5F); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                a(SoundEvents.field_187886_fs, 0.5F);
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 7:
              try {
                a(SoundEvents.field_187878_fo);
                a(SoundEvents.field_187874_fm);
                if (L())
                  aC.a(0.04D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 8:
              try {
                b(b1.SUCKBLOWJOB);
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 9:
              b(b1.SUCKBLOWJOB);
              break;
            case 10:
              try {
                if (!L())
                  break; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (bf.b)
                  A(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 11:
              try {
                if (L())
                  try {
                    if (bf.b)
                      A(); 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  }  
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 12:
              a(SoundEvents.field_187882_fq);
              break;
            case 13:
              try {
                a(SoundEvents.field_187882_fq);
                if (L())
                  aC.d(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 14:
              a(SoundEvents.field_187878_fo);
              break;
            case 15:
              try {
                if (L())
                  a5.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 16:
            case 17:
              try {
                if (L()) {
                  aC.b();
                  D();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 18:
              a(SoundEvents.field_187886_fs);
              this.r = this.field_70177_z;
              break;
            case 19:
              bn.a.sendToServer(new p(p(), (Minecraft.func_71410_x()).field_71439_g.getPersistentID()));
              b(b1.WAITDOGGY);
              break;
            case 20:
              a(c.MISC_TOUCH[0]);
              break;
            case 21:
              a(c.MISC_TOUCH[1]);
              break;
            case 22:
              a(SoundEvents.field_187886_fs, 0.25F);
              break;
            case 23:
              try {
                a(c.a(c.MISC_SMALLINSERTS), 1.5F);
                if (L())
                  aC.b(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 24:
              a(c.a(c.MISC_POUNDING), 0.33F);
              a(SoundEvents.field_187878_fo);
              break;
            case 25:
              try {
                b(b1.DOGGYSLOW);
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 26:
              a(c.a(c.MISC_POUNDING), 0.33F);
              i = bY.b.nextInt(4);
              if (i == 0) {
                i = bY.b.nextInt(2);
                try {
                  if (i == 0) {
                    a(SoundEvents.field_187882_fq);
                  } else {
                    a(SoundEvents.field_187886_fs);
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
              } else {
                a(SoundEvents.field_187878_fo);
              } 
              try {
                if (L())
                  aC.a(0.00666D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 27:
              try {
                a(c.a(c.MISC_POUNDING), 0.75F);
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              this.ai++;
              if (this.ai % 2 == 0) {
                int j = bY.b.nextInt(2);
                try {
                  if (j == 0) {
                    a(SoundEvents.field_187882_fq);
                    break;
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                a(SoundEvents.field_187886_fs);
                break;
              } 
              a(SoundEvents.field_187878_fo);
              break;
            case 28:
              b(b1.DOGGYSLOW);
              break;
            case 29:
              a(c.MISC_CUMINFLATION[0], 4.0F);
              a(c.a(c.MISC_POUNDING), 2.0F);
              a(SoundEvents.field_187874_fm);
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      };
    this.d.registerSoundListener(iSoundListener);
    paramAnimationData.addAnimationController(this.d);
    paramAnimationData.addAnimationController(this.b);
    paramAnimationData.addAnimationController(this.e);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */