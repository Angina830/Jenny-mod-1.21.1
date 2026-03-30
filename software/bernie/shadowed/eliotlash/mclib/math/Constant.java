/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Constant
/*    */   implements IValue
/*    */ {
/*    */   private double value;
/*    */   
/*    */   public Constant(double value) {
/* 12 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 17 */     return this.value;
/*    */   }
/*    */   
/*    */   public void set(double value) {
/* 21 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return String.valueOf(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */