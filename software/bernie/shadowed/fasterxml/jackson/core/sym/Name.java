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
/*    */ public abstract class Name
/*    */ {
/*    */   protected final String _name;
/*    */   protected final int _hashCode;
/*    */   
/*    */   protected Name(String name, int hashCode) {
/* 17 */     this._name = name;
/* 18 */     this._hashCode = hashCode;
/*    */   }
/*    */   public String getName() {
/* 21 */     return this._name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean equals(int paramInt);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean equals(int paramInt1, int paramInt2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean equals(int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean equals(int[] paramArrayOfint, int paramInt);
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return this._name;
/*    */   } public final int hashCode() {
/* 48 */     return this._hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 53 */     return (o == this);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\sym\Name.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */