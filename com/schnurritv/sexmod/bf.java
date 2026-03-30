package com.schnurritv.sexmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class bf {
  private static boolean a = true;
  
  public static boolean b = false;
  
  public static boolean c = false;
  
  @SubscribeEvent
  public void a(InputUpdateEvent paramInputUpdateEvent) {
    MovementInput movementInput = paramInputUpdateEvent.getMovementInput();
    try {
      b = movementInput.field_78899_d;
      c = movementInput.field_78901_c;
      if (a)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (movementInput.field_78901_c)
        U.w(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (movementInput.field_78899_d)
        Q.g((Minecraft.func_71410_x()).field_71439_g.getPersistentID()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (movementInput.field_78901_c)
        try {
          if (aC.e >= 1.0D)
            Q.i((Minecraft.func_71410_x()).field_71439_g.getPersistentID()); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    movementInput.field_187256_d = false;
    movementInput.field_187255_c = false;
    movementInput.field_187257_e = false;
    movementInput.field_187258_f = false;
    movementInput.field_78899_d = false;
    movementInput.field_78901_c = false;
    movementInput.field_192832_b = 0.0F;
    movementInput.field_78902_a = 0.0F;
    (Minecraft.func_71410_x()).field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
  }
  
  public static boolean a() {
    return a;
  }
  
  public static void a(boolean paramBoolean) {
    try {
      a = paramBoolean;
      if (!paramBoolean)
        b(); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  static void b() {
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    try {
      if (!U.c((EntityPlayer)entityPlayerSP))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    entityPlayerSP.func_146105_b((ITextComponent)new TextComponentString("Jump to get out of the animation"), true);
  }
  
  @SubscribeEvent
  public void a(MouseEvent paramMouseEvent) {
    try {
      if (!a)
        try {
          if (paramMouseEvent.isButtonstate())
            paramMouseEvent.setCanceled(true); 
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
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */