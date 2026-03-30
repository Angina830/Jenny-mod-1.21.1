package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class am extends Container {
  aI b;
  
  public Slot[] c;
  
  public UUID a;
  
  public static List<am> d = new ArrayList<>();
  
  public am(aI paramaI, InventoryPlayer paramInventoryPlayer, UUID paramUUID) {
    this.a = paramUUID;
    d.add(this);
    if (paramaI.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
      IItemHandler iItemHandler = paramaI.<IItemHandler>getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
      this.b = paramaI;
      this.c = new Slot[] { (Slot)new bs(bs.a.WEAPON, iItemHandler, bs.a.WEAPON.id, 41, 60), (Slot)new bs(bs.a.BOW, iItemHandler, bs.a.BOW.id, 59, 60), (Slot)new bs(bs.a.HELMET, iItemHandler, bs.a.HELMET.id, 81, 60), (Slot)new bs(bs.a.CHEST_PLATE, iItemHandler, bs.a.CHEST_PLATE.id, 100, 60), (Slot)new bs(bs.a.PANTS, iItemHandler, bs.a.PANTS.id, 119, 60), (Slot)new bs(bs.a.SHOES, iItemHandler, bs.a.SHOES.id, 138, 60), (Slot)new bs(bs.a.ROD, iItemHandler, bs.a.ROD.id, 22, 60) };
      ArrayList<Slot> arrayList = new ArrayList();
      byte b;
      for (b = 0; b < 3; b++) {
        byte b1 = 0;
        try {
          while (b1 < 9) {
            arrayList.add(new Slot((IInventory)paramInventoryPlayer, b1 + b * 9 + 9, 8 + b1 * 18, 84 + b * 18));
            b1++;
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
      b = 0;
      try {
        while (b < 9) {
          arrayList.add(new Slot((IInventory)paramInventoryPlayer, b, 8 + b * 18, 142));
          b++;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      for (Slot slot : this.c)
        func_75146_a(slot); 
      for (Slot slot : arrayList)
        func_75146_a(slot); 
    } 
  }
  
  public ItemStack func_82846_b(EntityPlayer paramEntityPlayer, int paramInt) {
    ItemStack itemStack = ItemStack.field_190927_a;
    Slot slot = this.field_75151_b.get(paramInt);
    try {
      if (slot != null && slot.func_75216_d()) {
        ItemStack itemStack1 = slot.func_75211_c();
        itemStack = itemStack1.func_77946_l();
        int i = this.field_75151_b.size() - paramEntityPlayer.field_71071_by.field_70462_a.size();
        try {
          if (paramInt < i) {
            try {
              if (!func_75135_a(itemStack1, i, this.field_75151_b.size(), true))
                return ItemStack.field_190927_a; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            try {
              if (!func_75135_a(itemStack1, 0, i, false))
                return ItemStack.field_190927_a; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          if (itemStack1.func_190916_E() == 0) {
            slot.func_75215_d(ItemStack.field_190927_a);
          } else {
            slot.func_75218_e();
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        slot.func_190901_a(paramEntityPlayer, itemStack1);
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return itemStack;
  }
  
  public void func_75141_a(int paramInt, ItemStack paramItemStack) {
    super.func_75141_a(paramInt, paramItemStack);
  }
  
  public boolean func_75145_c(EntityPlayer paramEntityPlayer) {
    return true;
  }
  
  public void func_75134_a(EntityPlayer paramEntityPlayer) {
    super.func_75134_a(paramEntityPlayer);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\am.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */