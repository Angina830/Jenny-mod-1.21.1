/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ternary
/*    */   implements IValue
/*    */ {
/*    */   public IValue condition;
/*    */   public IValue ifTrue;
/*    */   public IValue ifFalse;
/*    */   
/*    */   public Ternary(IValue condition, IValue ifTrue, IValue ifFalse) {
/* 15 */     this.condition = condition;
/* 16 */     this.ifTrue = ifTrue;
/* 17 */     this.ifFalse = ifFalse;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     return (this.condition.get() != 0.0D) ? this.ifTrue.get() : this.ifFalse.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 27 */     return this.condition.toString() + " ? " + this.ifTrue.toString() + " : " + this.ifFalse.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Ternary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */