package com.schnurritv.sexmod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber
public class o extends Potion {
  public static final Potion b = new o("horny potion", false, 16736968, 0, 0);
  
  public static final PotionType a = (PotionType)(new PotionType("horny_potion", new PotionEffect[] { new PotionEffect(b, 3600), new PotionEffect(MobEffects.field_76431_k, 200, 1) })).setRegistryName("horny_potion");
  
  public o() {
    super(false, 0);
  }
  
  public o(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
    super(paramBoolean, paramInt1);
    func_76390_b(paramString);
    func_76399_b(paramInt2, paramInt3);
    setRegistryName(new ResourceLocation("sexmod:" + paramString));
  }
  
  public static void b() {
    ForgeRegistries.POTIONS.register((IForgeRegistryEntry)b);
    ForgeRegistries.POTION_TYPES.register((IForgeRegistryEntry)a);
    PotionHelper.func_193357_a(PotionTypes.field_185231_c, Item.func_150898_a((Block)Blocks.field_150328_O), a);
  }
  
  @SideOnly(Side.SERVER)
  @SubscribeEvent
  public void b(TickEvent.PlayerTickEvent paramPlayerTickEvent) {
    EntityPlayer entityPlayer = paramPlayerTickEvent.player;
    PotionEffect potionEffect = entityPlayer.func_70660_b(b);
    try {
      if (potionEffect == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (potionEffect.func_76459_b() > 3500)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    entityPlayer.func_184589_d(b);
    bn.a.sendTo(new a3(), (EntityPlayerMP)entityPlayer);
  }
  
  @SubscribeEvent
  public void a(TickEvent.PlayerTickEvent paramPlayerTickEvent) {
    MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
    try {
      if (minecraftServer == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (minecraftServer.func_71262_S())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = paramPlayerTickEvent.player;
    PotionEffect potionEffect = entityPlayer.func_70660_b(b);
    try {
      if (potionEffect == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (potionEffect.func_76459_b() > 3500)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    entityPlayer.func_184589_d(b);
    a();
  }
  
  @SideOnly(Side.CLIENT)
  void a() {
    Minecraft minecraft = Minecraft.func_71410_x();
    minecraft.func_152344_a(() -> paramMinecraft.func_147108_a(new v()));
  }
  
  @SubscribeEvent
  public void a(LivingEvent.LivingUpdateEvent paramLivingUpdateEvent) {
    if (paramLivingUpdateEvent.getEntity() instanceof EntityVillager) {
      EntityVillager entityVillager = (EntityVillager)paramLivingUpdateEvent.getEntity();
      try {
        if (entityVillager.func_70644_a(b)) {
          entityVillager.field_70714_bg.func_75776_a(2, new N(entityVillager));
          entityVillager.func_184589_d(b);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    try {
      if (!(paramLivingUpdateEvent.getEntity() instanceof EntityAnimal))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityAnimal entityAnimal = (EntityAnimal)paramLivingUpdateEvent.getEntity();
    try {
      if (entityAnimal.func_70644_a(b)) {
        try {
          if (entityAnimal.func_70874_b() >= 0) {
            entityAnimal.func_70873_a(0);
            entityAnimal.func_70875_t();
            entityAnimal.func_146082_f(entityAnimal.field_70170_p.func_72890_a((Entity)entityAnimal, 30.0D));
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        entityAnimal.func_184589_d(b);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\o.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */