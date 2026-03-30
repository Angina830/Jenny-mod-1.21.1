package com.schnurritv.sexmod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class bp extends Container {
  private final IInventory a;
  
  private final int c;
  
  public static List<bp> b = new ArrayList<>();
  
  public UUID d;
  
  public bp(IInventory paramIInventory1, IInventory paramIInventory2, EntityPlayer paramEntityPlayer, UUID paramUUID) {
    this.d = paramUUID;
    b.add(this);
    this.a = paramIInventory2;
    paramIInventory2.func_174889_b(paramEntityPlayer);
    this.c = 3;
    byte b = -18;
    byte b1;
    for (b1 = 0; b1 < 3; b1++) {
      byte b2 = 0;
      try {
        while (b2 < 9) {
          func_75146_a(new Slot(paramIInventory2, b2 + b1 * 9, 8 + b2 * 18, 18 + b1 * 18));
          b2++;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    for (b1 = 0; b1 < 3; b1++) {
      byte b2 = 0;
      try {
        while (b2 < 9) {
          func_75146_a(new Slot(paramIInventory1, b2 + b1 * 9 + 9, 8 + b2 * 18, 103 + b1 * 18 + b));
          b2++;
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    b1 = 0;
    try {
      while (b1 < 9) {
        func_75146_a(new Slot(paramIInventory1, b1, 8 + b1 * 18, 161 + b));
        b1++;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  public boolean func_75145_c(EntityPlayer paramEntityPlayer) {
    return this.a.func_70300_a(paramEntityPlayer);
  }
  
  public ItemStack func_82846_b(EntityPlayer paramEntityPlayer, int paramInt) {
    ItemStack itemStack = ItemStack.field_190927_a;
    Slot slot = this.field_75151_b.get(paramInt);
    try {
      if (slot != null && slot.func_75216_d()) {
        ItemStack itemStack1 = slot.func_75211_c();
        itemStack = itemStack1.func_77946_l();
        try {
          if (paramInt < this.c * 9) {
            try {
              if (!func_75135_a(itemStack1, this.c * 9, this.field_75151_b.size(), true))
                return ItemStack.field_190927_a; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } else {
            try {
              if (!func_75135_a(itemStack1, 0, this.c * 9, false))
                return ItemStack.field_190927_a; 
            } catch (NullPointerException nullPointerException) {
              throw a(null);
            } 
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        try {
          if (itemStack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
          } else {
            slot.func_75218_e();
          } 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return itemStack;
  }
  
  public void func_75134_a(EntityPlayer paramEntityPlayer) {
    super.func_75134_a(paramEntityPlayer);
    this.a.func_174886_c(paramEntityPlayer);
  }
  
  public IInventory a() {
    return this.a;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */