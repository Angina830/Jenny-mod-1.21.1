/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.StringJoiner;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.Variable;
/*    */ import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public class MolangMultiStatement
/*    */   extends MolangExpression {
/* 13 */   public List<MolangExpression> expressions = new ArrayList<>();
/* 14 */   public Map<String, Variable> locals = new HashMap<>();
/*    */   
/*    */   public MolangMultiStatement(MolangParser context) {
/* 17 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 22 */     double value = 0.0D;
/*    */     
/* 24 */     for (MolangExpression expression : this.expressions) {
/* 25 */       value = expression.get();
/*    */     }
/*    */     
/* 28 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     StringJoiner builder = new StringJoiner("; ");
/*    */     
/* 35 */     for (MolangExpression expression : this.expressions) {
/* 36 */       builder.add(expression.toString());
/*    */       
/* 38 */       if (expression instanceof MolangValue && ((MolangValue)expression).returns) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 43 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\molang\expressions\MolangMultiStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */