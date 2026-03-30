/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.classic;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Mod extends Function {
/*    */   public Mod(IValue[] values, String name) throws Exception {
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
/* 18 */     return getArg(0) % getArg(1);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\functions\classic\Mod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */