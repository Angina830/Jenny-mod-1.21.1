/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class Jsr310KeyDeserializer
/*    */   extends KeyDeserializer
/*    */ {
/*    */   public final Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
/* 18 */     if ("".equals(key))
/*    */     {
/* 20 */       return null;
/*    */     }
/* 22 */     return deserialize(key, ctxt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Object deserialize(String paramString, DeserializationContext paramDeserializationContext) throws IOException;
/*    */ 
/*    */   
/*    */   protected <T> T _rethrowDateTimeException(DeserializationContext ctxt, Class<?> type, DateTimeException e0, String value) throws IOException {
/*    */     JsonMappingException e;
/* 32 */     if (e0 instanceof java.time.format.DateTimeParseException) {
/* 33 */       e = ctxt.weirdStringException(value, type, e0.getMessage());
/* 34 */       e.initCause(e0);
/*    */     } else {
/* 36 */       e = JsonMappingException.from(ctxt, 
/* 37 */           String.format("Failed to deserialize %s: (%s) %s", new Object[] {
/* 38 */               type.getName(), e0.getClass().getName(), e0.getMessage() }), e0);
/*    */     } 
/* 40 */     throw e;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\Jsr310KeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */