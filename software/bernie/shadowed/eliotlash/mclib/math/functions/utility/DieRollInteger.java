/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
/*    */ 
/*    */ import java.util.Random;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class DieRollInteger
/*    */   extends Function {
/*    */   public DieRollInteger(IValue[] values, String name) throws Exception {
/* 10 */     super(values, name);
/*    */     
/* 12 */     this.random = new Random();
/*    */   }
/*    */   public Random random;
/*    */   
/*    */   public int getRequiredArguments() {
/* 17 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     double i = 0.0D;
/* 23 */     double total = 0.0D;
/* 24 */     while (i < getArg(0))
/* 25 */       total += Math.round(getArg(1) + Math.random() * (getArg(2) - getArg(1))); 
/* 26 */     return total;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\function\\utility\DieRollInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */