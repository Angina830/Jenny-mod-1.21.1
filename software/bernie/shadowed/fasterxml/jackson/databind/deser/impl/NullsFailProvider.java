/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidNullException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*    */ 
/*    */ public class NullsFailProvider
/*    */   implements NullValueProvider, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final PropertyName _name;
/*    */   protected final JavaType _type;
/*    */   
/*    */   protected NullsFailProvider(PropertyName name, JavaType type) {
/* 21 */     this._name = name;
/* 22 */     this._type = type;
/*    */   }
/*    */   
/*    */   public static NullsFailProvider constructForProperty(BeanProperty prop) {
/* 26 */     return new NullsFailProvider(prop.getFullName(), prop.getType());
/*    */   }
/*    */   
/*    */   public static NullsFailProvider constructForRootValue(JavaType t) {
/* 30 */     return new NullsFailProvider(null, t);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AccessPattern getNullAccessPattern() {
/* 36 */     return AccessPattern.DYNAMIC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
/* 42 */     throw InvalidNullException.from(ctxt, this._name, this._type);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\NullsFailProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */