/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.rounding;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Trunc extends Function {
/*    */   public Trunc(IValue[] values, String name) throws Exception {
/*  8 */     super(values, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 13 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 18 */     double value = getArg(0);
/*    */     
/* 20 */     return (value < 0.0D) ? Math.ceil(value) : Math.floor(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\functions\rounding\Trunc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */