package com.schnurritv.sexmod;

import java.util.UUID;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class a7 extends Item implements IAnimatable {
  private final AnimationFactory b = new AnimationFactory(this);
  
  public static a7 a = new a7();
  
  public a7() {
    func_77625_d(1);
  }
  
  public static void a() {
    a.setRegistryName("sexmod", "kobold_egg_item");
    a.func_77655_b("kobold_egg_item");
    MinecraftForge.EVENT_BUS.register(a7.class);
  }
  
  public void registerControllers(AnimationData paramAnimationData) {}
  
  public AnimationFactory getFactory() {
    return this.b;
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void a(ModelRegistryEvent paramModelRegistryEvent) {
    ModelResourceLocation modelResourceLocation = new ModelResourceLocation("sexmod:kobold_egg_item");
    ModelLoader.setCustomMeshDefinition(a, paramItemStack -> paramModelResourceLocation);
    ModelBakery.registerItemVariants(a, new ResourceLocation[] { (ResourceLocation)modelResourceLocation });
    a.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)new bB());
  }
  
  @SubscribeEvent
  public static void a(RegistryEvent.Register<Item> paramRegister) {
    paramRegister.getRegistry().register((IForgeRegistryEntry)a);
  }
  
  @SubscribeEvent
  public static void a(PlayerInteractEvent.RightClickBlock paramRightClickBlock) {
    World world = paramRightClickBlock.getWorld();
    ItemStack itemStack = paramRightClickBlock.getItemStack();
    Vec3d vec3d = paramRightClickBlock.getHitVec();
    try {
      if (world.field_72995_K)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (itemStack.func_77973_b() != a)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    bM bM = new bM(world);
    bM.func_70107_b(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
    bM.func_184212_Q().func_187227_b(bM.e, EyeAndKoboldColor.getColorByWoolId(itemStack.func_77960_j()).toString());
    NBTTagCompound nBTTagCompound = itemStack.func_77978_p();
    try {
      if (nBTTagCompound != null)
        bM.b = UUID.fromString(nBTTagCompound.func_74779_i("tribeID")); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    world.func_72838_d((Entity)bM);
    itemStack.func_190918_g(1);
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\a7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */