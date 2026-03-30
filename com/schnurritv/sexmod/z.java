package com.schnurritv.sexmod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class z extends Item implements IAnimatable {
  public static final z a = new z();
  
  private final AnimationFactory b = new AnimationFactory(this);
  
  public z() {
    func_77637_a(CreativeTabs.field_78040_i);
    this.field_77777_bU = 1;
  }
  
  public static void a() {
    a.setRegistryName("sexmod", "dragon_staff");
    a.func_77655_b("dragon_staff");
    MinecraftForge.EVENT_BUS.register(z.class);
  }
  
  public ActionResult<ItemStack> func_77659_a(World paramWorld, EntityPlayer paramEntityPlayer, EnumHand paramEnumHand) {
    return new ActionResult(EnumActionResult.FAIL, paramEntityPlayer.func_184586_b(paramEnumHand));
  }
  
  @SubscribeEvent
  public static void a(RegistryEvent.Register<Item> paramRegister) {
    paramRegister.getRegistry().register((IForgeRegistryEntry)a);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void a(ModelRegistryEvent paramModelRegistryEvent) {
    ModelLoader.setCustomModelResourceLocation(a, 0, new ModelResourceLocation("sexmod:dragon_staff"));
    a.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new bv());
  }
  
  public void registerControllers(AnimationData paramAnimationData) {}
  
  public AnimationFactory getFactory() {
    return this.b;
  }
  
  public static class a {
    @SubscribeEvent
    public void a(PlayerInteractEvent.RightClickItem param1RightClickItem) {
      World world = param1RightClickItem.getWorld();
      try {
        if (!world.field_72995_K)
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      EntityPlayer entityPlayer = param1RightClickItem.getEntityPlayer();
      try {
        if (entityPlayer.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() != z.a)
          try {
            if (entityPlayer.func_184586_b(EnumHand.OFF_HAND).func_77973_b() != z.a)
              return; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (aD.af.isEmpty())
          return; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      a();
    }
    
    @SideOnly(Side.CLIENT)
    void a() {
      Minecraft.func_71410_x().func_147108_a(new ch());
      bn.a.sendToServer(new aL());
    }
    
    @SubscribeEvent
    public void a(PlayerInteractEvent.RightClickBlock param1RightClickBlock) {
      EntityPlayer entityPlayer = param1RightClickBlock.getEntityPlayer();
      try {
        if (entityPlayer.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() != z.a)
          try {
            if (entityPlayer.func_184586_b(EnumHand.OFF_HAND).func_77973_b() != z.a)
              return; 
          } catch (NullPointerException nullPointerException) {
            throw a(null);
          }  
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      Block block = param1RightClickBlock.getWorld().func_180495_p(param1RightClickBlock.getPos()).func_177230_c();
      try {
        if (block instanceof net.minecraft.block.BlockBed) {
          param1RightClickBlock.setCancellationResult(EnumActionResult.FAIL);
          param1RightClickBlock.setResult(Event.Result.DENY);
          param1RightClickBlock.setCanceled(true);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (block instanceof net.minecraft.block.BlockChest) {
          param1RightClickBlock.setCancellationResult(EnumActionResult.FAIL);
          param1RightClickBlock.setResult(Event.Result.DENY);
          param1RightClickBlock.setCanceled(true);
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    }
    
    private static NullPointerException a(NullPointerException param1NullPointerException) {
      return param1NullPointerException;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\z.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */