/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeBindings;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface TypeResolutionContext
/*    */ {
/*    */   JavaType resolveType(Type paramType);
/*    */   
/*    */   public static class Basic
/*    */     implements TypeResolutionContext
/*    */   {
/*    */     private final TypeFactory _typeFactory;
/*    */     private final TypeBindings _bindings;
/*    */     
/*    */     public Basic(TypeFactory tf, TypeBindings b) {
/* 25 */       this._typeFactory = tf;
/* 26 */       this._bindings = b;
/*    */     }
/*    */ 
/*    */     
/*    */     public JavaType resolveType(Type type) {
/* 31 */       return this._typeFactory.constructType(type, this._bindings);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\TypeResolutionContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */