/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.Variable;
/*    */ import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public class MolangAssignment extends MolangExpression {
/*    */   public Variable variable;
/*    */   public IValue expression;
/*    */   
/*    */   public MolangAssignment(MolangParser context, Variable variable, IValue expression) {
/* 12 */     super(context);
/*    */     
/* 14 */     this.variable = variable;
/* 15 */     this.expression = expression;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 20 */     double value = this.expression.get();
/*    */     
/* 22 */     this.variable.set(value);
/*    */     
/* 24 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return this.variable.getName() + " = " + this.expression.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\molang\expressions\MolangAssignment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */