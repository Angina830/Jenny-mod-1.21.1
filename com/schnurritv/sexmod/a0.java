package com.schnurritv.sexmod;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class a0 extends ItemFishingRod {
  public static final a0 a = new a0();
  
  public a0() {
    func_77656_e(64);
    func_77625_d(1);
    func_185043_a(new ResourceLocation("cast"), new a());
  }
  
  public static void a() {
    a.setRegistryName("sexmod", "luna_rod");
    a.func_77655_b("luna_rod");
    MinecraftForge.EVENT_BUS.register(a0.class);
  }
  
  @SubscribeEvent
  public static void a(RegistryEvent.Register<Item> paramRegister) {
    paramRegister.getRegistry().register((IForgeRegistryEntry)a);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void a(ModelRegistryEvent paramModelRegistryEvent) {
    ModelLoader.setCustomModelResourceLocation((Item)a, 0, new ModelResourceLocation("fishing_rod"));
  }
  
  public ActionResult<ItemStack> a(World paramWorld, aI paramaI, EnumHand paramEnumHand) {
    ItemStack itemStack = paramaI.func_184586_b(paramEnumHand);
    if (paramaI.W != null) {
      int i = paramaI.W.b();
      itemStack.func_77972_a(i, (EntityLivingBase)paramaI);
      paramaI.func_184609_a(paramEnumHand);
      paramWorld.func_184148_a((EntityPlayer)null, paramaI.field_70165_t, paramaI.field_70163_u, paramaI.field_70161_v, SoundEvents.field_193780_J, SoundCategory.NEUTRAL, 1.0F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
    } else {
      paramWorld.func_184148_a((EntityPlayer)null, paramaI.field_70165_t, paramaI.field_70163_u, paramaI.field_70161_v, SoundEvents.field_187612_G, SoundCategory.NEUTRAL, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
      if (!paramWorld.field_72995_K) {
        m.b = paramaI;
        double d = paramaI.func_174791_d().func_72438_d(new Vec3d(paramaI.ad.func_177958_n(), paramaI.ad.func_177956_o(), paramaI.ad.func_177952_p()));
        System.out.println("chosenFishingSpot: " + paramaI.ad.toString());
        System.out.println("ROD_THROWING_STRENGTH: " + (d * aI.aj));
        m m = new m(paramWorld, paramaI, d * aI.aj);
        int i = EnchantmentHelper.func_191528_c(itemStack);
        try {
          if (i > 0)
            m.a(i); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        int j = EnchantmentHelper.func_191529_b(itemStack);
        try {
          if (j > 0)
            m.b(j); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        paramWorld.func_72838_d(m);
      } 
      paramaI.func_184609_a(paramEnumHand);
    } 
    return new ActionResult(EnumActionResult.SUCCESS, itemStack);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  class a implements IItemPropertyGetter {
    @SideOnly(Side.CLIENT)
    public float func_185085_a(ItemStack param1ItemStack, @Nullable World param1World, @Nullable EntityLivingBase param1EntityLivingBase) {
      try {
        if (param1EntityLivingBase == null)
          return 0.0F; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (!(param1EntityLivingBase instanceof aI))
          return 0.0F; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
      
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      return ((Boolean)param1EntityLivingBase.func_184212_Q().func_187225_a(aI.at)).booleanValue() ? 1.0F : 0.0F;
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */