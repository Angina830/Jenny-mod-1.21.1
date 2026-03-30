package com.schnurritv.sexmod;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class w extends Item implements IAnimatable {
  static final String i = "sexmodAllieInUse";
  
  static final String g = "sexmodAllieInUseTicks";
  
  public static final String c = "sexmodUses";
  
  public static final String k = "sexmodAllieID";
  
  static final Integer d = Integer.valueOf(95);
  
  static final Integer h = Integer.valueOf(50);
  
  public static final int j = 150;
  
  public static final float a = 0.75F;
  
  public static final w e = new w();
  
  private final AnimationFactory b = new AnimationFactory(this);
  
  AnimationController<w> f;
  
  public w() {
    func_77637_a(CreativeTabs.field_78026_f);
    this.field_77777_bU = 1;
  }
  
  public static void a() {
    e.setRegistryName("sexmod", "allies_lamp");
    e.func_77655_b("allies_lamp");
    MinecraftForge.EVENT_BUS.register(w.class);
  }
  
  @SubscribeEvent
  public static void a(RegistryEvent.Register<Item> paramRegister) {
    paramRegister.getRegistry().register((IForgeRegistryEntry)e);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void a(ModelRegistryEvent paramModelRegistryEvent) {
    ModelLoader.setCustomModelResourceLocation(e, 0, new ModelResourceLocation("sexmod:allies_lamp"));
    e.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new aN());
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void a(RenderGameOverlayEvent.Pre paramPre) {
    NBTTagCompound nBTTagCompound = (Minecraft.func_71410_x()).field_71439_g.getEntityData();
    try {
      if (nBTTagCompound.func_74767_n("sexmodAllieInUse"))
        paramPre.setCanceled(true); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SubscribeEvent
  public void a(LootTableLoadEvent paramLootTableLoadEvent) {
    HashSet<ResourceLocation> hashSet = new HashSet();
    hashSet.add(LootTableList.field_186424_f);
    hashSet.add(LootTableList.field_186429_k);
    hashSet.add(LootTableList.field_186422_d);
    hashSet.add(LootTableList.field_191192_o);
    if (hashSet.contains(paramLootTableLoadEvent.getName())) {
      LootPool lootPool = paramLootTableLoadEvent.getTable().getPool("pool3");
      if (lootPool == null)
        lootPool = paramLootTableLoadEvent.getTable().getPool("pool2"); 
      try {
        if (lootPool != null)
          lootPool.addEntry((LootEntry)new LootEntryItem(e, 5, 0, new net.minecraft.world.storage.loot.functions.LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "sexmod:allies_lamp")); 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
  }
  
  public void registerControllers(AnimationData paramAnimationData) {
    this.f = new AnimationController(this, "controller", 2.0F, this::a);
    paramAnimationData.addAnimationController(this.f);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_77624_a(ItemStack paramItemStack, World paramWorld, List<String> paramList, ITooltipFlag paramITooltipFlag) {
    NBTTagCompound nBTTagCompound = paramItemStack.func_77978_p();
    try {
      if (nBTTagCompound == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    int i = 3 - paramItemStack.func_77978_p().func_74762_e("sexmodUses");
    try {
      switch (i) {
        case 2:
          paramList.add("2 wishes left");
          break;
        case 1:
          paramList.add("1 wish left");
          break;
        case 0:
          paramList.add("no wishes left");
          break;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  protected <segs extends IAnimatable> PlayState a(AnimationEvent<segs> paramAnimationEvent) {
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    NBTTagCompound nBTTagCompound = entityPlayerSP.getEntityData();
    boolean bool = nBTTagCompound.func_74767_n("sexmodAllieInUse");
    try {
      if (!bool) {
        paramAnimationEvent.getController().clearAnimationCache();
        return PlayState.STOP;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    paramAnimationEvent.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.lamp.rub", (ILoopType)ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
    return PlayState.CONTINUE;
  }
  
  public void func_77663_a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean) {
    try {
      if (!(paramEntity instanceof EntityPlayer))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    EntityPlayer entityPlayer = (EntityPlayer)paramEntity;
    NBTTagCompound nBTTagCompound1 = paramEntity.getEntityData();
    try {
      if (!paramItemStack.equals(entityPlayer.func_184614_ca()))
        try {
          if (!paramItemStack.equals(entityPlayer.func_184592_cb()))
            return; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    boolean bool = nBTTagCompound1.func_74767_n("sexmodAllieInUse");
    int i = nBTTagCompound1.func_74762_e("sexmodAllieInUseTicks");
    try {
      if (!bool)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      nBTTagCompound1.func_74768_a("sexmodAllieInUseTicks", i + 1);
      if (i > h.intValue() && i < d.intValue()) {
        double d = ((i - h.intValue()) / (d.intValue() - h.intValue()));
        d = bZ.c(d);
        Vec3d vec3d1 = new Vec3d(0.0D, entityPlayer.eyeHeight * (1.0D - d), 0.0D);
        bZ.a(paramWorld, EnumParticleTypes.CRIT_MAGIC, a(entityPlayer).func_178787_e(vec3d1), (int)(d * 150.0D), d * 0.75D, d);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (i < d.intValue())
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      bZ.a(paramWorld, EnumParticleTypes.CRIT_MAGIC, a(entityPlayer), 150, 0.75D, 2.0D);
      nBTTagCompound1.func_74757_a("sexmodAllieInUse", false);
      nBTTagCompound1.func_74768_a("sexmodAllieInUseTicks", 0);
      if (paramWorld.field_72995_K) {
        bf.a(false);
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    NBTTagCompound nBTTagCompound2 = paramItemStack.func_77978_p();
    if (nBTTagCompound2 == null)
      nBTTagCompound2 = new NBTTagCompound(); 
    nBTTagCompound2.func_74768_a("sexmodUses", nBTTagCompound2.func_74762_e("sexmodUses") + 1);
    R r = new R(entityPlayer.field_70170_p, entityPlayer.func_184614_ca());
    r.d(entityPlayer.getPersistentID());
    Vec3d vec3d = a(entityPlayer);
    r.func_70080_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, entityPlayer.field_70177_z + 180.0F, entityPlayer.field_70125_A);
    r.a(r.func_174791_d());
    r.a(entityPlayer.field_70177_z + 180.0F);
    r.a(true);
    r.func_189654_d(true);
    r.field_70145_X = true;
    entityPlayer.field_70170_p.func_72838_d((Entity)r);
    BlockPos blockPos = r.func_180425_c().func_177982_a(0, -1, 0);
    try {
      if (r.field_70170_p.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150354_m)) {
        r.b(b1.SUMMON_SAND);
      } else {
        try {
        
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        r.b(r.g() ? b1.SUMMON : b1.SUMMON_NORMAL);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    paramItemStack.func_77982_d(nBTTagCompound2);
  }
  
  Vec3d a(EntityPlayer paramEntityPlayer) {
    return paramEntityPlayer.func_174791_d().func_178787_e(bZ.a(new Vec3d(0.0D, 0.0D, 2.0D), paramEntityPlayer.field_70759_as));
  }
  
  public AnimationFactory getFactory() {
    return this.b;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public static class a {
    @SubscribeEvent
    public void a(PlayerEvent.PlayerLoggedOutEvent param1PlayerLoggedOutEvent) {
      param1PlayerLoggedOutEvent.player.getEntityData().func_74757_a("sexmodAllieInUse", false);
    }
    
    @SubscribeEvent
    public void a(PlayerInteractEvent.RightClickItem param1RightClickItem) {
      EntityPlayer entityPlayer = param1RightClickItem.getEntityPlayer();
      EnumHand enumHand = param1RightClickItem.getHand();
      ItemStack itemStack = entityPlayer.func_184586_b(enumHand);
      try {
        if (U.c(entityPlayer))
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      try {
        if (entityPlayer.field_70170_p.field_72995_K)
          try {
            if (!bf.a())
              return; 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      if (!entityPlayer.field_70170_p.field_72995_K)
        try {
          for (Q q : Q.f()) {
            try {
              if (q.field_70128_L)
                continue; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
            try {
              if (!(q instanceof R))
                continue; 
            } catch (ConcurrentModificationException concurrentModificationException) {
              throw a(null);
            } 
            R r = (R)q;
            ItemStack itemStack1 = (ItemStack)r.func_184212_Q().func_187225_a(R.O);
            if (itemStack.equals(itemStack1))
              return; 
          } 
        } catch (ConcurrentModificationException concurrentModificationException) {} 
      try {
        if (itemStack.func_77973_b() != w.e)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      NBTTagCompound nBTTagCompound1 = itemStack.func_77978_p();
      try {
        if (nBTTagCompound1 != null)
          try {
            if (nBTTagCompound1.func_74762_e("sexmodUses") >= 3)
              return; 
          } catch (ConcurrentModificationException concurrentModificationException) {
            throw a(null);
          }  
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      NBTTagCompound nBTTagCompound2 = entityPlayer.getEntityData();
      boolean bool = nBTTagCompound2.func_74767_n("sexmodAllieInUse");
      try {
        if (bool)
          return; 
      } catch (ConcurrentModificationException concurrentModificationException) {
        throw a(null);
      } 
      nBTTagCompound2.func_74757_a("sexmodAllieInUse", true);
      nBTTagCompound2.func_74768_a("sexmodAllieInUseTicks", 0);
    }
    
    private static ConcurrentModificationException a(ConcurrentModificationException param1ConcurrentModificationException) {
      return param1ConcurrentModificationException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\w.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */