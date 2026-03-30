package com.schnurritv.sexmod;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class af {
  @SideOnly(Side.CLIENT)
  public static BufferedImage a(UUID paramUUID) throws IOException {
    try {
      URL uRL1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + paramUUID.toString().replace("-", ""));
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL1.openStream()));
      String str1 = bufferedReader.lines().collect(Collectors.joining());
      int i = str1.indexOf("\"value\" : ");
      int j = i + 11;
      StringBuilder stringBuilder1 = new StringBuilder();
      byte b1 = 0;
      try {
        while (str1.charAt(j + b1) != '"') {
          stringBuilder1.append(str1.charAt(j + b1));
          b1++;
        } 
      } catch (Exception exception) {
        throw a(null);
      } 
      String str2 = new String(Base64.getDecoder().decode(stringBuilder1.toString()));
      int k = str2.indexOf("\"url\" : ");
      int m = k + 9;
      StringBuilder stringBuilder2 = new StringBuilder();
      byte b2 = 0;
      try {
        while (str2.charAt(m + b2) != '"') {
          stringBuilder2.append(str2.charAt(m + b2));
          b2++;
        } 
      } catch (Exception exception) {
        throw a(null);
      } 
      URL uRL2 = new URL(stringBuilder2.toString());
      return ImageIO.read(uRL2);
    } catch (Exception exception) {
      return ImageIO.read(Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("sexmod", "textures/player/steve.png")).func_110527_b());
    } 
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\af.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */