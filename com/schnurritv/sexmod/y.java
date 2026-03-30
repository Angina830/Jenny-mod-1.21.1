package com.schnurritv.sexmod;

public enum y {
  HEAD("customHat"),
  TOP("customTop"),
  LEGS("customLegs"),
  FOOT_L("customFootL"),
  FOOT_R("customFootR"),
  HAND_L("customHandL"),
  HAND_R("customHandR"),
  CUSTOM_BONE;
  
  public static final String SEPARATOR = "#";
  
  public int buttonIDPlus;
  
  public int buttonIDMinus;
  
  public String boneName;
  
  y(String paramString1) {
    this.boneName = paramString1;
    this.buttonIDPlus = ++bY.e;
    this.buttonIDMinus = ++bY.e;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\y.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */