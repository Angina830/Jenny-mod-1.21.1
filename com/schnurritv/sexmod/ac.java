package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class ac extends U {
  boolean ai = false;
  
  int aj = 0;
  
  boolean ak = false;
  
  protected ac(World paramWorld) {
    super(paramWorld);
  }
  
  public ac(World paramWorld, UUID paramUUID) {
    super(paramWorld, paramUUID);
  }
  
  public float z() {
    return 1.75F;
  }
  
  public float func_70047_e() {
    return 1.64F;
  }
  
  public void m() {
    b(b1.STARTDOGGY);
    this.m.func_187227_b(v, Integer.valueOf(0));
    this.r = ((Float)this.m.func_187225_a(z)).floatValue();
  }
  
  public boolean o() {
    return true;
  }
  
  public b0 a(int paramInt) {
    return new bW();
  }
  
  public String b(int paramInt) {
    try {
      if (paramInt == 0)
        return "textures/entity/jenny/hand_nude.png"; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return "textures/entity/jenny/hand.png";
  }
  
  public void b(String paramString, UUID paramUUID) {
    try {
      if ("action.names.boobjob".equals(paramString)) {
        this.m.func_187227_b(v, Integer.valueOf(0));
        b(b1.PAIZURI_START);
        a(0, b1.PAIZURI_START);
        c(paramUUID);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ("action.names.blowjob".equals(paramString)) {
        b(b1.STARTBLOWJOB);
        a(G(), b1.PAIZURI_START);
        c(paramUUID);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public void func_70619_bc() {
    super.func_70619_bc();
    if (h() == b1.WAITDOGGY) {
      EntityPlayer entityPlayer = c();
      try {
        if (entityPlayer != null)
          try {
            if (entityPlayer.func_70011_f((j()).field_72450_a, (j()).field_72448_b, (j()).field_72449_c) < 1.0D) {
              try {
                if (e(entityPlayer.getPersistentID())) {
                  entityPlayer.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + "sowy no lesbo action yet uwu"));
                  return;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              d(entityPlayer.getPersistentID());
              entityPlayer.func_70634_a((func_174791_d()).field_72450_a, (j()).field_72448_b, (func_174791_d()).field_72449_c);
              a((EntityPlayerMP)entityPlayer, false);
              entityPlayer.func_191958_b(0.0F, 0.0F, 0.0F, 0.0F);
              entityPlayer.field_71075_bZ.field_75100_b = true;
              (this.field_70170_p.func_152378_a(u())).field_71075_bZ.field_75100_b = true;
              a(0.0D, 0.0D, 0.4D, 0.0F, 60.0F);
              this.g = null;
              b(b1.DOGGYSTART);
              bn.a.sendTo(new b5(false), (EntityPlayerMP)entityPlayer);
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
  }
  
  public boolean a(EntityPlayer paramEntityPlayer) {
    a(paramEntityPlayer, this, new String[] { "action.names.blowjob", "action.names.boobjob" }, false);
    return true;
  }
  
  protected b1 a(b1 paramb1) {
    try {
      switch (a.a[paramb1.ordinal()]) {
        case 1:
          return b1.THRUSTBLOWJOB;
        case 2:
          return b1.DOGGYFAST;
        case 3:
          try {
            if (this.ak) {
              this.ak = false;
              a(0.0D, 0.0D, 0.0D, 0.0F, 70.0F);
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return b1.PAIZURI_FAST;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return null;
  }
  
  protected b1 c(b1 paramb1) {
    try {
      if (paramb1 != b1.SUCKBLOWJOB) {
        try {
          if (paramb1 == b1.THRUSTBLOWJOB) {
            a(0.0D, 0.0D, 0.0D, 0.0F, 70.0F);
            return b1.CUMBLOWJOB;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        a(0.0D, 0.0D, 0.0D, 0.0F, 70.0F);
        return b1.CUMBLOWJOB;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.DOGGYSLOW) {
        try {
          if (paramb1 == b1.DOGGYFAST)
            return b1.DOGGYCUM; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } else {
        return b1.DOGGYCUM;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramb1 != b1.PAIZURI_FAST)
        try {
          return (paramb1 != b1.PAIZURI_SLOW) ? null : b1.PAIZURI_CUM;
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return b1.PAIZURI_CUM;
  }
  
  public void b(b1 paramb1) {
    b1 b11 = h();
    try {
      if (b11 == b1.DOGGYCUM)
        try {
          if (paramb1 != b1.DOGGYSLOW) {
            try {
              if (paramb1 == b1.DOGGYFAST)
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
      if (b11 == b1.CUMBLOWJOB)
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
      if (b11 == b1.PAIZURI_CUM)
        try {
          if (paramb1 != b1.PAIZURI_SLOW) {
            try {
              if (paramb1 == b1.PAIZURI_FAST)
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
                  a("animation.jenny.fhappy", true, paramAnimationEvent);
                  break;
                } 
                a("animation.jenny.null", true, paramAnimationEvent);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.jenny.null", true, paramAnimationEvent);
        case 1:
          try {
            if (h() != b1.NULL) {
              a("animation.jenny.null", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (this.Y) {
              a("animation.jenny.sit", true, paramAnimationEvent);
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
                      this.ai = !this.ai;
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
              a("animation.jenny.fly" + (this.ai ? "2" : ""), true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          try {
            if (Math.abs(this.ad.x) + Math.abs(this.ad.y) > 0.0F) {
              try {
                if (this.U) {
                  a("animation.jenny.run", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                if (this.ad.y >= -0.1F) {
                  a("animation.jenny.fastwalk", true, paramAnimationEvent);
                  break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a("animation.jenny.backwards_walk", true, paramAnimationEvent);
              break;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          a("animation.jenny.idle", true, paramAnimationEvent);
          break;
        case 2:
          try {
            switch (a.a[h().ordinal()]) {
              case 4:
                a("animation.jenny.null", true, paramAnimationEvent);
                break;
              case 5:
                a("animation.jenny.strip", false, paramAnimationEvent);
                break;
              case 6:
                a("animation.jenny.payment", false, paramAnimationEvent);
                break;
              case 7:
                a("animation.jenny.blowjobintro", false, paramAnimationEvent);
                break;
              case 1:
                a("animation.jenny.blowjobsuck", true, paramAnimationEvent);
                break;
              case 8:
                a("animation.jenny.blowjobthrust", true, paramAnimationEvent);
                break;
              case 9:
                a("animation.jenny.blowjobcum", false, paramAnimationEvent);
                break;
              case 10:
                a("animation.jenny.doggygoonbed", false, paramAnimationEvent);
                break;
              case 11:
                a("animation.jenny.doggywait", true, paramAnimationEvent);
                break;
              case 12:
                a("animation.jenny.doggystart", false, paramAnimationEvent);
                break;
              case 2:
                a("animation.jenny.doggyslow", true, paramAnimationEvent);
                break;
              case 13:
                a("animation.jenny.doggyfast", true, paramAnimationEvent);
                break;
              case 14:
                a("animation.jenny.doggycum", false, paramAnimationEvent);
                break;
              case 15:
                a("animation.jenny.attack" + this.Q, false, paramAnimationEvent);
                break;
              case 16:
                a("animation.jenny.bowcharge", false, paramAnimationEvent);
                break;
              case 17:
                a("animation.jenny.ride", true, paramAnimationEvent);
                break;
              case 18:
                a("animation.jenny.sit", true, paramAnimationEvent);
                break;
              case 19:
                a("animation.jenny.throwpearl", false, paramAnimationEvent);
                break;
              case 20:
                a("animation.jenny.downed", true, paramAnimationEvent);
                break;
              case 21:
                a("animation.jenny.paizuri_start", false, paramAnimationEvent);
                break;
              case 3:
                a("animation.jenny.paizuri_slow", true, paramAnimationEvent);
                break;
              case 22:
                a("animation.jenny.paizuri_fast", true, paramAnimationEvent);
                break;
              case 23:
                a("animation.jenny.paizuri_cum", false, paramAnimationEvent);
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
            case -188221432:
              if (str.equals("stripMSG1"))
                b = 2; 
              break;
            case -1540620298:
              if (str.equals("paymentMSG1"))
                b = 3; 
              break;
            case -1540620297:
              if (str.equals("paymentMSG2"))
                b = 4; 
              break;
            case -1540620296:
              if (str.equals("paymentMSG3"))
                b = 5; 
              break;
            case 1988710681:
              if (str.equals("sexUiOn"))
                b = 6; 
              break;
            case -1540620295:
              if (str.equals("paymentMSG4"))
                b = 7; 
              break;
            case -1540860248:
              if (str.equals("paymentDone"))
                b = 8; 
              break;
            case -84916847:
              if (str.equals("bjiMSG1"))
                b = 9; 
              break;
            case -84916846:
              if (str.equals("bjiMSG2"))
                b = 10; 
              break;
            case -84916845:
              if (str.equals("bjiMSG3"))
                b = 11; 
              break;
            case -84916844:
              if (str.equals("bjiMSG4"))
                b = 12; 
              break;
            case -84916843:
              if (str.equals("bjiMSG5"))
                b = 13; 
              break;
            case -84916842:
              if (str.equals("bjiMSG6"))
                b = 14; 
              break;
            case -84916841:
              if (str.equals("bjiMSG7"))
                b = 15; 
              break;
            case -84916840:
              if (str.equals("bjiMSG8"))
                b = 16; 
              break;
            case -84916839:
              if (str.equals("bjiMSG9"))
                b = 17; 
              break;
            case 1662545087:
              if (str.equals("bjiMSG10"))
                b = 18; 
              break;
            case 1662545088:
              if (str.equals("bjiMSG11"))
                b = 19; 
              break;
            case 1662545089:
              if (str.equals("bjiMSG12"))
                b = 20; 
              break;
            case -74758116:
              if (str.equals("bjtMSG1"))
                b = 21; 
              break;
            case -85156797:
              if (str.equals("bjiDone"))
                b = 22; 
              break;
            case -74998066:
              if (str.equals("bjtDone"))
                b = 23; 
              break;
            case 441346873:
              if (str.equals("doggyfastReady"))
                b = 24; 
              break;
            case 1982646231:
              if (str.equals("bjtReady"))
                b = 25; 
              break;
            case 1668024441:
              if (str.equals("paizuriReady"))
                b = 26; 
              break;
            case -90457973:
              if (str.equals("bjcMSG1"))
                b = 27; 
              break;
            case -90457972:
              if (str.equals("bjcMSG2"))
                b = 28; 
              break;
            case -90457971:
              if (str.equals("bjcMSG3"))
                b = 29; 
              break;
            case -90457970:
              if (str.equals("bjcMSG4"))
                b = 30; 
              break;
            case -90457969:
              if (str.equals("bjcMSG5"))
                b = 31; 
              break;
            case -90457968:
              if (str.equals("bjcMSG6"))
                b = 32; 
              break;
            case -90457967:
              if (str.equals("bjcMSG7"))
                b = 33; 
              break;
            case -1370194640:
              if (str.equals("bjcBlackScreen"))
                b = 34; 
              break;
            case -90697923:
              if (str.equals("bjcDone"))
                b = 35; 
              break;
            case -669376408:
              if (str.equals("paizuri_cumDone"))
                b = 36; 
              break;
            case 1092262223:
              if (str.equals("doggyCumDone"))
                b = 37; 
              break;
            case 2106063356:
              if (str.equals("doggyGoOnBedMSG1"))
                b = 38; 
              break;
            case 2106063357:
              if (str.equals("doggyGoOnBedMSG2"))
                b = 39; 
              break;
            case 2106063358:
              if (str.equals("doggyGoOnBedMSG3"))
                b = 40; 
              break;
            case 2106063359:
              if (str.equals("doggyGoOnBedMSG4"))
                b = 41; 
              break;
            case 2105823406:
              if (str.equals("doggyGoOnBedDone"))
                b = 42; 
              break;
            case -1648851740:
              if (str.equals("doggystartMSG1"))
                b = 43; 
              break;
            case -1648851739:
              if (str.equals("doggystartMSG2"))
                b = 44; 
              break;
            case -1648851738:
              if (str.equals("doggystartMSG3"))
                b = 45; 
              break;
            case -1648851737:
              if (str.equals("doggystartMSG4"))
                b = 46; 
              break;
            case -1648851736:
              if (str.equals("doggystartMSG5"))
                b = 47; 
              break;
            case -1649091690:
              if (str.equals("doggystartDone"))
                b = 48; 
              break;
            case -2038339681:
              if (str.equals("doggyslowMSG1"))
                b = 49; 
              break;
            case -2038339680:
              if (str.equals("doggyslowMSG2"))
                b = 50; 
              break;
            case 14069882:
              if (str.equals("doggyfastMSG1"))
                b = 51; 
              break;
            case 13829932:
              if (str.equals("doggyfastDone"))
                b = 52; 
              break;
            case -572151107:
              if (str.equals("doggycumMSG1"))
                b = 53; 
              break;
            case -572151106:
              if (str.equals("doggycumMSG2"))
                b = 54; 
              break;
            case -572151105:
              if (str.equals("doggycumMSG3"))
                b = 55; 
              break;
            case -572151104:
              if (str.equals("doggycumMSG4"))
                b = 56; 
              break;
            case -572151103:
              if (str.equals("doggycumMSG5"))
                b = 57; 
              break;
            case 106540102:
              if (str.equals("pearl"))
                b = 58; 
              break;
            case -1149499193:
              if (str.equals("boobjob_camera"))
                b = 59; 
              break;
            case -362733489:
              if (str.equals("paizuri_startDone"))
                b = 60; 
              break;
            case 1179444406:
              if (str.equals("paizuriFastMSG1"))
                b = 61; 
              break;
            case -872965157:
              if (str.equals("paizuriSlowMSG1"))
                b = 62; 
              break;
            case 118020136:
              if (str.equals("paizuriStartMSG1"))
                b = 63; 
              break;
            case 1302251347:
              if (str.equals("paizuri_fastDone"))
                b = 64; 
              break;
            case -362282087:
              if (str.equals("paizuri_startStep"))
                b = 65; 
              break;
            case 738157628:
              if (str.equals("paizuri_cumStart"))
                b = 66; 
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          String str1;
          String str2;
          int i;
          int j;
          IBlockState iBlockState;
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
              b("Hihi~");
              a(c.a(c.GIRLS_JENNY_GIGGLE));
              break;
            case 3:
              b("Huh?");
              a(c.GIRLS_JENNY_HUH[1]);
              break;
            case 4:
              a(c.MISC_PLOB[0], 0.5F);
              str1 = "<" + (Minecraft.func_71410_x()).field_71439_g.func_70005_c_() + "> ";
              str2 = (String)this.m.func_187225_a(f);
              j = -1;
              try {
                switch (str2.hashCode()) {
                  case 109773592:
                    if (str2.equals("strip"))
                      j = 0; 
                    break;
                  case -20842805:
                    if (str2.equals("blowjob"))
                      j = 1; 
                    break;
                  case 95761198:
                    if (str2.equals("doggy"))
                      j = 2; 
                    break;
                  case 64419037:
                    if (str2.equals("boobjob"))
                      j = 3; 
                    break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                switch (j) {
                  case 0:
                    a(str1 + "show Bobs and vegana pls", true);
                    break;
                  case 1:
                    a(str1 + "Give me the sucky sucky and these are yours", true);
                    break;
                  case 2:
                    a(str1 + "Give me the sex pls :)", true);
                    break;
                  case 3:
                    a(str1 + "gib boba OwO", true);
                    break;
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              a(str1 + "sex pls", true);
              break;
            case 5:
              b("Hehe~");
              a(c.a(c.GIRLS_JENNY_GIGGLE));
              break;
            case 6:
              try {
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 7:
              a(c.MISC_PLOB[0], 0.25F);
              break;
            case 8:
              K();
              break;
            case 9:
              try {
                b("What are you...");
                a(c.GIRLS_JENNY_MMM[8]);
                this.r = 180.0F;
                if (L())
                  aC.b(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 10:
              b("eh... boys...");
              a(c.GIRLS_JENNY_LIGHTBREATHING[8]);
              break;
            case 11:
              b("OHOhh...!");
              a(c.GIRLS_JENNY_AFTERSESSIONMOAN[0]);
              break;
            case 12:
              a(c.MISC_BELLJINGLE[0]);
              break;
            case 13:
              try {
                b("Was this really necessary?!");
                a(c.GIRLS_JENNY_HMPH[1], 0.5F);
                if (L())
                  aC.b(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 14:
              b("Oh~");
              a(c.GIRLS_JENNY_LIGHTBREATHING[8]);
              break;
            case 15:
              b("You like it?~");
              a(c.GIRLS_JENNY_GIGGLE[4]);
              break;
            case 16:
              a("<" + (Minecraft.func_71410_x()).field_71439_g.func_70005_c_() + "> Yee", true);
              a(c.MISC_PLOB[0], 0.5F);
              break;
            case 17:
              b("Hihihi~");
              a(c.GIRLS_JENNY_GIGGLE[2]);
              break;
            case 18:
              try {
                if (L())
                  a(-0.4D, -0.8D, -0.2D, 60.0F, -3.0F); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 19:
              try {
                a(c.a(c.GIRLS_JENNY_LIPSOUND));
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 20:
              try {
                if (bY.b.nextInt(5) == 0)
                  a(c.a(c.GIRLS_JENNY_BJMOAN)); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              try {
                a(c.a(c.GIRLS_JENNY_LIPSOUND));
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 21:
              try {
                a(c.a(c.GIRLS_JENNY_MMM));
                a(c.a(c.GIRLS_JENNY_LIPSOUND));
                if (L())
                  aC.a(0.04D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 22:
              try {
                b(b1.SUCKBLOWJOB);
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 23:
              b(b1.SUCKBLOWJOB);
              break;
            case 24:
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
            case 25:
            case 26:
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
            case 27:
              a(c.GIRLS_JENNY_BJMOAN[1]);
              break;
            case 28:
              try {
                a(c.GIRLS_JENNY_BJMOAN[7]);
                if (L())
                  aC.d(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 29:
              a(c.GIRLS_JENNY_AFTERSESSIONMOAN[1]);
              break;
            case 30:
              a(c.GIRLS_JENNY_LIGHTBREATHING[0]);
              break;
            case 31:
              a(c.GIRLS_JENNY_LIGHTBREATHING[1]);
              break;
            case 32:
              a(c.GIRLS_JENNY_LIGHTBREATHING[2]);
              break;
            case 33:
              a(c.GIRLS_JENNY_LIGHTBREATHING[3]);
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
            case 36:
            case 37:
              try {
                if (L()) {
                  aC.b();
                  D();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 38:
              a(c.MISC_BEDRUSTLE[0]);
              this.r = this.field_70177_z;
              break;
            case 39:
              e("what are you waiting for?~");
              a(c.GIRLS_JENNY_LIGHTBREATHING[9]);
              break;
            case 40:
              e("this ass ain't gonna fuck itself...");
              a(c.GIRLS_JENNY_GIGGLE[0]);
              break;
            case 41:
              a(c.MISC_SLAP[0], 0.75F);
              break;
            case 42:
              bn.a.sendToServer(new p(p(), (Minecraft.func_71410_x()).field_71439_g.getPersistentID()));
              b(b1.WAITDOGGY);
              break;
            case 43:
              a(c.MISC_TOUCH[0]);
              break;
            case 44:
              a(c.MISC_TOUCH[1]);
              break;
            case 45:
              a(c.MISC_BEDRUSTLE[1], 0.5F);
              break;
            case 46:
              try {
                a(c.a(c.MISC_SMALLINSERTS));
                a(c.GIRLS_JENNY_MMM[1]);
                if (L())
                  aC.b(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 47:
              a(c.a(c.MISC_POUNDING), 0.33F);
              a(c.a(c.GIRLS_JENNY_MOAN));
              break;
            case 48:
              try {
                b(b1.DOGGYSLOW);
                if (L())
                  aC.a(); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 49:
              a(c.a(c.MISC_POUNDING), 0.33F);
              i = bY.b.nextInt(4);
              if (i == 0) {
                i = bY.b.nextInt(2);
                try {
                  if (i == 0) {
                    a(c.a(c.GIRLS_JENNY_MMM));
                  } else {
                    a(c.a(c.GIRLS_JENNY_MOAN));
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
              } else {
                a(c.a(c.GIRLS_JENNY_HEAVYBREATHING));
              } 
              try {
                if (L())
                  aC.a(0.00666D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 50:
              a(c.a(c.GIRLS_JENNY_LIGHTBREATHING), 0.5F);
              break;
            case 51:
              try {
                a(c.a(c.MISC_POUNDING), 0.75F);
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              this.aj++;
              if (this.aj % 2 == 0) {
                j = bY.b.nextInt(2);
                try {
                  if (j == 0) {
                    a(c.a(c.GIRLS_JENNY_MOAN));
                    break;
                  } 
                } catch (NullPointerException nullPointerException) {
                  throw a(null);
                } 
                a(c.a(c.GIRLS_JENNY_HEAVYBREATHING));
                break;
              } 
              a(c.a(c.GIRLS_JENNY_AHH));
              break;
            case 52:
              b(b1.DOGGYSLOW);
              break;
            case 53:
              a(c.MISC_CUMINFLATION[0], 2.0F);
              a(c.a(c.MISC_POUNDING), 2.0F);
              a(c.a(c.GIRLS_JENNY_MOAN));
              break;
            case 54:
              a(c.GIRLS_JENNY_HEAVYBREATHING[4]);
              break;
            case 55:
              a(c.GIRLS_JENNY_HEAVYBREATHING[5]);
              break;
            case 56:
              a(c.GIRLS_JENNY_HEAVYBREATHING[6]);
              break;
            case 57:
              a(c.GIRLS_JENNY_HEAVYBREATHING[7]);
              break;
            case 58:
              bn.a.sendToServer(new l(p()));
              break;
            case 59:
              try {
                if (L())
                  try {
                    if (!this.ak) {
                      this.ak = true;
                      this.r = 180.0F;
                      a(-0.7D, -0.6D, -0.2D, 60.0F, -3.0F);
                    } 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  }  
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 60:
              try {
                if (L()) {
                  b(b1.PAIZURI_SLOW);
                  aC.b();
                  aC.a();
                } 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 61:
              try {
                a(c.a(c.MISC_POUNDING));
                if (func_70681_au().nextBoolean()) {
                  a(c.a(c.GIRLS_JENNY_MMM));
                } else {
                  a(c.a(c.GIRLS_JENNY_AHH));
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
            case 62:
            case 63:
              try {
                a(c.a(c.MISC_POUNDING));
                if (L())
                  aC.a(0.02D); 
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 64:
              try {
                b(b1.PAIZURI_SLOW);
                if (L())
                  try {
                    if (!this.ak) {
                      this.ak = true;
                      a(-0.7D, -0.6D, -0.2D, 60.0F, -3.0F);
                    } 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  }  
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
            case 65:
              iBlockState = this.field_70170_p.func_180495_p(func_180425_c().func_177973_b(new Vec3i(0, 1, 0)));
              a(iBlockState.func_177230_c().getSoundType(iBlockState, this.field_70170_p, func_180425_c(), (Entity)this).func_185844_d());
              break;
            case 66:
              try {
                if (L())
                  try {
                    if (!this.ak)
                      a(-0.7D, -0.6D, -0.2D, 60.0F, -3.0F); 
                  } catch (NullPointerException nullPointerException) {
                    throw a(null);
                  }  
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              break;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
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


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */