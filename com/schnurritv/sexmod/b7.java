package com.schnurritv.sexmod;

import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class b7 {
  public static b7 b;
  
  private a a;
  
  public void c() {
    try {
      if (b.a == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (--b.a.e <= 0.0F) {
        (Minecraft.func_71410_x()).field_71439_g.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_135052_a("genderswap.sexpromt.timeout", new Object[0])));
        a();
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public a b() {
    return b.a;
  }
  
  void a() {
    b.a = null;
  }
  
  public void a(@Nonnull a parama) {
    World world = (Minecraft.func_71410_x()).field_71439_g.field_70170_p;
    EntityPlayer entityPlayer1 = world.func_152378_a(parama.d);
    EntityPlayer entityPlayer2 = world.func_152378_a(parama.c);
    try {
      if (entityPlayer2 != null)
        try {
          if (entityPlayer1 != null) {
            try {
            
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
            TextComponentString textComponentString1 = new TextComponentString(TextFormatting.LIGHT_PURPLE + (parama.b ? entityPlayer2.func_70005_c_() : entityPlayer1.func_70005_c_()) + " " + TextFormatting.DARK_PURPLE + I18n.func_135052_a("genderswap.sexpromt.playerxaskedfory", new Object[0]) + " " + TextFormatting.LIGHT_PURPLE + I18n.func_135052_a(parama.a, new Object[0]));
            TextComponentString textComponentString2 = new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_135052_a("genderswap.sexpromt.autodeletion", new Object[0]));
            TextComponentString textComponentString3 = new TextComponentString(TextFormatting.DARK_PURPLE + "[ " + TextFormatting.LIGHT_PURPLE + I18n.func_135052_a("genderswap.sexpromt.accept", new Object[0]) + TextFormatting.DARK_PURPLE + " | " + TextFormatting.LIGHT_PURPLE + I18n.func_135052_a("genderswap.sexpromt.decline", new Object[0]) + TextFormatting.DARK_PURPLE + " ]");
            entityPlayer1.func_145747_a((ITextComponent)textComponentString1);
            entityPlayer1.func_145747_a((ITextComponent)textComponentString2);
            entityPlayer1.func_145747_a((ITextComponent)textComponentString3);
            this.a = parama;
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
  
  @SubscribeEvent
  public void a(ClientChatEvent paramClientChatEvent) {
    try {
      if (b.b() == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    String str = paramClientChatEvent.getMessage().toLowerCase();
    if (str.equals(I18n.func_135052_a("genderswap.sexpromt.accept", new Object[0]).toLowerCase())) {
      a a1 = b.b();
      a(a1.a, a1.d, a1.c);
      a();
      paramClientChatEvent.setCanceled(true);
    } 
    try {
      if (str.equals(I18n.func_135052_a("genderswap.sexpromt.decline", new Object[0]).toLowerCase())) {
        (Minecraft.func_71410_x()).field_71439_g.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_135052_a("genderswap.sexpromt.declineconformation", new Object[0])));
        a();
        paramClientChatEvent.setCanceled(true);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void a(String paramString, UUID paramUUID1, UUID paramUUID2) {
    bn.a.sendToServer(new a(paramUUID1, paramUUID2, paramString));
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a {
    public String a;
    
    public UUID c;
    
    public UUID d;
    
    public float e;
    
    boolean b;
    
    public a(String param1String, UUID param1UUID1, UUID param1UUID2, boolean param1Boolean) {
      this.a = param1String;
      this.c = param1UUID1;
      this.d = param1UUID2;
      this.e = 1200.0F;
      this.b = param1Boolean;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\b7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */