/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;
/*    */ 
/*    */ public class Lerp extends Function {
/*    */   public Lerp(IValue[] values, String name) throws Exception {
/*  9 */     super(values, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 14 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 19 */     return Interpolations.lerp(getArg(0), getArg(1), getArg(2));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\function\\utility\Lerp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */