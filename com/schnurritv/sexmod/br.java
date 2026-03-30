package com.schnurritv.sexmod;

import javax.swing.JFrame;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class br extends JFrame {
  public boolean a = false;
  
  @SubscribeEvent
  public void a(TickEvent.ClientTickEvent paramClientTickEvent) {
    try {
      if (this.a)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.a = true;
    J.a();
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\br.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */