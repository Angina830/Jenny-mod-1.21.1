/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.YearMonth;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class YearMonthDeserializer
/*     */   extends JSR310DateTimeDeserializerBase<YearMonth>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   public static final YearMonthDeserializer INSTANCE = new YearMonthDeserializer();
/*     */ 
/*     */   
/*     */   private YearMonthDeserializer() {
/*  44 */     this(DateTimeFormatter.ofPattern("uuuu-MM"));
/*     */   }
/*     */ 
/*     */   
/*     */   public YearMonthDeserializer(DateTimeFormatter formatter) {
/*  49 */     super(YearMonth.class, formatter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<YearMonth> withDateFormat(DateTimeFormatter dtf) {
/*  55 */     return (JsonDeserializer<YearMonth>)new YearMonthDeserializer(dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public YearMonth deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  61 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/*  62 */       String string = parser.getText().trim();
/*  63 */       if (string.length() == 0) {
/*  64 */         return null;
/*     */       }
/*     */       try {
/*  67 */         return YearMonth.parse(string, this._formatter);
/*  68 */       } catch (DateTimeException e) {
/*  69 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  72 */     if (parser.isExpectedStartArrayToken()) {
/*  73 */       JsonToken t = parser.nextToken();
/*  74 */       if (t == JsonToken.END_ARRAY) {
/*  75 */         return null;
/*     */       }
/*  77 */       if ((t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT) && context
/*  78 */         .isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  79 */         YearMonth parsed = deserialize(parser, context);
/*  80 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  81 */           handleMissingEndArrayForSingle(parser, context);
/*     */         }
/*  83 */         return parsed;
/*     */       } 
/*  85 */       if (t != JsonToken.VALUE_NUMBER_INT) {
/*  86 */         _reportWrongToken(context, JsonToken.VALUE_NUMBER_INT, "years");
/*     */       }
/*  88 */       int year = parser.getIntValue();
/*  89 */       int month = parser.nextIntValue(-1);
/*  90 */       if (month == -1) {
/*  91 */         if (!parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/*  92 */           _reportWrongToken(context, JsonToken.VALUE_NUMBER_INT, "months");
/*     */         }
/*  94 */         month = parser.getIntValue();
/*     */       } 
/*  96 */       if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  97 */         throw context.wrongTokenException(parser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
/*     */       }
/*     */       
/* 100 */       return YearMonth.of(year, month);
/*     */     } 
/* 102 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/* 103 */       return (YearMonth)parser.getEmbeddedObject();
/*     */     }
/* 105 */     return (YearMonth)_reportWrongToken(parser, context, new JsonToken[] { JsonToken.VALUE_STRING, JsonToken.START_ARRAY });
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\YearMonthDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */