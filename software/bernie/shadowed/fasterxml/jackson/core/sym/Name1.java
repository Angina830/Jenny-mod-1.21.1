/*    */ package software.bernie.shadowed.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Name1
/*    */   extends Name
/*    */ {
/* 14 */   private static final Name1 EMPTY = new Name1("", 0, 0);
/*    */   private final int q;
/*    */   
/*    */   Name1(String name, int hash, int quad) {
/* 18 */     super(name, hash);
/* 19 */     this.q = quad;
/*    */   }
/*    */   public static Name1 getEmptyName() {
/* 22 */     return EMPTY;
/*    */   }
/* 24 */   public boolean equals(int quad) { return (quad == this.q); }
/* 25 */   public boolean equals(int quad1, int quad2) { return (quad1 == this.q && quad2 == 0); } public boolean equals(int q1, int q2, int q3) {
/* 26 */     return false;
/*    */   } public boolean equals(int[] quads, int qlen) {
/* 28 */     return (qlen == 1 && quads[0] == this.q);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\sym\Name1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */