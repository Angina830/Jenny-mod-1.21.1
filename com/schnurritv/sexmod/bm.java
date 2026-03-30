package com.schnurritv.sexmod;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class bm {
  @SubscribeEvent
  public void a(LivingDeathEvent paramLivingDeathEvent) {
    if (paramLivingDeathEvent.getEntity() instanceof Q) {
      Q q = (Q)paramLivingDeathEvent.getEntity();
      Q.f().remove(q);
    } 
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */