/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.OffsetDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ public class OffsetDateTimeKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 12 */   public static final OffsetDateTimeKeyDeserializer INSTANCE = new OffsetDateTimeKeyDeserializer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected OffsetDateTime deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 21 */       return OffsetDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
/* 22 */     } catch (DateTimeException e) {
/* 23 */       return _rethrowDateTimeException(ctxt, OffsetDateTime.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\OffsetDateTimeKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */