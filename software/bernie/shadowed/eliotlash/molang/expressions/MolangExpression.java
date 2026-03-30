/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.Operation;
/*    */ import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public abstract class MolangExpression
/*    */   implements IValue {
/*    */   public MolangParser context;
/*    */   
/*    */   public static boolean isZero(MolangExpression expression) {
/* 14 */     return isConstant(expression, 0.0D);
/*    */   }
/*    */   
/*    */   public static boolean isOne(MolangExpression expression) {
/* 18 */     return isConstant(expression, 1.0D);
/*    */   }
/*    */   
/*    */   public static boolean isConstant(MolangExpression expression, double x) {
/* 22 */     if (expression instanceof MolangValue) {
/* 23 */       MolangValue value = (MolangValue)expression;
/*    */       
/* 25 */       return (value.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant && Operation.equals(value.value.get(), x));
/*    */     } 
/*    */     
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isExpressionConstant(MolangExpression expression) {
/* 32 */     if (expression instanceof MolangValue) {
/* 33 */       MolangValue value = (MolangValue)expression;
/*    */       
/* 35 */       return value.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant;
/*    */     } 
/*    */     
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public MolangExpression(MolangParser context) {
/* 42 */     this.context = context;
/*    */   }
/*    */   
/*    */   public JsonElement toJson() {
/* 46 */     return (JsonElement)new JsonPrimitive(toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\molang\expressions\MolangExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */