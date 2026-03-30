/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Negate
/*    */   implements IValue
/*    */ {
/*    */   public IValue value;
/*    */   
/*    */   public Negate(IValue value) {
/* 12 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 17 */     return (this.value.get() == 0.0D) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "!" + this.value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Negate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */