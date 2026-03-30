package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public abstract class bj extends EntityAIBase {
  public Q g;
  
  public EntityPlayer b;
  
  public PathNavigate h;
  
  public EntityDataManager e;
  
  public a f = a.IDLE;
  
  public static final double c = 0.5D;
  
  public static final double d = 0.7D;
  
  public static final int a = 60;
  
  public bj(Q paramQ) {
    this.g = paramQ;
    this.h = paramQ.func_70661_as();
    this.e = paramQ.func_184212_Q();
  }
  
  protected void b() {
    BlockPos blockPos;
    byte b = 0;
    do {
      blockPos = this.b.func_180425_c().func_177982_a(bY.b.nextInt(10), 0, bY.b.nextInt(10));
    } while (++b < 20 && !this.g.func_184595_k(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
    try {
      if (b >= 20)
        this.g.func_70107_b(this.b.field_70165_t, this.b.field_70163_u, this.b.field_70161_v); 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    this.g.field_70159_w = 0.0D;
    this.g.field_70181_x = 0.0D;
    this.g.field_70179_y = 0.0D;
  }
  
  protected double c() {
    double d1;
    Q.a a1;
    float f = this.g.func_70032_d((Entity)this.b);
    if (this.b.func_70051_ag()) {
      d1 = 0.7D;
      a1 = Q.a.RUN;
    } else {
      d1 = 0.5D;
      a1 = Q.a.WALK;
    } 
    double d2 = Math.floor((f / 5.0F)) * 0.2D;
    d1 += d2;
    if (this.g.func_70090_H()) {
      d1 *= 60.0D;
      a1 = Q.a.WALK;
    } 
    this.h.func_75489_a(d1);
    this.g.a(a1);
    return d1;
  }
  
  public void func_75251_c() {
    this.h.func_75499_g();
    this.f = a.IDLE;
    this.g.b(b1.NULL);
    this.e.func_187227_b(Q.p, "");
    this.h = null;
    this.e = null;
    this.b = null;
  }
  
  public boolean func_75250_a() {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return !((String)this.g.func_184212_Q().func_187225_a(Q.p)).equals("");
  }
  
  public boolean func_75253_b() {
    String str = (String)this.e.func_187225_a(Q.p);
    try {
      if (!str.equals(""))
        try {
          if (this.g.field_70170_p.func_152378_a(UUID.fromString(str)) != null);
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    return false;
  }
  
  public void func_75249_e() {
    this.h = this.g.func_70661_as();
    this.e = this.g.func_184212_Q();
    this.b = this.g.field_70170_p.func_152378_a(UUID.fromString((String)this.e.func_187225_a(Q.p)));
  }
  
  public void func_75246_d() {
    try {
      this.f = a();
      if (this.g.w != null) {
        try {
        
        } catch (NullPointerException nullPointerException) {
          throw b(null);
        } 
        this.g.w.a = (this.f == a.IDLE);
      } 
    } catch (NullPointerException nullPointerException) {
      throw b(null);
    } 
    a(this.f);
  }
  
  protected abstract a a();
  
  protected abstract void a(a parama);
  
  @SubscribeEvent
  public void a(LivingDeathEvent paramLivingDeathEvent) {
    if (paramLivingDeathEvent.getEntityLiving() instanceof Q) {
      Q q = (Q)paramLivingDeathEvent.getEntityLiving();
      try {
        if (!((String)q.func_184212_Q().func_187225_a(Q.p)).equals(""))
          paramLivingDeathEvent.setCanceled(true); 
      } catch (NullPointerException nullPointerException) {
        throw b(null);
      } 
    } 
  }
  
  private static NullPointerException b(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public enum a {
    ATTACK, FOLLOW, IDLE, RIDE, DOWNED;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bj.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */