/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerators;
/*    */ 
/*    */ 
/*    */ public class PropertyBasedObjectIdGenerator
/*    */   extends ObjectIdGenerators.PropertyGenerator
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public PropertyBasedObjectIdGenerator(Class<?> scope) {
/* 13 */     super(scope);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object generateId(Object forPojo) {
/* 18 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectIdGenerator<Object> forScope(Class<?> scope) {
/* 23 */     return (scope == this._scope) ? (ObjectIdGenerator<Object>)this : (ObjectIdGenerator<Object>)new PropertyBasedObjectIdGenerator(scope);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectIdGenerator<Object> newForSerialization(Object context) {
/* 28 */     return (ObjectIdGenerator<Object>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectIdGenerator.IdKey key(Object key) {
/* 33 */     if (key == null) {
/* 34 */       return null;
/*    */     }
/*    */     
/* 37 */     return new ObjectIdGenerator.IdKey(getClass(), this._scope, key);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\PropertyBasedObjectIdGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */