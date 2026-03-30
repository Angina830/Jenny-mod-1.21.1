package com.schnurritv.sexmod;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class bs extends SlotItemHandler {
  a a;
  
  public bs(a parama, IItemHandler paramIItemHandler, int paramInt1, int paramInt2, int paramInt3) {
    super(paramIItemHandler, paramInt1, paramInt2, paramInt3);
    this.a = parama;
  }
  
  public static boolean a(ItemStack paramItemStack, int paramInt) {
    return a(paramItemStack, a.a(paramInt));
  }
  
  public boolean func_75214_a(ItemStack paramItemStack) {
    return a(paramItemStack, this.a);
  }
  
  static boolean a(ItemStack paramItemStack, a parama) {
    Item item = paramItemStack.func_77973_b();
    try {
      switch (b.a[parama.ordinal()]) {
        case 1:
          try {
            if (!(item instanceof net.minecraft.item.ItemSword)) {
              try {
                if (item instanceof net.minecraft.item.ItemTool);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              } 
              return false;
            } 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
        case 2:
          return item instanceof net.minecraft.item.ItemBow;
        case 3:
          try {
            if (item instanceof ItemArmor)
              try {
                if (((ItemArmor)item).field_77881_a == EntityEquipmentSlot.HEAD);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              }  
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return false;
        case 4:
          try {
            if (item instanceof ItemArmor)
              try {
                if (((ItemArmor)item).field_77881_a == EntityEquipmentSlot.CHEST);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              }  
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return false;
        case 5:
          try {
            if (item instanceof ItemArmor)
              try {
                if (((ItemArmor)item).field_77881_a == EntityEquipmentSlot.LEGS);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              }  
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return false;
        case 6:
          try {
            if (item instanceof ItemArmor)
              try {
                if (((ItemArmor)item).field_77881_a == EntityEquipmentSlot.FEET);
              } catch (NullPointerException nullPointerException) {
                throw a(null);
              }  
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          } 
          return false;
        case 7:
          return item instanceof net.minecraft.item.ItemFishingRod;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return false;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  public enum a {
    WEAPON(0),
    BOW(1),
    HELMET(2),
    CHEST_PLATE(3),
    PANTS(4),
    SHOES(5),
    ROD(6);
    
    public int id;
    
    public static a a(int param1Int) {
      try {
        switch (param1Int) {
          case 0:
            return WEAPON;
          case 1:
            return BOW;
          case 2:
            return HELMET;
          case 3:
            return CHEST_PLATE;
          case 4:
            return PANTS;
          case 5:
            return SHOES;
          case 6:
            return ROD;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      throw new NullPointerException("Girls don't have a slot nr. " + param1Int);
    }
    
    a(int param1Int1) {
      this.id = param1Int1;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */