package com.schnurritv.sexmod;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;

public interface h {
  public static final WorldServer a = FMLCommonHandler.instance().getMinecraftServerInstance().func_71218_a(0);
  
  public static final PlacementSettings b = (new PlacementSettings()).func_186218_a(null).func_186222_a(false).func_186214_a(Mirror.NONE).func_186220_a(Rotation.NONE);
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\h.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */