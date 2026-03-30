/*    */ package software.bernie.shadowed.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Name2
/*    */   extends Name
/*    */ {
/*    */   private final int q1;
/*    */   private final int q2;
/*    */   
/*    */   Name2(String name, int hash, int quad1, int quad2) {
/* 17 */     super(name, hash);
/* 18 */     this.q1 = quad1;
/* 19 */     this.q2 = quad2;
/*    */   }
/*    */   
/*    */   public boolean equals(int quad) {
/* 23 */     return false;
/*    */   }
/*    */   public boolean equals(int quad1, int quad2) {
/* 26 */     return (quad1 == this.q1 && quad2 == this.q2);
/*    */   } public boolean equals(int quad1, int quad2, int q3) {
/* 28 */     return false;
/*    */   }
/*    */   public boolean equals(int[] quads, int qlen) {
/* 31 */     return (qlen == 2 && quads[0] == this.q1 && quads[1] == this.q2);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\sym\Name2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */