package com.schnurritv.sexmod;

import java.util.ConcurrentModificationException;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class bz {
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(RenderWorldLastEvent paramRenderWorldLastEvent) {
    Minecraft minecraft = Minecraft.func_71410_x();
    try {
      if (minecraft.field_71474_y.field_74320_O != 0)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID = minecraft.field_71439_g.getPersistentID();
    ag ag = null;
    try {
      for (Q q : Q.f()) {
        try {
          if (q == null)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (!q.field_70170_p.field_72995_K)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
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
    } catch (ConcurrentModificationException concurrentModificationException) {}
    try {
      if (ag == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    Render render = minecraft.func_175598_ae().func_78713_a((Entity)ag);
    try {
      if (render == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    try {
      if (!(render instanceof cP))
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    cP cP = (cP)render;
    float f1 = minecraft.field_71439_g.field_70177_z;
    cP.q = (float)(minecraft.field_71439_g.field_71158_b.field_78902_a * cP.w.field_72450_a);
    cP.q += -(f1 - cP.D) * 3.0F;
    cP.q = bZ.a(cP.r, cP.q, 0.1F);
    float f2 = -minecraft.field_71439_g.field_70125_A;
    cP.p = (float)(minecraft.field_71439_g.field_71158_b.field_192832_b * cP.w.field_72449_c + (float)minecraft.field_71439_g.field_70181_x * cP.w.field_72448_b);
    cP.p += -(f2 - cP.C) * 3.0F;
    cP.p = bZ.a(cP.u, cP.p, 0.1F);
    cP.b(ag, paramRenderWorldLastEvent.getPartialTicks());
    cP.D = f1;
    cP.r = cP.q;
    cP.C = f2;
    cP.u = cP.p;
    minecraft.field_71474_y.field_74319_N = false;
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void b(RenderWorldLastEvent paramRenderWorldLastEvent) {
    Minecraft minecraft = Minecraft.func_71410_x();
    try {
      if (minecraft.field_71439_g == null)
        return; 
    } catch (ConcurrentModificationException concurrentModificationException) {
      throw a(null);
    } 
    UUID uUID = minecraft.field_71439_g.getPersistentID();
    try {
      for (Q q : Q.f()) {
        try {
          if (!q.field_70170_p.field_72995_K)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (!(q instanceof ag))
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        ag ag = (ag)q;
        try {
          if (q.h() == b1.START_THROWING) {
            try {
              ag.ag = true;
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
            minecraft.func_175598_ae().func_188391_a((Entity)ag, 0.0D, 0.0D, 0.0D, uUID.equals(ag.r()) ? -420.69F : 0.0F, minecraft.func_184121_ak(), false);
            ag.ag = false;
            minecraft.field_71474_y.field_74319_N = false;
            return;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {}
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(RenderHandEvent paramRenderHandEvent) {
    Minecraft minecraft = Minecraft.func_71410_x();
    UUID uUID = minecraft.field_71439_g.getPersistentID();
    try {
      for (Q q : Q.f()) {
        try {
          if (!(q instanceof ag))
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        ag ag = (ag)q;
        try {
          if (q.h() != b1.PICK_UP)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (uUID.equals(ag.r())) {
            paramRenderHandEvent.setCanceled(true);
            break;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {}
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(RenderPlayerEvent.Pre paramPre) {
    UUID uUID = paramPre.getEntityPlayer().getPersistentID();
    try {
      for (Q q : Q.f()) {
        try {
          if (!(q instanceof ag))
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        ag ag = (ag)q;
        try {
          if (q.h() != b1.PICK_UP)
            continue; 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
        try {
          if (uUID.equals(ag.r())) {
            paramPre.setCanceled(true);
            break;
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {
          throw a(null);
        } 
      } 
    } catch (ConcurrentModificationException concurrentModificationException) {}
  }
  
  private static ConcurrentModificationException a(ConcurrentModificationException paramConcurrentModificationException) {
    return paramConcurrentModificationException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */