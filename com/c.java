package com;

import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class c extends WorldClient {
  public Biome getBiomeForCoordsBody(BlockPos paramBlockPos) {
    return (Biome)new BiomePlains(false, (new Biome.BiomeProperties("Plains")).func_185398_c(0.125F).func_185400_d(0.05F).func_185400_d(0.8F).func_185395_b(0.4F));
  }
  
  public void func_175685_c(BlockPos paramBlockPos, Block paramBlock, boolean paramBoolean) {
    super.func_175685_c(paramBlockPos, paramBlock, paramBoolean);
  }
  
  public void markAndNotifyBlock(BlockPos paramBlockPos, Chunk paramChunk, IBlockState paramIBlockState1, IBlockState paramIBlockState2, int paramInt) {}
  
  public float getSunBrightnessFactor(float paramFloat) {
    return 1.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getSunBrightnessBody(float paramFloat) {
    return 1.0F;
  }
  
  public void updateWeatherBody() {}
  
  public boolean canBlockFreezeBody(BlockPos paramBlockPos, boolean paramBoolean) {
    return false;
  }
  
  public boolean canSnowAtBody(BlockPos paramBlockPos, boolean paramBoolean) {
    return false;
  }
  
  public c() {
    super(new b(Minecraft.func_71410_x()), new WorldSettings(0L, GameType.SURVIVAL, false, false, WorldType.field_77138_c), 0, EnumDifficulty.HARD, new Profiler());
    this.field_73011_w.func_76558_a((World)this);
  }
  
  public boolean canMineBlockBody(EntityPlayer paramEntityPlayer, BlockPos paramBlockPos) {
    return false;
  }
  
  public boolean isSideSolid(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (paramBlockPos.func_177956_o() <= 63);
  }
  
  public boolean isSideSolid(BlockPos paramBlockPos, EnumFacing paramEnumFacing, boolean paramBoolean) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return (paramBlockPos.func_177956_o() <= 63);
  }
  
  public int countEntities(EnumCreatureType paramEnumCreatureType, boolean paramBoolean) {
    return 0;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  protected static class a extends WorldProvider {
    public void setDimension(int param1Int) {}
    
    public String getSaveFolder() {
      return null;
    }
    
    public BlockPos getRandomizedSpawnPoint() {
      return new BlockPos(0, 64, 0);
    }
    
    public boolean shouldMapSpin(String param1String, double param1Double1, double param1Double2, double param1Double3) {
      return false;
    }
    
    public int getRespawnDimension(EntityPlayerMP param1EntityPlayerMP) {
      return 0;
    }
    
    public Biome getBiomeForCoords(BlockPos param1BlockPos) {
      return (Biome)new BiomePlains(false, (new Biome.BiomeProperties("Plains")).func_185398_c(0.125F).func_185400_d(0.05F).func_185400_d(0.8F).func_185395_b(0.4F));
    }
    
    public boolean isDaytime() {
      return true;
    }
    
    public void setAllowedSpawnTypes(boolean param1Boolean1, boolean param1Boolean2) {}
    
    public void calculateInitialWeather() {}
    
    public void updateWeather() {}
    
    public boolean canBlockFreeze(BlockPos param1BlockPos, boolean param1Boolean) {
      return false;
    }
    
    public boolean canSnowAt(BlockPos param1BlockPos, boolean param1Boolean) {
      return false;
    }
    
    public long getSeed() {
      return 1L;
    }
    
    public long getWorldTime() {
      return 1L;
    }
    
    public void setWorldTime(long param1Long) {}
    
    public boolean canMineBlock(EntityPlayer param1EntityPlayer, BlockPos param1BlockPos) {
      return false;
    }
    
    public boolean isBlockHighHumidity(BlockPos param1BlockPos) {
      return false;
    }
    
    public int getHeight() {
      return 256;
    }
    
    public int getActualHeight() {
      return 256;
    }
    
    public void resetRainAndThunder() {}
    
    public boolean canDoLightning(Chunk param1Chunk) {
      return false;
    }
    
    public boolean canDoRainSnowIce(Chunk param1Chunk) {
      return false;
    }
    
    public DimensionType func_186058_p() {
      return DimensionType.OVERWORLD;
    }
    
    public BlockPos getSpawnPoint() {
      return new BlockPos(0, 64, 0);
    }
  }
  
  protected static class d implements ISaveHandler {
    @Nullable
    public WorldInfo func_75757_d() {
      return null;
    }
    
    public void func_75762_c() throws MinecraftException {}
    
    public IChunkLoader func_75763_a(WorldProvider param1WorldProvider) {
      return null;
    }
    
    public void func_75755_a(WorldInfo param1WorldInfo, NBTTagCompound param1NBTTagCompound) {}
    
    public void func_75761_a(WorldInfo param1WorldInfo) {}
    
    public IPlayerFileData func_75756_e() {
      return null;
    }
    
    public void func_75759_a() {}
    
    public File func_75765_b() {
      return null;
    }
    
    public File func_75758_b(String param1String) {
      return null;
    }
    
    public TemplateManager func_186340_h() {
      return new TemplateManager("", Minecraft.func_71410_x().func_184126_aj());
    }
  }
  
  protected static class b implements IChunkProvider {
    @Nullable
    public Chunk func_186026_b(int param1Int1, int param1Int2) {
      return null;
    }
    
    public Chunk func_186025_d(int param1Int1, int param1Int2) {
      return null;
    }
    
    public boolean func_73156_b() {
      return false;
    }
    
    public String func_73148_d() {
      return null;
    }
    
    public boolean func_191062_e(int param1Int1, int param1Int2) {
      return true;
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */