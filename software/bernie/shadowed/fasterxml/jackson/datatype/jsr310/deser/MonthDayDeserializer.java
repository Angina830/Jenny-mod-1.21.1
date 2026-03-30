/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.MonthDay;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MonthDayDeserializer
/*    */   extends JSR310DateTimeDeserializerBase<MonthDay>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer(null);
/*    */   
/*    */   public MonthDayDeserializer(DateTimeFormatter formatter) {
/* 23 */     super(MonthDay.class, formatter);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonDeserializer<MonthDay> withDateFormat(DateTimeFormatter dtf) {
/* 28 */     return (JsonDeserializer<MonthDay>)new MonthDayDeserializer(dtf);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/* 34 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/* 35 */       String string = parser.getValueAsString().trim();
/*    */       try {
/* 37 */         if (this._formatter == null) {
/* 38 */           return MonthDay.parse(string);
/*    */         }
/* 40 */         return MonthDay.parse(string, this._formatter);
/* 41 */       } catch (DateTimeException e) {
/* 42 */         _rethrowDateTimeException(parser, context, e, string);
/*    */       } 
/*    */     } 
/* 45 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/* 46 */       return (MonthDay)parser.getEmbeddedObject();
/*    */     }
/* 48 */     if (parser.hasToken(JsonToken.START_ARRAY)) {
/* 49 */       return (MonthDay)_deserializeFromArray(parser, context);
/*    */     }
/* 51 */     return (MonthDay)_reportWrongToken(parser, context, new JsonToken[] { JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT });
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\MonthDayDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */