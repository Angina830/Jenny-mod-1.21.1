/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.module;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.KeyDeserializers;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ClassKey;
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
/*    */ public class SimpleKeyDeserializers
/*    */   implements KeyDeserializers, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 27 */   protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;
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
/*    */   public SimpleKeyDeserializers addDeserializer(Class<?> forClass, KeyDeserializer deser) {
/* 39 */     if (this._classMappings == null) {
/* 40 */       this._classMappings = new HashMap<>();
/*    */     }
/* 42 */     this._classMappings.put(new ClassKey(forClass), deser);
/* 43 */     return this;
/*    */   }
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
/*    */   public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) {
/* 56 */     if (this._classMappings == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     return this._classMappings.get(new ClassKey(type.getRawClass()));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\module\SimpleKeyDeserializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */