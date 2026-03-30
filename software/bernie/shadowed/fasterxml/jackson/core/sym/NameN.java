/*    */ package software.bernie.shadowed.fasterxml.jackson.core.sym;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ public final class NameN
/*    */   extends Name
/*    */ {
/*    */   private final int q1;
/*    */   private final int q2;
/*    */   private final int q3;
/*    */   private final int q4;
/*    */   private final int qlen;
/*    */   private final int[] q;
/*    */   
/*    */   NameN(String name, int hash, int q1, int q2, int q3, int q4, int[] quads, int quadLen) {
/* 17 */     super(name, hash);
/* 18 */     this.q1 = q1;
/* 19 */     this.q2 = q2;
/* 20 */     this.q3 = q3;
/* 21 */     this.q4 = q4;
/* 22 */     this.q = quads;
/* 23 */     this.qlen = quadLen;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NameN construct(String name, int hash, int[] q, int qlen) {
/*    */     int[] buf;
/* 31 */     if (qlen < 4) {
/* 32 */       throw new IllegalArgumentException();
/*    */     }
/* 34 */     int q1 = q[0];
/* 35 */     int q2 = q[1];
/* 36 */     int q3 = q[2];
/* 37 */     int q4 = q[3];
/*    */     
/* 39 */     int rem = qlen - 4;
/*    */ 
/*    */ 
/*    */     
/* 43 */     if (rem > 0) {
/* 44 */       buf = Arrays.copyOfRange(q, 4, qlen);
/*    */     } else {
/* 46 */       buf = null;
/*    */     } 
/* 48 */     return new NameN(name, hash, q1, q2, q3, q4, buf, qlen);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(int quad) {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int quad1, int quad2) {
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int quad1, int quad2, int quad3) {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int[] quads, int len) {
/* 66 */     if (len != this.qlen) return false;
/*    */ 
/*    */     
/* 69 */     if (quads[0] != this.q1) return false; 
/* 70 */     if (quads[1] != this.q2) return false; 
/* 71 */     if (quads[2] != this.q3) return false; 
/* 72 */     if (quads[3] != this.q4) return false;
/*    */     
/* 74 */     switch (len) {
/*    */       default:
/* 76 */         return _equals2(quads);
/*    */       case 8:
/* 78 */         if (quads[7] != this.q[3]) return false; 
/*    */       case 7:
/* 80 */         if (quads[6] != this.q[2]) return false; 
/*    */       case 6:
/* 82 */         if (quads[5] != this.q[1]) return false; 
/*    */       case 5:
/* 84 */         if (quads[4] != this.q[0]) return false;  break;
/*    */       case 4:
/*    */         break;
/* 87 */     }  return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private final boolean _equals2(int[] quads) {
/* 92 */     int end = this.qlen - 4;
/* 93 */     for (int i = 0; i < end; i++) {
/* 94 */       if (quads[i + 4] != this.q[i]) {
/* 95 */         return false;
/*    */       }
/*    */     } 
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\sym\NameN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */