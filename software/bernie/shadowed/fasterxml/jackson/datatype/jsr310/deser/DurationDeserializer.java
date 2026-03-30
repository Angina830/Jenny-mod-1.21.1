/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.math.BigDecimal;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.Duration;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.DecimalUtils;
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
/*    */ public class DurationDeserializer
/*    */   extends JSR310DeserializerBase<Duration>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 41 */   public static final DurationDeserializer INSTANCE = new DurationDeserializer();
/*    */ 
/*    */   
/*    */   private DurationDeserializer() {
/* 45 */     super(Duration.class);
/*    */   } public Duration deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*    */     BigDecimal value;
/*    */     long seconds;
/*    */     int nanoseconds;
/*    */     String string;
/* 51 */     switch (parser.getCurrentTokenId()) {
/*    */       
/*    */       case 8:
/* 54 */         value = parser.getDecimalValue();
/* 55 */         seconds = value.longValue();
/* 56 */         nanoseconds = DecimalUtils.extractNanosecondDecimal(value, seconds);
/* 57 */         return Duration.ofSeconds(seconds, nanoseconds);
/*    */       
/*    */       case 7:
/* 60 */         if (context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 61 */           return Duration.ofSeconds(parser.getLongValue());
/*    */         }
/* 63 */         return Duration.ofMillis(parser.getLongValue());
/*    */       
/*    */       case 6:
/* 66 */         string = parser.getText().trim();
/* 67 */         if (string.length() == 0) {
/* 68 */           return null;
/*    */         }
/*    */         try {
/* 71 */           return Duration.parse(string);
/* 72 */         } catch (DateTimeException e) {
/* 73 */           return (Duration)_rethrowDateTimeException(parser, context, e, string);
/*    */         } 
/*    */ 
/*    */       
/*    */       case 12:
/* 78 */         return (Duration)parser.getEmbeddedObject();
/*    */       
/*    */       case 3:
/* 81 */         return (Duration)_deserializeFromArray(parser, context);
/*    */     } 
/* 83 */     return (Duration)_reportWrongToken(parser, context, new JsonToken[] { JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT });
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\DurationDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */