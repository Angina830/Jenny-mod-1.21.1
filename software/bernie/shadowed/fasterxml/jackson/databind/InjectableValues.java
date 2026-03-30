/*    */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InjectableValues
/*    */ {
/*    */   public abstract Object findInjectableValue(Object paramObject1, DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, Object paramObject2) throws JsonMappingException;
/*    */   
/*    */   public static class Std
/*    */     extends InjectableValues
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     protected final Map<String, Object> _values;
/*    */     
/*    */     public Std() {
/* 49 */       this(new HashMap<>());
/*    */     }
/*    */     
/*    */     public Std(Map<String, Object> values) {
/* 53 */       this._values = values;
/*    */     }
/*    */     
/*    */     public Std addValue(String key, Object value) {
/* 57 */       this._values.put(key, value);
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Std addValue(Class<?> classKey, Object value) {
/* 62 */       this._values.put(classKey.getName(), value);
/* 63 */       return this;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty forProperty, Object beanInstance) throws JsonMappingException {
/* 70 */       if (!(valueId instanceof String)) {
/* 71 */         ctxt.reportBadDefinition(ClassUtil.classOf(valueId), String.format("Unrecognized inject value id type (%s), expecting String", new Object[] { ClassUtil.classNameOf(valueId) }));
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 76 */       String key = (String)valueId;
/* 77 */       Object ob = this._values.get(key);
/* 78 */       if (ob == null && !this._values.containsKey(key)) {
/* 79 */         throw new IllegalArgumentException("No injectable id with value '" + key + "' found (for property '" + forProperty.getName() + "')");
/*    */       }
/* 81 */       return ob;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\InjectableValues.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */