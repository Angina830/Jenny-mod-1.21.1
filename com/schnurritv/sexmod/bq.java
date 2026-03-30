package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class bq {
  static final int a = 284453;
  
  @SubscribeEvent
  public void a(PlayerSleepInBedEvent paramPlayerSleepInBedEvent) {
    EntityPlayer entityPlayer = paramPlayerSleepInBedEvent.getEntityPlayer();
    U u = U.b(entityPlayer.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.func_70093_af())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    paramPlayerSleepInBedEvent.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
  }
  
  @SubscribeEvent
  public void a(PlayerInteractEvent.RightClickBlock paramRightClickBlock) {
    U u = U.b(paramRightClickBlock.getEntityPlayer().getPersistentID());
    BlockPos blockPos1 = paramRightClickBlock.getPos();
    World world = (paramRightClickBlock.getEntityPlayer()).field_70170_p;
    EntityPlayer entityPlayer = paramRightClickBlock.getEntityPlayer();
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!u.z())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!bZ.a(world, blockPos1, paramRightClickBlock.getHitVec(), paramRightClickBlock.getFace(), entityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Boolean)u.func_184212_Q().func_187225_a(Q.c)).booleanValue()) {
        paramRightClickBlock.setCanceled(true);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.func_70093_af())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    ArrayList<BlockPos> arrayList = new ArrayList();
    try {
      if (world.func_180495_p(blockPos1.func_177978_c()).func_177230_c() == Blocks.field_150350_a)
        arrayList.add(blockPos1.func_177978_c()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (world.func_180495_p(blockPos1.func_177974_f()).func_177230_c() == Blocks.field_150350_a)
        arrayList.add(blockPos1.func_177974_f()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (world.func_180495_p(blockPos1.func_177968_d()).func_177230_c() == Blocks.field_150350_a)
        arrayList.add(blockPos1.func_177968_d()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (world.func_180495_p(blockPos1.func_177976_e()).func_177230_c() == Blocks.field_150350_a)
        arrayList.add(blockPos1.func_177976_e()); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    BlockPos blockPos2 = null;
    for (BlockPos blockPos : arrayList) {
      if (blockPos2 == null) {
        blockPos2 = blockPos;
        continue;
      } 
      Vec3d vec3d = entityPlayer.func_174791_d();
      double d1 = a(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
      double d2 = a(blockPos2.func_177958_n(), blockPos2.func_177956_o(), blockPos2.func_177952_p(), vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
      if (d1 < d2)
        blockPos2 = blockPos; 
    } 
    try {
      if (blockPos2 == null) {
        entityPlayer.func_145747_a((ITextComponent)new TextComponentString("Bed is obscured"));
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      entityPlayer.func_70107_b(blockPos2.func_177958_n() + 0.5D, blockPos2.func_177956_o(), blockPos2.func_177952_p() + 0.5D);
      if (blockPos1.func_177978_c().equals(blockPos2))
        entityPlayer.field_70177_z = 0.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (blockPos1.func_177974_f().equals(blockPos2))
        entityPlayer.field_70177_z = 90.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (blockPos1.func_177968_d().equals(blockPos2))
        entityPlayer.field_70177_z = 180.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (blockPos1.func_177976_e().equals(blockPos2))
        entityPlayer.field_70177_z = -90.0F; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ((paramRightClickBlock.getWorld()).field_72995_K) {
        bf.a(false);
        u.h();
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    u.a(new Vec3d(blockPos2.func_177958_n() + 0.5D, (blockPos2.func_177956_o() + 0.0F), blockPos2.func_177952_p() + 0.5D));
    u.a(entityPlayer.field_70177_z);
    u.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(true));
    u.m();
  }
  
  double a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    double d1 = paramDouble1 - paramDouble4;
    double d2 = paramDouble2 - paramDouble5;
    double d3 = paramDouble3 - paramDouble6;
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
  }
  
  @SubscribeEvent
  public void a(PlayerEvent.PlayerRespawnEvent paramPlayerRespawnEvent) {
    EntityPlayer entityPlayer = paramPlayerRespawnEvent.player;
    try {
      if (entityPlayer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    U u = U.d(entityPlayer.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d = entityPlayer.func_174791_d();
    u.field_71093_bK = entityPlayer.field_71093_bK;
    u.func_70634_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
    u.func_70619_bc();
    System.out.println(entityPlayer.field_70170_p.func_175697_a(u.func_180425_c(), 2));
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void b(PlayerInteractEvent.EntityInteract paramEntityInteract) {
    try {
      if (!(paramEntityInteract.getTarget() instanceof EntityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!paramEntityInteract.getEntityPlayer().getPersistentID().equals((Minecraft.func_71410_x()).field_71439_g.getPersistentID()))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    U u1 = U.b(entityPlayerSP.getPersistentID());
    EntityPlayer entityPlayer = (EntityPlayer)paramEntityInteract.getTarget();
    U u2 = U.b(entityPlayer.getPersistentID());
    try {
      if (u2 == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u1 != null) {
        entityPlayerSP.func_146105_b((ITextComponent)new TextComponentString("no lesbo yet owo"), true);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u2.e())
        u2.a((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(PlayerInteractEvent.EntityInteract paramEntityInteract) {
    try {
      if (!(paramEntityInteract.getTarget() instanceof EntityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!paramEntityInteract.getEntityPlayer().getPersistentID().equals((Minecraft.func_71410_x()).field_71439_g.getPersistentID()))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    U u1 = U.b(entityPlayerSP.getPersistentID());
    try {
      if (u1 == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = (EntityPlayer)paramEntityInteract.getTarget();
    U u2 = U.b(entityPlayer.getPersistentID());
    try {
      if (u2 != null) {
        entityPlayer.func_146105_b((ITextComponent)new TextComponentString("no lesbo yet owo"), true);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u1.e()) {
        u1.Z = false;
        u1.a(entityPlayer);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SubscribeEvent
  public void b(PlayerInteractEvent.RightClickBlock paramRightClickBlock) {
    EntityPlayer entityPlayer = paramRightClickBlock.getEntityPlayer();
    U u = U.b(entityPlayer.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!(u instanceof a2))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.func_70093_af())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!entityPlayer.func_184614_ca().equals(ItemStack.field_190927_a))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (((Boolean)u.func_184212_Q().func_187225_a(Q.c)).booleanValue())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (entityPlayer.field_70125_A < 20.0F)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d1 = paramRightClickBlock.getHitVec();
    try {
      if (vec3d1 == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Vec3d vec3d2 = new Vec3d(vec3d1.field_72450_a, Math.floor(vec3d1.field_72448_b) + 0.0D, vec3d1.field_72449_c);
    try {
      if (vec3d1.func_72438_d(entityPlayer.func_174791_d()) > 3.0D)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      entityPlayer.func_70107_b(vec3d2.field_72450_a, Math.floor(vec3d1.field_72448_b), vec3d2.field_72449_c);
      u.a(vec3d2);
      u.a(entityPlayer.field_70177_z);
      u.func_184212_Q().func_187227_b(Q.c, Boolean.valueOf(true));
      u.func_184212_Q().func_187227_b(Q.v, Integer.valueOf(0));
      u.b(b1.STARTDOGGY);
      if ((paramRightClickBlock.getWorld()).field_72995_K)
        try {
          if ((Minecraft.func_71410_x()).field_71439_g.getPersistentID().equals(entityPlayer.getPersistentID()))
            bf.a(false); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SubscribeEvent
  public void a(LivingHurtEvent paramLivingHurtEvent) {
    try {
      if (!(paramLivingHurtEvent.getEntityLiving() instanceof EntityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramLivingHurtEvent.getSource() != DamageSource.field_76379_h)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = (EntityPlayer)paramLivingHurtEvent.getEntityLiving();
    U u = U.b(entityPlayer.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!(u instanceof V)) {
        try {
          if (u instanceof W) {
            paramLivingHurtEvent.setCanceled(true);
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
    paramLivingHurtEvent.setCanceled(true);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(GuiScreenEvent.InitGuiEvent paramInitGuiEvent) {
    GuiScreen guiScreen = paramInitGuiEvent.getGui();
    try {
      if (!(guiScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
        try {
          if (!(guiScreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative))
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    try {
      if (entityPlayerSP == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    U u = U.b(entityPlayerSP.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!u.o())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    List<GuiButton> list = paramInitGuiEvent.getButtonList();
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    String str = I18n.func_135052_a((u.G() == 0) ? "action.names.dressup" : "action.names.strip", new Object[0]);
    list.add(new GuiButton(284453, (int)(guiScreen.field_146294_l * 0.5D - 35.0D), (int)(guiScreen.field_146295_m * 0.87D), 70, 20, str));
    paramInitGuiEvent.setButtonList(list);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(GuiScreenEvent.ActionPerformedEvent paramActionPerformedEvent) {
    GuiScreen guiScreen = paramActionPerformedEvent.getGui();
    try {
      if (!(guiScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
        try {
          if (!(guiScreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative))
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if ((paramActionPerformedEvent.getButton()).field_146127_k != 284453)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Minecraft minecraft = Minecraft.func_71410_x();
    U u = U.b(minecraft.field_71439_g.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (!u.o())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u.B() != null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u.h() != b1.NULL)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    minecraft.field_71474_y.field_74320_O = 2;
    minecraft.field_71460_t.func_175066_a(null);
    u.b(b1.STRIP);
    bf.a(false);
    minecraft.field_71439_g.func_71053_j();
  }
  
  @SubscribeEvent
  public void a(LivingDamageEvent paramLivingDamageEvent) {
    try {
      if (paramLivingDamageEvent.getSource() != DamageSource.field_76379_h)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityLivingBase entityLivingBase = paramLivingDamageEvent.getEntityLiving();
    try {
      if (!(entityLivingBase instanceof EntityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    U u = U.b(entityLivingBase.getPersistentID());
    try {
      if (u == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (u instanceof a2) {
        paramLivingDamageEvent.setResult(Event.Result.DENY);
        paramLivingDamageEvent.setAmount(0.0F);
        paramLivingDamageEvent.setCanceled(true);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */