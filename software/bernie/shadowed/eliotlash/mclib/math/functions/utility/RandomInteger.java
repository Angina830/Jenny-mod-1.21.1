/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
/*    */ 
/*    */ import java.util.Random;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class RandomInteger
/*    */   extends Function {
/*    */   public RandomInteger(IValue[] values, String name) throws Exception {
/* 10 */     super(values, name);
/*    */     
/* 12 */     this.random = new Random();
/*    */   }
/*    */   public Random random;
/*    */   
/*    */   public int getRequiredArguments() {
/* 17 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     double min = Math.ceil(getArg(0));
/* 23 */     double max = Math.floor(getArg(1));
/* 24 */     return Math.floor(Math.random() * (max - min) + min);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\function\\utility\RandomInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */