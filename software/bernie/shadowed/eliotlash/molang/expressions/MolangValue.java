/*    */ package software.bernie.shadowed.eliotlash.molang.expressions;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*    */ import software.bernie.shadowed.eliotlash.molang.MolangParser;
/*    */ 
/*    */ public class MolangValue
/*    */   extends MolangExpression {
/*    */   public IValue value;
/*    */   public boolean returns;
/*    */   
/*    */   public MolangValue(MolangParser context, IValue value) {
/* 14 */     super(context);
/*    */     
/* 16 */     this.value = value;
/*    */   }
/*    */   
/*    */   public MolangExpression addReturn() {
/* 20 */     this.returns = true;
/*    */     
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 27 */     return this.value.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return (this.returns ? "return " : "") + this.value.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement toJson() {
/* 37 */     if (this.value instanceof software.bernie.shadowed.eliotlash.mclib.math.Constant) {
/* 38 */       return (JsonElement)new JsonPrimitive(Double.valueOf(this.value.get()));
/*    */     }
/*    */     
/* 41 */     return super.toJson();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\molang\expressions\MolangValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */