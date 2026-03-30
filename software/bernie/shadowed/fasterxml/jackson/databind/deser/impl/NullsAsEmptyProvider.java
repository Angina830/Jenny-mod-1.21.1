/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullsAsEmptyProvider
/*    */   implements NullValueProvider, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final JsonDeserializer<?> _deserializer;
/*    */   
/*    */   public NullsAsEmptyProvider(JsonDeserializer<?> deser) {
/* 20 */     this._deserializer = deser;
/*    */   }
/*    */ 
/*    */   
/*    */   public AccessPattern getNullAccessPattern() {
/* 25 */     return AccessPattern.DYNAMIC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
/* 31 */     return this._deserializer.getEmptyValue(ctxt);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\NullsAsEmptyProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */