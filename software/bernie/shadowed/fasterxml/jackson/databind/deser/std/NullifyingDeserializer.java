/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullifyingDeserializer
/*    */   extends StdDeserializer<Object>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   public static final NullifyingDeserializer instance = new NullifyingDeserializer();
/*    */   public NullifyingDeserializer() {
/* 22 */     super(Object.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 32 */     return Boolean.FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 40 */     if (p.hasToken(JsonToken.FIELD_NAME)) {
/*    */       while (true) {
/* 42 */         JsonToken t = p.nextToken();
/* 43 */         if (t == null || t == JsonToken.END_OBJECT) {
/*    */           break;
/*    */         }
/* 46 */         p.skipChildren();
/*    */       } 
/*    */     } else {
/* 49 */       p.skipChildren();
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 60 */     switch (p.getCurrentTokenId()) {
/*    */       case 1:
/*    */       case 3:
/*    */       case 5:
/* 64 */         return typeDeserializer.deserializeTypedFromAny(p, ctxt);
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\NullifyingDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */