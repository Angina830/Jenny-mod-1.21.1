/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.ZonedDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ 
/*    */ public class ZonedDateTimeKeySerializer
/*    */   extends JsonSerializer<ZonedDateTime>
/*    */ {
/* 14 */   public static final ZonedDateTimeKeySerializer INSTANCE = new ZonedDateTimeKeySerializer();
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
/*    */   public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
/* 28 */     gen.writeFieldName(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\key\ZonedDateTimeKeySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */