/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Group
/*    */   implements IValue
/*    */ {
/*    */   private IValue value;
/*    */   
/*    */   public Group(IValue value) {
/* 13 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 18 */     return this.value.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 23 */     return "(" + this.value.toString() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Group.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */