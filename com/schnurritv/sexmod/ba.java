package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ba {
  public ba() {
    a.a(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.LEATHER, 1, 0);
    a.a(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.GOLD, 2, 0);
    a.a(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.CHAIN, 2, 0);
    a.a(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.IRON, 2, 0);
    a.a(EntityEquipmentSlot.HEAD, ItemArmor.ArmorMaterial.DIAMOND, 3, 3);
    a.a(EntityEquipmentSlot.CHEST, ItemArmor.ArmorMaterial.LEATHER, 3, 0);
    a.a(EntityEquipmentSlot.CHEST, ItemArmor.ArmorMaterial.GOLD, 5, 0);
    a.a(EntityEquipmentSlot.CHEST, ItemArmor.ArmorMaterial.CHAIN, 5, 0);
    a.a(EntityEquipmentSlot.CHEST, ItemArmor.ArmorMaterial.IRON, 6, 0);
    a.a(EntityEquipmentSlot.CHEST, ItemArmor.ArmorMaterial.DIAMOND, 8, 3);
    a.a(EntityEquipmentSlot.LEGS, ItemArmor.ArmorMaterial.LEATHER, 2, 0);
    a.a(EntityEquipmentSlot.LEGS, ItemArmor.ArmorMaterial.GOLD, 3, 0);
    a.a(EntityEquipmentSlot.LEGS, ItemArmor.ArmorMaterial.CHAIN, 4, 0);
    a.a(EntityEquipmentSlot.LEGS, ItemArmor.ArmorMaterial.IRON, 5, 0);
    a.a(EntityEquipmentSlot.LEGS, ItemArmor.ArmorMaterial.DIAMOND, 6, 3);
    a.a(EntityEquipmentSlot.FEET, ItemArmor.ArmorMaterial.LEATHER, 1, 0);
    a.a(EntityEquipmentSlot.FEET, ItemArmor.ArmorMaterial.GOLD, 1, 0);
    a.a(EntityEquipmentSlot.FEET, ItemArmor.ArmorMaterial.CHAIN, 1, 0);
    a.a(EntityEquipmentSlot.FEET, ItemArmor.ArmorMaterial.IRON, 2, 0);
    a.a(EntityEquipmentSlot.FEET, ItemArmor.ArmorMaterial.DIAMOND, 3, 3);
  }
  
  @SubscribeEvent
  public void a(LivingDamageEvent paramLivingDamageEvent) {
    try {
      if (!(paramLivingDamageEvent.getEntity() instanceof S))
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    S s = (S)paramLivingDamageEvent.getEntity();
    ItemStack[] arrayOfItemStack = { s.H.getStackInSlot(2), s.H.getStackInSlot(3), s.H.getStackInSlot(4), s.H.getStackInSlot(5) };
    ArrayList<ItemArmor> arrayList = new ArrayList();
    ArrayList<ItemStack> arrayList1 = new ArrayList();
    for (ItemStack itemStack : arrayOfItemStack) {
      try {
        if (itemStack.func_77973_b() instanceof ItemArmor) {
          arrayList.add((ItemArmor)itemStack.func_77973_b());
          arrayList1.add(itemStack);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    try {
      if (arrayList.size() == 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    DamageSource damageSource = paramLivingDamageEvent.getSource();
    int i = 0;
    int j = 0;
    if (!damageSource.func_76363_c())
      for (ItemArmor itemArmor : arrayList) {
        i += a.b(itemArmor.field_77881_a, itemArmor.func_82812_d());
        j += a.a(itemArmor.field_77881_a, itemArmor.func_82812_d());
      }  
    float f1 = paramLivingDamageEvent.getAmount();
    f1 *= 1.0F - Math.min(20.0F, Math.max(i / 5.0F, i - 4.0F * f1 / (j + 8.0F))) / 25.0F;
    float f2 = 0.0F;
    float f3 = f1;
    for (ItemStack itemStack : arrayList1) {
      int k = EnchantmentHelper.func_77506_a(Enchantments.field_180310_c, itemStack);
      f1 -= k * 0.04F * f1;
      int m = EnchantmentHelper.func_77506_a(Enchantments.field_92091_k, itemStack);
      try {
      
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      f2 += (bY.b.nextFloat() < 0.15F * m) ? (bY.b.nextFloat() * 4.0F + 1.0F) : 0.0F;
      f2 = Math.min(4.0F, f2);
      if (damageSource.func_76347_k()) {
        int n = EnchantmentHelper.func_77506_a(Enchantments.field_77329_d, itemStack);
        f1 -= n * 0.08F * f1;
      } 
      if (damageSource.func_94541_c()) {
        int n = EnchantmentHelper.func_77506_a(Enchantments.field_185297_d, itemStack);
        f1 -= n * 0.08F * f1;
      } 
      if (damageSource.field_76373_n.equals("fall")) {
        int n = EnchantmentHelper.func_77506_a(Enchantments.field_180309_e, itemStack);
        f1 -= n * 0.12F * f1;
      } 
      if (damageSource.func_76352_a()) {
        int n = EnchantmentHelper.func_77506_a(Enchantments.field_180308_g, itemStack);
        f1 -= n * 0.08F * f1;
      } 
    } 
    try {
      if (f2 > 0.0F && damageSource instanceof EntityDamageSource) {
        EntityDamageSource entityDamageSource = (EntityDamageSource)damageSource;
        try {
          if (entityDamageSource.func_76346_g() != null)
            entityDamageSource.func_76346_g().func_70097_a(DamageSource.func_92087_a((Entity)s), f2); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    paramLivingDamageEvent.setAmount(f1);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  static class a {
    public static HashMap<String, Integer[]> a = (HashMap)new HashMap<>();
    
    public static int b(EntityEquipmentSlot param1EntityEquipmentSlot, ItemArmor.ArmorMaterial param1ArmorMaterial) {
      try {
        return ((Integer[])a.get(param1EntityEquipmentSlot.toString() + param1ArmorMaterial.toString()))[0].intValue();
      } catch (NullPointerException nullPointerException) {
        return 3;
      } 
    }
    
    public static int a(EntityEquipmentSlot param1EntityEquipmentSlot, ItemArmor.ArmorMaterial param1ArmorMaterial) {
      try {
        return ((Integer[])a.get(param1EntityEquipmentSlot.toString() + param1ArmorMaterial.toString()))[1].intValue();
      } catch (NullPointerException nullPointerException) {
        return 0;
      } 
    }
    
    public static void a(EntityEquipmentSlot param1EntityEquipmentSlot, ItemArmor.ArmorMaterial param1ArmorMaterial, int param1Int1, int param1Int2) {
      a.put(param1EntityEquipmentSlot.toString() + param1ArmorMaterial.toString(), new Integer[] { Integer.valueOf(param1Int1), Integer.valueOf(param1Int2) });
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\ba.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */