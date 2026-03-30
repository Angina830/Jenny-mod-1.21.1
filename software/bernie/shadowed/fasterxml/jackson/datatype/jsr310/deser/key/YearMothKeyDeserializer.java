/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.YearMonth;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.format.DateTimeFormatterBuilder;
/*    */ import java.time.format.SignStyle;
/*    */ import java.time.temporal.ChronoField;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class YearMothKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 17 */   public static final YearMothKeyDeserializer INSTANCE = new YearMothKeyDeserializer();
/*    */ 
/*    */   
/* 20 */   private static final DateTimeFormatter FORMATTER = (new DateTimeFormatterBuilder())
/* 21 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/* 22 */     .appendLiteral('-')
/* 23 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/* 24 */     .toFormatter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected YearMonth deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 33 */       return YearMonth.parse(key, FORMATTER);
/* 34 */     } catch (DateTimeException e) {
/* 35 */       return _rethrowDateTimeException(ctxt, YearMonth.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\YearMothKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */