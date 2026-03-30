package com.schnurritv.sexmod;

import net.minecraft.util.math.Vec3i;

public enum x {
  LIGHT_GREEN(213, 239, 150),
  MEDIUM_GREEN(189, 165, 91),
  DARK_GREEN(160, 183, 135),
  LIGHT_YELLOW(234, 176, 102),
  LIGHT_BLUE(187, 203, 252);
  
  private final Vec3i b;
  
  x(int paramInt1, int paramInt2, int paramInt3) {
    this.b = new Vec3i(paramInt1, paramInt2, paramInt3);
  }
  
  public Vec3i a() {
    return this.b;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\x.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */