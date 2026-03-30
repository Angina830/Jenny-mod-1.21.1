package com.schnurritv.sexmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class bb {
  public static void a() {
    a("jenny", (Class)aX.class, n.JENNY.npcID, 3286592, 12655237);
    a("ellie", (Class)ae.class, n.ELLIE.npcID, 1447446, 9961472);
    a("slime", (Class)aF.class, n.SLIME.npcID, 13167780, 8244330);
    a("bia", (Class)T.class, n.BIA.npcID, 7488816, 7254603);
    a("bee", (Class)aO.class, n.BEE.npcID, 16701032, 4400155);
    a("luna", (Class)aI.class, n.LUNA.npcID, 7881787, 7940422);
    b("allie", (Class)R.class, n.ALLIE.npcID);
    b("kobold", (Class)aD.class, n.KOBOLD.npcID);
    b("kobold_egg", (Class)bM.class, 4674237);
    a("goblin", (Class)ag.class, n.GOBLIN.npcID, 39424, 19456);
    b("custom_model", (Class)bI.class, 6281823);
    a("player_jenny", (Class)ac.class, n.JENNY.playerID);
    a("player_ellie", (Class)aP.class, n.ELLIE.playerID);
    a("player_slime", (Class)a2.class, n.SLIME.playerID);
    a("player_bia", (Class)X.class, n.BIA.playerID);
    a("player_bee", (Class)W.class, n.BEE.playerID);
    a("player_allie", (Class)V.class, n.ALLIE.playerID);
    a("player_luna", (Class)ar.class, n.LUNA.playerID);
    a("player_kobold", (Class)Z.class, 544512455);
    EntityRegistry.registerModEntity(new ResourceLocation("sexmod:friendly_slime"), bk.class, "friendly_slime", 5548484, Main.instance, 50, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation("sexmod:luna_hook"), m.class, "luna_hook", 4768742, Main.instance, 50, 1, true);
    EntityRegistry.addSpawn(aF.class, 10, 1, 1, EnumCreatureType.CREATURE, new Biome[] { Biomes.field_76780_h, Biomes.field_150599_m });
    EntityRegistry.addSpawn(aO.class, 5, 1, 1, EnumCreatureType.CREATURE, new Biome[] { Biomes.field_76767_f, Biomes.field_76785_t });
  }
  
  private static void a(String paramString, Class<? extends Entity> paramClass, int paramInt) {
    EntityRegistry.registerModEntity(new ResourceLocation("sexmod:" + paramString), paramClass, paramString, paramInt, Main.instance, 100, 1, false);
  }
  
  private static void a(String paramString, Class<? extends Entity> paramClass, int paramInt1, int paramInt2, int paramInt3) {
    EntityRegistry.registerModEntity(new ResourceLocation("sexmod:" + paramString), paramClass, paramString, paramInt1, Main.instance, 50, 1, true, paramInt2, paramInt3);
  }
  
  private static void b(String paramString, Class<? extends Entity> paramClass, int paramInt) {
    EntityRegistry.registerModEntity(new ResourceLocation("sexmod:" + paramString), paramClass, "allie", paramInt, Main.instance, 50, 1, true);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */