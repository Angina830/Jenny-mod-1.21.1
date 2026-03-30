/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.Year;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.format.DateTimeFormatterBuilder;
/*    */ import java.time.format.SignStyle;
/*    */ import java.time.temporal.ChronoField;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ 
/*    */ public class YearKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 16 */   public static final YearKeyDeserializer INSTANCE = new YearKeyDeserializer();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   private static final DateTimeFormatter FORMATTER = (new DateTimeFormatterBuilder())
/* 22 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/* 23 */     .toFormatter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Year deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 32 */       return Year.parse(key, FORMATTER);
/* 33 */     } catch (DateTimeException e) {
/* 34 */       return _rethrowDateTimeException(ctxt, Year.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\YearKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */