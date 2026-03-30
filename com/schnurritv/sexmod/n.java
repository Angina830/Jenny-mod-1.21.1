package com.schnurritv.sexmod;

public enum n {
  JENNY((Class)aX.class, 177013, (Class)ac.class, 12388645),
  ELLIE((Class)ae.class, 228922, (Class)aP.class, 46348348),
  BIA((Class)T.class, 230053, (Class)X.class, 65456415),
  SLIME((Class)aF.class, 168597, (Class)a2.class, 54816432),
  BEE((Class)aO.class, 4663354, (Class)W.class, 48648638),
  ALLIE((Class)R.class, 5614613, (Class)V.class, 64867483),
  LUNA((Class)aI.class, 6816463, (Class)ar.class, 681234824),
  KOBOLD((Class)aD.class, 5648456),
  GOBLIN((Class)ag.class, 4567275);
  
  public final int npcID;
  
  public final int playerID;
  
  public final Class<? extends Q> npcClass;
  
  public final Class<? extends U> playerClass;
  
  public final boolean isNpcOnly;
  
  public final int editorID;
  
  n(Class<? extends Q> paramClass, int paramInt1, Class<? extends U> paramClass1, int paramInt2) {
    this.npcID = paramInt1;
    this.playerID = paramInt2;
    this.npcClass = paramClass;
    this.playerClass = paramClass1;
    this.isNpcOnly = false;
    this.editorID = bY.h++;
  }
  
  n(Class<? extends Q> paramClass, int paramInt1) {
    this.npcID = paramInt1;
    this.npcClass = paramClass;
    this.isNpcOnly = true;
    this.editorID = bY.h++;
    this.playerClass = null;
    this.playerID = 0;
  }
  
  public static n a(Q paramQ) {
    try {
      if (paramQ == null)
        return null; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    Class<?> clazz = paramQ.getClass();
    for (n n1 : values()) {
      try {
        if (clazz.equals(n1.npcClass))
          return n1; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (clazz.equals(n1.playerClass))
          return n1; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
    } 
    return null;
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */