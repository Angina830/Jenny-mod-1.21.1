/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*    */ public class Jsr310NullKeySerializer
/*    */   extends JsonSerializer<Object>
/*    */ {
/*    */   public static final String NULL_KEY = "";
/*    */   
/*    */   public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
/* 26 */     if (value != null) {
/* 27 */       throw JsonMappingException.from(gen, "Jsr310NullKeySerializer is only for serializing null values.");
/*    */     }
/*    */     
/* 30 */     gen.writeFieldName("");
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\key\Jsr310NullKeySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */