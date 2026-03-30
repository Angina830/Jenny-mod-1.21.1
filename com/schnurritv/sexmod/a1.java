package com.schnurritv.sexmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public abstract class a1 extends Q implements IInventory {
  public static final DataParameter<Boolean> F = EntityDataManager.func_187226_a(Q.class, DataSerializers.field_187198_h).func_187156_b().func_187161_a(111);
  
  public ItemStackHandler G = new ItemStackHandler(27);
  
  protected a1(World paramWorld) {
    super(paramWorld);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.m.func_187214_a(F, Boolean.valueOf(false));
  }
  
  public int func_70302_i_() {
    return 27;
  }
  
  public boolean func_191420_l() {
    return false;
  }
  
  public ItemStack func_70301_a(int paramInt) {
    try {
      if (paramInt >= this.G.getSlots())
        return ItemStack.field_190927_a; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return this.G.getStackInSlot(paramInt);
  }
  
  public ItemStack func_70298_a(int paramInt1, int paramInt2) {
    return this.G.extractItem(paramInt1, paramInt2, false);
  }
  
  public ItemStack func_70304_b(int paramInt) {
    return this.G.extractItem(paramInt, this.G.getStackInSlot(paramInt).func_190916_E(), false);
  }
  
  public void func_70299_a(int paramInt, ItemStack paramItemStack) {
    this.G.setStackInSlot(paramInt, paramItemStack);
  }
  
  public int func_70297_j_() {
    return 64;
  }
  
  public void func_70296_d() {}
  
  public boolean func_70300_a(EntityPlayer paramEntityPlayer) {
    return true;
  }
  
  public void func_174889_b(EntityPlayer paramEntityPlayer) {}
  
  public void func_174886_c(EntityPlayer paramEntityPlayer) {}
  
  public boolean func_94041_b(int paramInt, ItemStack paramItemStack) {
    return true;
  }
  
  public int func_174887_a_(int paramInt) {
    return paramInt;
  }
  
  public void func_174885_b(int paramInt1, int paramInt2) {}
  
  public int func_174890_g() {
    return 0;
  }
  
  public void func_174888_l() {}
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */