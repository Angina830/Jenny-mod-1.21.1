/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.classic;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ 
/*    */ public class ASin
/*    */   extends Function
/*    */ {
/*    */   public ASin(IValue[] values, String name) throws Exception {
/* 11 */     super(values, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 16 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 21 */     return Math.asin(getArg(0));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\functions\classic\ASin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */