/*    */ package software.bernie.shadowed.eliotlash.mclib.math.functions;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Function
/*    */   implements IValue
/*    */ {
/*    */   protected IValue[] args;
/*    */   protected String name;
/*    */   
/*    */   public Function(IValue[] values, String name) throws Exception {
/* 16 */     if (values.length < getRequiredArguments()) {
/* 17 */       String message = String.format("Function '%s' requires at least %s arguments. %s are given!", new Object[] {
/* 18 */             getName(), Integer.valueOf(getRequiredArguments()), Integer.valueOf(values.length)
/*    */           });
/* 20 */       throw new Exception(message);
/*    */     } 
/*    */     
/* 23 */     this.args = values;
/* 24 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getArg(int index) {
/* 31 */     if (index < 0 || index >= this.args.length) {
/* 32 */       return 0.0D;
/*    */     }
/*    */     
/* 35 */     return this.args[index].get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     String args = "";
/*    */     
/* 42 */     for (int i = 0; i < this.args.length; i++) {
/* 43 */       args = args + this.args[i].toString();
/*    */       
/* 45 */       if (i < this.args.length - 1) {
/* 46 */         args = args + ", ";
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return getName() + "(" + args + ")";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 57 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredArguments() {
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\functions\Function.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */