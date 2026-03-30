/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.Year;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*    */ public class YearDeserializer
/*    */   extends JSR310DeserializerBase<Year>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 38 */   public static final YearDeserializer INSTANCE = new YearDeserializer();
/*    */   
/*    */   private final DateTimeFormatter _formatter;
/*    */ 
/*    */   
/*    */   private YearDeserializer() {
/* 44 */     this((DateTimeFormatter)null);
/*    */   }
/*    */   
/*    */   public YearDeserializer(DateTimeFormatter formatter) {
/* 48 */     super(Year.class);
/* 49 */     this._formatter = formatter;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Year deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/* 55 */     JsonToken t = parser.getCurrentToken();
/* 56 */     if (t == JsonToken.VALUE_STRING) {
/* 57 */       String string = parser.getValueAsString().trim();
/*    */       try {
/* 59 */         if (this._formatter == null) {
/* 60 */           return Year.parse(string);
/*    */         }
/* 62 */         return Year.parse(string, this._formatter);
/* 63 */       } catch (DateTimeException e) {
/* 64 */         _rethrowDateTimeException(parser, context, e, string);
/*    */       } 
/*    */     } 
/* 67 */     if (t == JsonToken.VALUE_NUMBER_INT) {
/* 68 */       return Year.of(parser.getIntValue());
/*    */     }
/* 70 */     if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 71 */       return (Year)parser.getEmbeddedObject();
/*    */     }
/* 73 */     if (parser.hasToken(JsonToken.START_ARRAY)) {
/* 74 */       return (Year)_deserializeFromArray(parser, context);
/*    */     }
/* 76 */     return (Year)_reportWrongToken(parser, context, new JsonToken[] { JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT });
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\YearDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */