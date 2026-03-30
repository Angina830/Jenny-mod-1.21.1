/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.MonthDay;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.format.DateTimeFormatterBuilder;
/*    */ import java.time.temporal.ChronoField;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MonthDayKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 16 */   public static final MonthDayKeyDeserializer INSTANCE = new MonthDayKeyDeserializer();
/*    */ 
/*    */   
/* 19 */   private static final DateTimeFormatter PARSER = (new DateTimeFormatterBuilder())
/* 20 */     .appendLiteral("--")
/* 21 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/* 22 */     .appendLiteral('-')
/* 23 */     .appendValue(ChronoField.DAY_OF_MONTH, 2)
/* 24 */     .toFormatter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MonthDay deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 33 */       return MonthDay.parse(key, PARSER);
/* 34 */     } catch (DateTimeException e) {
/* 35 */       return _rethrowDateTimeException(ctxt, MonthDay.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\MonthDayKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */