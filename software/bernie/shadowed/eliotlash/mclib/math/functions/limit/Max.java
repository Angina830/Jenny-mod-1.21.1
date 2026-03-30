/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.limit;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Max extends Function {
/*    */   public Max(IValue[] values, String name) throws Exception {
/*  8 */     super(values, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 13 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 18 */     return Math.max(getArg(0), getArg(1));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\functions\limit\Max.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */