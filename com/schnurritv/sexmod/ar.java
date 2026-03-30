package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ar extends U {
  int ak = 0;
  
  boolean aj = false;
  
  boolean ai = false;
  
  boolean al = false;
  
  protected ar(World paramWorld) {
    super(paramWorld);
  }
  
  public ar(World paramWorld, UUID paramUUID) {
    super(paramWorld, paramUUID);
  }
  
  public float z() {
    return 1.6F;
  }
  
  public float func_70047_e() {
    return 1.34F;
  }
  
  public b0 a(int paramInt) {
    return new a4();
  }
  
  public String b(int paramInt) {
    return "textures/entity/cat/hand.png";
  }
  
  public void b(String paramString, UUID paramUUID) {
    try {
      if ("action.names.touchboobs".equals(paramString)) {
        a(0, b1.TOUCH_BOOBS_INTRO);
        b(b1.TOUCH_BOOBS_INTRO);
        this.m.func_187227_b(v, Integer.valueOf(0));
        c(paramUUID);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("action.names.headpat".equals(paramString)) {
        b(b1.HEAD_PAT);
        c(paramUUID);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public void m() {
    b(b1.WAIT_CAT);
  }
  
  public boolean z() {
    return true;
  }
  
  public boolean a(EntityPlayer paramEntityPlayer) {
    a(paramEntityPlayer, this, new String[] { "action.names.touchboobs", "action.names.headpat" }, false);
    return true;
  }
  
  public void b(b1 paramb1) {
    try {
      if (h() == b1.COWGIRL_SITTING_CUM)
        try {
          if (paramb1 != b1.COWGIRL_SITTING_SLOW) {
            try {
              if (paramb1 == b1.COWGIRL_SITTING_FAST)
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
      if (h() == b1.TOUCH_BOOBS_CUM)
        try {
          if (paramb1 != b1.TOUCH_BOOBS_FAST) {
            try {
              if (paramb1 == b1.TOUCH_BOOBS_SLOW)
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
  
  public void func_70071_h_() {
    try {
      super.func_70071_h_();
      if (b1.WAIT_CAT.equals(h())) {
        o();
      } else {
        this.ak = 0;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void o() {
    EntityPlayer entityPlayer = c();
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (entityPlayer.func_70011_f(this.field_70165_t, (j()).field_72448_b, this.field_70161_v) > 1.25D)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (this.field_70170_p.field_72995_K) {
        a(entityPlayer, this.ak);
      } else {
        try {
          if (this.ak == 25) {
            d(entityPlayer.getPersistentID());
            entityPlayer.func_191958_b(0.0F, 0.0F, 0.0F, 0.0F);
            entityPlayer.func_70634_a((func_174791_d()).field_72450_a, (j()).field_72448_b, (func_174791_d()).field_72449_c);
            b(b1.COWGIRL_SITTING_INTRO);
            entityPlayer.func_70034_d(m().floatValue() + 180.0F);
            entityPlayer.field_70177_z = m().floatValue() + 180.0F;
            entityPlayer.field_70126_B = m().floatValue() + 180.0F;
            this.r = m().floatValue() + 180.0F;
            a(0.0D, -0.07500000298023224D, -0.7109375D, 0.0F, 0.0F);
            this.m.func_187227_b(v, Integer.valueOf(0));
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.ak++;
  }
  
  @SideOnly(Side.CLIENT)
  void a(EntityPlayer paramEntityPlayer, int paramInt) {
    if (paramInt == 0) {
      EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
      try {
        if (entityPlayerSP.getPersistentID().equals(paramEntityPlayer.getPersistentID())) {
          a5.a();
          entityPlayerSP.func_70016_h(0.0D, 0.0D, 0.0D);
          bf.a(false);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    if (paramInt == 25) {
      EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
      try {
        if (entityPlayerSP.getPersistentID().equals(paramEntityPlayer.getPersistentID()))
          (Minecraft.func_71410_x()).field_71474_y.field_74320_O = 2; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
  }
  
  protected b1 a(b1 paramb1) {
    try {
      if (paramb1 == b1.TOUCH_BOOBS_SLOW)
        return b1.TOUCH_BOOBS_FAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 == b1.COWGIRL_SITTING_SLOW)
        return b1.COWGIRL_SITTING_FAST; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return null;
  }
  
  protected b1 c(b1 paramb1) {
    try {
      if (paramb1 != b1.TOUCH_BOOBS_SLOW) {
        try {
          if (paramb1 == b1.TOUCH_BOOBS_FAST)
            return b1.TOUCH_BOOBS_CUM; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        return b1.TOUCH_BOOBS_CUM;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.COWGIRL_SITTING_FAST)
        try {
          return (paramb1 != b1.COWGIRL_SITTING_SLOW) ? null : b1.COWGIRL_SITTING_CUM;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b1.COWGIRL_SITTING_CUM;
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
            if (h() == b1.NULL) {
              try {
                if ((h()).autoBlink) {
                  a("animation.cat.blink", true, paramAnimationEvent);
                  break;
                } 
                a("animation.cat.null", true, paramAnimationEvent);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.cat.null", true, paramAnimationEvent);
        case 1:
          try {
            if (h() != b1.NULL) {
              a("animation.cat.null", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (this.Y) {
              a("animation.cat.sit", true, paramAnimationEvent);
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
              a("animation.cat.fly" + (this.aj ? "2" : ""), true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (Math.abs(this.ad.x) + Math.abs(this.ad.y) > 0.0F) {
              try {
                if (this.U) {
                  a("animation.cat.run", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (this.ad.y >= -0.1F) {
                  a("animation.cat.fastwalk", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a("animation.cat.backwards_walk", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.cat.idle", true, paramAnimationEvent);
          break;
        case 2:
          try {
            switch (a.a[h().ordinal()]) {
              case 1:
                a("animation.cat.null", true, paramAnimationEvent);
                break;
              case 2:
                a("animation.cat.attack" + this.Q, false, paramAnimationEvent);
                break;
              case 3:
              case 4:
                a("animation.cat.sit", true, paramAnimationEvent);
                break;
              case 5:
                a("animation.cat.bowcharge", false, paramAnimationEvent);
                break;
              case 6:
                a("animation.cat.throwpearl", true, paramAnimationEvent);
                break;
              case 7:
                a("animation.cat.downed", true, paramAnimationEvent);
                break;
              case 8:
                a("animation.cat.start_fishing", false, paramAnimationEvent);
                break;
              case 9:
                a("animation.cat.idle_fishing", true, paramAnimationEvent);
                break;
              case 10:
                a("animation.cat.eat_fishing", false, paramAnimationEvent);
                break;
              case 11:
                a("animation.cat.throw_away", false, paramAnimationEvent);
                break;
              case 12:
                a("animation.cat.payment", false, paramAnimationEvent);
                break;
              case 13:
                a("animation.cat.touch_boobs_intro", false, paramAnimationEvent);
                break;
              case 14:
                try {
                
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                a("animation.cat.touch_boobs_slow" + (this.ai ? "1" : ""), true, paramAnimationEvent);
                break;
              case 15:
                a("animation.cat.touch_boobs_fast", true, paramAnimationEvent);
                break;
              case 16:
                a("animation.cat.touch_boobs_cum", false, paramAnimationEvent);
                break;
              case 17:
                a("animation.cat.wait", false, paramAnimationEvent);
                break;
              case 18:
                a("animation.cat.sitting_intro", false, paramAnimationEvent);
                break;
              case 19:
                a("animation.cat.sitting_slow", true, paramAnimationEvent);
                break;
              case 20:
                a("animation.cat.sitting_fast", true, paramAnimationEvent);
                break;
              case 21:
                a("animation.cat.sitting_cum", true, paramAnimationEvent);
                break;
              case 22:
                a("animation.cat.head_pat", true, paramAnimationEvent);
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
        String str = paramSoundKeyframeEvent.sound;
        byte b = -1;
        try {
          switch (str.hashCode()) {
            case -676816985:
              if (str.equals("attackSound"))
                b = 0; 
              break;
            case -1961942550:
              if (str.equals("attackDone"))
                b = 1; 
              break;
            case 54906230:
              if (str.equals("idleDone"))
                b = 2; 
              break;
            case 1684190080:
              if (str.equals("idle2Done"))
                b = 3; 
              break;
            case 106540102:
              if (str.equals("pearl"))
                b = 4; 
              break;
            case -1540620298:
              if (str.equals("paymentMSG1"))
                b = 5; 
              break;
            case -1540620297:
              if (str.equals("paymentMSG2"))
                b = 6; 
              break;
            case -1540620296:
              if (str.equals("paymentMSG3"))
                b = 7; 
              break;
            case -1540620295:
              if (str.equals("paymentMSG4"))
                b = 8; 
              break;
            case -1540860248:
              if (str.equals("paymentDone"))
                b = 9; 
              break;
            case -1380923296:
              if (str.equals("breath"))
                b = 10; 
              break;
            case -176763432:
              if (str.equals("rod_breath"))
                b = 11; 
              break;
            case 695019737:
              if (str.equals("happyOh"))
                b = 12; 
              break;
            case 620933088:
              if (str.equals("cutenya3"))
                b = 13; 
              break;
            case 620933087:
              if (str.equals("cutenya2"))
                b = 14; 
              break;
            case 103675:
              if (str.equals("huh"))
                b = 15; 
              break;
            case 3206589:
              if (str.equals("hmph"))
                b = 16; 
              break;
            case 3198650:
              if (str.equals("hehe"))
                b = 17; 
              break;
            case -1246024133:
              if (str.equals("giggle"))
                b = 18; 
              break;
            case 2094529267:
              if (str.equals("singing"))
                b = 19; 
              break;
            case 816936963:
              if (str.equals("touch_boobsMSG1"))
                b = 20; 
              break;
            case 110550847:
              if (str.equals("touch"))
                b = 21; 
              break;
            case 3273774:
              if (str.equals("jump"))
                b = 22; 
              break;
            case -334109968:
              if (str.equals("horninya"))
                b = 23; 
              break;
            case -1767474366:
              if (str.equals("horninya2"))
                b = 24; 
              break;
            case -108443135:
              if (str.equals("touch_boobs_cumMSG3"))
                b = 25; 
              break;
            case 296663352:
              if (str.equals("sitting_cumMSG1"))
                b = 26; 
              break;
            case 3357007:
              if (str.equals("moan"))
                b = 27; 
              break;
            case 298467170:
              if (str.equals("touch_boobs_introDone"))
                b = 28; 
              break;
            case -548534449:
              if (str.equals("touch_boobs_slowDone"))
                b = 29; 
              break;
            case -1265327365:
              if (str.equals("addCumSlow"))
                b = 30; 
              break;
            case -1265725098:
              if (str.equals("addCumFast"))
                b = 31; 
              break;
            case 968155646:
              if (str.equals("fastDone"))
                b = 32; 
              break;
            case -146438396:
              if (str.equals("moanOrNya"))
                b = 33; 
              break;
            case 403702091:
              if (str.equals("blackScreen"))
                b = 34; 
              break;
            case -108683087:
              if (str.equals("touch_boobs_cumDone"))
                b = 35; 
              break;
            case 2023406731:
              if (str.equals("resetGirl"))
                b = 36; 
              break;
            case -108443137:
              if (str.equals("touch_boobs_cumMSG1"))
                b = 37; 
              break;
            case -108443136:
              if (str.equals("touch_boobs_cumMSG2"))
                b = 38; 
              break;
            case -253569070:
              if (str.equals("call_playerMSG1"))
                b = 39; 
              break;
            case 809204182:
              if (str.equals("pounding"))
                b = 40; 
              break;
            case -1235980887:
              if (str.equals("sitting_introMSG1"))
                b = 41; 
              break;
            case -1236220837:
              if (str.equals("sitting_introDone"))
                b = 42; 
              break;
            case -874895228:
              if (str.equals("sitting_slowMSG1"))
                b = 43; 
              break;
            case 1177514335:
              if (str.equals("sitting_fastMSG1"))
                b = 44; 
              break;
            case 1177274385:
              if (str.equals("sitting_fastDone"))
                b = 45; 
              break;
            case 1185581771:
              if (str.equals("sitting_fastTp"))
                b = 46; 
              break;
            case 1888271923:
              if (str.equals("headpatMSG1"))
                b = 47; 
              break;
            case 1888271924:
              if (str.equals("headpatMSG2"))
                b = 48; 
              break;
            case 1888271925:
              if (str.equals("headpatMSG3"))
                b = 49; 
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          int[] arrayOfInt;
          int i;
          switch (b) {
            case 0:
              a(SoundEvents.field_187727_dV);
              break;
            case 1:
              try {
                if (++this.Q == 3)
                  this.Q = 0; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 2:
              try {
              
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              this.al = (func_70681_au().nextInt(10) == 0);
              break;
            case 3:
              this.al = false;
              break;
            case 4:
              bn.a.sendToServer(new l(p()));
              break;
            case 5:
              a(B(), "Here, I know u like fish and yea.. these are for you");
              a(c.MISC_PLOB[0]);
              break;
            case 6:
              e("huh~?");
              a(c.GIRLS_LUNA_HUH);
              break;
            case 7:
              e("nyyyaaaa~ :D");
              arrayOfInt = new int[] { 1, 7, 10, 11 };
              i = arrayOfInt[func_70681_au().nextInt(arrayOfInt.length)];
              a(c.GIRLS_LUNA_CUTENYA[i]);
              break;
            case 8:
              e("tankuuuu owowowo");
              a(c.GIRLS_LUNA_OWO);
              break;
            case 9:
              try {
                if (C())
                  K(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              this.t = 1.0F;
              break;
            case 10:
            case 11:
              a(c.GIRLS_LUNA_LIGHTBREATHING);
              break;
            case 12:
              a(c.GIRLS_LUNA_HAPPYOH);
              break;
            case 13:
              a(c.GIRLS_LUNA_CUTENYA[3]);
              break;
            case 14:
              a(c.GIRLS_LUNA_CUTENYA[2]);
              break;
            case 15:
              a(c.GIRLS_LUNA_HUH);
              break;
            case 16:
              a(c.GIRLS_LUNA_HMPH);
              break;
            case 17:
            case 18:
              a(c.GIRLS_LUNA_GIGGLE);
              break;
            case 19:
              a(c.GIRLS_LUNA_SINGING);
              break;
            case 20:
              e("comon~ touch me hihi~");
              a(c.GIRLS_LUNA_GIGGLE);
              break;
            case 21:
              a(c.MISC_TOUCH);
              break;
            case 22:
              a(c.MISC_JUMP[0], 0.2F);
              break;
            case 23:
              a(c.GIRLS_LUNA_HORNINYA);
              break;
            case 24:
            case 25:
            case 26:
              a(c.GIRLS_LUNA_HORNINYA[1]);
              a(c.MISC_CUMINFLATION[0], 5.0F);
              break;
            case 27:
              a(c.a(c.GIRLS_LUNA_MOAN));
              break;
            case 28:
              try {
                b(b1.TOUCH_BOOBS_SLOW);
                if (L()) {
                  aC.b();
                  aC.a();
                  bf.a(false);
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 29:
              try {
                if (this.ai) {
                  this.ai = false;
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
              
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              this.ai = (Math.random() < 0.5D);
              break;
            case 30:
              try {
                if (L())
                  aC.a(0.019999999552965164D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 31:
              try {
                if (L())
                  aC.a(0.03999999910593033D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 32:
              try {
                if (!L())
                  break; 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (!bf.b)
                  b(b1.TOUCH_BOOBS_SLOW); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 33:
              try {
                if (Math.random() > 0.5D) {
                  a(c.a(c.GIRLS_LUNA_MOAN));
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a(c.a(c.GIRLS_LUNA_HORNINYA));
              break;
            case 34:
              try {
                if (L())
                  a5.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 35:
              try {
                if (L()) {
                  aC.b();
                  D();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 36:
              try {
                if (L())
                  D(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 37:
              a(c.GIRLS_LUNA_HORNINYA[3]);
              break;
            case 38:
              a(c.GIRLS_LUNA_HORNINYA[9]);
              break;
            case 39:
              a(c.GIRLS_LUNA_GIGGLE);
              e("come here - big guy hehe~");
              break;
            case 40:
              a(c.a(c.MISC_POUNDING));
              break;
            case 41:
              a(c.GIRLS_LUNA_GIGGLE);
              e("hehe~");
              break;
            case 42:
              try {
                if (L()) {
                  b(b1.COWGIRL_SITTING_SLOW);
                  aC.b();
                  aC.a();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 43:
              try {
                if (func_70681_au().nextBoolean()) {
                  try {
                    if (func_70681_au().nextBoolean()) {
                      a(c.a(c.GIRLS_LUNA_HORNINYA));
                      break;
                    } 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  } 
                  a(c.a(c.GIRLS_LUNA_MOAN));
                } else {
                  a(c.a(c.GIRLS_LUNA_LIGHTBREATHING));
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 44:
              try {
                if (func_70681_au().nextBoolean()) {
                  a(c.a(c.GIRLS_LUNA_HORNINYA));
                } else {
                  a(c.a(c.GIRLS_LUNA_MOAN));
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (L())
                  aC.a(0.04D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 45:
              try {
                if (L() && !bf.b) {
                  b(b1.COWGIRL_SITTING_SLOW);
                  Vec3d vec3d1 = new Vec3d(0.0D, -0.07500000298023224D, -0.7109375D);
                  Vec3d vec3d2 = bZ.a(vec3d1, m().floatValue() + 180.0F);
                  (Minecraft.func_71410_x()).field_71439_g.func_70107_b((x()).field_72450_a + vec3d2.field_72450_a, (x()).field_72448_b - 0.0D + vec3d2.field_72448_b, (x()).field_72449_c + vec3d2.field_72449_c);
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 46:
              if (L()) {
                Vec3d vec3d1 = new Vec3d(0.0D, -0.160625D, -0.9925D);
                Vec3d vec3d2 = bZ.a(vec3d1, m().floatValue() + 180.0F);
                (Minecraft.func_71410_x()).field_71439_g.func_70107_b((x()).field_72450_a + vec3d2.field_72450_a, (x()).field_72448_b - 0.0D + vec3d2.field_72448_b, (x()).field_72449_c + vec3d2.field_72449_c);
              } 
              break;
            case 47:
              e("huh?~");
              a(c.GIRLS_LUNA_HUH);
              break;
            case 48:
              a(c.GIRLS_LUNA_MMM);
              break;
            case 49:
              e("nya~");
              a(c.GIRLS_LUNA_HORNINYA[0]);
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      };
    this.e.transitionLengthTicks = 10.0D;
    this.d.registerSoundListener(iSoundListener);
    paramAnimationData.addAnimationController(this.d);
    paramAnimationData.addAnimationController(this.e);
    paramAnimationData.addAnimationController(this.b);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */