/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class StringDeserializer
/*    */   extends StdScalarDeserializer<String>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   public static final StringDeserializer instance = new StringDeserializer();
/*    */   public StringDeserializer() {
/* 20 */     super(String.class);
/*    */   }
/*    */   
/*    */   public boolean isCachable() {
/* 24 */     return true;
/*    */   }
/*    */   
/*    */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 28 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 34 */     if (p.hasToken(JsonToken.VALUE_STRING)) {
/* 35 */       return p.getText();
/*    */     }
/* 37 */     JsonToken t = p.getCurrentToken();
/*    */     
/* 39 */     if (t == JsonToken.START_ARRAY) {
/* 40 */       return _deserializeFromArray(p, ctxt);
/*    */     }
/*    */     
/* 43 */     if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 44 */       Object ob = p.getEmbeddedObject();
/* 45 */       if (ob == null) {
/* 46 */         return null;
/*    */       }
/* 48 */       if (ob instanceof byte[]) {
/* 49 */         return ctxt.getBase64Variant().encode((byte[])ob, false);
/*    */       }
/*    */       
/* 52 */       return ob.toString();
/*    */     } 
/*    */     
/* 55 */     String text = p.getValueAsString();
/* 56 */     if (text != null) {
/* 57 */       return text;
/*    */     }
/* 59 */     return (String)ctxt.handleUnexpectedToken(this._valueClass, p);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 67 */     return deserialize(p, ctxt);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StringDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */