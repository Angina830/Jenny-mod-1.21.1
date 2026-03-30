/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions.utility;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*    */ 
/*    */ public class Random extends Function {
/*    */   public java.util.Random random;
/*    */   
/*    */   public Random(IValue[] values, String name) throws Exception {
/* 10 */     super(values, name);
/*    */     
/* 12 */     this.random = new java.util.Random();
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 17 */     double random = 0.0D;
/*    */     
/* 19 */     if (this.args.length >= 3) {
/* 20 */       this.random.setSeed((long)getArg(2));
/* 21 */       random = this.random.nextDouble();
/*    */     } else {
/* 23 */       random = Math.random();
/*    */     } 
/*    */     
/* 26 */     if (this.args.length >= 2) {
/* 27 */       double a = getArg(0);
/* 28 */       double b = getArg(1);
/*    */       
/* 30 */       double min = Math.min(a, b);
/* 31 */       double max = Math.max(a, b);
/*    */       
/* 33 */       random = random * (max - min) + min;
/* 34 */     } else if (this.args.length >= 1) {
/* 35 */       random *= getArg(0);
/*    */     } 
/*    */     
/* 38 */     return random;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\function\\utility\Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */