/*    */ package software.bernie.shadowed.eliotlash.mclib.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Variable
/*    */   implements IValue
/*    */ {
/*    */   private String name;
/*    */   private double value;
/*    */   
/*    */   public Variable(String name, double value) {
/* 18 */     this.name = name;
/* 19 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(double value) {
/* 26 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 31 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 35 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Variable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */