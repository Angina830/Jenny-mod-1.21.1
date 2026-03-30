package com.schnurritv.sexmod;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class aq {
  @SubscribeEvent
  public void a(GuiOpenEvent paramGuiOpenEvent) {
    try {
      if (!(paramGuiOpenEvent.getGui() instanceof net.minecraft.client.gui.GuiMainMenu)) {
        try {
          if (paramGuiOpenEvent.getGui() instanceof net.minecraft.client.gui.GuiMultiplayer) {
            Q.f().clear();
            U.X.clear();
            U.V.clear();
            return;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Q.f().clear();
    U.X.clear();
    U.V.clear();
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\aq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */