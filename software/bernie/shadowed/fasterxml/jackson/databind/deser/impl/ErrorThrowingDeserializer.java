/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorThrowingDeserializer
/*    */   extends JsonDeserializer<Object>
/*    */ {
/*    */   private final Error _cause;
/*    */   
/*    */   public ErrorThrowingDeserializer(NoClassDefFoundError cause) {
/* 22 */     this._cause = cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
/* 27 */     throw this._cause;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ErrorThrowingDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */